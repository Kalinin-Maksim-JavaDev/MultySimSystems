package Model.Models.Jumper;

import Application.Build.Panels.Control.Manipulator.IManipulator.IAction;
import Application.Build.Panels.Model.IEnvironment;
import Controler.Coordinates2dArgument;
import Logic.Model.Game.IGame;
import Logic.Model.Game.World.Collisions.IBodie;
import Logic.Model.IDataSource;
import Logic.Model.IWaponGame;
import Logic.Model.IWaponMotionReciver;
import Logic.Model.Scenarios.IScenario;
import MVC.Control.IKeysMap;
import MVC.Model.IDomainModel;
import Model.Game.GParametr;
import Model.Game.GameData;
import Model.Game.GameFabric;
import Model.Game.GameFabric.unitsType;
import Model.Game.Game;
import Model.Game.Ratio;
import Model.Game.World.Unit.GameUnit;
import Model.Geometry.D2.Coordinates2d;
import Model.Models.Jumper.Agents.Ball;
import Model.Models.Jumper.Agents.FundGroup;
import Model.Models.Jumper.Agents.Granate;
import Model.Models.Jumper.Agents.GameUnitJumper;
import Model.Models.Scenarios.Actions.SelectBalls;
import Model.Models.Scenarios.Hunt;
import Platform.Core.IArgument;
import Platform.Core.IMotion;
import Platform.Core.IMotionsProducer;
import Platform.Core.Motion.Motion;
import Platform.Core.Systems.SystemM;
import Platform.DataStructers.ILinkedList;
import Platform.DataStructers.ISelection;
import Platform.DataStructers.LinkedContainers;
import Platform.DataStructers.LinkedList;
import Platform.ILogCanvas;
import Platform.Util.Area.Area;
import Platform.Util.Area.PolygonSimple;
import Platform.Util.File;
import Presenter.Platformer.JParametr;
import java.util.Random;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalinin
 */
public abstract class Jumper extends Game implements IWaponGame {

//    public AISystemT turnsTimer;
//    final int turnTime = 5000;
//    int timer = turnTime;
//    boolean userTurn;
//    Lathce cpuTurn;
    // Group.MotionMoveWithSpeedAndGravity jspeed;
    final public Random random;
    final public Coordinates2d[] jumperViewVector = {new Coordinates2d(), new Coordinates2d()};
    public WaponSystem waponSystem;
    ILinkedList ballContainers;
    FundGroup blockGroupFundament;
    protected IGameUnitGroup jumperGroup;

    public Jumper(IEnvironment.control environment, ILogCanvas logCanvas_) {
        super(environment, logCanvas_, JParametr.w * JParametr.grad, JParametr.h * JParametr.grad, 2, new Ratio(500, GParametr.quant, ((double) GParametr.quant) / 2, JParametr.grad));
        ballContainers = new LinkedContainers();
        random = new Random();
    }

    public void StopEvent() {
        super.StopEvent();

        if (waponSystem != null) {
            waponSystem.Stop();
        }

        scenario.end();
    }

    protected void CreateGroupsSet() {
        jumperGroup = new JumperGroup(new GameUnitJumper(modcalc.getCurrentReciver(), 0, 255, 0, this));
    }

    protected void CompliteGroupSet() {
        jumperGroup.CompliteGroupSet();
        jumperGroup.SetSpeedEts();
    }

    @Override
    protected IGame.unit.factory getUnitsFactory() {
        return new IGame.unit.factory() {

            public unit create(int id) {
                unit result = null;

                unitsType type = GameFabric.unitsType.get(id);

                if (type == GameFabric.unitsType.fundament) {

                    result = new FundGroup(modcalc.getCurrentReciver(), 255, 0, 0, Jumper.this, getJumper().getBodie());
                    result.asUnit().setName(" Fund ");

                } else if (type == GameFabric.unitsType.fundament2) {

                    result = new FundGroup(modcalc.getCurrentReciver(), 255, 0, 0, Jumper.this, getJumper().getBodie());
                    result.asUnit().setName(" Fund ");

                } else if (type == GameFabric.unitsType.jumper) {

                    result = getJumper();
                    result.asUnit().setName(" Jumper ");

                } else if (type == GameFabric.unitsType.granate) {

                    result = CreateGranade();
                    result.asUnit().setName(" Grande ");

                } else if (type == GameFabric.unitsType.ball) {

                    result = new Ball(modcalc.getCurrentReciver(), Jumper.this);
                    result.asUnit().setName(" Ball ");
                    ballContainers.add(result.asUnit().asLinkedElement());
                }
                return result;
            }
        };
    }

    public Granate CreateGranade() {
        Granate granate = new Granate(modcalc.getCurrentReciver(), this);

        JParametr.Granade = granate;

        granate.GetWalker().getBalisticsStrategy().StopFreeFall();
        return granate;
    }

    public void DestroyGranade(Granate granate) {
        unitsList.del(JParametr.Granade.asUnit().asLinkedElement());
    }

    public IDomainModel.controllerSource getControllerSource() {
        return new controllerSource() {

            public IKeysMap getKeysMap() {
                return new KeysMap();
            }

            public IAction getAction() {

               
                //  mainAI.AddMotions(mainAI.scenario);

                return new SelectBalls(getRatio().getGrad(), scenario, getScenarioPathMotionReciver(), (LinkedList) ballContainers);
            }

            public IMotionsProducer getControlMotionsProducer() {

                return getJumper().getMotionsProducer();
            }
        };
    }

    @Override
    protected IScenario createScenario(unit.list manager) {


        unit.list.selection selection = manager.select();

        while (selection.end()) {

            GameUnit gameUnit = (GameUnit) selection.get();
            if (gameUnit.getTypeID() == GameFabric.unitsType.fundament.getId()) {

                Coordinates2d position = (Coordinates2d) gameUnit.asPoint().getPosition();

                PolygonSimple p_ = new PolygonSimple(false);
                int x_ = (int) (4 * position.x - 2);
                int y_ = (int) (4 * position.y - 2);
                int x2_ = (int) (4 * position.x + 2);
                int y2_ = (int) (4 * position.y + 2);
                p_.addPoint(x_, y_);
                p_.addPoint(x2_, y_);
                p_.addPoint(x2_, y2_);
                p_.addPoint(x_, y2_);
                fundamentArea.Join(p_);
            }
        }
        Area finalarea = fundamentArea.Complite(3);
        //finalarea.RunFrame();
        finalarea.Glide();
        return new Hunt(collisionsMap, this, (LinkedContainers) ballContainers, getJumper(), finalarea, this);
    }

    public IMotion newMotionGameSave(final IArgument[] arg, final IDataSource dataSource_) {
        return new Motion(1, "Save") {

            int slot = (int) ((Coordinates2dArgument) arg[0]).x;
            IDataSource dataSource = dataSource_;

            public void MotionMethod() {
                GameData gameData = (GameData) dataSource.GetData();
                gameData.UpDate(Jumper.this, getRatio());
                File.Save(gameData.ToData(), slot);
            }
        };
    }

    public byte getId(IBodie gr, int i, int j) {
        byte point;

        if (gr instanceof FundGroup) {
            point = (byte) GameFabric.unitsType.fundament.getId();
            if ((i == 0) || (j == 0)) {
                point = (byte) GameFabric.unitsType.fundament2.getId();
            } else if ((i == JParametr.w - 1) || (j == JParametr.h - 1)) {
                point = (byte) GameFabric.unitsType.fundament2.getId();
            }

        } else if (gr instanceof GameUnitJumper) {
            point = (byte) GameFabric.unitsType.jumper.getId();
        } else if (gr instanceof Ball) {
            point = (byte) GameFabric.unitsType.ball.getId();
        } else {
            point = 0;
        }
        return point;
    }

    public IWaponMotionReciver getWaponMotionReciver() {

        if (waponSystem == null) {
            waponSystem = new WaponSystem() {
            };
            //    turnsTimer = new AISystemT();

            waponSystem.Start();
        }

        return waponSystem;
    }

    private GameUnit getJumper() {
        return (GameUnit) jumperGroup.getUnit();
    }

    public Object getExecuterPauseResume() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    class WaponSystem extends SystemM implements IWaponMotionReciver {

        public WaponSystem() {
            super("A\t\t\t\t\t\t\t\t\t|");
        }

        public String SetThreadName() {
            return "ArsenalAI";
        }
    }

    private class JumperGroup extends GameUnitGroup {

        GameUnitJumper jumper;

        public JumperGroup(GameUnitJumper jumper) {
            this.jumper = jumper;
        }

        public unit getUnit() {
            return jumper;
        }

        public IGameUnitGroup SetSpeedEts() {
            {
                getMotionsReciver().AddMotions(newMotionMoveWithSpeedAndGravity((int) (10000 * ratio.getMoveperiod()), jumper));
                if (collisionsMap.getDot((jumper.GetWalker().GetIntX()) * ratio.getGrad(), (jumper.GetWalker().GetIntY() - 1) * ratio.getGrad()) == null) {
                    jumper.GetWalker().getBalisticsStrategy().SetFreeFall();
                } else {
                    jumper.GetWalker().getBalisticsStrategy().StopFreeFall();
                }
            }
            {
                ISelection selection = ballContainers.select();
                while (selection.getNext()) {
                    Ball b_ = (Ball) selection.getCurrent();
                    getMotionsReciver().AddMotions(newMotionMoveWithSpeedAndGravity((int) (10000 * ratio.getMoveperiod()), b_));
                    if (collisionsMap.getDot((b_.GetWalker().GetIntX()) * ratio.getGrad(), (b_.GetWalker().GetIntY() - 1) * ratio.getGrad()) == null) {
                        b_.GetWalker().getBalisticsStrategy().SetFreeFall();
                    } else {
                        b_.GetWalker().getBalisticsStrategy().StopFreeFall();
                    }
                }
            }
          
            //   getViewSource().PutImage(AmbientUnit, Arguments.getZero());
            //   getViewSource().PutImage(TargetUnit, Arguments.getZero());
            //   getViewSource().PutImage(AmbientUnit, Arguments.getZero());
            //   getViewSource().PutImage(TargetUnit, Arguments.getZero());
            return this;
        }
    }
}
