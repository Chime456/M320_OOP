# Clean Code: D4, V4 und Q4

Zurück zum [Kompetenznachweis](Kompetenznachweis.md)

## D4

| Anforderung | Stelle im Code |
|---|---|
| Delegation | `ConsoleUI.patientEntlassen()` ruft `SpitalService.entlassen()` auf, dieser ruft `Repository.speichern()` auf |
| Trennung von Benutzerschnittstelle und Logik | Eingaben und Ausgaben nur in `ConsoleUI`, Regeln in `SpitalService` |
| Eigene Exception für fehlerhafte Eingaben | `SpitalException`, geworfen z.B. in `ConsoleUI.leseId()`, abgefangen in `ConsoleUI.start()` |
| Generics in einer eigenen Klasse | `DateiRepository<T>` |

## V4

| Anforderung | Stelle im Code |
|---|---|
| Vererbung | `Arzt` und `Pflegekraft` erben von `Personal` |
| Interfaces | `VersicherungStrategy`, `Repository<T>` |
| Polymorphie | `Personal.istUeberlastet()` ruft `getMaxPatienten()` auf, `Rechnung` ruft `versicherung.berechne()` auf |
| Design Pattern Strategy | `VersicherungStrategy`, `VersicherungsKlasse` |
| Design Pattern Repository | `Repository<T>`, `DateiRepository<T>` |

## Q4: Clean-Code-Regeln

### Im Code markierte Stellen

Diese Stellen sind direkt im Code mit einem Kommentar `// Clean Code (...)` markiert:

| Regel | Datei | Was dort zu sehen ist |
|---|---|---|
| Eine Aufgabe pro Klasse | `SpitalService` | Keine Ein- oder Ausgaben und kein Dateizugriff |
| Kleine Methoden | `SpitalService` | `pruefeAufnahmeMoeglich()` ersetzt einen Kommentar in `aufnehmen()` |
| Aussagekräftiger Name | `Personal` | `istUeberlastet()` macht die Regel lesbar, ohne dass man den Vergleich kennen muss |
| Keine magischen Zahlen | `Arzt`, `Pflegekraft` | `MAX_PATIENTEN` hat einen Namen und steht nur an einem Ort |
| Keine magischen Zahlen | `VersicherungsKlasse` | `FAKTOR_HALBPRIVAT`, `FAKTOR_PRIVAT` |
| Keine magischen Werte | `ConsoleUI` | `DATEN_ORDNER`, `NAME_MUSTER`, `ZIMMERNUMMER_MUSTER` |
| Kommentar erklärt das Warum | `Patient` | Warum Zimmer und Personal nur über die ID referenziert werden |

### Vorher und Nachher

**Echte Änderung im Projekt (Commit `c50600f`):** Der Kommentar wiederholte einen Wert und wäre bei einer Änderung der Konstanten falsch geworden.

```java
// Vorher
// Ein Arzt betreut maximal 10 Patienten.
public class Arzt extends Personal {

// Nachher
// Spezialisierung von Personal: legt Rolle und maximale Patientenzahl für Ärzte fest.
public class Arzt extends Personal {
```

**Kommentar, der nur den Code wiederholt:** Das «Vorher» steht heute noch in `src/D3/WeatherApp.java`. Der Methodenname sagt bereits dasselbe.

```java
// Vorher
// Delegation: Eingabe lesen
String input = ui.askCity();

// Nachher
String input = ui.askCity();
```

**Kommentar durch eine benannte Methode ersetzen:** Das «Vorher» ist eine Vergleichsvariante und stand so nicht im Code. Das «Nachher» steht in `SpitalService.aufnehmen()`.

```java
// Vorher
// prüfen, ob ein Bett frei ist und das Personal Platz hat
if (zielZimmer.istVoll()) { ... }
if (arzt.istUeberlastet()) { ... }
if (pflegekraft.istUeberlastet()) { ... }

// Nachher
pruefeAufnahmeMoeglich(zielZimmer, arzt, pflegekraft);
```

**Kommentar zu einer Zahl durch eine Konstante ersetzen:** Das «Vorher» ist eine Vergleichsvariante und stand so nicht im Code. Das «Nachher» steht in `Arzt`.

```java
// Vorher
return 10; // maximale Anzahl Patienten

// Nachher
private static final int MAX_PATIENTEN = 10;

return MAX_PATIENTEN;
```

### Was noch besser werden könnte

- **Doppelte Meldungen:** «ist überlastet» und «ist voll» stehen in `SpitalService` und zusätzlich in `Personal` und `Zimmer`. Der Text sollte nur an einem Ort erzeugt werden.
- **Zu viele Aufgaben in `ConsoleUI`:** Die Klasse startet die Applikation, führt das Menü, prüft Eingaben und zeigt Ausgaben an. Die Prüfung könnte in eine eigene Klasse.
- **Strategy nicht konsequent:** `Patient` und `Rechnung` verwenden direkt das Enum `VersicherungsKlasse`. Mit `VersicherungStrategy` als Attributtyp wäre der Code unabhängiger von der konkreten Umsetzung.
- **Lange Parameterliste:** `SpitalService.aufnehmen()` hat sechs Parameter. Ein eigenes Objekt für die Aufnahmedaten wäre übersichtlicher.
