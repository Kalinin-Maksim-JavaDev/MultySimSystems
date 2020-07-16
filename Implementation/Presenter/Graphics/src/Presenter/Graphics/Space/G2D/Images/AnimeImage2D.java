/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter.Graphics.Space.G2D.Images;

import Model.Geometry.D2.Coordinates2d;
import Logic.Reflections.Space.ISpace;
import Presenter.Graphics.Space.G2D.Space2D;
import Render.Graphics.Geometry.ICoordinates;

/**
 *
 * @author kalinin
 */
public class AnimeImage2D extends ImageL {

    public final Sprite2D_ image;
    int time;

    public AnimeImage2D(Sprite2D_ image_, int layer_, int time_, ISpace space_) {
        super(layer_, space_);
        image = image_;
        time = time_;
    }

    public void IncTime(int quant_) {
        time += quant_;
        if (time >= image.period) {
            time = 0;
        }
    }

    public AnimeImage2D(Sprite2D_ image_, int layer_,ISpace space_) {
        this(image_, layer_, 0, space_);
    }

    @Override
    public void PutImage(ICoordinates coor) {
        int quant = ((Space2D) space).quant;
        //Graphics2D bufferGraphics=((Space2D)space).bufferGraphics[layer];
        IncTime(quant);
        //bufferGraphics.drawImage(image.GetFrame(time),(int) ((Coordinates2d) coor).x, Tr((int)((Coordinates2d) coor).y), null);

        int[] iarg = {time, (int) ((Coordinates2d) coor).x, Tr((int) ((Coordinates2d) coor).y)};
        Object[] oarg = {image};
        ((Space2D) space).painter.addToPictureList("AnimeImage2D", layer, iarg, oarg, false);
    }
}
