package Gestores;

import Excepciones.DatosInvalidosException;
import Excepciones.ElementoNoEncontradoException;
import Interfaces.Identificable;

import java.util.ArrayList;
import java.util.List;

public class  Gestor<T extends Identificable>{
    protected List<T> elementos = new ArrayList<T>();

    public void agregar(T elemento){
        if(elemento == null){
            throw new DatosInvalidosException("No se puede agregar un elemento nulo");
        }
        if(elementos.contains(elemento)){
            throw new DatosInvalidosException("El elemento ya existe");
        }
        elementos.add(elemento);
    }
    public T buscar(String id) throws ElementoNoEncontradoException{
        for(T elemento : elementos){
            if(elemento.getId().equals(id)){
                return elemento;
            }
        }
        throw new ElementoNoEncontradoException(id);
    }
    public void eliminar(String id)throws ElementoNoEncontradoException{
        for(T elemento : elementos){
            if(elemento.getId().equals(id)){
                elementos.remove(elemento);
            }
        }
    }
    public List<T> listar(){
        return new ArrayList<>(elementos);
    }

    public int cantidad(){
        return elementos.size();
    }

}
