/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Concurrent;

/**
 *
 * @author kalinin
 */
public interface IAtomicReferenceObject {

    public void set(Object object);

    public Object get();

    public boolean compareAndSet(Object prevToCheck, Object unit);
}
