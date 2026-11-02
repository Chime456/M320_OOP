package M4;

import java.util.List;
import java.util.Optional;

// KI-Hilfe: Mit Claude Code (Claude Opus 5.5) generiert.
// Generisches Interface für das Repository Pattern: Die Logik kennt die Art der Speicherung nicht.
public interface Repository<T> {

    void speichern(T objekt);

    Optional<T> findeNachId(long id);

    List<T> findeAlle();
}
