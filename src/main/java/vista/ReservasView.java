package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.border.TitledBorder;
import modelo.Cliente;
import java.util.List;
import modelo.Habitacion;
import java.awt.event.ActionEvent;

public class ReservasView extends JPanel {
    private JTable tablaReservas;
    private DefaultTableModel modeloTable;
    private JButton btnNuevaReserva, btnCheckIn, btnCheckOut, btnCancelar;
    private JTextField txtCliente, txtHabitacion;
    private JFormattedTextField txtFechaInicio, txtFechaFin;
    private JComboBox<String> cbEstado;

    private JDialog nuevaReservaDialog;
    private JComboBox<Cliente> cbClientes;
    private JComboBox<Habitacion> cbHabitaciones;
    private JFormattedTextField txtNuevaFechaInicio;
    private JFormattedTextField txtNuevaFechaFin;
    private JButton btnConfirmarReserva;
    private boolean checkOutRealizado;
    
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
        cbHabitaciones = new JComboBox<>();
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
        initNuevaReservaDialog();
    }

    private void initNuevaReservaDialog() {
        nuevaReservaDialog = new JDialog();
        nuevaReservaDialog.setTitle("Nueva Reserva");
        nuevaReservaDialog.setSize(400, 300);
        nuevaReservaDialog.setLayout(new GridLayout(5, 2, 10, 10));
        
        // Componentes del diálogo
        nuevaReservaDialog.add(new JLabel("Cliente:"));
        cbClientes = new JComboBox<>();
        nuevaReservaDialog.add(cbClientes);

        nuevaReservaDialog.add(new JLabel("Habitación:"));
        cbHabitaciones = new JComboBox<>();
        nuevaReservaDialog.add(cbHabitaciones);

        nuevaReservaDialog.add(new JLabel("Fecha Inicio:"));
        txtNuevaFechaInicio = new JFormattedTextField(LocalDate.now());
        nuevaReservaDialog.add(txtNuevaFechaInicio);

        nuevaReservaDialog.add(new JLabel("Fecha Fin:"));
        txtNuevaFechaFin = new JFormattedTextField(LocalDate.now().plusDays(1));
        nuevaReservaDialog.add(txtNuevaFechaFin);

        btnConfirmarReserva = new JButton("Confirmar Reserva");
        nuevaReservaDialog.add(btnConfirmarReserva);
    }

    public void limpiarFiltros() {
        txtFechaInicio.setValue(LocalDate.now());
        txtFechaFin.setValue(LocalDate.now().plusDays(7));
        cbEstado.setSelectedItem("Todas");
    }

    // Métodos para el diálogo de nueva reserva
    public void mostrarDialogoNuevaReserva() {
        nuevaReservaDialog.setVisible(true);
    }

    public void ocultarDialogoNuevaReserva() {
        nuevaReservaDialog.setVisible(false);
    }

    public void cargarClientes(List<Cliente> clientes) {
        DefaultComboBoxModel<Cliente> modelo = new DefaultComboBoxModel<>();
        for (Cliente cliente : clientes) {
            modelo.addElement(cliente);
        }
        cbClientes.setModel(modelo);
    }

    public void cargarHabitaciones(List<Habitacion> habitaciones) {
        DefaultComboBoxModel<Habitacion> modelo = new DefaultComboBoxModel<>();
        for (Habitacion habitacion : habitaciones) {
            modelo.addElement(habitacion);
        }
        cbHabitaciones.setModel(modelo);
    }

    public boolean isCheckOutRealizado() {
        return checkOutRealizado;
    }

    
    // Getters para los datos del formulario
    public String getCliente() { return txtCliente.getText(); }
    public String getHabitacion() { return txtHabitacion.getText(); }
    public LocalDate getFechaInicio() { return (LocalDate) txtFechaInicio.getValue(); }
    public LocalDate getFechaFin() { return (LocalDate) txtFechaFin.getValue(); }
    public JTable getTablaReservas() {return tablaReservas; }


    public String getEstadoFiltro() {
        return (String) cbEstado.getSelectedItem();
    }

    // Métodos para obtener los valores de filtrado
    public LocalDate getFechaDesde() {
        try {
            return (LocalDate) txtFechaInicio.getValue();
        } catch (Exception e) {
            return LocalDate.now();
        }
    }

    public LocalDate getFechaHasta() {
        try {
            return (LocalDate) txtFechaFin.getValue();
        } catch (Exception e) {
            return LocalDate.now().plusDays(7);
        }
    }

    public Cliente getClienteSeleccionado() {
        return (Cliente) cbClientes.getSelectedItem();
    }

    public Habitacion getHabitacionSeleccionada() {
        return (Habitacion) cbHabitaciones.getSelectedItem();
    }

    public LocalDate getNuevaFechaInicio() {
        return (LocalDate) txtNuevaFechaInicio.getValue();
    }

    public LocalDate getNuevaFechaFin() {
        return (LocalDate) txtNuevaFechaFin.getValue();
    }

    public JButton getBtnConfirmarReserva() {
        return btnConfirmarReserva;
    }

    public JComboBox<Habitacion> getCbHabitaciones() {
        return cbHabitaciones;
    }


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

    // Métodos para configurar listeners de filtrado
    public void setFiltroEstadoListener(ActionListener listener) {
        cbEstado.addActionListener(listener);
    }

    public void setFiltroFechaListener(ActionListener listener) {
        txtFechaInicio.addPropertyChangeListener("value", e -> listener.actionPerformed(
            new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, "fechaCambiada")));
        txtFechaFin.addPropertyChangeListener("value", e -> listener.actionPerformed(
            new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, "fechaCambiada")));
    }

    public void setCheckOutRealizado(boolean checkOutRealizado) {
        this.checkOutRealizado = checkOutRealizado;
    }
}