package javabeans;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 * Date: 20/01/13
 * Time: 18:25
 * To change this template use File | Settings | File Templates.
 */

public class Persona {
    private String nombre;
    private String dni;
    private int edad;

    public Persona (String nombre, String dni, int edad) {
        this.nombre = nombre;
        this.dni = dni;
        this.edad = edad;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
