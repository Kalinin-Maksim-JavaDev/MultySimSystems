/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Platform.Concurrent;

/**
 *
 * @author kalinin
 */
public interface IMonitor {

    void synchronizedRelease();

    void synchronizedWait();

    void Release();

    void Wait();

}
