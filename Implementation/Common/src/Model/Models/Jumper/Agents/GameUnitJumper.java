/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Models.Jumper.Agents;

import Game.Model.Game.World.Interaction.ICollisionMap;
import Model.Game.World.Collisions.CollisionsMap;
import Model.Game.World.Unit.Command.MotionAddSpeed;
import Model.Game.World.Unit.Command.MotionNullXSpeed;
import Model.Game.World.Unit.GameUnit;

import Model.Geometry.D2.Coordinates2d;
import Model.Models.Jumper.MotionJump;
import Model.Models.Resolvers.IResolverCollisionWithBall;
import Model.Models.Resolvers.IResolverCollisionWithFundament;
import Model.Models.Resolvers.IResolverCollisionWithGranate;
import Model.Models.Resolvers.IResolverCollisionWithJumper;
import Model.Models.Scenarios.WaponTarget;
import Logic.Model.Game.IGame;
import Logic.Model.Game.World.Collisions.IBodie;
import Logic.Model.Game.World.IAgent;
import Logic.Model.Game.World.Interaction.Resolvers.ICollisionResolver;
import Logic.Model.Scenarios.IScenarioAct;
import Model.Models.Scenarios.Acts.ScenarioAct;
import Model.Models.Unit.D2.Vector2d;
import Platform.Calculation.ITasksReciver;
import Platform.Core.IMotion;
import Platform.Core.Motion.Motion;
import Render.Graphics.Geometry.ICoordinates;

/**
 *
 * @author kalinin
 */
public class GameUnitJumper extends GameUnit implements IAgent {

    public WaponTarget placeAchive;
    volatile private boolean onWay;
    //Object[] viewState = zero;

    public GameUnitJumper(ITasksReciver motionReciver, int r, int g, int b, IGame game_) {
        super("Jumper", motionReciver, CollisionsMap.OneRealLevel, r, g, b, game_, game_.getRatio());

    }

//        boolean leftAllowed = true;
//        boolean rightallowed = true;
//        boolean upAllowed = true;
//        boolean downAllowed = true;
    public void ResolveCollisionOverScope(boolean inGorscope, boolean inVertscope) {



        if (!inGorscope) {
            getSpeed2d().multX(-1);
        }

        if (!inVertscope) {
            getSpeed2d().multY(-1);
        }
        asWalker().Stand();
        //  jspeed.Refresh();
    }

    public Motion CreateMotionMoveLeft() {
        return new MotionAddSpeed(new Vector2d(-2 * ratio.getGrad() / ratio.getMoveperiod(), 0), asWalker().getSpeed(), calcMotionsReciver);
    }

    public Motion StopMotionMoveLeft() {
        return new MotionNullXSpeed(asWalker());
    }

    public Motion CreateMotionMoveRight() {
        return new MotionAddSpeed(new Vector2d(2 * ratio.getGrad() / ratio.getMoveperiod(), 0), asWalker().getSpeed(), calcMotionsReciver);
    }

    public Motion StopMotionMoveRight() {
        return new MotionNullXSpeed(asWalker());
    }

    public Motion CreateMotionMoveUp() {
        return new MotionJump(1, "Jump", asWalker());
    }

    public Motion StopMotionMoveUp() {
        return null;
    }

    public Motion CreateMotionMoveDown() {
        asWalker().getBalisticsStrategy().SetFreeFall();
        return new MotionAddSpeed(new Vector2d(0, -2 * ratio.getGrad() / ratio.getMoveperiod()), asWalker().getSpeed(), calcMotionsReciver);
    }

    public Motion StopMotionMoveDown() {
        return null;
    }

    public void ResolveCollisionWith(IBodie unit_, double x, double y) {
        if (unit_ == this.getItem()) {
            return;
        }
        ((IResolverCollisionWithJumper) unit_.getCollisionResolver()).ResolveCollisionWithjumper(x, y);

    }

    public states getViewState() {

        if (getSpeed2d().getY() != 0) {
            if (getSpeed2d().getY() > 0) {
                return states.Jumper_State;
            } else {
                return states.Jumper_State;
            }
        } else if (getSpeed2d().getX() != 0) {
            return states.Jumper_Run;
        } else {
            return states.Jumper_State;
        }
    }

    public boolean FallAble() {
        return true;
    }

    public boolean OnWay() {
        return false;
    }

    public IScenarioAct GetMainStrategy() {
        return new ScenarioAct(1, "MainStrategy", null) {

            public void MotionMethod(IMotion motion) {
            }

            @Override
            public void finish() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    public ICoordinates GetPositionLink() {
        return as2Dbodie().getPosition();
    }

    @Override
    protected ICollisionResolver createCollisionResolver() {
        return new ResolverCollision();
    }

    public void OpenFair() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private class ResolverCollision implements IResolverCollisionWithFundament, IResolverCollisionWithBall, IResolverCollisionWithGranate {

        public void ResolveCollisionWithBall(ICollisionResolver unit_, double x, double y) {
            //game.End();
            ((IResolverCollisionWithFundament) unit_).ResolveCollisionWithfundament(x, y);
        }

        public void ResolveCollisionWithgranate(ICollisionResolver unit_, double x, double y) {
            //game.End();
            ((IResolverCollisionWithJumper) unit_).ResolveCollisionWithjumper(x, y);
        }

        public void ResolveCollisionWithfundament(double x, double y) {



            if (((Coordinates2d) getPosition()).y > ((Coordinates2d) getPosition()).yd) {

                //  onGrav = true;
                getSpeed2d().setY(0);
                asWalker().getBalisticsStrategy().StopFreeFall();
                asWalker().Stand();

            } else if (((Coordinates2d) getPosition()).y == ((Coordinates2d) getPosition()).yd) {

                ICollisionMap imap = game.getCollisionMap();

                if ((imap.getDot((int) x, (int) (y) - 1) instanceof FundGroup)
                        && ((imap.getDot((int) x, (int) (y) + 1) == this)
                        || imap.getDot((int) x, (int) (y) + 1) == null)) {
                    ((Coordinates2d) GameUnitJumper.this.getPosition()).yd = (y + 1);
                    asWalker().StandX();
                } else {
                    getSpeed2d().setX(0);
                    asWalker().Stand();
                }
            } else {
                asWalker().Stand();
            }
        }
    }
}
