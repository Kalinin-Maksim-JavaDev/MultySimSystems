/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Model.Game;

import Logic.Model.Scenarios.IGameMotionReciver;
import Game.Model.Game.IRatio;
import MVC.Model.IDomainModel;
import Logic.Model.Game.World.Collisions.IBodie;
import Logic.Model.Game.World.Collisions.Resolvers.IResolverDispather;
import Logic.Model.Game.World.IWalker;
import Logic.Model.Game.World.PathFindng.IPathMotionReciver;
import Logic.Model.Scenarios.IScenario;
import Logic.Model.Scenarios.IScenarioMotionReciver;
import Platform.Calculation.ICalculatable;
import Platform.Calculation.ICalculator;
import Platform.Core.IArgument;
import Platform.Core.IMotionsProducer;
import Platform.Core.Unit.IUnit;
import Platform.ILogCanvas;
import Render.Graphics.Geometry.IPointed;

/**
 *
 * @author kalinin
 */
public interface IGame extends IGameMotionFactory, ICalculatable, IDomainModel, IDomainModel.withUnits, IDomainModel.asGame {

    interface unit extends IMotionsProducer, IResolverDispather, IArgument {

        IUnit asUnit();

        IPointed asPoint();

        interface factory {

            unit create(int id);
        }

        interface list {

            selection select();

            interface selection {

                boolean end();

                unit get();
            }
        }

        IMotionsProducer getMotionsProducer();

        IWalker GetWalker();

        IBodie getBodie();
    }

    ICommonGameEventHandler getEventsHandler();

    IPathMotionReciver getPathMotionReciver();

    IScenarioMotionReciver getScenarioPathMotionReciver();

    ILogCanvas getLogCanvas();

    boolean isFinalCalc();

    int getIterationNumber();

    IRatio getRatio();

    IScenario getScenario();

    int ncalc();

    ICalculator getmodcalc();

    void setFinalCalc(boolean b);

    public IGameMotionReciver getMotionsReciver();

    public interface IGameUnitGroup {

        public IGameUnitGroup CompliteGroupSet();

        public IGameUnitGroup SetSpeedEts();

        public unit getUnit();
    }
}
