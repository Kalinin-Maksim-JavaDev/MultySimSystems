/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.Game.Presenter.Projections;

import Platform.Core.IArgument;
import Platform.DataStructers.ILinkedElement;
import Render.Graphics.Geometry.ICoordinates;

/**
 *
 * @author Adm
 */
public class UnitProjectionInfo implements IArgument,ILinkedElement {

    public ICoordinates position;

    ILinkedElement next;

    public UnitProjectionInfo(ICoordinates position_) {
        position = position_;
    }

    public ILinkedElement getNext() {
        return next;
    }

    public void setNext(ILinkedElement e) {
        this.next = e;
    }


}
