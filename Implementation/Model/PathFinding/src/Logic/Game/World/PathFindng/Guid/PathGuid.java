/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Game.World.PathFindng.Guid;

import Game.Model.Game.IRatio;
import Game.Model.Game.World.Interaction.ICollisionMap;
import Logic.Game.World.PathFindng.Guid.Operations.OperationForward;
import Logic.Game.World.PathFindng.Guid.Operations.OperationJump;
import Logic.Model.Game.World.Exployer.Target.IPathGuidManager;
import Logic.Model.Game.World.IWalker;
import Logic.Model.Game.World.PathFindng.IPathGuid;
import Logic.Model.Game.World.PathFindng.IVectorsFactory;
import Logic.Model.Game.World.PathFindng.IWalkAdviser;
import Logic.Model.Game.World.PathFindng.Path.IPath;
import Logic.Model.Game.World.PathFindng.Path.TrackPoint.ITrackPoint;
import Platform.Core.IMotion;
import Platform.Core.IMotionBody;
import Platform.Core.IMotionReciver;
import Platform.Core.ISystemM;
import Platform.Core.Motion.MotionFactory;
import Platform.Core.Systems.SystemFactory;
import Platform.ILogCanvas;
import Render.Graphics.Geometry.IVectorD;

/**
 *
 * @author kalinin
 */
public class PathGuid implements IPathGuid {

    private final IRatio ratio;
    private final IPath path;
    ISystemM sys;
    ICollisionMap map;
    IVectorsFactory vectorsFactory;

    public PathGuid(ICollisionMap map, IRatio ratio, ILogCanvas logCanvas) {
        vectorsFactory = new VectorsFactory();
        sys = SystemFactory.CreateSystemM("Exployer", this);
        this.ratio = ratio;
        this.map = map;
        path = new WalkerPath(4, map.preCompliteUnitsMap(0), map.getWeight() / ratio.getGrad(), map.getHight() / ratio.getGrad(), logCanvas);

    }

    public void TryGoTo(IWalker walker, int x_, int y_, IMotion nextMotion_) {

        final int period = (int) (ratio.getMoveperiod() / ratio.getQuant());
        IMotion mot_ = null;
        if (y_ > walker.GetIntY()) {
            //Up
            int k = (y_ - walker.GetIntY());

            if (k < 3) {
                mot_ = (new OperationJump(vectorsFactory,(period), walker, y_, nextMotion_, period, x_)).getMotion();
            } else {
                mot_ = MotionFactory.newMotion(1, "null", new IMotionBody() {

                    public void MotionMethod(IMotion motion) {
                    }
                });
            }
        } else if ((y_ == walker.GetIntY()) && (map.getDot(((x_ + walker.GetIntX()) / 2) * ratio.getGrad(), (walker.GetIntY() - 1) * ratio.getGrad()) == null)) {
            //Jump over
            mot_ = (new OperationForward(vectorsFactory,(2 * period), walker, x_, nextMotion_) {

                @Override
                public void BeginOperation() {
                    super.BeginOperation();
                    walker.getBalisticsStrategy().getJump().Go(walker);
                }
            }).getMotion();

        } else if (y_ <= walker.GetIntY()) {
            //forward
            int k = (int) (x_ - walker.GetIntX());
            if (k < 0) {
                k = -k;
            }
            mot_ = (new OperationForward(vectorsFactory,(k * period), walker, x_, nextMotion_).getMotion());
        }

        sys.AddMotions(mot_);
        sys.Resume();

    }

    public void CalculatePath(ITrackPoint start, ITrackPoint finish, IWalker agent, IWalkAdviser iRule) {
        ((WalkerPath) getPath()).CalculatePath(start, start, agent, iRule);
    }

    public int fallLimit() {
        return getPath().getFallLimit();
    }

    public int pathmapH() {
        return getPath().getMapH();
    }

    public int pathmapW() {
        return getPath().getMapW();
    }

//    private boolean Arrive(GameUnit gr, int x, int y) {
//        return (((x == ((Coordinates2d) gr.position).x) && (x == ((Coordinates2d) gr.position).x + game.grad - 1)) &&
//                ((y == ((Coordinates2d) gr.position).y) && (y == ((Coordinates2d) gr.position).y + game.grad - 1)));
//    }
    private void Release() {
    }

    public String SetThreadName() {
        return "E";
    }

    public IMotionReciver getMotionsReciver() {
        return (ISystemM) this;
    }

    /**
     * @return the path
     */
    public IPath getPath() {
        return (IPath) path;

    }

    public void addManager(IPathGuidManager manager) {
        sys.AddMotions(manager.newMotionFolowNext());
        sys.Resume();
    }

    public void Start() {
        sys.Start();
    }

    public void AddMotions(IMotion motion) {
        sys.AddMotions(motion);
    }

    public void Resume() {
        sys.Resume();
    }

    public void Stop() {
        sys.Stop();
    }

    private class VectorsFactory implements IVectorsFactory {

        public IVectorD NewVector(double x, double y) {
            return new Vector2d(x * ratio.getGrad() / ratio.getMoveperiod(), y * ratio.getGrad() / ratio.getMoveperiod());
        }
    }
}
