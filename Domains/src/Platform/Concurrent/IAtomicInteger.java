/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Platform.Concurrent;

/**
 *
 * @author kalinin
 */
public interface IAtomicInteger {

    public int incrementAndGet_();

    public int decrementAndGet_();

    public int get_();

}
