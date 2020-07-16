package Logic.Game.World.PathFindng.Path;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NewJFrame.java
 *
 * Created on 22.11.2011, 10:29:05
 */
import Logic.Game.World.PathFindng.Path.Vertice.PathPointsFactory;
import Logic.Model.Game.World.IWalker;
import Logic.Model.Game.World.PathFindng.IPathPointsFactory;
import Logic.Model.Game.World.PathFindng.IWalkAdviser;
import Logic.Model.Game.World.PathFindng.Path.IPath;
import Logic.Model.Game.World.PathFindng.Path.TrackPoint.ITrackPoint;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author kalinin
 */
class PathTest extends javax.swing.JFrame {

    public static final int f = 1;
    public static final int j = 2;
    public static final int b = 3;
    public static final int g = 4;
    public static final int h = 5;
    public static final byte[] l32 = {h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h};
    public static final byte[] l31 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, h};
    public static final byte[] l30 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, b, f, f, h};
    public static final byte[] l29 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, 0, 0, h};
    public static final byte[] l28 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, f, f, 0, f, f, 0, 0, 0, h};
    public static final byte[] l27 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, h};
    public static final byte[] l26 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, f, 0, 0, 0, 0, h};
    public static final byte[] l25 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, 0, 0, 0, 0, 0, 0, 0, h};
    public static final byte[] l24 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, 0, f, 0, f, f, 0, f, f, 0, 0, 0, 0, f, 0, 0, 0, h};
    public static final byte[] l23 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, 0, 0, h};
    public static final byte[] l22 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, h};
    public static final byte[] l21 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, f, 0, 0, 0, f, f, 0, h};
    public static final byte[] l20 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, b, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, 0, 0, 0, h};
    public static final byte[] l19 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, 0, 0, 0, 0, 0, 0, f, f, f, f, 0, 0, 0, 0, 0, 0, 0, 0, f, f, f, 0, 0, 0, 0, 0, h};
    public static final byte[] l18 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, 0, 0, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, 0, 0, 0, 0, h};
    public static final byte[] l17 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, b, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, 0, 0, 0, 0, 0, f, 0, 0, 0, 0, 0, f, f, 0, h};
    public static final byte[] l16 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, 0, 0, 0, 0, 0, 0, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, f, 0, 0, 0, h};
    public static final byte[] l15 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, 0, f, 0, 0, 0, 0, 0, 0, 0, f, f, h};
    public static final byte[] l14 = {h, 0, 0, 0, 0, 0, 0, 0, 0, f, 0, f, 0, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, 0, 0, 0, 0, 0, 0, 0, 0, f, f, 0, 0, 0, f, f, 0, 0, 0, 0, 0, 0, 0, 0, h};
    public static final byte[] l13 = {h, 0, 0, 0, b, 0, f, 0, f, 0, 0, 0, 0, 0, 0, 0, 0, b, 0, 0, 0, 0, 0, 0, f, 0, 0, 0, 0, 0, b, 0, 0, 0, 0, 0, 0, 0, 0, f, 0, 0, f, f, f, 0, 0, h};
    public static final byte[] l12 = {h, 0, 0, f, f, f, f, f, 0, 0, 0, 0, 0, 0, 0, 0, f, f, 0, 0, 0, 0, 0, 0, 0, 0, f, f, f, f, f, f, f, 0, 0, 0, 0, 0, 0, 0, f, 0, 0, 0, 0, 0, 0, h};
    public static final byte[] l11 = {h, b, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, 0, 0, 0, 0, 0, h};
    public static final byte[] l10 = {h, f, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, 0, 0, 0, 0, h};
    public static final byte[] l9 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, h};
    public static final byte[] l8 = {h, 0, 0, f, f, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, 0, 0, h};
    public static final byte[] l7 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, 0, f, 0, f, 0, 0, 0, f, 0, f, 0, 0, 0, 0, 0, 0, f, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, h};
    public static final byte[] l6 = {h, 0, 0, 0, 0, 0, f, f, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, b, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, f, f, 0, h};
    public static final byte[] l5 = {h, 0, 0, 0, 0, f, 0, 0, 0, f, 0, 0, 0, 0, 0, 0, 0, f, 0, 0, 0, 0, 0, 0, 0, f, f, 0, 0, 0, 0, 0, f, 0, f, f, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, h};
    public static final byte[] l4 = {h, 0, 0, 0, f, 0, 0, 0, 0, 0, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, 0, f, f, 0, 0, 0, 0, 0, 0, 0, f, f, f, f, 0, 0, 0, 0, 0, h};
    public static final byte[] l3 = {h, j, 0, 0, 0, 0, 0, b, 0, 0, 0, 0, 0, 0, 0, 0, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, h};
    public static final byte[] l2 = {h, f, f, f, 0, f, f, f, f, f, f, 0, b, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, b, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, h};
    public static final byte[] l1 = {h, g, 0, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, h};
    public byte[][] coor = {l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19, l20, l21, l22, l23, l24, l25, l26, l27, l28, l29, l30, l31, l32};
    BufferedImage im;
    Object[][] map;
    Random ranodm = new Random();
    IPath path;
    int a_ = 50;
    int b_ = a_ + 10;
    int ow = 50;
    int oh = 50;
    private IPathPointsFactory pointsFactory;

    /** Creates new form NewJFrame */
    public PathTest() {
        initComponents();
        pointsFactory = new PathPointsFactory();
    }

    private IPathPointsFactory PathPointsFactory() {
        return pointsFactory;
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(im, ow, oh, this);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(946, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addContainerGap(622, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        final int x = (evt.getX() - ow) / Path.getGrad();
        final int y = (evt.getY() - oh) / Path.getGrad();
        Thread tr = new Thread() {

            int x_ = x;
            int y_ = y;

            public void run() {
                ITrackPoint start = PathPointsFactory().Create(10, 1);
                ITrackPoint finish = PathPointsFactory().Create(getX(), map.length - 1 - y);
                path.CalculatePath(start, finish);
                repaint();
            }
        };
        tr.start();

    }//GEN-LAST:event_formMouseClicked

    public void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
    }//GEN-LAST:event_formKeyPressed

    public void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //map = new int[b_][a_];

//        for (int j = 0; j < map.length; j++) {
//            for (int i = 0; i < map[0].length; i++) {
//
//                if ((j == 0) || (j == b - 1) || (i == 0) || (i == a - 1)) {
////                    map[j][i] = Jumper.h;
//                } else {
//                    map[j][i] = ranodm.nextInt(5);
//                }
//            }
//        }
        // map[1][1] = 0;

        map = new Object[coor.length][coor[0].length];
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[0].length; i++) {

                if (coor[j][i] != 0) {
                    map[j][i] = new Object();
                }
            }
        }

        im = new BufferedImage(map[0].length * Path.getGrad(), map.length * Path.getGrad(), BufferedImage.TYPE_INT_ARGB);

        path = new PathImpl();
        repaint();

    }//GEN-LAST:event_jButton1ActionPerformed

    public void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
    }//GEN-LAST:event_formFocusGained

    public void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        repaint();
    }//GEN-LAST:event_formWindowActivated

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                PathTest f = new PathTest();
                f.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables

    class PathImpl extends Path {

        public final BufferedImage transparentImg;
        Graphics2D gr;

        public boolean FreeMapCell(Object[][] map, int x_, int y_) {
            return map[y_][x_] == null;
        }

        PathImpl() {
            super(10, map[0].length, map.length);

            gr = (Graphics2D) im.getGraphics();
            transparentImg = new BufferedImage(map[0].length * getGrad(), map.length * getGrad(), BufferedImage.TYPE_INT_ARGB);
            DrawCells();
        }

        public void DrawCells() {
            gr.setColor(Color.black);
            for (int j = 0; j < map.length; j++) {
                for (int i = 0; i < map[0].length; i++) {
                    if (map[j][i] != null) {
                        gr.drawRect(getGrad() * i + 1, Tr(getGrad() * j + getGrad() + 1), getGrad() - 2, getGrad() - 2);
                    }
                }
            }
        }

        public void RefreshGraphics() {
            im.setData(transparentImg.getData());
        }

        public void drawLine(int x1, int y1, int x2, int y2, int r, int g, int b) {
            gr.setColor(new Color(r, g, b));
            gr.drawLine(x1, y1, x2, y2);
        }

        public boolean IsMapsCellFree(int x_, int y_) {
            return FreeMapCell(map, x_, y_);
        }

        public void CalculatePath(ITrackPoint start, ITrackPoint start0, IWalker agent, IWalkAdviser iRule) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
