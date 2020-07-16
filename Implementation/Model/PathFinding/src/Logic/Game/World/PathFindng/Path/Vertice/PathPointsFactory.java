/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Game.World.PathFindng.Path.Vertice;

import Logic.Model.Game.World.PathFindng.IPathPointsFactory;
import Logic.Model.Game.World.PathFindng.Path.TrackPoint.ITrackPoint;


/**
 *
 * @author kalinin
 */
public class PathPointsFactory implements IPathPointsFactory {

    public ITrackPoint Create(int x, int y) {
        return new PathPoint(x, y);

    }

    public ITrackPoint Create(ITrackPoint target) {
        return new PathPoint(target);
    }
}
