package swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 * Date: 22/01/13
 * Time: 21:36
 * To change this template use File | Settings | File Templates.
 */
public class GestionFichero implements ActionListener {

    private VentanaSwingLectorNotas v;
    private int contador;

    public GestionFichero(VentanaSwingLectorNotas v) {
        this.v = v;
        contador = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            FileWriter writer = new FileWriter("notas.txt", true);
            writer.write(v.jnota.getText() + "|");
            contador ++;
            //ponemos salto de linea tras escribir 5 notas
            if(contador == 5) {
                writer.write(System.getProperty("line.separator"));
                contador = 0;
            }
            writer.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
