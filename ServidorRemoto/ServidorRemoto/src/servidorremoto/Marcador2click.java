package servidorremoto;

import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.JLabel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class Marcador2click extends JFrame{
    
    
    public Marcador2click(int X1, int Y1,int X2,int Y2, int tL, int aH){
//        setUndecorated(true);
        

        int dW = Toolkit.getDefaultToolkit().getScreenSize().width;
        int dH = Toolkit.getDefaultToolkit().getScreenSize().height;
        
        X1 = ((X1 * dW) / tL);
        Y1 = ((Y1 * dH) / aH);
        X2 = ((X2 * dW) / tL);
        Y2 = ((Y2 * dH) / aH);
        
        int baseX = X2 - X1;
        int alturaY = Y2 - Y1;
//        if(juntaX < 0){
//            juntaX = juntaX * (-1);
//        }
//        if(juntaY<0){
//            juntaY = juntaY * (-1);
//        }
        setSize(baseX,alturaY);
        setTitle("----------------------------------------");
        
        JPanel painel = new JPanel();
        
        painel.setLayout(null);
        JLabel label = new JLabel();
        label = new JLabel("[                                    ]");
        painel.add(label);
        label.setBounds(X1, Y1, X2+baseX,Y2+alturaY );
        add(painel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        painel.setLayout(new FlowLayout());
        setLocation(X1, Y1);

        
    }
    
}
