package M4;

// Eigene Exception für falsche Eingaben und verletzte Spitalregeln.
public class SpitalException extends Exception {

    private static final long serialVersionUID = 1L;

    public SpitalException(String meldung) {
        super(meldung);
    }
}
