/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Models.Scenarios.Actions;

import Application.Build.Panels.Control.Manipulator.IManipulator;
import Application.Build.Panels.Control.Manipulator.IManipulator.IAction;
import Logic.Model.Scenarios.IScenario;
import Logic.Model.Scenarios.IScenarioMotionReciver;
import Platform.Core.IArgument;

/**
 *
 * @author kalinin
 */
public abstract class ActionScenario implements IAction {

    protected int grad;
    protected IScenario scenario;
    protected IScenarioMotionReciver scenarioMotionReciver;
    IManipulator manipulator;

    public ActionScenario() {
    }

    public ActionScenario(int grad, IScenario scenario, IScenarioMotionReciver motionsReciver) {
        this.grad = grad;
        this.scenario = scenario;
        this.scenarioMotionReciver = motionsReciver;
    }

    public void setManipulator(IManipulator manipulator) {
        this.manipulator = manipulator;
    }

    public static IAction Blank() {
        return new ActionScenario() {

            public void Abort(IArgument[] arg_) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void Do(IArgument[] arg_) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void Move(IArgument[] arg_) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void Select(IArgument[] arg_) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void Selecting(IArgument[] arg_) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
}
