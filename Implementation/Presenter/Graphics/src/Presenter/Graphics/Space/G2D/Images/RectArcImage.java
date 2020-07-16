/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Presenter.Graphics.Space.G2D.Images;

import Model.Game.Presenter.Space.Space;
import Model.Geometry.D2.Coordinates2d;
import Presenter.Graphics.Space.G2D.Space2D;
import Render.Graphics.Geometry.ICoordinates;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author kalinin
 */
public class RectArcImage extends ImageL {

    public RectArcImage(int layer_, Space space_) {
        super(layer_, space_);
    }

    @Override
    public void PutImage(ICoordinates coor) {
        int a=((Space2D)space).a;
        int b=((Space2D)space).b;
            Graphics2D bufferGraphics=((Space2D)space).bufferGraphics[layer];
        bufferGraphics.setColor(Color.red);
        bufferGraphics.drawRect((int) ((Coordinates2d) coor).x - a, Tr((int) ((Coordinates2d) coor).y) - a, a * 2, a * 2);
        bufferGraphics.setColor(Color.yellow);
        bufferGraphics.drawOval((int) ((Coordinates2d) coor).x - b, Tr((int) ((Coordinates2d) coor).y) - b, b * 2, b * 2);
    }
}