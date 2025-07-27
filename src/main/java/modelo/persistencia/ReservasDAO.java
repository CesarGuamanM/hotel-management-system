package modelo.persistencia;

import modelo.Reserva;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReservasDAO {
    private static final String ARCHIVO = "reservas.dat";
    private static int ultimoId = 0;
    
    public void guardarReservas(List<Reserva> reservas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            oos.writeObject(new ArrayList<>(reservas)); // Usamos ArrayList para garantizar serialización
            actualizarUltimoId(reservas);
        } catch (IOException e) {
            System.err.println("Error al guardar reservas: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<Reserva> cargarReservas() {
        File file = new File(ARCHIVO);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO))) {
            Object obj = ois.readObject();
            
            // Verificación segura del tipo
            if (obj instanceof List<?>) {
                List<?> lista = (List<?>) obj;
                if (!lista.isEmpty() && !(lista.get(0) instanceof Reserva)) {
                    throw new IOException("Formato de archivo inválido: no contiene Reservas");
                }
                List<Reserva> reservas = (List<Reserva>) lista;
                actualizarUltimoId(reservas);
                return reservas;
            }
            throw new IOException("Formato de archivo inválido: no es una lista");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar reservas: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    private void actualizarUltimoId(List<Reserva> reservas) {
        if (!reservas.isEmpty()) {
            ultimoId = reservas.stream().mapToInt(Reserva::getId).max().orElse(0);
        }
    }
    
    public int getNuevoId() {
        return ++ultimoId;
    }
}
