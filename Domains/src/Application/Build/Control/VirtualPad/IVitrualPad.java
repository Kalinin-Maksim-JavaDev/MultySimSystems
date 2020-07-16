/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.Build.Control.VirtualPad;

import MVC.Control.IInputCollector;

/**
 *
 * @author kalinin
 */
public interface IVitrualPad {

    public boolean keyPressed(int x, int y);

    public void keyReleased();

    public void pointerDragged(int x, int y);

    void draw(Object tool);

    void setInput(IInputCollector inputCollector);
}
