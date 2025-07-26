package main;

import vista.LoginView;
import controlador.LoginController;

public class Main {
    public static void main(String[] args) {
        // Crear y mostrar la vista de login
        LoginView loginView = new LoginView();
        new LoginController(loginView); // El controlador se registra como listener
        loginView.setVisible(true);
    }
}
