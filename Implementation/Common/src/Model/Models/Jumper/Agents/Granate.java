/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Models.Jumper.Agents;

import Model.Game.World.Collisions.CollisionsMap;
import Logic.Model.Game.IGame;
import Logic.Model.Game.IGame.unit;
import Logic.Model.Game.World.Collisions.IBodie;
import Logic.Model.Game.World.Interaction.Resolvers.ICollisionResolver;
import Model.Game.World.Unit.Command.MotionMoveWithSpeed;
import Model.Game.World.Unit.Command.MotionMoveWithSpeedAndGravity;
import Model.Game.World.Unit.GameUnit;
import Model.Game.World.Unit.GameUnitNotActor;
import Model.Geometry.D2.Coordinates2d;
import Model.Models.Resolvers.IResolverCollisionWithBall;
import Model.Models.Resolvers.IResolverCollisionWithFundament;
import Model.Models.Resolvers.IResolverCollisionWithGranate;
import Model.Models.Resolvers.IResolverCollisionWithJumper;
import Platform.Calculation.ITasksReciver;
import Platform.Core.IMotion;

/**
 *
 * @author kalinin
 */
public class Granate extends GameUnitNotActor {

    states currenState;
    GameUnit owner;
    MotionMoveWithSpeed motionMoveWithSpeedGranate;

    public Granate(ITasksReciver sys_, IGame game_) {
        super("Granade", sys_, CollisionsMap.TwoRealLevel, 0, 0, 0, game_, game_.getRatio());
        motionMoveWithSpeedGranate =  (MotionMoveWithSpeed) game.newMotionMoveWithSpeedAndGravity((int) (10000 * game.getRatio().getMoveperiod()), this);
        game.getMotionsReciver().AddMotions(motionMoveWithSpeedGranate);
    }

    public states getViewState() {
        return currenState;
    }

    public void ResolveCollisionWith(IBodie unit_, double x, double y) {
        ((IResolverCollisionWithGranate) unit_.getCollisionResolver()).ResolveCollisionWithgranate(getCollisionResolver() , x, y);
    }

    public void ResolveCollisionOverScope(boolean inGorscope, boolean inVertscope) {



        if (!inGorscope) {
            getSpeed2d().multX(-1);
        }

        if (!inVertscope) {
            getSpeed2d().multY(-1);
        }
        GetWalker().Stand();
    }

    public boolean FallAble() {
        return false;
    }

    @Override
    protected ICollisionResolver createCollisionResolver() {
        return new ResolverCollision();
    }

    public IMotion newMotionMoveWithSpeedAndGravity(int iterates_,  unit gr_) {
        return new MotionMoveWithSpeedAndGravity(iterates_, gr_, game.getRatio());

    }

    private class ResolverCollision implements IResolverCollisionWithFundament, IResolverCollisionWithBall, IResolverCollisionWithJumper {

        public void ResolveCollisionWithfundament(double x, double y) {

            if (((Coordinates2d) getPosition()).y > ((Coordinates2d) getPosition()).yd) {
                getSpeed2d().multY(-0.5);
            } else if (((Coordinates2d) getPosition()).y < ((Coordinates2d) getPosition()).yd) {
                getSpeed2d().setY(0);
            } else if (((Coordinates2d) getPosition()).y == ((Coordinates2d) getPosition()).yd) {
                getSpeed2d().multX(-0.5);
            }
            GetWalker().Stand();
        }

        public void ResolveCollisionWithBall(ICollisionResolver unit_, double x, double y) {
        }

        public void ResolveCollisionWithjumper(double x, double y) {
            GetWalker().Stand();
            Granate.this.game.getEventsHandler().Over();
        }
    }
}
