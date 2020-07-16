package Platform.Concurrent;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Global.Tools;
import Platform.Core.ITaskMotions;

/**
 *
 * @author kalinin
 */
class Lathce extends LathceStructure implements ILathce {

    ITaskMotions task;

    public Lathce(boolean initState_, boolean allowedDoubleRelease_, ITaskMotions task) {
        this(initState_, allowedDoubleRelease_);
        this.task = task;
    }

    Lathce(boolean initState_, boolean allowedDoubleRelease_) {
        monitor = new Monitor_() {

            public synchronized void synchronizedWait() {
                if (ownerThreadName.isEmpty()) {
                    ownerThreadName = Thread.currentThread().getName();
                } else {
                    if (!ownerThreadName.equals(Thread.currentThread().getName())) {
                        Tools.UnsupportedOperationException();
                    }
                }
                while (locked) {
                    Wait();
                }
                locked = true;

            }

            public synchronized void synchronizedRelease() {
                master = Thread.currentThread().getName();
                if (!ownerThreadName.isEmpty()) {
                    //   diargamma.AddAccociation(master.replaceAll(" ", "").replaceAll("-", ""), slave.replaceAll(" ", "").replaceAll("-", ""), 1,"","");
                }
                if ((!locked) && (!allowedDoubleRelease)) {

                    System.out.println("Double release. Is unlocked!");
                    Tools.UnsupportedOperationException();
                }
                locked = false;
                Release();

            }
        };
        locked = initState_;
        allowedDoubleRelease = allowedDoubleRelease_;

    }

    public boolean isLocked() {

        return locked;
    }

    public void Release() {
        monitor.Release();
    }

    public void Wait() {
        monitor.Wait();
    }

    public void synchronizedRelease() {
        monitor.synchronizedRelease();
    }

    public void synchronizedWait() {
        monitor.synchronizedWait();
    }

    public ITaskMotions GetTask() {
        return task;
    }
}
