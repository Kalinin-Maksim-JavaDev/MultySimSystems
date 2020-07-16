/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Game.Presenter.Space;

import Logic.Reflections.Space.IImage;
import Logic.Reflections.Space.ISpace;

/**
 *
 * @author kalinin
 */
public abstract class Image implements IImage {

    public ISpace space;

    public Image(ISpace space_) {
        space = space_;
    }
}
