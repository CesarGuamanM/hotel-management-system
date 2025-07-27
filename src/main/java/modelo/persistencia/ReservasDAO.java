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
            oos.writeObject(reservas);
            // Actualizar el último ID usado
            if (!reservas.isEmpty()) {
                ultimoId = reservas.stream().mapToInt(Reserva::getId).max().orElse(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public List<Reserva> cargarReservas() {
        File file = new File(ARCHIVO);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO))) {
            List<Reserva> reservas = (List<Reserva>) ois.readObject();
            // Actualizar el último ID usado
            if (!reservas.isEmpty()) {
                ultimoId = reservas.stream().mapToInt(Reserva::getId).max().orElse(0);
            }
            return reservas;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public int getNuevoId() {
        return ++ultimoId;
    }
}
