/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Collaborations.MVC.Control;

import Platform.Core.IExecuter;
import Platform.Core.Unit.IUnit;
import Platform.DataStructers.ILinkedList;
import Model.Game.Presenter.Projections.ArgumentInt;
import Render.Graphics.Geometry.ICoordinates;

/**
 *
 * @author kalinin
 */
public abstract class MenuStructure {

    public static final ArgumentInt[] zero = {new ArgumentInt(0)};
    public static final ArgumentInt[] one = {new ArgumentInt(1)};
    protected IExecuter[][] executers;
    protected ILinkedList projectionInfo;
    protected ICoordinates[] viewVector;
    
}
