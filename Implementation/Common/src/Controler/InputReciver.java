/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;

import Application.Build.Control.VirtualPad.IVitrualPad;
import Application.Build.Control.VirtualPad.IVrtualPadMotionsReciver;
import MVC.Control.IInputCollector;
import MVC.Control.IInputReciver;
import Controler.VPad.VitrualJoystick;
import MVC.Control.IInputReciver.withVPad;

/**
 *
 * @author dkx6r0c
 */
public abstract class InputReciver implements IInputReciver, IInputReciver.withVPad {

    abstract protected IVrtualPadMotionsReciver getVrtualPadMotionsReciver();

    abstract protected IInputCollector getInputCollector();
    private IVitrualPad pad;

    public withVPad setPad(IVitrualPad pad) {
        this.pad = pad;
        return this;
    }

    private IVitrualPad getpad() {
        return pad;
    }

    public void keyPressed(int keyCode, int[] arg) {
        getInputCollector().addPressedKey(0, keyCode, arg);
    }

    public void keyReleased(int keyCode, int[] arg) {
        getInputCollector().addReleasedKey(0, keyCode, arg);
    }

    public void pointerPressed(int code, int x, int y) {
        if (getpad().keyPressed(x, y)) {
        } else {
            int[] arg = new int[4];
            arg[0] = (int) (x);
            arg[1] = (int) (y);
            getInputCollector().addPressedKey(1, code, arg);
            if (getVrtualPadMotionsReciver() != null) {
                getVrtualPadMotionsReciver().AddMotions(((VitrualJoystick) getpad()).MotionFlashOval(x, y));
            }
        }
    }

    public void pointerReleased(int button, int x, int y) {

        getpad().keyReleased();

        int[] arg = new int[4];
        arg[0] = x;
        arg[1] = y;
        arg[2] = 0;
        arg[3] = 0;
        getInputCollector().addReleasedKey(1, button, arg);
    }

    public void pointerDragged(int button, int x1, int y1, int x2, int y2) {
        // System.out.println(x+", "+y);
        int[] arg = new int[4];
        arg[0] = x1;
        arg[1] = y1;
        arg[2] = x2;
        arg[3] = y2;
        getInputCollector().addPressedKey(1, button, arg);
        getInputCollector().addReleasedKey(1, button, arg);
        if (getpad() != null) {
            getpad().pointerDragged(x2, y2);

            getpad().pointerDragged(x2, y2);
        }
    }

    public void mouseMoved(int button, int x, int y) {
        int[] arg = new int[4];
        arg[0] = x;
        arg[1] = y;
        arg[2] = 0;
        arg[3] = 0;
        getInputCollector().addPressedKey(1, button, arg);
        getInputCollector().addReleasedKey(1, button, arg);
    }

    public void drawPad(Object tool) {
        getpad().draw(tool);
    }
}
