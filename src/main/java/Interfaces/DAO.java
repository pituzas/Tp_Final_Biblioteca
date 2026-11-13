package Interfaces;

import Clases_Libros.Libro;
import Excepciones.AccesoDatosException;
import Excepciones.UsuarioNoEncontradoException;

import java.util.List;

public interface DAO<T> {
    public void insertar(Libro l) throws AccesoDatosException;

    public List<Libro> listarTodos() throws AccesoDatosException;

    public Libro buscarPorId(String codigo) throws AccesoDatosException, UsuarioNoEncontradoException;

    public void actualizar(Libro h) throws AccesoDatosException, UsuarioNoEncontradoException;

    public void eliminar(String codigo) throws AccesoDatosException, UsuarioNoEncontradoException;
}
