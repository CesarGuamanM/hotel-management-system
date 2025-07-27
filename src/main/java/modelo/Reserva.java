package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reserva implements Serializable, Pago {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private Cliente cliente;
    private Habitacion habitacion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private boolean pagado;
    private double total;
    
    public Reserva(int id, Cliente cliente, Habitacion habitacion, 
                  LocalDate fechaInicio, LocalDate fechaFin) {
        this.id = id;
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.pagado = false;
        this.total = calcularTotal();
    }
    
    // Getters
    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Habitacion getHabitacion() { return habitacion; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public boolean isPagado() { return pagado; }
    public double getTotal() { return total; }
    
    // Setters
    public void setPagado(boolean pagado) { this.pagado = pagado; }
    
    // Implementación de la interfaz Pago
    @Override
    public double calcularTotal() {
        long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        return dias * habitacion.getTipo().getPrecioBase();
    }
    
    @Override
    public String toString() {
        return "Reserva #" + id + " - " + cliente.getNombre() + " " + cliente.getApellido() + 
               " (" + habitacion.getNumero() + ")";
    }
}
