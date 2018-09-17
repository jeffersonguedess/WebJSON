package servidorremoto;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import static java.awt.Toolkit.getDefaultToolkit;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.swing.BorderFactory;

/**
 *
 * @author Julio Cesar
 */
public class JPanel extends javax.swing.JPanel {

    private static Image load(String url) {
        try {
            final Toolkit tk = getDefaultToolkit();
            final URL path = new URL("file:///" + url); // Any URL would work here
            final Image img = tk.createImage(path);
            tk.prepareImage(img, 0, 0, null);
            return img;
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private Image img = load(null);
    private int x = 0;
    private int y = 0;

    //   private Graphics g = getGraphics();
    public JPanel(String fundo) {
//setOpaque(false);
        try {
            this.img = load(fundo);
        } catch (Exception e) {

        }

        // paintComponents();
    }

    public JPanel() {
        super();
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponents(g);
        Graphics gr = g.create();
        gr.drawImage(img, x, y, this.getWidth(), this.getHeight(), this);
        gr.dispose();
    }

    public void alterarImagem(String fundo) {
        this.img = load(fundo);
        // this.img = novaimagem;
        this.revalidate(); // não lembro se repaint() serve aqui
    }
    private static final Logger LOG =  Logger.getLogger(JPanel.class.getName());

}
