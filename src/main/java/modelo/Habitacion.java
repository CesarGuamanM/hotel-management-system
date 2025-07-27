package modelo;

public class Habitacion {
    private int numero;
    private TipoHabitacion tipo;
    private boolean disponible;
    private String caracteristicas;

    public Habitacion(int numero, TipoHabitacion tipo, String caracteristicas) {
        this.numero = numero;
        this.tipo = tipo;
        this.caracteristicas = caracteristicas;
        this.disponible = true;
    }

    // Getters y setters
    public int getNumero() { return numero; }
    public TipoHabitacion getTipo() { return tipo; }
    public boolean isDisponible() { return disponible; }
    public String getCaracteristicas() { return caracteristicas; }

    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    public void setCaracteristicas(String caracteristicas) { this.caracteristicas = caracteristicas; }

    @Override
    public String toString() {
        return "Habitación #" + numero + " - " + tipo.getDescripcion() + 
               " (" + (disponible ? "Disponible" : "Ocupada") + ")";
    }
}