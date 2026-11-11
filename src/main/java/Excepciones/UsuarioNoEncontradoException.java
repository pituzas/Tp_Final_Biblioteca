package Excepciones;

public class UsuarioNoEncontradoException extends Exception {

    private final String id;
    public UsuarioNoEncontradoException(String id) {
        super("Usuario no encontrado: " + id);
        this.id = id;
    }
    public String getId() {
        return id;
    }
}
