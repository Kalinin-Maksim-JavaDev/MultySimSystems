/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Logic.Model.Game.World.PathFindng;

import Logic.Model.Game.World.PathFindng.Path.TrackPoint.ITrackPoint;

/**
 *
 * @author kalinin
 */
public interface IPathPointsFactory {

    ITrackPoint Create(ITrackPoint target);

    ITrackPoint Create(int x, int y);

}
