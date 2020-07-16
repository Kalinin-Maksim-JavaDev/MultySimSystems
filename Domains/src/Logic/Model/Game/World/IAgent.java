/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Model.Game.World;

import Logic.Model.Scenarios.IScenarioAct;
import Render.Graphics.Geometry.ICoordinates;

/**
 *
 * @author kalinin
 */
public interface IAgent {


    /*private void SetTarget(ITarget target);

    private ITarget GetTarget();*/

    void OpenFair();

    boolean OnWay();

    IScenarioAct GetMainStrategy();
 
}
