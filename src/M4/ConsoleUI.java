package M4;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

// KI-Hilfe: Mit Claude Code (Claude Opus 5.5) generiert.
// Startet die Applikation, liest und prüft die Eingaben und zeigt die Ausgaben an.
public class ConsoleUI {

    // Clean Code (keine magischen Werte): Pfad und Eingabemuster haben einen Namen und stehen nur hier.
    private static final Path DATEN_ORDNER = Path.of("daten");
    private static final String NAME_MUSTER = "[\\p{L} .'-]{2,50}";
    private static final String ZIMMERNUMMER_MUSTER = "[A-Za-z0-9-]{1,10}";
    private static final String MENUE_BEENDEN = "0";

    private final SpitalService service;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(SpitalService service) {
        this.service = service;
    }

    public static void main(String[] args) {
        try {
            Files.createDirectories(DATEN_ORDNER);
            SpitalService service = new SpitalService(
                    new DateiRepository<>(DATEN_ORDNER.resolve("patienten.dat"), Patient::getId),
                    new DateiRepository<>(DATEN_ORDNER.resolve("zimmer.dat"), Zimmer::getId),
                    new DateiRepository<>(DATEN_ORDNER.resolve("personal.dat"), Personal::getId),
                    new DateiRepository<>(DATEN_ORDNER.resolve("rechnungen.dat"), Rechnung::getId));
            service.legeStartdatenAn();
            new ConsoleUI(service).start();
        } catch (IOException | UncheckedIOException | IllegalStateException | SpitalException e) {
            System.out.println("Die Applikation konnte nicht gestartet werden: " + e.getMessage());
        }
    }

    public void start() {
        System.out.println("=== Spitalverwaltung ===");
        boolean laeuft = true;
        while (laeuft) {
            zeigeMenue();
            String auswahl = frage("Auswahl: ");
            try {
                laeuft = fuehreAus(auswahl);
            } catch (SpitalException e) {
                zeigeFehler(e.getMessage());
            } catch (UncheckedIOException e) {
                zeigeFehler("Die Daten konnten nicht gespeichert werden: " + e.getMessage());
            }
        }
        System.out.println("Auf Wiedersehen!");
    }

    private void zeigeMenue() {
        System.out.println();
        System.out.println("1) Patient aufnehmen");
        System.out.println("2) Patient verlegen");
        System.out.println("3) Patient entlassen");
        System.out.println("4) Zimmer erfassen");
        System.out.println("5) Zimmer anzeigen");
        System.out.println("6) Personal anzeigen");
        System.out.println("7) Rechnungen anzeigen");
        System.out.println("8) Personal erfassen");
        System.out.println("9) Patienten anzeigen");
        System.out.println(MENUE_BEENDEN + ") Beenden");
    }

    private boolean fuehreAus(String auswahl) throws SpitalException {
        switch (auswahl) {
            case "1" -> patientAufnehmen();
            case "2" -> patientVerlegen();
            case "3" -> patientEntlassen();
            case "4" -> zimmerErfassen();
            case "5" -> zeigeZimmer();
            case "6" -> zeigePersonal();
            case "7" -> zeigeRechnungen();
            case "8" -> personalErfassen();
            case "9" -> zeigePatienten();
            case MENUE_BEENDEN -> {
                return false;
            }
            default -> throw new SpitalException("Bitte eine Zahl aus dem Menü wählen.");
        }
        return true;
    }

    private void patientAufnehmen() throws SpitalException {
        String name = leseName("Name des Patienten: ");
        VersicherungsKlasse versicherung = leseVersicherung();
        LocalDate eintritt = leseEintritt();
        zeigeZimmer();
        long zimmerId = leseId("Zimmer-ID: ");
        zeigePersonal();
        long arztId = leseId("Arzt-ID: ");
        long pflegekraftId = leseId("Pflegekraft-ID: ");

        Patient patient = service.aufnehmen(name, versicherung, zimmerId, arztId, pflegekraftId, eintritt);
        System.out.println("Patient " + patient.getName() + " wurde mit der ID " + patient.getId() + " aufgenommen.");
    }

    private void patientVerlegen() throws SpitalException {
        zeigePatienten();
        long patientId = leseId("Patienten-ID: ");
        zeigeZimmer();
        long zimmerId = leseId("Neue Zimmer-ID: ");

        service.verlegen(patientId, zimmerId);
        System.out.println("Der Patient wurde verlegt.");
    }

    private void patientEntlassen() throws SpitalException {
        zeigePatienten();
        long patientId = leseId("Patienten-ID: ");

        Rechnung rechnung = service.entlassen(patientId);
        System.out.println("Der Patient wurde entlassen.");
        zeigeRechnung(rechnung);
    }

    private void zimmerErfassen() throws SpitalException {
        String nummer = leseZimmernummer();
        ZimmerTyp typ = leseZimmerTyp();

        Zimmer zimmer = service.erfasseZimmer(nummer, typ);
        System.out.println("Zimmer " + zimmer.getNummer() + " wurde mit der ID " + zimmer.getId() + " erfasst.");
    }

    private void personalErfassen() throws SpitalException {
        System.out.println("1) Arzt");
        System.out.println("2) Pflegekraft");
        String rolle = frage("Rolle: ");
        if (!rolle.equals("1") && !rolle.equals("2")) {
            throw new SpitalException("Bitte 1 oder 2 wählen.");
        }
        String name = leseName("Name: ");

        Personal person = rolle.equals("1") ? service.erfasseArzt(name) : service.erfassePflegekraft(name);
        System.out.println(person.getRolle() + " " + person.getName() + " wurde mit der ID " + person.getId() + " erfasst.");
    }

    private void zeigePatienten() {
        List<Patient> patienten = service.getAktivePatienten();
        System.out.println("--- Aktive Patienten ---");
        if (patienten.isEmpty()) {
            System.out.println("Keine aktiven Patienten.");
        }
        for (Patient patient : patienten) {
            System.out.printf("ID %d: %s, %s, Zimmer-ID %d, Eintritt %s%n", patient.getId(), patient.getName(),
                    patient.getVersicherung().getBezeichnung(), patient.getZimmerId(), patient.getEintritt());
        }
    }

    private void zeigeZimmer() {
        System.out.println("--- Zimmer ---");
        for (Zimmer zimmer : service.getZimmer()) {
            System.out.printf("ID %d: Zimmer %s, %s, %d von %d Betten frei, CHF %.2f pro Tag%n", zimmer.getId(),
                    zimmer.getNummer(), zimmer.getTyp().getBezeichnung(), zimmer.getFreieBetten(),
                    zimmer.getTyp().getBettenzahl(), zimmer.getTyp().getTagespauschale());
        }
    }

    private void zeigePersonal() {
        System.out.println("--- Personal ---");
        for (Personal person : service.getPersonal()) {
            System.out.printf("ID %d: %s %s, %d von %d Patienten%n", person.getId(), person.getRolle(),
                    person.getName(), person.getAnzahlPatienten(), person.getMaxPatienten());
        }
    }

    private void zeigeRechnungen() {
        List<Rechnung> rechnungen = service.getRechnungen();
        System.out.println("--- Rechnungen ---");
        if (rechnungen.isEmpty()) {
            System.out.println("Noch keine Rechnungen.");
        }
        for (Rechnung rechnung : rechnungen) {
            zeigeRechnung(rechnung);
        }
    }

    private void zeigeRechnung(Rechnung rechnung) {
        System.out.printf("Rechnung %d vom %s für %s%n", rechnung.getId(), rechnung.getDatum(), rechnung.getPatientName());
        System.out.printf("  Zimmer %s (%s), %d Tage: CHF %.2f%n", rechnung.getZimmerNummer(),
                rechnung.getZimmerTyp().getBezeichnung(), rechnung.getAnzahlTage(), rechnung.getZimmerKosten());
        System.out.printf("  Versicherung %s, Total: CHF %.2f%n", rechnung.getVersicherung().getBezeichnung(),
                rechnung.getTotalBetrag());
    }

    private void zeigeFehler(String meldung) {
        System.out.println("Fehler: " + meldung);
    }

    private String frage(String text) {
        System.out.print(text);
        return scanner.hasNextLine() ? scanner.nextLine().trim() : MENUE_BEENDEN;
    }

    private long leseId(String text) throws SpitalException {
        String eingabe = frage(text);
        try {
            long id = Long.parseLong(eingabe);
            if (id <= 0) {
                throw new SpitalException("Die ID muss grösser als 0 sein.");
            }
            return id;
        } catch (NumberFormatException e) {
            throw new SpitalException("Die ID muss eine ganze Zahl sein: '" + eingabe + "'");
        }
    }

    private String leseName(String text) throws SpitalException {
        String name = frage(text);
        if (!name.matches(NAME_MUSTER)) {
            throw new SpitalException("Der Name muss 2-50 Zeichen lang sein und darf keine Zahlen enthalten.");
        }
        return name;
    }

    private String leseZimmernummer() throws SpitalException {
        String nummer = frage("Zimmernummer: ");
        if (!nummer.matches(ZIMMERNUMMER_MUSTER)) {
            throw new SpitalException("Die Zimmernummer darf nur Buchstaben, Zahlen und Bindestriche enthalten (maximal 10 Zeichen).");
        }
        return nummer;
    }

    private VersicherungsKlasse leseVersicherung() throws SpitalException {
        VersicherungsKlasse[] klassen = VersicherungsKlasse.values();
        for (int i = 0; i < klassen.length; i++) {
            System.out.println((i + 1) + ") " + klassen[i].getBezeichnung());
        }
        return klassen[leseAuswahl("Versicherungsklasse: ", klassen.length)];
    }

    private ZimmerTyp leseZimmerTyp() throws SpitalException {
        ZimmerTyp[] typen = ZimmerTyp.values();
        for (int i = 0; i < typen.length; i++) {
            System.out.println((i + 1) + ") " + typen[i].getBezeichnung());
        }
        return typen[leseAuswahl("Zimmertyp: ", typen.length)];
    }

    private int leseAuswahl(String text, int anzahl) throws SpitalException {
        String eingabe = frage(text);
        try {
            int auswahl = Integer.parseInt(eingabe);
            if (auswahl < 1 || auswahl > anzahl) {
                throw new SpitalException("Bitte eine Zahl von 1 bis " + anzahl + " wählen.");
            }
            return auswahl - 1;
        } catch (NumberFormatException e) {
            throw new SpitalException("Bitte eine Zahl von 1 bis " + anzahl + " wählen.");
        }
    }

    private LocalDate leseEintritt() throws SpitalException {
        String eingabe = frage("Eintritt (JJJJ-MM-TT, leer = heute): ");
        if (eingabe.isEmpty()) {
            return LocalDate.now();
        }
        try {
            LocalDate eintritt = LocalDate.parse(eingabe);
            if (eintritt.isAfter(LocalDate.now())) {
                throw new SpitalException("Der Eintritt darf nicht in der Zukunft liegen.");
            }
            return eintritt;
        } catch (DateTimeParseException e) {
            throw new SpitalException("Das Datum muss das Format JJJJ-MM-TT haben: '" + eingabe + "'");
        }
    }
}
