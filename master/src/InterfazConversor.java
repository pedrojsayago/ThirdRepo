import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 * Date: 19/01/13
 * Time: 15:02
 * To change this template use File | Settings | File Templates.
 */
public class InterfazConversor extends JFrame {
    JRadioButton op1, op2, op3;
    JTextField txteuros;
    JLabel lblres;
    public InterfazConversor() {
        this("Conversor monetario", 250, 150, 400, 500);
    }

    public InterfazConversor(String title, int x, int y, int w, int h) {
        super(title);
        this.getContentPane().setLayout(null);
        this.setBounds(x, y, w, h);

        //crea controles
        JLabel lb = new JLabel("Introduzca valor en euros");
        lb.setBounds(80, 70, 150, 30);

        txteuros = new JTextField();
        txteuros.setBounds(230, 70, 100, 30);

        ButtonGroup bg = new ButtonGroup();
        op1 = new JRadioButton("pesetas");
        op1.setBounds(220, 150, 80, 30);

        op2 = new JRadioButton("libras");
        op2.setBounds(220, 190, 80, 30);

        op3 = new JRadioButton("dolares");
        op3.setBounds(220, 230, 80, 30);

        bg.add(op1);
        bg.add(op2);
        bg.add(op3);

        JButton bt = new JButton("Convertir");
        bt.setBounds(200, 260, 100, 30);

        //asocia boton con manejador
        bt.addActionListener(new RealizarConversion(this));

        lblres = new JLabel("");
        lblres.setBounds(200, 300, 100, 30);

        //aniade controles al contenedor
        this.getContentPane().add(lb);
        this.getContentPane().add(txteuros);
        this.getContentPane().add(op1);
        this.getContentPane().add(op2);
        this.getContentPane().add(op3);
        this.getContentPane().add(bt);
        this.getContentPane().add(lblres);

        //visualiza la ventana
        this.setVisible(true);
    }
}
