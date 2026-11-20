
package dao;

import Clases_Persona.Admin;
import Excepciones.AccesoDatosException;
import Excepciones.ElementoNoEncontradoException;
import Interfaces.DAO;
import config.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// NUEVO: implementamos la interfaz DAO para Admin
public class AdministradorDAO implements DAO<Admin> {

    // 1. INSERTAR ADMINISTRADOR
    @Override
    public void insertar(Admin elemento) throws AccesoDatosException {

        // NUEVO: guardamos el ID generado desde Java
        String sql = "INSERT INTO administradores " +
                "(idAdmin, nombre, dni, direccion, contacto) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, elemento.getIdAdmin());
            ps.setString(2, elemento.getNombre());
            ps.setString(3, elemento.getDni());
            ps.setString(4, elemento.getDireccion());
            ps.setString(5, elemento.getContacto());

            int filas = ps.executeUpdate();

            System.out.println("Filas modificadas: " + filas);

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "Error al insertar administrador: " + e.getMessage()
            );
        }
    }

    // 2. LISTAR ADMINISTRADORES
    @Override
    public List<Admin> listarTodos() throws AccesoDatosException {

        List<Admin> lista = new ArrayList<>();

        String sql = "SELECT * FROM administradores ORDER BY idAdmin";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(crearAdmin(rs));
            }

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "Error al listar administradores: " + e.getMessage()
            );
        }

        return lista;
    }

    // 3. BUSCAR ADMINISTRADOR POR ID
    @Override
    public Admin buscarPorId(int id)
            throws AccesoDatosException, ElementoNoEncontradoException {

        String sql = "SELECT * FROM administradores WHERE idAdmin = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // NUEVO: convertimos el String a int
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return crearAdmin(rs);
                }
            }

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "Error al buscar administrador: " + e.getMessage()
            );
        }

        throw new ElementoNoEncontradoException(id);
    }

    // 4. ACTUALIZAR ADMINISTRADOR
    @Override
    public void actualizar(Admin elemento)
            throws AccesoDatosException, ElementoNoEncontradoException {

        int filas;

        String sql = """
                UPDATE administradores
                SET nombre = ?, dni = ?, direccion = ?, contacto = ?
                WHERE idAdmin = ?
                """;

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, elemento.getNombre());
            ps.setString(2, elemento.getDni());
            ps.setString(3, elemento.getDireccion());
            ps.setString(4, elemento.getContacto());

            // NUEVO: identificador numerico
            ps.setInt(5, elemento.getIdAdmin());

            filas = ps.executeUpdate();

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "Error al actualizar administrador: " + e.getMessage()
            );
        }

        if (filas == 0) {
            throw new ElementoNoEncontradoException(elemento.getId());
        }
    }

    // 5. ELIMINAR ADMINISTRADOR
    @Override
    public void eliminar(int id)
            throws AccesoDatosException, ElementoNoEncontradoException {

        int filas;

        String sql = "DELETE FROM administradores WHERE idAdmin = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            filas = ps.executeUpdate();

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "Error al eliminar administrador: " + e.getMessage()
            );
        }

        if (filas == 0) {
            throw new ElementoNoEncontradoException(id);
        }
    }

    // 6. RECONSTRUIR ADMINISTRADOR DESDE MYSQL
    private Admin crearAdmin(ResultSet rs) throws SQLException {

        // NUEVO: recuperar el ID numerico
        Integer idAdmin = rs.getInt("idAdmin");

        String nombre = rs.getString("nombre");
        String dni = rs.getString("dni");
        String direccion = rs.getString("direccion");
        String contacto = rs.getString("contacto");

        // Utiliza el constructor que conserva el ID original
        return new Admin(
                nombre,
                dni,
                direccion,
                contacto,
                idAdmin
        );
    }
}
