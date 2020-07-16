/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Core.Hidden;

import Platform.Core.IConveer;
import Platform.Core.ISystemM;
import Platform.Core.Motion.EndLessMotion;

/**
 *
 * @author kalinin
 */
public class MotionRun extends EndLessMotion {

    boolean dead = false;
    private final IConveer conveer;

    public MotionRun(String motionName_, IConveer conveer) {
        super(motionName_);
        this.conveer = conveer;

    }

    @Override
    public void EndLessMethod() {
        ConveerTrue conveerTrue = (ConveerTrue) conveer;
        //     if (!timercancel) {

        // if (conveer.timer.processPause) {
        //    conveer.mod.taskMotions.Resume();
        //  } else {
        //     java.lang.System.out.print(itr.t + Thread.currentThread().getName() + ":" + currentTimeMillis() + " " + " time" + "\n");
        //  if (itr.LastItterationComplite()) {

        if (dead) {
            return;
            //  System.out.println(conveer.mod.taskMotions.getName());
        }

        conveerTrue.monitor.synchronizedWait();


        if (((MainTimer) conveerTrue.timer).stop) {
            conveerTrue.dis.Stop();
            conveerTrue.mod.Stop();
            conveerTrue.loger.Stop();
            conveerTrue.timer.Stop();
            dead = true;
            //   conveer.timer.taskMotions.print=true;
        } else {
            //       System.out.println("Time");
            //                            if (timer.GetCancelState()) {
            //
            //
            //                            } else {
            //                            if (timer.GetCancelState()) {
            //
            //
            //                            } else {

            ISystemM modSystemM = ((ISystemM) conveerTrue.mod);

            conveerTrue.RegBeg(Thread.currentThread().getName(), "Timer", " time---------------------------------------------------- ");
            //java.lang.System.out.print(itr.t + Thread.currentThread().getName() + ":" + currentTimeMillis() + " " + " time------------------------------------------------------------------------" + "\n");
            modSystemM.AddMotions(conveerTrue.motionModREPEAT.GetMotion(1));
            //      java.lang.System.out.print(timerCounter + Thread.currentThread().getName() + ":" + currentTimeMillis() + " " + "Timer " + " add to mod EndTurn" + "\n");
            modSystemM.Resume();
            conveerTrue.RegEnd(Thread.currentThread().getName(), "Timer", " time---------------------------------------------------- ");
            // }
            //  }
            try {
                Thread.sleep(conveerTrue.artquant);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            if (conveerTrue.GetTimer().autorepeat) {
                conveer.GetTimer().AddMotions(conveerTrue.GetTimer().motionRepeat.GetMotion(1));
                //  conveer.timer.Repeat(true);
            } else {
            }
        }
        //   conveer.Say(String.valueOf(conveer.m));
        //  }

    }
}
