package modelo.persistencia;

import modelo.Habitacion;
import modelo.TipoHabitacion; // Necesitamos importar TipoHabitacion
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HabitacionesDAO {
    private static final String ARCHIVO = "habitaciones.csv";
    private static final String DELIMITADOR = ",";

    public void guardarHabitaciones(List<Habitacion> habitaciones) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO))) {
            // Cabecera CSV
            writer.println("Numero,Tipo,Descripcion"); // Ajusta según los atributos de tu Habitacion

            for (Habitacion habitacion : habitaciones) {
                writer.println(
                    habitacion.getNumero() + DELIMITADOR +
                    habitacion.getTipo().name() + DELIMITADOR + // Guardar el enum como String
                    habitacion.getCaracteristicas()
                );
            }
        } catch (IOException e) {
            System.err.println("Error al guardar habitaciones en CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Habitacion> cargarHabitaciones() {
        List<Habitacion> habitaciones = new ArrayList<>();
        File file = new File(ARCHIVO);

        if (!file.exists() || file.length() == 0) {
            // Si no existe o está vacío, creamos las habitaciones por defecto
            return crearHabitacionesPorDefecto();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            reader.readLine(); // Saltar la línea de cabecera

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(DELIMITADOR, -1);

                // Asumiendo 3 campos: Numero, Tipo, Descripcion
                if (datos.length == 4) {
                    try {
                        int numero = Integer.parseInt(datos[0]);
                        TipoHabitacion tipo = TipoHabitacion.valueOf(datos[1]);
                        String caracteristicas = datos[2];
                        boolean disponible = Boolean.parseBoolean(datos[3]);

                        Habitacion habitacion = new Habitacion(numero, tipo, caracteristicas);
                        habitacion.setDisponible(disponible);

                        habitaciones.add(habitacion);
                    // Captura solo IllegalArgumentException, que incluye NumberFormatException
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error al parsear datos de habitación en CSV (Número, Tipo o Boolean): " + linea + " - " + e.getMessage());
                    }
                } else {
                    System.err.println("Advertencia: Línea con formato incorrecto en habitaciones.csv: " + linea + " (Campos esperados: 4, encontrados: " + datos.length + ")");
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar habitaciones desde CSV: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
        return habitaciones;
    }

    // Este método ya lo tenías, se mantiene igual pero ahora guarda en CSV
    private List<Habitacion> crearHabitacionesPorDefecto() {
        List<Habitacion> habitaciones = new ArrayList<>();
        try {
            // Crear habitaciones de ejemplo
            habitaciones.add(new Habitacion(101, TipoHabitacion.INDIVIDUAL, "Vista al jardín"));
            habitaciones.add(new Habitacion(102, TipoHabitacion.INDIVIDUAL, "Vista a la calle"));
            habitaciones.add(new Habitacion(201, TipoHabitacion.DOBLE, "2 camas individuales"));
            habitaciones.add(new Habitacion(202, TipoHabitacion.DOBLE, "1 cama matrimonial"));
            habitaciones.add(new Habitacion(301, TipoHabitacion.SUITE, "Suite ejecutiva con jacuzzi"));

            // Guardar las habitaciones por defecto en el nuevo formato CSV
            guardarHabitaciones(habitaciones);
        } catch (Exception e) {
            System.err.println("Error al crear habitaciones por defecto: " + e.getMessage());
            e.printStackTrace();
        }
        return habitaciones;
    }
}