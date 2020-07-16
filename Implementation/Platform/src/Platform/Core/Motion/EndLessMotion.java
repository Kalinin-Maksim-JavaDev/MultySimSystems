/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Platform.Core.Motion;

/**
 *
 * @author kalinin
 */
public abstract class EndLessMotion extends Motion{

    abstract protected void EndLessMethod();

    public EndLessMotion(String name) {
        super(1, name);
    }

    @Override
    public void MotionMethod() {
        incCounter();
        EndLessMethod();

    }

}
