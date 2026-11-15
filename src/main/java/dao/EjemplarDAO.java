package dao;

import Clases_Libros.Ejemplar;
import Excepciones.AccesoDatosException;
import Excepciones.ElementoNoEncontradoException;
import Interfaces.DAO;
import config.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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

    @Override
    public List<Ejemplar> listarTodos() throws AccesoDatosException {
        return List.of();
    }

    @Override
    public Ejemplar buscarPorId(String id) throws AccesoDatosException, ElementoNoEncontradoException {
        return null;
    }

    @Override
    public void actualizar(Ejemplar elemento) throws AccesoDatosException, ElementoNoEncontradoException {

    }

    @Override
    public void eliminar(String id) throws AccesoDatosException, ElementoNoEncontradoException {

    }
}
