/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Model.Game.World.PathFindng.Path.TrackPoint;

/**
 *
 * @author kalinin
 */
public interface ITrackPoint {

    int getX();

    int getY();

    public boolean equal(ITrackPoint point);

    public ITrackPoint getNext();

    public void setNext(ITrackPoint target);

    public void setX(int x);

    public void setY(int y);
}
