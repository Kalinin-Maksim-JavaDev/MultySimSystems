/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Game.World.Unit.Command;

import Platform.Calculation.ITasksReciver;
import Platform.Calculation.Work;
import Platform.Core.Motion.Motion;
import Render.Graphics.Geometry.IVectorD;

/**
 *
 * @author Adm
 */
public class MotionAddSpeed extends Motion {

    private final IVectorD unitSpeed;
    protected ITasksReciver tasksReciver;
    final public IVectorD newspeed;

    public MotionAddSpeed(IVectorD newspeed_, IVectorD unitSpeed, ITasksReciver motionsReciver) {
        this(1, newspeed_, unitSpeed, motionsReciver);
    }

    public MotionAddSpeed(int counter, IVectorD newspeed_, IVectorD unitSpeed, ITasksReciver tasksReciver) {
        super(counter, "AddSpeed");
        this.unitSpeed = unitSpeed;
        this.tasksReciver = tasksReciver;
        newspeed = newspeed_;
    }

    public void MotionMethod() {
        tasksReciver.AddTask(new Work() {

            public void body() {
                unitSpeed.Add(newspeed);
            }
        });
    }
}
