package View.Render;

import MVC.View.IMVC_View;
import MVC.View.IViewSource;
import Platform.Core.Unit.IUnit.drawable.projection;
import Platform.RenderCounter;
import Platform.Core.IArgument;
import Platform.Core.IMotion;
import Platform.Core.IMotionsProducer;
import Global.Tools;
import Platform.Core.Motion.MotionFabric;
import Platform.Core.Systems.SystemM;
import Platform.Core.Motion.Executer;
import Render.View.Render.IRender;
import Logic.Reflections.Space.ISpace;
import Model.Game.Presenter.Projections.ArgumentInt;
import Platform.Core.Motion.Motion;
import Platform.Core.Unit.IUnit;
import Render.View.IRenderResult;
import View.Render.Render.MotionCollect;
import View.Render.Render.MotionStartRender;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalinin
 */
public abstract class Render extends SystemM implements IRender, IMVC_View, IMVC_View.reflected, IRenderResult, IMotionsProducer {

    // RenderMashine renderMashine;
    ISpace space;
    public RenderCounter itr;
    IViewSource viewSource;
    MotionFabric motionRender;
    MotionFabric fmotionCollect;
    MotionFabric fmotionStartRender;
    private IUnit.drawable.projection[] allProjections = new IUnit.drawable.projection[1000];
    private int allProjectionsCounter = 0;

    public Render(IViewSource viewSource, ISpace space, RenderCounter itr_) {
        //    space = space_;
        super("R\t\t\t\t\t\t\t|");
        this.viewSource = viewSource;
        commandExecuters = new Executer[RenderFunctions.number];
        setCommandExecuter(RenderFunctions.CollectCommonProjectInfo_id, new ExecuterCollectCommonProjectInfo());
        setCommandExecuter(RenderFunctions.StartRender_id, new ExecuterStartRender());
        setCommandExecuter(RenderFunctions.RenderMainCode, new ExecuterRender(this));
        this.space = space;
        this.itr = itr_;

        motionRender = new MotionFactoryRender(1);
        fmotionCollect = new MotionFactoryCollectInfo(1);
        fmotionStartRender = new MotionFactoryStartRender(1);
    }
    //    public void Start(StartStopPauseSystem s) {
//        ((Render)s).space=space;
//    }
//    interface Render extends {
//
//        public abstract Motion CreateRender();
//    }
//    public class RenderMashine extends Unit implements Render {
//
//        String name = " render ";

    public IMotion CreateRender() {
        return motionRender.GetMotion(1);
    }

    public IMVC_View.reflected reflect(IUnit.drawable.projection[] projections) {

        //  long startTime = System.nanoTime();
        for (int i = 0; i < projections.length; i++) {
            projection projection = projections[i];

            if (projection == null) {
                continue;
            }
            boolean needToAdd = true;
            for (int j = 0; j < allProjectionsCounter; j++) {
                if (allProjections[j] == projection) {
                    needToAdd = false;
                    break;
                }
            }

            if (needToAdd) {

                if (allProjectionsCounter == allProjections.length) {

                    Tools.UnsupportedOperationException();
                }

                allProjections[allProjectionsCounter++] = projection;
            }
        }

        //   System.out.println(System.nanoTime()-startTime);

        for (int i = 0; i < allProjectionsCounter; i++) {
            projection projection = allProjections[i];
            getViewSource().reflect(projection.asImaginated(), projection.getViewState());
        }
        // System.out.println(System.nanoTime()-startTime);
        return this;
    }

    public IViewSource getViewSource() {
        return viewSource;
    }

    public String SetThreadName() {
        return "Render";
        // this.taskMotions.setName("Render");
    }

    class MotionFactoryCollectInfo extends MotionFabric {

        public MotionFactoryCollectInfo(int iterates_) {
            super(iterates_);
        }

        public IMotion NewMotion(int iterates_) {
            return new MotionCollect();
        }
    }

    class MotionCollect extends Motion {

        ArgumentInt n;

        public MotionCollect() {
            super(1, "Collect Info");
        }

        public void MotionMethod() {
            RegBeg(getMotionName());
            space.getPri().CollectcommonProjectionInfo();
            RegEnd(getMotionName());
        }

        public void SetArg(Object[] arg_) {
            if (arg_ != null) {
                n = (ArgumentInt) arg_[0];
            }
        }
    }

    class MotionFactoryStartRender extends MotionFabric {

        public MotionFactoryStartRender(int iterates_) {
            super(iterates_);
        }

        public IMotion NewMotion(int iterates_) {
            return new MotionStartRender();
        }
    }

    class MotionStartRender extends Motion {

        ArgumentInt n;

        public MotionStartRender() {
            super(1, "Start Render and wait");
        }

        public void MotionMethod() {
            //   conveerInterafe.RegBeg(currentTimeMillis(), taskMotions, sysName, " repeat ");
            motionSequenceRepeat();
            //  conveerInterafe.RegEnd(currentTimeMillis(), taskMotions, sysName, " repeat ");

            RegBeg(getMotionName());

//        if (pr.art.taskMotions.getName().contains("Render game")){
//            System.out.println("        " + pr.art.taskMotions.getName() + " go");
//        }
            AddMotions(getCommandExecuter(RenderFunctions.RenderMainCode).CreateMotion(null));
            Resume();
            itr.IncD();
//        if (pr.art.taskMotions.getName().contains("Render game")){
//            System.out.println("        for " + pr.art.taskMotions.getName() + " wait");
//        }
            itr.monitor.synchronizedWait();
//        if (pr.art.taskMotions.getName().contains("Render game")){
//            System.out.println("        " + pr.art.taskMotions.getName() + " finish");
//        }
            RegEnd(getMotionName());


//            conveerInterafe.RegBeg(currentTimeMillis(), taskMotions, sysName, "art repeat ");
//            art.motionSequence.Repeat();
//            conveerInterafe.RegEnd(currentTimeMillis(), taskMotions, sysName, "art repeat ");
        }

        public void SetArg(Object[] arg_) {
            if (arg_ != null) {
                n = (ArgumentInt) arg_[0];
            }
        }
    }

    class ExecuterRender extends Executer {

        public ExecuterRender(IMotionsProducer producer) {
            super(producer);
        }

        public IMotion CreateMotion(IArgument[] arg) {
            return ((Render) producer).CreateRender();
        }

        public IMotion StopMotion(IArgument[] arg) {
            Tools.UnsupportedOperationException();
            return null;
        }
    }

    class ExecuterCollectCommonProjectInfo extends Executer {

        public ExecuterCollectCommonProjectInfo() {
            super();
        }

        public IMotion CreateMotion(IArgument[] arg) {
            IMotion mot_ = fmotionCollect.GetMotion(1);
            ((MotionCollect) mot_).SetArg(null);
            return mot_;
        }

        public IMotion StopMotion(IArgument[] arg) {

            Tools.UnsupportedOperationException();
            return null;
        }
    }

    class ExecuterStartRender extends Executer {

        public ExecuterStartRender() {
            super();
        }

        public IMotion CreateMotion(IArgument[] arg) {
            IMotion mot_ = fmotionStartRender.GetMotion(1);
            ((MotionStartRender) mot_).SetArg(null);
            return mot_;

        }

        public IMotion StopMotion(IArgument[] arg) {

            Tools.UnsupportedOperationException();
            return null;
        }
    }

    class MotionFactoryRender extends MotionFabric {

        public MotionFactoryRender(int iterates_) {
            super(iterates_);
        }

        public IMotion NewMotion(int iterates_) {
            return new Motion(1, "Render") {

                @Override
                public void MotionMethod() {
                    //  conveerInterafe.RegBeg(currentTimeMillis(), taskMotions, sysName, " render ");
                    // render.Render();
                    space.Render();
                    //  conveerInterafe.RegEnd(currentTimeMillis(), taskMotions, sysName, " render ");
                    //                    java.lang.System.out.print(iterationCounter + Thread.currentThread().getName() + ":" + currentTimeMillis() + " " + sysName + "beg" + iterationCounter + "\n");

                    //                    java.lang.System.out.print(iterationCounter + Thread.currentThread().getName() + ":" + currentTimeMillis() + " " + sysName + "end" + iterationCounter + "\n");
                    //conveerInterafe.AfterMainWork(0, null);
                    itr.incRenderCounter();
                    itr.monitor.synchronizedRelease();
                }
            };
        }
    }
}
