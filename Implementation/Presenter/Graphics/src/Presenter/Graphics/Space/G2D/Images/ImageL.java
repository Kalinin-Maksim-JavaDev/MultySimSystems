/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter.Graphics.Space.G2D.Images;

import Model.Game.Presenter.Space.Image;
import Logic.Reflections.Space.ISpace;
import Presenter.Graphics.Space.G2D.Space2D;
import Render.Graphics.Geometry.ICoordinates;

/**
 *
 * @author kalinin
 */
public abstract class ImageL extends Image {

    protected final int layer;

    public ImageL(int layer_, ISpace space_) {
        super(space_);
        layer = layer_;
    }

    public abstract void PutImage(ICoordinates coor);

    public int Tr(int y) {
        return ((Space2D) space).height - y - ((Space2D) space).grad;
    }
}
