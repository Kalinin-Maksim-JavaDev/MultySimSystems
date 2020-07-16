/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Concurrent;

import Platform.Core.ITaskMotions;

/**
 *
 * @author kalinin
 */
public class Factory {

    public static ILathce createLathce(boolean initState_, boolean allowedDoubleRelease_) {
        return new Lathce(initState_, allowedDoubleRelease_);
    }

    public static ILathce createLathce(boolean initState_, boolean allowedDoubleRelease_, ITaskMotions task) {
        return new Lathce(initState_, allowedDoubleRelease_);
    }

    public static ILathceStop createLathceStop(boolean initState_) {
        return new LathceStop(initState_);
    }

    public static IAtomicReferenceObject newAtomicReferenceObject() {
        return new AtomicReferenceObject();
    }

    public static IAtomicInteger newAtomicIntegerS() {
        return new AtomicIntegerS();
    }

    public static IMonitor createMonitor(final IMonitorBody body) {
        return new Monitor_() {

            public void synchronizedRelease() {
                body.synchronizedRelease();
            }

            public void synchronizedWait() {
                body.synchronizedWait();
            }
        };
    }
}
