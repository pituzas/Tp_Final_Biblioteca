package Clases_Libros;

import Clases_Persona.Usuario;

import java.time.LocalDateTime;
import java.util.UUID;

import Enum.EstadoLibro;
import Enum.EstadoPrestamo;
import Excepciones.DatosInvalidosException;

public class Prestamo {
    private UUID idPrestamo;
    private Ejemplar ejemplar;
    private Usuario usuario;
    private EstadoPrestamo estadoPrestamo;
    private LocalDateTime fechaPrestamo;
    private LocalDateTime fechaVencimiento;
    private LocalDateTime fechaDevolucion;


    /// CONSTRUCTOR

    public Prestamo(Ejemplar ejemplar, Usuario usuario,
                    LocalDateTime fechaVencimiento) {

        if (ejemplar == null || usuario == null) {
            throw new DatosInvalidosException("El ejemplar y el usuario no pueden ser nulos");
        }

        if (!ejemplar.estaDispo()) {
            throw new DatosInvalidosException("El ejemplar no está disponible");
        }

        this.idPrestamo = UUID.randomUUID();
        this.ejemplar = ejemplar;
        this.usuario = usuario;
        this.estadoPrestamo = EstadoPrestamo.ACTIVO;
        this.fechaPrestamo = LocalDateTime.now();

        setFechaVencimiento(fechaVencimiento);

        this.fechaDevolucion = null;

        ejemplar.setEstadoLibro(EstadoLibro.PRESTADO);
    }

    public Prestamo(UUID idPrestamo, Ejemplar ejemplar,
                    Usuario usuario, LocalDateTime fechaPrestamo,
                    LocalDateTime fechaVencimiento,
                    LocalDateTime fechaDevolucion,
                    EstadoPrestamo estadoPrestamo) {

        if (idPrestamo == null || ejemplar == null ||
                usuario == null || fechaPrestamo == null ||
                fechaVencimiento == null || estadoPrestamo == null) {

            throw new DatosInvalidosException(
                    "Datos inválidos del préstamo"
            );
        }

        if (!fechaVencimiento.isAfter(fechaPrestamo)) {
            throw new DatosInvalidosException(
                    "El vencimiento debe ser posterior al préstamo"
            );
        }

        this.idPrestamo = idPrestamo;
        this.ejemplar = ejemplar;
        this.usuario = usuario;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaVencimiento = fechaVencimiento;
        this.fechaDevolucion = fechaDevolucion;
        this.estadoPrestamo = estadoPrestamo;
    }


    /// GETTERS Y SETTERS
    public UUID getIdPrestamo() {
        return idPrestamo;
    }


    public Ejemplar getEjemplar() {
        return ejemplar;
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new DatosInvalidosException(
                    "El usuario no puede ser nulo"
            );
        }
        this.usuario = usuario;
    }

    public LocalDateTime getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDateTime getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
        if (fechaVencimiento == null ||
                !fechaVencimiento.isAfter(fechaPrestamo)) {

            throw new DatosInvalidosException(
                    "La fecha de vencimiento debe ser posterior al préstamo"
            );
        }

        this.fechaVencimiento = fechaVencimiento;
    }

    public LocalDateTime getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDateTime fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public EstadoPrestamo getEstadoPrestamo() {
        return estadoPrestamo;
    }

    public boolean estaVencido() {
        return estadoPrestamo == EstadoPrestamo.ACTIVO
                && LocalDateTime.now().isAfter(fechaVencimiento);
    }

    public void devolverEjemplar() {

        if (estadoPrestamo != EstadoPrestamo.ACTIVO) {
            throw new DatosInvalidosException(
                    "El préstamo no se encuentra activo"
            );
        }

        this.fechaDevolucion = LocalDateTime.now();
        this.estadoPrestamo = EstadoPrestamo.DEVUELTO;
        this.ejemplar.setEstadoLibro(EstadoLibro.DISPONIBLE);

        if (fechaDevolucion.isAfter(fechaVencimiento)) {
            System.out.println(
                    "El ejemplar fue devuelto fuera de término"
            );
        } else {
            System.out.println(
                    "El ejemplar fue devuelto correctamente"
            );
        }
    }


}
