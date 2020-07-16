/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Presenter.Projections;

import Application.ILoger;
import Logic.Reflections.Projections.IImaginator;
import Logic.Reflections.Projections.IImaginator.charged;
import Render.View.IRenderResult;
import MVC.View.IMVC_View;
import MVC.View.IViewSource;
import Render.View.Render.IRender;
import Logic.Reflections.Space.ISpace;
import Platform.Core.IArgument;
import Platform.Core.IConveer;
import Platform.Core.IExecuter;
import Platform.Core.IMotion;
import Platform.Core.ISystemMContainer;
import Platform.Core.Motion.EndLessMotion;
import Platform.Core.Motion.Executer;
import Platform.Core.Motion.Motion;
import Platform.Core.Motion.MotionFabric;
import Platform.Core.Systems.SystemM;
import Platform.Core.Unit.INumered;
import Platform.DataStructers.ILinkedElement;
import Platform.DataStructers.ILinkedList;
import Platform.DataStructers.ISelection;
import Platform.DataStructers.LinkedList;
import Platform.RenderCounter;
import Presenter.Platformer.IDecoration;
import Render.Graphics.Geometry.ICoordinates;
import Render.View.IImaginatorFactory;
import Render.View.IImaginatorsFactorys;
import View.Presenter.Projections.IViewUnit.imaginated;
import View.Presenter.Projections.IViewUnit.imaginated.list.selection;
import View.Presenter.Projections.IViewUnit.states;
import View.Render.Render;
import View.Render.RenderFunctions;

/**
 *
 * @author Adm
 */
public abstract class ProjectionD extends ProjectionStructure implements ISystemMContainer, IViewSource {

    abstract protected IImaginatorsFactorys createImaginatorsFactorys();

    abstract protected DecorationsListManager setDecorationsImaginators();

    abstract protected void onImaginatorCreation(IViewUnit unit, ICoordinates position, IImaginatorFactory imagefactory);
    RenderCounter itr;
    //   public UnitProjectionInfo[] info;
    MotionFabric motionDisENDITER;

    public abstract String SetThreadName();
    private IImaginatorsFactorys imaginatorsFactorys;
    private DecorationsListManager decorationsListManager;

    public ProjectionD(ISpace space) {

        sys = new Performer("D\t\t\t\t\t|");
        itr = new RenderCounter();

        this.space = space;
        //   commonProjectionInfo = commonProjectionInfo_;
        // units = new ProjectionObject[objectCount];
        // info = info_;
        sys.setCommandExecuters(new Executer[ExecutersID.maxID]);
        // space_ = CreateSpace();

        this.motionDisENDITER = new MotionFactoryDisENDITER(1, this);
        art = new Render(this, space, itr) {

            @Override
            public IRenderResult Render() {

                //    System.out.println("ToRender");

                sys.AddMotions(new Motion(1, "Decorations^Draw") {

                    public void MotionMethod() {
                        IViewUnit.imaginated.list.selection selection = decorationsListManager.select();
                        while (!selection.end()) {

                            IViewUnit.imaginated decoration = selection.get();

                            
                            ProjectionD.this.reflect(decoration, decoration.getViewState());
                        }
                    }
                });
                sys.AddMotions(getCommandExecuter(RenderFunctions.CollectCommonProjectInfo_id).CreateMotion(null));
                sys.AddMotions(getCommandExecuter(RenderFunctions.StartRender_id).CreateMotion(null));
                sys.AddMotions(motionDisENDITER.GetMotion(1));
                sys.Resume();

                return null;
            }
        };


    }

    public IMVC_View getRender() {
        return (IMVC_View) art;
    }

//    public int GetTime(int quant_) {
//        return iterationCounter * quant_;
//    }
    public int GetViewOffset() {
        return 0;
    }

    public ICoordinates[] GetViewVector() {
        return null;
    }

    public void DrawPict(IArgument[] arg_) {
    }

    protected ISpace getSpace() {
        return space;
    }

    public IPerformer getPerformer() {
        return (IPerformer) sys;
    }

    public IViewSource.ISimplePerformer getSimplePerformer() {
        return (ISimplePerformer) sys;
    }

    public void reflect(imaginated unit, states state) {
        charged imaginator = unit.getImaginator();

        if (imaginator != null) {
            IMotion mot_ = imaginator.CreateMotionImageGenerator(state);

            // mot_.Iterate();
            // System.out.println(mot_.getMotionName());
            sys.AddMotions(mot_);
            sys.Resume();
        }

    }

    public IViewUnit.imaginated.list imaginate(IViewUnit.list list) {

        if (imaginatorsFactorys == null) {
            imaginatorsFactorys = createImaginatorsFactorys();
        }

        IViewUnit.list.selection selection = list.select();

        while (!selection.end()) {

            IViewUnit viewUnit = selection.get();
            int typeID = viewUnit.getTypeID();

            if (typeID != 0) {
                IImaginatorFactory factory = imaginatorsFactorys.getFactory(typeID);

                viewUnit.setImaginator(factory.getImaginator(viewUnit, viewUnit.getPosition()));

                onImaginatorCreation(viewUnit, viewUnit.getPosition(), factory);
            }
        }

        IViewUnit.imaginated.list result = new ImaginatedList(list);

        return result;
    }

    public Object GetImage() {

        return art;

    }

    public class ImaginatedList implements IViewUnit.imaginated.list {

        IViewUnit.list list;

        public ImaginatedList(IViewUnit.list list) {
            this.list = list;
        }

        public void add(imaginated e) {
            list.add((IViewUnit) e);
        }

        public selection select() {

            return new selection() {

                IViewUnit.list.selection selection = list.select();

                public boolean end() {

                    return selection.end();
                }

                public IViewUnit.imaginated get() {
                    return (imaginated) selection.get();
                }
            };
        }
    }

    private class Performer extends SystemM implements IViewSource.IPerformer, IViewSource.ISimplePerformer {

        public Performer(String sysName) {
            super(sysName);
        }

        @Override
        public String SetThreadName() {
            return ProjectionD.this.SetThreadName();
        }

        @Override
        public void setOwner(IConveer owner) {
            super.setOwner(owner);
            ((SystemM) art).setOwner(owner);
        }

        public void Start() {
            super.Start();
//        art.conveerInterafe = new ConveerInterface() {
//
//            public void AfterMainWork(int n, IArgument[] arg) {
//
//               Tools.UnsupportedOperationException();
//            }
//
//            public void RegBeg(long time_, Thread tr_, String name_, String log_) {
//                ProjectionD.this.conveerInterafe.RegBeg(time_, tr_, name_, log_);
//            }
//
//            public void RegEnd(long time_, Thread tr_, String name_, String log_) {
//                ProjectionD.this.conveerInterafe.RegEnd(time_, tr_, name_, log_);
//            }
//        };
            art.Start();
            new MotionDecorations(1, "Decorations^Init").add(sys);

        }

        public void Stop() {
            super.Stop();
            // art.conveerInterafe = null;
            art.AddMotions(new MotionArtStop(1, "pri=null", art));
            art.Resume();

        }

        public IExecuter[] getExecuters() {
            return sys.getCommandExecuters();
        }

        public void execute(int viewUnit) {
            AddMotions(getExecuters()[viewUnit].CreateMotion(null));
            Resume();
        }

        public void execute(int viewUnit, IArgument[] arg) {

            AddMotions(getExecuters()[viewUnit].CreateMotion(arg));
            Resume();
        }
    }

    class MotionArtStop extends Motion {

        IRender art;

        public MotionArtStop(int iterates_, String motionName_, IRender art) {
            super(iterates_, motionName_);
            this.art = art;
        }

        public void MotionMethod() {
//        art.space.pri = null;
            art.Stop();
        }
    }

    class MotionFactoryDisENDITER extends MotionFabric {

        public MotionFactoryDisENDITER(int iterates_, ProjectionD owner_) {
            super(iterates_);
        }

        public IMotion NewMotion(int iterates_) {
            return new Motion(1, "ENDITER") {

                @Override
                public void MotionMethod() {

                    ((ILoger) getSys()).RegBeg(" projectENDITER ");

                    ((ILoger) getSys()).RegBeg(" project repeat ");
                    getSys().Repeat(true);
                    //  System.out.println("project repeate");
                    ((ILoger) getSys()).RegEnd(" project repeat ");


                    ((ILoger) getSys()).RegBeg(" inc itter ");
                    //   System.out.println("ReleaseMonitor");
                    getSys().getMonitor().synchronizedRelease();
                    ((ILoger) getSys()).RegEnd(" inc itter ");
                    //   itr.IncD();

                    //       java.lang.System.out.print(project.iterationCounter + Thread.currentThread().getName() + ":" + currentTimeMillis() + " " + project.sysName + " add repeat" + "\n");
                    //   mod.motionSequence.AddMotions(motionModIncIter.GetMotion(1));
		/*2*/
                    //     mod.taskMotions.Resume();


                    //         java.lang.System.out.print(project.iterationCounter + Thread.currentThread().getName() + ":" + currentTimeMillis() + " " + project.sysName + " add render" + "\n");

                    //            art.motionSequence.AddMotions(art.commandExecuters[RenderMainCode].CreateMotion(null));
                    //            /*3*/
                    //            ArttaskMotionsResume();
                    ((ILoger) getSys()).RegEnd(" ownerENDITER ");
                }
            };
        }
    }

    private class MotionDecorations extends Motion {

        public MotionDecorations(int iterates, String name) {
            super(iterates, name);
        }

        @Override
        public void MotionMethod() {
            decorationsListManager = setDecorationsImaginators();

        }
    }

    public class Decoration implements IDecoration, ILinkedElement, INumered {

        private int index;
        private ILinkedElement next;
        private IImaginator.charged imaginator;

        public Decoration(IImaginator.charged imaginator) {

            this.imaginator = imaginator;
        }

        public void setIndex(int i) {
            index = i;
        }

        public int getIndex() {
            return index;
        }

        public ILinkedElement getNext() {
            return next;
        }

        public IViewUnit.imaginated setImaginator(IImaginator.charged imaginator) {
            this.imaginator = imaginator;
            return this;
        }

        public IImaginator.charged getImaginator() {
            return imaginator;
        }

        public ILinkedElement asLinkedElement() {
            return this;
        }

        public imaginated asImaginated() {
            return this;
        }

        public states getViewState() {
            return IViewUnit.states.zero;
        }

        public void setNext(ILinkedElement e) {
            next = e;
        }
    }

    public class DecorationsListManager implements IDecoration.list {

        protected ILinkedList unitsList;

        public DecorationsListManager() {

            unitsList = new LinkedList();
        }

        public IViewUnit.imaginated add(IDecoration e) {
            return (IDecoration) unitsList.add((ILinkedElement) e);
        }

        public IDecoration.list.selection select() {
            return new selection() {

                ISelection select = unitsList.select();

                public boolean end() {

                    return !select.getNext();
                }

                public IDecoration get() {
                    return (IDecoration) select.getCurrent();
                }
            };
        }
    }
}
