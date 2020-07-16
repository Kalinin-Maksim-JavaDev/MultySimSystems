/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Game.World.Unit.Command;

import Game.Model.Game.IRatio;
import Logic.Model.Game.IGame;
import Logic.Model.Game.World.IWalker;

/**
 *
 * @author kalinin
 */
public class MotionMoveWithSpeedAndGravity extends MotionMoveWithSpeed {

    public MotionMoveWithSpeedAndGravity(int iterates_, IGame.unit gr_,IRatio ratio) {
        super(iterates_, gr_,ratio);
    }

    public void MotionMethod() {
        super.MotionMethod();
        //  if (!IMap.OnSurface(lastUnit)) {
//                if ((!onGrav) || (!IMap.OnSurface(lastUnit))) {
//
//                }

        ((IWalker)gr.getItem()).getBalisticsStrategy().getGravity().Go((IWalker) gr.getItem());

    }
}
