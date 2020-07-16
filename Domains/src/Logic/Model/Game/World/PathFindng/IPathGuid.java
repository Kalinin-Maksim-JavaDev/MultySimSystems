/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Model.Game.World.PathFindng;

import Logic.Model.Game.World.Exployer.Target.IPathGuidManager;
import Logic.Model.Game.World.IWalker;
import Logic.Model.Game.World.PathFindng.Path.IPath;
import Logic.Model.Game.World.PathFindng.Path.TrackPoint.ITrackPoint;
import Platform.Core.IMotion;
import Platform.Core.ISystemOwner;

/**
 *
 * @author kalinin
 */
public interface IPathGuid extends ISystemOwner, IPathMotionReciver {

    void TryGoTo(IWalker agent, int i, int i0, IMotion motion);

    void CalculatePath(ITrackPoint start, ITrackPoint finish, IWalker agent, IWalkAdviser iRule);

    int fallLimit();

    int pathmapW();

    int pathmapH();

    IPath getPath();

    void addManager(IPathGuidManager manager);
}
