package M4;

// KI-Hilfe: Mit Claude Code (Claude Opus 5.5) generiert.
// Eine Pflegekraft betreut maximal 6 Patienten.
public class Pflegekraft extends Personal {

    private static final long serialVersionUID = 1L;
    private static final int MAX_PATIENTEN = 6;

    public Pflegekraft(long id, String name) {
        super(id, name);
    }

    @Override
    public int getMaxPatienten() {
        return MAX_PATIENTEN;
    }

    @Override
    public String getRolle() {
        return "Pflegekraft";
    }
}
