/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Game.World.Unit.Command;

import Logic.Model.Game.World.IWalker;
import Platform.Core.Motion.Motion;
import Render.Graphics.Geometry.D2.IVector2d;

/**
 *
 * @author Adm
 */
public class MotionNullXSpeed extends Motion {

    public IWalker gr;

    public MotionNullXSpeed(IWalker gr_) {
        this(1, gr_);
    }

    public MotionNullXSpeed(int counter, IWalker gr_) {
        super(counter, "AddSpeed");
        gr = gr_;
    }

    public void MotionMethod() {
        ((IVector2d)gr.getSpeed()).setX(0);
    }
}
