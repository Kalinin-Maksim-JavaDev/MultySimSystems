/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Models.Jumper.Agents;

import Game.Model.Game.IRatio;
import Model.Game.World.Collisions.CollisionsMap;
import Model.Game.World.Unit.GameUnitNotActor;
import Model.Geometry.D2.Coordinates2d;
import Platform.DataStructers.ILinkedElement;
import Model.Models.Jumper.Jumper;
import Model.Models.Jumper.MotionJump;
import Model.Models.Resolvers.IResolverCollisionWithBall;
import Model.Models.Resolvers.IResolverCollisionWithFundament;
import Model.Models.Resolvers.IResolverCollisionWithGranate;
import Model.Models.Resolvers.IResolverCollisionWithJumper;
import Logic.Model.Game.IGame;
import Logic.Model.Game.World.Collisions.IBodie;
import Logic.Model.Game.World.IAgent;
import Logic.Model.Game.World.Interaction.Resolvers.ICollisionResolver;
import Logic.Model.Scenarios.IScenarioAct;
import Model.Models.Scenarios.MotionSeach;
import Platform.Core.IMotion;
import Platform.Core.Motion.Motion;
import Platform.DataStructers.LinkedList;
import Model.Models.Scenarios.Acts.ScenarioAct;
import Model.Models.Scenarios.Hunt;
import Platform.Calculation.ITasksReciver;
import Platform.Calculation.Work;

/**
 *
 * @author kalinin
 */
public class Ball extends GameUnitNotActor implements IAgent, ILinkedElement {

    public final int[] ux = {1, 1, 1, 0, -1, -1, -1, 0, 0};
    public final int[] uy = {1, 0, -1, -1, -1, 0, 1, 1, 0};
    public ILinkedElement nextball = null;
    Granate granate;
    IRatio ratio;
    LinkedList balls;
   
    public Ball(ITasksReciver sys_, IGame game_) {
        super("Ball", sys_, CollisionsMap.OneRealLevel, 0, 0, 0, game_, game_.getRatio());

        ratio = game.getRatio();
     
        //onGrav=true;
    }

    Hunt getScenario(){
        return (Hunt) game.getScenario();
    }
    @Override
    protected ICollisionResolver createCollisionResolver() {
        return new BallCollisionsResolver();
    }

    public void AddUnit(int j, int xc, int yc) {
        super.PreMoveUnit(xc + ux[j], yc + uy[j]);
        //   unit_.Set_();

    }

    public void ResolveCollisionOverScope(boolean inGorscope, boolean inVertscope) {

        //   ms.Refresh();

        if (!inGorscope) {
            getSpeed2d().setX(-getSpeed2d().getX());
        }

        if (!inVertscope) {
            getSpeed2d().setY(-getSpeed2d().getY());
        }
        GetWalker().Stand();
    }

    @Override
    public void ResolveCollisionWith(IBodie unit_, double x, double y) {

        IResolverCollisionWithBall r = ((IResolverCollisionWithBall) unit_.getCollisionResolver());
      //  if (resolver != null) {
            r.ResolveCollisionWithBall(getCollisionResolver() , x, y);
       // }
    }

    public states getViewState() {
        if (!GetWalker().onWay().isLocked()) {
            if (selected) {
                return states.one;
            } else {
                return states.zero;
            }
        } else {
            return states.two;
        }
    }

    public IMotion CreateMotionMoveUp() {
        return new MotionJump(1, "Jump", this.GetWalker());
    }

    public void Fair() {
        double x = ((Coordinates2d) getPosition()).x;
        double y = ((Coordinates2d) getPosition()).y;

        double targetx = ((Coordinates2d) getScenario().getHero().asPoint().getPosition()).x;
        double targety = ((Coordinates2d) getScenario().getHero().asPoint().getPosition()).y;


        getSpeed2d().setX(targetx - x);
        getSpeed2d().setY(1);
        getSpeed2d().Norm();
        getSpeed2d().setX(getSpeed2d().getX() * 5 * ratio.getGrad() / ratio.getMoveperiod());
        getSpeed2d().setY(getSpeed2d().getY() * 100 * ratio.getGrad() / ratio.getMoveperiod());
        granate.currenState = states.zero;
    }

    public void PrepareGranate() {
        calcMotionsReciver.AddTask(new Work() {

            public void body() {
                double x = ((Coordinates2d) getPosition()).x;
                double y = ((Coordinates2d) getPosition()).y;
                granate = ((Jumper) game).CreateGranade();
                granate.owner = Ball.this;
                game.getCollisionMap().PreMoveUnit(granate.as2Dbodie(), x, y);
                game.getCollisionMap().CheckVectorsCollisionAndCompliteUnitsMove(game.getImaginatedProjectionsList().size());
                //  ((Coordinates2d) ((Jumper) game).granate.lastUnit.position).x = x;
                //  ((Coordinates2d) ((Jumper) game).granate.lastUnit.position).y = y;
                granate.motionMoveWithSpeedGranate.algr.p.xd = (x);
                granate.motionMoveWithSpeedGranate.algr.p.yd = (y);
            }
        });

        // System.out.println("granate getSpeed2d()" + ((Jumper) game).granate.getSpeed2d());

    }

    public void OpenFair() {
        game.getMotionsReciver().AddMotions(new MotionPrepareGranate());
        game.getMotionsReciver().Resume();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        game.getMotionsReciver().AddMotions(new MotionFair());
        game.getMotionsReciver().Resume();
        getScenario().granateExplouse.synchronizedWait();
        //System.out.println("Fair Complete");
    }

    public boolean FallAble() {
        return true;
    }

    public boolean OnWay() {
        return GetWalker().onWay().isLocked();
    }

    public IScenarioAct GetMainStrategy() {
        //if game.Seached(jupmer){
        //mainAI.Go(jumper)
        //}else{
        //game.Wait();mainAI.MainStrategy()
        //}
        return new ScenarioAct(1, "MainStrategy", getScenario()) {

            public void MotionMethod(IMotion motion) {
                scenario.getMotionReciver().AddMotions(new MotionSeach((Hunt) scenario, GetWalker(),
                        ((Hunt) scenario).getHero().GetWalker(),
                        game.getPathMotionReciver(),
                        ratio.getGrad()));
            }

            @Override
            public void finish() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    public ILinkedElement getNext() {
        return nextball;
    }

    public void setNext(ILinkedElement next) {
        nextball = next;
    }

    public class MotionExplouse extends Motion {

        public MotionExplouse() {
            super(1, "Explouse");
        }

        public void MotionMethod() {
            //     System.out.println("--explouse");
            granate.GetWalker().Stand();
            granate.getSpeed2d().SetNull();
            granate.currenState = states.one;
            granate.GetWalker().getBalisticsStrategy().StopFreeFall();
        }
    }

    public class MotionEndExplouse extends Motion {

        public MotionEndExplouse() {
            super(1, "EndExplouse");
        }

        public void MotionMethod() {
            granate.currenState = states.zero;
            //b.PrepareGranate();
            ((Jumper) game).DestroyGranade(granate);
            granate = null;
            getScenario().granateExplouse.synchronizedRelease();
//                        if (game.timer > 0) {
//                            game.timer = 0;
//                            //    System.out.println("---end CPU turn");
//                            System.out.println(game.timer);
//                        }
        }
    }

    class MotionFair extends Motion {

        public MotionFair() {
            super(1, "FairBall");
        }

        public void MotionMethod() {


            Fair();
            new Thread() {

                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    // System.out.println("to expl");
                    game.getMotionsReciver().AddMotions(new Ball.MotionExplouse());
                    game.getMotionsReciver().Resume();
                    try {
                        Thread.sleep(4 * 400);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    //  System.out.println("to end expl");
                    game.getMotionsReciver().AddMotions(new Ball.MotionEndExplouse());
                    game.getMotionsReciver().Resume();
                }
            }.start();

        }
//class MotionStartBall extends Motion {
//
//    Jumper game;
//    Ball b;
//
//    public MotionStartBall(Jumper game_, Ball b_) {
//        super(1, "StartBall");
//        game = game_;
//        b = b_;
//    }
//
//    public void MotionMethod() {
//        game.StartBall(b);
//
//    }
//}
    }

    class MotionPrepareGranate extends Motion {

        public MotionPrepareGranate() {
            super(1, "FairBall");
        }

        public void MotionMethod() {
            PrepareGranate();
        }
    }

    private class BallCollisionsResolver implements IResolverCollisionWithFundament, IResolverCollisionWithBall, IResolverCollisionWithJumper, IResolverCollisionWithGranate {

        public void ResolveCollisionWithfundament(double x, double y) {

            if (((Coordinates2d) getPosition()).y > ((Coordinates2d) getPosition()).yd) {

                //  onGrav = true;
                getSpeed2d().setY(0);
                GetWalker().getBalisticsStrategy().StopFreeFall();
                GetWalker().Stand();
                getScenario().ballLanding.synchronizedRelease();

            } else if (((Coordinates2d) getPosition()).y == ((Coordinates2d) getPosition()).yd) {
                if ((game.getCollisionMap().getDot((int) x, (int) (y) - 1) instanceof FundGroup) && ((game.getCollisionMap().getDot((int) x, (int) (y) + 1) == this) || (game.getCollisionMap().getDot((int) x, (int) (y) + 1) == null))) {
                    ((Coordinates2d) getPosition()).yd = (y + 1);
                    GetWalker().StandX();
                } else {
                    getSpeed2d().setX(0);
                    GetWalker().Stand();
                }
            } else {
                GetWalker().Stand();

            }
        }

        public void ResolveCollisionWithBall(ICollisionResolver unit_, double x, double y) {
            // if (gravity instanceof MotionProducerNon){
            ((IResolverCollisionWithFundament) unit_).ResolveCollisionWithfundament(x, y);
            //}else{
            // unit_.StandGroup();
            // }

        }

        public void ResolveCollisionWithjumper(double x, double y) {
            //game.End();
            ResolveCollisionWithfundament(x, y);
        }

        public void ResolveCollisionWithgranate(ICollisionResolver unit_, double x, double y) {
            ((IResolverCollisionWithBall) unit_).ResolveCollisionWithBall(unit_, x, y);
        }
    }
}
