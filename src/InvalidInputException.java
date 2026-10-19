// Eigene Exception, die bei falscher Eingabe geworfen wird und in WeatherApp abgefangen wird.
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}
