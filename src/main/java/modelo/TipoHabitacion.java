package modelo;

public enum TipoHabitacion {
    INDIVIDUAL("Individual", 50.0),
    DOBLE("Doble", 80.0),
    SUITE("Suite", 120.0);

    private final String nombre;
    private final double precio;

    private TipoHabitacion(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
}