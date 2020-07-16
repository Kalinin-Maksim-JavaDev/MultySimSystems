/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Models.Scenarios;

import Logic.Model.Scenarios.IScenario;
import Model.Models.Scenarios.Acts.ScenarioAct;
import Logic.Model.Game.World.IAgent;
import Platform.Core.IMotion;

/**
 *
 * @author kalinin
 */
public class FolowMainStrategy extends ScenarioAct {

    private IAgent[] agents;

    public FolowMainStrategy(IAgent[] agents_, IScenario scenario) {
        super(1, "ScenarioGoToFlat", scenario);
        agents = agents_;
    }

    public void MotionMethod(IMotion motion) {
        for (int i = 0; i < agents.length; i++) {
            scenario.addAct(agents[i].GetMainStrategy());
        }
    }

    public void finish() {
        
    }
}
