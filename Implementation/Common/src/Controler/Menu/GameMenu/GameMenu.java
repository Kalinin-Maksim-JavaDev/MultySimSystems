/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler.Menu.GameMenu;

import Logic.Environment.Menu.MainAnimMenu.IMenuCommonCommandsList;
import Platform.Core.Motion.Motion;
import Collaborations.MVC.Control.Menu;
import Platform.Core.IMotion;
import Global.Tools;
import Platform.Core.IMotionReciver;

/**
 *
 * @author kalinin
 */
public abstract class GameMenu extends Menu {

    abstract protected IMenuCommonCommandsList.Internal getCommandList();

    public GameMenu(IMotionReciver reciver) {
        super(reciver);


    }

    public void SetUnits() {
        IGParametr.MainMenuId = units().add(new Command() {

            public void Enter() {
                reciver.AddMotions(new Motion(1, "Go") {

                    public void MotionMethod() {
                        if (isFinal()) {
                            SetFocus((Command) (IGParametr.ResumeId));
                            getCommandList().ExitToMainMenuFromInternalMenu();
                        }
                    }
                });
            }

            public IMotion StopMotionEsc(Object[] arg) {
                return new MotionResumeGame();
            }
        });

        IGParametr.ResumeId = units().add(new Command() {

            public void Enter() {
                reciver.AddMotions(new Motion(1, "Go") {

                    public void MotionMethod() {
                        if (isFinal()) {
                            SetFocus((Command) (IGParametr.ResumeId));
                            getCommandList().ReturnToGame();
                        }
                    }
                });
            }

            public IMotion StopMotionEsc(Object[] arg) {
                return new MotionResumeGame();
            }
        });
        IGParametr.OpenSaveId = units().add(new Command() {

            public void Enter() {
                reciver.AddMotions(new Motion(1, "Go") {

                    public void MotionMethod() {
                        if (isFinal()) {
                            SetFocus((Command) (IGParametr.ResumeId));
                            getCommandList().OpenSaveDialog();
                        }
                    }
                });
            }

            public IMotion StopMotionEsc(Object[] arg) {
                return new MotionResumeGame();
            }
        });
        IGParametr.OpenLoadId = units().add(new Command() {

            public void Enter() {
                reciver.AddMotions(new Motion(1, "Go") {

                    public void MotionMethod() {
                        if (isFinal()) {
                            SetFocus((Command) (IGParametr.ResumeId));
                            getCommandList().OpenLoadDialog();
                        }
                    }
                });
            }

            public IMotion StopMotionEsc(Object[] arg) {
                return new MotionResumeGame();
            }
        });

        IGParametr.AmbientIndex = units().add(new Command() {

            public void Enter() {

                Tools.UnsupportedOperationException();
            }

            public IMotion StopMotionEsc(Object[] arg) {

                Tools.UnsupportedOperationException();
                return null;

            }
        });

        ((Command) (IGParametr.ResumeId)).LinkTo((Command) (IGParametr.OpenSaveId));
        ((Command) (IGParametr.OpenSaveId)).LinkTo((Command) (IGParametr.OpenLoadId));
        ((Command) (IGParametr.OpenLoadId)).LinkTo((Command) (IGParametr.MainMenuId));
        ((Command) (IGParametr.MainMenuId)).LinkTo((Command) (IGParametr.ResumeId));

        ((Command) (IGParametr.MainMenuId)).setVisible(true);
        ((Command) (IGParametr.ResumeId)).setVisible(true);
        ((Command) (IGParametr.OpenSaveId)).setVisible(true);
        ((Command) (IGParametr.OpenLoadId)).setVisible(true);
        ((Command) (IGParametr.AmbientIndex)).setVisible(true);

        SetFocus((Command) (IGParametr.ResumeId));

    }

//    public void Start(StartStopPauseSystem s) {
//
//    }
    class MotionResumeGame extends Motion {

        public MotionResumeGame() {
            super(1, "ResumeGame");
        }

        public void MotionMethod() {

            getCommandList().ReturnToGame();
        }
    }
}
