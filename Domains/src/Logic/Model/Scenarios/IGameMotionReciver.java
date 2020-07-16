/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Model.Scenarios;

import Platform.Core.IMotion;

/**
 *
 * @author kalinin
 */
public interface IGameMotionReciver {

    void AddMotions(IMotion motion);

    void Resume();
}
