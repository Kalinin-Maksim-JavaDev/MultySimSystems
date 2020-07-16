/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Application.Build.Control.VirtualPad;

/**
 *
 * @author kalinin
 */
public interface IBottonsChain {

    void Add(IButton btn);

    void DrawButtons();

    void keyReleased();

    boolean keyPressed(int x, int y);

    void pointerDragged(int x, int y);

}
