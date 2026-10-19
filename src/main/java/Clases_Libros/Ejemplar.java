package Clases_Libros;

import Interfaces.Prestado;

import java.util.UUID;

import Enum.EstadoLibro;

public class Ejemplar implements Prestado {
    private UUID id;
    private Libro libro;
    private EstadoLibro estadoLibro;


    /// CONSTRUCTORES
    public Ejemplar(Libro libro) {
        if (libro == null) {
            throw new IllegalArgumentException("El libro no puede ser null.");
        }
        this.id = UUID.randomUUID();
        this.libro = libro;
        this.estadoLibro = EstadoLibro.DISPONIBLE;
    }


    /// GETTERS Y SETTERS
    public UUID getId() {
        return id;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
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
        if (estadoLibro == EstadoLibro.DISPONIBLE) {
            System.out.println("El libro" + getLibro().getNombre() + "id :" + getId() + "Esta Disponible.");
            return true;
        } else {
            System.out.println("EL libro no se encuentra disponible. Estado:" + getEstadoLibro());
            return false;
        }
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
