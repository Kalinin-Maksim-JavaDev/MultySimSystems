/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import Platform.Core.IMotion;
import Platform.Core.Motion.Motion;
import Platform.Core.Motion.MotionSequence;

/**
 *
 * @author dkx6r0c
 */
public class WorkersManager extends MotionSequence {

    //  MotionFactory CheckChange;
    //  Lathce l;
    public WorkersManager() {
        super(1, "MotionSequence");
        //l = new Lathce(true);
//            CheckChange = new MotionFactory(1) {
//
//                public Motion NewMotion(int iterates_) {
//
//                    return new Motion(1, "Relese") {
//
//                        public void MotionMethod() {
//                            System.out.println("    CheckChange");
//                            Repeat(true);
//                 //           l.synchronizedRelease();
//                        }
//                    };
//                }
//            };
    }

    public synchronized void AddMotions(Motion motion_) {
        // System.out.println(motion_.motionName + " motion added");
        super.AddMotions(motion_);
    }

    public synchronized void enableDisable() {
        IMotion current = this;
        while (current.getNext() != this) {
            current = current.getNext();
            if (current.Iterate()) {
                IMotion prev = current.getPrev();

                if (TryDelMotions(current)) {

                    current = prev;

                }
            }
        }
    }
}
