/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Models.Scenarios.Actions;

import Controler.Coordinates2dArgument;
import Logic.Model.Game.IGame;
import Logic.Model.Game.World.Exployer.Target.IPathGuidManager;
import Logic.Model.Game.World.IAgent;
import Logic.Model.Game.World.PathFindng.IPathPointsFactory;
import Logic.Model.Game.World.PathFindng.IWalkAdviser;
import Logic.Model.Game.World.PathFindng.Path.IPath;
import Logic.Model.Game.World.PathFindng.Path.TrackPoint.ITrackPoint;
import Logic.Model.Scenarios.IScenario;
import Logic.Model.Scenarios.IScenarioMotionReciver;
import Platform.Core.IArgument;
import Platform.DataStructers.ISelection;
import Platform.DataStructers.LinkedList;

/**
 *
 * @author kalinin
 */
public class TargetingBalls extends ActionScenario {

    public int x1;
    public int y1;
    LinkedList balls;

    public TargetingBalls(LinkedList balls, int grad, IScenario scenario, IScenarioMotionReciver motionsReciver) {
        super(grad, scenario, motionsReciver);
        this.balls = balls;

    }

    public void Do(IArgument[] arg_) {
        //                    if (move.Continue) {
        //                        move.Stop();
        //                        move.synchronizedWait();
        //                    }
        //                    move.Continue = true;
        Coordinates2dArgument finish = (Coordinates2dArgument) arg_[0];
        x1 = (int) (finish.x / grad);
        y1 = (int) (finish.y / grad);
//                {
//                    int dx = x1;
//                    for (int i = 0; i < countballs; i++) {
//                        if (((game.getInteractive().getCollisionMap().preCompliteUnitsMap(dx, y1) != null) && (game.getInteractive().getCollisionMap().preCompliteUnitsMap(dx, y1) instanceof Ball) && (!((Ball) game.getInteractive().getCollisionMap().preCompliteUnitsMap(dx, y1)).selected)) || (game.getInteractive().getCollisionMap().preCompliteUnitsMap(dx, y1 - 1) == null)) {
//                            x1--;
//                        } else {
//                            dx++;
//                        }
//                    }
//                    dx = x1 - 1;
//                    for (int i = 0; i < countballs; i++) {
//                        if (((game.getInteractive().getCollisionMap().preCompliteUnitsMap(dx, y1) != null) && (game.getInteractive().getCollisionMap().preCompliteUnitsMap(dx, y1) instanceof Ball) && (!((Ball) game.getInteractive().getCollisionMap().preCompliteUnitsMap(dx, y1)).selected)) || (game.getInteractive().getCollisionMap().preCompliteUnitsMap(dx, y1 - 1) == null)) {
//                            x1++;
//                        } else {
//                            dx--;
//                        }
//                    }
//                }
        IPath path_ = scenario.getPathGuid().getPath();

        BallPath[] fromleft = new BallPath[balls.size()];
        BallPath[] fromright = new BallPath[balls.size()];
        int fromleftcountballs = 0;
        int fromrightcountballs = 0;
        boolean noFromLeftSide = true;
        boolean noFromRightSide = true;
        IPathPointsFactory pointsFactory = scenario.getPathGuid().getPath().getPathPointsFactory();

        ISelection selection = balls.select();

        while (selection.getNext()) {
             IGame.unit b_ = ( IGame.unit) selection.getCurrent();
            if (b_.GetWalker().isSelected()) {

                IWalkAdviser rule = new IWalkAdviser() {

                    public boolean canWalkThrough(IAgent agent) {
                        return (( IGame.unit) agent).GetWalker().isSelected();
                    }
                };
                path_.CalculatePath(pointsFactory.Create(b_.GetWalker().GetIntX(), b_.GetWalker().GetIntY()), pointsFactory.Create(x1, y1), b_.GetWalker(), rule);
                if (path_.getLen() > 0) {
                    if (path_.getPreTarget().getX() < x1) {
                        fromleft[fromleftcountballs++] = new BallPath(path_.getLen(), b_);
                        noFromLeftSide = false;
                    } else {
                        fromright[fromrightcountballs++] = new BallPath(path_.getLen(), b_);
                        noFromRightSide = false;
                    }
                }
            }
        }


        //b_ = game.balls;

        SortBallPath(fromleft);
        SortBallPath(fromright);

        if (noFromRightSide) {
            GoBalls(fromleft, x1, -1, noFromRightSide);
        } else {
            GoBalls(fromleft, x1 - 1, -1, noFromRightSide);
            GoBalls(fromright, x1, 1, noFromLeftSide);
        }


        manipulator.bindAction(new TargetingBalls(balls, grad, scenario, scenarioMotionReciver));

        System.out.println("Selecting/");
    }

    //                private void EndTargeing() {
    //                    currentAction = actionSelectBalls;
    //                    move.Continue = false;
    //                    move.synchronizedRelease();
    //                }
    public void Abort(IArgument[] arg_) {
        // move.Stop();
    }

    public void Select(IArgument[] arg_) {
    }

    public void Selecting(IArgument[] arg_) {
    }

    public void Move(IArgument[] arg_) {
    }

    void SortBallPath(BallPath[] ballPath) {
        boolean nosort = true;
        BallPath bp;
        while (nosort) {
            nosort = false;

            for (int i = 0; i < ballPath.length - 1; i++) {
                double l1 = 999999999;
                double l2 = 999999999;
                if (ballPath[i] != null) {
                    l1 = ballPath[i].len;
                }
                if (ballPath[i + 1] != null) {
                    l2 = ballPath[i + 1].len;
                }
                if (l1 > l2) {
                    //�������� ������� �������� A[j] � A[j+1]
                    bp = ballPath[i];
                    ballPath[i] = ballPath[i + 1];
                    ballPath[i + 1] = bp;
                    nosort = true;
                }
            }

        }
    }

    private void GoBalls(BallPath[] ballPath, int dx, int k, boolean noFromOtherSide) {
        for (int i = 0; i < ballPath.length; i++) {
            if (ballPath[i] != null) {
                ballPath[i].target.setY(y1);
                if (scenario.getMap().getDot(dx * grad, (y1 - 1) * grad) != null) {
                    ballPath[i].target.setX(dx);
                    dx += k;
                } else {
                    if (noFromOtherSide) {
                        for (int j = 0; j < i; j++) {
                            ballPath[j].target.setY(y1);
                            ballPath[j].target.setX(ballPath[j].target.getX() - k);
                        }
                        ballPath[i].target.setX(dx - k);
                    }
                }
            }

        }
        for (int i = 0; i < ballPath.length; i++) {
            if (ballPath[i] != null) {
                StartBall(ballPath[i], ballPath[i].target);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }

            }
        }
    }

    void StartBall(BallPath ballPath, ITrackPoint finish_) {
         IGame.unit unit = ballPath.unit;


        unit.GetWalker().onWay().synchronizedWait();
        System.out.println("    start ball");
        unit.GetWalker().setUnselected();
        IPathGuidManager pathManager = scenario.createPathGuidSimpleManager(unit.GetWalker(), finish_, scenario.getPathGuidMotionReciver(), grad, scenario.getPathGuid());//.Start();
        scenario.getPathGuid().addManager(pathManager);

    }

    class BallPath {

        double len;
         IGame.unit unit;
        ITrackPoint target;

        BallPath(double len_,  IGame.unit unit) {
            len = len_;
            this.unit = unit;
            target = scenario.getPathGuid().getPath().getPathPointsFactory().Create(0, 0);
        }
    }
}
