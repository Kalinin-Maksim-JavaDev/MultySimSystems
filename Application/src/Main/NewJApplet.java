/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Application.Control.IInputOwner;
import Application.View.IScreen;
import Application.Platform.IWorkPlace;
import MVC.Control.IInputReciver;
import Render.View.IPictureQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

/**
 *
 * @author kalinin
 */
public class NewJApplet extends JApplet implements IWorkPlace, IScreen, IScreen.UnInit, IInputOwner {

    public BufferedImage target;
    public Graphics2D targetG;
    public JPanel pane = new JPanel();
    public JPanel pane2 = new JPanel();
    private MouseAdapter ml;
    private MouseMotionAdapter mml;
    IInputReciver.withVPad inputReciver;

    /**
     * Initialization method that will be called after the applet is loaded
     * into the browser.
     */
    @Override
    public void init() {


        add(pane);
        add(pane2);
        GetPanel().setSize(480, 320);
        target = new BufferedImage(GetPanel().getWidth(), GetPanel().getHeight(), BufferedImage.TYPE_INT_ARGB);
        //  target.setRenderingHints(rh);
        targetG = (Graphics2D) target.getGraphics();

//        RenderingHints rh = new RenderingHints(
//                RenderingHints.KEY_TEXT_ANTIALIASING,
//                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //targetG.setRenderingHints(rh);
        targetG.setFont(new Font(null, 0, 10));
    }

    public IScreen prepare() {
        init();
        return this;

    }

    public IInputOwner prepare(IInputReciver.withVPad inputReciver) {
        setInputReciver(inputReciver);
        return this;
    }
    // TODO overwrite start(), stop() and destroy() methods

    public JPanel GetPanel() {

        return pane;
    }

    public JPanel GetPanel2() {

        return pane2;
    }

    public Object getGraphicTool() {
        return targetG;
    }

    public void update(boolean simple) {
        inputReciver.drawPad(targetG);
        //panelGr.drawImage(target, 0, 0, GetPanel());
        //targetG.clearRect(0, 0, GetPanel().getWidth(), GetPanel().getHeight());
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(target, 0, 0, GetPanel());
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

    public IInputReciver.withVPad getInput() {
        return inputReciver;
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
                inputReciver.keyPressed(e.getKeyCode(), new int[4]);

            }

            @Override
            public void keyReleased(KeyEvent e) {
                inputReciver.keyReleased(e.getKeyCode(), new int[4]);
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
}
