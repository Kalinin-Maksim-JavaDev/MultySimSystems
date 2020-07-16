/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.Control;

import Application.Build.Panels.Control.Manipulator.IManipulator;
import Platform.Core.IExecuter;
import Platform.Core.IMotionsProducer;

/**
 *
 * @author kalinin
 */
public interface IControler {

    IManipulator getManipulator();

    IExecuter getExecutor(String name);

    Ready with(IMotionsProducer producer);

    interface Ready {

        IManipulator.binded getManipulator();

        reciving withStartedManipulator(IManipulator.IAction action);

        interface reciving {

            

            boolean keyPress(int device, int keyCode, int[] arg);

            void keyRelease(int device, int keyCode, int[] arg);
        }
    }
}
