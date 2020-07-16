package Model.Models.PingPong;

import Application.Build.Panels.Model.IEnvironment;
import Model.Game.GParametr;
import Model.Game.Game;
import Model.Game.World.Unit.GameUnitNotActor;
import Model.Game.World.Collisions.CollisionsMap;
import Model.Game.World.Unit.Command.MotionMoveWithSpeed;
import Model.Game.World.Unit.GameUnit;
import Platform.Core.Motion.Motion;
import Model.Geometry.D2.Coordinates2d;
import Logic.Model.Game.IGame;
import Logic.Model.Game.IGame.unit.factory;
import Logic.Model.Game.World.Collisions.IBodie;
import Logic.Model.Scenarios.IScenario;
import MVC.Model.IDomainModel;
import Model.Game.Ratio;
import Model.Models.Unit.D2.Vector2d;
import Platform.Calculation.CalcSystemM;
import Platform.Calculation.ITasksReciver;
import Platform.ILogCanvas;
import Render.Graphics.Geometry.D2.IVector2d;
import java.util.Random;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalinin
 */
public abstract class PingPong extends Game {

    PBall balls[];
    public static Random random = new Random();

    public PingPong(IEnvironment.control environment, ILogCanvas logCanvas_) {
        super(environment, logCanvas_, PParametr.w, PParametr.h, 2, new Ratio(1000, GParametr.quant, ((double) GParametr.quant) / 10, PParametr.grad));
    }

    public int[][] CreateIImageCreator() {
        return null;
    }

    @Override
    protected factory getUnitsFactory() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public unit create(int id) {
        unit result = null;

        return result;
    }

    public void CreateGroupsSet() {
        balls = new PBall[PParametr.ballnum];
        CalcSystemM s = (CalcSystemM) modcalc.getCurrent().getNext();
        for (int i = 0; i < PParametr.ballnum; i++) {

            int xc = 3 + random.nextInt(PParametr.w - 4);
            int yc = 3 + random.nextInt(PParametr.h - 4);
            balls[i] = new PBall(s, this);

            for (int j = 0; j < PParametr.partball; j++) {
                balls[i].AddUnit(j, xc, yc);
            }
            s = (CalcSystemM) s.getNext();
        }
        /* ISelection selection = units.select();
        while (selection.getNext()) {
        UnitPoint unit = (UnitPoint) selection.getCurrent();
        IArgument[] arg = new IArgument[2];
        arg[0] = new ArgumentInt(unit.getIndex());
        arg[1] = new UnitProjectionInfo(unit.getPosition());
        getViewSource().CreateImage(arg);
        }*/

        new GameUnitGroup() {

            public IGameUnitGroup SetSpeedEts() {
                for (int i = 0; i < PParametr.ballnum; i++) {
                    Vector2d v = new Vector2d(10 * (1 - 2 * random.nextDouble()) / getRatio().getMoveperiod(), 10 * (1 - 2 * random.nextDouble()) / getRatio().getMoveperiod());
                    balls[i].GetWalker().getSpeed().Add(v);
                    balls[i].ms = new MotionMoveWithSpeed((int) (10000 * getRatio().getMoveperiod()), balls[i], getRatio());
                    getMotionsReciver().AddMotions(balls[i].ms);
                    //   balls[i].ms.Refresh();
                }
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
}

class PBall extends GameUnitNotActor {

    public final int[] ux = {1, 1, 1, 0, -1, -1, -1, 0, 0};
    public final int[] uy = {1, 0, -1, -1, -1, 0, 1, 1, 0};
    MotionMoveWithSpeed ms;

    public PBall(ITasksReciver motionsReciver, IGame game_) {
        super("Ball", motionsReciver, CollisionsMap.OneRealLevel, 255, 0, 0, game_, game_.getRatio());
    }

    public void ResolveCollisionWith(IBodie unit_, double x, double y) {
        if (this.getItem() != unit_) {
            GetWalker().Stand();
            ((GameUnit) unit_).GetWalker().Stand();
            IVector2d v1 = (IVector2d) GetWalker().getSpeed();
            IVector2d v2 = (IVector2d) ((GameUnit) unit_).GetWalker().getSpeed();

            Coordinates2d p1 = (Coordinates2d) asPoint().getPosition();
            Coordinates2d p2 = (Coordinates2d) ((GameUnit) unit_).asPoint().getPosition();
            Vector2d d = new Vector2d(p2.x - p1.x, p2.y - p1.y);


            d.Norm();
            double GetX = v2.getX();
            double GetY = v1.getY();
            d.ScalMull((GetX * d.x + v2.getY() * d.y) - (v1.getX() * d.x + GetY * d.y));

            v1.Add(d);
            v2.Sub(d);


        }
    }

    public void ResolveCollisionOverScope(boolean inGorscope, boolean inVertscope) {
        GetWalker().Stand();
        //ms.Refresh();

        if (!inGorscope) {
            ((Vector2d) GetWalker().getSpeed()).multX(-1);
        }

        if (!inVertscope) {
            ((Vector2d) GetWalker().getSpeed()).multY(-1);
        }

    }

    public void AddUnit(int j, int xc, int yc) {
        super.PreMoveUnit(xc + ux[j], yc + uy[j]);
        //   unit_.Set_();

    }

    public states getViewState() {
        return states.zero;
    }

    public void PreChangeUnitPosition(GameUnit unit_) {
    }

    public boolean FallAble() {
        return true;
    }
}
