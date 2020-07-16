/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Core.Systems;

import Application.ILoger;
import Platform.Concurrent.IMonitor;
import Platform.Core.ITaskMotions;
import Platform.Core.IConveer;
import Platform.Core.IExecuter;

/**
 *
 * @author kalinin
 */
public class SystemMStructure {

    protected String sysName;
    protected String trname;
    protected IMonitor monitor;
    protected IConveer owner;
    protected ITaskMotions taskMotions;
    protected IExecuter[] commandExecuters;

    public SystemMStructure(String sysName) {
        this.sysName = sysName;
    }
}
