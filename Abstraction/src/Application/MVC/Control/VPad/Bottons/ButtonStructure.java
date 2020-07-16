/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.MVC.Control.VPad.Bottons;

import Application.Build.Control.VirtualPad.IVrtualPadMotionsReciver;
import MVC.Control.IInputCollector;
import Application.Build.Control.VirtualPad.IButton;
import Application.Build.Control.VirtualPad.IBottonsChain;

/**
 *
 * @author kalinin
 */
abstract class ButtonStructure {

    int code;
    IButton nextBtn;
    IBottonsChain chain;
    IVrtualPadMotionsReciver motionsReciver;
    IInputCollector input;
}
