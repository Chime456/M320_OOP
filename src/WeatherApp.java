import java.io.IOException;

public class WeatherApp {

    private final ConsoleUI ui = new ConsoleUI();
    private final InputValidator validator = new InputValidator();
    private final WeatherService service = new WeatherService(new WeatherApi());

    public void start() {
        ui.showWelcome();
        while (true) {
            String input = ui.askCity();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            try {
                String city = validator.validateCity(input);
                double temperature = service.getTemperature(city);
                String recommendation = service.getRecommendation(temperature);
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
