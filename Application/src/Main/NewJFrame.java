package Main;

import Application.Control.IInputOwner;
import Application.View.IScreen;
import Application.Platform.IWorkPlace;
import MVC.Control.IInputReciver;
import Platform.Core.Hidden.TaskMotions;
import Platform.Core.Systems.SystemM;
import Render.View.IPictureQueue;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.MouseInputAdapter;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NewJFrame.java
 *
 * Created on 10.05.2011, 11:36:04
 */
/**
 *
 * @author kalinin
 */
public class NewJFrame extends javax.swing.JFrame implements IWorkPlace, IScreen, IScreen.UnInit, IInputOwner {

    public BufferedImage target;
    public Graphics2D targetG;
    private Graphics2D panelGr;
    ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
    MouseInputAdapter ml;
    MouseMotionListener mml;
    IInputReciver.withVPad inputReciver;

    /** Creates new form NewJFrame */
    public NewJFrame() {
        initComponents();

        GetPanel().setSize(480, 320);
        target = new BufferedImage(GetPanel().getWidth(), GetPanel().getHeight(), BufferedImage.TYPE_INT_ARGB);
        //  target.setRenderingHints(rh);
        targetG = (Graphics2D) target.getGraphics();
        targetG.setBackground(Color.white);
        //        RenderingHints rh = new RenderingHints(
//                RenderingHints.KEY_TEXT_ANTIALIASING,
//                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //targetG.setRenderingHints(rh);
        targetG.setFont(new Font(null, 0, 10));
        panelGr = (Graphics2D) GetPanel().getGraphics();
        new Thread(new Runnable() {

            public void run() {
                while (true) {
                    info.setText("");
                    info.setText(info.getText() + "Threads - " + String.valueOf(threadBean.getThreadCount()) + "\n");
                    info.setText(info.getText() + "SystemM - " + SystemM.SystemMCount.get_() + "\n");
                    info.setText(info.getText() + "TaskMotions - " + TaskMotions.TaskMotionsCount.get_() + "\n");
                    for (int i = 0; i < SystemM.SystemMnames.size(); i++) {
                        info.setText(info.getText() + SystemM.SystemMnames.get(i) + "\n");
                    }

                    try {
                        Thread.currentThread().sleep(5000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }, "ObjectsInfo").start();

        //  panelGr.setBackground(new Color(0, true));
        // targetG.setBackground(Color.white);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        info = new java.awt.TextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        jPanel1.setPreferredSize(new java.awt.Dimension(480, 320));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );

        jPanel2.setDoubleBuffered(false);
        jPanel2.setFocusCycleRoot(true);
        jPanel2.setPreferredSize(new java.awt.Dimension(480, 320));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );

        info.setEditable(false);
        info.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(info, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(162, 162, 162))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(info, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(220, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_formKeyReleased

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseMoved
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.TextArea info;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JPanel GetPanel() {

        return jPanel1;
    }

    public javax.swing.JPanel GetPanel2() {
        return jPanel2;
    }

    public Object getGraphicTool() {
        return targetG;
    }

    public void update(boolean simple) {
        // System.out.println("Update<");
        inputReciver.drawPad(targetG);
        panelGr.drawImage(target, 0, 0, GetPanel());
        targetG.clearRect(0, 0, GetPanel().getWidth(), GetPanel().getHeight());
        //  System.out.println(">Update");
    }

    public void Selected(int selectingbtn, int x1, int y1, int x2, int y2) {
        int[] arg = new int[4];
        arg[0] = x1;
        arg[1] = y1;
        arg[2] = x2;
        arg[3] = y2;

        inputReciver.keyPressed(selectingbtn, arg);
        inputReciver.keyReleased(selectingbtn, arg);
    }

    public void SetListeners() {
        ml = new MouseInputAdapter() {

            final int selectingbtn = 6;
            int x;
            int y;

            @Override
            public void mousePressed(MouseEvent e) {
                x = e.getX();
                y = e.getY();
                //        if (!commandPanel.Positioning(e.getX(), e.getY())) {
                inputReciver.pointerPressed(e.getButton(), e.getX(), e.getY());
                //   }

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int x_;
                int y_;
                x_ = e.getX();
                y_ = e.getY();
                if ((x != x_) && (y_ != y)) {

                    Selected(selectingbtn, x, y, x_, y_);
                }
                //     if (!commandPanel.Action(e.getX(), e.getY())) {
                inputReciver.pointerReleased(e.getButton(), e.getX(), e.getY());
                //     }

            }

            @Override
            public void mouseDragged(MouseEvent e) {

                //     if (!commandPanel.Select(e.getX(), e.getY())) {
                //       System.out.println(" "+x+", "+y+", "+e.getX()+", "+e.getY()+", ");
                inputReciver.pointerDragged(e.getButton(), x, y, e.getX(), e.getY());
                //      }

            }
        };
        mml = new MouseMotionAdapter() {

            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                inputReciver.mouseMoved(5, e.getX(), e.getY());
            }
        };

        GetPanel().addMouseListener(ml);
        GetPanel().addMouseMotionListener(ml);
        GetPanel().addMouseMotionListener(mml);




//        trKey = new Thread(new Runnable() {
//
//            public void run() {
//                while (true) {
//                    if (currentForm != null) {
//                        for (int i = 0; i < ((lMenu) currentForm).arg.length; i++) {
//                            if (((lMenu) currentForm).keyPressed[((lMenu) currentForm).arg[i]]) {
//                                ((lMenu) currentForm).keyPressed(((lMenu) currentForm).arg[i]);
//                            }else{
//                                ((lMenu) currentForm).keyReleased(((lMenu) currentForm).arg[i]);
//                            }
//                        }
//                        try {
//                            Thread.sleep(100);
//                        } catch (InterruptedException ex) {
//                            Logger.getLogger(GameManager.class.getName()).log(java.util.logging.lMenu.SEVERE, null, ex);
//                        }
//                    }
//                }
//
//            }
//        });
//        trKey.start();


    }

    public void setInputReciver(IInputReciver.withVPad inputReciver) {
        this.inputReciver = inputReciver;
    }

    public IScreen.UnInit getScreen() {
        return this;
    }

    public IInputOwner getInputOwner() {
        return this;
    }

    public void Listen() {
        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("keyPressed " + e.getKeyCode());
                inputReciver.keyPressed(e.getKeyCode(), new int[4]);
                //if (e.getKeyCode()==27)IOOwner.InputReciver().keyPressed(0, e.getKeyCode(), new int[4]);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("keyReleased " + e.getKeyCode());
                inputReciver.keyReleased(e.getKeyCode(), new int[4]);
                //if (e.getKeyCode()==27)IOOwner.InputReciver().keyReleased(0, e.getKeyCode(), new int[4]);
            }
        });
        SetListeners();
    }

    @Override
    public java.awt.Image createImage_(int width, int height) {
        return GetPanel2().createImage(width, height);
    }

    @Override
    public Graphics getGraphics_() {
        return GetPanel2().getGraphics();
    }

    @Override
    public int getWidth() {
        return GetPanel().getWidth();
    }

    @Override
    public int getHeight() {
        return GetPanel().getHeight();
    }

    public IScreen prepare() {
        setFocusable(true);
        setLayout(new BorderLayout());
        setVisible(true);

        return this;
    }

    public IInputOwner prepare(IInputReciver.withVPad inputReciver) {
        setInputReciver(inputReciver);
        return this;
    }
}
