package Main;

import Clases_Libros.Libro;
import Clases_Persona.Usuario;
import Excepciones.AccesoDatosException;
import dao.LibroDAO;
import dao.UsuarioDAO;

public class PruebaLibroDAO {

    public static void main(String[] args) {

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        try {
            Usuario usuario = new Usuario("Lucio", "46206064", "Falucho 3029", "luciolive9@gmail.ocm", "1");
            usuarioDAO.insertar(usuario);
        } catch (AccesoDatosException e) {
            System.out.println("ERROR:" + e.getMessage()); ///CORREGIR CATCH
        }

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
