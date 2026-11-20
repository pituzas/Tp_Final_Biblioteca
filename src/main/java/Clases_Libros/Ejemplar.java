package Clases_Libros;

import Excepciones.DatosInvalidosException;
import Interfaces.Prestado;


import Enum.EstadoLibro;

public class Ejemplar implements Prestado {
    private static int contador = 1;
    private Integer id;
    private Libro libro;
    private EstadoLibro estadoLibro;


    /// CONSTRUCTORES
    public Ejemplar(Libro libro) {
        ///MOVI LA VALIDACION DEL ID QUE LO VALIDE DIRECTAMENTE EN EL SETTER Y NO EN EL CONSTRUCTOR.
        setLibro(libro);
        this.id = contador++;

        this.estadoLibro = EstadoLibro.DISPONIBLE;
    }

    public Ejemplar(Integer id, Libro libro, EstadoLibro estadoLibro) {

        if (id == null || id <= 0) {
            throw new DatosInvalidosException("El id no puede ser null o negativo ");
        }
        this.id = id;
        setLibro(libro);
        setEstadoLibro(estadoLibro);

        if (id >= contador) {
            contador = id + 1;
        }
    }

    /// GETTERS Y SETTERS
    public Integer getId() {
        return id;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        if (libro == null) {
            throw new DatosInvalidosException(
                    "El libro no puede ser null"
            );
        }

        this.libro = libro;
    }

    public EstadoLibro getEstadoLibro() {
        return estadoLibro;
    }

    public void setEstadoLibro(EstadoLibro estadoLibro) {
        if (estadoLibro == null) {
            throw new IllegalArgumentException("El estado no puede ser Null.");
        }
        this.estadoLibro = estadoLibro;
    }

    /// INTERFACE
    @Override
    public boolean estaDispo() {
        return estadoLibro == EstadoLibro.DISPONIBLE;
    }

    @Override
    public String toString() {
        return "Ejemplar{" +
                "id=" + id +
                ", libro=" + libro +
                ", estadoLibro=" + estadoLibro +
                '}';
    }
}
