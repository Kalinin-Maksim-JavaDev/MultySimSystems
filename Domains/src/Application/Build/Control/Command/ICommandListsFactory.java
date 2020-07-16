/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.Build.Control.Command;

import Application.Panels.IPanelsStack;
import Logic.Environment.Menu.MainAnimMenu.IMenuCommonCommandsList;
import Logic.IGameCommonCommandsList;

/**
 *
 * @author kalinin
 */
public interface ICommandListsFactory {

       //IComandsDispatching getDispatching();

//    IPanelsStack getPanelsStack();

    IMenuCommonCommandsList createMenuCommonCommandsList();

    IGameCommonCommandsList createGameCommonCommandsList();
}
