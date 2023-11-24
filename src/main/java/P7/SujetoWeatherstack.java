package P7;

import java.util.ArrayList;
import java.util.List;

// Sujeto (clase que proporciona actualizaciones a los observadores)
class SujetoWeatherstack {
    private List<Observador> observadores = new ArrayList<>();
    private String condicionesMeteorologicas;

    public void agregarObservador(Observador observador) {
        observadores.add(observador);
    }

    public void eliminarObservador(Observador observador) {
        observadores.remove(observador);
    }

    public void notificarObservadores() {
        for (Observador observador : observadores) {
            observador.actualizar(condicionesMeteorologicas);
        }
    }

    public void setCondicionesMeteorologicas(String condicionesMeteorologicas) {
        this.condicionesMeteorologicas = condicionesMeteorologicas;
        notificarObservadores();
    }
}
