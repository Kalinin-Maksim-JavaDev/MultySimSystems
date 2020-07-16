/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Models.Scenarios.Acts;

import Game.Model.Game.IRatio;
import Logic.Model.Game.ICommonGameEventHandler;
import Logic.Model.Game.IGame;
import Logic.Model.Game.World.Exployer.Target.IPathGuidManager;
import Logic.Model.Game.World.IWalker;
import Logic.Model.Game.World.PathFindng.Guid.IPathStrategy;
import Logic.Model.Game.World.PathFindng.IPathPointsFactory;
import Logic.Model.Game.World.PathFindng.Path.TrackPoint.ITrackPoint;
import Logic.Model.Game.World.PathFindng.IPathMotionReciver;
import Logic.Model.IWaponMotionReciver;
import Logic.Model.Scenarios.IScenario;
import Logic.Model.Game.IWaponScenario;
import Model.Models.Scenarios.Target.WalkerTarget;
import Model.Models.Scenarios.WaponTarget;
import Platform.Concurrent.Factory;
import Platform.Concurrent.ILathce;
import Platform.Core.IMotion;

/**
 *
 * @author kalinin
 */
public class GoToFlat extends ScenarioAct {
    //boolean endsceanrio;
    // CountLathce countLathce;
    //int moveperiod;

    final boolean ballStop = false;
    final IWalker[] walkers;
    public ICommonGameEventHandler events;
    public ITrackPoint mainTarget;
    TargetgoBalls MainBallTarget;
    IWalker hero;
    IGame.unit[] fund;
    private IRatio ratio;
    private IPathMotionReciver pathMotionsReciver;

    public GoToFlat(ICommonGameEventHandler events, IRatio ratio, IPathMotionReciver pathMotionsReciver, IWalker hero_, IWalker[] walkers, IGame.unit[] fund_, IScenario scenario) {
        super(1, "ScenarioGoToFlat", scenario);
        this.ratio = ratio;
        this.events = events;
        this.pathMotionsReciver = pathMotionsReciver;
        this.walkers = walkers;
        fund = fund_;
//        moveperiod = moveperiod_;
        mainTarget = scenario.getPathGuid().getPath().getPathPointsFactory().Create(0, 0);
        hero = hero_;

//        endsceanrio=false;
        //countLathce=new CountLathce();
    }

    public void MotionMethod(IMotion motion) {
        //    cpuTurn.synchronizedWait();
        MainBallTarget = new TargetgoBalls(scenario.getPathGuid().getPath().getPathPointsFactory());

        hero.SetTarget(new WalkerTarget(null, null, scenario.getPathGuid().getPath().getPathPointsFactory()) {

            public void WhenAchieved() {
                //   ballStop = true;
                events.Win();

            }

            public boolean Condithion() {
                return (hero).Arrive(mainTarget.getX(), mainTarget.getY() + 1);
            }
        });
        int n = 0;



        for (int i = 0; i < walkers.length - 1; i++) {
            IWalker ball = walkers[i];
            ITrackPoint target = scenario.getPathGuid().getPath().getPathPointsFactory().Create((walkers[i + 1]).GetIntX(), (walkers[i + 1]).GetIntY());
            ball.SetTarget(new BallTarget_(ball, target, this, i, this.scenario.getPathGuid().getPath().getPathPointsFactory(), ((IWaponScenario) scenario).getWaponSystem().getWaponMotionReciver()));
            MainBallTarget.AddSubTarget(ball.GetTarget());

        }
        {
            int i = walkers.length - 1;
            ITrackPoint point = scenario.getPathGuid().getPath().getPathPointsFactory().Create(22, 0);
            IWalker ball = walkers[i];
            ball.SetTarget(new WaponTarget(ball, point, this, scenario.getPathGuid().getPath().getPathPointsFactory(), ((IWaponScenario) scenario).getWaponSystem().getWaponMotionReciver()));
            MainBallTarget.AddSubTarget(ball.GetTarget());
        }

        for (int i = 0; i < walkers.length; i++) {

            final IPathGuidManager pathManager = scenario.createPathGuidManager(walkers[i], pathMotionsReciver, ratio.getGrad(), scenario.getPathGuid());

            pathManager.setStrategy(new IPathStrategy() {

                public boolean abort() {
                    return ballStop;
                }

                public boolean retry() {
                    return true;
                }

                public void finishated() {
                    System.out.println("out");
                }

                public boolean forceFinshed() {
                    return false;
                }

                public boolean beginning() {
                    return pathManager.getStartingCount() > 1;
                }
            });//.Start();
            scenario.getPathGuid().addManager(pathManager);

        }

        MainBallTarget.TargetgoBallsAchieved.synchronizedWait();

        if (MainBallTarget.isAchivied()) {
            events.Over();

        }

        //            int[][] targetcoor = {{1, 0, 1, 0, 1},
//                {1, 0, 1, 0, 1},
//                {1, 1, 1, 1, 1},
//                {1, 1, 1, 1, 1}};
//
//
//            for (int j = 0; j < targetcoor.length; j++) {
//                for (int i = 0; i < targetcoor[0].length; i++) {
//                    if (targetcoor[targetcoor.length - 1 - j][i] == 1) {
//                        Coordinates2d target = new Coordinates2d();
//                        target.x = 22 + i;
//                        target.y = j;
//                        Agent ball = agents[n];
//                        ball.SetTarget(new TargetBall(ball, target, n));
//                        MainBallTarget.AddSubTarget(ball.GetTarget());
//                        n++;
//                    }
//                }
//            }


//            agents[0].SetTarget(new BallTarget(agents[0], hero.GetPositionLink(), this) {
//
//                public void WhenAchieved() {
//                    super.WhenAchieved();
//                    new Targeting(agents[2], game, exployer) {
//
//                        public boolean Abort() {
//                            return ballStop;
//                        }
//
//                        public boolean Retry() {
//                            return true;
//                        }
//
//                        public void Finishated() {
//                            System.out.println("out");
//                        }
//                    }.Start();
//                }
//
//                public boolean Condithion() {
//                    return ((scenarioGoToFlat.exployer.Arrive((Group) agent, position.x + 1, position.y)));
//                }
//
//                public ITrackPoint GetArivePoint() {
//                    return new ITrackPoint(position.x + 1, position.y);
//                }
//            });
//            agents[1].SetTarget(new BallTarget(agents[1], hero.GetPositionLink(), this) {
//
//                public void WhenAchieved() {
//                    super.WhenAchieved();
//                    new Targeting(agents[3], game, exployer) {
//
//                        public boolean Abort() {
//                            return ballStop;
//                        }
//
//                        public boolean Retry() {
//                            return true;
//                        }
//
//                        public void Finishated() {
//                            System.out.println("out");
//                        }
//                    }.Start();
//                }
//
//                public boolean Condithion() {
//                    return ((scenarioGoToFlat.exployer.Arrive((Group) agent, position.x - 1, position.y)));
//                }
//
//                public ITrackPoint GetArivePoint() {
//                    return new ITrackPoint(position.x - 1, position.y);
//                }
//            });
//            agents[2].SetTarget(new BallTarget(agents[2], hero.GetPositionLink(), this) {
//
//                public boolean Condithion() {
//                    return ((scenarioGoToFlat.exployer.Arrive((Group) agent, position.x + 1, position.y + 1)));
//                }
//
//                public ITrackPoint GetArivePoint() {
//                    return new ITrackPoint(position.x + 1, position.y + 1);
//                }
//            });
//            agents[3].SetTarget(new BallTarget(agents[3], hero.GetPositionLink(), this) {
//
//                public boolean Condithion() {
//                    return ((scenarioGoToFlat.exployer.Arrive((Group) agent, position.x - 1, position.y + 1)));
//                }
//
//                public ITrackPoint GetArivePoint() {
//                    return new ITrackPoint(position.x - 1, position.y + 1);
//                }
//            });
//            agents[4].SetTarget(new BallTarget(agents[4], hero.GetPositionLink(), this) {
//
//                public boolean Condithion() {
//                    return ((scenarioGoToFlat.exployer.Arrive((Group) agent, position.x, position.y + 1)));
//                }
//
//                public ITrackPoint GetArivePoint() {
//                    return new ITrackPoint(position.x, position.y + 1);
//                }
//            });
//            n = 5;
//            for (int i = 0; i < n; i++) {
//                MainBallTarget.AddSubTarget(agents[i].GetTarget());
//            }
//
//            new Targeting(agents[0], game, exployer) {
//
//                public boolean Abort() {
//                    return ballStop;
//                }
//
//                public boolean Retry() {
//                    return true;
//                }
//
//                public void Finishated() {
//                    System.out.println("out");
//                }
//            }.Start();
//            new Targeting(agents[1], game, exployer) {
//
//                public boolean Abort() {
//                    return ballStop;
//                }
//
//                public boolean Retry() {
//                    return true;
//                }
//
//                public void Finishated() {
//                    System.out.println("out");
//                }
//            }.Start();
//            new Targeting(agents[4], game, exployer) {
//
//                public boolean Abort() {
//                    return ballStop;
//                }
//
//                public boolean Retry() {
//                    return true;
//                }
//
//                public void Finishated() {
//                    System.out.println("out");
//                }
//            }.Start();




        //   countLathce.Set(agents.length);


        //    countLathce.synchronizedWait();
        // endsceanrio=true;
        // mainAI.Repeat(mainAI.Think);
    }

    public void finish() {
        MainBallTarget.TargetgoBallsAchieved.synchronizedRelease();

//        for (int i = 0; i < agents.length; i++) {
//            final Agent ball = agents[i];
//            exployer.Release();
//        }

//         ballStop = true;
    }

    class TargetgoBalls extends WalkerTarget {

        ILathce TargetgoBallsAchieved;

        public TargetgoBalls(IPathPointsFactory pointsFactory) {
            super(null, null, pointsFactory);
            TargetgoBallsAchieved = Factory.createLathce(true, false);
        }

        public void WhenAchieved() {
            TargetgoBallsAchieved.synchronizedRelease();
        }

        public boolean Condithion() {
            return true;
        }
    }

    private class BallTarget_ extends WaponTarget {

        int n;

        public BallTarget_(IWalker ball, ITrackPoint target, GoToFlat aThis, int n_, IPathPointsFactory pointsFactory, IWaponMotionReciver motionReciver) {
            super(ball, target, aThis, pointsFactory, motionReciver);
            n = n_;
        }

        public boolean Condithion() {
            return super.Condithion() && (walkers[n + 1].GetTarget().isAchivied());
        }
    }
}
