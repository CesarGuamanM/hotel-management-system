package modelo;

public enum TipoHabitacion {
    INDIVIDUAL("Individual", 50.0, 1),
    DOBLE("Doble", 80.0, 2),
    SUITE("Suite", 120.0, 4);

    private final String descripcion;
    private final double precioBase;
    private final int capacidad;

    TipoHabitacion(String descripcion, double precioBase, int capacidad) {
        this.descripcion = descripcion;
        this.precioBase = precioBase;
        this.capacidad = capacidad;
    }

    // Getters
    public String getDescripcion() { return descripcion; }
    public double getPrecioBase() { return precioBase; }
    public int getCapacidad() { return capacidad; }
}