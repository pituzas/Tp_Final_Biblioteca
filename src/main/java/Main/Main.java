package Main;

import Excepciones.ElementoNoEncontradoException;
import Menus.MenuBibliotecario;
import dao.LibroDAO;
import Clases_Libros.Libro;
import Excepciones.AccesoDatosException;
import dao.UsuarioDAO;
import dao.EjemplarDAO;
import dao.PrestamoDAO;

import Clases_Persona.Usuario;
import Clases_Libros.Ejemplar;
import Clases_Libros.Prestamo;
import dao.AdministradorDAO;
import dao.BibliotecarioDAO;
import Clases_Persona.Admin;
import Clases_Persona.Bibliotecario;

public class Main {
    public static void main(String[] args) {

//        LibroDAO libroDAO = new LibroDAO();
//
//        try {
//            // PRUEBA 1: listar libros desde MySQL
//            for (Libro libro : libroDAO.listarTodos()) {
//                System.out.println(
//                        "ID: " + libro.getId() +
//                                " | Nombre: " + libro.getNombre() +
//                                " | Autor: " + libro.getAutor()
//                );
//            }
//
//            // PRUEBA 2: buscar libro por ID
//            Libro libro = libroDAO.buscarPorId(1);
//            Libro libro1 = libroDAO.buscarPorId(4);
//            Libro libro2 = libroDAO.buscarPorId(3);
//            System.out.println("Libro encontrado: " + libro.getNombre());
//            System.out.println("Libro encontrado: " + libro1.getNombre());
//            System.out.println("Libro encontrado: " + libro2.getNombre());
//
//        } catch (AccesoDatosException e) {
//            System.out.println("Error de base de datos: " + e.getMessage());
//
//        } catch (Excepciones.ElementoNoEncontradoException e) {
//            System.out.println("Libro no encontrado: " + e.getMessage());
//        }
//
//
//        UsuarioDAO usuarioDAO = new UsuarioDAO();
//        EjemplarDAO ejemplarDAO = new EjemplarDAO();
//        PrestamoDAO prestamoDAO = new PrestamoDAO();
//
//        try {
//
//            // PRUEBA 3: LISTAR USUARIOS
//            System.out.println("\n--- USUARIOS ---");
//
//            for (Usuario usuario : usuarioDAO.listarTodos()) {
//                System.out.println(
//                        "ID: " + usuario.getIdSocio() +
//                                " | Nombre: " + usuario.getNombre()
//                );
//            }
//
//            // PRUEBA 4: LISTAR EJEMPLARES
//            System.out.println("\n--- EJEMPLARES ---");
//
//            for (Ejemplar ejemplar : ejemplarDAO.listarTodos()) {
//                System.out.println(
//                        "ID: " + ejemplar.getId() +
//                                " | Libro: " + ejemplar.getLibro().getNombre() +
//                                " | Estado: " + ejemplar.getEstadoLibro()
//                );
//            }
//
//            // PRUEBA 5: LISTAR PRESTAMOS
//            System.out.println("\n--- PRESTAMOS ---");
//
//            for (Prestamo prestamo : prestamoDAO.listarTodos()) {
//                System.out.println(
//                        "ID prestamo: " + prestamo.getIdPrestamo() +
//                                " | Usuario: " + prestamo.getUsuario().getNombre() +
//                                " | Libro: " + prestamo.getEjemplar().getLibro().getNombre() +
//                                " | Estado: " + prestamo.getEstadoPrestamo()
//                );
//            }
//
//        } catch (AccesoDatosException e) {
//            System.out.println("Error JDBC: " + e.getMessage());
//        }
//
//        // PRUEBA 6: ADMINISTRADORES
//        AdministradorDAO adminDAO = new AdministradorDAO();
//
//        try {
//            System.out.println("\n--- ADMINISTRADORES ---");
//
//            for (Admin admin : adminDAO.listarTodos()) {
//                System.out.println(
//                        "ID: " + admin.getIdAdmin() +
//                                " | Nombre: " + admin.getNombre()
//                );
//            }
//
//            // PRUEBA: BUSCAR ADMINISTRADOR POR ID
//            Admin admin = adminDAO.buscarPorId(1);
//            System.out.println("Administrador encontrado: " + admin.getNombre());
//
//        } catch (AccesoDatosException e) {
//            System.out.println("Error JDBC: " + e.getMessage());
//
//        } catch (Excepciones.ElementoNoEncontradoException e) {
//            System.out.println("Administrador no encontrado: " + e.getMessage());
//        }
//
//
//// PRUEBA 7: BIBLIOTECARIOS
//        BibliotecarioDAO bibliotecarioDAO = new BibliotecarioDAO();
//
//        try {
//            System.out.println("\n--- BIBLIOTECARIOS ---");
//
//            for (Bibliotecario bibliotecario : bibliotecarioDAO.listarTodos()) {
//                System.out.println(
//                        "Legajo: " + bibliotecario.getLegajo() +
//                                " | Nombre: " + bibliotecario.getNombre()
//                );
//            }
//
//            // PRUEBA: BUSCAR BIBLIOTECARIO POR LEGAJO
//            Bibliotecario bibliotecario = bibliotecarioDAO.buscarPorId(1);
//            System.out.println("Bibliotecario encontrado: " + bibliotecario.getNombre());
//
//        } catch (AccesoDatosException e) {
//            System.out.println("Error JDBC: " + e.getMessage());
//
//        } catch (Excepciones.ElementoNoEncontradoException e) {
//            System.out.println("Bibliotecario no encontrado: " + e.getMessage());
//        }
//
//        try {
//            System.out.println(
//                    "Usuario encontrado: " + usuarioDAO.buscarPorId(2).getNombre()
//            );
//
//            System.out.println(
//                    "Ejemplar encontrado: " +
//                            ejemplarDAO.buscarPorId(3).getLibro().getNombre()
//            );
//
//            System.out.println(
//                    "Préstamo encontrado: " +
//                            prestamoDAO.buscarPorId(1).getIdPrestamo()
//            );
//
//        } catch (AccesoDatosException | ElementoNoEncontradoException e)
//        {
//            System.out.println("ERROR: " + e.getMessage());
//        }

        MenuBibliotecario menuBibliotecario = new MenuBibliotecario();
        menuBibliotecario.mostrarMenu();
//
    }
}