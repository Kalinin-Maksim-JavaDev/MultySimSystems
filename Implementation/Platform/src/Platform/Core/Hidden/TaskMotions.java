/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Core.Hidden;

import Platform.Concurrent.AtomicIntegerS;
import Platform.Concurrent.Factory;
import Platform.Concurrent.IAtomicInteger;
import Platform.Concurrent.ILathce;
import Platform.Core.IMotion;
import Platform.Core.IMotionSequence;
import Platform.Core.ITaskMotions;
import Platform.Core.Motion.MotionSequence;

/**
 *
 * @author kalinin
 */
public class TaskMotions extends Thread implements ITaskMotions {

    //public boolean threadSuspended = true;
    final static public IAtomicInteger TaskMotionsCount = new AtomicIntegerS();
    final private ILathce lacthe;
    private IMotionSequence sequence;
    public boolean repeat = false;
    private Thread blinker;

    //public boolean print=false;
    public TaskMotions() {
        super();
        TaskMotionsCount.incrementAndGet_();
        sequence = new MotionSequence(1, "MotionSequence");
        lacthe = Factory.createLathce(true, true, this);
        SetBlinker(this);
    }

    @Override
    public String toString() {

        String result = "";
        IMotion mnext = sequence.getCurrent().getNext();

        while ((mnext != sequence)&&(mnext != null)) {

            result = result + " -- " + mnext.getMotionName();
            mnext = mnext.getNext();
        }

        return "TaskMotions{" + "name=" + getName() + "} motions: " + result;
    }

    public void SetBlinker(Thread tr) {
        blinker = tr;
    }

    public void Resume() {
//        if (Thread.currentThread() == tr) {
//            Tools.UnsupportedOperationException();
//        }
//        if (!threadSuspended) {
//            return;
//        }
        //  boolean threadSuspended_ = threadSuspended;
//        if (getName().contains("Render game menu")) {
//            System.out.println(getName() + " Resume");
//        }
        //System.out.println(lacthe.GetTask().toString() + " Release");
        lacthe.synchronizedRelease();

//        if (threadSuspended == threadSuspended_) {
//            //       java.lang.System.out.print("  " + Thread.currentThread().getName() + ":" + currentTimeMillis() + " " + System.this.sysName + "thread already resume" + "\n");
//        }
    }

    public String GetMotionSec() {
        String st = "";
        IMotion mn_;
        mn_ = sequence.getNext();

        while (mn_ != sequence) {
            st = st + (mn_).getMotionName() + ", " + (mn_).getCounter() + ", " + (mn_).isActive() + "\t";
            mn_ = (mn_).getNext();

        }
        return st;
    }

    public void run() {
        Thread thisThread = Thread.currentThread();
        //  java.lang.System.out.print("  " + ":" + currentTimeMillis() + " " + sysName + "Start" + "\n");

        IMotion mnext = null;
        while (blinker == thisThread) {
            while (sequence.getCurrent().getNext() == sequence) {
                //   if (Thread.currentThread().getName().equalsIgnoreCase("GameDIS")) {
                //      System.out.println("end");
                //  }
//                if (getName().contains("Render game menu")) {
//                    System.out.println(getName() + " sleep");
//                }
                //System.out.println(toString() + " Wait..");
                lacthe.synchronizedWait();
                //System.out.println(toString() + " go!");
//                if (getName().contains("Render game menu")) {
//                    System.out.println(getName() + " wakeup");
//                }
            }

            mnext = sequence.getCurrent().getNext();

//           if (print){
//               System.out.println(mnext.motionName);
//           }
            if (mnext.Iterate()) {
                IMotion prev = mnext.getPrev();

                if (sequence.TryDelMotions(mnext)) {

                    mnext = prev;

                    // if (Thread.currentThread().getName().equalsIgnoreCase("GameDIS")) {
                    //   System.out.println("After delete");
                    //   System.out.println(GetMotionSec());
                    //   }
                } else {
                    //if (Thread.currentThread().getName().equalsIgnoreCase("GameDIS")) {
                    //  System.out.println(GetMotionSec());
                    // }
                }
            }

            if (repeat) {
                sequence.Repeat_();
                repeat = false;
            } else {
                sequence.SetCurrent(mnext);
            }
            //}

        }
        TaskMotionsCount.decrementAndGet_();
        //  java.lang.System.out.print("  " + ":" + Tools.currentTimeMillis() + " " + this.getName() + "Stop" + "\n");

    }

    public void Repeat(boolean repeat_) {
        repeat = repeat_;
    }

    public Thread Getblinker() {
        return blinker;
    }

    public void AddMotions(IMotion motion_) {
        sequence.AddMotions(motion_);
    }

    public IMotionSequence GetMotionSequence() {
        return sequence;
    }

    public void SetMotionSequence(IMotionSequence motionSequence) {
        this.sequence = motionSequence;
    }

    public void motionSequenceRepeat() {
        sequence.Repeat_();
    }

    public void Say(String msg) {
    }
}
