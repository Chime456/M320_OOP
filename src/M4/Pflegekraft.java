package M4;

// KI-Hilfe: Mit Claude Code (Claude Opus 5.5) generiert.
// Spezialisierung von Personal: legt Rolle und maximale Patientenzahl für Pflegekräfte fest.
public class Pflegekraft extends Personal {

    private static final long serialVersionUID = 1L;
    // Clean Code (keine magischen Zahlen): Der Grenzwert hat einen Namen und steht nur hier.
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
