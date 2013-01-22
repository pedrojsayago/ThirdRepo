import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 * Date: 18/01/13
 * Time: 22:25
 * To change this template use File | Settings | File Templates.
 */
public class MyVentana extends Frame {
    public MyVentana() {
        this("ventana por defecto", 250, 150, 400, 200);
    }

    public MyVentana(String title, int x, int y, int w, int h) {
        super(title);
        this.setBounds(x, y, w, h);
        this.setVisible(true);
    }

    public void paint(Graphics g) {
        int xcentro = this.getSize().width/2;
        int ycentro = this.getSize().height/2;
        int x = xcentro;
        int y = ycentro;
        while (x>0 && y>0) {
            g.drawOval(x, y, (xcentro-x)*2, (ycentro-y)*2);
            x-=10;
            y-=10;
        }
    }
}
