/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MVC.Control;

/**
 *
 * @author User
 */
public interface IInputCollector {

    public void addPressedKey(int device, int keyCode, int[] arg);

    public void addReleasedKey(int device, int keyCode, int[] arg);

    public IInputSource getInputSource();

}
