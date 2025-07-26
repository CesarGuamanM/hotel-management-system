package modelo;

public class Recepcionista extends Usuario {
    public Recepcionista(String username, String password, String nombre) {
        super(username, password, nombre);
    }

    @Override
    public String getTipoUsuario() {
        return "Recepcionista";
    }
}
