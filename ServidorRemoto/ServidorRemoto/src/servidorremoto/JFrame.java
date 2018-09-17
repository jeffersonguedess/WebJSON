package servidorremoto;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import static java.awt.Toolkit.getDefaultToolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import static javax.imageio.ImageIO.read;

public class JFrame extends javax.swing.JFrame {

    private BufferedImage img = null;
    private int x = 0;
    private int y = 0;

    public JFrame() {
        //centralizarComponente();
        setUndecorated(true);
pack(); 
setBackground(new Color(0, 0, 0, 0)); 

    }
   

    public JFrame(String urlImg) throws IOException {
        this.img = read(new File(urlImg));

    }

    public void centralizarComponente() {
        Dimension ds = getDefaultToolkit().getScreenSize();
        Dimension dw = getSize();
        setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);
    }

    @Override
    public void paintComponents(Graphics g) {

        super.paintComponents(g);
        Graphics gr = g.create();
        gr.drawImage(img, x, y, this.getWidth(), this.getHeight(), this);
        gr.dispose();
    }
    private static final Logger LOG =  Logger.getLogger(JFrame.class.getName());
}
