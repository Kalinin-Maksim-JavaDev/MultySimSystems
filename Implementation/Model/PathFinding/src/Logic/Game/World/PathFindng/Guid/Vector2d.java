/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Game.World.PathFindng.Guid;

import Render.Graphics.Geometry.D2.IVector2d;
import Render.Graphics.Geometry.IVectorD;

/**
 *
 * @author kalinin
 */
public class Vector2d implements IVector2d {

    public double x, y;

    public Vector2d(double x_, double y_) {
        x = x_;
        y = y_;
    }

    public void SetFrom(IVectorD v_) {
        x = ((Vector2d) v_).x;
        y = ((Vector2d) v_).y;
    }

    public void Add(IVectorD v_) {
        x += ((Vector2d) v_).x;
        y += ((Vector2d) v_).y;
    }

    public void Sub(IVectorD v_) {
        x -= ((Vector2d) v_).x;
        y -= ((Vector2d) v_).y;
    }

    public void Norm() {
        double m = Module();
        x /= m;
        y /= m;
    }

    public void ScalMull(double k) {
        x *= k;
        y *= k;
    }

    public double Module() {
        return Math.sqrt(x * x + y * y);
    }

    public void SetNull() {
        x = 0;
        y = 0;
    }

    public boolean IsNull() {
        return ((x == 0) && (y == 0));
    }

    public String toString() {
        return x + ", " + y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double value) {
        x = value;
    }

    public void setY(double value) {
        y = value;
    }

    public void multX(double k) {
        x *= k;
    }

    public void multY(double k) {
        y *= k;
    }
}
