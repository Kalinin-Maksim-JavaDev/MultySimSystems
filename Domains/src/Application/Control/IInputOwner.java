/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.Control;

import MVC.Control.IInputReciver;

/**
 *
 * @author kalinin
 */
public interface IInputOwner {

    IInputOwner prepare(IInputReciver.withVPad inputReciver);

    void Listen();
}
