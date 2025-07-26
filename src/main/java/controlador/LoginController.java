package controlador;

import vista.LoginView;
import modelo.Usuario;
import modelo.Recepcionista;
import modelo.Administrador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class LoginController {
    private LoginView vista;
    
    public LoginController(LoginView vista) {
        this.vista = vista;
        
        // Configurar el action listener para el botón de login
        vista.getBtnLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = vista.getTxtUsuario().getText();
                String contrasena = new String(vista.getTxtContrasena().getPassword());
                
                // Validación básica (esto debería conectarse con una base de datos)
                if(usuario.equals("admin") && contrasena.equals("admin123")) {
                    JOptionPane.showMessageDialog(vista, "Bienvenido Administrador");
                    // Aquí abriríamos el menú principal
                } else if(usuario.equals("recepcion") && contrasena.equals("recepcion123")) {
                    JOptionPane.showMessageDialog(vista, "Bienvenido Recepcionista");
                    // Aquí abriríamos el menú principal
                } else {
                    JOptionPane.showMessageDialog(vista, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}