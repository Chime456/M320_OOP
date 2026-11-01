package D3;

import java.io.IOException;

// Holt die Temperatur und berechnet die Kleidungsempfehlung.
public class WeatherService {

    //API aufrufen
    private final WeatherApi api;

    public WeatherService(WeatherApi api) {
        this.api = api;
    }

    public double getTemperature(String city) throws InvalidInputException, IOException, InterruptedException {
        // Delegation an D3.WeatherApi: Daten aus dem Internet holen
        double[] coordinates = api.findCoordinates(city);
        return api.getTemperature(coordinates[0], coordinates[1]);
    }

    public String getRecommendation(double temperature) {
        if (temperature < 0) {
            return "Winterjacke, Mütze und Handschuhe";
        } else if (temperature < 10) {
            return "warme Jacke";
        } else if (temperature < 20) {
            return "Pullover";
        } else {
            return "T-Shirt";
        }
    }
}
