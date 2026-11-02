# KI-Doku

Dieses Dokument gehört zum Projekt Spitalverwaltung. Die restliche Dokumentation steht im [Kompetenznachweis](Kompetenznachweis.md) und in [cleancode.md](cleancode.md).

**Die gesamte Applikation in `src/M4`, die Diagramme, die Dokumentation und die Beispielwerte wurden mit Claude Code (Claude Opus 5.5) generiert.**

| Teil | Entstanden durch | Deklaration |
|---|---|---|
| Alle 14 Java-Dateien in `src/M4` | vollständig mit KI generiert | Kommentar `// KI-Hilfe` oben in jeder Datei |
| UML-Diagramme | mit KI generiert | Prompts in diesem Dokument |
| Dokumentation und Beispielwerte | mit KI generiert | Prompts in diesem Dokument |
| Idee der Applikation | selbst | – |
| Auswahl: maximal 10 Klassen, nur eine Exception-Klasse, PlantUML, Klassendiagramm nur mit Namen | selbst entschieden | – |

## Prompts

Als Grundlage erhielt die KI den M4-Auftrag und die Beschreibung der Idee (siehe «Beschreibung der Applikation» im [Kompetenznachweis](Kompetenznachweis.md)).

| Schritt | Prompt |
|---|---|
| Klassen- und Sequenzdiagramm erstellen | «bei m4 brauche ich ein klassen und sequenzdiagramm also zwei!» |
| Nur Klassen anzeigen | «klassendiagramm nur klassen anzeigen?» |
| Dokumentation im README zusammenführen | «kannst du mir der ganze auftrag in einem Readme erstellen» und «nein auch die umls in den readme, also usecases design pattern, aufgbau etc» |
| PlantUML statt Mermaid verwenden | «aber ich will nicht mermaid sondern plant uml» |
| Use-Case-Diagramm, Tabelle und Kompetenzen ergänzen (mit den Kompetenzen D4, V4 und Q4 und den Unterlagen zur Delegation) | «und ein usecase uml erstellen wäre gut und eine tabelle erstellen zu patienten aufnehmen für M4 Readme doku» |
| Beispielwerte festlegen | «durchschnittliche zahlen nicht zu extrem nicht zu wenig» |
| README als Kompetenznachweis anpassen | «REadme ist der Kompetenznachweis und dort bitte alles anppassen» |
| `Repository<T>` und `DateiRepository<T>` erstellen | «kannst du mir den auftrag für D4, V4 und Q4 erledigen !!!!!» |
| Gesamte Applikation generieren, Diagramme und Dokumentation an den Code anpassen | «ist mir egal ich brauch ein laufendes projekt jetzt» |
| Klassendiagramm nur mit Namen, restliche Dokumentation ergänzen | «bitte korrigiere uml -> nur klassenname keine inhalte, danach diese sachen noch dokumentieren» |
| Stand gegen die Level-4-Vorgaben prüfen, detailliertes Klassendiagramm, Sequenzdiagramm nachführen, Clean-Code-Hinweise im Code | «ich muss nur noch diese aufgaben machen level 4» (mit dem M4-Auftrag und den Unterlagen zu Delegation und Polymorphismus) |
| D4, V4 und Q4 in ein eigenes Dokument auslagern | «kannst du d4, v4 und q4 in einem seperates doku machen und das md cleancode beschriften» |
| KI-Einsatz in ein eigenes Dokument auslagern | «auch brauche ich noch ein KI doku» |
