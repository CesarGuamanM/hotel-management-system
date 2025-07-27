package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import modelo.TipoHabitacion;

public class HabitacionesView extends JPanel {
    private JTable tablaHabitaciones;
    private DefaultTableModel modeloTable;
    private JButton btnAgregar, btnEditar, btnCambiarEstado;
    private JTextField txtNumero, txtCaracteristicas;
    private JComboBox<TipoHabitacion> cbTipo;
    
    public HabitacionesView() {
        setLayout(new BorderLayout());
        
        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de Habitación"));
        
        panelFormulario.add(new JLabel("Número:"));
        txtNumero = new JTextField();
        panelFormulario.add(txtNumero);
        
        panelFormulario.add(new JLabel("Tipo:"));
        cbTipo = new JComboBox<>(TipoHabitacion.values());
        panelFormulario.add(cbTipo);
        
        panelFormulario.add(new JLabel("Características:"));
        txtCaracteristicas = new JTextField();
        panelFormulario.add(txtCaracteristicas);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnCambiarEstado = new JButton("Cambiar Estado");
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnCambiarEstado);
        
        // Panel norte (formulario + botones)
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelFormulario, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);
        
        // Tabla de habitaciones
        modeloTable = new DefaultTableModel();
        modeloTable.addColumn("Número");
        modeloTable.addColumn("Tipo");
        modeloTable.addColumn("Estado");
        modeloTable.addColumn("Características");
        
        tablaHabitaciones = new JTable(modeloTable);
        JScrollPane scrollPane = new JScrollPane(tablaHabitaciones);
        
        // Añadir componentes al panel principal
        add(panelNorte, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    // Getters
    public String getNumero() { return txtNumero.getText(); }
    public TipoHabitacion getTipo() { return (TipoHabitacion) cbTipo.getSelectedItem(); }
    public String getCaracteristicas() { return txtCaracteristicas.getText(); }
    public JTable getTablaHabitaciones() { return tablaHabitaciones; }
    
    // Setters
    public void setNumero(String numero) { txtNumero.setText(numero); }
    public void setTipo(TipoHabitacion tipo) { cbTipo.setSelectedItem(tipo); }
    public void setCaracteristicas(String caracteristicas) { txtCaracteristicas.setText(caracteristicas); }
    
    // Métodos para la tabla
    public void agregarHabitacionALista(Object[] datosHabitacion) {
        modeloTable.addRow(datosHabitacion);
    }
    
    public void limpiarTabla() {
        modeloTable.setRowCount(0);
    }
    
    public void limpiarCampos() {
        txtNumero.setText("");
        cbTipo.setSelectedIndex(0);
        txtCaracteristicas.setText("");
    }
    
    // Métodos para los listeners
    public void setAgregarListener(ActionListener listener) {
        btnAgregar.addActionListener(listener);
    }
    
    public void setEditarListener(ActionListener listener) {
        btnEditar.addActionListener(listener);
    }
    
    public void setCambiarEstadoListener(ActionListener listener) {
        btnCambiarEstado.addActionListener(listener);
    }
    
    public int getFilaSeleccionada() {
        return tablaHabitaciones.getSelectedRow();
    }
}