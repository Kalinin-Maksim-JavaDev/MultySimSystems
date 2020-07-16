/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Game.World.PathFindng.Guid.Operations;

import Logic.Model.Game.World.IWalker;
import Logic.Model.Game.World.PathFindng.IVectorsFactory;
import Platform.Core.IMotion;
import Platform.Core.IMotionReciver;

/**
 *
 * @author kalinin
 */
public class OperationJump extends Operation {

    private final int x;
    private final int y;
    private final IMotion nextMotion;
    private final int period;
    private IMotionReciver motionsReciver;
    private IVectorsFactory vectorsFactory;

    public OperationJump(IVectorsFactory vectorsFactory, int iterates, IWalker walker, int y_, IMotion nextMotion_, int period, int x_) {
        super(iterates, "Jump", walker);
        this.vectorsFactory = vectorsFactory;
        this.y = y_;
        this.nextMotion = nextMotion_;
        this.period = period;
        this.x = x_;
    }

    public void BeginOperation() {
        walker.getBalisticsStrategy().getJump().Go(walker);
    }

    public boolean EndCondition() {
        return walker.ArriveY(y, walker.fromeAbove());
    }

    public void EndOperation() {
        //                    game.AddMotions(new Motion(1, "forward") {
        //
        //                        private void MotionMethod() {
        motionsReciver.AddMotions((new OperationForward(vectorsFactory,2 * period, walker, x, nextMotion)).getMotion());
        //                          }
        //                    });
    }
}
