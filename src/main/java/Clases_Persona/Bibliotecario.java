package Clases_Persona;

public class Bibliotecario extends Persona {
    private static int legajo;

    public Bibliotecario() {
        this.legajo=legajo++;
    }

    public static int getLegajo() {
        return legajo;
    }



}
