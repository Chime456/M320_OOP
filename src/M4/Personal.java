package M4;

import java.io.Serializable;

// KI-Hilfe: Mit Claude Code (Claude Opus 5.5) generiert.
// Gemeinsame Daten und gemeinsames Verhalten von Ärzten und Pflegekräften.
public abstract class Personal implements Serializable {

    private static final long serialVersionUID = 1L;

    private final long id;
    private final String name;
    private int anzahlPatienten;

    protected Personal(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public abstract int getMaxPatienten();

    public abstract String getRolle();

    public boolean istUeberlastet() {
        return anzahlPatienten >= getMaxPatienten();
    }

    public void nimmPatient() throws SpitalException {
        if (istUeberlastet()) {
            throw new SpitalException(getRolle() + " " + name + " ist überlastet (maximal "
                    + getMaxPatienten() + " Patienten).");
        }
        anzahlPatienten++;
    }

    public void gibPatientAb() {
        if (anzahlPatienten > 0) {
            anzahlPatienten--;
        }
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAnzahlPatienten() {
        return anzahlPatienten;
    }
}
