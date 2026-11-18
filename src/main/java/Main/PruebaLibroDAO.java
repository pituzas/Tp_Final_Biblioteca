package Main;

import Clases_Libros.Libro;
import Excepciones.AccesoDatosException;
import dao.LibroDAO;

public class PruebaLibroDAO {

    public static void main(String[] args) {

        LibroDAO libroDAO = new LibroDAO();

        try {
            // Crear un libro de prueba
            Libro libro = new Libro(
                    "El Principito",
                    "Antoine de Saint-Exupéry",
                    "Novela"
            );

            // Guardarlo en MySQL
            libroDAO.insertar(libro);

            // Recuperar todos los libros
            for (Libro l : libroDAO.listarTodos()) {
                System.out.println(
                        l.getId() + " - " +
                                l.getNombre() + " - " +
                                l.getAutor()
                );
            }

        } catch (AccesoDatosException e) {
            System.out.println(
                    "Error JDBC: " + e.getMessage()
            );
        }
    }
}
