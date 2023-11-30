package P7;

import org.json.JSONObject;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class PantallaMeteorologica implements Observador {
    private JFrame frame;
    private JLabel labelCiudad;
    private JLabel labelHora;
    private JLabel labelTemperatura;
    private JLabel labelHumedad;
    private JLabel labelIcono;
    private JLabel labelAlerta;

    public PantallaMeteorologica(String ciudad) {
        frame = new JFrame(ciudad);
        frame.setLayout(null); // Usamos layout nulo para posicionar manualmente los componentes
        frame.setSize(470, 362);

        // Establecer imagen de fondo
        ImageIcon imagenFondo = new ImageIcon("IMG/FONDO.jpg");
        JLabel fondo = new JLabel(imagenFondo);
        fondo.setBounds(0, 0, 470, 362);
        frame.setContentPane(fondo);

        labelCiudad = new JLabel(ciudad, JLabel.CENTER);
        labelCiudad.setForeground(Color.BLACK);
        labelCiudad.setFont(new Font("Arial", Font.BOLD, 38));
        labelCiudad.setBounds(10, 10, 450, 35);
        frame.add(labelCiudad);

        int horaWidth = 100;
        int horaHeight = 56;
        int iconoSize = 60;
        int totalWidth = horaWidth + iconoSize;

        labelHora = new JLabel();
        labelHora.setForeground(Color.BLACK);
        labelHora.setFont(new Font("Arial", Font.BOLD, 35));
        labelHora.setOpaque(true);
        labelHora.setBackground(Color.WHITE);
        labelHora.setBounds((frame.getWidth() - totalWidth) / 2, labelCiudad.getY() + labelCiudad.getHeight() + 10, horaWidth, horaHeight);
        frame.add(labelHora);

        labelIcono = new JLabel();
        int iconoX = labelHora.getX() + horaWidth + 20; // 20 píxeles de espacio entre hora e icono
        labelIcono.setBounds(iconoX, labelHora.getY(), iconoSize, iconoSize);
        frame.add(labelIcono);


        labelTemperatura = new JLabel();
        labelTemperatura.setForeground(Color.BLACK);
        labelTemperatura.setFont(new Font("Arial", Font.BOLD, 18));
        labelTemperatura.setBounds(10, 160, 140, 35);
        frame.add(labelTemperatura);

        labelHumedad = new JLabel();
        labelHumedad.setForeground(Color.BLACK);
        labelHumedad.setFont(new Font("Arial", Font.BOLD, 18));
        labelHumedad.setBounds(300, 160, 140, 35);
        frame.add(labelHumedad);

        labelAlerta = new JLabel();
        labelAlerta.setForeground(Color.RED);
        labelAlerta.setFont(new Font("Arial", Font.BOLD, 18));
        labelAlerta.setBounds(10, 200, 450, 35);
        frame.add(labelAlerta);

        frame.setSize(470, 362);
        frame.setVisible(true);
    }

    @Override
    public void actualizar(String condicionesJSON) {
        try {
            JSONObject jsonCompleto = new JSONObject(condicionesJSON);

            // Accediendo a los objetos 'current' y 'location'
            JSONObject current = jsonCompleto.getJSONObject("current");
            JSONObject location = jsonCompleto.getJSONObject("location");

            // Extrayendo datos de 'current'
            String temperatura = "Temp: " + current.optInt("temperature", 0) + "°C";
            String humedad = "Humedad: " + current.optInt("humidity", 0) + "%";
            String iconoURL = current.getJSONArray("weather_icons").optString(0, "");

            // Extrayendo la hora local de 'location'
            String horaLocal = location.optString("localtime", "No disponible");
            String horaObservacion = extraerHora(horaLocal);

            SwingUtilities.invokeLater(() -> {
                labelTemperatura.setText(temperatura);
                labelHumedad.setText(humedad);
                labelHora.setText(horaObservacion);

                if (!iconoURL.isEmpty()) {
                    cargarIcono(iconoURL);
                } else {
                    labelIcono.setText("Icono no disponible");
                }
            });
            mostrarAlertaSiNecesario(current);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al procesar la respuesta JSON: " + condicionesJSON);
        }
    }

    private String extraerHora(String horaCompleta) {
        // Asume un formato 'YYYY-MM-DD HH:MM' y extrae solo la parte de la hora
        int spaceIndex = horaCompleta.indexOf(' ');
        return spaceIndex != -1 ? horaCompleta.substring(spaceIndex + 1) : "Formato inválido";
    }




    private void cargarIcono(String iconoURL) {
        try {
            URL url = new URL(iconoURL);
            ImageIcon icono = new ImageIcon(url);
            Image img = icono.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            labelIcono.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            e.printStackTrace();
            labelIcono.setText("Error al cargar icono");
        }
    }

    private void mostrarAlertaSiNecesario(JSONObject current) {
        int temperaturaActual = current.optInt("temperature", 0);

        if (temperaturaActual > 25) {
            labelAlerta.setText("¡Alerta de Calor! Considere tomar precauciones.");
        }else if (temperaturaActual < 5) {
            labelAlerta.setText("¡Alerta de Frio! Considere tomar precauciones.");
        } else {
            labelAlerta.setText("");
        }
    }
}



