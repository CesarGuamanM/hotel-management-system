package modelo;

public enum TipoHabitacion {
    INDIVIDUAL("Individual", 50.0),
    DOBLE("Doble", 80.0),
    SUITE("Suite", 120.0);

    private final String descripcion;
    private final double precioBase;

    TipoHabitacion(String descripcion, double precioBase) {
        this.descripcion = descripcion;
        this.precioBase = precioBase;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecioBase() {
        return precioBase;
    }
}