/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter.Graphics.Space.G2D;

import Presenter.Graphics.Space.G2D.Images.ImageL;
import Model.Geometry.D2.Coordinates2d;
import Logic.Reflections.Space.ISpace;
import Render.Graphics.Geometry.ICoordinates;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author kalinin
 */
public class FillFrame extends ImageL {

    int period;

    public FillFrame(int layer_, ISpace space_) {
        super(layer_, space_);
    }

    @Override
    public void PutImage(ICoordinates coor) {
        int a = 15;
        Graphics2D bufferGraphics = ((Space2D) space).bufferGraphics[layer];
        // bufferGraphics[layer].setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        bufferGraphics.setColor(Color.GREEN);

        int c = Math.round((float) a * 2);

        bufferGraphics.fill(new Rectangle((int) ((Coordinates2d) coor).x, Tr((int) ((Coordinates2d) coor).y), 4 * c, 5 * c));
        //bufferGraphics[0].drawRect((int) ((Coordinates2d) coor).x - a, Tr((int) ((Coordinates2d) coor).y) -2* a,  c,  c);

    }
}
