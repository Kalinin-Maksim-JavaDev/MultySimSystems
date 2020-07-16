/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Models;

import Logic.Game.World.PathFindng.Guid.PathGuidManager;
import Logic.Model.Game.World.IWalker;
import Logic.Model.Game.World.PathFindng.Guid.IPathStrategy;
import Logic.Model.Game.World.PathFindng.IPathGuid;
import Logic.Model.Game.World.PathFindng.IPathMotionReciver;
import Logic.Model.Game.World.PathFindng.Path.TrackPoint.ITrackPoint;

/**
 *
 * @author kalinin
 */
public class PathGuidSimpleManager extends PathGuidManager {

    public PathGuidSimpleManager(IWalker walker, IPathMotionReciver motionsReciver, int grad, IPathGuid pathGuid, ITrackPoint target, IPathStrategy strategy) {
        super(walker, motionsReciver, grad, pathGuid, strategy);
    }

    public PathGuidSimpleManager(IWalker walker, IPathMotionReciver motionsReciver, int grad, IPathGuid pathGuid) {
        this(walker, motionsReciver, grad, pathGuid, walker.GetTarget().GetArivePoint(), null);
        setStrategy(new IPathStrategy() {

            public boolean beginning() {
                return false;
            }

            public boolean forceFinshed() {
                return startingCount > 10;
            }

            public boolean abort() {
                return PathGuidSimpleManager.this.walker.isSelected();
            }

            public boolean retry() {
                return true;
            }

            public void finishated() {
                System.out.println("    stop ball");
                PathGuidSimpleManager.this.walker.onWay().synchronizedRelease(); //; = false;
            }
        });

    }
}
