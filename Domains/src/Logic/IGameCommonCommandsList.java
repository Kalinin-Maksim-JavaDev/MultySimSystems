/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Application.CommandInterface.ICommand;

/**
 *
 * @author dkx6r0c
 */
public interface IGameCommonCommandsList {

    void ExitToMainMenuFromGame(ICommand calling);

    void ExitToMainMenuFromGame();

    void OpenGameMenu();

    void LoadGame(int slot);

    void SaveGame(int slot);
}
