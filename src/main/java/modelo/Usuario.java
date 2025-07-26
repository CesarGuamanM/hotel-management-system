package modelo;

public abstract class Usuario {
    private String username;
    private String password;
    private String nombre;

    public Usuario(String username, String password, String nombre) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
    }

    // Getters y setters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getNombre() { return nombre; }

    public abstract String getTipoUsuario();
}
