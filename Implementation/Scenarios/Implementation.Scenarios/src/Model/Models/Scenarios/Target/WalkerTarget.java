/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Models.Scenarios.Target;

import Logic.Model.Game.World.IWalker;
import Logic.Model.Game.World.PathFindng.IPathPointsFactory;
import Logic.Model.Game.World.PathFindng.Path.TrackPoint.ITrackPoint;

/**
 *
 * @author kalinin
 */
public abstract class WalkerTarget extends Target {

    protected ITrackPoint position;
    protected IWalker walker;

    public WalkerTarget(IWalker walker, ITrackPoint position,IPathPointsFactory pointsFactory) {
        super(pointsFactory);
        this.walker = walker;
        this.position = position;
        
    }

    public ITrackPoint GetArivePoint() {
        return pointsFactory.Create((int) position.getX(), (int) position.getY() + 1);
    }
}
