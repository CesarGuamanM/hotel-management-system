///////////////////      MENU PRINCIPAL     //////
package vista;

import javax.swing.*;
import java.awt.*;

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
        // Por ahora las dejamos vacías
        
        add(tabbedPane);
    }
    
    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }
}