/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Manipulator;

import Application.Build.Panels.Control.Manipulator.IManipulator;
import MVC.Control.IControler;
import Platform.Concurrent.Factory;
import Platform.Concurrent.ILathceStop;
import Platform.Core.IArgument;
import Platform.Core.IExecuter;
import Platform.Core.IMotion;
import Platform.Core.Motion.Motion;
import Platform.Core.Systems.SystemM;

/**
 *
 * @author kalinin
 */
public class ManipulatorSystem extends SystemM implements IManipulator, IManipulator.binded {

    private final ILathceStop move;
    private IAction action;
    private IControler.Ready controler;

    public ManipulatorSystem() {
        super("M\t\t\t\t\t\t\t\t\t|");
        move = Factory.createLathceStop(false);
    }

    public String SetThreadName() {
        return "Manipulator";
    }

    public IManipulator.binded bindAction(IAction action) {
        this.action = action;
        action.setManipulator(this);
        return this;
    }

    public binded setProducer(IExecuter executer) {
        executer.setProducer(this);
        return this;
    }

    public IMotion MouseClickLeft(final IArgument[] arg) {
        return new Motion(1, "MouseLeft") {

            IArgument[] arg_ = arg;

            public void MotionMethod() {
                action.Do(arg_);
            }
        };

    }

    public IMotion StopMouseClickLeft(IArgument[] arg) {
        return null;
    }

    public IMotion MouseClickRight(final IArgument[] arg) {
        return new Motion(1, "MouseRight") {

            IArgument[] arg_ = arg;

            public void MotionMethod() {
                action.Abort(arg_);
            }
        };
    }

    public IMotion StopMouseClickRight(IArgument[] arg) {
        return null;
    }


    public void Stop() {
        move.setContinue(false);
        // exployer.Stop();
        // game.jumper.GetExployer().Release();
        super.Stop();
    }

    public IMotion MouseSelect(final IArgument[] arg) {
        return new Motion(1, "MouseSelect") {

            IArgument[] arg_ = arg;

            public void MotionMethod() {
                action.Select(arg_);
            }
        };
    }

    public IMotion StopMouseSelect(IArgument[] arg) {
        return null;
    }

    public IMotion MouseSelecting(final IArgument[] arg) {
        return new Motion(1, "MouseSelecting") {

            IArgument[] arg_ = arg;

            public void MotionMethod() {

                action.Selecting(arg_);
            }
        };
    }

    public IMotion StopMouseSelecting(IArgument[] arg) {
        return null;
    }

    public IMotion MoveMouse(final IArgument[] arg) {
        return new Motion(1, "MouseSelecting") {

            IArgument[] arg_ = arg;

            public void MotionMethod() {
                action.Move(arg_);
            }
        };
    }

    public IMotion StopMoveMouse(IArgument[] arg) {
        return null;
    }
//    void SetCurrentCommand(Coordinates2d pos) {
//        Coordinates2d Jpos = (Coordinates2d) game.jumper.lastUnit.position;
//
//        Action a_ = menu.Include(pos);
//        if (a_ != null) {
//            menu.Show();
//            currentAction = menu;
//        } else {
//        }
//    }
}
