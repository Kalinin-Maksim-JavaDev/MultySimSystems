package Platform.Core.Systems;

import Application.ILoger;
import Platform.Core.IConveer;
import Platform.Core.IExecuter;
import Platform.Core.IMotion;
import Platform.Core.ITaskMotions;
import Platform.Concurrent.IMonitor;
import Platform.Core.Hidden.TaskMotions;
import Platform.Core.Motion.Motion;
import Platform.Util.Profiler.GraphVizColor;
import Platform.Util.Profiler.observercode.Diagram.DataBlock;
import Global.Tools;
import Platform.Concurrent.Factory;
import Platform.Concurrent.IAtomicInteger;
import Platform.Core.ISystemM;
import java.util.ArrayList;
import java.util.HashSet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalinin
 */
public abstract class SystemM extends SystemMStructure implements ISystemM, ILoger {

    {
        SystemMCount.incrementAndGet_();
    }
    public final static IAtomicInteger SystemMCount = Factory.newAtomicIntegerS();
    public final static ArrayList<String> SystemMnames = new ArrayList<String>();
    static HashSet<String> threadNames = new HashSet();
    // public Thread tr;
    //  public int iterationCounter = 0;

    public SystemM(String sysName) {
        super(sysName);

        SystemMnames.add(sysName);

        taskMotions = new TaskMotions() {

            public void Say(String msg) {
                SystemM.this.Say(msg);
            }
        };
        //    objectCount = objectCount_;

        // this(objectCount_);



        //    tr = new Thread(taskMotions);

    }

    @Override
    public String toString() {
        return taskMotions.toString();
    }


    public ISystemM getSelf() {
        return this;
    }

    public void Start() {
//        if (taskMotions.blinker != null) {
//            taskMotions.start();
//        }

        trname = SetThreadName();
        while (threadNames.contains(trname)) {

            trname = trname + "t";
        }
        threadNames.add(trname);
        DataBlock.threadRegister.threadColor.put(trname, GraphVizColor.GetColor());

        taskMotions.setName(trname);
        taskMotions.start();
    }

//    public void Start(StartStopPauseSystem s) {
//        Tools.UnsupportedOperationException();
//    }
    public boolean PauseResume() {
        return taskMotions.Getblinker() == null;
    }

    public void Resume() {
        taskMotions.Resume();
    }

    public void Repeat(boolean repeat_) {
        taskMotions.Repeat(repeat_);
    }

    public void Stop() {
        AddMotions(new MotionStop(taskMotions));
        taskMotions.Resume();
    }

    public String GetSec() {
        return "";
    }

    public String GetMotionSec() {
        return taskMotions.GetMotionSec();
    }

    public abstract String SetThreadName();

    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("  " + ":" + Tools.currentTimeMillis() + " " + sysName + "destroy.");
        SystemMCount.decrementAndGet_();
        SystemMnames.remove(sysName);
    }

    public void AddMotions(IMotion motion) {
        taskMotions.AddMotions(motion);
    }

    public void delligate(final ISystemM system, final IMotion motion) {
        AddMotions(new Motion(1, "delligate " + motion.getMotionName() + " to " + system.getSysName()) {

            @Override
            public void MotionMethod() {
                system.AddMotions(motion);
                system.Resume();
            }
        });
    }

    public void SetTaskMotion(ITaskMotions tm) {
        taskMotions.SetMotionSequence(tm.GetMotionSequence());
    }

    public ITaskMotions GetTaskMotion() {
        return taskMotions;
    }

//        public void Repeat() {
//            taskMotions.motionSequence_.Repeat();
//        }
    public void motionSequenceRepeat() {
        taskMotions.motionSequenceRepeat();
    }

    public void Say(String msg) {
    }

    public int GetIteration() {

        return owner.GetIteration();

    }

    public void setOwner(IConveer owner) {
        this.owner = owner;
    }

    public IExecuter[] getCommandExecuters() {
        return commandExecuters;
    }

    public void setCommandExecuters(IExecuter[] commandExecuters) {
        this.commandExecuters = commandExecuters;
    }

    public void setCommandExecuter(int id, IExecuter executer) {
        commandExecuters[id] = executer;
    }

    public IExecuter getCommandExecuter(int id) {
        return commandExecuters[id];
    }

    public IMonitor getMonitor() {
        return monitor;
    }

    public void setMonitor(IMonitor monitor) {
        this.monitor = monitor;
    }

    public IConveer getOwner() {
        return owner;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public ITaskMotions getTaskMotions() {
        return taskMotions;
    }

    public void setTaskMotions(ITaskMotions taskMotions) {
        this.taskMotions = taskMotions;
    }

    public String getTrname() {
        return trname;
    }

    public void setTrname(String trname) {
        this.trname = trname;
    }

    public void RegBeg(String log_) {
        owner.RegBeg(trname, sysName, log_);
    }

    public void RegEnd(String log_) {
        owner.RegBeg(trname, sysName, log_);
    }
}

class MotionStop extends Motion {

    ITaskMotions taskMotions;

    public MotionStop(ITaskMotions taskMotions_) {
        super(1, "Stop");
        taskMotions = taskMotions_;
    }

    public void MotionMethod() {
        taskMotions.SetBlinker(null);
        taskMotions = null;
    }
}
