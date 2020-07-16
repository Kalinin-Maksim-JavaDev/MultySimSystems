/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Game.World.Unit.Command;

import Model.Game.World.Unit.GameUnit;
import Model.Geometry.D2.Coordinates2d;
import Platform.Core.Motion.Motion;

/**
 *
 * @author Adm
 */
public class MotionRotate extends Motion {

    final GameUnit gr;
    // double time;
    final double xaxis;
    final double yaxis;

    public MotionRotate(int iterates_, String motionName_, GameUnit gr_) {
        super(iterates_, motionName_);
        gr = gr_;
        xaxis = ((Coordinates2d) gr.asPoint().getPosition()).x;
        yaxis = ((Coordinates2d) gr.asPoint().getPosition()).y;
    }

    public void MotionMethod() {
        //    time += dt;
       // GroupUnit c_ = gr.lastUnit;
      //  do {
            double x_ = ((Coordinates2d) gr.asPoint().getPosition()).x - xaxis;
            double y_ = ((Coordinates2d) gr.asPoint().getPosition()).y - yaxis;
            double x = xaxis - y_;
            double y = yaxis + x_;
            gr.game.getCollisionMap().PreMoveUnit(gr.as2Dbodie(), x, y);
      //      c_ = c_.next;
      //  } while (c_ != gr.lastUnit);
    }
}
