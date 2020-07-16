/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler.Menu.MainAnimMenu;

import Logic.Environment.Menu.MainAnimMenu.IMenuCommonCommandsList;
import Collaborations.MVC.Control.Menu;
import Platform.Core.IMotionReciver;
import Platform.Core.Motion.Motion;
import Platform.Core.IMotion;
import Platform.Core.Unit.IUnit;

/**
 *
 * @author kalinin
 */
public abstract class MainAnimMenu extends Menu {

    abstract protected IMenuCommonCommandsList.Main getCommandList();

    private Command NewGameId;
    private Command LoadGameId;
    private Command StartId;
    private Command ExitId;

    public MainAnimMenu(IMotionReciver reciver) {
        super(reciver);
    }

    public void SetUnits() {

        StartId = (Command) units().add(new Command() {

            @Override
            public void PreSetFocus(Command c_) {
                this.setVisible(false);
                if (c_ != null) {
                    c_.setVisible(true);
                }
            }

            public void Enter() {

                reciver.AddMotions(new Motion(10, "Go") {

                    public void MotionMethod() {
                        PreSetFocus(NewGameId);
                        if (isFinal()) {
                            SetFocus(NewGameId);
                        }
                    }
                });
                reciver.Resume();
            }

            public IMotion StopMotionEsc(Object[] arg) {
                return new Motion(1, "not") {

                    public void MotionMethod() {

                        getCommandList().ExitToSystem();
                    }
                };
            }
        }).setTypeID(1);

        NewGameId = (Command) units().add(new Command() {

            @Override
            public void PreSetFocus(Command c_) {
                this.setVisible(false);
                if (c_ != null) {
                    c_.setVisible(true);
                }
            }

            public void Enter() {
                getCommandList().StartNewGame();
            }

            public IMotion StopMotionEsc(Object[] arg) {
                return new Motion(10, "Esc") {

                    public void MotionMethod() {
                        PreSetFocus(StartId);
                        if (isFinal()) {
                            SetFocus(StartId);
                        }
                    }
                };
            }
        }).setTypeID(2);
        LoadGameId = (Command) units().add(new Command() {

            @Override
            public void PreSetFocus(Command c_) {
                this.setVisible(false);
                if (c_ != null) {
                    c_.setVisible(true);
                }
            }

            public void Enter() {
                getCommandList().LoadGame(1);
            }

            public IMotion StopMotionEsc(Object[] arg) {
                return new Motion(10, "Esc") {

                    public void MotionMethod() {
                        PreSetFocus(StartId);
                        if (isFinal()) {
                            SetFocus(StartId);
                        }
                    }
                };
            }
        }).setTypeID(3);

        ExitId = (Command) units().add(new Command() {

            @Override
            public void PreSetFocus(Command c_) {
                this.setVisible(false);
                if (c_ != null) {
                    c_.setVisible(true);
                }
            }

            public void Enter() {
                reciver.AddMotions(new Motion(10, "Go") {

                    public void MotionMethod() {

                        if (isFinal()) {
                            getCommandList().ExitToSystem();
                        }
                    }
                });
                reciver.Resume();
            }

            public IMotion StopMotionEsc(Object[] arg) {
                return new Motion(1, "not") {

                    public void MotionMethod() {

                        getCommandList().ExitToSystem();
                    }
                };
            }
        }).setTypeID(4);
//        (this.ExitWithSaveId] = new Command() {
//
//            public void Enter() {
//                SaveGame();
//                ExitToSystem();
//            }
//
//            public IMotion StopMotionEsc(Object[] arg) {
//                return new IMotion(1, "Esc") {
//
//                    public void MotionMethod() {
//                        SetFocus((Command) (this.ExitId]);
//                        ((UnitDMenuObject) (this.ExitWithOutSaveId]).visible = false;
//                        ((UnitDMenuObject) (this.ExitWithSaveId]).visible = false;
//                    }
//                };
//            }
//        };
//        (this.ExitWithOutSaveId] = new Command() {
//
//            public void Enter() {
//                ExitToSystem();
//            }
//
//            public IMotion StopMotionEsc(Object[] arg) {
//                return new IMotion(1, "Esc") {
//
//                    public void MotionMethod() {
//                        SetFocus((Command) (this.ExitId]);
//                        ((UnitDMenuObject) (this.ExitWithOutSaveId]).visible = false;
//                        //((UnitDMenuObject) (this.ExitWithSaveId]).visible = false;
//                    }
//                };
//            }
//        };
        StartId.LinkTo(ExitId);
        ExitId.LinkTo(StartId);
        NewGameId.LinkTo(LoadGameId);
        LoadGameId.LinkTo(NewGameId);
        // ( (ExitWithSaveId)).LinkTo( (ExitWithOutSaveId));
        //  ( (ExitWithOutSaveId)).LinkTo( (ExitWithSaveId));

        //  ((UnitDMenuObject) (FocusId)).visible = true;
        StartId.setVisible(true);
        ExitId.setVisible(false);

        SetFocus(StartId);
    }
}
