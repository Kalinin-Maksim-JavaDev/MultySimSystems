/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Models.Scenarios.Acts;

import Logic.Model.Scenarios.IScenario;
import Logic.Model.Scenarios.IScenarioAct;
import Platform.Core.IMotion;
import Platform.Core.IMotionBody;
import Platform.Core.Motion.MotionFactory;

/**
 *
 * @author kalinin
 */
public abstract class ScenarioAct implements IMotionBody, IScenarioAct {

    protected IScenario scenario;
    private IMotion motion ;

    public abstract void finish();

    public ScenarioAct(int iterates, String name, IScenario scenario) {
        this.motion = MotionFactory.newMotion(iterates, name, this);
        this.scenario = scenario;
    }

    public IMotion getMotion() {
        return motion;
    }
}
