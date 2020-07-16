package Platform.Core.Conveer;

import Platform.Concurrent.IMonitor;
import Platform.Core.IConveer;
import Platform.Core.ISystemM;
import Platform.Core.Hidden.ConveerTrue;
import Platform.Core.ISystemMContainer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalinin
 */
public class Conveer extends ConveerStructure implements IConveer {

    public Conveer(boolean oneThread_, int artquant_) {
        inner = new ConveerTrue(oneThread_, artquant_) {

            public void Say(String msg) {
                Conveer.this.Say(msg);
            }
        };
    }

    public IConveer GetInner() {
        return inner;
    }

    public void Say(String msg) {
    }

    public int GetIteration() {
        return inner.GetIteration();
    }

    public void setDis(ISystemM sys) {
        inner.setDis(sys);
    }


    public ISystemM getDis() {
        return inner.getDis();
    }

    public void Start() {
        inner.Start();
    }

    public void Stop() {
        inner.Stop();
    }

    public void setMod(ISystemM sys) {
        inner.setMod(sys);
    }

    public void setMod(ISystemMContainer sys) {
        inner.setMod(sys.getSys());
    }

    public ISystemM getMod() {
        return inner.getMod();
    }

    public boolean PauseResume() {
        return inner.PauseResume();
    }

    public void Pause() {
        inner.Pause();
    }

    public void Resume() {
        inner.Resume();
    }

    public void RegBeg(String tr_, String name_, String log_) {
        inner.RegBeg(tr_, name_, log_);
    }

    public void RegEnd(String tr_, String name_, String log_) {
        inner.RegEnd(tr_, name_, log_);
    }

    public void incTimerCounter() {
        inner.incTimerCounter();
    }

    public ISystemM GetTimer() {
        return inner.GetTimer();
    }

    public IMonitor getMonitor() {
        return inner.getMonitor();
    }
}
