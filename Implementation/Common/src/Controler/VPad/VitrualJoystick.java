/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler.VPad;

import Platform.Core.Motion.Motion;
import Application.MVC.Control.VPad.Bottons.BottonScope;
import Application.MVC.Control.VPad.Bottons.Button;
import Application.Build.Control.VirtualPad.IVrtualPadMotionsReciver;
import MVC.Control.IInputCollector;
import Application.Build.Control.VirtualPad.IBottonsChain;
import Application.Build.Control.VirtualPad.IVitrualPad;
import Logic.Reflections.Space.IPainter;
import Presenter.Graphics.Space.G2D.Painter2D;

/**
 *
 * @author Администратор
 */
public class VitrualJoystick implements IVitrualPad {

    IBottonsChain sticik;
    IBottonsChain triggers;
    int btnwidth = 30;
    int btnheight = 30;
    int[] arg = {65, 68, 87, 83, 10, 32, 27};
    //public BufferedImage vpImg;
    //public Graphics2D vpImgGr;
    IPainter painter;
    IVrtualPadMotionsReciver motionsReciver;
    IInputCollector input;

    public VitrualJoystick(int width, int height, IVrtualPadMotionsReciver motionsReciver) {
        this.motionsReciver = motionsReciver;
//        vpImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//
//        vpImgGr = (Graphics2D) vpImg.getGraphics();
//        vpImgGr.setBackground(new Color(0, true));
//        vpImgGr.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

        int[] vp_arg = {width, height};
        painter = new Painter2D("vp", 1, 0, vp_arg);

        sticik = new BottonScope(painter, 0, 3 * height / 4, width / 4, height / 4);

        sticik.Add(new Button(arg[0], 1.0f / 5.0f, 1.0f / 2.0f, btnwidth, btnheight, sticik, motionsReciver, input));
        sticik.Add(new Button(arg[1], 4.0f / 5.0f, 1.0f / 2.0f, btnwidth, btnheight, sticik, motionsReciver, input));
        sticik.Add(new Button(arg[2], 1.0f / 2.0f, 1.0f / 5.0f, btnwidth, btnheight, sticik, motionsReciver, input));
        sticik.Add(new Button(arg[3], 1.0f / 2.0f, 4.0f / 5.0f, btnwidth, btnheight, sticik, motionsReciver, input));

        triggers = new BottonScope(painter, width / 4, 3 * height / 4, width / 4, height / 4);
        triggers.Add(new Button(arg[4], 1.0f / 4.0f, 1.0f / 3.0f, btnwidth, btnheight, triggers, motionsReciver, input));
        triggers.Add(new Button(arg[4], 2.0f / 4.0f, 1.0f / 3.0f, btnwidth, btnheight, triggers, motionsReciver, input));
        triggers.Add(new Button(arg[4], 3.0f / 4.0f, 1.0f / 3.0f, btnwidth, btnheight, triggers, motionsReciver, input));

        // vpImgGr.setClip(0, 3 * height / 4, width / 2, height - vp.h);

        sticik.DrawButtons();
        triggers.DrawButtons();
    }

    public void setInput(IInputCollector input) {
        this.input = input;
    }

    public Motion MotionFlashOval(int x, int y) {
        return new MotionFlashOval(x, y);
    }

    public void draw(Object tool) {
        painter.Draw(tool);
    }

    public boolean keyPressed(int x, int y) {
        return ((sticik != null) && sticik.keyPressed(x, y))
                || ((triggers != null) && triggers.keyPressed(x, y));
    }

    public void keyReleased() {
        sticik.keyReleased();
        triggers.keyReleased();
    }

    public void pointerDragged(int x, int y) {
        sticik.pointerDragged(x, y);
        triggers.pointerDragged(x, y);
    }

    private class MotionFlashOval extends Motion {

        private int x;
        private int y;
        //Graphics2D vpImgGr_ = (Graphics2D) vpImg.getGraphics();

        public MotionFlashOval(int x, int y) {
            super(20, "flashOval");
            this.x = x;
            this.y = y;

        }

        @Override
        public void MotionMethod() {

            int[] arg_ = {getCounter(), x, y};

            painter.addToPictureList("FlashOval", 0, arg_, null, false);
        }
    }
}
