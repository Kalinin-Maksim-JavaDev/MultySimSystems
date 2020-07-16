/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.Panels;

import Logic.IGameData;
import Logic.Environment.Menu.MainAnimMenu.IMenuCommonCommandsList;
import Application.Build.Panels.Model.IInteractvePanel;
import Logic.IGameCommonCommandsList;
import Logic.Model.IDataSource;

/**
 *
 * @author dkx6r0c
 */
public interface IPanelsFactory {

    IPanelsFactory setMenuCommonCommandsList(IMenuCommonCommandsList list);

    IPanelsFactory setGameCommonCommandsList(IGameCommonCommandsList list);

    Ready finish();

    interface Ready {

        IDataSource CreateDataSource();

        IInteractvePanel CreateMainMenu();

        IInteractvePanel CreateGame(IGameData data);

        IInteractvePanel CreateGameMenu();

        IInteractvePanel CreateSaveDialog();

        IInteractvePanel CreateLoadDialog();

        IDataSource CreateDataSource(byte[] data);
    }
}
