/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Model.Game.World.PathFindng;

import Logic.Model.Game.World.IAgent;

/**
 *
 * @author kalinin
 */
public interface IWalkAdviser {

    boolean canWalkThrough(IAgent agent);
}
