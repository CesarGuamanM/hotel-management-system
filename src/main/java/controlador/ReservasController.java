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
//import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
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
        
        cargarDatosIniciales();
        cargarReservasEnTabla();
        configurarListeners();
    }
    
    private void cargarDatosIniciales() {
        // Cargar clientes y habitaciones disponibles
        List<Cliente> clientes = clientesDAO.cargarClientes();
        List<Habitacion> habitaciones = habitacionesDAO.cargarHabitaciones()
            .stream()
            .filter(Habitacion::isDisponible)
            .collect(Collectors.toList());

        vista.cargarClientes(clientes);
        vista.cargarHabitaciones(habitaciones);
        cargarReservasEnTabla();
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

        vista.getBtnConfirmarReserva().addActionListener(e -> crearNuevaReserva());

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
        try {
            // Obtener habitaciones disponibles
            List<Habitacion> habitacionesDisponibles = habitacionesDAO.cargarHabitaciones()
                .stream()
                .filter(Habitacion::isDisponible)
                .collect(Collectors.toList());
            
            // Actualizar el comboBox de habitaciones
            DefaultComboBoxModel<Habitacion> modeloHabitaciones = new DefaultComboBoxModel<>();
            for (Habitacion hab : habitacionesDisponibles) {
                modeloHabitaciones.addElement(hab);
            }
            vista.getCbHabitaciones().setModel(modeloHabitaciones);
            
            vista.mostrarDialogoNuevaReserva();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, 
                "Error al cargar habitaciones: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void crearNuevaReserva() {
        try {
            Cliente cliente = vista.getClienteSeleccionado();
            Habitacion habitacion = vista.getHabitacionSeleccionada();
            LocalDate fechaInicio = vista.getNuevaFechaInicio();
            LocalDate fechaFin = vista.getNuevaFechaFin();

            // Validaciones
            if (cliente == null || habitacion == null) {
                JOptionPane.showMessageDialog(vista, "Seleccione cliente y habitación", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (fechaFin.isBefore(fechaInicio)) {
                JOptionPane.showMessageDialog(vista, "La fecha fin debe ser posterior a la fecha inicio", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar disponibilidad
            boolean disponible = reservas.stream()
                .filter(r -> r.getHabitacion().equals(habitacion))
                .noneMatch(r -> !r.getFechaFin().isBefore(fechaInicio) && !r.getFechaInicio().isAfter(fechaFin));

            if (!disponible) {
                JOptionPane.showMessageDialog(vista, "La habitación no está disponible en las fechas seleccionadas", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear nueva reserva
            Reserva nuevaReserva = new Reserva(
                reservasDAO.getNuevoId(),
                cliente,
                habitacion,
                fechaInicio,
                fechaFin
            );

            // Actualizar estado de la habitación
            habitacion.setDisponible(false);
            habitacionesDAO.guardarHabitaciones(habitacionesDAO.cargarHabitaciones());

            // Guardar reserva
            reservas.add(nuevaReserva);
            reservasDAO.guardarReservas(reservas);
            
            // Actualizar vista
            cargarReservasEnTabla();
            vista.ocultarDialogoNuevaReserva();
            
            JOptionPane.showMessageDialog(vista, "Reserva creada exitosamente!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al crear reserva: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
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
