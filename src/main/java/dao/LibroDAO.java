package dao;

import Clases_Libros.Libro;
import Excepciones.AccesoDatosException;
import Excepciones.UsuarioNoEncontradoException;
import Interfaces.DAO;

import java.util.List;

public class LibroDAO implements DAO<Libro> {
    @Override
    public void insertar(Libro l) throws AccesoDatosException {

    }

    @Override
    public List<Libro> listarTodos() throws AccesoDatosException {
        return List.of();
    }

    @Override
    public Libro buscarPorId(String codigo) throws AccesoDatosException, UsuarioNoEncontradoException {
        return null;
    }

    @Override
    public void actualizar(Libro h) throws AccesoDatosException, UsuarioNoEncontradoException {

    }

    @Override
    public void eliminar(String codigo) throws AccesoDatosException, UsuarioNoEncontradoException {

    }
}
