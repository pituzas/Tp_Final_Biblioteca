
package Clases_Libros;

import Enum.EstadoLibro;
import Interfaces.Identificable;

import java.util.Objects;

public class Libro implements Identificable<Integer> {

    /// Cambio id de UUID a integer para menos complicaciones

    private static int contador = 1;

    private Integer id;
    private String nombre;
    private String autor;
    private String genero;
    private boolean disponible;
    private EstadoLibro estado;

    // CONSTRUCTOR PARA CREAR UN LIBRO NUEVO
    public Libro(String nombre, String autor, String genero,
                 boolean disponible, EstadoLibro estado) {

        this.id = contador++;
        this.nombre = nombre;
        this.autor = autor;
        this.genero = genero;
        this.disponible = disponible;
        this.estado = estado;
    }

    // CONSTRUCTOR PARA RECUPERAR UN LIBRO DE MYSQL
    public Libro(Integer id, String nombre, String autor, String genero) {

        this.id = id;
        this.nombre = nombre;
        this.autor = autor;
        this.genero = genero;
        this.estado = EstadoLibro.DISPONIBLE;
        this.disponible = true;

        if (id != null && id >= contador) {
            contador = id + 1;
        }
    }

    public Libro() {
        this.id = contador++;
    }

    // PERMITE SINCRONIZAR EL CONTADOR CON MYSQL
    public static void configurarContador(int ultimoId) {
        contador = Math.max(contador, ultimoId + 1);
    }

    // GETTERS Y SETTERS
    @Override
    public Integer getId() {
        return id;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public EstadoLibro getEstado() {
        return estado;
    }

    public void setEstado(EstadoLibro estado) {
        this.estado = estado;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Libro libro)) return false;

        return Objects.equals(nombre, libro.nombre)
                && Objects.equals(autor, libro.autor)
                && Objects.equals(genero, libro.genero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, autor, genero);
    }

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
