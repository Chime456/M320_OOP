import java.util.Scanner;

// Liest die Eingaben des Benutzers und zeigt die Ausgaben an.
public class ConsoleUI {

    private final Scanner scanner = new Scanner(System.in);

    public void showWelcome() {
        System.out.println("=== Wetter-Berater ===");
        System.out.println("Gib eine Stadt ein oder 'exit' zum Beenden.");
    }

    public String askCity() {
        System.out.print("\nStadt: ");
        return scanner.hasNextLine() ? scanner.nextLine() : "exit";
    }

    public void showResult(String city, double temperature, String recommendation) {
        System.out.println("In " + city + " ist es " + temperature + " °C.");
        System.out.println("Empfehlung: " + recommendation);
    }

    public void showError(String message) {
        System.out.println("Fehler: " + message);
    }

    public void showGoodbye() {
        System.out.println("Auf Wiedersehen!");
    }
}
