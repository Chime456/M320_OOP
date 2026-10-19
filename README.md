# M320_OOP – Wetter-Berater

Kompetenznachweis D3 (Delegation, eigene Exception, Trennung von UI / Logik / API)

## Beschreibung

Der **Wetter-Berater** ist eine kleine Java-Konsolenapplikation. Der Benutzer gibt eine Stadt
ein. Die App holt über die kostenlose [Open-Meteo API](https://open-meteo.com) (kein API-Key)
die aktuelle Temperatur und gibt eine Kleidungsempfehlung aus. Mit `exit` wird die App beendet.

Ungültige Eingaben (leer, Zahlen oder Sonderzeichen, unbekannte Stadt) führen zu einer eigenen
`InvalidInputException`. Die App zeigt dann eine Fehlermeldung und fragt erneut.

```
Stadt: Zürich
In Zürich ist es 14.2 °C.
Empfehlung: Pullover

Stadt: Z123
Fehler: Die Stadt darf nur Buchstaben enthalten.
```

## Klassen

| Klasse                  | Aufgabe                                          |
|-------------------------|--------------------------------------------------|
| `Main`                  | Startet nur die App                              |
| `WeatherApp`            | Steuert den Ablauf und delegiert an die anderen  |
| `ConsoleUI`             | User-Input und Ausgabe                           |
| `InputValidator`        | Prüft die Eingabe, wirft `InvalidInputException` |
| `WeatherService`        | Logik: Temperatur holen, Empfehlung berechnen    |
| `WeatherApi`            | Aufruf der fremden API (HTTP)                    |
| `InvalidInputException` | Eigene Exception für falsche Eingaben            |

```mermaid
classDiagram
    Main ..> WeatherApp
    WeatherApp --> ConsoleUI
    WeatherApp --> InputValidator
    WeatherApp --> WeatherService
    WeatherService --> WeatherApi
    InputValidator ..> InvalidInputException : wirft
    WeatherApi ..> InvalidInputException : wirft
```

## Fragen zur Besprechung

**Delegation:** `WeatherApp` macht die Arbeit nicht selbst. Die Ein- und Ausgabe gibt sie an
`ConsoleUI` ab, die Prüfung an `InputValidator` und die Logik an `WeatherService`.
`WeatherService` gibt den API-Aufruf an `WeatherApi` weiter.

**Kommunikation:** Die Objekte rufen gegenseitig Methoden auf und geben Werte als Parameter mit.
Das Resultat kommt als Rückgabewert zurück:
`askCity()` → `"Zürich"` → `validateCity("Zürich")` → `getTemperature("Zürich")` →
`findCoordinates("Zürich")` → `getTemperature(lat, lon)` → `14.2` → `getRecommendation(14.2)` →
`showResult(...)`. Fehler werden als Exception zurückgegeben und in `WeatherApp` abgefangen.

**Trennung:** Nur `ConsoleUI` liest und schreibt auf die Konsole. Nur `WeatherService` enthält
Logik. Nur `WeatherApi` kennt URLs und HTTP.

## Starten

In IntelliJ `src/Main.java` öffnen und auf ▶ klicken. Es braucht eine Internetverbindung.
