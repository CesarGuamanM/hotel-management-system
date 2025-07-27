///////////////////      MENU PRINCIPAL     //////
package vista;

import javax.swing.*;

import controlador.ClientesController;
import controlador.HabitacionesController;
//import controlador.LoginController;
import controlador.ReservasController;

//import java.awt.*;

public class MainMenuView extends JFrame {
    private JTabbedPane tabbedPane;
    
    public MainMenuView() {
        setTitle("Sistema de Gestión de Hotel");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Crear el panel con pestañas
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Clientes", new ClientesView());
        
        // Aquí añadiremos las pestañas (Clientes, Habitaciones, Reservas)
        ClientesView clientesView = new ClientesView();
        new ClientesController(clientesView);
        tabbedPane.addTab("Clientes", clientesView);
        
        HabitacionesView habitacionesView = new HabitacionesView();
        new HabitacionesController(habitacionesView);
        tabbedPane.addTab("Habitaciones", habitacionesView);
        
        ReservasView reservasView = new ReservasView();
        new ReservasController(reservasView);
        tabbedPane.addTab("Reservas", reservasView);

        add(tabbedPane);
    }
    
    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }
}