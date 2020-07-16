/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Presenter.Graphics.Space.G3D;

import Model.Game.Presenter.Space.Space;

/**
 *
 * @author kalinin
 */
public class AnimeImage3D extends Image3D {

    Sprite3D_ image;
    int time;

    public AnimeImage3D(Sprite3D_ image_, int time_,Space space_) {
        super(space_);
    }
}
