
package Gestores;

import Excepciones.DatosInvalidosException;
import Excepciones.ElementoNoEncontradoException;
import Interfaces.Identificable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Gestor<ID, T extends Identificable<ID>> {

    protected List<T> elementos = new ArrayList<>();

    // AGREGAR ELEMENTO
    public void agregar(T elemento) {

        if (elemento == null || elemento.getId() == null) {
            throw new DatosInvalidosException(
                    "El elemento o su ID no pueden ser nulos"
            );
        }

        for (T existente : elementos) {
            if (Objects.equals(existente.getId(), elemento.getId())) {
                throw new DatosInvalidosException(
                        "El elemento ya existe"
                );
            }
        }

        elementos.add(elemento);
    }

    // BUSCAR ELEMENTO POR ID
    public T buscar(ID id) throws ElementoNoEncontradoException {

        for (T elemento : elementos) {
            if (Objects.equals(elemento.getId(), id)) {
                return elemento;
            }
        }

        throw new ElementoNoEncontradoException(
                String.valueOf(id)
        );
    }

    // ELIMINAR ELEMENTO POR ID
    public void eliminar(ID id) throws ElementoNoEncontradoException {

        T elemento = buscar(id);
        elementos.remove(elemento);
    }

    // LISTAR ELEMENTOS
    public List<T> listar() {
        return new ArrayList<>(elementos);
    }

    // CONTAR ELEMENTOS
    public int cantidad() {
        return elementos.size();
    }
}
