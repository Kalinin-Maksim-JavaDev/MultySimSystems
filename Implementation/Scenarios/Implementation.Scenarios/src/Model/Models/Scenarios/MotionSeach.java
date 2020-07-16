/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Models.Scenarios;

import Logic.Model.Game.World.Exployer.Target.IPathGuidManager;
import Logic.Model.Game.World.IAgent;
import Logic.Model.Game.World.IWalker;
import Logic.Model.Game.World.PathFindng.Guid.IPathStrategy;
import Logic.Model.Game.World.PathFindng.IPathMotionReciver;
import Logic.Model.Game.World.PathFindng.Path.TrackPoint.ITrackPoint;
import Platform.Core.IMotion;
import Platform.Core.IMotionBody;
import Platform.Core.IMotionTemplate;
import Platform.Core.Motion.MotionFactory;
import Platform.Core.MotionsDispatcher;

/**
 *
 * @author kalinin
 */
public class MotionSeach implements IMotionTemplate {

    private SystemMScenario scenario;
    private IWalker hunter;
    private IWalker target;
    private IPathMotionReciver motionsReciver;
    private int grad;

    public MotionSeach(SystemMScenario scenario, IWalker hunter,
            IWalker target, IPathMotionReciver motionsReciver, int grad) {
        this.scenario = scenario;
        this.hunter = hunter;
        this.target = target;
        this.motionsReciver = motionsReciver;
        this.grad = grad;

    }

    public int getIterates() {
        return 1;
    }

    public String getNаme() {
        return "Seach";
    }

    public void MotionMethod(IMotion motion) {
        int x1 = (int) hunter.GetIntX();
        int y1 = (int) hunter.GetIntY();
        int x2 = (int) target.GetIntX();
        int y2 = (int) target.GetIntY();
        if (scenario.find(x1, y1, x2, y2)) {
            ITrackPoint point = scenario.pathGuid.getPath().getPathPointsFactory().Create(x2, y2 + 1);
            final IPathGuidManager target = scenario.createPathGuidManager(hunter, motionsReciver, grad, scenario.pathGuid);
            target.setStrategy(new IPathStrategy() {

                public boolean abort() {
                    return false;
                }

                public boolean retry() {
                    return true;
                }

                public boolean forceFinshed() {
                    return target.getStartingCount() > 10;
                }

                public boolean beginning() {
                    return false;
                }

                public void finishated() {
                    scenario.AddMotions("WaitThenRepeat", new IMotionBody() {

                        public void MotionMethod(IMotion motion) {
                           MotionsDispatcher.addMotion(motionsReciver,MotionFactory.newMotion(20, "wait", new IMotionBody() {

                                public void MotionMethod(IMotion motion) {
                                    if (motion.isFinal()) {
                                        scenario.addAct(((IAgent) hunter).GetMainStrategy());
                                    }
                                }
                            }));
                        }

                    });
                }
            }); //.Start();
            scenario.pathGuid.addManager(target);
            hunter.setSelected(true);
        } else {
            hunter.setSelected(false);
            scenario.AddMotions( "WaitThenRepeat", new IMotionBody() {

                public void MotionMethod(IMotion motion) {
                    MotionsDispatcher.addMotion(motionsReciver,MotionFactory.newMotion(20, "wait", new IMotionBody() {

                        public void MotionMethod(IMotion motion) {
                            if (motion.isFinal()) {
                                scenario.addAct(((IAgent) hunter).GetMainStrategy());
                            }
                        }
                    }));
                }
            });
        }
        scenario.Resume();
    }
}
