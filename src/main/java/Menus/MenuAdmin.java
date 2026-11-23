package Menus;

import Clases_Persona.Bibliotecario;
import Clases_Persona.Usuario;
import Excepciones.AccesoDatosException;
import Excepciones.DatosInvalidosException;
import Excepciones.ElementoNoEncontradoException;
import dao.BibliotecarioDAO;
import dao.UsuarioDAO;

import java.util.List;
import java.util.Scanner;

public class MenuAdmin {
    private final Scanner scanner;
    private final BibliotecarioDAO bibliotecarioDAO;
    private final UsuarioDAO usuarioDAO;

    public MenuAdmin(Scanner scanner) {
        this.scanner = scanner;
        this.bibliotecarioDAO = new BibliotecarioDAO();
        this.usuarioDAO = new UsuarioDAO();
    }

    public void mostrarMenu() {
        int opcion = 0;
        do {
            System.out.println("\n--- MENÚ ADMINISTRADOR ---");
            System.out.println("1. Gestionar bibliotecarios");
            System.out.println("2. Ver informe de usuarios");
            System.out.println("3. Volver al menú principal");

            try {
                opcion = leerNumero("Seleccione una opción: ");

                switch (opcion) {
                    case 1:
                        gestionarBibliotecarios();
                        break;
                    case 2:
                        mostrarInformeUsuarios();
                        break;
                    case 3:
                        System.out.println("Volviendo al menú principal...");
                        break;
                    default:
                        System.out.println("\n[!] Opción inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n[!] Ingrese un número válido.");
            } catch (AccesoDatosException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        } while (opcion != 3);
    }

    private void gestionarBibliotecarios() {
        int opcion = -1;
        do {
            System.out.println("\n--- GESTIÓN DE BIBLIOTECARIOS ---");
            System.out.println("1. Listar bibliotecarios");
            System.out.println("2. Dar de alta");
            System.out.println("3. Modificar");
            System.out.println("4. Dar de baja");
            System.out.println("0. Volver al menú administrador");

            try {
                opcion = leerNumero("Seleccione una opción: ");

                switch (opcion) {
                    case 1:
                        listarBibliotecarios();
                        break;
                    case 2:
                        agregarBibliotecario();
                        break;
                    case 3:
                        modificarBibliotecario();
                        break;
                    case 4:
                        eliminarBibliotecario();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("\n[!] Opción inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n[!] Ingrese un número válido.");
            } catch (ElementoNoEncontradoException e) {
                System.out.println("No encontrado: " + e.getMessage());
            } catch (AccesoDatosException | DatosInvalidosException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void listarBibliotecarios() throws AccesoDatosException {
        List<Bibliotecario> bibliotecarios = bibliotecarioDAO.listarTodos();
        if (bibliotecarios.isEmpty()) {
            System.out.println("No hay bibliotecarios registrados.");
        }

        for (Bibliotecario bibliotecario : bibliotecarios) {
            System.out.println("Legajo: " + bibliotecario.getLegajo()
                    + " | Nombre: " + bibliotecario.getNombre()
                    + " | DNI: " + bibliotecario.getDni()
                    + " | Dirección: " + bibliotecario.getDireccion()
                    + " | Contacto: " + bibliotecario.getContacto());
        }
    }

    private void agregarBibliotecario() throws AccesoDatosException {
        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.nextLine().trim();

        if (nombre.isBlank() ||
                !nombre.matches("[\\p{L} ]+")) {

            throw new DatosInvalidosException(
                    "El nombre solo puede contener letras y espacios."
            );
        }

        System.out.print("Ingrese el DNI: ");
        String dni = scanner.nextLine().trim();

        if (!dni.matches("\\d{7,8}")) {
            throw new DatosInvalidosException(
                    "El DNI debe contener entre 7 y 8 numeros."
            );
        }

        for (Bibliotecario b : bibliotecarioDAO.listarTodos()) {
            if (b.getDni().equals(dni)) {
                throw new DatosInvalidosException("Ya existe un bibiliotecario con el dni:" + dni);
            }
        }

        System.out.print("Ingrese la direccion: ");
        String direccion = scanner.nextLine().trim();

        if (!direccion.matches("[\\p{L}0-9 .º°#/-]+")
                || direccion.isBlank()) {
            throw new DatosInvalidosException("Ingrese una direccion valida, por ejemplo: Av. Colon 1500");
        }


        System.out.print("Ingrese el contacto: ");
        String contacto = scanner.nextLine().trim();

        if (!contacto.matches("[0-9+() -]{6,25}")
                || contacto.replaceAll("\\D", "").length() < 6) {

            throw new DatosInvalidosException(
                    "El contacto debe ser un telefono valido."
            );
        }


        // Al recuperar los registros, el constructor actualiza el contador de legajos.
        bibliotecarioDAO.listarTodos();
        Bibliotecario bibliotecario = new Bibliotecario(nombre, dni, direccion, contacto);
        bibliotecarioDAO.insertar(bibliotecario);
        System.out.println("Bibliotecario registrado. Legajo: " + bibliotecario.getLegajo());
    }

    private void modificarBibliotecario()
            throws AccesoDatosException, ElementoNoEncontradoException {
        int legajo = leerNumero("Ingrese el legajo: ");
        ///NUEVO: VALIDACIONES PARA QUE NO SE PUEDAN INGRESAR NUMEROS O LETRAS.
        Bibliotecario bibliotecario = bibliotecarioDAO.buscarPorId(legajo);
        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.nextLine().trim();

        if (nombre.isBlank() ||
                !nombre.matches("[\\p{L} ]+")) {

            throw new DatosInvalidosException(
                    "El nombre solo puede contener letras y espacios."
            );
        }


        System.out.print("Ingrese el DNI: ");
        String dni = scanner.nextLine().trim();
        if (!dni.matches("\\d{7,8}")) {
            throw new DatosInvalidosException(
                    "El DNI debe contener entre 7 y 8 numeros."
            );
        }

        for (Bibliotecario b : bibliotecarioDAO.listarTodos()) {
            if (b.getDni().equals(dni)
                    && b.getLegajo() != legajo) {

                throw new DatosInvalidosException("Otro bibliotecario ya tiene ese el dni:" + dni);
            }
        }


        System.out.print("Ingrese la direccion: ");
        String direccion = scanner.nextLine().trim();
        if (!direccion.matches("[\\p{L}0-9 .º°#/-]+") || direccion.isBlank()) {
            throw new DatosInvalidosException("Ingrese una direccion valida, por ejemplo: Av. Colon 1500");
        }


        System.out.print("Ingrese el contacto: ");
        String contacto = scanner.nextLine().trim();
        if (!contacto.matches("[0-9+() -]{6,25}")
                || contacto.replaceAll("\\D", "").length() < 6) {

            throw new DatosInvalidosException(
                    "El contacto debe ser un telefono valido."
            );
        }

        bibliotecario.setNombre(nombre);
        bibliotecario.setDni(dni);
        bibliotecario.setDireccion(direccion);
        bibliotecario.setContacto(contacto);

        bibliotecarioDAO.actualizar(bibliotecario);
        System.out.println("Bibliotecario actualizado.");
    }

    private void eliminarBibliotecario()
            throws AccesoDatosException, ElementoNoEncontradoException {
        int legajo = leerNumero("Ingrese el legajo: ");
        bibliotecarioDAO.eliminar(legajo);
        System.out.println("Bibliotecario dado de baja.");
    }

    private void mostrarInformeUsuarios() throws AccesoDatosException {
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        System.out.println("\n--- INFORME DE USUARIOS ---");

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        }

        for (Usuario usuario : usuarios) {
            System.out.println("Nombre: " + usuario.getNombre()
                    + " | DNI: " + usuario.getDni()
                    + " | Dirección: " + usuario.getDireccion()
                    + " | Contacto: " + usuario.getContacto());
        }
    }

    private int leerNumero(String mensaje) {
        System.out.print(mensaje);
        return Integer.parseInt(scanner.nextLine().trim());

    }
}
