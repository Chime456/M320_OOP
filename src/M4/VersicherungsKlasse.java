package M4;

// KI-Hilfe: Mit Claude Code (Claude Opus 5.5) generiert.
// Die Versicherungsklassen sind die konkreten Strategien für die Berechnung der Rechnung.
public enum VersicherungsKlasse implements VersicherungStrategy {

    ALLGEMEIN("Allgemein") {
        @Override
        public double berechne(double zimmerKosten) {
            return zimmerKosten;
        }
    },
    HALBPRIVAT("Halbprivat") {
        @Override
        public double berechne(double zimmerKosten) {
            return zimmerKosten * FAKTOR_HALBPRIVAT;
        }
    },
    PRIVAT("Privat") {
        @Override
        public double berechne(double zimmerKosten) {
            return zimmerKosten * FAKTOR_PRIVAT;
        }
    };

    private static final double FAKTOR_HALBPRIVAT = 1.3;
    private static final double FAKTOR_PRIVAT = 1.6;

    private final String bezeichnung;

    VersicherungsKlasse(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }
}
