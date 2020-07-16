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
import java.awt.Rectangle;

/**
 *
 * @author kalinin
 */
public class AnimeFrame extends AnimeImage2D {

    int period;

    public AnimeFrame(int period_, int layer_, int time_, ISpace space_) {
        super(null, layer_, time_, space_);
        period = period_;
    }

    public AnimeFrame(int period_, int layer_, ISpace space_) {
        this(period_, layer_, 0, space_);
    }

    @Override
    public void PutImage(ICoordinates coor) {
        int a = 15;
        int quant = ((Space2D) space).quant;
        Graphics2D bufferGraphics = ((Space2D) space).bufferGraphics[layer];
        bufferGraphics.setColor(Color.GREEN);
        IncTime(quant);
        int c = Math.round((float) a * 2 * time / (float) (period));


        bufferGraphics.fill(new Rectangle((int) ((Coordinates2d) coor).x, Tr((int) ((Coordinates2d) coor).y), 4 * c, 5 * c));
        //bufferGraphics[0].drawRect((int) ((Coordinates2d) coor).x - a, Tr((int) ((Coordinates2d) coor).y) -2* a,  c,  c);

    }
}
