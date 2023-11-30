package P7;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WeatherStackAPI {
    String apiKey = "5cfbdb768ce502c32fb609af82754840";
    String apiUrl = "http://api.weatherstack.com/current?access_key=" + apiKey + "&query=";

    public String getWeatherConditionsFrom(String zone) {
        try {
            String ciudadCodificada = URLEncoder.encode(zone, StandardCharsets.UTF_8.toString());
            HttpResponse<JsonNode> response = Unirest.get(apiUrl + ciudadCodificada)
                    .header("Accept", "application/json")
                    .asJson();
           // System.out.println(response.getBody().toString()); //comprobacion info por pantalla
            return getWeatherConditionFromResponse(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return "Not provided";
        }
    }

    private static String getWeatherConditionFromResponse(JsonNode jsonResponse) {
        return jsonResponse.getObject().toString();
    }
}
