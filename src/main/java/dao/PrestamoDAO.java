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


public class PrestamoDAO implements DAO<Prestamo> {
    @Override
    public void insertar(Prestamo elemento) throws AccesoDatosException {

        int filas = 0;

        String sql = "INSERT INTO prestamos(idPrestamo, ejemplar, usuario, fechaPrestamo, fechaVencimiento, fechaDevolucion, estadoPrestamo) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // MODIFICADO: antes ps.setString() con UUID
            ps.setInt(1, elemento.getIdPrestamo());

            // MODIFICADO: ID del ejemplar ahora es Integer
            ps.setInt(2, elemento.getEjemplar().getId());

            // MODIFICADO: ID del usuario ahora es Integer
            ps.setInt(3, elemento.getUsuario().getIdSocio());

            // SIN CAMBIOS: fechas
            ps.setTimestamp(4,
                    Timestamp.valueOf(elemento.getFechaPrestamo()));

            ps.setTimestamp(5,
                    Timestamp.valueOf(elemento.getFechaVencimiento()));

            if (elemento.getFechaDevolucion() == null) {
                ps.setNull(6, Types.TIMESTAMP);
            } else {
                ps.setTimestamp(6,
                        Timestamp.valueOf(elemento.getFechaDevolucion()));
            }

            // SIN CAMBIOS: estado del prestamo
            ps.setString(7, elemento.getEstadoPrestamo().name());

            filas = ps.executeUpdate();
            System.out.println("Filas modificadas: " + filas);

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "Error al insertar el préstamo: " + e.getMessage()
            );
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
    public Prestamo buscarPorId(int id)
            throws AccesoDatosException, ElementoNoEncontradoException {

        String sql = "SELECT * FROM prestamos WHERE idPrestamo = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // MODIFICADO: antes ps.setString(1, id)
            ps.setInt(1, id);

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
    public void actualizar(Prestamo elemento)
            throws AccesoDatosException, ElementoNoEncontradoException {

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

            // MODIFICADO: ID del ejemplar
            ps.setInt(1, elemento.getEjemplar().getId());

            // MODIFICADO: ID del usuario
            ps.setInt(2, elemento.getUsuario().getIdSocio());

            // SIN CAMBIOS: fechas
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

            // SIN CAMBIOS: estado del préstamo
            ps.setString(6, elemento.getEstadoPrestamo().name());

            // MODIFICADO: antes setString() con UUID
            ps.setInt(7, elemento.getIdPrestamo());

            filas = ps.executeUpdate();

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "Error al actualizar préstamo: " + e.getMessage()
            );
        }

        if (filas == 0) {
            throw new ElementoNoEncontradoException(elemento.getIdPrestamo() );
        }
    }

    @Override
    public void eliminar(int id)
            throws AccesoDatosException, ElementoNoEncontradoException {

        int filas;

        String sql = "DELETE FROM prestamos WHERE idPrestamo = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // MODIFICADO: antes setString()
            ps.setInt(1, id);

            filas = ps.executeUpdate();

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "Error al eliminar el préstamo: " + e.getMessage()
            );
        }

        if (filas == 0) {
            throw new ElementoNoEncontradoException(id);
        }
    }

    private Prestamo crearPrestamo(ResultSet rs)
            throws SQLException, AccesoDatosException {

        // MODIFICADO: antes UUID.fromString()
        Integer idPrestamo = rs.getInt("idPrestamo");

        // MODIFICADO: antes eran String
        Integer idEjemplar = rs.getInt("ejemplar");
        Integer idUsuario = rs.getInt("usuario");

        // SIN CAMBIOS: fechas
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

        // SIN CAMBIOS: enum
        EstadoPrestamo estadoPrestamo = EstadoPrestamo.valueOf(
                rs.getString("estadoPrestamo")
        );

        EjemplarDAO ejemplarDAO = new EjemplarDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        try {
            // MODIFICADO: conversión temporal a String
            Ejemplar ejemplar = ejemplarDAO.buscarPorId(idEjemplar);

            // MODIFICADO: conversión temporal a String
            Usuario usuario = usuarioDAO.buscarPorId(idUsuario);

            // MODIFICADO: constructor con Integer idPrestamo
            return new Prestamo(
                    idPrestamo,
                    ejemplar,
                    usuario,
                    fechaPrestamo,
                    fechaVencimiento,
                    fechaDevolucion,
                    estadoPrestamo
            );

        } catch (ElementoNoEncontradoException e) {
            throw new AccesoDatosException(
                    "Error al reconstruir préstamo: " + e.getMessage()
            );
        }
    }

    // NUEVO: listar solamente prestamos activos
    public List<Prestamo> listarActivos()
            throws AccesoDatosException {

        List<Prestamo> lista = new ArrayList<>();

        String sql = "SELECT * FROM prestamos " +
                "WHERE estadoPrestamo = 'ACTIVO' " +
                "ORDER BY fechaVencimiento";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(crearPrestamo(rs));
            }

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "Error al listar prestamos activos: " + e.getMessage()
            );
        }

        return lista;
    }

    // NUEVO: listar prestamos de un usuario
    public List<Prestamo> listarPorUsuario(int idUsuario)
            throws AccesoDatosException {

        List<Prestamo> lista = new ArrayList<>();

        String sql = "SELECT * FROM prestamos " +
                "WHERE usuario = ? ORDER BY fechaPrestamo DESC";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(crearPrestamo(rs));
                }
            }

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "Error al listar prestamos por usuario: " +
                            e.getMessage()
            );
        }

        return lista;
    }


    // NUEVO: registrar prestamo en una transaccion
    public int registrarPrestamoSeguro(int idUsuario, int idEjemplar)
            throws AccesoDatosException {

        try (Connection con = ConexionDB.getConexion()) {

            con.setAutoCommit(false);

            try {
                // Bloquear el ejemplar y comprobar su estado
                String sqlEjemplar =
                        "SELECT estado FROM ejemplares " +
                                "WHERE id = ? FOR UPDATE";

                try (PreparedStatement ps = con.prepareStatement(sqlEjemplar)) {
                    ps.setInt(1, idEjemplar);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Ejemplar inexistente.");
                        }

                        if (!"DISPONIBLE".equals(rs.getString("estado"))) {
                            throw new SQLException(
                                    "El ejemplar no esta disponible."
                            );
                        }
                    }
                }

                // Comprobar que el usuario exista
                String sqlUsuario =
                        "SELECT idSocio FROM usuarios WHERE idSocio = ?";

                try (PreparedStatement ps = con.prepareStatement(sqlUsuario)) {
                    ps.setInt(1, idUsuario);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Usuario inexistente.");
                        }
                    }
                }

                // Comprobar que no tenga otro prestamo activo
                String sqlActivo =
                        "SELECT COUNT(*) FROM prestamos " +
                                "WHERE ejemplar = ? AND estadoPrestamo = 'ACTIVO'";

                try (PreparedStatement ps = con.prepareStatement(sqlActivo)) {
                    ps.setInt(1, idEjemplar);

                    try (ResultSet rs = ps.executeQuery()) {
                        rs.next();

                        if (rs.getInt(1) > 0) {
                            throw new SQLException(
                                    "El ejemplar ya tiene un prestamo activo."
                            );
                        }
                    }
                }

                // Generar ID numerico
                int nuevoId;

                String sqlId =
                        "SELECT COALESCE(MAX(idPrestamo), 0) + 1 FROM prestamos";

                try (PreparedStatement ps = con.prepareStatement(sqlId);
                     ResultSet rs = ps.executeQuery()) {

                    rs.next();
                    nuevoId = rs.getInt(1);
                }

                LocalDateTime fecha = LocalDateTime.now();
                LocalDateTime vencimiento = fecha.plusDays(7);

                // Insertar prestamo
                String sqlInsert =
                        "INSERT INTO prestamos " +
                                "(idPrestamo, ejemplar, usuario, fechaPrestamo, " +
                                "fechaVencimiento, fechaDevolucion, estadoPrestamo) " +
                                "VALUES (?, ?, ?, ?, ?, NULL, 'ACTIVO')";

                try (PreparedStatement ps = con.prepareStatement(sqlInsert)) {

                    ps.setInt(1, nuevoId);
                    ps.setInt(2, idEjemplar);
                    ps.setInt(3, idUsuario);
                    ps.setTimestamp(4, Timestamp.valueOf(fecha));
                    ps.setTimestamp(5, Timestamp.valueOf(vencimiento));

                    if (ps.executeUpdate() != 1) {
                        throw new SQLException("No se inserto el prestamo.");
                    }
                }

                // Actualizar ejemplar
                String sqlUpdate =
                        "UPDATE ejemplares SET estado = 'PRESTADO' " +
                                "WHERE id = ? AND estado = 'DISPONIBLE'";

                try (PreparedStatement ps = con.prepareStatement(sqlUpdate)) {
                    ps.setInt(1, idEjemplar);

                    if (ps.executeUpdate() != 1) {
                        throw new SQLException(
                                "No se pudo actualizar el ejemplar."
                        );
                    }
                }

                con.commit();
                return nuevoId;

            } catch (SQLException e) {
                con.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "Error al registrar prestamo: " + e.getMessage()
            );
        }
    }


    // NUEVO: registrar devolucion en una transaccion
    public void registrarDevolucionSegura(int idPrestamo)
            throws AccesoDatosException {

        try (Connection con = ConexionDB.getConexion()) {

            con.setAutoCommit(false);

            try {
                int idEjemplar;

                // Buscar prestamo y bloquearlo
                String sqlBuscar =
                        "SELECT ejemplar, estadoPrestamo FROM prestamos " +
                                "WHERE idPrestamo = ? FOR UPDATE";

                try (PreparedStatement ps = con.prepareStatement(sqlBuscar)) {
                    ps.setInt(1, idPrestamo);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Prestamo inexistente.");
                        }

                        if (!"ACTIVO".equals(
                                rs.getString("estadoPrestamo"))) {
                            throw new SQLException(
                                    "El prestamo ya no esta activo."
                            );
                        }

                        idEjemplar = rs.getInt("ejemplar");
                    }
                }

                // Actualizar prestamo
                String sqlPrestamo =
                        "UPDATE prestamos SET estadoPrestamo = 'DEVUELTO', " +
                                "fechaDevolucion = NOW() " +
                                "WHERE idPrestamo = ? AND estadoPrestamo = 'ACTIVO'";

                try (PreparedStatement ps = con.prepareStatement(sqlPrestamo)) {
                    ps.setInt(1, idPrestamo);

                    if (ps.executeUpdate() != 1) {
                        throw new SQLException(
                                "No se pudo actualizar el prestamo."
                        );
                    }
                }

                // Liberar ejemplar
                String sqlEjemplar =
                        "UPDATE ejemplares SET estado = 'DISPONIBLE' " +
                                "WHERE id = ? AND estado = 'PRESTADO'";

                try (PreparedStatement ps = con.prepareStatement(sqlEjemplar)) {
                    ps.setInt(1, idEjemplar);

                    if (ps.executeUpdate() != 1) {
                        throw new SQLException(
                                "El ejemplar no estaba PRESTADO."
                        );
                    }
                }

                con.commit();

            } catch (SQLException e) {
                con.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "Error al registrar devolucion: " + e.getMessage()
            );
        }
    }

}
