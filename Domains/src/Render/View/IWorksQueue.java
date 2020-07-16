/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Render.View;

/**
 *
 * @author kalinin
 */
public interface IWorksQueue {

    void flush(boolean b);

    void fill(int layerID);

    int size();

    boolean isEmpty();
}
