/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Presenter.Graphics.Space.G3D;

import Model.Game.Presenter.Space.Anime;
import Logic.Reflections.Space.ISpace;

/**
 *
 * @author kalinin
 */
public class SpotLight extends Image3D implements Anime {

    public SpotLight(int x_, int y_, int z_, float yangel_, float angel_, float angelstep_,ISpace space_) {
        super( space_);
    }

    public void Move() {
    }
}
