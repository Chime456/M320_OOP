package M4;

import java.io.Serializable;

// KI-Hilfe: Mit Claude Code (Claude Opus 5.5) generiert.
// Ein Spitalzimmer mit seinem Typ und seiner aktuellen Belegung.
public class Zimmer implements Serializable {

    private static final long serialVersionUID = 1L;

    private final long id;
    private final String nummer;
    private final ZimmerTyp typ;
    private int belegteBetten;

    public Zimmer(long id, String nummer, ZimmerTyp typ) {
        this.id = id;
        this.nummer = nummer;
        this.typ = typ;
    }

    public boolean istVoll() {
        return belegteBetten >= typ.getBettenzahl();
    }

    public void belegeBett() throws SpitalException {
        if (istVoll()) {
            throw new SpitalException("Zimmer " + nummer + " ist voll.");
        }
        belegteBetten++;
    }

    public void gibBettFrei() {
        if (belegteBetten > 0) {
            belegteBetten--;
        }
    }

    public int getFreieBetten() {
        return typ.getBettenzahl() - belegteBetten;
    }

    public long getId() {
        return id;
    }

    public String getNummer() {
        return nummer;
    }

    public ZimmerTyp getTyp() {
        return typ;
    }

    public int getBelegteBetten() {
        return belegteBetten;
    }
}
