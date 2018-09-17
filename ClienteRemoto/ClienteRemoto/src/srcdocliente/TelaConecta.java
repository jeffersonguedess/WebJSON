/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package srcdocliente;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class TelaConecta extends javax.swing.JFrame {

    private long tamanhoPermitidoKB = 5120; //Igual a 5MB
    private Arquivo arquivo;

    public void enviaArquivo(Socket socket) {
        try {

            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setDialogTitle("Escolha o arquivo");

            if (chooser.showOpenDialog(this) == JFileChooser.OPEN_DIALOG) {
                File fileSelected = chooser.getSelectedFile();

                byte[] bFile = new byte[(int) fileSelected.length()];
                FileInputStream fis = new FileInputStream(fileSelected);
                fis.read(bFile);
//                fis.close();

                long kbSize = fileSelected.length() / 1024;

                arquivo = new Arquivo();
                arquivo.setConteudo(bFile);
                arquivo.setDataHoraUpload(new Date());
                arquivo.setNome(fileSelected.getName());
                arquivo.setTamanhoKB(kbSize);

                byte[] bytea = serializarArquivo();
                conecta.envia.writeObject(bytea);
                conecta.envia.flush();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    Conecta conecta = null;

    public TelaConecta() {
        initComponents();
    }

    public synchronized void enviaPosMouse(int X, int Y, String codigo) {
        try {
            if (conecta == null) {
                return;
            }
            try {
                conecta.envia.writeUTF(codigo);
                conecta.envia.flush();
                conecta.envia.writeInt(X);
                conecta.envia.flush();
                conecta.envia.writeInt(Y);
                conecta.envia.flush();

//                byte b[] = (byte[]) conecta.recebe.readObject();
//
//                Rectangle d = exibeimagem.getBounds();
//                exibeimagem.setIcon(new ImageIcon(b));
//                exibeimagem.setBounds(d);

            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
    }
    
    public synchronized void enviaPosMouse(String codigo) {
        try {
            if (conecta == null) {
                return;
            }
            try {
                conecta.envia.writeUTF(codigo);
                conecta.envia.flush();
                
                conecta.envia.writeInt(exibeimagem.getWidth());
                    conecta.envia.flush();
                    conecta.envia.writeInt(exibeimagem.getHeight());
                    conecta.envia.flush();
                conecta.envia.writeInt(postDuploXmouse1);
                conecta.envia.flush();
                conecta.envia.writeInt(postDuploYmouse1);
                conecta.envia.flush();
                conecta.envia.writeInt(postDuploXmouse2);
                conecta.envia.flush();
                conecta.envia.writeInt(postDuploYmouse2);
                conecta.envia.flush();

//                byte b[] = (byte[]) conecta.recebe.readObject();
//
//                Rectangle d = exibeimagem.getBounds();
//                exibeimagem.setIcon(new ImageIcon(b));
//                exibeimagem.setBounds(d);

            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
    }

    public synchronized void enviaComando(String comando, int x, int y, int mouse, int key,
            String comandoKey) {
        try {
            if (conecta == null) {
                return;
            }
            try {
                conecta.envia.writeUTF(comando);
                if (comando.contains("imagem")) {
                    conecta.envia.writeInt(largura);
                    conecta.envia.flush();
                    conecta.envia.writeInt(altura);
                    conecta.envia.flush();

                    byte b[] = (byte[]) conecta.recebe.readObject();

                    Rectangle d = exibeimagem.getBounds();
                    exibeimagem.setIcon(new ImageIcon(b));
                    exibeimagem.setBounds(d);

                } else if (comando.contains("mousemove")) {
                    conecta.envia.writeInt(exibeimagem.getWidth());
                    conecta.envia.flush();
                    conecta.envia.writeInt(exibeimagem.getHeight());
                    conecta.envia.flush();

                    conecta.envia.writeInt(x);
                    conecta.envia.flush();
                    conecta.envia.writeInt(y);
                    conecta.envia.flush();
                } else if (comando.contains("mouseclick")) {

                    conecta.envia.writeInt(mouse);
                    conecta.envia.flush();

                } else if (comando.contains("keypressed")) {
                    conecta.envia.writeInt(key);
                    conecta.envia.flush();
                } else if (comando.contains("cmd")) {

                    conecta.envia.writeUTF(comandoKey);
                    conecta.envia.flush();
                } else if (comando.contains("receberaquivo")) {
                    enviaArquivo(conecta.s);
                }

            } catch (IOException ex) {
                Logger.getLogger(TelaConecta.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TelaConecta.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (Exception e) {
           
        }
    }

    private byte[] serializarArquivo() {
        try {
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            ObjectOutputStream ous;
            ous = new ObjectOutputStream(bao);
            ous.writeObject(arquivo);
            return bao.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean validaArquivo() {
        if (arquivo.getTamanhoKB() > tamanhoPermitidoKB) {
            JOptionPane.showMessageDialog(this,
                    "Tamanho máximo permitido atingido (" + (tamanhoPermitidoKB / 1024) + ")");
            return false;
        } else {
            return true;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        comando = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        teclas = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        ip = new javax.swing.JTextField();
        porta = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        exibeimagem = new javax.swing.JLabel();
        btnMarca = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Conexão remota");
        setBackground(java.awt.SystemColor.window);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        jLabel4.setText("Comando");

        jButton2.setText("enviar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        teclas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                teclasKeyReleased(evt);
            }
        });

        jLabel1.setText("Teclado");

        jLabel2.setText("IP");

        porta.setText("3312");

        jLabel3.setText("Porta");

        jButton1.setText("Conectar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("EnviarArquivo");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 144, Short.MAX_VALUE))
                    .addComponent(ip))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(porta, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(comando, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(teclas, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1))
                        .addGap(4, 4, 4)
                        .addComponent(ip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(teclas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2)
                        .addComponent(comando, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3)
                        .addComponent(jButton1)
                        .addComponent(porta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jPanel1ComponentResized(evt);
            }
        });

        exibeimagem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        exibeimagem.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                exibeimagemMouseMoved(evt);
            }
        });
        exibeimagem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                exibeimagemMousePressed(evt);
            }
        });

        btnMarca.setText("MARCA");
        btnMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarcaActionPerformed(evt);
            }
        });

        jButton4.setText("Marca Dupla Ponto 1");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Marca Dupla Ponto 2");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(exibeimagem, javax.swing.GroupLayout.DEFAULT_SIZE, 906, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(exibeimagem, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMarca, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {
            conecta = new Conecta(ip.getText(), Integer.parseInt(porta.getText()));

            JOptionPane.showMessageDialog(null, "OK");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {

                        enviaComando("imagem", exibeimagem.getWidth(),
                                exibeimagem.getHeight(), WIDTH, '0', null);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(TelaConecta.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }

                }
            }).start();

        } catch (IOException ex) {
            Logger.getLogger(TelaConecta.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro");

        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        enviaComando("cmd", 0, 0, 0, 's', comando.getText());


    }//GEN-LAST:event_jButton2ActionPerformed

    private int postDuploXmouse1 = 0;
    private int postDuploYmouse1 = 0;
    private int postDuploXmouse2 = 0;
    private int postDuploYmouse2 = 0;
    
    private void exibeimagemMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exibeimagemMousePressed
        if (marcadora) {
            int posXMouse = evt.getXOnScreen();
            int posYMouse = evt.getYOnScreen();
            
            enviaPosMouse(posXMouse, posYMouse, "999");
        } else if (marcadoraDupla1) {
            marcadoraDupla1 = false;
            marcadoraDupla2 = true;
            postDuploXmouse1= evt.getX();
            postDuploYmouse1= evt.getY();
            
            if (postDuploXmouse2 != 0 && postDuploYmouse2 != 0) {
                marcadoraDupla1 =false;
                marcadoraDupla2 = false;
                 enviaPosMouse("111");
                 postDuploXmouse1 = 0;
                 postDuploXmouse2 = 0;
                 postDuploYmouse1 = 0;
                 postDuploYmouse2 = 0;
            }
        } else if (marcadoraDupla2) {
            marcadoraDupla1 = true;
            marcadoraDupla2 = false;
            postDuploXmouse2= evt.getX();
            postDuploYmouse2= evt.getY();
            
            if (postDuploXmouse1 != 0 && postDuploYmouse1 != 0) {
                marcadoraDupla1 =false;
                marcadoraDupla2 = false;
                 enviaPosMouse("111");
                 postDuploXmouse1 = 0;
                 postDuploXmouse2 = 0;
                 postDuploYmouse1 = 0;
                 postDuploYmouse2 = 0;
            }
        }  else {
            enviaComando("mousemove", evt.getX(), evt.getY(), 0, '0', null);        // TODO add your handling code here:
            int click = (evt.getButton() == MouseEvent.BUTTON1) ? 0 : 1;

            enviaComando("mouseclick", 0, 0, click, '0', null);
        }
    }//GEN-LAST:event_exibeimagemMousePressed

    private void exibeimagemMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exibeimagemMouseMoved
        enviaComando("mousemove", evt.getX(), evt.getY(), 0, '0', null);        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_exibeimagemMouseMoved

    private void teclasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_teclasKeyReleased

        System.out.println("executou aqui");
        enviaComando("keypressed", 0, 0, 0, evt.getKeyCode(), null);        // TODO add your handling code here:
        //
        // TODO add your handling code here:
    }//GEN-LAST:event_teclasKeyReleased

    int largura = 0;
    int altura = 0;

    private void jPanel1ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel1ComponentResized


    }//GEN-LAST:event_jPanel1ComponentResized

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        largura = exibeimagem.getWidth();
        altura = exibeimagem.getHeight();   // TODO add your handling code here:
    }//GEN-LAST:event_formComponentResized

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        enviaArquivo(conecta.s);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private static boolean marcadora = false;

    private void btnMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarcaActionPerformed
        if (marcadora) {
            marcadora = false;
        } else {
            marcadora = true;
        }
    }//GEN-LAST:event_btnMarcaActionPerformed

    private static boolean marcadoraDupla1 = false;
    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (marcadoraDupla1) {
            marcadoraDupla1 = false;
        } else {
            marcadoraDupla1 = true;
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private static boolean marcadoraDupla2 = false;
    
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (marcadoraDupla2) {
            marcadoraDupla2 = false;
        } else {
            marcadoraDupla2 = true;
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaConecta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaConecta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaConecta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaConecta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaConecta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMarca;
    private javax.swing.JTextField comando;
    private javax.swing.JLabel exibeimagem;
    private javax.swing.JTextField ip;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField porta;
    private javax.swing.JTextField teclas;
    // End of variables declaration//GEN-END:variables
}
