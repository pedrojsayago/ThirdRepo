import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 * Date: 20/01/13
 * Time: 22:19
 * To change this template use File | Settings | File Templates.
 */
public class ControlReloj implements ActionListener {
    RelojApplet r;
    public ControlReloj(RelojApplet r) {
        this.r = r;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Calendar c = Calendar.getInstance();

        //formateo de hora
        String hora = "";
        hora+=c.get(Calendar.HOUR) + ":";
        hora+=c.get(Calendar.MINUTE) + ":";
        hora+=c.get(Calendar.SECOND);
        r.lblreloj.setText(hora);
    }
}
