package Application.CommandInterface.Strategy;

import Application.CommandInterface.Command;
import Application.Build.Control.Command.IComandsDispatching;
import Application.Build.Control.Command.ICommandMediator;
import Application.CommandInterface.ICommandStack;
import Platform.Core.ISystemM;
import Platform.Core.Motion.Motion;
import Application.CommandInterface.ICommand;
import Platform.Core.Systems.SystemFactory;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalinin
 */
public abstract class SerialComandsDispatching implements ICommandMediator.commandsProxy,IComandsDispatching {

    private ICommandMediator.commandsReciver commandsReciver;

    private CommandStack commandStack = new CommandStack();
    
    public SerialComandsDispatching(ICommandMediator.commandsReciver commandsReciver) {
        this.commandsReciver = commandsReciver;
    }

    private ICommandMediator.commandsReciver getCommandsReciver() {
        return commandsReciver;
    }

    public void EnterToMainMenu(final ICommand calling) {
        commandStack.push(new Command() {

            ICommand _calling = calling;

            public void perform() {
                getCommandsReciver().EnterToMainMenu(_calling);
            }
        });
    }

    public void ReturnToGame() {
        commandStack.push(new Command() {

            public void perform() {
                ReturnToGame();
            }
        });
    }

    public void ExitToMainMenuFromInternalMenu() {

        commandStack.push(new Command() {

            public void perform() {
                getCommandsReciver().ExitToMainMenuFromInternalMenu();
            }
        });
    }

    public void StartNewGame() {

        commandStack.push(new Command() {

            public void perform() {
                getCommandsReciver().StartNewGame();
            }
        });
    }

    public void ExitToSystem() {
        commandStack.push(new Command() {

            public void perform() {
                getCommandsReciver().ExitToSystem();
            }
        });
    }

    public void ExitToMainMenuFromGame() {
        commandStack.push(new Command() {

            public void perform() {
                getCommandsReciver().ExitToMainMenuFromGame();
            }
        });
    }

    public void ExitToMainMenuFromGame(final ICommand calling) {
        commandStack.push(new Command() {

            ICommand _calling = calling;

            public void perform() {
                getCommandsReciver().ExitToMainMenuFromGame(_calling);
            }
        });
    }

    public void OpenGameMenu() {
        commandStack.push(new Command() {

            public void perform() {
                getCommandsReciver().OpenGameMenu();
            }
        });
    }

    public void OpenSaveDialog() {
        commandStack.push(new Command() {

            public void perform() {
                getCommandsReciver().OpenSaveDialog();
            }
        });
    }

    public void OpenLoadDialog() {
        commandStack.push(new Command() {

            public void perform() {
                getCommandsReciver().OpenLoadDialog();
            }
        });
    }

    public void SaveGame(final int slot) {

        commandStack.push(new Command() {

            int _slot = slot;

            public void perform() {
                getCommandsReciver().SaveGame(_slot);
            }
        });
    }

    public void LoadGame(final int slot) {

        commandStack.push(new Command() {

            int _slot = slot;

            public void perform() {
                getCommandsReciver().LoadGame(_slot);
            }
        });
    }

    public ISystemM unwanted_getPerfomerSystem() {
        return commandStack.sys;
    }

    private static class CommandStack implements ICommandStack {

        ISystemM sys;

        public CommandStack() {
            sys = SystemFactory.CreateSystemM("GameManager", "CommandStack");
            sys.Start();
        }

        public void push(ICommand сommand) {
            sys.AddMotions(new MotionCommandPerform(сommand));
            sys.Resume();
        }

        private class MotionCommandPerform extends Motion {

            private ICommand сommand;

            public MotionCommandPerform(ICommand сommand) {
                super(1, "call");
                this.сommand = сommand;
            }

            @Override
            public void MotionMethod() {
                сommand.perform();
            }
        }
    }
}
