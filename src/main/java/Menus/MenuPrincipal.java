package Menus;

import java.util.Scanner;

public class MenuPrincipal {
    private Scanner scanner;

    public MenuPrincipal() {
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion = 0;
        do {
            System.out.println("\n========================================");
            System.out.println("   SISTEMA DE GESTIÓN DE BIBLIOTECA     ");
            System.out.println("========================================");
            System.out.println("1. Ingresar como Administrador");
            System.out.println("2. Ingresar como Bibliotecario");
            System.out.println("3. Ingresar como Usuario / Socio");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer

                switch (opcion) {
                    case 1:
                        MenuAdmin menuAdmin = new MenuAdmin(scanner);
                        menuAdmin.mostrarMenu();
                        break;
                    case 2:
                        MenuBibliotecario menuBiblio = new MenuBibliotecario(scanner);
                        menuBiblio.mostrarMenu();
                        break;
                    case 3:
                        MenuUsuario menuUsuario = new MenuUsuario(scanner);
                        menuUsuario.mostrarMenu();
                        break;
                    case 4:
                        System.out.println("\n¡Gracias por utilizar el sistema! Saliendo...");
                        break;
                    default:
                        System.out.println("\n[!] Opción inválida. Intente de nuevo.");
                }
            } else {
                System.out.println("\n[!] Error: Debe ingresar un número válido.");
                scanner.nextLine();
            }

        } while (opcion != 4);
    }
}

