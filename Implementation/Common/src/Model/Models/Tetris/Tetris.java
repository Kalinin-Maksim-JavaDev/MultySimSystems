package Model.Models.Tetris;

import Application.Build.Panels.Model.IEnvironment;
import Global.Tools;
import Model.Game.GParametr;
import Model.Game.Game;
import Model.Game.World.Collisions.CollisionsMap;
import Model.Game.World.Unit.GameUnit;
import Model.Game.World.Unit.GameUnitNotActorState;
import Model.Geometry.D2.Coordinates2d;
import Model.Geometry.VectorD;
import Model.Models.Unit.D2.Vector2d;
import Platform.Calculation.CalcSystemM;
import Platform.Core.Motion.Motion;
import Platform.Core.Unit.IUnit;
import Control.Events.Event;
import Model.Game.EventEmpty;
import Logic.Model.Game.IGame;
import Logic.Model.Game.IGame.unit.factory;
import Logic.Model.Game.World.Collisions.IBodie;
import Logic.Model.Game.World.Exployer.Target.IEvent;
import Logic.Model.Scenarios.IScenario;
import MVC.Model.IDomainModel;
import Model.Game.Ratio;
import Model.Game.World.Unit.Command.MotionMoveWithSpeed;
import Platform.Calculation.ITasksReciver;
import Platform.DataStructers.ISelection;
import Platform.ILogCanvas;
import Render.Graphics.Geometry.ICoordinates;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalinin
 */
public abstract class Tetris extends Game {

    GroupBlock blockGroup;
    GameUnit blockGroupFundament;
    GameUnit heap;
    IEvent event1;
    IEvent event2;
    IEvent event3;
    //  Group.MotionAddSpeed lastMotionAddSpped;
    MotionMoveWithSpeed MoveWithSpeed;
    protected int actualUnitsNumber = 0;
    //final GravitiTimer timer = new GravitiTimer();
    public int unitx0 = 0;
    public int unity0 = 5;
    private VectorD defSpeed;
    boolean leftAllowed = true;
    boolean rightAllowed = true;
    boolean upAllowed = true;
    boolean downAllowed = true;
    //private double maxspeed = TParametr.k / moveperiod;

    public Tetris(IEnvironment.control environment, ILogCanvas logCanvas_) {
        super(environment, logCanvas_, TParametr.w, TParametr.h, 1, new Ratio(200, GParametr.quant, ((double) GParametr.quant) / 1, TParametr.grad));
        defSpeed = new Vector2d(0, -0.5 * getRatio().getGrad() / getRatio().getMoveperiod());
    }

    @Override
    protected factory getUnitsFactory() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int[][] CreateIImageCreator() {
        return null;
    }

    public unit create(int id) {
        unit result = null;

        return result;
    }

    public void CreateGroupsSet() {

        event1 = new AddNewGroupRule();
        event2 = new StrokeLineGroupRule();
        event3 = new EndGameRule();
        CalcSystemM calc = (CalcSystemM) modcalc.getCurrent();

        heap = new GameUnitNotActorStateHeap(calc, 0, 0, 255, this);
        blockGroup = new GroupBlock(calc, 0, 255, 0, this);
        blockGroupFundament = new StateGroupNotActorBlGrGumd(calc, 255, 0, 0, this);
        {
            ISelection selection = unitsList.select();
            while (selection.getNext()) {
                int i = ((IUnit) selection.getCurrent()).getIndex();
                int y_ = i / (collisionsMap.getWeight() / 2);
                int x_ = i - y_ * (collisionsMap.getWeight() / 2) + (collisionsMap.getWeight() / 2);
                ((GameUnitNotActorStateHeap) heap).AddUnit((IUnit) selection.getCurrent(), x_, y_);
            }
        }
        //   CheckVectorsCollisionAndCompliteUnitsMove();

        DellAndAddUnit(blockGroupFundament, 0, 0);
        DellAndAddUnit(blockGroupFundament, 1, 0);
        DellAndAddUnit(blockGroupFundament, 2, 0);
        DellAndAddUnit(blockGroupFundament, 3, 0);
        DellAndAddUnit(blockGroupFundament, 4, 0);
        DellAndAddUnit(blockGroupFundament, 5, 0);
        DellAndAddUnit(blockGroupFundament, 6, 0);
        DellAndAddUnit(blockGroupFundament, 7, 0);

        AddNewGroup(blockGroup);
        {
            /*  ISelection selection = units.select();
            while (selection.getNext()) {
            UnitPoint unit = (UnitPoint) selection.getCurrent();
            IArgument[] arg = new IArgument[2];
            arg[0] = new ArgumentInt(unit.getIndex());
            arg[1] = new UnitProjectionInfo(unit.getPosition());
            getViewSource().CreateImage(arg);
            }*/
        }

        new GameUnitGroup() {

            public IGameUnitGroup SetSpeedEts() {
                MoveWithSpeed = new MotionMoveWithSpeed((int) (10000 * getRatio().getMoveperiod()), blockGroup, getRatio());
                getMotionsReciver().AddMotions(MoveWithSpeed);
//        blockGroup.speed.SetFrom(defSpeed);


//                 timer.schedule(new TimerTask() {
//
//                    public void run() {
//                        if (timer.GetProcessState()) {
//                        } else {
//                            //       motionSequence.AddMotions(blockGroup.CreateMotionMoveDown());
//                        }
//                    }
//                }, 0, 1000);
                return this;
            }

            public unit getUnit() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };

    }

    @Override
    protected void CompliteGroupSet() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IDomainModel.controllerSource getControllerSource() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IScenario createScenario(IGame.unit.list list) {
        return null;
    }

    public void AddNewGroup(GameUnit unit_) {

        blockGroup.lastUnit = null;


        //  ((GroupUnit) units()[actualUnitsNumber + 0]).next = null;
        DellAndAddUnit(blockGroup, unitx0 + 0, unity0);
        DellAndAddUnit(blockGroup, unitx0 + 1, unity0);
        DellAndAddUnit(blockGroup, unitx0 + 2, unity0);
        DellAndAddUnit(blockGroup, unitx0 + 3, unity0);
        unit_.GetWalker().getSpeed().SetFrom(defSpeed);

        // motionblockGroupMoveWithSpeed.Refresh();





//        if (actualUnitsNumber == unitsNumber) {
//            rule3.appl = true;
//        }
    }
//

//    public void Stop() {
//        super.Stop();
//        timer.cancel();
//    }
//    public boolean PauseResume() {
//        return timer.PauseResume();
//    }
    public void Reglaments() {
        //  sys.iterate(null);


//        motionSequence.AddMotions(new Motion(1, null) {
//            public void MotionMethod() {
//        int n_ = 2;
//        for (int i = 0; i < n_; i++) {
        // CalculateSeq(GParametr.quant / n_);
//            CheckVectorsCollisionAndCompliteUnitsMove();
        // }
//            }
//        });

        event1.Apply();

//
//
        event2.Apply();

//        rule3.Apply();


    }

    public void DellAndAddUnit(GameUnit unit_, int x, int y) {
        //  unit_.unit.DelUnit_(unit_);
        unit_.PreMoveUnit(x, y);
        actualUnitsNumber++;
    }

    public Motion EndScene() {
        return new Motion(1, "endScene") {

            public void MotionMethod() {
            }
        };

    }

    public Motion WinScene() {
        return new Motion(1, "winScene") {

            public void MotionMethod() {
            }
        };

    }

    public byte getId(IBodie gr, int i, int j) {
        return 0;
    }

    class AddNewGroupRule extends Event {

        public void Apply() {
            if (appl) {
                appl = false;


                GameUnit c_ = blockGroup.lastUnit;
                do {
                    //   c_.unit = blockGroupFundament;
//                game.IMap.SetPreCompliteUnitsMap(((Coordinates2d) c_.position).x, ((Coordinates2d) c_.position).y, blockGroupFundament);
                    //  c_ = c_.next;
                } while (c_ != blockGroup.lastUnit);

                //UnitGame firstBlock = blockGroup.lastUnit.next;
                // UnitGame firstFundament = blockGroupFundament.lastUnit.next;

                // blockGroup.lastUnit.next = firstFundament;
                // blockGroupFundament.lastUnit.next = firstBlock;



                AddNewGroup(blockGroup);
                //lastMotionAddSpped.counter = 0;
                MoveWithSpeed.algr.p = (Coordinates2d) blockGroup.lastUnit.asPoint().getPosition();
                leftAllowed = true;
                rightAllowed = true;
                upAllowed = true;
                downAllowed = true;
                getCollisionMap().CheckVectorsCollisionAndCompliteUnitsMove(getImaginatedProjectionsList().size());

            }
        }
    }

    class StateGroupNotActorBlGrGumd extends GameUnitNotActorState {

        GameUnit lastUnit;

        public StateGroupNotActorBlGrGumd(ITasksReciver sys_, int r, int g, int b, IGame game_) {
            super(sys_, CollisionsMap.OneRealLevel, r, g, b, game_, game_.getRatio());
        }

        public void ResolveCollisionWith(IBodie unit_, double x, double y) {
            ((GameUnit) unit_).GetWalker().Stand();
            //                unit_.speed.SetFrom(defSpeed);
            ((GameUnit) unit_).GetWalker().getSpeed().SetNull();
            event1.setAppl(true);
        }

        public void ResolveCollisionOverScope(boolean inGorscope, boolean inVertscope) {
            Tools.UnsupportedOperationException();
        }

        public states getViewState() {
            return states.zero;
        }
    }

    class GameUnitNotActorStateHeap extends GameUnitNotActorState {

        public GameUnitNotActorStateHeap(ITasksReciver sys_, int r, int g, int b, IGame game_) {
            super(sys_, CollisionsMap.OneRealLevel, r, g, b, game_, game_.getRatio());
        }

        public void ResolveCollisionWith(IBodie unit_, double x, double y) {

            Tools.UnsupportedOperationException();
        }

        public void ResolveCollisionOverScope(boolean inGorscope, boolean inVertscope) {

            Tools.UnsupportedOperationException();
        }

        public states getViewState() {
            return states.zero;
        }

        void AddUnit(IUnit unitUnit, int x_, int y_) {
        }
    }

    class StrokeLineGroupRule extends Event {

        public void Apply() {
            setAppl(true);
            if (appl) {

                boolean delline = false;
                int downCounter = 0;
                GameUnit un_;
                for (int j = 1; j < TParametr.b; j++) {
                    for (int c = 0; c < downCounter; c++) {
                        for (int i = 0; i < TParametr.a / 2; i++) {
                            IBodie gr_ = getCollisionMap().getDot(i, j);
                            if ((gr_ != null) && (gr_ == blockGroupFundament)) {
                                // u_.sys.motionSequence.AddMotions(u_.CreateMotionMoveDown());
                                //blockGroupFundament.DellAndAddUnit(gr_.GetUnit(i, j), i, j-1);
//                            game.IMap.PreMoveUnit(gr_.GetUnit(i, j), i, i - 1);
                            }

                        }
                        getCollisionMap().CheckVectorsCollisionAndCompliteUnitsMove(getImaginatedProjectionsList().size());
                    }

                    delline = true;
                    for (int i = 0; i < TParametr.a / 2; i++) {
                        IBodie gr_ = getCollisionMap().getDot(i, j);
                        if ((gr_ != null) && (gr_ == blockGroupFundament)) {
                        } else {
                            delline = false;
                            break;
                        }

                    }
                    if (delline) {
                        for (int i = 0; i < TParametr.a / 2; i++) {
                            IBodie gr_ = getCollisionMap().getDot(i, j);
                            if (gr_ == blockGroupFundament) {
                                //      un_ = gr_.GetUnit(i, j);
                                //          int y_ = un_.index / (game.getInteractive().IMap.xn / 2);
                                //          int x_ = (game.getInteractive().IMap.xn / 2) + un_.index - y_ * (game.getInteractive().IMap.xn / 2);
                                //         DellAndAddUnit(heap, un_, x_, y_);
                                actualUnitsNumber--;
                            }
                        }
                        downCounter++;
                        getCollisionMap().CheckVectorsCollisionAndCompliteUnitsMove(getImaginatedProjectionsList().size());
                    }


                }

            }
        }
    }

    class GroupBlock extends GameUnitNotActorState {

        GameUnit lastUnit;

        public GroupBlock(ITasksReciver sys_, int r, int g, int b, IGame game_) {
            super(sys_, CollisionsMap.OneRealLevel, r, g, b, game_, game_.getRatio());
        }

        public void ResolveCollisionOverScope(boolean inGorscope, boolean inVertscope) {
            GetWalker().Stand();
        }

        public states getViewState() {
            return states.one;
        }

        public void ResolveCollisionWith(IBodie unit_, double x, double y) {
        }

        //            public Motion CreateMotionMoveLeft() {
        //                lastMotionAddSpped = new MotionAddSpeed(new Vector2d(-1 / moveperiod, 0));
        //                return lastMotionAddSpped;
        //            }
        //
        //            public Motion StopMotionMoveLeft() {
        //                lastMotionAddSpped = new MotionAddSpeed(new Vector2d(1 / moveperiod, 0));
        //                return lastMotionAddSpped;
        //            }
        //
        //            public Motion CreateMotionMoveRight() {
        //                lastMotionAddSpped = new MotionAddSpeed(new Vector2d(1 / moveperiod, 0));
        //                return lastMotionAddSpped;
        //            }
        //
        //            public Motion StopMotionMoveRight() {
        //
        //                lastMotionAddSpped = new MotionAddSpeed(new Vector2d(-1 / moveperiod, 0));
        //                return lastMotionAddSpped;
        //            }
        //
        //            public Motion CreateMotionMoveUp() {
        //                lastMotionAddSpped = new MotionAddSpeed(new Vector2d(0, 1 / moveperiod));
        //                return lastMotionAddSpped;
        //            }
        //
        //            public Motion StopMotionMoveUp() {
        //                lastMotionAddSpped = new MotionAddSpeed(new Vector2d(0, -1 / moveperiod));
        //                return lastMotionAddSpped;
        //            }
        //
        //            public Motion CreateMotionMoveDown() {
        //                lastMotionAddSpped = new MotionAddSpeed(new Vector2d(0, -1 / moveperiod));
        //                return lastMotionAddSpped;
        //            }
        //
        //            public Motion StopMotionMoveDown() {
        //                lastMotionAddSpped = new MotionAddSpeed(new Vector2d(0, 1 / moveperiod));
        //                return lastMotionAddSpped;
        //            }
        public ICoordinates GetPosition() {

            Tools.UnsupportedOperationException();
            return null;
        }

        public boolean FallAble() {
            return true;
        }
        //            class AddSpeedTetr extends AddSpeed {
        //
        //                public AddSpeedTetr(Vector2d newspeed_) {
        //                    super(newspeed_);
        //
        //                }
        //
        //                public void MotionMethod() {
        //                   // speed.SetNull();
        //                    //super.MotionMethod();
        //                    Vector2d speed_=new Vector2d(speed);
        //                    speed_.Add(newspeed);
        //
        ////                    if (speed_.Module() <= maxspeed) {
        ////                        speed.SetFrom(speed_);
        ////                    }
        ////                    if ((speed.x > maxspeed)) {
        ////                        speed.x = maxspeed;
        ////                    }
        ////                    if ((speed.x < -maxspeed)) {
        ////                        speed.x = -maxspeed;
        ////                    }
        ////                    if ((speed.y > maxspeed)) {
        ////                        speed.y = maxspeed;
        ////                    }
        ////                    if ((speed.y < -maxspeed)) {
        ////                        speed.y = -maxspeed;
        ////                    }
        //                    sys.motionSequence.AddMotions(new MotionMoveWithSpeed((int)(moveperiod/dt),newspeed));
        //                }
        //            }
        //            class AddSpeedTetr extends AddSpeed {
        //
        //                public AddSpeedTetr(Vector2d newspeed_) {
        //                    super(newspeed_);
        //
        //                }
        //
        //                public void MotionMethod() {
        //                   // speed.SetNull();
        //                    //super.MotionMethod();
        //                    Vector2d speed_=new Vector2d(speed);
        //                    speed_.Add(newspeed);
        //
        ////                    if (speed_.Module() <= maxspeed) {
        ////                        speed.SetFrom(speed_);
        ////                    }
        ////                    if ((speed.x > maxspeed)) {
        ////                        speed.x = maxspeed;
        ////                    }
        ////                    if ((speed.x < -maxspeed)) {
        ////                        speed.x = -maxspeed;
        ////                    }
        ////                    if ((speed.y > maxspeed)) {
        ////                        speed.y = maxspeed;
        ////                    }
        ////                    if ((speed.y < -maxspeed)) {
        ////                        speed.y = -maxspeed;
        ////                    }
        //                    sys.motionSequence.AddMotions(new MotionMoveWithSpeed((int)(moveperiod/dt),newspeed));
        //                }
        //            }
    }

    class EndGameRule extends Event {

        public void Apply() {
            if (appl) {
                event1 = new EventEmpty();
                event2 = new EventEmpty();

                getEventsHandler().Over();
                appl = false;
            }
        }
    }
}
