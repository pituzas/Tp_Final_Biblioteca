package Clases_Persona;

public class bibliotecario {
    private static int legajo;

    public bibliotecario() {
        this.legajo=legajo++;
    }

    public static int getLegajo() {
        return legajo;
    }



}
