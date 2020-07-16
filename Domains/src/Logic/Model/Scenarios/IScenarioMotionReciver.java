/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Model.Scenarios;

import Platform.Core.IMotion;
import Platform.Core.IMotionBody;
import Platform.Core.IMotionTemplate;

/**
 *
 * @author kalinin
 */
public interface IScenarioMotionReciver {

    public void AddMotions(IMotion motion);

    void AddMotions(String name, IMotionBody command);

    void AddMotions(IMotionTemplate template);

    void Resume();
}
