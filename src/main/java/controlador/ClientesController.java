package controlador;

import vista.ClientesView;
import modelo.Cliente;
import modelo.persistencia.ClientesDAO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ClientesController {
    private ClientesView vista;
    private List<Cliente> clientes;
    private ClientesDAO clientesDAO;
    
    public ClientesController(ClientesView vista) {
        this.vista = vista;
        this.clientesDAO = new ClientesDAO();
        this.clientes = clientesDAO.cargarClientes();
        cargarClientesEnTabla();
        configurarListeners();
    }

    private void cargarClientesEnTabla() {
        DefaultTableModel model = (DefaultTableModel) vista.getTablaClientes().getModel();
        model.setRowCount(0); // Limpiar tabla
        
        for (Cliente cliente : clientes) {
            model.addRow(new Object[]{
                cliente.getDni(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getEmail(),
                cliente.getTelefono()
            });
        }
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
        String dni = vista.getDni();
        
        // Validar DNI (8 dígitos)
        if (!dni.matches("\\d{8}")) {
            JOptionPane.showMessageDialog(vista, 
                "DNI debe tener 8 dígitos numéricos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verificar si el DNI ya existe
        for (Cliente c : clientes) {
            if (c.getDni().equals(dni)) {
                JOptionPane.showMessageDialog(vista, 
                    "El DNI ya está registrado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        if (vista.getNombre().isEmpty() || vista.getApellido().isEmpty()) {
            JOptionPane.showMessageDialog(vista, 
                "Nombre y Apellido son campos obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Cliente nuevoCliente = new Cliente(
            dni,
            vista.getNombre(),
            vista.getApellido(),
            vista.getEmail(),
            vista.getTelefono()
        );
        
        clientes.add(nuevoCliente);
        clientesDAO.guardarClientes(clientes);
        cargarClientesEnTabla();
        vista.limpiarCampos();
        
        JOptionPane.showMessageDialog(vista, "Cliente agregado correctamente");
    }
    
    private void editarCliente() {
        int filaSeleccionada = vista.getFilaSeleccionada();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, 
                "Seleccione un cliente para editar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Cliente cliente = clientes.get(filaSeleccionada);
        vista.setDni(cliente.getDni());
        vista.setNombre(cliente.getNombre());
        vista.setApellido(cliente.getApellido());
        vista.setEmail(cliente.getEmail());
        vista.setTelefono(cliente.getTelefono());
        
        // Implementar lógica para guardar cambios
    }
    
    private void eliminarCliente() {
        int filaSeleccionada = vista.getFilaSeleccionada();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, 
                "Seleccione un cliente para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(vista, 
            "¿Está seguro de eliminar este cliente?", "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            clientes.remove(filaSeleccionada);
            clientesDAO.guardarClientes(clientes);
            cargarClientesEnTabla();
            JOptionPane.showMessageDialog(vista, "Cliente eliminado correctamente");
        }
    }
}
