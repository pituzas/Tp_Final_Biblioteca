package Gestores;

import Clases_Persona.Usuario;

import java.util.ArrayList;
import java.util.List;
import Enum.EstadoPrestamo;

public class GestorUsuarios extends Gestor<Integer,Usuario> {

public List<Usuario> listarPorPrestamo(int idPrestamo)
{
    List<Usuario> listaUsuario = new ArrayList<Usuario>();
    for(Usuario usuario : listaUsuario){
        if(usuario.getEstado() == EstadoPrestamo.ACTIVO){
            listaUsuario.add(usuario);
        }
    }
    return  listaUsuario;
}

///COMPLETAR


}
