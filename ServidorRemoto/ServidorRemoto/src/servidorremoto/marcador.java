package servidorremoto;

import com.sun.awt.AWTUtilities;
import java.awt.FlowLayout;
import javax.swing.JLabel;

public class marcador extends JFrame {

    public marcador(int X, int Y){
//        setUndecorated(true);
        setSize(600, 50);
        setTitle("----------------------------------------");
        
        setLocation(X, Y);
        JPanel painel = new JPanel();
        
        painel.setLayout(null);
        JLabel label = new JLabel();
        label = new JLabel("[                                    ]");
        painel.add(label);
        label.setBounds(X, Y, X+200, Y+600);
        add(painel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        painel.setLayout(new FlowLayout());
        
    }
}
