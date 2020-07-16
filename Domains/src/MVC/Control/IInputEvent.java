/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MVC.Control;

/**
 *
 * @author kalinin
 */
public interface IInputEvent {

    /**
     * @return the arg
     */
    int[] getArg();

    /**
     * @return the device
     */
    int getDevice();

    /**
     * @return the keyCode
     */
    int getKeyCode();

    /**
     * @param arg the arg to set
     */
    void setArg(int[] arg);

    /**
     * @param device the device to set
     */
    void setDevice(int device);

    /**
     * @param keyCode the keyCode to set
     */
    void setKeyCode(int keyCode);

}
