
package Clases_Persona;

import Interfaces.Identificable; // NUEVO

// MODIFICADO: implementamos la interfaz generica
public class Admin extends Persona implements Identificable<Integer> {

    // NUEVO: contador compartido entre administradores
    private static int contador = 1;

    // NUEVO: identificador individual
    private Integer idAdmin;

    // CONSTRUCTOR PARA CREAR ADMINISTRADORES NUEVOS
    public Admin(String nombre, String dni,
                 String direccion, String contacto) {

        // Inicializa los atributos heredados de Persona
        super(nombre, dni, direccion, contacto);

        // NUEVO: genera un ID numerico
        this.idAdmin = contador++;
    }

    // CONSTRUCTOR PARA RECUPERAR ADMINISTRADORES DE MYSQL
    public Admin(String nombre, String dni,
                 String direccion, String contacto,
                 Integer idAdmin) {

        super(nombre, dni, direccion, contacto);

        // NUEVO: conserva el ID registrado en MySQL
        if (idAdmin == null || idAdmin <= 0) {
            throw new IllegalArgumentException("ID invalido");
        }

        this.idAdmin = idAdmin;

        // NUEVO: actualiza el contador durante esta ejecucion
        if (idAdmin >= contador) {
            contador = idAdmin + 1;
        }
    }

    // GETTERS

    @Override
    public Integer getId() {
        return idAdmin;
    }

    public Integer getIdAdmin() {
        return idAdmin;
    }

    // SETTER
    public void setIdAdmin(Integer idAdmin) {

        if (idAdmin == null || idAdmin <= 0) {
            throw new IllegalArgumentException("ID invalido");
        }

        this.idAdmin = idAdmin;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "idAdmin=" + idAdmin +
                ", nombre='" + getNombre() + '\'' +
                ", dni='" + getDni() + '\'' +
                ", direccion='" + getDireccion() + '\'' +
                ", contacto='" + getContacto() + '\'' +
                '}';
    }
}
