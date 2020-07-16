/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Calculation;

import Application.ILoger;
import Platform.Core.IMotion;
import Platform.Core.Motion.Motion;
import Platform.Core.Motion.MotionFabric;
import Platform.Core.Systems.SystemM;

/**
 *
 * @author Adm
 */
public class CalcSystemM extends SystemM implements ICalcSystemM,ITasksReciver {

    final Calculator calculator;
    //public int calccounter = 0;
    final public MotionFabric endCalc;
    final public MotionFabric repeatCalc;
    final public int n;
    final ILoger loger;
    private ICalcSystemM next;

    public CalcSystemM(Calculator calculator_, int n_, ILoger loger) {
        super("C" + n_ + " |");
        n = n_;
        calculator = calculator_;
        endCalc = new MotionFactoryEndCalc(1, this);
        repeatCalc = new MotionFactoryRepeatCalc(1, this);
        this.loger = loger;
        trname = SetThreadName();
    }

    public String SetThreadName() {
        return "Calc" + String.valueOf(n);
    }

    public void AddCalcEnd() {
        AddMotions(endCalc.GetMotion(1));
    }

    public void AddRepeat() {
        AddMotions(repeatCalc.GetMotion(1));
    }

    public void AddTask(final IWork task) {
        AddMotions(new Motion(1, "") {

            @Override
            public void MotionMethod() {
                task.body();
            }
        });
    }

    /**
     * @return the next
     */
    public ICalcSystemM getNext() {
        return next;
    }

    /**
     * @param next the next to set
     */
    public void setNext(ICalcSystemM next) {
        this.next = next;
    }

    class MotionFactoryEndCalc extends MotionFabric {

        CalcSystemM csM;

        public MotionFactoryEndCalc(int iterates_, CalcSystemM csM_) {
            super(iterates_);
            csM = csM_;
        }

        public IMotion NewMotion(int iterates_) {
            return new MotionEndCalc(1, "EndCalc", this);
        }
    }

    class MotionEndCalc extends Motion {

        MotionFactoryEndCalc mf;

        public MotionEndCalc(int iterates_, String motionName_, MotionFactoryEndCalc mf_) {
            super(iterates_, motionName_);
            mf = mf_;
        }

        public void MotionMethod() {
            //System.out.println("  " + ":" + currentTimeMillis() + " " + sysName + taskMotions.getName() + " End");
            // int c = mf.csM.calculator.calcCounter.decrementAndGet();
            // if (c == 0) {
            loger.RegBeg("end calc ");
            mf.csM.calculator.getMonitor().synchronizedRelease();
            loger.RegEnd("end calc ");

            // }
        }
    }

    class MotionFactoryRepeatCalc extends MotionFabric {

        CalcSystemM csM;

        public MotionFactoryRepeatCalc(int iterates_, CalcSystemM csM_) {
            super(iterates_);
            csM = csM_;
        }

        public IMotion NewMotion(int iterates_) {
            return new MotionRepeatCalc(1, "RepeatCalc", this);
        }
    }

    class MotionRepeatCalc extends Motion {

        MotionFactoryRepeatCalc mf;

        public MotionRepeatCalc(int iterates_, String motionName_, MotionFactoryRepeatCalc mf_) {
            super(iterates_, motionName_);
            mf = mf_;
        }

        public void MotionMethod() {
            loger.RegBeg("repeat calc ");
            mf.csM.Repeat(true);
            loger.RegEnd("repeat calc ");

        }
    }
}
