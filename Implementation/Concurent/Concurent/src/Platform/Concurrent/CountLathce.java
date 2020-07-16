/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Concurrent;


/**
 *
 * @author kalinin
 */
public class CountLathce extends Monitor_ {

    int n;

    @Override
    public synchronized void synchronizedWait() {
        while (n != 0) {
            Wait();
        }
    }

    @Override
    public synchronized void synchronizedRelease() {
        n--;
        if (n == 0) {
            Release();
        }
    }

    public void Set(int n_) {
        n = n_;
    }
}
