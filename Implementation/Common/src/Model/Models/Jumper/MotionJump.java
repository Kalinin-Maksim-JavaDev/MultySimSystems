/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Models.Jumper;

import Logic.Model.Game.World.IWalker;
import Platform.Core.Motion.Motion;

/**
 *
 * @author kalinin
 */
public class MotionJump extends Motion {

    IWalker gr;

    public MotionJump(int iterates_, String motionName_, IWalker gr_) {
        super(iterates_, motionName_);
        gr = gr_;
    }

    public void MotionMethod() {
        gr.getBalisticsStrategy().getJump().Go(gr);
    }
}
