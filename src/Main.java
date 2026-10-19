// Startet die Applikation.
public class Main {
    public static void main(String[] args) {
        // Delegation an WeatherApp: Main startet nur die App
        new WeatherApp().start();
    }
}
