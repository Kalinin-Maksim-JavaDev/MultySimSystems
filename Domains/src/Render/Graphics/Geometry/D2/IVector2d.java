/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Render.Graphics.Geometry.D2;

import Render.Graphics.Geometry.IVectorD;

/**
 *
 * @author kalinin
 */
public interface IVector2d extends IVectorD {

    double getX();

    double getY();

    void setX(double value);

    void setY(double value);

    void multX(double k);

    void multY(double k);
}
