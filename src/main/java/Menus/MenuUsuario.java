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
import Clases_Libros.Libro;
import dao.LibroDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuUsuario {
    private final Scanner scanner;
    private final EjemplarDAO ejemplarDAO;
    private final PrestamoDAO prestamoDAO;
    private final UsuarioDAO usuarioDAO;
    private final LibroDAO libroDAO;

    public MenuUsuario(Scanner scanner) {
        this.scanner = scanner;
        this.ejemplarDAO = new EjemplarDAO();
        this.prestamoDAO = new PrestamoDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.libroDAO = new LibroDAO();
    }

    public void mostrarMenu() {
        int opcion = 0;
        do {
            System.out.println("\n--- MENÚ USUARIO ---");
            System.out.println("1. Consultar catálogo de libros disponibles");
            System.out.println("2. Ver mis préstamos activos");
            System.out.println("3. Buscar libros por título");
            System.out.println("4. Buscar libros por autor");
            System.out.println("5. Buscar libros por género");
            System.out.println("6. Volver al menú principal");
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
                        buscarLibros("titulo");
                        break;

                    case 4:
                        buscarLibros("autor");
                        break;

                    case 5:
                        buscarLibros("genero");
                        break;
                    case 6:
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
        } while (opcion != 6);
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

    // NUEVO: buscar libros por titulo, autor o genero
    private void buscarLibros(String criterio) throws AccesoDatosException {

        System.out.println("\n===== BUSQUEDA DE LIBROS =====");
        System.out.print("Ingrese " + criterio + ": ");

        String texto = scanner.nextLine().trim();

        // Validar que se ingrese un texto
        if (texto.isBlank()) {
            System.out.println("ERROR: Debe ingresar un texto.");
            return;
        }

        List<Libro> resultados;

        // NUEVO: consultar segun el criterio seleccionado
        switch (criterio) {
            case "titulo":
                resultados = libroDAO.buscarPorTitulo(texto);
                break;

            case "autor":
                resultados = libroDAO.buscarPorAutor(texto);
                break;

            case "genero":
                resultados = libroDAO.buscarPorGenero(texto);
                break;

            default:
                System.out.println("ERROR: Criterio invalido.");
                return;
        }

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron libros.");
            return;
        }

        // NUEVO: recuperar los ejemplares una sola vez
        List<Ejemplar> ejemplares = ejemplarDAO.listarTodos();

        System.out.println("\n===== LIBROS ENCONTRADOS =====");

        for (Libro libro : resultados) {

            int disponibles = 0;

            for (Ejemplar ejemplar : ejemplares) {

                if (ejemplar.getLibro().getId().equals(libro.getId())
                        && ejemplar.estaDispo()) {
                    disponibles++;
                }
            }

            // Mostrar datos y disponibilidad
            System.out.println("-------------------------------");
            System.out.println("ID: " + libro.getId());
            System.out.println("Titulo: " + libro.getNombre());
            System.out.println("Autor: " + libro.getAutor());
            System.out.println("Genero: " + libro.getGenero());
            System.out.println("Ejemplares disponibles: " + disponibles);
        }

        System.out.println("-------------------------------");
        System.out.println("Total de libros encontrados: " + resultados.size());
    }

}
