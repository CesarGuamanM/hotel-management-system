package modelo.persistencia;

import modelo.Cliente;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClientesDAO {
    // Cambiamos la extensión del archivo a .csv
    private static final String ARCHIVO = "clientes.csv";
    private static final String DELIMITADOR = ","; // Delimitador CSV

    public void guardarClientes(List<Cliente> clientes) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO))) {
            // Escribir cabecera CSV
            writer.println("DNI,Nombre,Apellido,Email,Telefono");

            for (Cliente cliente : clientes) {
                // Escribir los datos del cliente, escapando comas si fuera necesario (aquí no se implementa)
                writer.println(
                    cliente.getDni() + DELIMITADOR +
                    cliente.getNombre() + DELIMITADOR +
                    cliente.getApellido() + DELIMITADOR +
                    cliente.getEmail() + DELIMITADOR +
                    cliente.getTelefono()
                );
            }
        } catch (IOException e) {
            System.err.println("Error al guardar clientes en CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Cliente> cargarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        File file = new File(ARCHIVO);

        // Si el archivo no existe o está vacío, retornar una lista vacía
        if (!file.exists() || file.length() == 0) {
            return clientes;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            // Saltar la línea de cabecera
            reader.readLine();

            while ((linea = reader.readLine()) != null) {
                // Dividir la línea por el delimitador
                String[] datos = linea.split(DELIMITADOR, -1); // -1 para mantener campos vacíos al final

                // Asegurarse de que tenemos el número correcto de campos
                if (datos.length == 5) { // DNI, Nombre, Apellido, Email, Telefono
                    String dni = datos[0];
                    String nombre = datos[1];
                    String apellido = datos[2];
                    String email = datos[3];
                    String telefono = datos[4];

                    // Crear y agregar el objeto Cliente
                    clientes.add(new Cliente(dni, nombre, apellido, email, telefono));
                } else {
                    System.err.println("Advertencia: Línea con formato incorrecto en clientes.csv: " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar clientes desde CSV: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>(); // Retornar lista vacía en caso de error de carga
        }
        return clientes;
    }
}