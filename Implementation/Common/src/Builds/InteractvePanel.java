/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Builds;

import Logic.Model.Game.World.PathFindng.IPathMotionReciver;
import Logic.Model.Scenarios.IGameMotionReciver;
import MVC.Strategy.IMVCStrategy;
import Application.Panels.IPanelsManager;
import Application.Build.Panels.Model.IInteractvePanel;
import Application.ILoger;
import Application.Build.Panels.Model.IEnvironment;
import MVC.Control.IControler;
import MVC.Control.IInputCollector;
import MVC.Model.IDomainModel;
import MVC.Strategy.IMVCFactory;
import MVC.View.IViewSource;
import Control.Control;
import Controler.Coordinates2dArgument;
import Global.Tools;
import Environment.Motions.MotionReciversDispatcer;
import Logic.Environment.Motions.IMotionReciversDispatcer;
import Logic.Model.IDataSource;
import MVC.Strategy.MVCFuncional;
import Platform.Core.Conveer.Conveer;
import Platform.Core.IArgument;
import Platform.Core.IMotion;
import Platform.Core.IMotionReciver;
import Platform.Core.ISystemMContainer;
import Platform.Core.Systems.SystemM;
import Platform.Util.Profiler.ClassAssociations;

/**
 *
 * @author kalinin
 */
public abstract class InteractvePanel extends Conveer implements IInteractvePanel, IInteractvePanel.Window, IEnvironment, IEnvironment.control, IMVCFactory {

    private boolean DEBUG_paused = true;
    private Control control;
    private IMVCStrategy MVCStrategy;

    public InteractvePanel(boolean oneThread_, int artquant_) {
        super(oneThread_, artquant_);
        ClassAssociations.PrintAssociaties();

        control = new Control();

        setMod(new ModelSystem("M\t\t\t|"));
    }

    private IMVCStrategy createMVCMediator() {

        MVCStrategy = new MVCFuncional(this, control.setEnvironmentControl(this));
        
        MVCStrategy.getDomainModel().PATCH_setUp(control.getControler());

        setDis(((ISystemMContainer) MVCStrategy.getPresenter()).getSys());
        return MVCStrategy;
    }

    public IInteractvePanel.Window start() {
        return start(getNULLData());
    }

    public IInteractvePanel.Window start(IDataSource dataSource) {

        createMVCMediator().init(dataSource).add(getMod()).Resume();

        super.Start();

        return this;
    }

    public Window setCurrentFor(IPanelsManager stack) {
        stack.setCurrentPanel(this);
        return this;
    }

    public void keyPressed(int device, int n, int[] arg) {
        getInputCollector().addPressedKey(device, n, arg);
    }

    public void keyReleased(int device, int n, int[] arg) {
        getInputCollector().addReleasedKey(device, n, arg);
    }

    public int GetViewOffset() {
        return 0;
    }

    public IInputCollector getInputCollector() {
        return control.getInputCollector();
    }

    public IControler getController() {
        return control.getControler();
    }

    protected IDomainModel.asGame DEBUG_getDomainModel() {
        return (IDomainModel.asGame) MVCStrategy.getDomainModel();
    }

    public int getHight() {
        return ((IDomainModel.asGame) MVCStrategy.getDomainModel()).getCollisionMap().getHight();
    }

    public int getWeight() {

        return ((IDomainModel.asGame) MVCStrategy.getDomainModel()).getCollisionMap().getWeight();
    }

    public IArgument[] ArgToCoor(int[] arg) {
        Coordinates2dArgument[] coor = new Coordinates2dArgument[2];
        coor[0] = new Coordinates2dArgument();
        coor[0].x = (arg[0]);
        //coor[0].y = ((getWeight() - 0) - arg[1]);
        coor[1] = new Coordinates2dArgument();
        coor[1].x = (arg[2]);
        //coor[1].y = ((getHight() - 0) - arg[3]);

        coor[0].y = arg[1];
        coor[1].y = arg[1];

        return coor;
    }

    public void Resume() {

        if (DEBUG_paused) {
            DEBUG_paused = false;
            super.Resume();
        } else {
            System.out.println("Level " + getMod().getTaskMotions().getName() + " already on resume!!!");

            Tools.UnsupportedOperationException();
        }
    }

    public void Pause() {
        if (!DEBUG_paused) {
            DEBUG_paused = true;
            super.Pause();
        } else {
            System.out.println("Level " + getMod().getTaskMotions().getName() + " already on pause!!!");
            Tools.UnsupportedOperationException();
        }
    }

    public void AddMotions(IMotion motion) {
        getMod().AddMotions(motion);
        getMod().Resume();
    }

    public IViewSource getPresenter() {
        return MVCStrategy.getPresenter();
    }

    public void RegBeg(String string) {
        ((ILoger) getMod()).RegBeg(string);
    }

    public void RegEnd(String string) {
        ((ILoger) getMod()).RegEnd(string);
    }

    public ILoger getLogger() {
        return (ILoger) getMod();
    }

    public IMotionReciver getModelsMotionsReciver() {
        return ((ModelSystem) getMod()).motionsReciver.getMotionReciver();
    }

    public IGameMotionReciver getGameMotionReciver() {
        return ((ModelSystem) getMod()).motionsReciver.getGameMotionReciver();
    }

    public IPathMotionReciver getPathMotionReciver() {
        return ((ModelSystem) getMod()).motionsReciver.getPathMotionReciver();
    }

    class ModelSystem extends SystemM {

        IMotionReciversDispatcer motionsReciver;

        public ModelSystem(String sysName_) {
            super(sysName_);
            motionsReciver = new MotionReciversDispatcer(this);
        }

        public String SetThreadName() {
            return (MVCStrategy.getDomainModel()).SetThreadName();
        }

        public String GetSec() {
            String st = "";
//        CalcSystemM first = modcalc.current;
//        IMotion mn_ = first.motionSequence.next;
//        while (mn_ != first.motionSequence) {
//            st = st + ((IMotion) mn_).motionName + ", "
//                    + ((IMotion) mn_).counter + "\n";
//            mn_ = (IMotion) (mn_).next;
//        }

//        st = st + "----------------" + "\n";
//        first = modcalc.current.next;
//        mn_ = first.motionSequence.next;
//        while (mn_ != first.motionSequence) {
//            st = st + ((IMotion) mn_).motionName + ", "
//                    + ((IMotion) mn_).counter + "\n";
//            mn_ = (IMotion) (mn_).next;
//        }
            return st;
        }

        @Override
        public void AddMotions(IMotion motion) {
            super.AddMotions(motion);
        }
    }

    private IDataSource getNULLData() {
        return new IDataSource() {

            public Object GetData() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
}
