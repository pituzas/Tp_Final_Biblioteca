package Menus;

import dao.AdministradorDAO;
import java.util.Scanner;

public class MenuAdmin {
    private Scanner scanner;
    private AdministradorDAO adminDAO;

    public MenuAdmin(Scanner scanner) {
        this.scanner = scanner;
        this.adminDAO = new AdministradorDAO();
    }

    public void mostrarMenu() {
        int opcion = 0;
        do {
            System.out.println("\n--- MENÚ ADMINISTRADOR ---");
            System.out.println("1. Gestionar Bibliotecarios (Altas/Bajas/Modificaciones)");
            System.out.println("2. Ver reportes del sistema");
            System.out.println("3. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        // Ejemplo de integración con DAO
                        System.out.println("\n[-- Gestión de Bibliotecarios --]");
                        break;
                    case 2:
                        System.out.println("\n[-- Reportes del Sistema --]");
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


