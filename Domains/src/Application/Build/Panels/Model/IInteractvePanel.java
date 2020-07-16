/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.Build.Panels.Model;

import Application.Panels.IPanelsManager;
import MVC.Control.IInputCollector;
import MVC.View.IViewSource;
import Logic.Model.IDataSource;
import MVC.Control.IControler;
import Platform.Core.IMotion;

/**
 *
 * @author kalinin
 */
public interface IInteractvePanel {

    Window start();

    Window start(IDataSource dataSource);

    interface Window {

        IControler getController();

        IViewSource getPresenter();

        IInputCollector getInputCollector();

        void Pause();

        void Resume();

        boolean PauseResume();

        void Stop();

        int getLayer();

        void AddMotions(IMotion motion);

        Window setCurrentFor(IPanelsManager stack);

        interface Graphical extends Window {

            Object[][] DEBUG_getMap();

           
        }
    }
}
