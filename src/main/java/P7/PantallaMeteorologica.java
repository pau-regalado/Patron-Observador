package P7;

// Implementación de un Observador (pantalla meteorológica)
class PantallaMeteorologica implements Observador {
    private String nombre;

    public PantallaMeteorologica(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void actualizar(String condiciones) {
        System.out.println(nombre + ": Condiciones meteorológicas actualizadas - " + condiciones);
    }
}
