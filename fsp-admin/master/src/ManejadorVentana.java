import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Hacemos uso de un adaptador, q es una clase q proporciona una implementacion por defecto de una
 * interfaz de escucha. Por cada interfaz de escucha existe un adaptador.
 * Con los adaptadores evitamos tener q sobreescribir todos los metodos de la interfaz
 * En caso de WindowListener existe el WindowAdapter
 */
public class ManejadorVentana extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e) {
        Window v = (Window)e.getSource();
        v.dispose();
    }
}
