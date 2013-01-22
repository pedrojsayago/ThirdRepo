package swing;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 * Date: 19/01/13
 * Time: 14:42
 * To change this template use File | Settings | File Templates.
 */
public class VentanaSwing extends JFrame {

    public VentanaSwing() {
         this("este es el tema", 250, 150, 400, 504);
    }
    public VentanaSwing(String title, int x, int y, int w, int h) {
        super(title);

        //se quita el gestor de organizacion del panel d contenido
        this.getContentPane().setLayout(null);
        this.setBounds(x, y, w, h);
        JButton jb = new JButton("Boton swing");
        jb.setBounds(180, 200, 120, 50);

        this.getContentPane().add(jb);

        this.setVisible(true);
    }
}
