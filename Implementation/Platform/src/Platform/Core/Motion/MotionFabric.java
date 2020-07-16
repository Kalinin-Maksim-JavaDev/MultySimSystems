/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Core.Motion;

import Platform.Core.IMotion;

/**
 *
 * @author kalinin
 */
public abstract class MotionFabric {

    public IMotion mot;

    public MotionFabric(int iterates_) {
    }

    public IMotion GetMotion(int iterates_) {
        if (mot == null || mot.isActive()) {
            mot = NewMotion(iterates_);
        } else {
            mot.setCounter(iterates_);
        }
        return mot;

//            return NewMotion(iterates_);
    }

    abstract public IMotion NewMotion(int iterates_);
}
