/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Presenter.Graphics.Space.G2D;

import Presenter.Graphics.Space.G2D.Images.ImageL;
import Model.Game.Presenter.Space.Space;
import Model.Geometry.D2.Coordinates2d;
import Render.Graphics.Geometry.ICoordinates;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author kalinin
 */

public class Frame extends ImageL {

    public Frame(int layer_, Space space_) {
        super(layer_, space_);
    }

    @Override
    public void PutImage(ICoordinates coor) {
        int a=((Space2D)space).a;
        Graphics2D bufferGraphics = ((Space2D) space).bufferGraphics[layer];
        bufferGraphics.setColor(Color.red);
        bufferGraphics.drawRect((int) ((Coordinates2d) coor).x - a, Tr((int) ((Coordinates2d) coor).y) - 2 * a, a * 3, a * 2);
    }
}