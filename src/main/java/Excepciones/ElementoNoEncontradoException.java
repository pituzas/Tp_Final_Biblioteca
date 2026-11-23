package Excepciones;

public class ElementoNoEncontradoException extends Exception {
    private final Integer id;

    public ElementoNoEncontradoException(int id) {
        super("No se encontró ningún elemento con el identificador: " + id);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}