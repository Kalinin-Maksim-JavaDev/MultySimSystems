/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Core;

import Platform.Concurrent.IMonitor;

/**
 *
 * @author kalinin
 */
public interface ISystemM extends IMotionReciver {

    void Stop();

    void Start();


    void Repeat(boolean b);

    void setOwner(IConveer owner);

    void setMonitor(IMonitor monitor);

    IExecuter[] getCommandExecuters();

    IMonitor getMonitor();

    IConveer getOwner();

    String getSysName();

    ITaskMotions getTaskMotions();

    String getTrname();

    void setCommandExecuters(IExecuter[] commandExecuters);

    void setSysName(String sysName);

    void setTaskMotions(ITaskMotions taskMotions);

    void setTrname(String trname);

    public void setCommandExecuter(int id, IExecuter executer);

    IExecuter getCommandExecuter(int id);

    public ISystemM getSelf();

    public String SetThreadName();

    public void delligate(ISystemM system, IMotion motion);
}
