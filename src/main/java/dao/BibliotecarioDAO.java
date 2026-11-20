
package dao;

import Clases_Persona.Bibliotecario;
import Excepciones.AccesoDatosException;
import Excepciones.ElementoNoEncontradoException;
import Interfaces.DAO;
import config.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BibliotecarioDAO implements DAO<Bibliotecario> {

    // 1. NUEVO: INSERTAR BIBLIOTECARIO
    @Override
    public void insertar(Bibliotecario elemento)
            throws AccesoDatosException {

        String sql = "INSERT INTO bibliotecarios " +
                "(legajo, nombre, dni, direccion, contacto) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // NUEVO: guardar el legajo generado desde Java
            ps.setInt(1, elemento.getLegajo());

            ps.setString(2, elemento.getNombre());
            ps.setString(3, elemento.getDni());
            ps.setString(4, elemento.getDireccion());
            ps.setString(5, elemento.getContacto());

            int filas = ps.executeUpdate();
            System.out.println("Filas modificadas: " + filas);

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "Error al insertar bibliotecario: " + e.getMessage()
            );
        }
    }

    // 2. NUEVO: LISTAR TODOS LOS BIBLIOTECARIOS
    @Override
    public List<Bibliotecario> listarTodos()
            throws AccesoDatosException {

        List<Bibliotecario> lista = new ArrayList<>();

        String sql = "SELECT * FROM bibliotecarios ORDER BY legajo";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(crearBibliotecario(rs));
            }

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "Error al listar bibliotecarios: " + e.getMessage()
            );
        }

        return lista;
    }

    // 3. NUEVO: BUSCAR BIBLIOTECARIO POR LEGAJO
    @Override
    public Bibliotecario buscarPorId(int id)
            throws AccesoDatosException, ElementoNoEncontradoException {

        String sql = "SELECT * FROM bibliotecarios WHERE legajo = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // MODIFICADO: convertir String a int
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return crearBibliotecario(rs);
                }
            }

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "Error al buscar bibliotecario: " + e.getMessage()
            );
        }

        throw new ElementoNoEncontradoException(id);
    }

    // 4. NUEVO: ACTUALIZAR BIBLIOTECARIO
    @Override
    public void actualizar(Bibliotecario elemento)
            throws AccesoDatosException, ElementoNoEncontradoException {

        int filas;

        String sql = """
                UPDATE bibliotecarios
                SET nombre = ?, dni = ?, direccion = ?, contacto = ?
                WHERE legajo = ?
                """;

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, elemento.getNombre());
            ps.setString(2, elemento.getDni());
            ps.setString(3, elemento.getDireccion());
            ps.setString(4, elemento.getContacto());

            // NUEVO: legajo Integer
            ps.setInt(5, elemento.getLegajo());

            filas = ps.executeUpdate();

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "Error al actualizar bibliotecario: " + e.getMessage()
            );
        }

        if (filas == 0) {
            throw new ElementoNoEncontradoException(elemento.getId());
        }
    }

    // 5. NUEVO: ELIMINAR BIBLIOTECARIO
    @Override
    public void eliminar(int id)
            throws AccesoDatosException, ElementoNoEncontradoException {

        int filas;

        String sql = "DELETE FROM bibliotecarios WHERE legajo = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // MODIFICADO: convertir String a int
            ps.setInt(1, id);

            filas = ps.executeUpdate();

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "Error al eliminar bibliotecario: " + e.getMessage()
            );
        }

        if (filas == 0) {
            throw new ElementoNoEncontradoException(id);
        }
    }

    // 6. NUEVO: RECONSTRUIR BIBLIOTECARIO DESDE MYSQL
    private Bibliotecario crearBibliotecario(ResultSet rs)
            throws SQLException {

        // NUEVO: recuperar el legajo numérico
        Integer legajo = rs.getInt("legajo");

        String nombre = rs.getString("nombre");
        String dni = rs.getString("dni");
        String direccion = rs.getString("direccion");
        String contacto = rs.getString("contacto");

        // NUEVO: constructor que conserva el ID original
        return new Bibliotecario(
                nombre,
                dni,
                direccion,
                contacto,
                legajo
        );
    }
}
