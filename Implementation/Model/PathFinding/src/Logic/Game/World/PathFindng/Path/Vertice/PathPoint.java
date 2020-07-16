/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Game.World.PathFindng.Path.Vertice;

import Logic.Model.Game.World.PathFindng.Path.TrackPoint.ITrackPoint;


/**
 *
 * @author kalinin
 */
class PathPoint implements ITrackPoint {

    private int x;
    private int y;
    private ITrackPoint next;
    //private TrackPoint prev = null;

    PathPoint(int x_, int y_) {
        x = x_;
        y = y_;
    }

    PathPoint(ITrackPoint tr_) {
        x = (int) tr_.getX();
        y = (int) tr_.getY();
    }

    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }

    public void setNext(ITrackPoint next) {
        this.next = next;
    }
    public ITrackPoint getNext() {
        return next;
    }


    public boolean equal(ITrackPoint tp_) {
        return ((getX() == tp_.getX()) && (getY() == tp_.getY()));
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    private double Lenght() {
        ITrackPoint a = this;
        ITrackPoint b = getNext();
        double l = 0;
        while (b != null) {
            l += Math.sqrt(Math.pow(b.getX() - a.getX(), 2) + Math.pow(b.getY() - a.getY(), 2));
            a = b;
            b = b.getNext();
        }

        return l;
    }
}
