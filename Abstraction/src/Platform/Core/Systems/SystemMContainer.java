/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Core.Systems;

import Platform.Core.ISystemM;
import Platform.Core.ISystemMContainer;

/**
 *
 * @author kalinin
 */
public abstract class SystemMContainer implements ISystemMContainer {

    protected ISystemM sys;

    public SystemMContainer() {
    }

    public ISystemM getSys() {
        return sys;
    }
}
