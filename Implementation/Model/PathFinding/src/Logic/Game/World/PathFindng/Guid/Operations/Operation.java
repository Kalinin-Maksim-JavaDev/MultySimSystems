/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Game.World.PathFindng.Guid.Operations;

import Logic.Model.Game.World.IWalker;
import Platform.Core.IMotion;
import Platform.Core.Motion.IMotionRepetableBody;
import Platform.Core.Motion.MotionFactory;

/**
 *
 * @author Dkx6r0c
 */
public abstract class Operation implements IMotionRepetableBody {

    IMotion motion;

    public IMotion getMotion() {
        return motion;
    }
    protected IWalker walker;

    public Operation(int iterates, String name, IWalker walker) {
        this.motion = MotionFactory.newRepetableMotion(iterates, name, this);
        this.walker = walker;

    }
}
