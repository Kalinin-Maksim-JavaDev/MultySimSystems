/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.CommandInterface;

import Application.Build.Control.Command.ICommandListsFactory;
import Application.Build.Control.Command.ICommandMediator;
import Logic.Environment.Menu.MainAnimMenu.IMenuCommonCommandsList;
import Logic.IGameCommonCommandsList;

/**
 *
 * @author kalinin
 */
public abstract class CommandListsFactory implements ICommandListsFactory {

    private ICommandMediator.commandsReciver commandsReciver;

    public CommandListsFactory(ICommandMediator.commandsProxy dispatching) {
        this.commandsReciver = dispatching;
    }

   /* public IComandsDispatching dispatching {
        return dispatching;
    }*/

    public IMenuCommonCommandsList createMenuCommonCommandsList() {
        return new IMenuCommonCommandsList.Main() {

            public void ExitToSystem() {
                commandsReciver.ExitToSystem();
            }

            public void SaveGame(int slot) {
                commandsReciver.SaveGame(slot);
            }

            public void StartNewGame() {
                commandsReciver.StartNewGame();
            }

            public void EnterToMainMenu(ICommand iCommand) {
                  commandsReciver.EnterToMainMenu(iCommand);
            }

            public void LoadGame(int slot) {
                commandsReciver.LoadGame(slot);
            }

            public void OpenLoadDialog() {
                commandsReciver.OpenLoadDialog();
            }

            public void OpenSaveDialog() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    public IGameCommonCommandsList createGameCommonCommandsList() {
        return new IGameCommonCommandsList() {

            public void LoadGame(int slot) {
                commandsReciver.LoadGame(slot);
            }

            public void SaveGame(int slot) {
                commandsReciver.SaveGame(slot);
            }

            public void ExitToMainMenuFromGame(ICommand calling) {
                commandsReciver.ExitToMainMenuFromGame(calling);
            }

            public void ExitToMainMenuFromGame() {
                commandsReciver.ExitToMainMenuFromGame();
            }

            public void OpenGameMenu() {
                commandsReciver.OpenGameMenu();
            }
        };
    }
}
