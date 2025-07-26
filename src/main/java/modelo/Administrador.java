package modelo;

public class Administrador extends Usuario {
    public Administrador(String username, String password, String nombre) {
        super(username, password, nombre);
    }

    @Override
    public String getTipoUsuario() {
        return "Administrador";
    }
}