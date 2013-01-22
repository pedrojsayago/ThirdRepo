import javax.swing.*;

/**
 *
 * Esta clase utiliza el javabean Persona
 */
public class VentanaInsercion extends JFrame {
    JTextField txtnombre, txtdni, txtedad;
    DefaultListModel dlm;
    JLabel lblnombre, lbldni, lbledad;
    JList jl;
    public VentanaInsercion(String title, int x, int y, int w, int h) {
        super(title);
        this.getContentPane().setLayout(null);
        this.setBounds(x,y,w,h);

        //crea campos de texto
        JLabel l1 = new JLabel("Nombre: ");
        l1.setBounds(100, 80, 100, 30);
        txtnombre = new JTextField();
        txtnombre.setBounds(220, 80, 100, 30);

        JLabel l2 = new JLabel("Dni: ");
        l2.setBounds(100, 120, 100, 30);
        txtdni = new JTextField();
        txtdni.setBounds(220, 120, 100, 30);

        JLabel l3 = new JLabel("Edad: ");
        l3.setBounds(100, 160, 100, 30);
        txtedad = new JTextField();
        txtedad.setBounds(220, 160, 100, 30);

        //crea boton de aniadir
        JButton jb = new JButton("aniadir");
        jb.setBounds(350, 130, 100, 30);
        jb.addActionListener(new AgregarPersona(this));

        //crea lista de nombres
        dlm = new DefaultListModel();
        jl = new JList(dlm);
        jl.setBounds(150, 220, 100, 80);
        jl.addListSelectionListener(new SeleccionElementos(this));

        //etiketas con datos de elementos seleccionado
        lblnombre = new JLabel();
        lblnombre.setBounds(300, 220, 100, 30);
        lblnombre.setVisible(false);

        lbldni = new JLabel();
        lbldni.setBounds(300, 260, 100, 30);
        lbldni.setVisible(false);

        lbledad = new JLabel();
        lbledad.setBounds(300, 300, 100, 30);
        lbledad.setVisible(false);

        //aniade controles a la interfaz
        this.getContentPane().add(l1);
        this.getContentPane().add(l2);
        this.getContentPane().add(l3);
        this.getContentPane().add(txtnombre);
        this.getContentPane().add(txtdni);
        this.getContentPane().add(txtedad);
        this.getContentPane().add(lblnombre);
        this.getContentPane().add(lbldni);
        this.getContentPane().add(lbledad);
        this.getContentPane().add(jb);
        this.getContentPane().add(jl);
        this.setVisible(true);
    }
}
