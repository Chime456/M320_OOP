package M4;

import java.io.Serializable;
import java.time.LocalDate;

// Die Rechnung hält die Werte zum Zeitpunkt der Entlassung fest.
public class Rechnung implements Serializable {

    private static final long serialVersionUID = 1L;

    private final long id;
    private final String patientName;
    private final String zimmerNummer;
    private final ZimmerTyp zimmerTyp;
    private final VersicherungsKlasse versicherung;
    private final long anzahlTage;
    private final double zimmerKosten;
    private final double totalBetrag;
    private final LocalDate datum;

    public Rechnung(long id, Patient patient, Zimmer zimmer, LocalDate datum) {
        this.id = id;
        this.patientName = patient.getName();
        this.zimmerNummer = zimmer.getNummer();
        this.zimmerTyp = zimmer.getTyp();
        this.versicherung = patient.getVersicherung();
        this.anzahlTage = patient.getAnzahlTage();
        this.zimmerKosten = anzahlTage * zimmerTyp.getTagespauschale();
        // Delegation und Polymorphie: Die Versicherungsklasse entscheidet zur Laufzeit über den Betrag.
        this.totalBetrag = versicherung.berechne(zimmerKosten);
        this.datum = datum;
    }

    public long getId() {
        return id;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getZimmerNummer() {
        return zimmerNummer;
    }

    public ZimmerTyp getZimmerTyp() {
        return zimmerTyp;
    }

    public VersicherungsKlasse getVersicherung() {
        return versicherung;
    }

    public long getAnzahlTage() {
        return anzahlTage;
    }

    public double getZimmerKosten() {
        return zimmerKosten;
    }

    public double getTotalBetrag() {
        return totalBetrag;
    }

    public LocalDate getDatum() {
        return datum;
    }
}
