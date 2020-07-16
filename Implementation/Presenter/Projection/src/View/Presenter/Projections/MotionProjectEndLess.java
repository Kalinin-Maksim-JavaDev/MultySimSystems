/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Presenter.Projections;

/**
 *
 * @author kalinin
 */
public class MotionProjectEndLess extends MotionProject {

    public MotionProjectEndLess(Imaginator imaginator) {
        super(imaginator);
    }

    public void MotionMethod() {
        incCounter();
        super.MotionMethod();
    }
}
