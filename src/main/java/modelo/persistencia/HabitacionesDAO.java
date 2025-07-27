package modelo.persistencia;

import modelo.Habitacion;
import modelo.TipoHabitacion;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HabitacionesDAO {
    private static final String ARCHIVO = "habitaciones.dat";
    
    public void guardarHabitaciones(List<Habitacion> habitaciones) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            oos.writeObject(habitaciones);
        } catch (IOException e) {
            System.err.println("Error al guardar habitaciones: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<Habitacion> cargarHabitaciones() {
        File file = new File(ARCHIVO);
        if (!file.exists()) {
            return crearHabitacionesPorDefecto();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO))) {
            return (List<Habitacion>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar habitaciones: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    private List<Habitacion> crearHabitacionesPorDefecto() {
        List<Habitacion> habitaciones = new ArrayList<>();
        try {
            // Crear habitaciones de ejemplo
            habitaciones.add(new Habitacion(101, TipoHabitacion.INDIVIDUAL, "Vista al jardín"));
            habitaciones.add(new Habitacion(102, TipoHabitacion.INDIVIDUAL, "Vista a la calle"));
            habitaciones.add(new Habitacion(201, TipoHabitacion.DOBLE, "2 camas individuales"));
            habitaciones.add(new Habitacion(202, TipoHabitacion.DOBLE, "1 cama matrimonial"));
            habitaciones.add(new Habitacion(301, TipoHabitacion.SUITE, "Suite ejecutiva con jacuzzi"));
            
            // Guardar las habitaciones por defecto
            guardarHabitaciones(habitaciones);
        } catch (Exception e) {
            System.err.println("Error al crear habitaciones por defecto: " + e.getMessage());
            e.printStackTrace();
        }
        return habitaciones;
    }
}