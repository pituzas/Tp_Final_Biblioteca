package Clases_Persona;
import Enum.EstadoPrestamo;

public class Usuario extends Persona{
    EstadoPrestamo estado;
    public Usuario(String nombre, String dni, String direccion, String contacto, String idSocio) {
        super(nombre, dni, direccion, contacto);
        this.estado=EstadoPrestamo.CANCELADO;
    }

    public EstadoPrestamo getEstado() {
        return estado;
    }

    public void setEstado(EstadoPrestamo estado) {
        this.estado = estado;
    }

    public boolean prestamo(){
        if(EstadoPrestamo.ACTIVO!=this.estado)
        return true;
        else
            return false;
    }

    public boolean devolucion(){
        if(this.estado==EstadoPrestamo.ACTIVO)
        return true;
        else
            return false;
    }



    }

