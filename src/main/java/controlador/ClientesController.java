package controlador;

import vista.ClientesView;
import modelo.Cliente;
import modelo.persistencia.ClientesDAO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ClientesController {
    private ClientesView vista;
    private List<Cliente> clientes;
    private ClientesDAO clientesDAO;

    // Variables de estado para el modo edición
    private boolean modoEdicion = false;
    private int indiceClienteEditando = -1; // Almacena el índice del cliente que se está editando

    public ClientesController(ClientesView vista) {
        this.vista = vista;
        this.clientesDAO = new ClientesDAO();
        this.clientes = clientesDAO.cargarClientes();
        cargarClientesEnTabla();
        configurarListeners();
        configurarTablaSeleccionListener();
        resetearFormulario(); // Asegurarse de que el formulario esté en estado inicial al arrancar
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
                // Si estamos en modo edición y se pulsa "Agregar", cancelar edición antes de agregar
                if (modoEdicion) {
                    resetearFormulario();
                    modoEdicion = false;
                    indiceClienteEditando = -1;
                    // Opcional: Mostrar un mensaje si se cancela la edición
                    // JOptionPane.showMessageDialog(vista, "Modo edición cancelado.");
                }
                agregarCliente();
            }
        });

        vista.setEditarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica principal del botón "Editar":
                // Si estamos en modo edición (es decir, ya hay un cliente cargado para modificar)
                // y el usuario presiona el botón, significa que quiere GUARDAR LOS CAMBIOS.
                if (modoEdicion) {
                    guardarCambiosCliente();
                } else {
                    // Si NO estamos en modo edición, al presionar "Editar",
                    // significa que el usuario quiere SELECCIONAR un cliente para entrar en modo edición.
                    // La lógica para cargar los datos ya la maneja el ListSelectionListener,
                    // pero esta parte es para validar si hay algo seleccionado.
                    int filaSeleccionada = vista.getFilaSeleccionada();
                    if (filaSeleccionada != -1) {
                        // Aunque el ListSelectionListener ya cargó los datos,
                        // esta llamada asegura que el estado `modoEdicion` y `indiceClienteEditando` se establezcan.
                        // También se encarga de cambiar el texto del botón y deshabilitar el DNI.
                        entrarModoEdicion(filaSeleccionada);
                    } else {
                        JOptionPane.showMessageDialog(vista,
                            "Seleccione un cliente para editar", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        vista.setEliminarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Si estamos en modo edición y se pulsa "Eliminar", cancelar edición antes de eliminar
                if (modoEdicion) {
                    resetearFormulario();
                    modoEdicion = false;
                    indiceClienteEditando = -1;
                }
                eliminarCliente();
            }
        });
    }

    private void configurarTablaSeleccionListener() {
        vista.getTablaClientes().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // Asegura que el evento solo se dispare una vez
                    int filaSeleccionada = vista.getFilaSeleccionada();
                    if (filaSeleccionada != -1) {
                        // Carga los datos del cliente seleccionado en los campos de texto
                        Cliente clienteSeleccionado = clientes.get(filaSeleccionada);
                        vista.setDni(clienteSeleccionado.getDni());
                        vista.setNombre(clienteSeleccionado.getNombre());
                        vista.setApellido(clienteSeleccionado.getApellido());
                        vista.setEmail(clienteSeleccionado.getEmail());
                        vista.setTelefono(clienteSeleccionado.getTelefono());

                        // Deshabilita el DNI ya que es la clave primaria y no debería editarse
                        vista.getTxtDni().setEnabled(false);

                        // Establecer modo edición al seleccionar una fila
                        modoEdicion = true;
                        indiceClienteEditando = filaSeleccionada;
                        vista.getBtnAgregar().setEnabled(false); // Deshabilitar agregar mientras se edita
                        vista.getBtnEditar().setText("Guardar Cambios"); // Cambiar texto del botón
                    } else {
                        // Si no hay fila seleccionada, resetear el formulario y salir de modo edición
                        resetearFormulario();
                    }
                }
            }
        });
    }

    // Método para limpiar campos y restablecer el estado del formulario y botones
    private void resetearFormulario() {
        vista.limpiarCampos();
        vista.getTxtDni().setEnabled(true); // Habilitar DNI para nuevos registros
        vista.getBtnAgregar().setEnabled(true); // Habilitar botón Agregar
        vista.getBtnEditar().setText("Editar"); // Restaurar texto del botón Editar
        modoEdicion = false; // Salir del modo edición
        indiceClienteEditando = -1; // Reiniciar índice
    }

    private void agregarCliente() {
        String dni = vista.getDni();

        if (!dni.matches("\\d{8}")) {
            JOptionPane.showMessageDialog(vista,
                "DNI debe tener 8 dígitos numéricos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

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
        resetearFormulario(); // Usar el método de reseteo

        JOptionPane.showMessageDialog(vista, "Cliente agregado correctamente");
    }

    // Método para entrar en modo edición (puede ser llamado por el listener de la tabla o el botón Editar)
    private void entrarModoEdicion(int filaSeleccionada) {
        // Los datos ya se cargan automáticamente por el ListSelectionListener
        // Solo necesitamos asegurar que el modoEdicion y el indiceClienteEditando estén correctos.
        modoEdicion = true;
        indiceClienteEditando = filaSeleccionada;
        vista.getTxtDni().setEnabled(false); // Mantener DNI deshabilitado
        vista.getBtnAgregar().setEnabled(false); // Deshabilitar agregar
        vista.getBtnEditar().setText("Guardar Cambios"); // Cambiar el texto del botón
    }

    private void guardarCambiosCliente() {
        // Verificar si realmente estamos en modo edición y si hay un cliente para editar
        if (!modoEdicion || indiceClienteEditando == -1) {
            JOptionPane.showMessageDialog(vista,
                "No hay un cliente en edición para guardar cambios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cliente clienteAEditar = clientes.get(indiceClienteEditando);

        // Obtener los nuevos valores de los campos de texto
        String nuevoNombre = vista.getNombre();
        String nuevoApellido = vista.getApellido();
        String nuevoEmail = vista.getEmail();
        String nuevoTelefono = vista.getTelefono();

        // Validaciones básicas antes de actualizar
        if (nuevoNombre.isEmpty() || nuevoApellido.isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                "Nombre y Apellido son campos obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Actualizar el objeto Cliente
        clienteAEditar.setNombre(nuevoNombre);
        clienteAEditar.setApellido(nuevoApellido);
        clienteAEditar.setEmail(nuevoEmail);
        clienteAEditar.setTelefono(nuevoTelefono);

        // Guardar la lista de clientes actualizada en el archivo
        clientesDAO.guardarClientes(clientes);
        // Recargar la tabla para mostrar los cambios
        cargarClientesEnTabla();
        resetearFormulario(); // Limpiar campos y resetear estado para salir del modo edición

        JOptionPane.showMessageDialog(vista, "Cliente editado correctamente");
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
            resetearFormulario(); // Limpiar campos y resetear estado
            JOptionPane.showMessageDialog(vista, "Cliente eliminado correctamente");
        }
    }
}
