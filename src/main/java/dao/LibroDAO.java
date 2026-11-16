package dao;

import Clases_Libros.Libro;
import Excepciones.AccesoDatosException;
import Excepciones.ElementoNoEncontradoException;
import Interfaces.DAO;
import config.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT * from libros;";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(crearLibro(rs));
            }
        } catch (SQLException e) {
            throw new AccesoDatosException(e.getMessage());
        }
        return lista;
    }

    private Libro crearLibro(ResultSet rs) throws SQLException {
        UUID id = UUID.fromString(rs.getString("id"));
        String nombre = rs.getString("nombre");
        String autor = rs.getString("autor");
        String genero = rs.getString("genero");

        return new Libro(id, nombre, autor, genero);

    }

    @Override
    public Libro buscarPorId(String codigo) throws AccesoDatosException, ElementoNoEncontradoException {
        String sql = "SELECT * FROM libros WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return crearLibro(rs);
                }
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al buscar el libro" + e.getMessage());
        }
        throw new ElementoNoEncontradoException(codigo);
    }

    @Override
    public void actualizar(Libro libro) throws AccesoDatosException, ElementoNoEncontradoException {
        int filas;
        String sql = "UPDATE libros\n SET nombre = ?, autor = ?, genero = ?\n WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, libro.getNombre());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getGenero());
            ps.setString(4, libro.getId().toString());
            filas = ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al actualizar el libro" + e.getMessage());
        }
        if (filas == 0) {
            throw new ElementoNoEncontradoException(libro.getId().toString());
        }
    }

    @Override
    public void eliminar(String codigo) throws AccesoDatosException, ElementoNoEncontradoException {
        int filas;
        String sql = "DELETE FROM libros WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            filas = ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al eliminar el libro" + e.getMessage());
        }
        if (filas == 0) {
            throw new ElementoNoEncontradoException(codigo);
        }
    }
}