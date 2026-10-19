import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherApi {

    private final HttpClient client = HttpClient.newHttpClient();

    public double[] findCoordinates(String city) throws InvalidInputException, IOException, InterruptedException {
        String json = get("https://geocoding-api.open-meteo.com/v1/search?count=1&language=en&name="
                + URLEncoder.encode(city, StandardCharsets.UTF_8));
        if (!json.contains("\"results\"")) {
            throw new InvalidInputException("Die Stadt '" + city + "' wurde nicht gefunden.");
        }
        return new double[]{readNumber(json, "latitude"), readNumber(json, "longitude")};
    }

    public double getTemperature(double latitude, double longitude) throws IOException, InterruptedException {
        String json = get("https://api.open-meteo.com/v1/forecast?latitude=" + latitude
                + "&longitude=" + longitude + "&current=temperature_2m");
        String current = json.substring(json.indexOf("\"current\":"));
        return readNumber(current, "temperature_2m");
    }

    private String get(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("Status " + response.statusCode());
        }
        return response.body();
    }

    private double readNumber(String json, String key) {
        Matcher matcher = Pattern.compile("\"" + key + "\":(-?[0-9.]+)").matcher(json);
        matcher.find();
        return Double.parseDouble(matcher.group(1));
    }
}
