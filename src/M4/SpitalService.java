package M4;

import java.time.LocalDate;
import java.util.List;
import java.util.function.ToLongFunction;

// KI-Hilfe: Mit Claude Code (Claude Opus 5.5) generiert.
// Logik
// Enthält die Abläufe und Regeln des Spitals. Das Speichern wird an die Repositories delegiert.
// Clean Code (eine Aufgabe pro Klasse): Hier gibt es keine Ein- oder Ausgaben und keinen Dateizugriff.
public class SpitalService {

    private final Repository<Patient> patienten;
    private final Repository<Zimmer> zimmer;
    private final Repository<Personal> personal;
    private final Repository<Rechnung> rechnungen;

    public SpitalService(Repository<Patient> patienten, Repository<Zimmer> zimmer,
                         Repository<Personal> personal, Repository<Rechnung> rechnungen) {
        this.patienten = patienten;
        this.zimmer = zimmer;
        this.personal = personal;
        this.rechnungen = rechnungen;
    }

    public void legeStartdatenAn() throws SpitalException {
        if (zimmer.findeAlle().isEmpty()) {
            erfasseZimmer("101", ZimmerTyp.MEHRBETT);
            erfasseZimmer("102", ZimmerTyp.EINZEL);
            erfasseZimmer("201", ZimmerTyp.INTENSIV);
        }
        if (personal.findeAlle().isEmpty()) {
            erfasseArzt("Dr. Meier");
            erfasseArzt("Dr. Keller");
            erfassePflegekraft("Anna Frei");
            erfassePflegekraft("Luca Graf");
        }
    }

    public Patient aufnehmen(String name, VersicherungsKlasse versicherung, long zimmerId,
                             long arztId, long pflegekraftId, LocalDate eintritt) throws SpitalException {
        Zimmer zielZimmer = findeZimmer(zimmerId);
        Personal arzt = findeArzt(arztId);
        Personal pflegekraft = findePflegekraft(pflegekraftId);
        pruefeAufnahmeMoeglich(zielZimmer, arzt, pflegekraft);

        zielZimmer.belegeBett();
        arzt.nimmPatient();
        pflegekraft.nimmPatient();

        Patient patient = new Patient(naechsteId(patienten, Patient::getId), name, versicherung,
                zimmerId, arztId, pflegekraftId, eintritt);
        zimmer.speichern(zielZimmer);
        personal.speichern(arzt);
        personal.speichern(pflegekraft);
        patienten.speichern(patient);
        return patient;
    }

    public void verlegen(long patientId, long neueZimmerId) throws SpitalException {
        Patient patient = findeAktivenPatienten(patientId);
        if (patient.getZimmerId() == neueZimmerId) {
            throw new SpitalException("Der Patient liegt bereits in diesem Zimmer.");
        }
        Zimmer altesZimmer = findeZimmer(patient.getZimmerId());
        Zimmer neuesZimmer = findeZimmer(neueZimmerId);

        neuesZimmer.belegeBett();
        altesZimmer.gibBettFrei();
        patient.verlegeNach(neueZimmerId);

        zimmer.speichern(altesZimmer);
        zimmer.speichern(neuesZimmer);
        patienten.speichern(patient);
    }

    public Rechnung entlassen(long patientId) throws SpitalException {
        Patient patient = findeAktivenPatienten(patientId);
        Zimmer patientenZimmer = findeZimmer(patient.getZimmerId());
        Personal arzt = findeArzt(patient.getArztId());
        Personal pflegekraft = findePflegekraft(patient.getPflegekraftId());
        LocalDate heute = LocalDate.now();

        patient.entlasse(heute);
        patientenZimmer.gibBettFrei(); //delegation
        arzt.gibPatientAb();
        pflegekraft.gibPatientAb();
        Rechnung rechnung = new Rechnung(naechsteId(rechnungen, Rechnung::getId), patient, patientenZimmer, heute);

        zimmer.speichern(patientenZimmer);
        personal.speichern(arzt);
        personal.speichern(pflegekraft);
        patienten.speichern(patient);
        rechnungen.speichern(rechnung); //delegation
        return rechnung;
    }

    public Zimmer erfasseZimmer(String nummer, ZimmerTyp typ) throws SpitalException {
        boolean nummerVergeben = zimmer.findeAlle().stream()
                .anyMatch(vorhanden -> vorhanden.getNummer().equalsIgnoreCase(nummer));
        if (nummerVergeben) {
            throw new SpitalException("Die Zimmernummer " + nummer + " ist bereits vergeben.");
        }
        Zimmer neuesZimmer = new Zimmer(naechsteId(zimmer, Zimmer::getId), nummer, typ);
        zimmer.speichern(neuesZimmer);
        return neuesZimmer;
    }

    public Arzt erfasseArzt(String name) {
        Arzt arzt = new Arzt(naechsteId(personal, Personal::getId), name);
        personal.speichern(arzt);
        return arzt;
    }

    public Pflegekraft erfassePflegekraft(String name) {
        Pflegekraft pflegekraft = new Pflegekraft(naechsteId(personal, Personal::getId), name);
        personal.speichern(pflegekraft);
        return pflegekraft;
    }

    public List<Patient> getAktivePatienten() {
        return patienten.findeAlle().stream()
                .filter(patient -> !patient.istEntlassen())
                .toList();
    }

    public List<Zimmer> getZimmer() {
        return zimmer.findeAlle();
    }

    public List<Personal> getPersonal() {
        return personal.findeAlle();
    }

    public List<Rechnung> getRechnungen() {
        return rechnungen.findeAlle();
    }

    // Clean Code (kleine Methoden): Die benannte Methode ersetzt einen Kommentar in aufnehmen().
    private void pruefeAufnahmeMoeglich(Zimmer zielZimmer, Personal arzt, Personal pflegekraft)
            throws SpitalException {
        if (zielZimmer.istVoll()) {
            throw new SpitalException("Zimmer " + zielZimmer.getNummer() + " ist voll.");
        }
        pruefeNichtUeberlastet(arzt);
        pruefeNichtUeberlastet(pflegekraft);
    }

    private void pruefeNichtUeberlastet(Personal person) throws SpitalException {
        if (person.istUeberlastet()) {
            throw new SpitalException(person.getRolle() + " " + person.getName() + " ist überlastet (maximal "
                    + person.getMaxPatienten() + " Patienten).");
        }
    }

    private Patient findeAktivenPatienten(long id) throws SpitalException {
        Patient patient = patienten.findeNachId(id)
                .orElseThrow(() -> new SpitalException("Patient mit ID " + id + " wurde nicht gefunden."));
        if (patient.istEntlassen()) {
            throw new SpitalException("Patient " + patient.getName() + " ist bereits entlassen.");
        }
        return patient;
    }

    private Zimmer findeZimmer(long id) throws SpitalException {
        return zimmer.findeNachId(id)
                .orElseThrow(() -> new SpitalException("Zimmer mit ID " + id + " wurde nicht gefunden."));
    }

    private Personal findeArzt(long id) throws SpitalException {
        return personal.findeNachId(id)
                .filter(Arzt.class::isInstance)
                .orElseThrow(() -> new SpitalException("Arzt mit ID " + id + " wurde nicht gefunden."));
    }

    private Personal findePflegekraft(long id) throws SpitalException {
        return personal.findeNachId(id)
                .filter(Pflegekraft.class::isInstance)
                .orElseThrow(() -> new SpitalException("Pflegekraft mit ID " + id + " wurde nicht gefunden."));
    }

    private <T> long naechsteId(Repository<T> repository, ToLongFunction<T> idLeser) {
        return repository.findeAlle().stream().mapToLong(idLeser).max().orElse(0) + 1;
    }
}
