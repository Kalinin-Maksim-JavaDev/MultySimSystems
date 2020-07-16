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
public class CircleImage extends ImageL {

    public CircleImage(int layer_, Space space_) {
        super(layer_, space_);
    }

    @Override
    public void PutImage(ICoordinates coor) {
        int b = ((Space2D) space).b;
        int grad = ((Space2D) space).grad;
        Graphics2D bufferGraphics = ((Space2D) space).bufferGraphics[layer];
        bufferGraphics.setColor(Color.RED);
        bufferGraphics.drawOval((int) ((Coordinates2d) coor).x, Tr((int) ((Coordinates2d) coor).y), grad, grad);
    }
}
