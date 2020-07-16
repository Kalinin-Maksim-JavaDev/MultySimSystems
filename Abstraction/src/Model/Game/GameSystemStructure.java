/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Game;

import Game.Model.Game.IRatio;
import Game.Model.Game.World.Interaction.ICollisionMap;
import Platform.Calculation.ICalculator;
import Platform.DataStructers.ILinkedList;
import Platform.ILogCanvas;
import Model.Game.Presenter.Projections.ArgumentInt;
import Application.Build.Panels.Model.IEnvironment;
import Logic.Model.Game.IGame.IGameUnitGroup;
import Logic.Model.Scenarios.IScenario;
import Platform.Core.Unit.IUnit;

/**
 *
 * @author kalinin
 */
public class GameSystemStructure {

    protected ICollisionMap collisionsMap;
    protected ArgumentInt[] three = {new ArgumentInt(3)};
    protected ArgumentInt[] four = {new ArgumentInt(4)};
    protected ILogCanvas logCanvas;
    protected int unitCount;
    protected int ncalc;
    protected boolean finalCalc = true;
    protected boolean calculate;
    
    
    protected ICalculator modcalc;
    protected IScenario scenario;
    protected IRatio ratio;
    protected IEnvironment.control environment;
    
}
