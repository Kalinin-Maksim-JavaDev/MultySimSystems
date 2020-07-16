/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Game.World.Unit.Command;

import Game.Model.Game.IRatio;
import Logic.Model.Game.IGame;
import Model.Game.World.Unit.GameUnit;
import Model.Game.World.Unit.MovementsAlgoritms.AlgMoveSpeed;
import Model.Geometry.D2.Coordinates2d;
import Logic.Model.Game.World.IWalker;
import Platform.Core.Motion.Motion;
import Render.Graphics.Geometry.D2.IVector2d;


/**
 *
 * @author Adm
 */
public class MotionMoveWithSpeed extends Motion {

    public GameUnit gr;
    public AlgMoveSpeed algr;

    public MotionMoveWithSpeed(int iterates_, IGame.unit gr_,IRatio ratio) {
        super(iterates_, "MoveWithVxVy");
        gr = (GameUnit) gr_;
        algr = new AlgMoveSpeed(ratio,(Coordinates2d) gr.asPoint().getPosition(), (IVector2d) gr.GetWalker().getSpeed());
    }

    public void MotionMethod() {
        if (((IWalker)gr.getItem()).getSpeed().Module() != 0) {
//            if (gr.game.isFinalCalc()) {
//                gr.game.getLoger().RegBeg("calc " + gr.getUnitName() + gr.getItem().getIndex());
//            }
          //  GroupUnit c_ = gr.lastUnit;
            algr.CalculatePosition();
           // double dx = (algr.p.xd - ((Coordinates2d) gr.position).x);
           // double dy = (algr.p.yd - ((Coordinates2d) gr.position).y);
         //   do {
              //  double xc = ((Coordinates2d) gr.position).x;
             //   double yc = ((Coordinates2d) gr.position).y;
                gr.game.getCollisionMap().PreMoveUnit(gr.as2Dbodie(), algr.p.xd, algr.p.yd);
         //       c_ = c_.next;
         //   } while (c_ != gr.lastUnit);
//            if (gr.game.isFinalCalc()) {
//                gr.game.getLoger().RegEnd("calc " + gr.getUnitName() + gr.getItem().getIndex());
//            }

        }
//                else{
//                    GroupUnit c_ = lastUnit;
//                    do {
//                       c_.repaint=false;
//                        c_ = c_.next;
//                    } while (c_ != lastUnit);
//                }
//                if (counter == 1) {
//                    speed.Sub(newspeed);
//                }
        //     System.out.println(counter + ":" + currentTimeMillis() + " " + sysName + " calc");
        //  test.incrementAndGet();
//                if (sys.num == 1) {
//                    test1.incrementAndGet();
//                } else {
//                    test2.incrementAndGet();
//                }
        // System.out.println("  " + ":" + currentTimeMillis() + " " + sys.sysName + sys.taskMotions.getName() + " work");

    }
//            public void Refresh() {
//                //algr = new AlgMoveSpeed(((Coordinates2d)lastUnit.position).x,((Coordinates2d)lastUnit.position).y);
//            }
}

