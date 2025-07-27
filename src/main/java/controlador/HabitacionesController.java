package controlador;


import vista.HabitacionesView;
import modelo.Habitacion;
//import modelo.TipoHabitacion;
import modelo.persistencia.HabitacionesDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class HabitacionesController {
    private HabitacionesView vista;
    private List<Habitacion> habitaciones;
    private HabitacionesDAO habitacionesDAO;
    
    public HabitacionesController(HabitacionesView vista) {
        this.vista = vista;
        try {
            this.habitacionesDAO = new HabitacionesDAO();
            this.habitaciones = habitacionesDAO.cargarHabitaciones();
            cargarHabitacionesEnTabla();
            configurarListeners();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, 
                "Error al inicializar el controlador: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarHabitacionesEnTabla() {
        DefaultTableModel model = (DefaultTableModel) vista.getTablaHabitaciones().getModel();
        model.setRowCount(0); // Limpiar tabla
        
        for (Habitacion habitacion : habitaciones) {
            model.addRow(new Object[]{
                habitacion.getNumero(),
                habitacion.getTipo().getDescripcion(),
                habitacion.isDisponible() ? "Disponible" : "Ocupada",
                habitacion.getCaracteristicas()
            });
        }
    }
    
    private void configurarListeners() {
        vista.setAgregarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarHabitacion();
            }
        });
        
        vista.setEditarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarHabitacion();
            }
        });
        
        vista.setCambiarEstadoListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarEstadoHabitacion();
            }
        });
    }
    
    private void agregarHabitacion() {
        try {
            int numero = Integer.parseInt(vista.getNumero());
            
            // Verificar si el número ya existe
            for (Habitacion h : habitaciones) {
                if (h.getNumero() == numero) {
                    JOptionPane.showMessageDialog(vista, 
                        "El número de habitación ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            Habitacion nuevaHabitacion = new Habitacion(
                numero,
                vista.getTipo(),
                vista.getCaracteristicas()
            );
            
            habitaciones.add(nuevaHabitacion);
            habitacionesDAO.guardarHabitaciones(habitaciones);
            cargarHabitacionesEnTabla();
            vista.limpiarCampos();
            
            JOptionPane.showMessageDialog(vista, "Habitación agregada correctamente");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, 
                "El número de habitación debe ser un valor numérico", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void editarHabitacion() {
        int filaSeleccionada = vista.getFilaSeleccionada();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, 
                "Seleccione una habitación para editar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            Habitacion habitacion = habitaciones.get(filaSeleccionada);
            int nuevoNumero = Integer.parseInt(vista.getNumero());
            
            // Verificar si el nuevo número ya existe (excepto para esta habitación)
            for (Habitacion h : habitaciones) {
                if (h != habitacion && h.getNumero() == nuevoNumero) {
                    JOptionPane.showMessageDialog(vista, 
                        "El número de habitación ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            // Actualizar los datos - SOLUCIÓN AL ERROR
            habitacion.setNumero(nuevoNumero);  // Necesitarás agregar setNumero() en Habitacion
            habitacion.setCaracteristicas(vista.getCaracteristicas());
            habitacion.setTipo(vista.getTipo());  // Ahora debería funcionar
            
            habitacionesDAO.guardarHabitaciones(habitaciones);
            cargarHabitacionesEnTabla();
            vista.limpiarCampos();
            
            JOptionPane.showMessageDialog(vista, "Habitación actualizada correctamente");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, 
                "El número de habitación debe ser un valor numérico", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cambiarEstadoHabitacion() {
        int filaSeleccionada = vista.getFilaSeleccionada();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, 
                "Seleccione una habitación para cambiar su estado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Habitacion habitacion = habitaciones.get(filaSeleccionada);
        habitacion.setDisponible(!habitacion.isDisponible());
        habitacionesDAO.guardarHabitaciones(habitaciones);
        cargarHabitacionesEnTabla();
        
        JOptionPane.showMessageDialog(vista, 
            "Estado de la habitación actualizado a: " + 
            (habitacion.isDisponible() ? "Disponible" : "Ocupada"));
    }
}
