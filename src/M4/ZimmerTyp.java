package M4;

// KI-Hilfe: Mit Claude Code (Claude Opus 5.5) generiert.
// Die Zimmertypen unterscheiden sich nur in ihren Werten, darum genügt ein Enum ohne Vererbung.
public enum ZimmerTyp {

    MEHRBETT("Mehrbettzimmer", 4, 300.0),
    EINZEL("Einzelzimmer", 1, 500.0),
    INTENSIV("Intensivzimmer", 2, 1200.0);

    private final String bezeichnung;
    private final int bettenzahl;
    private final double tagespauschale;

    ZimmerTyp(String bezeichnung, int bettenzahl, double tagespauschale) {
        this.bezeichnung = bezeichnung;
        this.bettenzahl = bettenzahl;
        this.tagespauschale = tagespauschale;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public int getBettenzahl() {
        return bettenzahl;
    }

    public double getTagespauschale() {
        return tagespauschale;
    }
}
