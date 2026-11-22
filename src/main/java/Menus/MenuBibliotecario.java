package Menus;

import dao.LibroDAO;
import dao.UsuarioDAO;
import Clases_Persona.Usuario; // NUEVO
import Enum.EstadoPrestamo;
import Enum.EstadoLibro;// NUEVO
import dao.PrestamoDAO;
import Clases_Libros.Prestamo;

import java.util.List;
import java.util.Scanner;


// NUEVO
import Clases_Libros.Libro;
import Clases_Libros.Ejemplar;
import Excepciones.AccesoDatosException;
import Excepciones.ElementoNoEncontradoException;
import dao.EjemplarDAO;

public class MenuBibliotecario {
    private Scanner scanner;
    private LibroDAO libroDAO;
    private UsuarioDAO usuarioDAO;
    private EjemplarDAO ejemplarDAO;
    // NUEVO
    private PrestamoDAO prestamoDAO;

    public MenuBibliotecario(Scanner scanner) {
        this.scanner = scanner;
        this.libroDAO = new LibroDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.ejemplarDAO = new EjemplarDAO(); ///Inicializamos el ejemoplarDAO
        this.prestamoDAO = new PrestamoDAO();
    }

    public void mostrarMenu() {
        int opcion = -1;
        do {
            System.out.println("\n--- MENÚ BIBLIOTECARIO ---");
            System.out.println("1. Gestionar Libros y Ejemplares");
            System.out.println("2. Gestionar Usuarios");
            System.out.println("3. Gestionar Prestamos");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        sub_menuLibros();
                        break;
                    case 2:
                        sub_menuUsuarios();
                        break;
                    case 3:
                        sub_menuPrestamos(); // NUEVO
                        break;
                    case 4:
                        System.out.println("Volviendo al menú principal...");
                        break;
                    default:
                        System.out.println("\n[!] Opción inválida.");
                }
            } else {
                System.out.println("\n[!] Ingrese un número válido.");
                scanner.nextLine();
            }
        } while (opcion != 4);
    }

    private void sub_menuLibros() {
        System.out.println("\n[Gestión de Libros conectada con LibroDAO]");
        // Acá se invocan metodos de libroDAO.listar(), registrar(), etc.
        int opcion = -1;
        do {

            System.out.println("\n===== GESTIÓN DE LIBROS Y EJEMPLARES =====");
            System.out.println("1. Listar todos los libros");
            System.out.println("2. Buscar libro por ID");
            System.out.println("3. Listar ejemplares");
            System.out.println("4. Buscar ejemplar por ID");
            System.out.println("5. Registrar nuevo libro"); // NUEVO
            System.out.println("6. Modificar libro"); // NUEVO
            System.out.println("7. Eliminar libro"); // NUEVO
            System.out.println("8. Registrar ejemplar");
            System.out.println("9. Modificar ejemplar");
            System.out.println("10. Eliminar ejemplar");
            System.out.println("11. Listar ejemplares de un libro"); // NUEVO

            System.out.println("0. Volver al menú bibliotecario");
            System.out.print("Seleccione una opción: ");

            // MODIFICADO: validar la opcion antes de leerla
            if (!scanner.hasNextInt()) {
                System.out.println("ERROR: Debe ingresar una opcion valida.");
                scanner.nextLine();
                continue; // NUEVO: vuelve al inicio del menu
            }

            opcion = scanner.nextInt();
            scanner.nextLine();
            try {
                switch (opcion) {
                    case 1:
                        /// Listar libros desde MYSQL
                        System.out.println("LISTADO:");
                        for (Libro l : libroDAO.listarTodos()) {
                            System.out.println(l.toString());
                        }
                        break;
                    case 2:
                        /// Buscar por id
                        System.out.println("Ingrese el id del libro:");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        Libro l = libroDAO.buscarPorId(id);
                        System.out.println(l.toString());
                        break;
                    case 3:
                        /// Listar ejemplares
                        System.out.println("LISTADO EJEMPLARES");
                        for (Ejemplar ejemplar : ejemplarDAO.listarTodos()) {
                            System.out.println(ejemplar.toString());
                        }
                        break;
                    case 4:
                        /// Buscar ejemplar por id
                        System.out.println("Ingrese el ID del ejemplar:");
                        int idEjemplar = scanner.nextInt();
                        scanner.nextLine();

                        Ejemplar ejemplar = ejemplarDAO.buscarPorId(idEjemplar);
                        System.out.println(ejemplar.toString());
                        break;
                    case 5:
                        registrarLibro(); // NUEVO
                        break;
                    case 6:
                        // NUEVO: modificar libro existente
                        modificarLibro();
                        break;

                    case 7:
                        // NUEVO: eliminar libro existente
                        eliminarLibro();
                        break;
                    // NUEVO
                    case 8:
                        registrarEjemplar();
                        break;

                    case 9:
                        modificarEjemplar();
                        break;

                    case 10:
                        eliminarEjemplar();
                        break;
                    case 11:
                        listarEjemplaresPorLibro(); // NUEVO
                        break;
                    case 0:
                        System.out.println("Volviendo al menu..");
                        break;
                    default:
                        System.out.println("Opcion invalida");
                        break;
                }
            } catch (AccesoDatosException e) {
                System.out.println("ERROR de MySQL: " + e.getMessage());

            } catch (ElementoNoEncontradoException e) {
                System.out.println("No encontrado: " + e.getMessage());

            } catch (java.util.InputMismatchException e) {
                System.out.println("ERROR: El ID debe ser numérico.");
                scanner.nextLine();
            }
        } while (opcion != 0);

    }

    private void sub_menuPrestamos() {

        // NUEVO: submenu de prestamos

        int opcion = -1;

        do {
            System.out.println("\n===== GESTION DE PRESTAMOS =====");
            System.out.println("1. Registrar prestamo");
            System.out.println("2. Registrar devolucion");
            System.out.println("3. Listar todos los prestamos");
            System.out.println("4. Buscar prestamo por ID");
            System.out.println("5. Listar prestamos activos");
            System.out.println("6. Listar prestamos de un usuario");
            System.out.println("0. Volver al menu bibliotecario");
            System.out.print("Seleccione una opcion: ");

            if (!scanner.hasNextInt()) {
                System.out.println("ERROR: Ingrese un numero.");
                scanner.nextLine();
                continue;
            }

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    registrarPrestamo();
                    break;

                case 2:
                    registrarDevolucion();
                    break;

                case 3:
                    listarPrestamos();
                    break;

                case 4:
                    buscarPrestamo();
                    break;

                case 5:
                    listarPrestamosActivos();
                    break;

                case 6:
                    listarPrestamosPorUsuario();
                    break;

                case 0:
                    System.out.println("Volviendo al menu bibliotecario...");
                    break;

                default:
                    System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);
    }


    private void sub_menuUsuarios() {

        int opcion = -1;

        do {
            System.out.println("\n===== GESTION DE USUARIOS =====");
            System.out.println("1. Listar todos los usuarios");
            System.out.println("2. Buscar usuario por ID");
            System.out.println("3. Registrar nuevo usuario"); // NUEVO
            System.out.println("4. Modificar usuario"); // NUEVO
            System.out.println("5. Eliminar usuario"); // NUEVO
            System.out.println("0. Volver al menu bibliotecario");
            System.out.print("Seleccione una opcion: ");

            // NUEVO: validamos la entrada
            if (!scanner.hasNextInt()) {
                System.out.println("ERROR: Debe ingresar un numero.");
                scanner.nextLine();
                continue;
            }

            opcion = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (opcion) {

                    case 1:
                        // NUEVO: recuperar usuarios desde MySQL
                        System.out.println("\n--- LISTADO DE USUARIOS ---");

                        for (Usuario usuario : usuarioDAO.listarTodos()) {
                            System.out.println(
                                    "ID: " + usuario.getIdSocio() +
                                            " | Nombre: " + usuario.getNombre() +
                                            " | DNI: " + usuario.getDni() +
                                            " | Contacto: " + usuario.getContacto()
                            );
                        }
                        break;

                    case 2:
                        // NUEVO: buscar usuario por ID
                        System.out.print("Ingrese el ID del usuario: ");

                        if (!scanner.hasNextInt()) {
                            System.out.println("ERROR: El ID debe ser numerico.");
                            scanner.nextLine();
                            break;
                        }

                        int idUsuario = scanner.nextInt();
                        scanner.nextLine();

                        Usuario usuario = usuarioDAO.buscarPorId(idUsuario);

                        System.out.println("\n--- USUARIO ENCONTRADO ---");
                        System.out.println("ID: " + usuario.getIdSocio());
                        System.out.println("Nombre: " + usuario.getNombre());
                        System.out.println("DNI: " + usuario.getDni());
                        System.out.println("Direccion: " + usuario.getDireccion());
                        System.out.println("Contacto: " + usuario.getContacto());
                        break;
                    case 3:
                        registrarUsuario();
                        break;
                    case 4:
                        modificarUsuario(); // NUEVO
                        break;
                    case 5:
                        // NUEVO: eliminar usuario desde MySQL
                        eliminarUsuario();
                        break;
                    case 0:
                        System.out.println("Volviendo al menu bibliotecario...");
                        break;

                    default:
                        System.out.println("Opcion invalida.");
                        break;
                }

            } catch (AccesoDatosException e) {
                System.out.println("ERROR de MySQL: " + e.getMessage());

            } catch (ElementoNoEncontradoException e) {
                System.out.println("Usuario no encontrado: " + e.getMessage());
            }

        } while (opcion != 0);
    }


    /// METODOS SUBMENU USUARIOS
    private void registrarUsuario() {
        try {
            List<Usuario> usuarios = usuarioDAO.listarTodos();

            System.out.println("REGISTRAR USUARIO");

            System.out.print("Ingrese el nombre: ");
            String nombre = scanner.nextLine().trim();

            System.out.print("Ingrese el DNI: ");
            String dni = scanner.nextLine().trim();

            System.out.print("Ingrese la direccion: ");
            String direccion = scanner.nextLine().trim();

            System.out.print("Ingrese el contacto: ");
            String contacto = scanner.nextLine().trim();

            // NUEVO: validar campos obligatorios
            if (nombre.isEmpty() || dni.isEmpty() || direccion.isEmpty() || contacto.isEmpty()) {
                System.out.println("ERROR: Todos los campos son obligatorios.");
                return;
            }

            // NUEVO: comprobar que no haya otro usuario con el mismo DNI
            for (Usuario usuario : usuarios) {
                if (usuario.getDni().equals(dni)) {
                    System.out.println("ERROR: Ya existe un usuario con ese DNI.");
                    return;
                }
            }
            Usuario nuevoUsuario = new Usuario(nombre, dni, direccion, contacto);
            usuarioDAO.insertar(nuevoUsuario);
            System.out.println("Usuario registrado correctamente." +
                    "id: " + nuevoUsuario.getIdSocio());
        } catch (AccesoDatosException e) {
            System.out.println("ERROR de MySQL: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR en los datos ingresados" + e.getMessage());
        }
    }

    // NUEVO: modificar usuario existente
    private void modificarUsuario() {

        try {
            System.out.print("Ingrese el ID del usuario a modificar: ");

            if (!scanner.hasNextInt()) {
                System.out.println("ERROR: El ID debe ser numérico.");
                scanner.nextLine();
                return;
            }

            int id = scanner.nextInt();
            scanner.nextLine();

            // Buscamos el usuario en MySQL
            Usuario usuario = usuarioDAO.buscarPorId(id);

            System.out.println("Usuario encontrado: " + usuario.getNombre());

            System.out.print("Nuevo nombre: ");
            String nombre = scanner.nextLine().trim();

            System.out.print("Nueva dirección: ");
            String direccion = scanner.nextLine().trim();

            System.out.print("Nuevo contacto: ");
            String contacto = scanner.nextLine().trim();

            // NUEVO: validar datos
            if (nombre.isEmpty() || direccion.isEmpty() || contacto.isEmpty()) {
                System.out.println("ERROR: Los campos no pueden estar vacíos.");
                return;
            }

            // MODIFICADO: actualizar atributos del usuario
            usuario.setNombre(nombre);
            usuario.setDireccion(direccion);
            usuario.setContacto(contacto);

            // Guardar los cambios en MySQL
            usuarioDAO.actualizar(usuario);

            System.out.println("Usuario actualizado correctamente.");

        } catch (AccesoDatosException e) {
            System.out.println("ERROR de MySQL: " + e.getMessage());

        } catch (ElementoNoEncontradoException e) {
            System.out.println("Usuario no encontrado: " + e.getMessage());

        } catch (IllegalArgumentException e) {
            System.out.println("ERROR en los datos: " + e.getMessage());
        }
    }


    // NUEVO: eliminar usuario de MySQL
    private void eliminarUsuario() {

        try {
            System.out.println("\n===== ELIMINAR USUARIO =====");
            System.out.print("Ingrese el ID del usuario a eliminar: ");

            // NUEVO: validar que el ID sea numerico
            if (!scanner.hasNextInt()) {
                System.out.println("ERROR: El ID debe ser numerico.");
                scanner.nextLine();
                return;
            }

            int id = scanner.nextInt();
            scanner.nextLine();

            // NUEVO: recuperar usuario antes de eliminar
            Usuario usuario = usuarioDAO.buscarPorId(id);

            System.out.println("\nUsuario encontrado:");
            System.out.println("ID: " + usuario.getIdSocio());
            System.out.println("Nombre: " + usuario.getNombre());
            System.out.println("DNI: " + usuario.getDni());

            // NUEVO: confirmar eliminacion
            System.out.print("\n¿Seguro que desea eliminarlo? (S/N): ");
            String confirmacion = scanner.nextLine().trim();

            if (!confirmacion.equalsIgnoreCase("S")) {
                System.out.println("Eliminacion cancelada.");
                return;
            }

            // NUEVO: eliminar de MySQL
            usuarioDAO.eliminar(id);

            System.out.println("Usuario eliminado correctamente.");

        } catch (AccesoDatosException e) {
            System.out.println("ERROR de MySQL: " + e.getMessage());

        } catch (ElementoNoEncontradoException e) {
            System.out.println("Usuario no encontrado: " + e.getMessage());
        }
    }


    /// METODOS SUBMENU LIBROS

    // NUEVO: registrar un libro en MySQL
    private void registrarLibro() {

        try {
            System.out.println("\n===== REGISTRAR NUEVO LIBRO =====");

            // NUEVO: recuperar los libros existentes para
            // actualizar el contador estatico de Libro
            libroDAO.listarTodos();

            // NUEVO: solicitar los datos del libro
            System.out.print("Ingrese el nombre del libro: ");
            String nombre = scanner.nextLine().trim();

            System.out.print("Ingrese el autor: ");
            String autor = scanner.nextLine().trim();

            System.out.print("Ingrese el genero: ");
            String genero = scanner.nextLine().trim();

            // NUEVO: validar los campos obligatorios
            if (nombre.isEmpty() || autor.isEmpty() || genero.isEmpty()) {
                System.out.println("ERROR: Todos los campos son obligatorios.");
                return;
            }

            // NUEVO: comprobar si el libro ya esta registrado
            for (Libro libro : libroDAO.listarTodos()) {

                if (libro.getNombre().equalsIgnoreCase(nombre)
                        && libro.getAutor().equalsIgnoreCase(autor)) {

                    System.out.println(
                            "ERROR: Ya existe un libro con ese nombre y autor."
                    );
                    return;
                }
            }

            // NUEVO: crear libro con el ID generado en Java
            Libro nuevoLibro = new Libro(
                    nombre,
                    autor,
                    genero,
                    true,
                    EstadoLibro.DISPONIBLE
            );

            // NUEVO: guardar en MySQL
            libroDAO.insertar(nuevoLibro);

            System.out.println("\nLibro registrado correctamente.");
            System.out.println("ID asignado: " + nuevoLibro.getId());
            System.out.println("Nombre: " + nuevoLibro.getNombre());
            System.out.println("Autor: " + nuevoLibro.getAutor());

        } catch (AccesoDatosException e) {
            System.out.println("ERROR de MySQL: " + e.getMessage());

        } catch (IllegalArgumentException e) {
            System.out.println("ERROR en los datos: " + e.getMessage());
        }
    }


    // NUEVO: modificar un libro existente en MySQL
    private void modificarLibro() {

        try {
            System.out.println("\n===== MODIFICAR LIBRO =====");
            System.out.print("Ingrese el ID del libro a modificar: ");

            // NUEVO: validar ID numerico
            if (!scanner.hasNextInt()) {
                System.out.println("ERROR: El ID debe ser numerico.");
                scanner.nextLine();
                return;
            }

            int id = scanner.nextInt();
            scanner.nextLine();

            // NUEVO: buscar libro existente
            Libro libro = libroDAO.buscarPorId(id);

            System.out.println("\nLibro encontrado:");
            System.out.println("ID: " + libro.getId());
            System.out.println("Nombre: " + libro.getNombre());
            System.out.println("Autor: " + libro.getAutor());
            System.out.println("Genero: " + libro.getGenero());

            // NUEVO: solicitar los nuevos datos
            System.out.print("\nNuevo nombre: ");
            String nombre = scanner.nextLine().trim();

            System.out.print("Nuevo autor: ");
            String autor = scanner.nextLine().trim();

            System.out.print("Nuevo genero: ");
            String genero = scanner.nextLine().trim();

            // NUEVO: validar campos
            if (nombre.isEmpty() || autor.isEmpty() || genero.isEmpty()) {
                System.out.println("ERROR: Todos los campos son obligatorios.");
                return;
            }

            // MODIFICADO: actualizar los atributos en memoria
            libro.setNombre(nombre);
            libro.setAutor(autor);
            libro.setGenero(genero);

            // NUEVO: guardar los cambios en MySQL
            libroDAO.actualizar(libro);

            System.out.println("\nLibro actualizado correctamente.");
            System.out.println("ID: " + libro.getId());
            System.out.println("Nombre: " + libro.getNombre());

        } catch (AccesoDatosException e) {
            System.out.println("ERROR de MySQL: " + e.getMessage());

        } catch (ElementoNoEncontradoException e) {
            System.out.println("Libro no encontrado: " + e.getMessage());

        } catch (IllegalArgumentException e) {
            System.out.println("ERROR en los datos: " + e.getMessage());
        }
    }


    // NUEVO: eliminar libro de MySQL
    private void eliminarLibro() {

        try {
            System.out.println("\n===== ELIMINAR LIBRO =====");
            System.out.print("Ingrese el ID del libro a eliminar: ");

            // NUEVO: validar ID numerico
            if (!scanner.hasNextInt()) {
                System.out.println("ERROR: El ID debe ser numerico.");
                scanner.nextLine();
                return;
            }

            int id = scanner.nextInt();
            scanner.nextLine();

            // NUEVO: buscar el libro antes de eliminarlo
            Libro libro = libroDAO.buscarPorId(id);

            System.out.println("\nLibro encontrado:");
            System.out.println("ID: " + libro.getId());
            System.out.println("Nombre: " + libro.getNombre());
            System.out.println("Autor: " + libro.getAutor());

            // NUEVO: comprobar si tiene ejemplares asociados
            for (Ejemplar ejemplar : ejemplarDAO.listarTodos()) {
                if (ejemplar.getLibro().getId().equals(libro.getId())) {
                    System.out.println(
                            "\nERROR: No se puede eliminar este libro " +
                                    "porque tiene ejemplares registrados."
                    );
                    return;
                }
            }

            // NUEVO: solicitar confirmacion
            System.out.print("\n¿Seguro que desea eliminarlo? (S/N): ");
            String confirmacion = scanner.nextLine().trim();

            if (!confirmacion.equalsIgnoreCase("S")) {
                System.out.println("Eliminacion cancelada.");
                return;
            }

            // NUEVO: eliminar desde MySQL
            libroDAO.eliminar(id);

            System.out.println("\nLibro eliminado correctamente.");

        } catch (AccesoDatosException e) {
            System.out.println("ERROR de MySQL: " + e.getMessage());

        } catch (ElementoNoEncontradoException e) {
            System.out.println("Libro no encontrado: " + e.getMessage());
        }
    }


    /// METODOS EJEMPLARES LIBROS

    // NUEVO: registrar ejemplar
    private void registrarEjemplar() {

        try {
            System.out.println("\n===== REGISTRAR EJEMPLAR =====");

            // Actualizar el contador de IDs con los registros existentes
            ejemplarDAO.listarTodos();

            System.out.print("Ingrese el ID del libro: ");

            if (!scanner.hasNextInt()) {
                System.out.println("ERROR: El ID debe ser numerico.");
                scanner.nextLine();
                return;
            }

            int idLibro = scanner.nextInt();
            scanner.nextLine();

            // Buscar el libro en MySQL
            Libro libro = libroDAO.buscarPorId(idLibro);

            System.out.println("\nLibro encontrado:");
            System.out.println("ID: " + libro.getId());
            System.out.println("Nombre: " + libro.getNombre());
            System.out.println("Autor: " + libro.getAutor());

            System.out.print("\n¿Registrar ejemplar? (S/N): ");
            String confirmacion = scanner.nextLine().trim();

            if (!confirmacion.equalsIgnoreCase("S")) {
                System.out.println("Registro cancelado.");
                return;
            }

            // Crear el ejemplar
            Ejemplar nuevoEjemplar = new Ejemplar(libro);

            // Insertar en MySQL
            ejemplarDAO.insertar(nuevoEjemplar);

            System.out.println("\nEjemplar registrado correctamente.");
            System.out.println("ID: " + nuevoEjemplar.getId());
            System.out.println("Libro: " + nuevoEjemplar.getLibro().getNombre());
            System.out.println("Estado: " + nuevoEjemplar.getEstadoLibro());

        } catch (AccesoDatosException e) {
            System.out.println("ERROR de MySQL: " + e.getMessage());

        } catch (ElementoNoEncontradoException e) {
            System.out.println("Libro no encontrado: " + e.getMessage());

        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    // NUEVO: modificar estado de un ejemplar
    private void modificarEjemplar() {

        try {
            System.out.println("\n===== MODIFICAR EJEMPLAR =====");
            System.out.print("Ingrese el ID del ejemplar: ");

            if (!scanner.hasNextInt()) {
                System.out.println("ERROR: El ID debe ser numerico.");
                scanner.nextLine();
                return;
            }

            int id = scanner.nextInt();
            scanner.nextLine();

            // Buscar ejemplar en MySQL
            Ejemplar ejemplar = ejemplarDAO.buscarPorId(id);

            System.out.println("\nEjemplar encontrado:");
            System.out.println("ID: " + ejemplar.getId());
            System.out.println("Libro: " + ejemplar.getLibro().getNombre());
            System.out.println("Estado actual: " + ejemplar.getEstadoLibro());


            for (Prestamo prestamo : prestamoDAO.listarTodos()) {
                if (prestamo.getEjemplar().getId().equals(id)) {
                    System.out.println(
                            "ERROR: El ejemplar tiene prestamos asociados. " +
                                    "No se permite modificar su estado desde este menu."
                    );
                    return;
                }
            }

            System.out.println("\nEstados disponibles:");

            for (EstadoLibro estado : EstadoLibro.values()) {
                System.out.println("- " + estado);
            }

            System.out.print("Ingrese el nuevo estado: ");
            String estadoIngresado = scanner.nextLine()
                    .trim()
                    .toUpperCase();

            EstadoLibro nuevoEstado = EstadoLibro.valueOf(estadoIngresado);

            // Evitar marcar prestamos manualmente
            if (nuevoEstado == EstadoLibro.PRESTADO) {
                System.out.println(
                        "ERROR: Para prestar un ejemplar utilice el menu de prestamos."
                );
                return;
            }

            // Actualizar objeto
            ejemplar.setEstadoLibro(nuevoEstado);

            // Guardar en MySQL
            ejemplarDAO.actualizar(ejemplar);

            System.out.println("\nEjemplar actualizado correctamente.");
            System.out.println("ID: " + ejemplar.getId());
            System.out.println("Nuevo estado: " + ejemplar.getEstadoLibro());

        } catch (AccesoDatosException e) {
            System.out.println("ERROR de MySQL: " + e.getMessage());

        } catch (ElementoNoEncontradoException e) {
            System.out.println("Ejemplar no encontrado: " + e.getMessage());

        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: Estado invalido.");
        }
    }

    // NUEVO: eliminar ejemplar de MySQL
    private void eliminarEjemplar() {

        try {
            System.out.println("\n===== ELIMINAR EJEMPLAR =====");
            System.out.print("Ingrese el ID del ejemplar a eliminar: ");

            if (!scanner.hasNextInt()) {
                System.out.println("ERROR: El ID debe ser numerico.");
                scanner.nextLine();
                return;
            }

            int id = scanner.nextInt();
            scanner.nextLine();
            Ejemplar ejemplar = ejemplarDAO.buscarPorId(id);


            System.out.println("\nEjemplar encontrado:");
            System.out.println("ID: " + ejemplar.getId());
            System.out.println("Libro: " + ejemplar.getLibro().getNombre());
            System.out.println("Estado: " + ejemplar.getEstadoLibro());

            // NUEVO: comprobar prestamos asociados
            PrestamoDAO prestamoDAO = new PrestamoDAO();

            for (Prestamo prestamo : prestamoDAO.listarTodos()) {
                if (prestamo.getEjemplar().getId().equals(id)) {
                    System.out.println(
                            "\nERROR: No se puede eliminar el ejemplar " +
                                    "porque tiene prestamos registrados."
                    );
                    return;
                }
            }

            // Confirmar eliminacion
            System.out.print("\n¿Seguro que desea eliminarlo? (S/N): ");
            String confirmacion = scanner.nextLine().trim();

            if (!confirmacion.equalsIgnoreCase("S")) {
                System.out.println("Eliminacion cancelada.");
                return;
            }

            // Eliminar de MySQL
            ejemplarDAO.eliminar(id);

            System.out.println("\nEjemplar eliminado correctamente.");

        } catch (AccesoDatosException e) {
            System.out.println("ERROR de MySQL: " + e.getMessage());

        } catch (ElementoNoEncontradoException e) {
            System.out.println("Ejemplar no encontrado: " + e.getMessage());
        }
    }

    // NUEVO: listar todos los ejemplares de un libro
    private void listarEjemplaresPorLibro() {

        try {
            System.out.println("\n===== EJEMPLARES POR LIBRO =====");
            System.out.print("Ingrese el ID del libro: ");

            if (!scanner.hasNextInt()) {
                System.out.println("ERROR: El ID debe ser numerico.");
                scanner.nextLine();
                return;
            }

            int idLibro = scanner.nextInt();
            scanner.nextLine();

            // NUEVO: verificar que el libro exista
            Libro libro = libroDAO.buscarPorId(idLibro);

            System.out.println("\nLibro: " + libro.getNombre());
            System.out.println("Autor: " + libro.getAutor());
            System.out.println("\n--- EJEMPLARES ---");

            int cantidad = 0;
            int disponibles = 0;

            // NUEVO: filtrar los ejemplares por ID del libro
            for (Ejemplar ejemplar : ejemplarDAO.listarTodos()) {

                if (ejemplar.getLibro().getId().equals(idLibro)) {

                    System.out.println(
                            "ID Ejemplar: " + ejemplar.getId() +
                                    " | Estado: " + ejemplar.getEstadoLibro()
                    );

                    cantidad++;

                    if (ejemplar.getEstadoLibro() == EstadoLibro.DISPONIBLE) {
                        disponibles++;
                    }
                }
            }

            if (cantidad == 0) {
                System.out.println("Este libro no tiene ejemplares registrados.");
            } else {
                System.out.println("\nTotal de ejemplares: " + cantidad);
                System.out.println("Disponibles: " + disponibles);
                System.out.println("No disponibles: " + (cantidad - disponibles));
            }

        } catch (AccesoDatosException e) {
            System.out.println("ERROR de MySQL: " + e.getMessage());

        } catch (ElementoNoEncontradoException e) {
            System.out.println("Libro no encontrado: " + e.getMessage());
        }
    }


    /// METODOS SUBMENU PRESTAMOS


    // MODIFICADO: el menu no ejecuta SQL
    private void registrarPrestamo() {

        try {
            System.out.println("\n===== REGISTRAR PRESTAMO =====");

            System.out.print("Ingrese ID del usuario: ");
            if (!scanner.hasNextInt()) {
                System.out.println("ERROR: ID invalido.");
                scanner.nextLine();
                return;
            }

            int idUsuario = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Ingrese ID del ejemplar: ");
            if (!scanner.hasNextInt()) {
                System.out.println("ERROR: ID invalido.");
                scanner.nextLine();
                return;
            }

            int idEjemplar = scanner.nextInt();
            scanner.nextLine();

            // Consultar datos para mostrarlos
            Usuario usuario = usuarioDAO.buscarPorId(idUsuario);
            Ejemplar ejemplar = ejemplarDAO.buscarPorId(idEjemplar);

            System.out.println("Usuario: " + usuario.getNombre());
            System.out.println("Libro: " + ejemplar.getLibro().getNombre());
            System.out.println("Estado: " + ejemplar.getEstadoLibro());

            if (ejemplar.getEstadoLibro() != EstadoLibro.DISPONIBLE) {
                System.out.println("El ejemplar no esta disponible.");
                return;
            }

            System.out.print("Confirmar prestamo (S/N): ");

            if (!scanner.nextLine().trim().equalsIgnoreCase("S")) {
                System.out.println("Operacion cancelada.");
                return;
            }

            // MODIFICADO: ahora el DAO hace toda la transaccion
            int idPrestamo = prestamoDAO.registrarPrestamoSeguro(
                    idUsuario, idEjemplar
            );

            System.out.println("\nPrestamo registrado correctamente.");
            System.out.println("ID asignado: " + idPrestamo);

        } catch (AccesoDatosException e) {
            System.out.println("ERROR de MySQL: " + e.getMessage());

        } catch (ElementoNoEncontradoException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }


    // MODIFICADO: devolver ejemplar mediante el DAO
    private void registrarDevolucion() {

        try {
            System.out.println("\n===== REGISTRAR DEVOLUCION =====");
            System.out.print("Ingrese ID del prestamo: ");

            if (!scanner.hasNextInt()) {
                System.out.println("ERROR: ID invalido.");
                scanner.nextLine();
                return;
            }

            int idPrestamo = scanner.nextInt();
            scanner.nextLine();

            // NUEVO: buscar el prestamo para mostrar sus datos
            Prestamo prestamo = prestamoDAO.buscarPorId(idPrestamo);

            System.out.println("Prestamo: " + prestamo.getIdPrestamo());
            System.out.println("Usuario: " + prestamo.getUsuario().getNombre());
            System.out.println("Libro: " +
                    prestamo.getEjemplar().getLibro().getNombre());
            System.out.println("Estado: " + prestamo.getEstadoPrestamo());

            if (prestamo.getEstadoPrestamo() != EstadoPrestamo.ACTIVO) {
                System.out.println("ERROR: El prestamo no esta activo.");
                return;
            }

            System.out.print("Confirmar devolucion (S/N): ");

            if (!scanner.nextLine().trim().equalsIgnoreCase("S")) {
                System.out.println("Operacion cancelada.");
                return;
            }

            // MODIFICADO: el DAO actualiza las dos tablas
            prestamoDAO.registrarDevolucionSegura(idPrestamo);

            System.out.println("\nDevolucion registrada correctamente.");
            System.out.println("Prestamo: DEVUELTO");
            System.out.println("Ejemplar: DISPONIBLE");

        } catch (AccesoDatosException e) {
            System.out.println("ERROR de MySQL: " + e.getMessage());

        } catch (ElementoNoEncontradoException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    // NUEVO: mostrar prestamos desde MySQL


    // MODIFICADO: listar todos los prestamos
    private void listarPrestamos() {

        try {
            System.out.println("\n===== TODOS LOS PRESTAMOS =====");
            mostrarPrestamos(prestamoDAO.listarTodos());

        } catch (AccesoDatosException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    // MODIFICADO: buscar prestamo por ID
    private void buscarPrestamo() {

        try {
            System.out.println("\n===== BUSCAR PRESTAMO =====");
            System.out.print("Ingrese ID del prestamo: ");

            if (!scanner.hasNextInt()) {
                System.out.println("ERROR: ID invalido.");
                scanner.nextLine();
                return;
            }

            int id = scanner.nextInt();
            scanner.nextLine();

            Prestamo prestamo = prestamoDAO.buscarPorId(id);

            // Reutilizamos el metodo auxiliar
            mostrarPrestamos(List.of(prestamo));

        } catch (AccesoDatosException e) {
            System.out.println("ERROR: " + e.getMessage());

        } catch (ElementoNoEncontradoException e) {
            System.out.println("Prestamo no encontrado: " + e.getMessage());
        }
    }

    // MODIFICADO: listar prestamos activos
    private void listarPrestamosActivos() {

        try {
            System.out.println("\n===== PRESTAMOS ACTIVOS =====");
            mostrarPrestamos(prestamoDAO.listarActivos());

        } catch (AccesoDatosException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    // MODIFICADO: listar prestamos de un usuario
    private void listarPrestamosPorUsuario() {

        try {
            System.out.println("\n===== PRESTAMOS POR USUARIO =====");
            System.out.print("Ingrese ID del usuario: ");

            if (!scanner.hasNextInt()) {
                System.out.println("ERROR: ID invalido.");
                scanner.nextLine();
                return;
            }

            int idUsuario = scanner.nextInt();
            scanner.nextLine();

            Usuario usuario = usuarioDAO.buscarPorId(idUsuario);

            System.out.println("Usuario: " + usuario.getNombre());

            mostrarPrestamos(prestamoDAO.listarPorUsuario(idUsuario)
            );

        } catch (AccesoDatosException e) {
            System.out.println("ERROR: " + e.getMessage());

        } catch (ElementoNoEncontradoException e) {
            System.out.println("Usuario no encontrado: " + e.getMessage());
        }
    }


    // NUEVO: mostrar objetos Prestamo
    private void mostrarPrestamos(List<Prestamo> prestamos) {

        if (prestamos.isEmpty()) {
            System.out.println("No se encontraron prestamos.");
            return;
        }

        for (Prestamo prestamo : prestamos) {

            System.out.println("\n--------------------------");
            System.out.println("ID: " + prestamo.getIdPrestamo());
            System.out.println("Usuario: " +
                    prestamo.getUsuario().getNombre());
            System.out.println("Ejemplar: " +
                    prestamo.getEjemplar().getId());
            System.out.println("Libro: " +
                    prestamo.getEjemplar().getLibro().getNombre());
            System.out.println("Fecha: " + prestamo.getFechaPrestamo());
            System.out.println("Vencimiento: " +
                    prestamo.getFechaVencimiento());
            System.out.println("Devolucion: " +
                    prestamo.getFechaDevolucion());
            System.out.println("Estado: " +
                    prestamo.getEstadoPrestamo());
        }

        System.out.println("\nTotal: " + prestamos.size());
    }


}



