package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class ClientesView extends JPanel {
    private JTable tablaClientes;
    private DefaultTableModel modeloTable;
    private JButton btnAgregar, btnEditar, btnEliminar;
    private JTextField txtDni, txtNombre, txtApellido, txtEmail, txtTelefono;
    
    public ClientesView() {
        setLayout(new BorderLayout());
        
        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));
        
        panelFormulario.add(new JLabel("DNI:"));
        txtDni = new JTextField();
        panelFormulario.add(txtDni);
        
        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);
        
        panelFormulario.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        panelFormulario.add(txtApellido);
        
        panelFormulario.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelFormulario.add(txtEmail);
        
        panelFormulario.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelFormulario.add(txtTelefono);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        
        // Panel norte (formulario + botones)
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelFormulario, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);
        
        // Tabla de clientes
        modeloTable = new DefaultTableModel();
        modeloTable.addColumn("DNI");
        modeloTable.addColumn("Nombre");
        modeloTable.addColumn("Apellido");
        modeloTable.addColumn("Email");
        modeloTable.addColumn("Teléfono");
        
        tablaClientes = new JTable(modeloTable);
        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        
        // Añadir componentes al panel principal
        add(panelNorte, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    // Getters para los campos de texto
    public String getDni() { return txtDni.getText(); }
    public String getNombre() { return txtNombre.getText(); }
    public String getApellido() { return txtApellido.getText(); }
    public String getEmail() { return txtEmail.getText(); }
    public String getTelefono() { return txtTelefono.getText(); }
    
    // Setters para los campos de texto
    public void setDni(String dni) { txtDni.setText(dni); }
    public void setNombre(String nombre) { txtNombre.setText(nombre); }
    public void setApellido(String apellido) { txtApellido.setText(apellido); }
    public void setEmail(String email) { txtEmail.setText(email); }
    public void setTelefono(String telefono) { txtTelefono.setText(telefono); }
    
    // Métodos para los botones
    public void agregarClienteALista(String[] datosCliente) {
        modeloTable.addRow(datosCliente);
    }
    
    public void setAgregarListener(ActionListener listener) {
        btnAgregar.addActionListener(listener);
    }
    
    public void setEditarListener(ActionListener listener) {
        btnEditar.addActionListener(listener);
    }
    
    public void setEliminarListener(ActionListener listener) {
        btnEliminar.addActionListener(listener);
    }
    
    public int getFilaSeleccionada() {
        return tablaClientes.getSelectedRow();
    }
    
    public void limpiarCampos() {
        txtDni.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
    }
}