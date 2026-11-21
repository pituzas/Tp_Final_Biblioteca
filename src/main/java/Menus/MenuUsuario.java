package Menus;

import Clases_Libros.Ejemplar;
import Clases_Libros.Prestamo;
import Enum.EstadoPrestamo;
import Excepciones.AccesoDatosException;
import Excepciones.DatosInvalidosException;
import Excepciones.ElementoNoEncontradoException;
import dao.EjemplarDAO;
import dao.PrestamoDAO;
import dao.UsuarioDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuUsuario {
    private final Scanner scanner;
    private final EjemplarDAO ejemplarDAO;
    private final PrestamoDAO prestamoDAO;
    private final UsuarioDAO usuarioDAO;

    public MenuUsuario() {
        this.scanner = new Scanner(System.in);
        this.ejemplarDAO = new EjemplarDAO();
        this.prestamoDAO = new PrestamoDAO();
        this.usuarioDAO = new UsuarioDAO();
    }

    public void mostrarMenu() {
        int opcion = 0;
        do {
            System.out.println("\n--- MENÚ USUARIO ---");
            System.out.println("1. Consultar catálogo de libros disponibles");
            System.out.println("2. Ver mis préstamos activos");
            System.out.println("3. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());

                switch (opcion) {
                    case 1:
                        mostrarCatalogoDisponible();
                        break;
                    case 2:
                        mostrarPrestamosActivos();
                        break;
                    case 3:
                        System.out.println("Volviendo al menú principal...");
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
        } while (opcion != 3);
    }

    private void mostrarCatalogoDisponible() throws AccesoDatosException {
        List<Integer> librosMostrados = new ArrayList<>();
        System.out.println("\n--- LIBROS DISPONIBLES ---");

        for (Ejemplar ejemplar : ejemplarDAO.listarTodos()) {
            int idLibro = ejemplar.getLibro().getId();
            if (ejemplar.estaDispo() && !librosMostrados.contains(idLibro)) {
                librosMostrados.add(idLibro);
                System.out.println(ejemplar.getLibro());
            }
        }

        if (librosMostrados.isEmpty()) {
            System.out.println("No hay libros disponibles.");
        }
    }

    private void mostrarPrestamosActivos()
            throws AccesoDatosException, ElementoNoEncontradoException {
        System.out.print("Ingrese su ID de socio: ");
        int idSocio = Integer.parseInt(scanner.nextLine().trim());
        usuarioDAO.buscarPorId(idSocio);

        System.out.println("\n--- MIS PRÉSTAMOS ACTIVOS ---");
        boolean tienePrestamosActivos = false;
        for (Prestamo prestamo : prestamoDAO.listarTodos()) {
            if (prestamo.getUsuario().getIdSocio() == idSocio
                    && prestamo.getEstadoPrestamo() == EstadoPrestamo.ACTIVO) {
                System.out.println("ID: " + prestamo.getIdPrestamo()
                        + " | Libro: " + prestamo.getEjemplar().getLibro().getNombre()
                        + " | Préstamo: " + prestamo.getFechaPrestamo()
                        + " | Vencimiento: " + prestamo.getFechaVencimiento());
                tienePrestamosActivos = true;
            }
        }

        if (!tienePrestamosActivos) {
            System.out.println("No tenés préstamos activos.");
        }
    }
}
