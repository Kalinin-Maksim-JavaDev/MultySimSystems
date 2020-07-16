package Model.Game;

import Logic.Model.Scenarios.IGameMotionReciver;
import Game.Model.Game.IRatio;
import MVC.View.IViewSource;
import Platform.Calculation.Calculator;
import Controler.Coordinates2dArgument;
import Platform.Calculation.ICalculator;
import Platform.DataStructers.ILinkedElement;
import Platform.DataStructers.ISelection;
import Platform.Util.Profiler.ClassAssociations;
import Application.Build.Panels.Model.IEnvironment;
import Control.KeysID;
import Platform.Core.IArgument;
import Platform.Core.IExecuter;
import Platform.Core.IMotion;
import Platform.ILogCanvas;
import Global.Tools;
import Model.Game.World.Collisions.CollisionsMap;
import Game.Model.Game.World.Interaction.ICollisionMap;
import Model.Game.World.Unit.Command.MotionMoveWithSpeedAndGravity;
import Model.Models.Unit.D2.Vector2d;
import Platform.DataStructers.ILinkedList;
import Platform.DataStructers.LinkedList;
import Logic.Model.Game.IGame;
import Logic.Model.Game.World.Collisions.IBodie;
import Logic.Model.Game.World.PathFindng.IPathMotionReciver;
import Logic.Model.IDataSource;
import Logic.Model.Scenarios.IScenario;
import Logic.Model.Scenarios.IScenarioMotionReciver;
import MVC.Control.IKeysMap;
import MVC.Model.IDomainModel.Imaginated;
import Model.Game.World.Unit.GameUnit;
import Model.Geometry.D2.Coordinates2d;
import Platform.Core.Motion.Motion;
import Platform.Core.Unit.IUnit;
import Platform.Core.Unit.IUnit.drawable.projection;
import Platform.Util.Area.Area;
import Render.Graphics.Geometry.IVectorD;
import Render.View.IImaginatorsFactorys;
import View.Presenter.Projections.IViewUnit;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalinin
 */
public abstract class Game extends GameSystemStructure implements IGame {

    abstract protected IGame.unit.factory getUnitsFactory();

    abstract protected void CreateGroupsSet();

    abstract protected void CompliteGroupSet();

    abstract protected IScenario createScenario(IGame.unit.list list);
    public boolean DEBUG_outToGameMenu;
    protected ILinkedList unitsList;
    private UnitsListManager unitsListManager;
    private ProjectionsListManager projectionsManager;
    protected Area fundamentArea;

    public Game(IEnvironment.control environment, ILogCanvas logCanvas, int w, int h, int calcprocess, IRatio ratio) {
        ClassAssociations.PrintAssociaties();
        fundamentArea = new Area();
        this.environment = environment;
        this.ratio = ratio;
        this.logCanvas = logCanvas;

        ncalc = (int) (ratio.getQuant() / ratio.getQuant());

        collisionsMap = new CollisionsMap(w, h, ratio);


        modcalc = new Calculator();
        for (int i = 0; i < calcprocess; i++) {
            modcalc.AddCalc(environment.getLogger());
        }
    }

    public UnitsListManager getUnitsListManager() {

        if (unitsListManager == null) {
            unitsListManager = new UnitsListManager();
        }
        return unitsListManager;
    }

    public ProjectionsListManager getProjectionsManager() {

        if (projectionsManager == null) {
            projectionsManager = new ProjectionsListManager();
        }
        return projectionsManager;
    }

    public withUnits createUnits(IDataSource dataSource) {
        unitsList = new LinkedList();

        GameData gameData = (GameData) dataSource.GetData();
        IGame.unit.factory unitsFactory = getUnitsFactory();

        CreateGroupsSet();

        int index = 0;

        for (int y = 0; y < gameData.gameMap.length / ratio.getGrad(); y++) {
            for (int x = 0; x < gameData.gameMap[y].length / ratio.getGrad(); x++) {

                int c = gameData.gameMap[y * ratio.getGrad()][x * ratio.getGrad() + ratio.getGrad() / 2];
                if (c == 0) {
                    continue;
                }

                GameFabric.unitsType type = GameFabric.unitsType.get(c);

                if (type != null) {

                    IGame.unit unit = unitsFactory.create(type.getId());

                    Coordinates2d pos = (Coordinates2d) unit.asPoint().getPosition();

                    pos.xd = x;
                    pos.yd = y;

                    GameUnit gameUnit = (GameUnit) unit;
                    gameUnit.setTypeID(type.getId());
                    for (int jm = 0; jm < gameUnit.map.length; jm++) {
                        for (int im = 0; im < gameUnit.map[0].length; im++) {

                            int cgu = (gameData).gameMap[y * ratio.getGrad() + jm][x * ratio.getGrad() + im];
                            gameUnit.map[jm][im] = (cgu != 0);
                        }

                        gameUnit.PreMoveUnit(x * getRatio().getGrad(), y * getRatio().getGrad());
                    }

                    unit.asUnit().setIndex(index++);

                    getUnitsListManager().add(unit.asUnit());
                }
            }
        }

        CompliteGroupSet();
        allowCalculate();

        scenario = createScenario(getUnitsListManager().asGameUnitslist());
        scenario.begin();

        return this;
    }

    public Imaginated imaginateUnits(IViewSource viewSource) {

        viewSource.imaginate(getViewUnitList());
        return this;
    }

    public IViewUnit.list getViewUnitList() {
        return getImaginatedProjectionsList().asViewUnitslist();
    }

    public IUnit.drawable.projection.list getImaginatedProjectionsList() {
        return getProjectionsManager();
    }

    public ILinkedList getUnits() {
        return unitsList;
    }

    public IGameMotionReciver getMotionsReciver() {
        return environment.getGameMotionReciver();
    }

    public IPathMotionReciver getPathMotionReciver() {
        return environment.getPathMotionReciver();
    }

    public IScenarioMotionReciver getScenarioPathMotionReciver() {
        return scenario.getMotionReciver();
    }

    public IArgument[] getThree() {
        return three;
    }

    public void setFinalCalc(boolean finalCalc) {
        this.finalCalc = finalCalc;
    }

    public void StopEvent() {
        modcalc.Stop();
    }

    public IMotion newMotionMoveWithSpeedAndGravity(int iterates_, IGame.unit gr_) {
        return new MotionMoveWithSpeedAndGravity(iterates_, gr_, getRatio());

    }

    public IMotion newMotionGameSave(final IArgument[] arg) {
        Tools.UnsupportedOperationException();
        return null;
    }

    public IMotion newMotionGameLoad(final IArgument[] arg) {
        Tools.UnsupportedOperationException();
        return null;
    }

    public IMotion NewMotionGamePauseResume(int i, String string, IExecuter ee_, final IArgument[] arg) {
        return new Motion(1, "PauseResume") {

            IArgument[] _arg = arg;

            @Override
            public void MotionMethod() {
                if (((Coordinates2dArgument) _arg[0]).x == 0) {
                    stopCalculate();
                    if (DEBUG_outToGameMenu) {
                        System.out.println("Game menu already showed!!!!!!!");
                        //       Util.Tools.UnsupportedOperationException();
                    } else {
                        DEBUG_outToGameMenu = true;
                    }
                } else {
                    allowCalculate();
                    DEBUG_outToGameMenu = false;
                }
            }
        };
    }

    public boolean isCalculated() {
        return calculate;
    }

    public void allowCalculate() {
        calculate = true;
    }

    public void stopCalculate() {
        calculate = false;
    }

    public ILogCanvas getLogCanvas() {
        return logCanvas;
    }

    public boolean isFinalCalc() {
        return finalCalc;
    }

    public IRatio getRatio() {
        return ratio;
    }

    public ICollisionMap getCollisionMap() {
        return collisionsMap;
    }

    public int getIterationNumber() {
        return (int) (getRatio().getMoveperiod() / getRatio().getDt());
    }

    public IScenario getScenario() {
        return scenario;
    }

    public IUnit.drawable.projection[] activeProjections() {

        IBodie[] resolved = null;
        if (isCalculated()) {
            for (int i = 0; i < ncalc(); i++) {
                setFinalCalc(i == ncalc() - 1);
                environment.RegBeg("start calc " + i);
                getmodcalc().Do();
                environment.RegEnd("start calc " + ncalc());
                getmodcalc().getMonitor().synchronizedWait();

//                 demo.begin.synchronizedRelease();
//                 demo.end.synchronizedWait();
                resolved = getCollisionMap().CheckVectorsCollisionAndCompliteUnitsMove(projectionsManager.size());
            }

        }

        IUnit.drawable.projection[] result = new IUnit.drawable.projection[resolved.length];

        for (int i = 0; i < resolved.length; i++) {
            projection pr = (projection) resolved[i];
            result[i] = pr;
        }
        return result;
    }

    public ICalculator getmodcalc() {
        return modcalc;
    }

    public int ncalc() {
        return ncalc;
    }

    public projection[] visibleProjections() {
        IUnit.drawable.projection.list _list = getImaginatedProjectionsList();

        projection[] result = new projection[unitsList.size()];

        int i = 0;


        ProjectionsListManager projectionsList = new ProjectionsListManager();
        IUnit.drawable.projection.list.selection selection = projectionsList.select();

        while (!selection.end()) {

            projection _unit = (projection) selection.get();

            if (_unit.isVisible()) {
                result[i++] = _unit;
            }
        }
        return result;
    }

    public Object[][] DEBUG_getMap() {
        return collisionsMap.getMap();
    }

    protected class UnitsListManager implements IUnit.list {

        public IUnit add(IUnit e) {
            return (IUnit) unitsList.add((ILinkedElement) e);
        }

        public selection select() {
            return new selection() {

                ISelection select = unitsList.select();

                public boolean end() {

                    return !select.getNext();
                }

                public IUnit get() {
                    return (IUnit) select.getCurrent();
                }
            };
        }

        public IGame.unit.list asGameUnitslist() {
            return new IGame.unit.list() {

                public IGame.unit add(IViewUnit e) {
                    return (IGame.unit) unitsList.add((ILinkedElement) e);
                }

                public selection select() {
                    return new IGame.unit.list.selection() {

                        ISelection select = unitsList.select();

                        public boolean end() {

                            return !select.getNext();
                        }

                        public IGame.unit get() {
                            return (IGame.unit) select.getCurrent();
                        }
                    };
                }
            };
        }
    }

    protected class ProjectionsListManager implements IUnit.drawable.projection.list {

        public IUnit.drawable.projection.list.selection select() {
            return new IUnit.drawable.projection.list.selection() {

                ISelection select = unitsList.select();

                public boolean end() {

                    return !select.getNext();
                }

                public IUnit.drawable.projection get() {
                    return (IUnit.drawable.projection) select.getCurrent();
                }
            };
        }

        public int size() {
            return unitsList.size();
        }

        public IViewUnit.list asViewUnitslist() {
            return new IViewUnit.list() {

                public ILinkedList getList() {
                    return unitsList;
                }

                public IViewUnit add(IViewUnit e) {
                    return (IViewUnit) unitsList.add((ILinkedElement) e);
                }

                public selection select() {
                    return new IViewUnit.list.selection() {

                        ISelection select = unitsList.select();

                        public boolean end() {

                            return !select.getNext();
                        }

                        public IViewUnit get() {
                            return (IViewUnit) select.getCurrent();
                        }
                    };
                }
            };
        }
    }

    public abstract class GameUnitGroup implements IGameUnitGroup {

        public IGameUnitGroup CompliteGroupSet() {

            IBodie c_ = ((IBodie) getCollisionMap().currentToCheck().get());
            while (c_ != null) {
                getCollisionMap().CompliteGroupSet(c_);
                c_.removeFromCheck();
                c_ = c_.getPrevToCheck();
            }
            //  getCollisionMap().currentToCheck().set(null);
            return this;
        }
    }

    public class KeysMap implements IKeysMap {

        public String getName(int code) {
            if (code == 65) {
                return "MoveRight";
            }

            if (code == 68) {
                return "MoveLeft";
            }

            if (code == 87) {
                return "MoveUp";
            }

            if (code == 83) {
                return "MoveDown";
            }

            if (code == 32) {
                return "MoveUp";
            }

            if (code == KeysID.Space) {
                return "MoveUp";
            }

            if (code == KeysID.Esc) {
                return "Esc";
            }

            if (code == KeysID.F5) {
                return "Save";
            }

            if (code == 1) {
                return "MouseLeft";
            }

            if (code == 3) {
                return "MouseRight";
            }

            if (code == 0) {
                return "Selecting";
            }

            if (code == 6) {
                return "Select";
            }

            if (code == 5) {
                return "Move";
            }

            return null;
        }
    }
}
