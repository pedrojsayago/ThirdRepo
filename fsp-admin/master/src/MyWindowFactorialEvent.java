import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 * Date: 18/01/13
 * Time: 22:25
 * To change this template use File | Settings | File Templates.
 */
public class MyWindowFactorialEvent extends Frame {
    Label lbres;
    TextField txtnum;

    public MyWindowFactorialEvent() {
        this("ventana por defecto", 250, 150, 500, 300);
    }

    public MyWindowFactorialEvent(String title, int x, int y, int w, int h) {
        super(title);
        this.setLayout(null);
        this.setBounds(x, y, w, h);

        //creacion y colocacion de caja de texto
        txtnum = new TextField();
        txtnum.setBounds(220, 100, 50, 25);
        this.add(txtnum);

        //creacion y colocacion de boton
        Button bt = new Button("Calcular");
        bt.setBounds(180, 160, 60, 40);
        this.add(bt);

        //creacion y colocacion de etiketa
        Label lb = new Label("introduce un numero");
        lb.setBounds(10, 100, 100, 30);
        this.add(lb);

        //creacion y colocacion d etiketa
        lbres = new Label();
        lbres.setBounds(150, 220, 100, 40);
        this.add(lbres);

        //asocia el bton a su manejador, pasandole como parametro el objeto ventana
        bt.addActionListener(new ManejadorBoton(this));

        //asocia la ventana con el manejador
        this.addWindowListener(new ManejadorVentana());
        this.setVisible(true);
    }
}
