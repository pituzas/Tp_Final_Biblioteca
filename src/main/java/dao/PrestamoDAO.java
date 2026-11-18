package dao;

import Clases_Libros.Ejemplar;
import Clases_Libros.Prestamo;
import Clases_Persona.Usuario;
import Enum.EstadoPrestamo;
import Excepciones.AccesoDatosException;
import Excepciones.ElementoNoEncontradoException;
import Interfaces.DAO;
import config.ConexionDB;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PrestamoDAO implements DAO<Prestamo> {
    @Override
    public void insertar(Prestamo elemento) throws AccesoDatosException {
        int filas = 0;
        String sql = "INSERT INTO prestamos(idPrestamo, ejemplar, usuario, fechaPrestamo,fechaVencimiento, fechaDevolucion, estadoPrestamo)VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, elemento.getIdPrestamo().toString());
            ps.setString(2, elemento.getEjemplar().getId().toString());
            ps.setString(3, elemento.getUsuario().getIdSocio());

            ps.setTimestamp(4, Timestamp.valueOf(elemento.getFechaPrestamo()));
            ps.setTimestamp(5, Timestamp.valueOf(elemento.getFechaVencimiento()));

            if (elemento.getFechaDevolucion() == null) {
                ps.setNull(6, Types.TIMESTAMP);
            } else {
                ps.setTimestamp(6,
                        Timestamp.valueOf(elemento.getFechaDevolucion()));
            }

            ps.setString(7, elemento.getEstadoPrestamo().name());

            filas = ps.executeUpdate();
            System.out.println("Filas modificadas: " + filas);


        } catch (SQLException e) {
            throw new AccesoDatosException(e.getMessage());
        }
    }

    @Override
    public List<Prestamo> listarTodos() throws AccesoDatosException {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT * FROM prestamos ORDER BY idPrestamo";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(crearPrestamo(rs));
            }
        } catch (SQLException e) {
            throw new AccesoDatosException(e.getMessage());
        }
        return lista;
    }


    @Override
    public Prestamo buscarPorId(String id) throws AccesoDatosException, ElementoNoEncontradoException {

        String sql = "SELECT * FROM prestamos WHERE idPrestamo = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return crearPrestamo(rs);
                }
            }

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "Error al buscar el préstamo: " + e.getMessage()
            );
        }

        throw new ElementoNoEncontradoException(id);
    }


    @Override
    public void actualizar(Prestamo elemento) throws AccesoDatosException, ElementoNoEncontradoException {

        int filas;

        String sql = """
                UPDATE prestamos
                SET ejemplar = ?, usuario = ?, fechaPrestamo = ?,
                    fechaVencimiento = ?, fechaDevolucion = ?,
                    estadoPrestamo = ?
                WHERE idPrestamo = ?
                """;

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, elemento.getEjemplar().getId().toString());
            ps.setString(2, elemento.getUsuario().getIdSocio());

            ps.setTimestamp(3,
                    Timestamp.valueOf(elemento.getFechaPrestamo()));

            ps.setTimestamp(4,
                    Timestamp.valueOf(elemento.getFechaVencimiento()));

            if (elemento.getFechaDevolucion() == null) {
                ps.setNull(5, Types.TIMESTAMP);
            } else {
                ps.setTimestamp(5,
                        Timestamp.valueOf(elemento.getFechaDevolucion()));
            }

            ps.setString(6, elemento.getEstadoPrestamo().name());
            ps.setString(7, elemento.getIdPrestamo().toString());

            filas = ps.executeUpdate();

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "Error al actualizar préstamo: " + e.getMessage()
            );
        }

        if (filas == 0) {
            throw new ElementoNoEncontradoException(
                    elemento.getIdPrestamo().toString()
            );
        }
    }


    @Override
    public void eliminar(String id) throws AccesoDatosException, ElementoNoEncontradoException {
        int filas;
        String sql = "DELETE FROM prestamos WHERE idPrestamo = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            filas = ps.executeUpdate();

        } catch (SQLException e) {
            throw new AccesoDatosException(e.getMessage());

        }
        if (filas == 0) {
            throw new ElementoNoEncontradoException(id);
        }

    }


    private Prestamo crearPrestamo(ResultSet rs) throws SQLException, AccesoDatosException {

        UUID idPrestamo = UUID.fromString(
                rs.getString("idPrestamo")
        );

        String idEjemplar = rs.getString("ejemplar");
        String idUsuario = rs.getString("usuario");

        LocalDateTime fechaPrestamo = rs
                .getTimestamp("fechaPrestamo")
                .toLocalDateTime();

        LocalDateTime fechaVencimiento = rs
                .getTimestamp("fechaVencimiento")
                .toLocalDateTime();

        Timestamp timestampDevolucion =
                rs.getTimestamp("fechaDevolucion");

        LocalDateTime fechaDevolucion =
                timestampDevolucion == null
                        ? null
                        : timestampDevolucion.toLocalDateTime();

        EstadoPrestamo estadoPrestamo = EstadoPrestamo.valueOf(
                rs.getString("estadoPrestamo")
        );

        EjemplarDAO ejemplarDAO = new EjemplarDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        try {
            Ejemplar ejemplar = ejemplarDAO.buscarPorId(idEjemplar);
            Usuario usuario = usuarioDAO.buscarPorId(idUsuario);

            return new Prestamo(idPrestamo, ejemplar, usuario, fechaPrestamo, fechaVencimiento, fechaDevolucion, estadoPrestamo);

        } catch (ElementoNoEncontradoException e) {
            throw new AccesoDatosException(
                    "Error al reconstruir préstamo: " + e.getMessage()
            );
        }
    }

}
