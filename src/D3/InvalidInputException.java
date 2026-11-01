package D3;

// bei falscher Eingabe wird geworfen und wird in D3.WeatherApp abgefangen.
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message); // fehlermeldung speichern
    }
}
