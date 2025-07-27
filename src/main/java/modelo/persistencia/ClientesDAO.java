package modelo.persistencia;

import modelo.Cliente;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClientesDAO {
    private static final String ARCHIVO = "clientes.dat";
    
    public void guardarClientes(List<Cliente> clientes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            oos.writeObject(new ArrayList<>(clientes)); // Usamos ArrayList para garantizar serialización
        } catch (IOException e) {
            System.err.println("Error al guardar clientes: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<Cliente> cargarClientes() {
        File file = new File(ARCHIVO);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO))) {
            Object obj = ois.readObject();
            
            if (obj instanceof List<?>) {
                List<?> lista = (List<?>) obj;
                // Verificamos que todos los elementos sean de tipo Cliente
                if (!lista.isEmpty() && !(lista.get(0) instanceof Cliente)) {
                    throw new IOException("Formato de archivo inválido");
                }
                return (List<Cliente>) lista;
            }
            throw new IOException("Formato de archivo inválido");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar clientes: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}