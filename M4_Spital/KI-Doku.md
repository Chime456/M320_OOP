# KI-Doku: Spitalverwaltung

Zurück zum [Kompetenznachweis](Kompetenznachweis.md)

**Die gesamte Applikation in `src/M4`, die Diagramme, die Dokumentation und die Beispielwerte wurden mit Claude Code (Claude Opus 5.5) generiert.**

Der Auftrag sagt: Wird die komplette Applikation mit KI generiert, wird die Arbeit nicht akzeptiert. Dieser Punkt ist darum offen und muss mit der Lehrperson geklärt werden.

## Was von wem stammt

| Teil | Entstanden durch | Deklaration |
|---|---|---|
| Alle 14 Java-Dateien in `src/M4` | vollständig mit KI generiert | Kommentar `// KI-Hilfe` oben in jeder Datei |
| UML-Diagramme in [Diagramme.puml](Diagramme.puml) | mit KI generiert | Prompts in diesem Dokument |
| Dokumentation und Beispielwerte | mit KI generiert | Prompts in diesem Dokument |
| Idee der Applikation | selbst | – |
| Auswahl: maximal 10 Klassen, nur eine Exception-Klasse, PlantUML | selbst entschieden | – |

## Prompts

Als Grundlage erhielt die KI den M4-Auftrag und die Beschreibung der Idee. Die Prompts sind im Originalwortlaut aufgeführt.

### Diagramme

| Schritt | Prompt |
|---|---|
| Klassen- und Sequenzdiagramm erstellen | «bei m4 brauche ich ein klassen und sequenzdiagramm also zwei!» |
| Nur Klassen anzeigen | «klassendiagramm nur klassen anzeigen?» |
| PlantUML statt Mermaid verwenden | «aber ich will nicht mermaid sondern plant uml» |
| Klassendiagramm nur mit Namen, restliche Dokumentation ergänzen | «bitte korrigiere uml -> nur klassenname keine inhalte, danach diese sachen noch dokumentieren» |

### Applikation

| Prompt |
|---|
| «kannst du mir den auftrag für D4, V4 und Q4 erledigen !!!!!» |
| «ist mir egal ich brauch ein laufendes projekt jetzt» |
| «durchschnittliche zahlen nicht zu extrem nicht zu wenig» |

### Dokumentation

| Schritt | Prompt |
|---|---|
| Dokumentation zusammenführen | «kannst du mir der ganze auftrag in einem Readme erstellen» und «nein auch die umls in den readme, also usecases design pattern, aufgbau etc» |
| Use-Case-Diagramm und Tabelle ergänzen | «und ein usecase uml erstellen wäre gut und eine tabelle erstellen zu patienten aufnehmen für M4 Readme doku» |
| README als Kompetenznachweis anpassen | «REadme ist der Kompetenznachweis und dort bitte alles anppassen» |
| Stand gegen die Level-4-Vorgaben prüfen | «ich muss nur noch diese aufgaben machen level 4» (mit dem M4-Auftrag und den Unterlagen zu Delegation und Polymorphismus) |
| D4, V4 und Q4 auslagern | «kannst du d4, v4 und q4 in einem seperates doku machen und das md cleancode beschriften» |
| KI-Einsatz auslagern | «auch brauche ich noch ein KI doku» |
| Alle Dokumente und Diagramme neu erstellen (Auswahl danach: Kompetenznachweis, drei weitere Dateien, nur die Doku) | «generiere mir die 4 files» und «mir gefällt nichts, kannst du alles neu machen» |
| Vorgaben am aktuellen Stand prüfen | «wird das erfüllt mit der jetztigen stand» (mit der Liste der Vorgaben) |
