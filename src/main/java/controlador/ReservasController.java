package controlador;

import vista.ReservasView;
import modelo.Reserva;
import modelo.Cliente;
import modelo.Habitacion;
import modelo.persistencia.ReservasDAO;
import modelo.persistencia.ClientesDAO;
import modelo.persistencia.HabitacionesDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ReservasController {
    private ReservasView vista;
    private List<Reserva> reservas;
    private ReservasDAO reservasDAO;
    private ClientesDAO clientesDAO;
    private HabitacionesDAO habitacionesDAO;
    
    public ReservasController(ReservasView vista) {
        this.vista = vista;
        this.reservasDAO = new ReservasDAO();
        this.clientesDAO = new ClientesDAO();
        this.habitacionesDAO = new HabitacionesDAO();
        this.reservas = reservasDAO.cargarReservas();
        cargarReservasEnTabla();
        configurarListeners();
    }
    
    private void cargarReservasEnTabla() {
        DefaultTableModel model = (DefaultTableModel) vista.getTablaReservas().getModel();
        model.setRowCount(0); // Limpiar tabla
        
        for (Reserva reserva : reservas) {
            model.addRow(new Object[]{
                reserva.getId(),
                reserva.getCliente().getNombre() + " " + reserva.getCliente().getApellido(),
                reserva.getHabitacion().getNumero(),
                reserva.getFechaInicio(),
                reserva.getFechaFin(),
                reserva.getTotal(),
                reserva.isPagado() ? "Confirmada" : "Pendiente"
            });
        }
    }
    
    private void configurarListeners() {
        vista.setNuevaReservaListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDialogoNuevaReserva();
            }
        });
        
        vista.setCheckInListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarCheckIn();
            }
        });
        
        vista.setCheckOutListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarCheckOut();
            }
        });
        
        vista.setCancelarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelarReserva();
            }
        });
    }
    
    private void mostrarDialogoNuevaReserva() {
        // Implementar diálogo para nueva reserva
        JOptionPane.showMessageDialog(vista, "Funcionalidad de nueva reserva en desarrollo");
    }
    
    private void realizarCheckIn() {
        int filaSeleccionada = vista.getFilaSeleccionada();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, 
                "Seleccione una reserva para hacer Check-In", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Reserva reserva = reservas.get(filaSeleccionada);
        if (reserva.isPagado()) {
            JOptionPane.showMessageDialog(vista, 
                "El Check-In ya fue realizado para esta reserva", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        reserva.setPagado(true);
        reserva.getHabitacion().setDisponible(false);
        reservasDAO.guardarReservas(reservas);
        habitacionesDAO.guardarHabitaciones(habitacionesDAO.cargarHabitaciones()); // Actualizar estado de habitación
        
        cargarReservasEnTabla();
        JOptionPane.showMessageDialog(vista, "Check-In realizado exitosamente");
    }
    
    private void realizarCheckOut() {
        int filaSeleccionada = vista.getFilaSeleccionada();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, 
                "Seleccione una reserva para hacer Check-Out", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Reserva reserva = reservas.get(filaSeleccionada);
        if (!reserva.isPagado()) {
            JOptionPane.showMessageDialog(vista, 
                "No se puede hacer Check-Out de una reserva no confirmada", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        reserva.getHabitacion().setDisponible(true);
        habitacionesDAO.guardarHabitaciones(habitacionesDAO.cargarHabitaciones()); // Actualizar estado de habitación
        
        cargarReservasEnTabla();
        JOptionPane.showMessageDialog(vista, "Check-Out realizado exitosamente");
    }
    
    private void cancelarReserva() {
        int filaSeleccionada = vista.getFilaSeleccionada();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, 
                "Seleccione una reserva para cancelar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(vista, 
            "¿Está seguro de cancelar esta reserva?", "Confirmar cancelación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            Reserva reserva = reservas.get(filaSeleccionada);
            reservas.remove(reserva);
            
            // Liberar la habitación si estaba reservada
            if (reserva.isPagado()) {
                reserva.getHabitacion().setDisponible(true);
                habitacionesDAO.guardarHabitaciones(habitacionesDAO.cargarHabitaciones());
            }
            
            reservasDAO.guardarReservas(reservas);
            cargarReservasEnTabla();
            JOptionPane.showMessageDialog(vista, "Reserva cancelada exitosamente");
        }
    }
}
