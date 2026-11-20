package Menus;

import dao.LibroDAO;
import dao.PrestamoDAO;
import dao.UsuarioDAO;
import java.util.Scanner;

public class MenuBibliotecario {
    private Scanner scanner;
    private LibroDAO libroDAO;
    private PrestamoDAO prestamoDAO;
    private UsuarioDAO usuarioDAO;

    public MenuBibliotecario(Scanner scanner) {
        this.scanner = scanner;
        this.libroDAO = new LibroDAO();
        this.prestamoDAO = new PrestamoDAO();
        this.usuarioDAO = new UsuarioDAO();
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
    }

    private void sub_menuPrestamos() {
        System.out.println("\n[Gestión de Préstamos conectada con PrestamoDAO]");
    }

    private void sub_menuUsuarios() {
        System.out.println("\n[Gestión de Usuarios conectada con UsuarioDAO]");
    }
}


