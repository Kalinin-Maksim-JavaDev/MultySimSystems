/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Model.Game.World.Exployer.Target;

import Logic.Model.Game.World.PathFindng.Path.TrackPoint.ITrackPoint;

/**
 *
 * @author kalinin
 */
public interface ITarget {

    public void CheckAchieved();

    public boolean isAchivied();

    public void setSuperTarget(ITarget target);

    public void setN(int n);

    public void SubTargetAchieved(int n);

    void AddSubTarget(ITarget sub);

    ITrackPoint GetArivePoint();
}
