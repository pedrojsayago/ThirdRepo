package swing;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 * Date: 20/01/13
 * Time: 15:24
 * To change this template use File | Settings | File Templates.
 */
public class VentanaSwingJList extends JFrame {
    public VentanaSwingJList(String title, int x, int y, int w, int h) {
        super(title);

        //se quita el gestor de organizacion del panel d contenido
        this.getContentPane().setLayout(null);
        this.setBounds(x, y, w, h);

        DefaultListModel dlm = new DefaultListModel();
        dlm.addElement("Primavera");
        dlm.addElement("Verano");
        dlm.addElement("Otonio");
        dlm.addElement("Invierno");

        JList jl = new JList(dlm);
        jl.setBounds(180, 100, 120, 80);

        this.getContentPane().add(jl);
        this.setVisible(true);
    }
}
