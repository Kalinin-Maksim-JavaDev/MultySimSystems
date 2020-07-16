package Logic.Game.World.PathFindng.Guid;

import Logic.Model.Game.World.Exployer.Target.IPathGuidManager;
import Logic.Model.Game.World.IAgent;
import Logic.Model.Game.World.IWalker;
import Logic.Model.Game.World.PathFindng.Guid.IPathStrategy;
import Logic.Model.Game.World.PathFindng.IPathGuid;
import Logic.Model.Game.World.PathFindng.IPathMotionReciver;
import Logic.Model.Game.World.PathFindng.IWalkAdviser;
import Logic.Model.Game.World.PathFindng.Path.TrackPoint.ITrackPoint;
import Platform.Core.IMotion;
import Platform.Core.IMotionBody;
import Platform.Core.IMotionTemplate;
import Platform.Core.Motion.MotionFactory;
import Platform.Core.MotionsDispatcher;
import java.util.Random;

public class PathGuidManager implements IPathGuidManager, IPathStrategy {

    protected int startingCount = 0;
    protected IWalker walker;
    private IPathGuid pathGuid;
    private ITrackPoint finish;
    private ITrackPoint start;
    private ITrackPoint c_;
    private IPathMotionReciver pathMotionsReciver;
    private int grad;
    private IPathStrategy strategy;

    public PathGuidManager(IWalker walker, IPathMotionReciver motionsReciver, int grad, IPathGuid pathGuid, ITrackPoint finish, IPathStrategy strategy) {
        this.pathGuid = pathGuid;
        this.walker = walker;
        this.finish = finish;
        this.pathMotionsReciver = motionsReciver;
        this.grad = grad;
        this.strategy = strategy;
    }

    public PathGuidManager(IWalker walker, IPathMotionReciver motionsReciver, int grad, IPathGuid pathGuid, IPathStrategy strategy) {
        this(walker, motionsReciver, grad, pathGuid, walker.GetTarget().GetArivePoint(), strategy);
    }

    public IMotion newMotionGo() {
        return MotionFactory.newMotion(new MotionGo());
    }

    public IMotion newMotionGoRandom() {
        return MotionFactory.newMotion(new MotionGoRandom());
    }

    public IMotion newMotionFolowNext() {
        return MotionFactory.newMotion(new MotionFolowNext());
    }

    public boolean abort() {
        return strategy.abort();
    }

    public boolean beginning() {
        return strategy.beginning();
    }

    public void finishated() {
        strategy.finishated();
    }

    public boolean forceFinshed() {
        return strategy.forceFinshed();
    }

    public boolean retry() {
        return strategy.retry();
    }

    private void repeat() {
        if ((!(walker).Arrive(finish.getX(), finish.getY())) && (!abort())) {

            ++startingCount;
            if (forceFinshed()) {
                finishated();
            } else if (beginning()) {
                startingCount = 0;
               MotionsDispatcher.addMotion(pathGuid,newMotionGoRandom());
            } else {
               MotionsDispatcher.addMotion(pathGuid,newMotionGo());
            }
        } else {
            finishated();

        }
    }

    public void setStrategy(IPathStrategy strategy) {
        this.strategy = strategy;
    }

    public int getStartingCount() {
        return startingCount;
    }

    private class MotionGoRandom implements IMotionTemplate {

        public int getIterates() {
            return 1;
        }

        public String getNаme() {
            return "GoRandom";
        }

        public void MotionMethod(IMotion motion) {
            Random r = new Random();
            start = pathGuid.getPath().getPathPointsFactory().Create(((IWalker) walker).GetIntX(), ((IWalker) walker).GetIntY());
            int xscope = pathGuid.pathmapW();
            int yscope = pathGuid.pathmapH();
            //GroupUnit f = fund[r.nextInt(fund.length)];
            // int x =((Coordinates2d)f.position).x ;
            //int y =((Coordinates2d)f.position).y +1;
            int x = start.getX();
            int y = start.getY();
            if (r.nextBoolean()) {
                x += 1 + r.nextInt(2);
            } else {
                x -= 1 + r.nextInt(2);
            }
            if (r.nextBoolean()) {
                y += r.nextInt(3);
            } else {
                y -= r.nextInt(pathGuid.fallLimit());
            }
            if (x < 0) {
                x = 0;
            }
            if (y < 0) {
                y = 0;
            }
            if (x > xscope) {
                x = xscope;
            }
            if (y > xscope) {
                y = yscope;
            }
            pathGuid.TryGoTo(walker, x * grad, y * grad, MotionFactory.newMotion(new IMotionWait()));
        }
    }

    private class IMotionWait implements IMotionTemplate {

        public int getIterates() {
            return 1;
        }

        public String getNаme() {
            return "Wait";
        }

        public void MotionMethod(IMotion motion) {
            //                        try {
            //                            Thread.sleep((long) (moveperiod));
            //                        } catch (InterruptedException ex) {
            //                            ex.printStackTrace();
            //
            pathMotionsReciver.AddMotions(MotionFactory.newMotion(25, "StrartAfterWait", new MotionStrartAfterWait()));
        }
    }

    private class MotionGo implements IMotionTemplate {

        public int getIterates() {
            return 1;
        }

        public String getNаme() {
            return "go";
        }

        public void MotionMethod(IMotion motion) {
            ITrackPoint start = pathGuid.getPath().getPathPointsFactory().Create(walker.GetIntX(), walker.GetIntY());
            ITrackPoint finish = pathGuid.getPath().getPathPointsFactory().Create(PathGuidManager.this.finish);
            pathGuid.CalculatePath(start, finish, walker, new IWalkAdviser() {

                public boolean canWalkThrough(IAgent agent) {
                    return agent.OnWay();
                }
            });
            c_ = start.getNext();
            MotionsDispatcher.addMotion(pathGuid,MotionFactory.newMotion(new MotionFolowNext()));
        }
    }

    private class MotionFolowNext implements IMotionTemplate {

        public int getIterates() {
            return 1;
        }

        public String getNаme() {
            return "get path";
        }

        public void MotionMethod(IMotion motion) {
            if ((c_ != null) && (!abort())) {
                pathGuid.TryGoTo(walker, c_.getX(), c_.getY(), MotionFactory.newMotion(new MotionCheckConditional()));
            } else {
                if (retry()) {
                    repeat();
                } else {
                    finishated();
                }
            }
        }
    }

    private class MotionCheckConditional implements IMotionTemplate {

        public int getIterates() {
            return 1;
        }

        public String getNаme() {
            return "CheckConditional";
        }

        public void MotionMethod(IMotion motion) {
            if (((walker).Arrive(c_.getX(), c_.getY())) && (!abort())) {
                c_ = c_.getNext();
                MotionsDispatcher.addMotion(pathGuid,MotionFactory.newMotion(new MotionFolowNext()));
            } else {
                if (retry()) {
                    repeat();
                } else {
                    finishated();
                }
            }
        }
    }

    private class MotionStrartAfterWait implements IMotionBody {

        public void MotionMethod(IMotion motion) {
            if (motion.isFinal()) {
                // }
                //   if (!agent.GetTarget().IsAchivied()) {
                repeat();
                //   }
            }
            // }
        }
    }
}
