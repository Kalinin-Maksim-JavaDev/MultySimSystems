/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.Platform;

import Application.Control.IInputOwner;
import Application.View.IScreen;

/**
 *
 * @author kalinin
 */
public interface IWorkPlace {

    IScreen.UnInit getScreen();

    IInputOwner getInputOwner();

}
