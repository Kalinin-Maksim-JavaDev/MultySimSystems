/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Logic.Model.Game.World.PathFindng;

import Render.Graphics.Geometry.IVectorD;

/**
 *
 * @author ASUS
 */
public interface IVectorsFactory {

    IVectorD NewVector(double x, double y);
}
