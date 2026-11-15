package Excepciones;

public class ElementoNoEncontradoException extends Exception {
    private final String id;

    public ElementoNoEncontradoException(String id) {
        super("No se encontró ningún elemento con el identificador: " + id);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}