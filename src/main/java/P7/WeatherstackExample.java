package P7;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WeatherstackExample {
    public static void main(String[] args) throws IOException {
        // Crear una instancia del sujeto (Weatherstack) y observadores (pantallas meteorológicas)
        SujetoWeatherstack sujetoWeatherstack = new SujetoWeatherstack();
        PantallaMeteorologica pantallaPrincipal = new PantallaMeteorologica("Pantalla Principal");
        PantallaMeteorologica pantallaSecundaria = new PantallaMeteorologica("Pantalla Secundaria");

        // Agregar observadores al sujeto (Weatherstack)
        sujetoWeatherstack.agregarObservador(pantallaPrincipal);
        sujetoWeatherstack.agregarObservador(pantallaSecundaria);

        try {
            // Reemplaza "TU_API_KEY" con tu clave de API de Weatherstack
            String apiKey = "6d17415ee1b47b2434bde4826cf59f16";

            // URL de la API de Weatherstack (puedes ajustar los parámetros según tus necesidades)
            String ciudadCodificada = URLEncoder.encode("Tenerife", StandardCharsets.UTF_8.toString());
            String apiUrl = "http://api.weatherstack.com/current?access_key=" + apiKey + "&query=" + ciudadCodificada;

            HttpResponse<JsonNode> response = Unirest.get(apiUrl)
                    .header("Accept", "application/json")
                    .asJson();

            // Imprimir la respuesta completa (para entender la estructura)
            System.out.println("Respuesta completa de Weatherstack: " + response.getBody());

            // Extraer información relevante de la respuesta
            String condicionesMeteorologicas = obtenerCondicionesDesdeRespuesta(response.getBody());

            // Actualizar las condiciones meteorológicas del sujeto
            sujetoWeatherstack.setCondicionesMeteorologicas(condicionesMeteorologicas);
        } catch (UnirestException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            // Cerrar Unirest (importante para liberar recursos)
            Unirest.shutdown();
        }
    }

    private static String obtenerCondicionesDesdeRespuesta(JsonNode jsonResponse) {
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

