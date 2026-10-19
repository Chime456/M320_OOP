import java.io.IOException;

// Steuert den Ablauf und gibt die Aufgaben an die anderen Klassen weiter.
public class WeatherApp {

    // Delegation: WeatherApp hat diese Objekte als Attribute
    private final ConsoleUI ui = new ConsoleUI();
    private final InputValidator validator = new InputValidator();
    private final WeatherService service = new WeatherService(new WeatherApi());

    public void start() {
        ui.showWelcome();
        while (true) {
            // Delegation: Eingabe lesen
            String input = ui.askCity();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            try {
                // Delegation: Eingabe prüfen
                String city = validator.validateCity(input);
                // Delegation: Temperatur und Empfehlung holen
                double temperature = service.getTemperature(city);
                String recommendation = service.getRecommendation(temperature);
                // Delegation: Resultat anzeigen
                ui.showResult(city, temperature, recommendation);
            } catch (InvalidInputException e) {
                ui.showError(e.getMessage());
            } catch (IOException | InterruptedException e) {
                ui.showError("Die Wetter-API ist im Moment nicht erreichbar.");
            }
        }
        ui.showGoodbye();
    }
}
