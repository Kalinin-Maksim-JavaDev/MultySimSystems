/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Presenter.Projections;

import Application.ILoger;
import Model.Game.Presenter.Projections.ArgumentInt;
import Platform.Core.ISystemMContainer;
import Platform.Core.Motion.Motion;
import View.Presenter.Projections.IViewUnit.states;

/**
 *
 * @author kalinin
 */
public class MotionProject extends Motion {

    protected Imaginator.charged imaginator;
    private int stateID = -1;

    public MotionProject(Imaginator imaginator) {
        super(1, "Project");
        this.imaginator = imaginator;
    }

    public void MotionMethod() {

        ISystemMContainer prD = imaginator.getReflectionPerformer();
        ((ILoger) prD.getSys()).RegBeg(imaginator.getName() + imaginator.getIndex());
        //                    java.lang.System.out.print(iterationCounter + Thread.currentThread().getName() + ":" + currentTimeMillis() + " " + sysName + "beg" + iterationCounter + "\n");
        imaginator.Translate();

        if (stateID == -1) {
            imaginator.Project();
        } else {
            imaginator.Project(stateID);
            stateID = - 1;
        }
        //                    java.lang.System.out.print(iterationCounter + Thread.currentThread().getName() + ":" + currentTimeMillis() + " " + sysName + "end" + iterationCounter + "\n");
        ((ILoger) prD.getSys()).RegEnd(imaginator.getName() + imaginator.getIndex());
    }

    public void SetArg(int stateID) {
        this.stateID = stateID;
    }
}
