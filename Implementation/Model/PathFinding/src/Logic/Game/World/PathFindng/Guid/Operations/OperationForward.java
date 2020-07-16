/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Game.World.PathFindng.Guid.Operations;

import Logic.Model.Game.World.IWalker;
import Logic.Model.Game.World.PathFindng.IVectorsFactory;
import Platform.Core.IMotion;
import Platform.Core.IMotionBody;
import Platform.Core.IMotionReciver;
import Platform.Core.Motion.MotionFactory;
import java.util.Random;

/**
 *
 * @author kalinin
 */
public class OperationForward extends Operation  {

    private final int x;
    //  Vector2d vx = new Vector2d(0, 0);
    private IMotion nextMotion;
    private IMotionReciver motionsReciver;
    private IVectorsFactory vectorsFactory;

    public OperationForward(IVectorsFactory vectorsFactory, int iterates,IWalker walker, int x_, IMotion nextMotion_) {
        super(iterates, "Forward",walker);
        x = x_;
        nextMotion = nextMotion_;
    }

    public void BeginOperation() {
        if (x > walker.GetIntX()) {
            walker.getSpeed().Add(vectorsFactory.NewVector(2, 0));
        } else if (x < walker.GetIntX()) {
            walker.getSpeed().Add(vectorsFactory.NewVector(-2, 0));
        }
        //  gr.speed.Add(vx);
        // Influnce();
    }

    public boolean EndCondition() {
        return walker.ArriveX(x, walker.fromeAbove());
    }

    public void EndOperation() {
        // vx.x = -vx.x;
        walker.stop();
        motionsReciver.AddMotions(MotionFactory.newMotion(1, "WaitLanding", new MotionLandWait(nextMotion)));
    }

    public void Influnce() {
        Random infl = new Random();
        if (infl.nextInt(4) == 1) {
            walker.getBalisticsStrategy().getJump().Go(walker);

            walker.TurnBack();

        }
    }


    private class MotionLandWait implements IMotionBody {

        private IMotion nextMotion;

        private MotionLandWait(IMotion nextMotion_) {
            nextMotion = nextMotion_;
        }

        public void MotionMethod(IMotion motion) {
            if (!walker.getBalisticsStrategy().Landed()) {
                System.out.println("wait landed");
                motion.incCounter();
            } else {
                motionsReciver.AddMotions(nextMotion);
                motionsReciver.Resume();
            }
        }
    }
}
