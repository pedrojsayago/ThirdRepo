import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 * Date: 20/01/13
 * Time: 22:13
 * To change this templateb use File | Settings | File Templates.
 */
public class RelojApplet extends JApplet {
    JLabel lblreloj;

    public void init() {
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        lblreloj = new JLabel("hora");

        JButton bt = new JButton("Ver hora");
        bt.addActionListener(new ControlReloj(this));
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();

        p1.setLayout(new FlowLayout(FlowLayout.CENTER));
        p1.add(lblreloj);
        p2.setLayout(new FlowLayout(FlowLayout.CENTER));
        p2.add(bt);
        this.getContentPane().add(p1);
        this.getContentPane().add(p2);
    }
}
