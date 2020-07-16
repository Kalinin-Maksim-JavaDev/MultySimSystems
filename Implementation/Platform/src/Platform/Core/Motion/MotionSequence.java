/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Core.Motion;

import Platform.Core.IMotion;
import Global.Tools;
import Platform.Concurrent.Factory;
import Platform.Concurrent.IAtomicReferenceObject;
import Platform.Core.IMotionSequence;

/**
 *
 * @author kalinin
 */
public class MotionSequence extends Motion implements IMotionSequence {

    IAtomicReferenceObject motionsNode;
    public IMotion currentMotion;

    public MotionSequence(int iterates_, String name_) {
        super(iterates_, name_);
        motionsNode = Factory.newAtomicReferenceObject();
        next = this;

        motionsNode.set(this);
        currentMotion = ((Motion) this);

    }

    public void Repeat_() {
        currentMotion = this;
    }

    public void AddMotions(IMotion newMotion_) {
        IMotion motionsNode_;
        for (;;) {

            motionsNode_ = (IMotion) motionsNode.get();

            newMotion_.setPrev(motionsNode_);

            newMotion_.setNext(motionsNode_.getNext());

            if (motionsNode.compareAndSet(motionsNode_, newMotion_)) {

                motionsNode_.setNext(newMotion_);
                newMotion_.ativate();
                break;
            }
        }


    }

    public boolean TryDelMotions(IMotion motion_) {
        boolean res = false;

        if (((Motion) motion_).getNext() == this) {
            res = false;
        } else {

            motion_.getPrev().setNext(motion_.getNext());

            motion_.getNext().setPrev(motion_.getPrev());

            motion_.setNext(null);
            motion_.setPrev(null);

            motion_.deativate();
            res = true;
        }

        return res;
    }

    public void MotionMethod() {

        Tools.UnsupportedOperationException();
    }

    public void SetCurrent(IMotion motion) {
        currentMotion = motion;
    }

    public IMotion getCurrent() {
        return currentMotion;
    }

    public void Resume() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

//    protected void finalize() throws Throwable {
//        super.finalize();
//        System.out.println(" MotionSequence destroy.");
//    }
}