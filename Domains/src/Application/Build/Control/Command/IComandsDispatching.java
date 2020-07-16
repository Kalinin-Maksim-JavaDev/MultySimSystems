/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.Build.Control.Command;

import Platform.Core.ISystemM;

/**
 *
 * @author kalinin
 */
public interface IComandsDispatching extends ICommandMediator.commandsProxy{

   // ICommandPerformer.manager getCommandPerformer();

    public ISystemM unwanted_getPerfomerSystem();


}
