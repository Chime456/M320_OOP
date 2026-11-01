package D3;

// Startet die Applikation.
public class Main {
    public static void main(String[] args) {
        // Delegation an D3.WeatherApp: D3.Main startet nur die App
        new WeatherApp().start();
    }
}
