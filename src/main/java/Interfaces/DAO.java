package Interfaces;

import Excepciones.AccesoDatosException;
import Excepciones.ElementoNoEncontradoException;
import java.util.List;

public interface DAO<T> {

    void insertar(T elemento)
            throws AccesoDatosException;

    List<T> listarTodos()
            throws AccesoDatosException;

    T buscarPorId(String id)
            throws AccesoDatosException,
                   ElementoNoEncontradoException;

    void actualizar(T elemento)
            throws AccesoDatosException,
                   ElementoNoEncontradoException;

    void eliminar(String id)
            throws AccesoDatosException,
                   ElementoNoEncontradoException;
}