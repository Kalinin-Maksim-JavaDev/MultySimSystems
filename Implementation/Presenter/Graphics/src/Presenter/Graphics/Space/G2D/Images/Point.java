/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Presenter.Graphics.Space.G2D.Images;

import Model.Geometry.D2.Coordinates2d;
import Logic.Reflections.Space.ISpace;
import Presenter.Graphics.Space.G2D.Space2D;
import Render.Graphics.Geometry.ICoordinates;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author kalinin
 */
public class Point extends ImageL {

    public Color color;
     static Color[] colorin={Color.black,Color.GRAY,Color.ORANGE,Color.black};
    public Point(int layer_, int r,int g, int b, ISpace space_) {
        super(layer_, space_);
        color =new Color(r,g,b);
    }

    public Point(int ic, int layer_, ISpace space_) {
        super(layer_, space_);
        color = colorin[ic];
    }

    @Override
    public void PutImage(ICoordinates coor) {
        int b=((Space2D)space).b;
        Graphics2D bufferGraphics=((Space2D)space).bufferGraphics[layer];
        bufferGraphics.setColor(color);
        bufferGraphics.drawRect((int) ((Coordinates2d) coor).x, Tr((int) ((Coordinates2d) coor).y), 0, 0);
    }
}
