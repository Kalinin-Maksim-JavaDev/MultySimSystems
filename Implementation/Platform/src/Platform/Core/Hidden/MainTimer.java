/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Core.Hidden;

import Platform.Core.Motion.MotionFabric;
import Platform.Core.Systems.SystemM;

/**
 *
 * @author Администратор
 */
public class MainTimer extends SystemM {

    final ConveerTrue conveer;
    final MotionFabric motionRepeat;
    
    boolean stop = false;
    MotionRun motionRun;
    public boolean autorepeat;
    //boolean processPause_=false;
//    Monitor_ monitorProcessPause = new Monitor_() {
//
//
//        public void synchronizedWait() {
//            while(processPause_){
//                Wait();
//            }
//        }
//
//        public void synchronizedRelease() {
//           processPause_=false;
//           Release();
//        }
//    };

    public MainTimer(ConveerTrue conveer_) {
        super("T\t|");
        autorepeat = true;
        conveer = conveer_;
        motionRepeat = new MotionFactoryRepeat(1, this);
    }

    public boolean PauseResume() {
        boolean processPause__ = autorepeat;
        AddMotions(new MotionPauseResume(1, "PauseResume", conveer));
        // autorepeat = !autorepeat ;
        Resume();
        return processPause__;
    }

    public void AddPause() {
        AddMotions(new MotionPause(1, "Pause", conveer));
        // autorepeat = !autorepeat ;
        Resume();

    }

    public void AddResume() {
        AddMotions(new MotionResume(1, "Resume", conveer));
        // autorepeat = !autorepeat ;
        Resume();
    }

//    void SetCancel() {
//        AddMotions(new MotionSetCancel(1, "SetCancel", conveer));
//        Resume();
//    }
//    void Run() {
//
//        Start();
//    }
    public String SetThreadName() {
        return "Timer";
    }
}
