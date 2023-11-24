package Observador;

// Ejemplo de uso
public class EjemploObservador {
    public static void main(String[] args) {
        // Crear observadores
        ObservadorConcreto observador1 = new ObservadorConcreto("Observador 1");
        ObservadorConcreto observador2 = new ObservadorConcreto("Observador 2");

        // Crear sujeto
        SujetoConcreto sujeto = new SujetoConcreto();

        // Agregar observadores al sujeto
        sujeto.agregarObservador(observador1);
        sujeto.agregarObservador(observador2);

        // Realizar una acción importante que notificará a los observadores
        sujeto.realizarAccionImportante();

        // Eliminar un observador
        sujeto.eliminarObservador(observador1);

        // Realizar otra acción importante después de eliminar un observador
        sujeto.realizarAccionImportante();
    }
}
