/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler.Menu.MainMenu;

import Collaborations.MVC.Control.Menu;
import Platform.Core.Motion.Motion;
import Platform.Core.IMotion;
import Platform.Core.IMotionReciver;

/**
 *
 * @author kalinin
 */
public abstract class MainMenu extends Menu {

    public MainMenu(IMotionReciver reciver) {
        super(reciver);

        ((Command) (IParametr.StartId)).LinkTo((Command) (IParametr.ExitId));
        ((Command) (IParametr.ExitId)).LinkTo((Command) (IParametr.StartId));
        ((Command) (IParametr.NewGameId)).LinkTo((Command) (IParametr.LoadGameId));
        ((Command) (IParametr.LoadGameId)).LinkTo((Command) (IParametr.NewGameId));
        //   ((Command) (IParametr.ExitWithSaveId]).LinkTo((Command) (IParametr.ExitWithOutSaveId]);
        // ((Command) (IParametr.ExitWithOutSaveId]).LinkTo((Command) (IParametr.ExitWithSaveId]);

        //  ((Command) (IParametr.FocusId]).visible = true;
        ((Command) (IParametr.StartId)).setVisible(true);
        ((Command) (IParametr.ExitId)).setVisible(true);

        SetFocus((Command) (IParametr.StartId));

    }

    public void SetUnits() {

        //  (IParametr.FocusId] = new Command();
        IParametr.StartId = units().add(new Command() {

            public void Enter() {

                reciver.AddMotions(new Motion(10, "Go") {

                    public void MotionMethod() {
                        if (isFinal()) {
                            SetFocus((Command) (IParametr.NewGameId));
                            ((Command) (IParametr.Panel1Id)).setVisible(true);
                            ((Command) (IParametr.NewGameId)).setVisible(true);
                            ((Command) (IParametr.LoadGameId)).setVisible(true);
                        }
                    }
                });
            }

            public IMotion StopMotionEsc(Object[] arg) {
                return new Motion(1, "not") {

                    public void MotionMethod() {

                        ExitToSystem();
                    }
                };
            }
        });
        IParametr.ExitId = units().add(new Command() {

            public void Enter() {
                reciver.AddMotions(new Motion(10, "Go") {

                    public void MotionMethod() {
                        if (isFinal()) {
                            ExitToSystem();
                        }
                    }
                });
            }

            public IMotion StopMotionEsc(Object[] arg) {
                return new Motion(1, "not") {

                    public void MotionMethod() {
                    }
                };
            }
        });

        IParametr.NewGameId = units().add(new Command() {

            public void Enter() {
                StartNewGame();
            }

            public IMotion StopMotionEsc(Object[] arg) {
                return new Motion(1, "Esc") {

                    public void MotionMethod() {
                        SetFocus((Command) (IParametr.StartId));
                        ((Command) (IParametr.Panel1Id)).setVisible(false);
                        ((Command) (IParametr.NewGameId)).setVisible(false);
                        ((Command) (IParametr.LoadGameId)).setVisible(false);
                    }
                };
            }
        });

        IParametr.LoadGameId = units().add(new Command() {

            public void Enter() {
                LoadGame(1);
            }

            public IMotion StopMotionEsc(Object[] arg) {
                return new Motion(1, "Esc") {

                    public void MotionMethod() {
                        SetFocus((Command) (IParametr.StartId));
                        ((Command) (IParametr.Panel1Id)).setVisible(false);
                        ((Command) (IParametr.NewGameId)).setVisible(false);
                        ((Command) (IParametr.LoadGameId)).setVisible(false);
                    }
                };
            }
        });
//        (IParametr.ExitWithSaveId] = new Command() {
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
//                        SetFocus((Command) (IParametr.ExitId]);
//                        ((Command) (IParametr.Panel2Id]).visible = false;
//                        ((Command) (IParametr.ExitWithOutSaveId]).visible = false;
//                        ((Command) (IParametr.ExitWithSaveId]).visible = false;
//                    }
//                };
//            }
//        };
//        (IParametr.ExitWithOutSaveId] = new Command() {
//
//            public void Enter() {
//                ExitToSystem();
//            }
//
//            public IMotion StopMotionEsc(Object[] arg) {
//                return new IMotion(1, "Esc") {
//
//                    public void MotionMethod() {
//                        SetFocus((Command) (IParametr.ExitId]);
//                        ((Command) (IParametr.Panel2Id]).visible = false;
//                        ((Command) (IParametr.ExitWithOutSaveId]).visible = false;
//                    //    ((Command) (IParametr.ExitWithSaveId]).visible = false;
//                    }
//                };
//            }
//        };
    }


    public abstract void StartNewGame();

    public abstract void LoadGame(int slot);

    public abstract void ExitToSystem();
}
