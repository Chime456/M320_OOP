package M4;

import java.io.Serializable;

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

    // Clean Code (aussagekräftiger Name): Der Aufrufer liest die Regel, ohne den Vergleich zu kennen.
    public boolean istUeberlastet() {  //Polymorphismus
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
