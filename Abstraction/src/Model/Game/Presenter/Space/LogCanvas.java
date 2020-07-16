/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.Game.Presenter.Space;

import Platform.ILogCanvas;

/**
 *
 * @author User
 */
public abstract class LogCanvas implements ILogCanvas{

    public abstract void Refresh();

    public  abstract void drawLine(int x1, int y1, int x2, int y2, int r, int g, int b);

}
