package P7;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WeatherStackAPI {
    String apiKey = "6d17415ee1b47b2434bde4826cf59f16";

    String ciudadCodificada;
    String apiUrl;

    String zone;

    public WeatherStackAPI (String zone) {
        this.setZone(zone);
        apiUrl = "http://api.weatherstack.com/current?access_key=" + apiKey + "&query=";
        this.generateCodedZone();
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    private void generateCodedZone() {
        try {
            ciudadCodificada = URLEncoder.encode(this.zone, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String getWeatherConditions() {
        try {
            HttpResponse<JsonNode> response = Unirest.get(this.apiUrl + this.ciudadCodificada)
                    .header("Accept", "application/json")
                    .asJson();

            // Imprimir la respuesta completa (para entender la estructura)
            System.out.println("Respuesta completa de Weatherstack: " + response.getBody());

            // Extraer información relevante de la respuesta
            return getWeatherConditionFromResponse(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return "Not provided";
        }

    }

    public String getWeatherConditionsFrom(String zone) {
        try {
            HttpResponse<JsonNode> response = Unirest.get(apiUrl)
                    .header("Accept", "application/json")
                    .asJson();

            // Imprimir la respuesta completa (para entender la estructura)
            System.out.println("Respuesta completa de Weatherstack: " + response.getBody());

            // Extraer información relevante de la respuesta
            return getWeatherConditionFromResponse(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return "Not provided";
        }
    }

    private static String getWeatherConditionFromResponse(JsonNode jsonResponse) {
        try {
            // Extraer la información relevante de la respuesta JSON de Weatherstack
            // Ajusta esto según la estructura real de la respuesta
            return jsonResponse.getObject()
                    .getJSONObject("current")
                    .getJSONArray("weather_descriptions")
                    .getString(0);  // Tomar el primer elemento de la lista (pueden haber múltiples descripciones)
        } catch (Exception e) {
            e.printStackTrace();
            return "No disponible";
        }
    }
}
