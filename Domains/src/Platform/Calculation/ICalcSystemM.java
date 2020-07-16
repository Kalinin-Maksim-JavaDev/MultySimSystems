/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Calculation;

/**
 *
 * @author kalinin
 */
public interface ICalcSystemM {

    void setNext(ICalcSystemM current);

    ICalcSystemM getNext();

    void Start();

    void Stop();

    void AddRepeat();

    void AddCalcEnd();

    void Resume();
}
