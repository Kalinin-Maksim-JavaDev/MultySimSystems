/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Model.Game.World.PathFindng;

import Platform.Core.IMotion;

/**
 *
 * @author kalinin
 */
public interface IPathMotionReciver {

    void AddMotions(IMotion motion);

    void Resume();
}
