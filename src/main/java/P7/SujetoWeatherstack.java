package P7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SujetoWeatherstack {
    private Map<String, List<Observador>> observadoresPorCiudad = new HashMap<>();

    public void agregarObservador(String ciudad, Observador observador) {
        observadoresPorCiudad.putIfAbsent(ciudad, new ArrayList<>());
        observadoresPorCiudad.get(ciudad).add(observador);
    }

    public void notificarObservadores(String ciudad, String condicionesMeteorologicas) {
        if (observadoresPorCiudad.containsKey(ciudad)) {
            for (Observador observador : observadoresPorCiudad.get(ciudad)) {
                observador.actualizar(condicionesMeteorologicas);
            }
        }
    }
}
