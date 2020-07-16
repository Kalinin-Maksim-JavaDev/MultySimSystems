/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Models.Jumper.Agents;

import Global.Tools;
import Model.Game.World.Collisions.CollisionsMap;
import Logic.Model.Game.IGame;
import Logic.Model.Game.World.Collisions.IBodie;
import Logic.Model.Game.World.Interaction.Resolvers.ICollisionResolver;
import Model.Game.World.Unit.GameUnitNotActorState;
import Model.Models.Resolvers.IResolverCollisionWithBall;
import Model.Models.Resolvers.IResolverCollisionWithFundament;
import Model.Models.Resolvers.IResolverCollisionWithGranate;
import Model.Models.Resolvers.IResolverCollisionWithJumper;
import Platform.Calculation.ITasksReciver;

/**
 *
 * @author kalinin
 */
public class FundGroup extends GameUnitNotActorState {

    private IBodie jumper;

    public FundGroup(ITasksReciver sys_, int r, int g, int b, IGame game_, IBodie jumper) {
        super(sys_, CollisionsMap.OneRealLevel, r, g, b, game_, game_.getRatio());
        this.jumper = jumper;
    }

    public void ResolveCollisionWith(IBodie unit_, double x, double y) {
        ((IResolverCollisionWithFundament) unit_.getCollisionResolver()).ResolveCollisionWithfundament(x, y);
    }

    public void ResolveCollisionOverScope(boolean inGorscope, boolean inVertscope) {

        Tools.UnsupportedOperationException();
    }

    public states getViewState() {
        return states.zero;
    }

    @Override
    protected ICollisionResolver createCollisionResolver() {
        return new ResolverCollision();
    }

    private class ResolverCollision implements IResolverCollisionWithBall, IResolverCollisionWithJumper, IResolverCollisionWithGranate {

        public void ResolveCollisionWithBall(ICollisionResolver unit_, double x, double y) {
            ((IResolverCollisionWithFundament) unit_).ResolveCollisionWithfundament(x, y);
        }

        public void ResolveCollisionWithjumper(double x, double y) {
            ((IResolverCollisionWithFundament) jumper.getCollisionResolver()).ResolveCollisionWithfundament(x, y);
        }

        public void ResolveCollisionWithgranate(ICollisionResolver unit_, double x, double y) {
            ((IResolverCollisionWithFundament) unit_).ResolveCollisionWithfundament(x, y);
        }
    }
}
