/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Model.Game.World.Exployer.Target;

import Logic.Model.Game.World.PathFindng.Guid.IPathStrategy;
import Platform.Core.IMotion;

/**
 *
 * @author kalinin
 */
public interface IPathGuidManager {

    void setStrategy(IPathStrategy iPathStrategy);

    int getStartingCount();

    IMotion newMotionGo();

    IMotion newMotionFolowNext();
}
