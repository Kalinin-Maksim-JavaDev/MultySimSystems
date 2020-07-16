/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import Presenter.Graphics.Space.G2D.Space2D;


/**
 *
 * @author kalinin
 */
public abstract class Space2DShared extends Space2D {

    abstract void Render(int layer);

    public final int layer;

    public Space2DShared(int layersCount_, int width_, int height_, int grad_, int quant_, String spaceName_, int yLoger_, int layer_) {
        super(layersCount_, width_, height_, grad_, quant_, spaceName_, yLoger_);
        layer = layer_;
    }

    public void Render() {
        //Draw(tool); //tool<-Space.im[0]<-Space.im[1]<-...
        Render(layer);
        //PostDraw();
    }
}