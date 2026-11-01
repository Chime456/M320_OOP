package D3;

// Prüft, ob die eingegebene Stadt gültig ist.
public class InputValidator {

    public String validateCity(String input) throws InvalidInputException {
        if (input == null || input.isBlank()) {
            throw new InvalidInputException("Die Stadt darf nicht leer sein.");
        }
        String city = input.trim();
        if (city.length() < 2) {
            throw new InvalidInputException("Die Stadt ist zu kurz.");
        }
        if (!city.matches("[\\p{L} -]+")) {
            throw new InvalidInputException("Die Stadt darf nur Buchstaben enthalten.");
        }
        return city;
    }
}
