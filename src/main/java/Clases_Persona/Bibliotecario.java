package Clases_Persona;

import Clases_Libros.Ejemplar;
import Clases_Libros.Libro;
import Clases_Libros.Prestamo;
import Enum.EstadoLibro;
import Excepciones.DatosInvalidosException;
import Interfaces.Identificable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Bibliotecario extends Persona implements Identificable<Integer> {
    private static int contadorlegajo = 1;
    /// Agregue el = 1 para que inicialice en 1
    private Integer legajo;
    /// Lo converti en integer para trabajar todos los id con el mismo tipo

    private List<Prestamo> prestamoActivo = new ArrayList<>();
    private List<Usuario> usuarioRegistrado = new ArrayList<>();

    public Bibliotecario(String nombre, String dni, String direccion, String contacto) {
        super(nombre, dni, direccion, contacto);
        this.legajo = contadorlegajo++;
    }

    // NUEVO: constructor para recuperar un bibliotecario desde MySQL
    public Bibliotecario(String nombre, String dni,
                         String direccion, String contacto,
                         Integer legajo) {

        super(nombre, dni, direccion, contacto);

        // NUEVO: valida el identificador recuperado
        if (legajo == null || legajo <= 0) {
            throw new IllegalArgumentException("Legajo inválido");
        }

        // NUEVO: conserva el ID original de MySQL
        this.legajo = legajo;

        // NUEVO: actualiza el contador durante esta ejecución
        if (legajo >= contadorlegajo) {
            contadorlegajo = legajo + 1;
        }
    }

    public Integer getLegajo() {
        return legajo;
    }

    @Override
    public Integer getId() {
        return legajo;
    }

    // NUEVO: setter con validación
    public void setLegajo(Integer legajo) {

        if (legajo == null || legajo <= 0) {
            throw new IllegalArgumentException("Legajo inválido");
        }

        this.legajo = legajo;
    }

    public boolean verificarDisponibilidad(Libro libro) {
        if (EstadoLibro.DISPONIBLE != libro.getEstado()) {
            throw new DatosInvalidosException("El libro no se encuentra en el disponible.");
        }
        return libro.isDisponible();

    }

    public Prestamo registrarPrestamo(Ejemplar ejemplar, Usuario usuario, Libro libro) {
        if (verificarDisponibilidad(libro)) {
            libro.setDisponible(false);


            Prestamo nuevoPrestamo = new Prestamo(ejemplar, usuario, LocalDateTime.now());
            prestamoActivo.add(nuevoPrestamo);

            System.out.println("Prestamo registrado");
            return nuevoPrestamo;
        } else {
            System.out.println("El libro no se encuentra en el disponible.");
            return null;
        }


    }

    public void registrarDevolucion(Prestamo prestamo) {
        if (prestamo != null && prestamoActivo.contains(prestamo)) {
            prestamo.setFechaDevolucion(LocalDateTime.now());

            prestamo.getEjemplar().setEstadoLibro(EstadoLibro.DISPONIBLE);

            prestamoActivo.remove(prestamo);

            System.out.println("Prestamo registrado con exito. El libro vuelve a estar disponible.");
        } else {
            throw new DatosInvalidosException("El libro no se encuentra en el disponible.");
        }
    }

    public Usuario cargarDatosUsuario(String dni, String nombre, String direccion, String contacto) {
        for (Usuario usuario : usuarioRegistrado) {
            if (usuario.getDni().equals(dni)) {
                throw new DatosInvalidosException("Ya hay un usuario registrado con ese DNI en el sistema.");
            }
        }


        Usuario nuevoUsuario = new Usuario(dni, nombre, direccion, contacto);
        usuarioRegistrado.add(nuevoUsuario);
        System.out.println("Usuario registrado correctamente.");
        return nuevoUsuario;
    }
}





