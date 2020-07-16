package Platform.Core.Hidden;

import Platform.Concurrent.IMonitor;
import Platform.Core.IMotionReciver;
import Platform.Core.Systems.SystemM;
import Platform.Core.IConveer;
import Platform.Core.ISystemM;
import Platform.Core.ISystemMContainer;
import Platform.Core.Motion.Motion;
import Platform.Core.Motion.MotionFabric;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalinin
 */
public class ConveerTrue extends ConveerTrueStructure implements IConveer {

    final SystemM loger;
    final MotionFabric motionDisREPEATMOD;
    final MotionFabric motionModREPEAT;
    final IMonitor monitor;

    public IConveer GetInner() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ConveerTrue(boolean oneThread, int artquant) {

        //  quant = quant_;
        this.oneThread = oneThread;
        this.artquant = (int) (0.5 * artquant);

        loger = new SystemMLoger();

        timer = new MainTimer(this);
//        motionModENDITER = new MotionFactoryModENDITER(1, this);



        motionDisREPEATMOD = new MotionFactoryDisREPEATMOD(1, this);

        motionModREPEAT = new MotionFactoryModREPEAT(1, this);
//        motionStart = new MotionStart(this);
        monitor = new MonitorConveer(this);
        //  itr = new Iteration();

    }

    public IMonitor getMonitor() {
        return monitor;
    }

    public void Start() {

        mod.setOwner(this);
        dis.setOwner(this);


        ((SystemM) dis).setMonitor(monitor);

        //        art.conveerInterafe = new ConveerInterfaceWhithLogerArt(art);

        if (oneThread) {
            ((SystemM) dis).SetTaskMotion((mod).getTaskMotions());
            ((SystemM) dis).Start();
            //   art.motionSequence = mod.motionSequence;
        } else {
            //      art.Start();
            //    java.lang.System.out.print(" " + Thread.currentThread().getName() + ":" + currentTimeMillis() + " " + "art start" + "\n");
            ((SystemM) dis).Start();
            //     java.lang.System.out.print(" " + Thread.currentThread().getName() + ":" + currentTimeMillis() + " " + "dis start" + "\n");
        }

        timer.Start();

        mod.Start();

        mod.delligate(timer, new MotionRun("Run", (IConveer) this));


//        mod.taskMotions.Resume();
        //  java.lang.System.out.print(" " + Thread.currentThread().getName() + ":" + currentTimeMillis() + " " + "mod start" + "\n");


        loger.Start();

    }

//    public void Start(StartStopPauseSystem con_) {
//
//        itr = ((Conveer) con_).itr;
//        mod.conveerInterafe = new ConveerInterfaceWhithLogerMod(((Conveer) con_).dis, dis, disMainCode, (Conveer) con_);
//        dis.conveerInterafe = new ConveerInterfaceWhithLogerDis((Conveer) con_);
//        // art.conveerInterafe = null;
//
//        mod.motionSequence = ((Conveer) con_).mod.motionSequence;
//        dis.motionSequence = ((Conveer) con_).dis.motionSequence;
//        //   art.motionSequence = ((Conveer) con_).art.motionSequence;
//
//        mod.Start(((Conveer) con_).mod);
//        //  art.Start(((Conveer)con_).art);
//        dis.Start(((Conveer) con_).dis);
//
//        timer = ((Conveer) con_).timer;
//    }
    //public abstract void AfterModRun();
    public void Stop() {
        // mod.AddMotions(new MotionStop(this));
        //  mod.Resume();
        GetTimer().stop = true;

    }

    public boolean PauseResume() {
        return GetTimer().PauseResume();
    }

    public void Pause() {
        GetTimer().AddPause();
    }

    public void Resume() {
        GetTimer().AddResume();
    }

    public boolean UserSeeActualData() {
        return m == t;
    }

    void IncM() {
        m++;
        if (m > lim) {
            m = 0;
        }
    }

    public void incTimerCounter() {
        t++;
        if (t > lim) {
            t = 0;
        }
    }

    boolean LastItterationComplite() {
        return m == t;
    }

    public void Say(String msg) {
    }

    public int GetIteration() {
        return m;
    }

    void RegBeg_(long time_, String tr_, String name_, String log_) {
    }

    void RegEnd_(long time_, String tr_, String name_, String log_) {
    }

    public void RegBeg(String tr_, String name_, String log_) {
        //  conveer.loger.motionSequence.AddMotions(new MotionLogBeg(conveer.itr.m, "RegBeg_", tr_, name_, log_));
        //  conveer.loger.taskMotions.Resume();
    }

    public void RegEnd(String tr_, String name_, String log_) {
        //  conveer.loger.motionSequence.AddMotions(new MotionLogEnd(conveer.itr.m, "EndBeg_", tr_, name_, log_));
        // conveer.loger.taskMotions.Resume();
    }

    public MainTimer GetTimer() {
        return (MainTimer) timer;
    }

    /**
     * @return the dis
     */
    public ISystemM getDis() {
        return dis.getSelf();
    }

    /**
     * @param proxy the dis to set
     */
    public void setDis(ISystemMContainer proxy) {
        this.dis = proxy.getSys();
    }

    public void setDis(ISystemM dis) {
        this.dis = dis;
    }

    /**
     * @return the mod
     */
    public ISystemM getMod() {
        return mod;
    }

    /**
     * @param mod the mod to set
     */
    public void setMod(ISystemMContainer proxy) {
        this.mod = proxy.getSys();
    }

    public void setMod(ISystemM mod) {
        this.mod = mod;
    }

    public void setMod(IMotionReciver reciver) {
        this.mod = (ISystemM) reciver;
    }

    /**
     * @return the timer
     */
    public ISystemM getTimer() {
        return timer;
    }

    /**
     * @param timer the timer to set
     */
    public void setTimer(ISystemM timer) {
        this.timer = timer;
    }
}

class MotionFactoryRepeat extends MotionFabric {

    ISystemM sys;

    public MotionFactoryRepeat(int iterates_, ISystemM sys) {
        super(iterates_);
        this.sys = sys;
    }

    public Motion NewMotion(int iterates_) {
        return new Motion(1, "Repeat") {

            @Override
            public void MotionMethod() {
                sys.Repeat(true);
            }
        };
    }
}

class MotionFactoryDisREPEATMOD extends MotionFabric {

    private ConveerTrue conveer;

    public MotionFactoryDisREPEATMOD(int iterates_, ConveerTrue conveerTrue) {
        super(iterates_);
        this.conveer = conveerTrue;
    }

    public Motion NewMotion(int iterates_) {
        return new Motion(1, "REPEAT") {

            @Override
            public void MotionMethod() {
                ISystemM dis = conveer.dis;
                ISystemM mod = conveer.mod;
                conveer.RegBeg(dis.getTrname(), dis.getSysName(), " beg mod repeat ");

                //       java.lang.System.out.print(dis.iterationCounter + Thread.currentThread().getName() + ":" + currentTimeMillis() + " " + dis.sysName + " add repeat" + "\n");
                mod.AddMotions(conveer.motionModREPEAT.GetMotion(1));
                /*2*/
                mod.Resume();


                //         java.lang.System.out.print(dis.iterationCounter + Thread.currentThread().getName() + ":" + currentTimeMillis() + " " + dis.sysName + " add render" + "\n");

                //            art.motionSequence.AddMotions(art.commandExecuters[RenderMainCode].CreateMotion(null));
                //            /*3*/
                //            ArttaskMotionsResume();
                conveer.RegEnd(dis.getTrname(), dis.getSysName(), " beg mod repeat ");
            }
        };
    }
}

class MotionFactoryModREPEAT extends MotionFabric {

    private ConveerTrue conveer;

    public MotionFactoryModREPEAT(int iterates_, ConveerTrue conveerTrue) {
        super(iterates_);
        this.conveer = conveerTrue;
    }

    public Motion NewMotion(int iterates_) {
        return new Motion(iterates_, "REPEAT") {

            @Override
            public void MotionMethod() {
                SystemM mod = (SystemM) conveer.mod;

                conveer.RegBeg(mod.getTrname(), mod.getSysName(), " repeat ");
                mod.Repeat(true);
                //  System.out.println("Mod repeate");
                conveer.RegEnd(mod.getTrname(), mod.getSysName(), " repeat ");

                // java.lang.System.out.print(mod.iterationCounter + Thread.currentThread().getName() + ":" + currentTimeMillis() + " " + mod.sysName + " repeat" + "\n");
            }
        };
    }
}

//class MotionFactoryModENDITER extends InnerMotionFactory {
//
//    public MotionFactoryModENDITER(int iterates_, ISystemM sys) {
//        super(iterates_, owner_);
//    }
//
//    public Motion NewMotion(int iterates_) {
//        return new MotionModENDITER(1, "ENDITER", owner);
//    }
//}
//class MotionModENDITER extends InnerMotion {
//
//    public MotionModENDITER(int iterates_, String motionName_, ISystemM sys) {
//        super(iterates_, motionName_, owner_);
//    }
//
//    public void MotionMethod() {
//        Conveer conveer = (Conveer) owner;
//        USystem dis = conveer.dis;
//        USystem mod = conveer.mod;
//        mod.conveerInterafe.RegBeg(mod.trname, mod.sysName, " ModENDITER ");
//
//
//
//        dis.motionSequence.AddMotions(conveer.motionDisREPEATMOD.GetMotion(1));
//
//        //         java.lang.System.out.print(mod.iterationCounter + Thread.currentThread().getName() + ":" + currentTimeMillis() + " " + mod.sysName + " add to dis fork" + "\n");
//		/*1*/
//        dis.taskMotions.Resume();
//        // DistaskMotionsResume();
//        mod.conveerInterafe.RegEnd(mod.trname, mod.sysName, " ModENDITER ");
//        owner = null;
//    }
//}
class MotionPauseResume extends Motion {

    private ConveerTrue conveer;

    public MotionPauseResume(int iterates_, String motionName_, ConveerTrue conveer) {
        super(iterates_, motionName_);
        this.conveer = conveer;
    }

    public void MotionMethod() {

        conveer.GetTimer().autorepeat = !conveer.GetTimer().autorepeat;
        conveer.timer.Repeat(true);
        //System.out.println(Thread.currentThread().getName()+"conveer.timer.Repeat");
    }
}

class MotionResume extends Motion {

    private ConveerTrue conveer;

    public MotionResume(int iterates_, String motionName_, ConveerTrue conveer) {
        super(iterates_, motionName_);
        this.conveer = conveer;
    }

    public void MotionMethod() {


        if (conveer.GetTimer().autorepeat) {
            System.out.println("!!!!!!!!!" + conveer.GetTimer().getTaskMotions().getName() + " allready Resumed");
            // Util.Tools.UnsupportedOperationException();
        }
        conveer.GetTimer().autorepeat = true;
        conveer.timer.Repeat(true);
        //System.out.println(Thread.currentThread().getName()+"conveer.timer.Repeat");
    }
}

class MotionPause extends Motion {

    private ConveerTrue conveer;

    public MotionPause(int iterates_, String motionName_, ConveerTrue conveer) {
        super(iterates_, motionName_);
        this.conveer = conveer;
    }

    public void MotionMethod() {

        if (!conveer.GetTimer().autorepeat) {
            System.out.println("!!!!!!!!!" + conveer.GetTimer().getTaskMotions().getName() + " allready paused");
            // Util.Tools.UnsupportedOperationException();
        }
        conveer.GetTimer().autorepeat = false;
        conveer.timer.Repeat(true);
        //System.out.println(Thread.currentThread().getName()+"conveer.timer.Repeat");
    }
}

//
//class MotionStart extends InnerMotion {
//
//    public MotionStart(ISystemM sys) {
//        super(1, "Start", owner_);
//    }
//
//    public void MotionMethod() {
//
//       // ((Conveer) owner).timer.Run();
//        //    timer2.schedule(new TimerTask2(), 0, 50);
//        ((Conveer) owner).AfterModRun();
//        owner = null;
//    }
//}
//class MotionSetCancel extends InnerMotion {
//
//    public MotionSetCancel(int iterates_, String motionName_, ISystemM sys) {
//        super(iterates_, motionName_, owner_);
//    }
//
//    public void MotionMethod() {
//        Conveer conveer = (Conveer) owner;
//        conveer.dis.Stop();
//        conveer.mod.Stop();
//        conveer.loger.Stop();
//        //     ArttaskMotionsResume();
//        //     DistaskMotionsResume();
////        conveer.dis.taskMotions.Resume();
////        conveer.mod.taskMotions.Resume();
////        conveer.loger.taskMotions.Resume();
//        //  conveer.timer.motionRun.owner = null;
//        conveer.timer.Stop();
//        //conveer.timer = null;
//        // timer2.cancel();
//        //  timer = null;
//        //   timer2 = null;
//
//        //   owner = null;
//
//    }
//}
//class MotionStop extends InnerMotion {
//
//    public MotionStop(ISystemM sys) {
//        super(1, "Stop", owner_);
//    }
//
//    public void MotionMethod() {
//        //            timer2.cancel();
//        ((Conveer) owner).timer.SetCancel();
//        owner = null;
//    }
//}
class SystemMLoger extends SystemM {

    public SystemMLoger() {
        super("L\t|");
    }

    public String SetThreadName() {
        return "Loger";
    }

    public void setOwner(IConveer owner) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

//class MotionStop extends InnerMotion {
//
//    public MotionStop(ISystemM sys) {
//        super(1, "Stop", owner_);
//    }
//
//    public void MotionMethod() {
//        //            timer2.cancel();
//        ((Conveer) owner).timer.SetCancel();
//        owner = null;
//    }
//}
class MotionLogEnd extends MotionLogBeg {

    String node = "End";

    public MotionLogEnd(int iterationCounter_, String motionName_, long time_, String tr_, String name_, String log_) {
        super(iterationCounter_, motionName_, time_, tr_, name_, log_);
    }

    public void MotionMethod() {
        java.lang.System.out.println(iterationCounter + tr + ":" + time + name + node + log + " " + iterationCounter);
    }
}

class MotionLogBeg extends Motion {

    int iterationCounter;
    long time;
    String tr;
    String name;
    String log;
    String node = "Beg";

    public MotionLogBeg(int iterationCounter_, String motionName_, long time_, String tr_, String name_, String log_) {
        super(1, motionName_);
        iterationCounter = iterationCounter_;
        time = time_;
        tr = tr_;
        name = name_;
        log = log_;

    }

    public void MotionMethod() {
        java.lang.System.out.println(iterationCounter + tr + ":" + time + name + node + log + " " + iterationCounter);
    }
}
