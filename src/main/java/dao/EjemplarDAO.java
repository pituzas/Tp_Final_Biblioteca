package dao;

import Clases_Libros.Ejemplar;
import Clases_Libros.Libro;
import Excepciones.AccesoDatosException;
import Excepciones.ElementoNoEncontradoException;
import Interfaces.DAO;
import Enum.EstadoLibro;
import config.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EjemplarDAO implements DAO<Ejemplar> {

    @Override
    public void insertar(Ejemplar elemento) throws AccesoDatosException {

        int filas = 0;
        String sql = "INSERT INTO ejemplares (id, libro_id, estado) VALUES (?, ?, ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, elemento.getId().toString());
            ps.setString(2, elemento.getLibro().getId().toString());
            ps.setString(3, elemento.getEstadoLibro().name());

            filas = ps.executeUpdate();
            System.out.println("Filas modificadas:" + filas);
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al insertar el ejemplar: " + e.getMessage());
        }
    }

    private Ejemplar crearEjemplar(ResultSet rs) throws SQLException, AccesoDatosException, ElementoNoEncontradoException {
        UUID id = UUID.fromString(rs.getString("id"));
        String libroId = rs.getString("libro_id");
        EstadoLibro estado = EstadoLibro.valueOf(rs.getString("estado"));

        LibroDAO libroDAO = new LibroDAO();
        Libro libro = libroDAO.buscarPorId(libroId);

        return new Ejemplar(id, libro, estado);
    }

    @Override
    public List<Ejemplar> listarTodos() throws AccesoDatosException {
        List<Ejemplar> lista = new ArrayList<>();
        String sql = "SELECT * FROM ejemplares";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(crearEjemplar(rs));
            }

        } catch (SQLException | ElementoNoEncontradoException e) {
            throw new AccesoDatosException(
                    "Error al listar ejemplares: " + e.getMessage()
            );
        }

        return lista;
    }

    @Override
    public Ejemplar buscarPorId(String id) throws AccesoDatosException, ElementoNoEncontradoException {
        String sql = "SELECT * FROM ejemplares WHERE id = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    try {
                        return crearEjemplar(rs);
                    } catch (ElementoNoEncontradoException e) {
                        throw new AccesoDatosException(
                                "El ejemplar tiene un libro inexistente: "
                                        + e.getMessage()
                        );
                    }
                }
            }

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "Error al buscar ejemplar: " + e.getMessage()
            );
        }

        throw new ElementoNoEncontradoException(id);
    }

    @Override
    public void actualizar(Ejemplar elemento) throws AccesoDatosException, ElementoNoEncontradoException {
        int filas;

        String sql = "UPDATE ejemplares SET libro_id = ?, estado = ? WHERE id = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, elemento.getLibro().getId().toString());
            ps.setString(2, elemento.getEstadoLibro().name());
            ps.setString(3, elemento.getId().toString());

            filas = ps.executeUpdate();

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "Error al actualizar el ejemplar: " + e.getMessage()
            );
        }

        if (filas == 0) {
            throw new ElementoNoEncontradoException(
                    elemento.getId().toString()
            );
        }
    }

    @Override
    public void eliminar(String id) throws AccesoDatosException, ElementoNoEncontradoException {

        int filas;

        String sql = "DELETE FROM ejemplares WHERE id = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, id);

            filas = ps.executeUpdate();

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "Error al eliminar el ejemplar: " + e.getMessage()
            );
        }

        if (filas == 0) {
            throw new ElementoNoEncontradoException(id);
        }
    }
}
