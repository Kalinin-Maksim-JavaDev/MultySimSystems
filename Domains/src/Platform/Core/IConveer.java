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
public interface IConveer {

    public void RegBeg(String trname, String sysName, String log_);

    public void RegEnd(String trname, String sysName, String log_);

    public void Start();

    public void Stop();

    public void Pause();

    public void Resume();

    public boolean PauseResume();

    public void incTimerCounter();

    public int GetIteration();

    public ISystemM GetTimer();

    void setDis(ISystemM sys);


    ISystemM getDis();

    void setMod(ISystemM sys);

    void setMod(ISystemMContainer sys);

    ISystemM getMod();

    IMonitor getMonitor();

    IConveer GetInner();



}
