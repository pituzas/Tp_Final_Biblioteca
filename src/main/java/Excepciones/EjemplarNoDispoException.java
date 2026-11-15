package Excepciones;
import Enum.EstadoLibro;


public class EjemplarNoDispoException extends Exception {
    private final String id;
    private final EstadoLibro estado;
    public EjemplarNoDispoException(String id, EstadoLibro estado) {

        super("El ejemplar "+id+ "no esta disponible (estado actual : "+estado+")");
        this.id = id;
        this.estado = estado;
    }
    public String getId() {
        return id;
    }
    public EstadoLibro getEstado() {
        return estado;
    }
}
