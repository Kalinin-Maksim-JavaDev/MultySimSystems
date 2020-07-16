package Platform.Core.Motion;

import Global.Tools;
import Platform.Core.IExecuter;
import Platform.Core.IMotion;
import Platform.Core.IMotionReciver;
import Platform.Util.Profiler.observercode.Diagram.ThreadRegisters;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalinin
 */
public abstract class Motion implements IMotion {

    private String motionName;
    private int counter;
    protected IMotion next;
    private IMotion prev;
    private boolean insec;
    private IMotionReciver reciver;


//
//    public Motion(int iterates_, Object[] arg) {
//        this();
//        counter = iterates_;
//    }
    public Motion(int iterates, String name) {
        if (iterates == 0) {
            Tools.UnsupportedOperationException();
        }
        if (name == null) {
            Tools.UnsupportedOperationException();
        }
        if (name == "") {
            Tools.UnsupportedOperationException();
        }
        counter = iterates;
        motionName = name;
    }

    public boolean Iterate() {
        if (getCounter() != 0) {
            Tools.Pause1();
            ThreadRegisters.motionName.put(Thread.currentThread().getName(), getMotionName());
            MotionMethod();
            ThreadRegisters.motionName.put(Thread.currentThread().getName(), null);
            decCounter();
        }
        return getCounter() == 0;
    }

    public abstract void MotionMethod();

    /**
     * @return the motionName
     */
    public String getMotionName() {
        return motionName;
    }

    /**
     * @param motionName the motionName to set
     */
    public void setMotionName(String motionName) {
        this.motionName = motionName;
    }

    /**
     * @return the next
     */
    public IMotion getNext() {
        return next;
    }

    /**
     * @param next the next to set
     */
    public void setNext(IMotion next) {
        this.next = next;
    }

    /**
     * @return the prev
     */
    public IMotion getPrev() {
        return prev;
    }

    /**
     * @param prev the prev to set
     */
    public void setPrev(IMotion prev) {
        this.prev = prev;
    }

    /**
     * @return the insec
     */
    public boolean isActive() {
        return insec;
    }

    public boolean isFinal() {
        return counter == 1;
    }

    /**
     * @param insec the insec to set
     */
    public void deativate() {
        this.insec = false;
    }

    public void ativate() {
        this.insec = true;
    }

    /**
     * @return the counter
     */
    public int getCounter() {
        return counter;
    }

    /**
     * @param counter the counter to set
     */
    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void incCounter() {
        counter++;
    }

    void decCounter() {
        counter--;
    }

    public IMotionReciver add(IMotionReciver reciver) {
        this.reciver = reciver;
        this.reciver.AddMotions(this);
        return this.reciver;
    }

    public IMotionReciver getReciver() {
        if (reciver == null) {
            Tools.UnsupportedOperationException("A motion must be added per 'add' method");

        }
        return reciver;
    }

    public void useNext(IMotion motion) {

        getReciver().AddMotions(motion);
    }
}
