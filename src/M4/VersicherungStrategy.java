package M4;

// KI-Hilfe: Mit Claude Code (Claude Opus 5.5) generiert.
// Strategy Pattern: Jede Versicherungsklasse berechnet den Rechnungsbetrag auf ihre eigene Art.
public interface VersicherungStrategy {

    double berechne(double zimmerKosten);
}
