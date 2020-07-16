/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.Build.Control.Command;

import Application.CommandInterface.ICommand;
import Logic.Environment.Menu.MainAnimMenu.IMenuCommonCommandsList;
import Logic.IGameCommonCommandsList;

/**
 *
 * @author kalinin
 */
public interface ICommandMediator {

    

    commandsProxy getСommandsProxy();

    commandsReciver getCommandsReciver();

   

    interface commandsReciver extends IMenuCommonCommandsList.Main, IMenuCommonCommandsList.Internal, IGameCommonCommandsList {
    }

    interface commandsProxy extends commandsReciver {
    }
}
