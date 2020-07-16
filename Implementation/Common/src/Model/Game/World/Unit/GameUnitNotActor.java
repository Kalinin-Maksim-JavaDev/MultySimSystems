/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Game.World.Unit;

import Game.Model.Game.IRatio;
import Logic.Model.Game.IGame;
import Logic.Model.Game.World.Interaction.Resolvers.ICollisionResolver;
import Platform.Calculation.ITasksReciver;

/**
 *
 * @author Adm
 */
public abstract class GameUnitNotActor extends GameUnit {

    public GameUnitNotActor(String name, ITasksReciver motionReciver, int realLevel_,  int r, int g, int b,IGame game_,IRatio ratio) {
        super(name, motionReciver, realLevel_, r, g, b, game_,ratio);

    }

    @Override
    protected ICollisionResolver createCollisionResolver() {
        return new CollisionResolver();
    }

    private class CollisionResolver implements ICollisionResolver {
    }


}
