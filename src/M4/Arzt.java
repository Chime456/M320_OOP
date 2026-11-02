package M4;

// Spezialisierung von Personal: legt Rolle und maximale Patientenzahl für Ärzte fest.
public class Arzt extends Personal {

    private static final long serialVersionUID = 1L;
    // Clean Code (keine magischen Zahlen): Der Grenzwert hat einen Namen und steht nur hier.
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
