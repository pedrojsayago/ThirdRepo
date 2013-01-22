package swing;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 * Date: 20/01/13
 * Time: 20:23
 * To change this template use File | Settings | File Templates.
 */
public class VentanaSwingConLista extends JFrame {
    public VentanaSwingConLista(String title, int x, int y, int w, int h) {
        super(title);

        //se quita el gestor de organizacion del panel d contenido
        this.getContentPane().setLayout(new GridLayout(2, 2));
        this.getContentPane().add(new JButton("Primer boton"));
        this.getContentPane().add(new JButton("Segundo boton"));
        this.getContentPane().add(new JButton("Tercer boton"));
        this.getContentPane().add(new JButton("Cuarto boton"));
        this.setBounds(x, y, w, h);
        this.setVisible(true);
    }
}
