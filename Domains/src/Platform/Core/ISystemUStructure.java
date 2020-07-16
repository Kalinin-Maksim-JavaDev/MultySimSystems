/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Platform.Core;

import Platform.Core.Unit.IUnit;
import Platform.DataStructers.ILinkedList;

/**
 *
 * @author kalinin
 */
public interface ISystemUStructure {

    /**
     * @return the units
     */
    ILinkedList getUnits();

    /**
     * @return the unitsNumber
     */

    void setUnits(ILinkedList units);

    void addUnit(int index, IUnit unit);

}
