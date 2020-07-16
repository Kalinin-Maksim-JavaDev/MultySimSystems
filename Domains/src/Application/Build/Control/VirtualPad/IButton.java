/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.Build.Control.VirtualPad;

/**
 *
 * @author kalinin
 */
public interface IButton {

    void Draw(boolean clear);

    void keyPressed();

    void keyReleased();

    void SetNext(IButton btn);

    IButton getNext();
}
