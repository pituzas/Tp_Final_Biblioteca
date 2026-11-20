package Clases_Persona;

import Enum.EstadoPrestamo;
import Interfaces.Identificable;

public class Usuario extends Persona implements Identificable<String> {
    EstadoPrestamo estado;
    String idSocio;
    public Usuario(String nombre, String dni, String direccion, String contacto) {
    private String idSocio;

    public Usuario(String nombre, String dni, String direccion, String contacto, String idSocio) {
        super(nombre, dni, direccion, contacto);
        this.estado = EstadoPrestamo.CANCELADO;
        this.idSocio = idSocio;
    }

    public String getIdSocio() {
        return idSocio;
    }

    @Override
    public String getId() {
        return idSocio;
    }

    public void setIdSocio(String idSocio) {
        this.idSocio = idSocio;
    }


    public EstadoPrestamo getEstado() {
        return estado;
    }

    public void setEstado(EstadoPrestamo estado) {
        this.estado = estado;
    }

    public boolean prestamo() {
        if (EstadoPrestamo.ACTIVO != this.estado)
            return true;
        else
            return false;
    }

    public boolean devolucion() {
        if (this.estado == EstadoPrestamo.ACTIVO)
            return true;
        else
            return false;
    }


}

