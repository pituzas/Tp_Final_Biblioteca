package dao;

import Clases_Libros.Libro;
import Excepciones.AccesoDatosException;
import Excepciones.ElementoNoEncontradoException;
import Interfaces.DAO;
import config.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class LibroDAO implements DAO<Libro> {

    @Override
    public void insertar(Libro libro) throws AccesoDatosException {

        int filas = 0;

        String sql = """
                INSERT INTO libros (id, nombre, autor, genero)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, libro.getId().toString());
            ps.setString(2, libro.getNombre());
            ps.setString(3, libro.getAutor());
            ps.setString(4, libro.getGenero());

            filas = ps.executeUpdate();

            System.out.println("Filas modificadas: " + filas);

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "Error al insertar el libro: " + e.getMessage()
            );
        }
    }

    @Override
    public List<Libro> listarTodos() throws AccesoDatosException {
        return List.of();
    }

    @Override
    public Libro buscarPorId(String codigo)
            throws AccesoDatosException, ElementoNoEncontradoException {

        return null;
    }

    @Override
    public void actualizar(Libro libro)
            throws AccesoDatosException, ElementoNoEncontradoException {

    }

    @Override
    public void eliminar(String codigo)
            throws AccesoDatosException, ElementoNoEncontradoException {

    }
}