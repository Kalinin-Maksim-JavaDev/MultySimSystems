/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;

import Platform.Core.IArgument;

/**
 *
 * @author kalinin
 */
public class Coordinates2dArgument implements IArgument {

    public int x;
    public int y;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
