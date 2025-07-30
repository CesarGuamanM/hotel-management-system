#  Sistema de Gestión de Hotel

Sistema de gestión de reservas para hotel desarrollado en Java usando Swing y el patrón arquitectónico MVC.

##  Integrantes

- César Guamá
- Sebastián Tacuri

##  Requisitos

- Java 11 o superior  

##  Instrucciones

### 1. Clonar repositorio:

``bash
git clone [url]

### 2. Ejecutar aplicación:

java -cp target/classes main.Main

Alternativamente, puedes abrir el proyecto en un IDE como IntelliJ IDEA o Visual Studio Code y ejecutar main.Main.

## Funcionalidad del PROYECTO

### ¿Cómo funciona el sistema?

Este sistema permite gestionar las **reservas de un hotel** a través de una interfaz gráfica desarrollada en Java con Swing. Está organizado utilizando el patrón **MVC (Modelo - Vista - Controlador)** para mantener una estructura limpia y modular del código.

### Funcionalidades principales

- **Login**: acceso al sistema mediante usuario y contraseña.
- **Gestión de clientes**: registrar, editar y eliminar información de los clientes.
- **Visualización de habitaciones**: muestra información de habitaciones disponibles.
- **Gestión de reservas**: permite realizar nuevas reservas y cancelarlas según disponibilidad de fechas.

##  Estructura del Proyecto

<pre>
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
</pre>

## Capturas


## Diagrama UML
<img width="2911" height="892" alt="Reserva_hotel" src="https://github.com/user-attachments/assets/a1892724-90e8-48eb-b2f7-b92196c3856b" />

