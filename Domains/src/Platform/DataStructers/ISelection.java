/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.DataStructers;

/**
 *
 * @author kalinin
 */
public interface ISelection {

    ILinkedElement getCurrent();

    boolean getNext();

    void reset();
}
