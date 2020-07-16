/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Game;

import Application.Build.Panels.Model.IEnvironment;
import Logic.Model.Game.ICommonGameEventHandler;
import MVC.Control.IControler;
import MVC.Model.IDomainModel;
import Model.Models.Jumper.Jumper;
import Model.Models.PingPong.PingPong;
import Model.Models.Tetris.Tetris;
import Application.CommandInterface.Command;
import Application.Build.Panels.Model.IInteractvePanel;
import Global.Tools;
import Logic.IGameCommonCommandsList;
import Platform.Core.IArgument;
import Platform.Core.IMotion;
import Platform.Core.Motion.Executer;
import Platform.Core.Motion.Motion;
import Platform.ILogCanvas;
import Presenter.Platformer.JParametr;
import View.Presenter.Projections.IViewUnit.imaginated;
import View.Presenter.Projections.IViewUnit.states;

/**
 *
 * @author kalinin
 */
public class GameFabric {

    static public enum unitsType {

        fundament, jumper, ball, granate, fundament2;

        static public unitsType get(int id) {

            unitsType result = null;
            if (id == 1) {
                result = fundament;
            } else if (id == 2) {
                result = jumper;
            } else if (id == 3) {
                result = ball;
            } else if (id == 4) {
                result = granate;
            } else if (id == 5) {
                result = fundament2;
            }

            if ((result == null) || (result.getId() != id)) {
                Tools.UnsupportedOperationException();
            }
            return result;
        }

        public int getId() {
            int result = -1;
            if (this == fundament) {
                result = 1;
            } else if (this == jumper) {
                result = 2;
            } else if (this == ball) {
                result = 3;
            } else if (this == granate) {
                result = 4;
            } else if (this == fundament2) {
                result = 5;
            }

            return result;
        }
    }

    public static IDomainModel CreateJumper(IEnvironment.control environment, final IGameCommonCommandsList _commandsList, final IInteractvePanel.Window _panel, ILogCanvas logCanvas_) {
        final Jumper jumper = new Jumper(environment, logCanvas_) {

            public String SetThreadName() {
                return "GameMOD";
            }

            public ICommonGameEventHandler getEventsHandler() {
                return new ICommonGameEventHandler() {

                    IGameCommonCommandsList commandsList = _commandsList;
                    IInteractvePanel.Window panel = _panel;

                    public void Win() {
                        commandsList.ExitToMainMenuFromGame(new Command() {

                            public void perform() {
                                panel.getPresenter().reflect((imaginated) JParametr.WinSceneID, states.zero);
                            }
                        });
                    }

                    public void Over() {
                        commandsList.ExitToMainMenuFromGame(new Command() {

                            public void perform() {
                                panel.getPresenter().reflect((imaginated) JParametr.EndSceneID, states.zero);
                            }
                        });
                    }
                };
            }

            public void PATCH_setUp(IControler controler) {
                _panel.getController().getExecutor("Esc").setNext(new Executer() {

                    IGameCommonCommandsList commandsList = _commandsList;

                    public IMotion CreateMotion(IArgument[] arg) {
                        return new Motion(1, "OpenGameMenu") {

                            @Override
                            public void MotionMethod() {

                                if (!isCalculated()) {
                                    commandsList.OpenGameMenu();
                                }
                            }
                        };
                    }

                    public IMotion StopMotion(IArgument[] arg) {
                        return null;
                    }
                });
            }
        };
        return jumper;
    }

    public static IDomainModel CreateTetris(IEnvironment.control environment, ILogCanvas logCanvas_) {
        return new Tetris(environment, logCanvas_) {

            public String SetThreadName() {
                return "GameMOD";
            }

            public ICommonGameEventHandler getEventsHandler() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void PATCH_setUp(IControler controler) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    public static IDomainModel CreatePingPong(IEnvironment.control environment, ILogCanvas logCanvas_) {
        return new PingPong(environment, logCanvas_) {

            public String SetThreadName() {
                return "GameMOD";
            }

            public ICommonGameEventHandler getEventsHandler() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void PATCH_setUp(IControler controler) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
}
