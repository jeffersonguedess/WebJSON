/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorremoto;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class Servidorremoto extends Thread {

    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    public static byte[] retornaImagem(BufferedImage originalImage) {
        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
//            baos.close();
            return imageInByte;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ServerSocket server = null;
    
    JFrame frameConecta = new JFrame("Clientes conectados");
    JTextArea textAreal = new JTextArea();

    class recebeComandos extends Thread {

        //private javax.swing.JFrame frame = new JFrame();
        private marcadorBkp frame = new marcadorBkp();
        ObjectInputStream entrada = null;
        ObjectOutputStream saida = null;
        Robot robo = null;
        Socket s = null;

        public recebeComandos(Socket s) {
            this.s = s;
            //frame.setUndecorated(true);
            
            
                        frameConecta.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            String cliente = s.getInetAddress().getHostAddress().toString();
            textAreal.append("\n Conectado: " + cliente);

            textAreal.setPreferredSize(new Dimension(300, 150));
            textAreal.setLineWrap(true);
            textAreal.setWrapStyleWord(true);
            textAreal.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(textAreal, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

            frameConecta.add(scrollPane);
            frameConecta.pack();
            frameConecta.setVisible(true);
            
            try {
                robo = new Robot();
                entrada = new ObjectInputStream(s.getInputStream());
                saida = new ObjectOutputStream(s.getOutputStream());

            } catch (IOException ex) {
                Logger.getLogger(Servidorremoto.class.getName()).log(Level.SEVERE, null, ex);
            } catch (AWTException ex) {
                Logger.getLogger(Servidorremoto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void run() {
            while (true) {
                try {
                    String comando = entrada.readUTF();

                    if (comando.contains("999")) {
                        int X = entrada.readInt();
                        int Y = entrada.readInt();
                        
//                        JOptionPane.showMessageDialog(null, X+":X - Y:"+Y);
                        
                        new marcador(X, Y);
                        saida.flush();
                        
                        
                    }else if (comando.contains("111")) {
                        int dW = entrada.readInt(); 
                        int dH = entrada.readInt();
                        int X1 = entrada.readInt();
                        int Y1 = entrada.readInt();
                        int X2 = entrada.readInt();
                        int Y2 = entrada.readInt();
                        
//                        JOptionPane.showMessageDialog(null, X+":X - Y:"+Y);
                        
                        new Marcador2click(X1, Y1,X2, Y2, dW, dH);
                        saida.flush();
                        
                        
                    }else if (comando.contains("imagem")) {
                        int x = entrada.readInt();
                        int y = entrada.readInt();
                        System.out.println(x + " " + y);
                        Image img
                                = robo.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())).
                                        getScaledInstance(x, y, Image.SCALE_FAST);
                        saida.writeObject(retornaImagem(toBufferedImage(img)));
                        saida.flush();
                    } else if (comando.contains("mousemove")) {
                        
//                        frame.setVisible(false);
//                        frame.setUndecorated(false);
                        int tL = entrada.readInt();
                        int aH = entrada.readInt();

                        int x = entrada.readInt();
                        int y = entrada.readInt();
                        int dW = Toolkit.getDefaultToolkit().getScreenSize().width;
                        int dH = Toolkit.getDefaultToolkit().getScreenSize().height;
                        
//                        frame.setSize(100, 100);
                        
                        frame.setLocation(((x * dW) / tL), ((y * dH) / aH));
//                        frame.setUndecorated(true);
//                        frame.setVisible(true);
//                        robo.mouseMove(((x * dW) / tL), ((y * dH) / aH));

                    } else if (comando.contains("mouseclick")) {
                        int mouse = entrada.readInt();
                        if (mouse == 0) {
                            robo.mousePress(InputEvent.BUTTON1_MASK);
                            robo.mouseRelease(InputEvent.BUTTON1_MASK);
                        } else {
                            robo.mousePress(InputEvent.BUTTON3_MASK);
                            robo.mouseRelease(InputEvent.BUTTON3_MASK);
                        }

                    } else if (comando.contains("keypressed")) {
                        int key = entrada.readInt();
                        robo.keyPress(key);
                        robo.keyRelease(key);

                    } else if (comando.contains("cmd")) {
                        Runtime.getRuntime().exec(entrada.readUTF());

                    } else if (comando.contains("receberaquivo")) {
                        recebendoArquivo();
                    }
                    //Açoes do robo
                } catch (IOException ex) {
                    String clienteDesc = s.getInetAddress().getHostAddress().toString();
                    
                    String aux = textAreal.getText();
                    
                    aux = aux.replace(clienteDesc, "");
                    
                    textAreal.setText("");
                            
                    textAreal.append(aux);
                    Logger.getLogger(Servidorremoto.class.getName()).log(Level.SEVERE, null, ex);
                    break;
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Servidorremoto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        public synchronized void recebendoArquivo() throws IOException, ClassNotFoundException {

            //2
            byte objectAsByte[] = (byte[]) entrada.readObject();

            //3
            Arquivo arquivo = (Arquivo) getObjectFromByte(objectAsByte);

            //4
            String dir = arquivo.getDiretorioDestino().endsWith("/") ? arquivo
                    .getDiretorioDestino() + arquivo.getNome() : arquivo
                    .getDiretorioDestino() + "/" + arquivo.getNome();
            System.out.println("Escrevendo arquivo " + dir);

            //5
            FileOutputStream fos = new FileOutputStream(dir);
            fos.write(arquivo.getConteudo());
//            fos.close();

        }

    }

    public void run() {
        while (true) {

            try {
                server = new ServerSocket(3312);

                while (true) {
                    new recebeComandos(server.accept()).start();
                }
            } catch (IOException ex) {
                Logger.getLogger(Servidorremoto.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }
        }
    }

    private static Object getObjectFromByte(byte[] objectAsByte) {
        Object obj = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(objectAsByte);
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();

//            bis.close();
//            ois.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return obj;

    }

    public static void main(String args[]) {

        new Servidorremoto().start();
    }
}
