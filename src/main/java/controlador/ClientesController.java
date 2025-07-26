package controlador;

import vista.ClientesView;
import modelo.Cliente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ClientesController {
    private ClientesView vista;
    
    public ClientesController(ClientesView vista) {
        this.vista = vista;
        configurarListeners();
    }
    
    private void configurarListeners() {
        vista.setAgregarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarCliente();
            }
        });
        
        vista.setEditarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarCliente();
            }
        });
        
        vista.setEliminarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCliente();
            }
        });
    }
    
    private void agregarCliente() {
        // Validar campos obligatorios
        if (vista.getDni().isEmpty() || vista.getNombre().isEmpty() || vista.getApellido().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "DNI, Nombre y Apellido son campos obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Crear el cliente y añadirlo a la tabla
        String[] datosCliente = {
            vista.getDni(),
            vista.getNombre(),
            vista.getApellido(),
            vista.getEmail(),
            vista.getTelefono()
        };
        
        vista.agregarClienteALista(datosCliente);
        vista.limpiarCampos();
        
        JOptionPane.showMessageDialog(vista, "Cliente agregado correctamente");
    }
    
    private void editarCliente() {
        int filaSeleccionada = vista.getFilaSeleccionada();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un cliente para editar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Aquí implementarías la lógica para editar
        JOptionPane.showMessageDialog(vista, "Funcionalidad de edición en desarrollo");
    }
    
    private void eliminarCliente() {
        int filaSeleccionada = vista.getFilaSeleccionada();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un cliente para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(vista, 
            "¿Está seguro de eliminar este cliente?", "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            DefaultTableModel model = (DefaultTableModel) vista.getTablaClientes().getModel();
            model.removeRow(filaSeleccionada);
            JOptionPane.showMessageDialog(vista, "Cliente eliminado correctamente");
        }
    }
}
