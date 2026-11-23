package Clases_Persona;

import Enum.EstadoPrestamo;
import Interfaces.Identificable;

public class Usuario extends Persona implements Identificable<Integer> {

    private static int contador = 1; // NUEVO: contador compartido
    private EstadoPrestamo estado;
    private Integer idSocio; // MODIFICADO: antes String

    public Usuario(String nombre, String dni,
                   String direccion, String contacto) {
        super(nombre, dni, direccion, contacto);
        this.estado = EstadoPrestamo.CANCELADO;
        this.idSocio = contador++;
    }

    public Usuario(String nombre, String dni,
                   String direccion, String contacto,
                   Integer idSocio) {

        super(nombre, dni, direccion, contacto);

        this.estado = EstadoPrestamo.CANCELADO;

        this.idSocio = idSocio; // MODIFICADO: conserva el ID de MySQL

        // NUEVO: actualiza el contador si recuperamos un ID mayor
        if (idSocio != null && idSocio >= contador) {
            contador = idSocio + 1;
        }
    }

    public Integer getIdSocio() {
        return idSocio;
    }

    @Override
    public Integer getId() {
        return idSocio;
    }

    public void setIdSocio(Integer idSocio) {
        if (idSocio == null || idSocio <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
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

