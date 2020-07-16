/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Global.Tools;
import Logic.Model.Game.IGameMotionFactory;
import MVC.Control.MovementsProducers.Manipulator.IProducer.IMouseLeftClickProducer;
import MVC.Control.MovementsProducers.Manipulator.IProducer.IMouseMoveProducer;
import MVC.Control.MovementsProducers.Manipulator.IProducer.IMouseRightClickProducer;
import MVC.Control.MovementsProducers.Manipulator.IProducer.IMouseSelectProducer;
import MVC.Control.MovementsProducers.Manipulator.IProducer.IMouseSelectingProducer;
import MVC.Control.MovementsProducers.MenuCommands.IProducers;
import MVC.Control.MovementsProducers.MenuCommands.IProducers.Enter;
import MVC.Control.MovementsProducers.MenuCommands.IProducers.Esc;
import MVC.Control.MovementsProducers.UnitCommands.IProducer.MoveDown;
import MVC.Control.MovementsProducers.UnitCommands.IProducer.MoveLeft;
import MVC.Control.MovementsProducers.UnitCommands.IProducer.MoveRight;
import MVC.Control.MovementsProducers.UnitCommands.IProducer.MoveUp;
import MVC.Control.MovementsProducers.UnitCommands.IProducer.Rotate;
import Platform.Core.IExecuter;
import Platform.Core.Motion.Executer;
import Platform.Core.IArgument;
import Platform.Core.IMotion;
import Platform.Core.Motion.Motion;

/**
 *
 * @author Dkx6r0c
 */
public class MovementsFactory {

    public static IExecuter CreateExecuter(String name) {

        if (name.equals("Enter")) {
            return MovementsFactory.CreateExecuterEnter();
        }

        if (name.equals("Esc")) {
            return MovementsFactory.CreateExecuterEsc();
        }

        if (name.equals("MoveLeft")) {
            return MovementsFactory.CreateExecuterMoveLeft();
        }

        if (name.equals("MoveRight")) {
            return MovementsFactory.CreateExecuterMoveRight();
        }
        if (name.equals("MoveUp")) {
            return MovementsFactory.CreateExecuterMoveUp();
        }
        if (name.equals("MoveDown")) {
            return MovementsFactory.CreateExecuterMoveDown();
        }

        if (name.equals("MouseLeft")) {
            return MovementsFactory.CreateExecuterMouseLeft();
        }

        if (name.equals("MouseRight")) {
            return MovementsFactory.CreateExecuterMouseRight();
        }

        if (name.equals("MouseSelecting")) {
            return MovementsFactory.CreateExecuterMouseSelecting();
        }

        if (name.equals("MouseSelect")) {
            return MovementsFactory.CreateExecuterMouseSelect();
        }

        if (name.equals("Save")) {
            return MovementsFactory.CreateExecuterSave();
        }

        if (name.equals("Load")) {
            return MovementsFactory.CreateExecuterLoad();
        }

        if (name.equals("NextCommand")) {
            return MovementsFactory.CreateExecuterNextCommand();
        }

        if (name.equals("PreviosCommand")) {
            return MovementsFactory.CreateExecuterPreviosCommand();
        }

        if (name.equals("Selecting")) {
            return MovementsFactory.CreateExecuterMouseSelecting();
        }

        if (name.equals("Select")) {
            return MovementsFactory.CreateExecuterMouseSelect();
        }
        if (name.equals("Move")) {
            return MovementsFactory.CreateExecuterMouseMove();
        }


        Tools.UnsupportedOperationException("Unknow name " + name);
        return null;

    }

    public static Executer CreateExecuterEnter() {
        return new ExecuterEnter();
    }

    public static Executer CreateExecuterEsc() {
        return new ExecuterEsc();
    }

    public static Executer CreateNothingExecuter() {
        return new NothingExecuter();
    }

    public static Executer CreateExecuterMoveLeft() {
        return new ExecuterMoveLeft();
    }

    public static Executer CreateExecuterMoveRight() {
        return new ExecuterMoveRight();
    }

    public static Executer CreateExecuterMoveUp() {
        return new ExecuterMoveUp();
    }

    public static Executer CreateExecuterMoveDown() {
        return new ExecuterMoveDown();
    }

    public static Executer CreateExecuterMoveRotate() {
        return new ExecuterMoveRotate();
    }

    public static Executer CreateExecuterMouseLeft() {
        return new ExecuterMouseLeft();
    }

    public static Executer CreateExecuterMouseRight() {
        return new ExecuterMouseRight();
    }

    public static Executer CreateExecuterMouseSelecting() {
        return new ExecuterMouseSelecting();
    }

    public static Executer CreateExecuterMouseSelect() {
        return new ExecuterMouseSelect();
    }

    public static Executer CreateExecuterMouseMove() {
        return new ExecuterMouseMove();
    }

    public static Executer CreateExecuterSave() {
        return new ExecuterSave();
    }

    static boolean isNothing(IExecuter exec) {
        return exec instanceof NothingExecuter;
    }

    private static IExecuter CreateExecuterLoad() {
        return new ExecuterLoad();
    }

    private static IExecuter CreateExecuterNextCommand() {
        return new ExecuterNextCommand();
    }

    private static IExecuter CreateExecuterPreviosCommand() {
        return new ExecuterPreviosCommand();
    }

    public static class ExecuterEsc extends Executer {

        public IMotion CreateMotion(IArgument[] arg) {
            return ((Esc) producer).CreateMotionEsc(arg);
        }

        public boolean occupy(int i_) {
            return true;
        }

        public IMotion StopMotion(IArgument[] arg) {
            return new Motion(1, "not") {

                public void MotionMethod() {
                }
            };
        }
    }

    public static class ExecuterEnter extends Executer {

        public IMotion CreateMotion(IArgument[] arg) {
            return ((Enter) producer).CreateMotionEnter(arg);
        }

        public boolean occupy(int i_) {
            return true;
        }

        public IMotion StopMotion(IArgument[] arg) {
            return new Motion(1, "not") {

                public void MotionMethod() {
                }
            };
        }
    }

    public static class ExecuterPauseResume extends Executer {

        public ExecuterPauseResume() {
            super();
        }

        public IMotion CreateMotion(IArgument[] arg) {
            return ((IGameMotionFactory) producer).NewMotionGamePauseResume(1, "PauseResume", this, arg);
        }

        public boolean occupy(int i_) {
            return true;
        }

        public IMotion StopMotion(IArgument[] arg) {
            return new Motion(1, "not") {

                public void MotionMethod() {
                }
            };
        }
    }

    public static class ExecuterSave extends Executer {

        public ExecuterSave() {
            super();
        }

        public IMotion CreateMotion(IArgument[] arg) {
            return new Motion(1, "not") {

                public void MotionMethod() {
                }
            };
        }

        public boolean occupy(int i_) {
            return true;
        }

        public IMotion StopMotion(IArgument[] arg) {
            return ((IGameMotionFactory) producer).newMotionGameSave(arg);
        }
    }

    public static class ExecuterLoad extends Executer {

        public ExecuterLoad() {
            super();
        }

        public IMotion CreateMotion(IArgument[] arg) {
            return new Motion(1, "not") {

                public void MotionMethod() {
                }
            };
        }

        public boolean occupy(int i_) {
            return true;
        }

        public IMotion StopMotion(IArgument[] arg) {
            return ((IGameMotionFactory) producer).newMotionGameLoad(arg);
        }
    }

    public static class NothingExecuter extends Executer {

        public NothingExecuter() {
            super();
        }

        public IMotion CreateMotion(IArgument[] arg) {
            return null;
        }

        public IMotion StopMotion(IArgument[] arg) {
            return null;
        }
    }

    public static class ExecuterMoveDown extends Executer {

        public ExecuterMoveDown() {
            super();
        }

        public IMotion CreateMotion(IArgument[] arg) {
            return ((MoveDown) producer).CreateMotionMoveDown();
        }

        public IMotion StopMotion(IArgument[] arg) {
            return ((MoveDown) producer).StopMotionMoveDown();
        }
    }

    public static class ExecuterMoveLeft extends Executer {

        public ExecuterMoveLeft() {
            super();
        }

        public IMotion CreateMotion(IArgument[] arg) {
            return ((MoveLeft) producer).CreateMotionMoveLeft();
        }

        public IMotion StopMotion(IArgument[] arg) {
            return ((MoveLeft) producer).StopMotionMoveLeft();
        }
    }

    public static class ExecuterMoveRight extends Executer {

        public ExecuterMoveRight() {
            super();
        }

        public IMotion CreateMotion(IArgument[] arg) {
            return ((MoveRight) producer).CreateMotionMoveRight();
        }

        public IMotion StopMotion(IArgument[] arg) {
            return ((MoveRight) producer).StopMotionMoveRight();
        }
    }

    public static class ExecuterMoveRotate extends Executer {

        public ExecuterMoveRotate() {
            super();
        }

        public IMotion CreateMotion(IArgument[] arg) {
            return ((Rotate) producer).CreateMotionMoveRotate(arg);
        }

        public IMotion StopMotion(IArgument[] arg) {
            return ((Rotate) producer).StopMotionMoveRotate(arg);
        }
    }

    public static class ExecuterMoveUp extends Executer {

        public ExecuterMoveUp() {
            super();
        }

        public IMotion CreateMotion(IArgument[] arg) {
            return ((MoveUp) producer).CreateMotionMoveUp();
        }

        public IMotion StopMotion(IArgument[] arg) {
            MoveUp MoveUpactor = ((MoveUp) producer);
            if (MoveUpactor != null) {
                return MoveUpactor.StopMotionMoveUp();
            } else {
                return null;
            }
        }
    }

    public static class ExecuterMouseLeft extends Executer {

        public ExecuterMouseLeft() {
            super();
        }

        public IMotion CreateMotion(IArgument[] arg) {
            return ((IMouseLeftClickProducer) producer).MouseClickLeft(arg);
        }

        public IMotion StopMotion(IArgument[] arg) {
            return ((IMouseLeftClickProducer) producer).StopMouseClickLeft(arg);
        }
    }

    public static class ExecuterMouseRight extends Executer {

        public ExecuterMouseRight() {
            super();
        }

        public IMotion CreateMotion(IArgument[] arg) {
            return ((IMouseRightClickProducer) producer).MouseClickRight(arg);
        }

        public IMotion StopMotion(IArgument[] arg) {
            return ((IMouseRightClickProducer) producer).StopMouseClickRight(arg);
        }
    }

    public static class ExecuterMouseSelecting extends Executer {

        public ExecuterMouseSelecting() {
            super();
        }

        public IMotion CreateMotion(IArgument[] arg) {
            return ((IMouseSelectingProducer) producer).MouseSelecting(arg);
        }

        public IMotion StopMotion(IArgument[] arg) {
            return ((IMouseSelectingProducer) producer).StopMouseSelecting(arg);
        }
    }

    public static class ExecuterMouseSelect extends Executer {

        public ExecuterMouseSelect() {
            super();
        }

        public IMotion CreateMotion(IArgument[] arg) {
            return ((IMouseSelectProducer) producer).MouseSelect(arg);
        }

        public IMotion StopMotion(IArgument[] arg) {
            return ((IMouseSelectProducer) producer).StopMouseSelect(arg);
        }
    }

    public static class ExecuterMouseMove extends Executer {

        public ExecuterMouseMove() {
            super();
        }

        public IMotion CreateMotion(IArgument[] arg) {
            return ((IMouseMoveProducer) producer).MoveMouse(arg);
        }

        public IMotion StopMotion(IArgument[] arg) {
            return ((IMouseMoveProducer) producer).StopMoveMouse(arg);
        }
    }

    public static class ExecuterNextCommand extends Executer {

        public ExecuterNextCommand() {
            super();
        }

        public IMotion CreateMotion(IArgument[] arg) {
            return ((IProducers.NextCommand) producer).CreateMotionNextCommand(arg);
        }

        public IMotion StopMotion(IArgument[] arg) {
            return ((IProducers.PreviosCommand) producer).StopMotionPreviosCommand(arg);
        }
    }

    public static class ExecuterPreviosCommand extends Executer {

        public ExecuterPreviosCommand() {
            super();
        }

        public IMotion CreateMotion(IArgument[] arg) {
            return ((IProducers.PreviosCommand) producer).CreateMotionPreviosCommand(arg);
        }

        public IMotion StopMotion(IArgument[] arg) {
            return ((IProducers.PreviosCommand) producer).StopMotionPreviosCommand(arg);
        }
    }
}
