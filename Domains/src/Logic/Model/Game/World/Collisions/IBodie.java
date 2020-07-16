/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Model.Game.World.Collisions;

import Application.DEBUG.IColoredPoint;
import Logic.Model.Game.World.Interaction.Resolvers.ICollisionResolver;
import Platform.Core.Unit.IUnit;
import Render.Graphics.Geometry.ICoordinates;

/**
 *
 * @author Dkx6r0c
 */
public interface IBodie extends IColoredPoint {

    ICollisionResolver getCollisionResolver();

    ICoordinates getPosition();

    void addToCheck();

    boolean isAddToCheck();

    void setPrevToCheck(IBodie bodie);

    IBodie getPrevToCheck();

    IUnit getUnit();

    public int getLevel();

    public boolean[][] getMap();

    public void ResolveCollisionWith(IBodie boide, double xd, double yd);

    public void ResolveCollisionOverScope(boolean inGorscope, boolean inVertscope);

    public void setPositionFromD(ICoordinates position);

    public void PreChangeUnitPosition();

    public void PostChangeUnitPosition();

    public String getUnitName();

    public void removeFromCheck();
}
