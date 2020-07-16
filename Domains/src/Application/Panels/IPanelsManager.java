package Application.Panels;

import Application.Build.Control.Command.ICommandMediator;
import Application.Build.Control.VirtualPad.IVrtualPadMotionsReciver;
import Application.Build.Panels.Model.IInteractvePanel;
import Application.CommandInterface.ICommand;
import Application.IApplication;
import Platform.Core.IMotion;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalinin
 */
public interface IPanelsManager {

    void setCurrentPanel(IInteractvePanel.Window window);

    binded bind(IPanelsFactory.Ready factory,IApplication.events applEvents);

    interface binded {

        IInteractvePanel.Window StartFromMainMenu(ICommand calling);

        IInteractvePanel.Window getCurrentPanel();

        void unwanted_AddMotions(IMotion motion);

        IVrtualPadMotionsReciver getVrtualPadMotionsReciver();

        Object[][] getMap();

        ICommandMediator getCommandPerformer();
    }
}
