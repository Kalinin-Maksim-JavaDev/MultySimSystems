/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter.Graphics.Space.G3D;

import Model.Game.Presenter.Space.CommonProjectionInfo;
import Model.Models.Unit.D3.Coordinates3d;
import Render.Graphics.Geometry.ICoordinates;



/**
 *
 * @author Adm
 */
public abstract class CommonProjectionInfo3d extends CommonProjectionInfo {

    public int renderedObject;
    public ICoordinates[] viewVector={new Coordinates3d(),new Coordinates3d()};
    public ICoordinates[] lightVector={new Coordinates3d(),new Coordinates3d()};
}
