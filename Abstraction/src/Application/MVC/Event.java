/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.MVC;

import MVC.Control.IInputEvent;

/**
 *
 * @author User
 */
public class Event implements IInputEvent {

    private int device;
    private int keyCode;
    private int[] arg;

    public Event(int device_, int keyCode_, int[] arg_) {
        device = device_;
        keyCode = keyCode_;
        arg = arg_;
    }

    /**
     * @return the device
     */
    public int getDevice() {
        return device;
    }

    /**
     * @param device the device to set
     */
    public void setDevice(int device) {
        this.device = device;
    }

    /**
     * @return the keyCode
     */
    public int getKeyCode() {
        return keyCode;
    }

    /**
     * @param keyCode the keyCode to set
     */
    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    /**
     * @return the arg
     */
    public int[] getArg() {
        return arg;
    }

    /**
     * @param arg the arg to set
     */
    public void setArg(int[] arg) {
        this.arg = arg;
    }
}
