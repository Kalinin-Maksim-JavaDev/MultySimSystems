/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Game.Presenter.Space;

import Logic.Reflections.Space.ICommonProjectionInfo;

/**
 *
 * @author Adm
 */
public abstract class CommonProjectionInfo implements ICommonProjectionInfo {

    protected double viewOffsetX;
    protected double viewOffsetY;

    public double getViewOffsetX() {
        return viewOffsetX;
    }

    public void setViewOffsetX(double viewOffsetX) {
        this.viewOffsetX = viewOffsetX;
    }

    public double getViewOffsetY() {
        return viewOffsetY;
    }

    public void setViewOffsetY(double viewOffsetY) {
        this.viewOffsetY = viewOffsetY;
    }
}
