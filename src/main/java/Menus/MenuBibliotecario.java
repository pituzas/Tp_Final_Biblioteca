package Menus;

import dao.LibroDAO;
import dao.PrestamoDAO;
import dao.UsuarioDAO;

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
    private PrestamoDAO prestamoDAO;
    private UsuarioDAO usuarioDAO;
    private EjemplarDAO ejemplarDAO;

    /// Creamos un atributo de tipo EjemplarDAO

    public MenuBibliotecario() {
        this.scanner = new Scanner(System.in);
        this.libroDAO = new LibroDAO();
        this.prestamoDAO = new PrestamoDAO();
        this.usuarioDAO = new UsuarioDAO();

        this.ejemplarDAO = new EjemplarDAO(); ///Inicializamos el ejemoplarDAO
    }

    public void mostrarMenu() {
        int opcion = 0;
        do {
            System.out.println("\n--- MENÚ BIBLIOTECARIO ---");
            System.out.println("1. Gestionar Libros y Ejemplares");
            System.out.println("2. Registrar Préstamos y Devoluciones");
            System.out.println("3. Gestionar Usuarios");
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
                        sub_menuPrestamos();
                        break;
                    case 3:
                        sub_menuUsuarios();
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
        System.out.println("\n[Gestión de Préstamos conectada con PrestamoDAO]");
    }

    private void sub_menuUsuarios() {
        System.out.println("\n[Gestión de Usuarios conectada con UsuarioDAO]");
    }
}



