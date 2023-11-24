package Observador;

import java.util.ArrayList;
import java.util.List;

// Clase Concreta de Sujeto
class SujetoConcreto implements Sujeto {
    private List<Observador> observadores = new ArrayList<>();

    @Override
    public void agregarObservador(Observador observador) {
        observadores.add(observador);
    }

    @Override
    public void eliminarObservador(Observador observador) {
        observadores.remove(observador);
    }

    @Override
    public void notificarObservadores(String mensaje) {
        for (Observador observador : observadores) {
            observador.actualizar(mensaje);
        }
    }

    // Método que simula un cambio en el estado del sujeto
    public void realizarAccionImportante() {
        // Realizar alguna lógica importante aquí...

        // Notificar a los observadores después de realizar la acción importante
        notificarObservadores("Se ha realizado una acción importante");
    }
}

