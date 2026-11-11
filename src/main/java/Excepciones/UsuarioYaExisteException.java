package Excepciones;

public class UsuarioYaExisteException extends Exception {
private final String id;
    public UsuarioYaExisteException(String id) {
        super("Ya existe un usuario con el identificador : "+id);
        this.id = id;
    }
    public String getId() {
        return id;
    }
}
