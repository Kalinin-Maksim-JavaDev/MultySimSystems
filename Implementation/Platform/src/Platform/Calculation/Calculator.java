/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Calculation;

import Application.ILoger;
import Platform.Concurrent.AtomicIntegerS;

/**
 *
 * @author Adm
 */
public class Calculator implements ICalculator {

    private final AtomicIntegerS calcCounter;
    private final Monitor_Calculator monitor;
    private ICalcSystemM current;

    public Calculator() {
        calcCounter = new AtomicIntegerS();
        calcCounter.set(0);
        monitor = new Monitor_Calculator(this);
    }

    public void AddCalc(ILoger logger) {

        ICalcSystemM newcurrent = new CalcSystemM(this, getCalcCounter().get(), logger);
        //   calcnum++;
        //   newcurrent.taskMotions.setName("calc");
        if (getCurrent() == null) {
            setCurrent(newcurrent);
            getCurrent().setNext(getCurrent());
        } else {
            newcurrent.setNext(getCurrent().getNext());
            getCurrent().setNext(newcurrent);
            setCurrent(newcurrent);
        }

        // current.CreateMotionBeg();
        monitor.calcnum = getCalcCounter().incrementAndGet();
        getCurrent().Start();

    }

    public void Stop() {
        ICalcSystemM c_ = getCurrent();
        do {
            c_.Stop();
            c_ = (ICalcSystemM) c_.getNext();
        } while (c_ != getCurrent());


    }

    public void Do() {
        ICalcSystemM first = getCurrent();
        ICalcSystemM curr_ = first;
        do {
            curr_.AddRepeat();
            curr_.AddCalcEnd();
            //curr_ = curr_.next;
            //} while (curr_ != first);
            //            int c = modcalc.calcCounter.addAndGet(modcalc.calcnum);
            //            if (c != modcalc.calcnum) {
            //                Tools.UnsupportedOperationException();
            //            }
            //  first = modcalc.current;
            // curr_ = first;
            //   do {
            curr_.Resume();
            curr_ = curr_.getNext();
        } while (curr_ != first);
    }

    /**
     * @return the calcCounter
     */
    public AtomicIntegerS getCalcCounter() {
        return calcCounter;
    }

    /**
     * @return the monitor
     */
    public Monitor_Calculator getMonitor() {
        return monitor;
    }

    /**
     * @return the current
     */
    public ICalcSystemM getCurrent() {
        return current;
    }

    /**
     * @param current the current to set
     */
    public void setCurrent(ICalcSystemM current) {
        this.current = current;
    }

    public ITasksReciver getCurrentReciver() {
        return (CalcSystemM) current;
    }
}
