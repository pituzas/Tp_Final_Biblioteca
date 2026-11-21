package Menus;



import Clases_Persona.Usuario;
import Excepciones.AccesoDatosException;
import dao.LibroDAO;
import dao.UsuarioDAO;

import java.util.Scanner;

public class MenuUsuario {
    private Scanner scanner;
    private LibroDAO libroDAO;
    private UsuarioDAO usuarioDAO;


    public MenuUsuario() {
        this.scanner = new Scanner(System.in);
        this.libroDAO = new LibroDAO();
    }

    public void mostrarMenu() {
        int opcion = 0;
        do {
            System.out.println("\n--- MENÚ USUARIO ---");
            System.out.println("1. Consultar catálogo de libros disponibles");
            System.out.println("2. Ver mis préstamos activos");
            System.out.println("3. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        try {
                            System.out.println("\n[-- Listado de libros desde la base de datos --]");
                            libroDAO.listarTodos();
                        } catch (AccesoDatosException e) {
                            System.out.println("ERROR: " + e.getMessage());
                        }
                        break;
                    case 2:
                        System.out.println("\n[-- Consulta de préstamos por usuario --]");




                        break;
                    case 3:
                        System.out.println("Volviendo al menú principal...");
                        break;
                    default:
                        System.out.println("\n[!] Opción inválida.");
                }
            } else {
                System.out.println("\n[!] Ingrese un número válido.");
                scanner.nextLine();
            }
        } while (opcion != 3);
    }
}
