/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Model.Game.World.PathFindng.Path;

import Logic.Model.Game.World.IWalker;
import Logic.Model.Game.World.PathFindng.IPathPointsFactory;
import Logic.Model.Game.World.PathFindng.IWalkAdviser;
import Logic.Model.Game.World.PathFindng.Path.TrackPoint.ITrackPoint;

/**
 *
 * @author kalinin
 */
public interface IPath {

    IPathPointsFactory getPathPointsFactory();

    void CalculatePath(ITrackPoint start, ITrackPoint finish);

    int getMapW();

    int getMapH();

    int getFallLimit();

    int getLen();

    ITrackPoint getPreTarget();

    void CalculatePath(ITrackPoint start_, ITrackPoint target_, IWalker traveler_, IWalkAdviser rule_);
}
