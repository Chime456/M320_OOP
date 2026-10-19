// Prüft, ob die eingegebene Stadt gültig ist.
public class InputValidator {

    public String validateCity(String input) throws InvalidInputException {
        if (input == null || input.isBlank()) {
            throw new InvalidInputException("Die Stadt darf nicht leer sein.");
        }
        String city = input.trim();
        if (!city.matches("[\\p{L} -]{2,50}")) {
            throw new InvalidInputException("Die Stadt darf nur Buchstaben enthalten.");
        }
        return city;
    }
}
