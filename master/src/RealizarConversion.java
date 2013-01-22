import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 * Date: 19/01/13
 * Time: 15:15
 * To change this template use File | Settings | File Templates.
 */
public class RealizarConversion implements ActionListener {
    InterfazConversor ic;
    //constantes de conversion
    private final double pesetas = 166.386;
    private final double libras = 0.9;
    private final double dolares = 1.4;

    public RealizarConversion(InterfazConversor ic) {
        this.ic = ic;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double cantidad = Double.parseDouble(ic.txteuros.getText());
        double resultado = 0.0;
        String moneda = "";

        //pesetas
        if(ic.op1.isSelected()) {
            resultado = cantidad*pesetas;
            moneda = " ptas";
        }
        //libras
        if(ic.op2.isSelected()) {
            resultado = cantidad*libras;
            moneda = " libras";
        }
        //dolares
        if(ic.op3.isSelected()) {
            resultado = cantidad*dolares;
            moneda = " dolares";
        }
        ic.lblres.setText(resultado+moneda);
    }
}
