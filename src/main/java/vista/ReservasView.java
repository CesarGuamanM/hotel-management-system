package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.border.TitledBorder;

public class ReservasView extends JPanel {
    private JTable tablaReservas;
    private DefaultTableModel modeloTable;
    private JButton btnNuevaReserva, btnCheckIn, btnCheckOut, btnCancelar;
    private JTextField txtCliente, txtHabitacion;
    private JFormattedTextField txtFechaInicio, txtFechaFin;
    private JComboBox<String> cbEstado;
    
    public ReservasView() {
        setLayout(new BorderLayout());
        
        // Panel de filtros
        JPanel panelFiltros = new JPanel(new GridLayout(1, 3, 5, 5));
        panelFiltros.setBorder(new TitledBorder("Filtros"));
        
        panelFiltros.add(new JLabel("Fecha desde:"));
        txtFechaInicio = new JFormattedTextField(LocalDate.now());
        panelFiltros.add(txtFechaInicio);
        
        panelFiltros.add(new JLabel("Fecha hasta:"));
        txtFechaFin = new JFormattedTextField(LocalDate.now().plusDays(7));
        panelFiltros.add(txtFechaFin);
        
        panelFiltros.add(new JLabel("Estado:"));
        cbEstado = new JComboBox<>(new String[]{"Todas", "Pendientes", "Confirmadas", "Canceladas"});
        panelFiltros.add(cbEstado);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnNuevaReserva = new JButton("Nueva Reserva");
        btnCheckIn = new JButton("Check-In");
        btnCheckOut = new JButton("Check-Out");
        btnCancelar = new JButton("Cancelar");
        
        panelBotones.add(btnNuevaReserva);
        panelBotones.add(btnCheckIn);
        panelBotones.add(btnCheckOut);
        panelBotones.add(btnCancelar);
        
        // Panel norte (filtros + botones)
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelFiltros, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);
        
        // Tabla de reservas
        modeloTable = new DefaultTableModel();
        modeloTable.addColumn("ID");
        modeloTable.addColumn("Cliente");
        modeloTable.addColumn("Habitación");
        modeloTable.addColumn("Fecha Inicio");
        modeloTable.addColumn("Fecha Fin");
        modeloTable.addColumn("Total");
        modeloTable.addColumn("Estado");
        
        tablaReservas = new JTable(modeloTable);
        JScrollPane scrollPane = new JScrollPane(tablaReservas);
        
        // Añadir componentes al panel principal
        add(panelNorte, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    // Getters para los datos del formulario
    public String getCliente() { return txtCliente.getText(); }
    public String getHabitacion() { return txtHabitacion.getText(); }
    public LocalDate getFechaInicio() { return (LocalDate) txtFechaInicio.getValue(); }
    public LocalDate getFechaFin() { return (LocalDate) txtFechaFin.getValue(); }
    
    // Métodos para la tabla
    public void agregarReservaALista(Object[] datosReserva) {
        modeloTable.addRow(datosReserva);
    }
    
    public void limpiarTabla() {
        modeloTable.setRowCount(0);
    }
    
    // Métodos para los listeners
    public void setNuevaReservaListener(ActionListener listener) {
        btnNuevaReserva.addActionListener(listener);
    }
    
    public void setCheckInListener(ActionListener listener) {
        btnCheckIn.addActionListener(listener);
    }
    
    public void setCheckOutListener(ActionListener listener) {
        btnCheckOut.addActionListener(listener);
    }
    
    public void setCancelarListener(ActionListener listener) {
        btnCancelar.addActionListener(listener);
    }
    
    public int getFilaSeleccionada() {
        return tablaReservas.getSelectedRow();
    }
}