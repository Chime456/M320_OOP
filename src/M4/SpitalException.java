package M4;

// KI-Hilfe: Mit Claude Code (Claude Opus 5.5) generiert.
// Eigene Exception für falsche Eingaben und verletzte Spitalregeln.
public class SpitalException extends Exception {

    private static final long serialVersionUID = 1L;

    public SpitalException(String meldung) {
        super(meldung);
    }
}
