/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Environment.Menu.MainAnimMenu;

import Application.CommandInterface.ICommand;

/**
 *
 * @author dkx6r0c
 */
public interface IMenuCommonCommandsList {

    interface Common extends IMenuCommonCommandsList{

        void EnterToMainMenu(ICommand iCommand);

        void LoadGame(int slot);

        void SaveGame(int slot);

        void ExitToSystem();

        void OpenLoadDialog();

        void OpenSaveDialog();
    }

    interface Main extends Common {

        void StartNewGame();
    }

    interface Internal extends Common {

        void ReturnToGame();

        void ExitToMainMenuFromInternalMenu();
    }
}
