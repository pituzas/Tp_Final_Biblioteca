package dao;

import Clases_Persona.Usuario;
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

public class UsuarioDAO implements DAO<Usuario> {
    @Override
    public void insertar(Usuario elemento) throws AccesoDatosException {
        int filas = 0;
        String sql = "INSERT INTO usuarios (nombre,dni,direccion,contacto,idSocio) VALUES (?,?,?,?,?)";
        try(Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1,elemento.getNombre());
            ps.setString(2,elemento.getDni());
            ps.setString(3,elemento.getDireccion());
            ps.setString(4,elemento.getContacto());
            ps.setString(5,elemento.getIdSocio());

            filas = ps.executeUpdate();
            System.out.println("Filas modificadas: " + filas);


        }catch (SQLException e){
            throw new AccesoDatosException("Error al insertar el usuario"+e.getMessage());
        }


    }

    @Override
    public List<Usuario> listarTodos() throws AccesoDatosException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY dni";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(crearUsuario(rs));
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al listar el usuario"+e.getMessage());
        }
        return lista;


    }

    @Override
    public Usuario buscarPorId(String id) throws AccesoDatosException, ElementoNoEncontradoException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try(Connection con = ConexionDB.getConexion();
        PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1,id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return crearUsuario(rs);
                }
            }
        }catch (SQLException e){
            throw new AccesoDatosException("Error al buscar el usuario"+e.getMessage());
        }
        throw new ElementoNoEncontradoException(id);

    }

    @Override
    public void actualizar(Usuario elemento) throws AccesoDatosException, ElementoNoEncontradoException {
    int filas;
    String sql = "UPDATE usuarios SET nombre = ?,direccion = ?,contacto =? WHERE id = ?";
    try(Connection con = ConexionDB.getConexion();
    PreparedStatement ps = con.prepareStatement(sql)){
        ps.setString(1,elemento.getNombre());
        ps.setString(2,elemento.getDireccion());
        ps.setString(3,elemento.getContacto());
        ps.setString(4,elemento.getIdSocio());
        filas = ps.executeUpdate();


    }catch (SQLException e){
        throw new AccesoDatosException("Error al actualizar el usuario"+e.getMessage());
    }
    if(filas == 0){
        throw new ElementoNoEncontradoException(elemento.getNombre());
    }


    }

    @Override
    public void eliminar(String id) throws AccesoDatosException, ElementoNoEncontradoException {
    int filas;
    String sql = "DELETE FROM usuarios WHERE id = ?";
    try(Connection con = ConexionDB.getConexion();
        PreparedStatement ps = con.prepareStatement(sql)){
        ps.setString(1,id);
filas = ps.executeUpdate();

    }catch (SQLException e){
    throw new AccesoDatosException("Error al eliminar el usuario"+e.getMessage());
    }
    if(filas == 0){
throw new ElementoNoEncontradoException(id);
    }

    }
    private Usuario crearUsuario(ResultSet rs) throws SQLException {
    String nombre = rs.getString("nombre");
    String dni = rs.getString("dni");
    String direccion = rs.getString("direccion");
    String contacto = rs.getString("contacto");
    String idSocio = rs.getString("idSocio");

    return new Usuario(nombre,dni,direccion,contacto,idSocio);

    }
}
