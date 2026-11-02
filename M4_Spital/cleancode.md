# Cleancode: D4, V4 und Q4

Dieses Dokument gehört zum Projekt Spitalverwaltung. Die restliche Dokumentation steht im [Kompetenznachweis](Kompetenznachweis.md).

## D4

| Anforderung | Status | Stelle im Code |
|---|---|---|
| Delegation | umgesetzt | `ConsoleUI.patientEntlassen()` ruft `SpitalService.entlassen()` auf, dieser ruft `Repository.speichern()` auf |
| Trennung von Benutzerschnittstelle und Logik | umgesetzt | Eingaben und Ausgaben nur in `ConsoleUI`, Regeln in `SpitalService` |
| Eigene Exception für fehlerhafte Eingaben | umgesetzt | `SpitalException`, geworfen z.B. in `ConsoleUI.leseId()`, abgefangen in `ConsoleUI.start()` |
| Generics in einer eigenen Klasse | umgesetzt | `DateiRepository<T>` |

## V4

| Anforderung | Status | Stelle im Code |
|---|---|---|
| Vererbung | umgesetzt | `Arzt` und `Pflegekraft` erben von `Personal` |
| Interfaces | umgesetzt | `VersicherungStrategy`, `Repository<T>` |
| Polymorphie | umgesetzt | `Personal.istUeberlastet()` ruft `getMaxPatienten()` auf, `Rechnung` ruft `versicherung.berechne()` auf |
| Design Pattern Strategy | umgesetzt | `VersicherungStrategy`, `VersicherungsKlasse` |
| Design Pattern Repository | umgesetzt | `Repository<T>`, `DateiRepository<T>` |

## Q4: Clean Code und Kommentare

| Clean-Code-Regel | Anwendung | Stelle im Code |
|---|---|---|
| Aussagekräftige Namen | Klassen und Methoden sagen, was sie tun | `Personal.istUeberlastet()`, `SpitalService.findeAktivenPatienten()` |
| Eine Aufgabe pro Klasse | UI, Logik und Speicherung sind getrennt | `ConsoleUI`, `SpitalService`, `DateiRepository<T>` |
| Kleine Methoden | Lange Abläufe sind in benannte Methoden aufgeteilt | `SpitalService.pruefeAufnahmeMoeglich()`, `ConsoleUI.leseId()` |
| Kommentare durch Struktur vermeiden | Der Code hat kurze Klassenkommentare und Hinweise auf Clean-Code-Regeln, den Rest erklären die Namen | alle Klassen in `src/M4` |
| Keine magischen Zahlen | Werte stehen als Konstanten oder Enum-Werte an einem Ort | `ZimmerTyp`, `Arzt.MAX_PATIENTEN`, `VersicherungsKlasse.FAKTOR_PRIVAT` |

Im Code sind die angewendeten Regeln mit einem Kommentar `// Clean Code (...)` markiert, zum Beispiel in `SpitalService`, `Personal`, `Patient`, `Arzt` und `Pflegekraft`.

### Verbesserungsvorschläge für den eigenen Code

- Die Meldungen «ist überlastet» und «ist voll» stehen doppelt: in `SpitalService` und in `Personal` bzw. `Zimmer`. Der Text könnte an einem Ort erzeugt werden, zum Beispiel in einer Methode von `Personal` und `Zimmer`.
- `ConsoleUI` macht viel: Applikation starten, Menü führen, Eingaben prüfen und Ausgaben anzeigen. Die Prüfung der Eingaben könnte in eine eigene Klasse ausgelagert werden.
- `Patient` und `Rechnung` verwenden direkt das Enum `VersicherungsKlasse`. Mit dem Interface `VersicherungStrategy` als Attributtyp wäre der Code unabhängiger von der konkreten Umsetzung.

### Vorher-Nachher-Beispiele

**Echte Änderung im Projekt: Kommentar, der veralten kann.** Der Kommentar wiederholte den Wert der Konstanten und wäre bei einer Änderung falsch geworden.

Vorher:

```java
// Ein Arzt betreut maximal 10 Patienten.
public class Arzt extends Personal {
```

Nachher:

```java
// Spezialisierung von Personal: legt Rolle und maximale Patientenzahl für Ärzte fest.
public class Arzt extends Personal {
```

**Beispiel 1: Kommentar, der nur den Code wiederholt.** Der Kommentar in `src/D3/WeatherApp.java` sagt nichts, was der Methodenname nicht schon sagt.

Vorher (D3):

```java
// Delegation: Eingabe lesen
String input = ui.askCity();
```

Nachher, im gleichen Stil wie M4:

```java
String input = ui.askCity();
```

**Beispiel 2: Kommentar durch eine benannte Methode ersetzen.** Das «Vorher» ist eine Vergleichsvariante und stand so nicht im Code. Das «Nachher» steht in `SpitalService.aufnehmen()`.

Vorher:

```java
// prüfen, ob ein Bett frei ist und das Personal Platz hat
if (zielZimmer.istVoll()) { ... }
if (arzt.istUeberlastet()) { ... }
if (pflegekraft.istUeberlastet()) { ... }
```

Nachher:

```java
pruefeAufnahmeMoeglich(zielZimmer, arzt, pflegekraft);
```

**Beispiel 3: Kommentar zu einer Zahl durch eine Konstante ersetzen.** Das «Vorher» ist eine Vergleichsvariante und stand so nicht im Code. Das «Nachher» steht in `Arzt`.

Vorher:

```java
return 10; // maximale Anzahl Patienten
```

Nachher:

```java
private static final int MAX_PATIENTEN = 10;

return MAX_PATIENTEN;
```

**Sinnvoller Kommentar:** In `Patient` bleibt der Kommentar stehen, warum Zimmer und Personal über ihre ID referenziert werden. Er erklärt das Warum, das der Code allein nicht zeigt.
