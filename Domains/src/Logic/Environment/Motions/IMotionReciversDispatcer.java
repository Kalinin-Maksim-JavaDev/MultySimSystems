/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Environment.Motions;

import Logic.Model.Scenarios.IGameMotionReciver;
import Logic.Model.Game.World.PathFindng.IPathMotionReciver;
import Platform.Core.IMotionReciver;

/**
 *
 * @author dkx6r0c
 */
public interface IMotionReciversDispatcer {

    IMotionReciver getMotionReciver();
    
    IPathMotionReciver getPathMotionReciver();

    IGameMotionReciver getGameMotionReciver();
}
