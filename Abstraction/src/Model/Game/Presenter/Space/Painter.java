/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Game.Presenter.Space;

import Logic.Reflections.Space.IPainter;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public abstract class Painter implements IPainter {

    protected ArrayList[] picturesList;

    public ArrayList[] getPictureList() {
        return picturesList;
    }

    public void setPictureList(ArrayList[] picturesList) {
        this.picturesList = picturesList;
    }
}
