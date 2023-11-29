package P7;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WeatherstackExample {

    public static void main(String[] args) throws IOException {
        String zone = "tenerife";
        JFrame ventanaPrincipal = new JFrame(zone);
        JLabel etiquetaCondiciones = new JLabel(zone);

        ventanaPrincipal.add(etiquetaCondiciones, BorderLayout.CENTER);

        ventanaPrincipal.setSize(400, 300);
        ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaPrincipal.setLocationRelativeTo(null);
        ventanaPrincipal.setVisible(true);

        WeatherStackAPI weatherStackAPI = new WeatherStackAPI(zone);

        // Crear una instancia del sujeto (Weatherstack) y observadores (pantallas meteorológicas)
        SujetoWeatherstack sujetoWeatherstack = new SujetoWeatherstack();
        PantallaMeteorologica pantallaPrincipal = new PantallaMeteorologica("Pantalla Principal");

        // Agregar observadores al sujeto (Weatherstack)
        sujetoWeatherstack.agregarObservador(pantallaPrincipal);

        // Actualizar las condiciones meteorológicas del sujeto
        sujetoWeatherstack.setCondicionesMeteorologicas(weatherStackAPI.getWeatherConditions());
    }
}

