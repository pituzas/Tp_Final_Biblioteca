package Clases_Libros;

import Enum.EstadoLibro;

import java.util.Objects;
import java.util.UUID;


public class Libro {
    private UUID id;
    private String nombre;
    private String autor;
    private String genero;


    /// CONSTRUCTORES
    public Libro(String nombre, String autor, String genero) {

        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.autor = autor;
        this.genero = genero;
    }

    public Libro() {
    }


    /// GETTERS Y SETTERS
    public UUID getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    /// EQUALS Y HASH CON NOMBRE/AUTOR/GENERO
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return Objects.equals(nombre, libro.nombre) && Objects.equals(autor, libro.autor) && Objects.equals(genero, libro.genero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, autor, genero);
    }

    /// MOSTRAR INFO
    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", autor='" + autor + '\'' +
                ", genero='" + genero + '\'' +
                '}';
    }
}
