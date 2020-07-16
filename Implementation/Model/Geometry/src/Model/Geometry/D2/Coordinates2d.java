package Model.Geometry.D2;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import Render.Graphics.Geometry.ICoordinates;

/**
 *
 * @author kalinin
 */
public class Coordinates2d implements ICoordinates {

    public double x;
    public double y;
    public double xd;
    public double yd;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setXd(double Xd) {
        this.xd = Xd;
    }

    public void setYd(double Yd) {
        this.yd = Yd;
    }

    public double getXd() {
        return xd;
    }

    public double getYd() {
        return yd;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
