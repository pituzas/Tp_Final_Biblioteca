package Clases_Libros;

import Clases_Persona.Usuario;

import java.time.LocalDateTime;
import java.util.UUID;

import Enum.EstadoLibro;
import Enum.EstadoPrestamo;
import Excepciones.DatosInvalidosException;

public class Prestamo {
    private static String idPrestamo;
    private Ejemplar ejemplar;
    private Usuario usuario;
    private EstadoPrestamo estadoPrestamo;
    private LocalDateTime fechaPrestamo;
    private LocalDateTime fechaVencimiento;
    private LocalDateTime fechaDevolucion;


    /// CONSTRUCTOR

    public Prestamo(Ejemplar ejemplar, Usuario usuario, LocalDateTime fechaPrestamo) {

        this.idPrestamo = idPrestamo;
        this.ejemplar = ejemplar;
        this.usuario = usuario;
        this.estadoPrestamo = EstadoPrestamo.ACTIVO;
        this.fechaPrestamo = LocalDateTime.now();
        this.fechaVencimiento = fechaVencimiento;
        this.fechaDevolucion = null;

        ejemplar.setEstadoLibro(EstadoLibro.PRESTADO);
    }


    /// GETTERS Y SETTERS
    public String getIdPrestamo() {
        return idPrestamo;
    }


    public Ejemplar getEjemplar() {
        return ejemplar;
    }

    public void setEjemplar(Ejemplar ejemplar) {
        if (ejemplar == null || usuario == null) {
            throw new DatosInvalidosException("Usuario o Ejemplar nulos.");
        }
        if (!ejemplar.estaDispo()) {
            throw new DatosInvalidosException("El ejemplar no esta disponible");
        }
        this.ejemplar = ejemplar;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDateTime getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public LocalDateTime getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDateTime fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public void devolverEjemplar() {
        if (estadoPrestamo == EstadoPrestamo.DEVUELTO) {
            throw new IllegalArgumentException("El ejemplar ya fue devuelto!");
        } else if (estadoPrestamo == EstadoPrestamo.CANCELADO) {
            System.out.println("Te excediste de la fecha de devolucion, que no vuelva a ocurrir.");

            ejemplar.setEstadoLibro(EstadoLibro.DISPONIBLE);
            fechaDevolucion = LocalDateTime.now();
        } else {
            ejemplar.setEstadoLibro(EstadoLibro.DISPONIBLE);
            fechaDevolucion = LocalDateTime.now();
        }
    }

}
