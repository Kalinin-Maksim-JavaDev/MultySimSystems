/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Core.Motion;

/**
 *
 * @author kalinin
 */
public abstract class RepetableMotion extends Motion implements IMotionRepetable {

    private boolean firstIttr = true;

    public RepetableMotion(int iterates_, String motionName_) {
        super(iterates_, motionName_);
    }

    public void MotionMethod() {
        if (firstIttr) {
            BeginOperation();
            firstIttr = false;
        }
        if (EndCondition()) {
            setCounter(1);
        }
        if (isFinal()) {
            EndOperation();
        }
    }
}
