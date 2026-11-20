package Main;


import config.ConexionDB;

import java.sql.Connection;
import java.sql.SQLException;

public class PruebaConexion {

    public static void main(String[] args) {

        try (Connection con = ConexionDB.getConexion()) {
            System.out.println("Conexión exitosa a biblioteca_db");

        } catch (SQLException e) {
            System.out.println(
                    "Error de conexión: " + e.getMessage()
            );
        }
    }
}
