/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Logic.Reflections.Space;

import java.util.ArrayList;

/**
 *
 * @author kalinin
 */
public interface IPainter {

    void Draw(Object tool);

    void addToPictureList(String disription, int layer, int[] iarg, Object[] oarg, final boolean debug);

    /**
     * @return the pictureQueue
     */
    ArrayList[] getPictureList();

    /**
     * @param pictureQueue the pictureQueue to set
     */
    void setPictureList(ArrayList[] pictureQueue);

}
