/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Game.World.Unit;

import Game.Model.Game.IRatio;
import Logic.Model.Game.IGame;
import Logic.Model.Game.Unit.IUnit2DMoveble;
import Logic.Model.Game.World.Collisions.IBodie;
import Logic.Model.Game.World.Exployer.Target.ITarget;
import Logic.Model.Game.World.IWalker;
import Logic.Model.Game.World.Interaction.Resolvers.ICollisionResolver;
import Logic.Model.Game.World.Unit.IBalisticsStrategics;
import Logic.Reflections.Projections.IImaginator;
import Model.Game.World.Unit.Behaviors.Physics.BalisticsStrategics;
import Model.Game.World.Unit.Command.MotionTeleport;
import Model.Geometry.D2.Coordinates2d;
import Model.Models.Unit.D2.Vector2d;
import Platform.Calculation.ITasksReciver;
import Platform.Concurrent.Factory;
import Platform.Concurrent.ILathce;
import Platform.Core.IMotion;
import Platform.Core.IMotionsProducer;
import Platform.Core.Unit.IUnit;
import Platform.Core.Unit.IUnit.drawable;
import Platform.Core.Unit.Unit;
import Platform.Util.Profiler.observercode.Diagram.DataBlock;
import Render.Graphics.Geometry.D2.IVector2d;
import Render.Graphics.Geometry.ICoordinates;
import Render.Graphics.Geometry.IPointed;
import Render.Graphics.Geometry.IVectorD;
import View.Presenter.Projections.IViewUnit;
import java.awt.Color;

/**
 *
 * @author Adm
 */
public abstract class GameUnit extends Unit implements IGame.unit, IViewUnit, IViewUnit.imaginated, IUnit2DMoveble, IUnit2DMoveble.bodie2D, IUnit2DMoveble.asWalker {

    protected abstract ICollisionResolver createCollisionResolver();

    protected abstract boolean FallAble();
    public int realLevel;
    public ITasksReciver calcMotionsReciver;
    public IGame game;
    public IRatio ratio;
    public DataBlock data;
    //public boolean liquid;
    //public double vx;
    // public double vy;
    final private double c0999 = 0.9;
    final double[] partX = {0, c0999, c0999, 0};
    final double[] partY = {0, 0, c0999, c0999};
    final public boolean[][] map;
    private final Color color;
    final String[] values = {"jumpSpeed"};
    public int jumpSpeed = 0;
    public ITarget placeAchive;
    public IBodie prevToCheck;
    public boolean isAddToCheck;
    //public boolean onGrav;
    //public MotionProducer fair;
    
    private ICollisionResolver resolver;
    public volatile boolean selected;
    public IBalisticsStrategics balisticsStrategy;
    public Vector2d speed;
    public ILathce onWay;
    private boolean visible;
    private IImaginator.charged imaginator;

    public GameUnit(String name_, ITasksReciver motionReciver, int realLevel_, int r, int g, int b, IGame game_, IRatio ratio) {

        this.calcMotionsReciver = motionReciver;
        this.game = game_;
        this.realLevel = realLevel_;
        this.ratio = ratio;

        balisticsStrategy = new BalisticsStrategics(data, ratio.getDt());
        speed = new Vector2d(0, 0);
        onWay = Factory.createLathce(false, false);


        data = new DataBlock(values);

        color = new Color(r, g, b);
        data.Set(jumpSpeed, new Vector2d(0, 5 * ratio.getGrad() / ratio.getMoveperiod()));
        map = new boolean[ratio.getGrad()][ratio.getGrad()];
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[j].length; i++) {
                map[j][i] = true;
            }
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public IMotion CreateMotionTeleport(int x_, int y_) {
        return new MotionTeleport(x_, y_, this);
    }

    public Vector2d getSpeed2d() {
        return (Vector2d) asWalker().getSpeed();
    }

    public String getUnitName() {
        return getName();
    }

    public IPointed asPoint() {
        if (position == null) {

            position = new Coordinates2d();
        }

        return this;
    }

    public bodie2D as2Dbodie() {
        return this;
    }

    public asWalker asWalker() {
        return this;
    }

    public drawable asDrawble() {
        return this;
    }

    public IUnit asUnit() {
        return this;
    }

    public IMotionsProducer getMotionsProducer() {
        return this;
    }

    public IWalker GetWalker() {
        return asWalker();
    }

    public IBodie getBodie() {
        return this;
    }

    public IViewUnit.imaginated setImaginator(IImaginator.charged imaginator) {
        this.imaginator = imaginator;
        return this;
    }

    public IImaginator.charged getImaginator() {
        return imaginator;
    }

    public imaginated asImaginated() {
        return this;
    }

    public void setPosition(double x_, double y_) {
        if (position == null) {
            position = new Coordinates2d();
        }
        ((Coordinates2d) position).x = (x_);
        ((Coordinates2d) position).y = (y_);
    }

    public ICollisionResolver getCollisionResolver() {

        if (resolver == null) {
            resolver = createCollisionResolver();
        }
        return resolver;
    }

    public IBalisticsStrategics getBalisticsStrategy() {
        return balisticsStrategy;
    }

    public IVector2d GetSpeed() {
        return (IVector2d) speed;
    }

    public void PreMoveUnit(int x, int y) {
        game.getCollisionMap().PreMoveUnit(as2Dbodie(), x, y);
    }

    public void PreChangeUnitPosition() {
        IBodie gr_ = null;
        if (speed.getX() > 0) {
            gr_ = game.getCollisionMap().getDot((int) ((Coordinates2d) getPosition()).x, (int) ((Coordinates2d) getPosition()).y + ratio.getGrad());
        } else {
            gr_ = game.getCollisionMap().getDot((int) ((Coordinates2d) getPosition()).x + ratio.getGrad() - 1, (int) ((Coordinates2d) getPosition()).y + ratio.getGrad());
        }
        if (gr_ != null) {
            if (((GameUnit) gr_).FallAble()) {
                balisticsStrategy.SetFreeFall();
            }
        }
    }

    public void PostChangeUnitPosition() {
        Coordinates2d pos_ = (Coordinates2d) getPosition();
        IBodie ownerCell_ = null;
        boolean setfallmode = true;
        boolean brk = false;
        for (int dy = 0; dy < map.length; dy++) {
            for (int dx = 0; dx < map[0].length; dx++) {
                if (map[dy][dx]) {
                    int dxi = (int) (pos_.xd + dx);
                    int dyi = (int) (pos_.yd + dy);
                    ownerCell_ = game.getCollisionMap().preCompliteUnitsMap(realLevel)[dyi][dxi];
                    if ((ownerCell_ != null) && (ownerCell_ != this)) {
                        setfallmode = false;
                        brk = true;
                        break;
                    }
                }
            }
            if (brk) {
                break;
            }
        }
        if (setfallmode) {
            balisticsStrategy.SetFreeFall(); //включить падение 
        }
        if (placeAchive != null) {
            placeAchive.CheckAchieved();
        }
    }

    public Color GetColor() {
        return color;
    }

    public boolean Arrive(int x, int y) {
        boolean resX = ArriveX(x);
        boolean resY = ArriveY(y);
        return resX && resY;
    }

    public boolean ArriveX(int x) {
        return x == GetIntX();
    }

    public boolean ArriveY(int y) {
        return y == GetIntY();
    }

    public boolean ArriveX(int x, boolean fromleft) {
        if (fromleft) {
            return x == (int) (((Coordinates2d) getPosition()).x / ratio.getGrad());
        } else {
            return x == (int) ((((Coordinates2d) getPosition()).x + ratio.getGrad() - 1) / ratio.getGrad());
        }
    }

    public boolean ArriveY(int y, boolean fromeAbove) {
        if (fromeAbove) {
            return y == (int) (((Coordinates2d) getPosition()).y / ratio.getGrad());
        } else {
            return y == (int) ((((Coordinates2d) getPosition()).y + ratio.getGrad() - 1) / ratio.getGrad());
        }
    }

    public int GetIntX() {
        return (int) ((((Coordinates2d) getPosition()).x + ratio.getGrad() / 2) / ratio.getGrad());
    }

    public int GetIntY() {
        return (int) ((((Coordinates2d) getPosition()).y + ratio.getGrad() / 2) / ratio.getGrad());
    }

    public void Stand() {
        ((Coordinates2d) position).xd = (((Coordinates2d) getPosition()).x);
        ((Coordinates2d) position).yd = (((Coordinates2d) getPosition()).y);
    }

    public void StandX() {
        ((Coordinates2d) position).xd = (((Coordinates2d) getPosition()).x);
    }

    public void StandY() {
        ((Coordinates2d) position).yd = (((Coordinates2d) getPosition()).y);
    }

    public void SetTarget(ITarget target) {
        placeAchive = target;
    }

    public ITarget GetTarget() {
        return placeAchive;
    }

    public IVectorD getSpeed() {
        return speed;
    }

    public int getJumpSpeed() {
        return jumpSpeed;
    }

    public void stop() {
        Vector2d speed = (Vector2d) getSpeed();
        speed.setX(0);
    }

    public boolean fromeAbove() {
        return ((Vector2d) getSpeed()).getX() > 0;
    }

    public void TurnBack() {
        Vector2d speed = (Vector2d) getSpeed();
        speed.setX(-speed.getX());
    }

    public void setSelected(boolean value) {
        selected = value;
    }

    public ILathce onWay() {
        return onWay;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected() {
        selected = true;
    }

    public void setUnselected() {
        selected = false;
    }

    public void addToCheck() {
        isAddToCheck = true;
    }

    public int getLevel() {
        return realLevel;
    }

    public boolean isAddToCheck() {
        return isAddToCheck;
    }

    public void removeFromCheck() {
        isAddToCheck = false;
    }

    public boolean[][] getMap() {
        return map;
    }

    public IUnit getUnit() {
        return this;
    }

    public void ResolveCollisionOverScope(boolean inGorscope, boolean inVertscope) {
        GameUnit.this.ResolveCollisionOverScope(inGorscope, inVertscope);
    }

    public void ResolveCollisionWith(IBodie boide, double xd, double yd) {
        GameUnit.this.ResolveCollisionWith(boide, xd, yd);
    }

    public IBodie getPrevToCheck() {

        return prevToCheck;
    }

    public void setPositionFromD(ICoordinates position) {

        Coordinates2d pos2d = (Coordinates2d) position;
        setPosition(pos2d.xd, pos2d.yd);
    }

    public void setPrevToCheck(IBodie bodie) {
        prevToCheck = bodie;
    }
}
