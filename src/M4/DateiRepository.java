package M4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.ToLongFunction;

// KI-Hilfe: Mit Claude Code (Claude Opus 5.5) generiert.
// Eigene generische Klasse: Speichert Objekte eines beliebigen Typs T dauerhaft in einer Datei.
public class DateiRepository<T extends Serializable> implements Repository<T> {

    private final Path datei;
    private final ToLongFunction<T> idLeser;
    private final List<T> objekte;

    public DateiRepository(Path datei, ToLongFunction<T> idLeser) {
        this.datei = datei;
        this.idLeser = idLeser;
        this.objekte = ladeAusDatei();
    }

    @Override
    public void speichern(T objekt) {
        int position = findePosition(idLeser.applyAsLong(objekt));
        if (position >= 0) {
            objekte.set(position, objekt);
        } else {
            objekte.add(objekt);
        }
        schreibeInDatei();
    }

    @Override
    public Optional<T> findeNachId(long id) {
        return objekte.stream()
                .filter(objekt -> idLeser.applyAsLong(objekt) == id)
                .findFirst();
    }

    @Override
    public List<T> findeAlle() {
        return new ArrayList<>(objekte);
    }

    private int findePosition(long id) {
        for (int i = 0; i < objekte.size(); i++) {
            if (idLeser.applyAsLong(objekte.get(i)) == id) {
                return i;
            }
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    private List<T> ladeAusDatei() {
        if (!Files.exists(datei)) {
            return new ArrayList<>();
        }
        try (ObjectInputStream eingabe = new ObjectInputStream(Files.newInputStream(datei))) {
            return (List<T>) eingabe.readObject();
        } catch (IOException e) {
            throw new UncheckedIOException("Datei konnte nicht gelesen werden: " + datei, e);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Datei enthält unbekannte Daten: " + datei, e);
        }
    }

    private void schreibeInDatei() {
        try (ObjectOutputStream ausgabe = new ObjectOutputStream(Files.newOutputStream(datei))) {
            ausgabe.writeObject(new ArrayList<>(objekte));
        } catch (IOException e) {
            throw new UncheckedIOException("Datei konnte nicht geschrieben werden: " + datei, e);
        }
    }
}
