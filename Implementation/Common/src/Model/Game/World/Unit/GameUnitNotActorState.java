/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Game.World.Unit;

import Game.Model.Game.IRatio;
import Logic.Model.Game.IGame;
import Logic.Model.Game.IGame.unit;
import Platform.Calculation.ITasksReciver;

/**
 *
 * @author Adm
 */
public abstract class GameUnitNotActorState extends GameUnitNotActor {

    public GameUnitNotActorState(ITasksReciver sys_, int realLevel_, int r, int g, int b, IGame game_,IRatio ratio) {
        super("wall", sys_, realLevel_, r, g, b, game_,ratio);
    }

    public void PreChangeUnitPosition(unit unit_) {
    }

    public boolean FallAble() {
        return false;
    }
}
