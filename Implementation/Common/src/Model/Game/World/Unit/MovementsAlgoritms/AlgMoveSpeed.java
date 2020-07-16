/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Game.World.Unit.MovementsAlgoritms;

import Game.Model.Game.IRatio;
import Model.Geometry.D2.Coordinates2d;
import Render.Graphics.Geometry.D2.IVector2d;

/**
 *
 * @author kalinin
 */
public class AlgMoveSpeed extends AlgMove {

    public Coordinates2d p;
    private IVector2d speed;
    double x, y;

    public AlgMoveSpeed(IRatio ratio, Coordinates2d p_, IVector2d speed) {
        super(ratio);
        p = p_;
        p.xd = (p.x);
        p.yd = (p.y);
        this.speed = speed;

    }

    public AlgMoveSpeed(IRatio ratio, double x_, double y_, IVector2d speed) {
        super(ratio);
        x = x_;
        y = y_;
        this.speed = speed;
    }

    public void CalculatePosition() {
        //time += time_  ;

        double dx = speed.getX() * ratio.getDt();
        double dy = speed.getY() * ratio.getDt();
        p.xd = (p.xd + dx);
        p.yd = (p.yd + dy);
    }
}
