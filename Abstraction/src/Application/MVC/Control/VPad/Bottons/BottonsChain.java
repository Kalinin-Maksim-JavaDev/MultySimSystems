/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.MVC.Control.VPad.Bottons;

import Application.Build.Control.VirtualPad.IBottonsChain;
import Logic.Reflections.Space.IPainter;

/**
 *
 * @author kalinin
 */
public abstract class BottonsChain implements IBottonsChain {

    IPainter painter;

    Buttons btns = new Buttons();

    public BottonsChain(IPainter painter) {
        this.painter = painter;
    }
}
