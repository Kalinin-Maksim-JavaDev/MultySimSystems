/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Concurrent;

import Platform.Concurrent.IAtomicInteger;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author kalinin
 */
public class AtomicIntegerS extends AtomicInteger implements IAtomicInteger {

    public int incrementAndGet_() {
        return super.incrementAndGet();
    }

    public int decrementAndGet_() {
        return super.decrementAndGet();
    }

    public int get_() {
       return super.get();
    }


}
