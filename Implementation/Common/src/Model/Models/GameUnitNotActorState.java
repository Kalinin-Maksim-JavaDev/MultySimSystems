/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Models;

import Logic.Model.Game.IGame;
import Platform.Calculation.ITasksReciver;

/**
 *
 * @author Adm
 */
public abstract class GameUnitNotActorState extends GameUnitNotActor {

    public GameUnitNotActorState(ITasksReciver sys_, int realLevel_, int r, int g, int b, IGame game_) {
        super("wall", sys_, realLevel_, r, g, b, game_);
    }

    public void PreChangeUnitPosition(IGame.unit unit_) {
    }

    public boolean FallAble() {
        return false;
    }
}
