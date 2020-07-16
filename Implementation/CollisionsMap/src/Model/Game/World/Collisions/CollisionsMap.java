/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Game.World.Collisions;

import Game.Model.Game.IRatio;
import Global.Tools;
import Game.Model.Game.World.Interaction.ICollisionMap;
import Model.Geometry.D2.Coordinates2d;
import Logic.Model.Game.World.Collisions.IBodie;
import Logic.Model.Game.World.IWalker;
import Logic.Model.Game.World.Interaction.Resolvers.ICollisionResolver;
import Platform.Concurrent.IAtomicReferenceObject;
import Platform.Concurrent.Factory;
import Render.Graphics.Geometry.D2.IVector2d;
import Render.Graphics.Geometry.ICoordinates;

/**
 *
 * @author Adm
 */
public class CollisionsMap implements ICollisionMap {

    static final public int OneRealLevel = 0;
    static final public int TwoRealLevel = 1;
    final public IBodie[][][] preCompliteUnitsMap;
    final public int w;
    final public int h;
    private final IAtomicReferenceObject currentToCheck;
    final IRatio ratio;

    public CollisionsMap(int w, int h, IRatio game_) {
        this.w = w;
        this.h = h;
        ratio = game_;
        preCompliteUnitsMap = new IBodie[2][h][2 * w];
        currentToCheck = Factory.newAtomicReferenceObject();
        currentToCheck.set(null);
    }

    public void PreMoveUnit(IBodie bodie, double xnew, double ynew) {

        ((Coordinates2d) bodie.getPosition()).xd = (xnew);
        ((Coordinates2d) bodie.getPosition()).yd = (ynew);
        if (!bodie.isAddToCheck()) {

            for (;;) {
                bodie.setPrevToCheck((IBodie) currentToCheck.get());
                if (currentToCheck.compareAndSet(bodie.getPrevToCheck(), bodie)) {
                    break;
                }
            }
            bodie.addToCheck();
        }

    }

    public IBodie[] CheckVectorsCollisionAndCompliteUnitsMove(int size) {

        IBodie[] _result = new IBodie[size];
        int resultCuonter = 0;

        IBodie c_ = (IBodie) currentToCheck.get();
        while (c_ != null) {
            CheckVectorCollisionAndResolve(c_);
            _result[resultCuonter++] = c_;
            c_ = c_.getPrevToCheck();
        }

        c_ = (IBodie) currentToCheck.get();
        while (c_ != null) {
            CompliteGroupMove(c_);
            c_.removeFromCheck();
            c_ = c_.getPrevToCheck();
        }
        currentToCheck.set(null);

        IBodie[] result = new IBodie[resultCuonter];
        for (int i = 0; i < resultCuonter; i++) {
            result[i] = _result[i];
        }
        return result;
    }

    public void CheckVectorCollisionAndResolve(IBodie bodie) {
        int xd = 0;
        int yd = 0;
        boolean inGorscope = true;
        boolean inVertscope = true;

        Coordinates2d pos_ = (Coordinates2d) bodie.getPosition();
        xd = (int) (pos_.xd);
        yd = (int) (pos_.yd);
        inGorscope = inGorscope && (xd >= 0) && (xd < w);
        inVertscope = inVertscope && (yd >= 0) && (yd < h);

        if (((IVector2d) ((IWalker) bodie.getUnit()).getSpeed()).getY() != 0) {
            int u = 1;
        }
        IBodie pt;
        if (inGorscope && inVertscope) {
            ICollisionResolver resolver = null;

            boolean brk = false;
            for (int dy = 0; dy < bodie.getMap().length; dy++) {
                for (int dx = 0; dx < bodie.getMap()[0].length; dx++) {
                    if (bodie.getMap()[dy][dx]) {
                        int dxi = Mathround(pos_.xd + dx);
                        int dyi = Mathround(pos_.yd + dy);
                        pt = preCompliteUnitsMap[bodie.getLevel()][dyi][dxi];
                        if ((pt != null) && (pt != bodie)) {

                            resolver = pt.getCollisionResolver();
                            if (resolver == null) {
                                Tools.UnsupportedOperationException();
                            }

                            if (pt == null) {
                                Tools.UnsupportedOperationException();
                            }

                            bodie.ResolveCollisionWith(pt, pos_.x + dx, pos_.y + dy);
                            brk = true;
                            break;

                        }
                    }
                }
                if (brk) {
                    break;
                }
            }
        } else {
            bodie.ResolveCollisionOverScope(inGorscope, inVertscope);

        }

    }

    public void CompliteGroupMove(IBodie bodie) {
        Coordinates2d pos_ = (Coordinates2d) bodie.getPosition();
        SetPreCompliteUnitsMap(Mathround(pos_.x), Mathround(pos_.y), bodie.getMap(), preCompliteUnitsMap[bodie.getLevel()], null);
        bodie.PreChangeUnitPosition();
        UnitSet(bodie);
        bodie.PostChangeUnitPosition();
    }

    public void UnitSet(IBodie bodie) {
        Coordinates2d pos_ = (Coordinates2d) bodie.getPosition();
        bodie.setPositionFromD((ICoordinates) pos_);
        SetPreCompliteUnitsMap(Mathround(pos_.x), Mathround(pos_.y), bodie.getMap(), preCompliteUnitsMap[bodie.getLevel()], (IBodie) bodie.getUnit());
    }

    public void SetPreCompliteUnitsMap(int x, int y, boolean[][] map, IBodie[][] level, IBodie value) {
        for (int dy = 0; dy < map.length; dy++) {
            for (int dx = 0; dx < map[0].length; dx++) {
                if (map[dy][dx]) {
                    level[(y + dy)][(x + dx)] = value;
                }
            }
        }
    }

    public void CompliteGroupSet(IBodie bodie) {

        UnitSet(bodie);
    }

    public int Mathround(double d) {
        return (int) d;
    }

    public IBodie getDot(int x, int y) {
        if ((x < 0) || (y < 0) || (x >= w) || (y >= h)) {
            return null;
        } else {
            return preCompliteUnitsMap[0][(y)][(x)];
        }
    }

    public int getWeight() {
        return w;
    }

    public int getHight() {
        return h;
    }

    public IBodie[][] preCompliteUnitsMap(int i) {
        return preCompliteUnitsMap[i];
    }

    public IAtomicReferenceObject currentToCheck() {
        return currentToCheck;
    }

    public Object[][] getMap() {
        return preCompliteUnitsMap(0);
    }
}
