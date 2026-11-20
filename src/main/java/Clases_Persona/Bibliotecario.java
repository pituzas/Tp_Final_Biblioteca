package Clases_Persona;

import Clases_Libros.Ejemplar;
import Clases_Libros.Libro;
import Clases_Libros.Prestamo;
import Enum.EstadoLibro;
import Excepciones.DatosInvalidosException;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Bibliotecario extends Persona {
    private static int contadorlegajo;
    private int legajo;

    private List<Prestamo> prestamoActivo = new ArrayList<>();
    private List<Usuario> usuarioRegistrado = new ArrayList<>();

    public Bibliotecario(String nombre, String dni, String direccion, String contacto) {
        super(nombre, dni, direccion, contacto);
        this.legajo=contadorlegajo++;
    }

    public  int getLegajo() {
        return legajo;
    }
    public boolean verificarDisponibilidad(Libro libro){
        if(EstadoLibro.DISPONIBLE != libro.getEstado() ){
            throw new DatosInvalidosException("El libro no se encuentra en el disponible.");
        }
        return libro.isDisponible();

    }

    public Prestamo registrarPrestamo(Ejemplar ejemplar,Usuario usuario, Libro libro) {
        if (verificarDisponibilidad(libro)) {
            libro.setDisponible(false);


        Prestamo nuevoPrestamo = new Prestamo(ejemplar, usuario, LocalDateTime.now());
        prestamoActivo.add(nuevoPrestamo);

        System.out.println("Prestamo registrado");
        return nuevoPrestamo;
    }else {
            System.out.println("El libro no se encuentra en el disponible.");
            return null;
        }



    }
    public void registrarDevolucion(Prestamo prestamo){
        if(prestamo != null && prestamoActivo.contains(prestamo)) {
            prestamo.setFechaDevolucion(LocalDateTime.now());

            prestamo.getEjemplar().setEstadoLibro(EstadoLibro.DISPONIBLE);

            prestamoActivo.remove(prestamo);

            System.out.println("Prestamo registrado con exito. El libro vuelve a estar disponible.");
        } else
        {
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





public class Bibliotecario extends Persona {
    private static int legajo;

    public Bibliotecario() {
        this.legajo=legajo++;
    }

    public static int getLegajo() {
        return legajo;
    }



}
