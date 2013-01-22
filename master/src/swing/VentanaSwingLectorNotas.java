package swing;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 * Date: 22/01/13
 * Time: 21:29
 * To change this template use File | Settings | File Templates.
 */
public class VentanaSwingLectorNotas extends JFrame {

        JTextField jnota;

        public VentanaSwingLectorNotas () {
            this("este es el tema", 250, 150, 400, 504);
        }

        public VentanaSwingLectorNotas (String title, int x, int y, int w, int h) {
            super(title);

            //se quita el gestor de organizacion del panel d contenido
            this.getContentPane().setLayout(null);
            this.setBounds(x, y, w, h);

            JLabel jl = new JLabel("Introduce la nota: ");
            jl.setBounds(50, 100, 140, 40);
            jnota = new JTextField();
            jnota.setBounds(50, 100, 140, 40);

            JButton jb = new JButton("Aniadir");
            jb.setBounds(150, 200, 120, 40);

            //se aniaden los controles al panel de contenido
            this.getContentPane().add(jb);
            this.getContentPane().add(jnota);
            this.getContentPane().add(jl);

            //asocia el boton con su manejador
            jb.addActionListener(new GestionFichero(this));
            this.setVisible(true);
        }
    }
