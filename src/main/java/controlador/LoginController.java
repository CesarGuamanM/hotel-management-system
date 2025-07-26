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
    private Usuario usuarioAutenticado;
    
    // Usuarios de prueba (luego se reemplazará por base de datos)
    private Usuario[] usuarios = {
        new Administrador("admin", "admin123", "Admin Principal"),
        new Recepcionista("recepcion", "recepcion123", "Recepcionista 1")
    };

    public LoginController(LoginView vista) {
        this.vista = vista;
        configurarListeners();
    }
    
    private void configurarListeners() {
        vista.getBtnLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticarUsuario();
            }
        });
    }
    
    private void autenticarUsuario() {
        String username = vista.getTxtUsuario().getText();
        String password = new String(vista.getTxtContrasena().getPassword());
        
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                if (usuario.getPassword().equals(password)) {
                    usuarioAutenticado = usuario;
                    JOptionPane.showMessageDialog(vista, 
                        "Bienvenido " + usuario.getNombre() + " (" + usuario.getTipoUsuario() + ")");
                    // Aquí abriríamos el menú principal
                    return;
                }
            }
        }
        
        JOptionPane.showMessageDialog(vista, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }
}