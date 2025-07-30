#  Sistema de Gestión de Hotel

Sistema de gestión de reservas para hotel desarrollado en Java usando Swing y el patrón arquitectónico MVC.

##  Integrantes

- César Guamán (Rol: Vista / Controlador)  
- Sebastián Tacuri (Rol: Modelo / Persistencia)

##  Requisitos

- Java 11 o superior  
- Maven  
- Librerías utilizadas:
  - SQLite (persistencia de datos)
  - JCalendar (selección de fechas en formularios)

##  Instrucciones

### 1. Clonar repositorio:

``bash
git clone [url]

### 2. Compilar con Maven:
mvn compile

### 3. Ejecutar aplicación:

java -cp target/classes main.Main

Alternativamente, puedes abrir el proyecto en un IDE como IntelliJ IDEA o Visual Studio Code y ejecutar main.Main.

##  Estructura del Proyecto

hotel-management-system/
├── src/
│   └── main/
│       └── java/
│           ├── controlador/        # Lógica de control (MVC)
│           ├── main/               # Clase principal del sistema
│           ├── modelo/             # Clases del modelo
│           │   └── persistencia/   # Acceso a datos (DAOs)
│           └── vista/              # Interfaces gráficas (Swing)
├── .gitignore
├── LICENSE
├── README.md

## Capturas


## Diagrama UML
<img width="2911" height="892" alt="Reserva_hotel" src="https://github.com/user-attachments/assets/a1892724-90e8-48eb-b2f7-b92196c3856b" />

