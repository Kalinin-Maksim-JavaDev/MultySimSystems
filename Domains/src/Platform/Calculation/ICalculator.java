/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Calculation;

import Application.ILoger;
import Platform.Concurrent.IMonitor;

/**
 *
 * @author kalinin
 */
public interface ICalculator {

    public void AddCalc(ILoger logger);

    public void Stop();

    public void Do();

    public IMonitor getMonitor();

    public ICalcSystemM getCurrent();

    public ITasksReciver getCurrentReciver();
}
