import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 * Date: 19/01/13
 * Time: 03:40
 * To change this template use File | Settings | File Templates.
 */
public class VentanaControles extends Frame {
    public VentanaControles() {
        this("ventana ", 250,150,500,300);
    }
    public VentanaControles(String title, int x, int y, int w, int h) {
        super(title);
        this.setLayout(null);
        this.setBounds(x, y, w, h);

        Label lb = new Label("Texto de etiketa");
        lb.setBounds(60, 50, 100, 20);

        TextField tf = new TextField("escriba aqui");
        tf.setBounds(180, 50, 80, 20);

        TextArea ta = new TextArea("comentarios...");
        ta.setBounds(300, 50, 100, 60);

        Choice lista = new Choice();
        lista.setBounds(120, 150, 70, 30);
        lista.add("lunes");
        lista.add("martes");

        CheckboxGroup cbg = new CheckboxGroup();
        Checkbox op1 = new Checkbox("1", cbg, false);
        op1.setBounds(220, 150, 60, 30);
        Checkbox op2 = new Checkbox("2", cbg, false);
        op2.setBounds(220, 190, 60, 30);
        Checkbox op3 = new Checkbox("3", cbg, false);
        op3.setBounds(220, 230, 60, 30);

        this.add(lb);
        this.add(tf);
        this.add(ta);
        this.add(lista);
        this.add(op1);
        this.add(op2);
        this.add(op3);
        this.setVisible(true);
    }
}
