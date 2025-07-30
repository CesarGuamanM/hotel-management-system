#  Sistema de Gestión de Hotel

Sistema de gestión de reservas para hotel desarrollado en Java usando Swing y el patrón arquitectónico MVC.

##  Integrantes

- César Guamá
- Sebastián Tacuri

##  Requisitos

- Java 11 o superior

## Funcionalidad del PROYECTO

### ¿Cómo funciona el sistema?

Este sistema permite gestionar las **reservas de un hotel** a través de una interfaz gráfica desarrollada en Java con Swing. Está organizado utilizando el patrón **MVC (Modelo - Vista - Controlador)** para mantener una estructura limpia y modular del código.

### Funcionalidades principales

- **Login**: acceso al sistema mediante usuario y contraseña.
- **Gestión de clientes**: registrar, editar y eliminar información de los clientes.
- **Visualización de habitaciones**: muestra información de habitaciones disponibles.
- **Gestión de reservas**: permite realizar nuevas reservas y cancelarlas según disponibilidad de fechas.

##  Instrucciones

### 1. Clonar repositorio:

``bash
git clone [url]

### 2. Ejecutar aplicación:

java -cp target/classes main.Main

Alternativamente, puedes abrir el proyecto en un IDE como IntelliJ IDEA o Visual Studio Code y ejecutar main.Main.


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

#### Ventana LOGIN
![WhatsApp Image 2025-07-29 at 11 15 39 PM](https://github.com/user-attachments/assets/1f95a16f-4f70-4f82-9a9a-bd3ed8eae2e9)

#### Mensaje de Autenticacion
![WhatsApp Image 2025-07-29 at 11 15 56 PM](https://github.com/user-attachments/assets/5a27f4b6-0abc-45f2-836c-bfb0896c8696)

#### Ventana CLIENTES
![WhatsApp Image 2025-07-29 at 11 16 13 PM](https://github.com/user-attachments/assets/817cc5bd-481c-404b-9bda-dcd8d295c032)

#### Ventana HABITACIONES
![WhatsApp Image 2025-07-29 at 11 16 28 PM](https://github.com/user-attachments/assets/9e5a1811-d238-4147-8473-fdc62aebde8e)

#### Ventana RESERVA

![WhatsApp Image 2025-07-29 at 11 16 46 PM](https://github.com/user-attachments/assets/a81b55a1-3fb9-4daf-8399-88667151a36b)



## Diagrama UML
<img width="2911" height="892" alt="Reserva_hotel" src="https://github.com/user-attachments/assets/a1892724-90e8-48eb-b2f7-b92196c3856b" />

