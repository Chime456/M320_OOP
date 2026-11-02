package M4;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

// Ein Patient mit seinem Aufenthalt.
// Clean Code (Kommentar erklärt das Warum): Zimmer und Personal werden über ihre ID referenziert,
// damit beim Speichern in getrennten Dateien keine doppelten Objekte entstehen.
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final long MINDEST_TAGE = 1;

    private final long id;
    private final String name;
    private final VersicherungsKlasse versicherung;
    private final long arztId;
    private final long pflegekraftId;
    private final LocalDate eintritt;
    private long zimmerId;
    private LocalDate austritt;

    public Patient(long id, String name, VersicherungsKlasse versicherung, long zimmerId,
                   long arztId, long pflegekraftId, LocalDate eintritt) {
        this.id = id;
        this.name = name;
        this.versicherung = versicherung;
        this.zimmerId = zimmerId;
        this.arztId = arztId;
        this.pflegekraftId = pflegekraftId;
        this.eintritt = eintritt;
    }

    public void verlegeNach(long neueZimmerId) {
        zimmerId = neueZimmerId;
    }

    public void entlasse(LocalDate datum) {
        austritt = datum;
    }

    public boolean istEntlassen() {
        return austritt != null;
    }

    public long getAnzahlTage() {
        LocalDate ende = istEntlassen() ? austritt : LocalDate.now();
        return Math.max(MINDEST_TAGE, ChronoUnit.DAYS.between(eintritt, ende));
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public VersicherungsKlasse getVersicherung() {
        return versicherung;
    }

    public long getZimmerId() {
        return zimmerId;
    }

    public long getArztId() {
        return arztId;
    }

    public long getPflegekraftId() {
        return pflegekraftId;
    }

    public LocalDate getEintritt() {
        return eintritt;
    }
}
