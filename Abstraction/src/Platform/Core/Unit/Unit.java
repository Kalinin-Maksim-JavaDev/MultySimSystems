/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Core.Unit;

import Platform.DataStructers.ILinkedElement;
import Render.Graphics.Geometry.ICoordinates;

/**
 *
 * @author Adm
 */












public abstract class Unit implements ILinkedElement, IUnit, IUnit.drawable, IUnit.drawable.projection {

    protected int typeID;
    protected ICoordinates position;
    private String name;
    private int index;
    private ILinkedElement next;

    public void setIndex(int i) {
        index = i;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public void setName(String UnitName) {
        this.name = UnitName;
    }

    public IUnit setTypeID(int id) {
        this.typeID = id;
        return this;
    }

    public int getTypeID() {
        return typeID;
    }

    public drawable asDrawble() {
        return this;
    }

    public ICoordinates getPosition() {
        return position;
    }

    public void setPosition(ICoordinates position) {
        this.position = position;
    }

    public ILinkedElement getItem() {
        return this.asLinkedElement();
    }

    public ILinkedElement asLinkedElement() {
        return this;
    }

    public ILinkedElement getNext() {
        return next;
    }

    public void setNext(ILinkedElement e) {
        next = e;
    }
}
