/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter.Graphics.Space.G2D;

import Presenter.Graphics.Space.G2D.Images.ImageL;
import Model.Game.Presenter.Space.Space;
import Model.Geometry.D2.Coordinates2d;
import Logic.Reflections.Space.ISpace;
import Render.Graphics.Geometry.ICoordinates;
import java.awt.Color;

/**
 *
 * @author kalinin
 */
public class Text extends ImageL {

    Color color;
    public String st;
    static Color[] colorin = {Color.black, Color.GRAY, Color.ORANGE, Color.black};

    public Text(String st_, int r, int g, int b, int layer_, Space space_) {
        super(layer_, space_);
        st = st_;
        color = new Color(r, g, b);
    }

    public Text(String st_, int ic, int layer_, ISpace space_) {
        super(layer_, space_);
        st = st_;
        color = colorin[ic];
    }

    @Override
    public void PutImage(ICoordinates coor) {
//        int a = ((Space2D) space).a;
//        Graphics2D bufferGraphics = ((Space2D) space).bufferGraphics[layer];
//        bufferGraphics.setColor(color);
//        bufferGraphics.drawString(st, (int)(((Coordinates2d) coor).x) - a, Tr((int) ((Coordinates2d) coor).y) - a);

        int[] iarg = {((Space2D) space).a, (int) (((Coordinates2d) coor).x), Tr((int) ((Coordinates2d) coor).y)};
        Object[] oarg = {color, st};
        ((Space2D) space).painter.addToPictureList("Text", layer, iarg, oarg, false);

    }
}
