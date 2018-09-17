package servidorremoto;

import com.sun.awt.AWTUtilities;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Random;
import javax.swing.BorderFactory;

import javax.swing.JLabel;

public class marcadorBkp extends JFrame {

    public marcadorBkp(){
//        setUndecorated(true);
        setSize(10, 10);
        setTitle("----------------------------------------");
        JPanel painel = new JPanel();
       
        painel.setLayout(null);
        JLabel label = new JLabel();
        label = new JLabel("[                                    ]");
        painel.add(label);
       
        add(painel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        painel.setLayout(new FlowLayout());
        
        Random rand = new Random();
        
        
        float R = rand.nextFloat();
        float G = rand.nextFloat();
        float B = rand.nextFloat();
        
        Color c = new Color(R,G,B);
        
        painel.setBorder(BorderFactory.createLineBorder(c));
        
    }
}
