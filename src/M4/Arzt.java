package M4;

// KI-Hilfe: Mit Claude Code (Claude Opus 5.5) generiert.
// Ein Arzt betreut maximal 10 Patienten.
public class Arzt extends Personal {

    private static final long serialVersionUID = 1L;
    private static final int MAX_PATIENTEN = 10;

    public Arzt(long id, String name) {
        super(id, name);
    }

    @Override
    public int getMaxPatienten() {
        return MAX_PATIENTEN;
    }

    @Override
    public String getRolle() {
        return "Arzt";
    }
}
