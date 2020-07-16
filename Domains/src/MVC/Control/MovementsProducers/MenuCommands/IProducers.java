/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.Control.MovementsProducers.MenuCommands;

import Platform.Core.IMotion;

/**
 *
 * @author dkx6r0c
 */
public interface IProducers {

    
    interface Enter{

        IMotion CreateMotionEnter(Object[] arg);

        IMotion StopMotionEnter(Object[] arg);
    }

    interface Esc{

        IMotion CreateMotionEsc(Object[] arg);

        IMotion StopMotionEsc(Object[] arg);
    }

    interface NextCommand {

        IMotion CreateMotionNextCommand(Object[] arg);

        IMotion StopMotionNextCommand(Object[] arg);
    }

    interface PreviosCommand {

        IMotion CreateMotionPreviosCommand(Object[] arg);

        IMotion StopMotionPreviosCommand(Object[] arg);
    }
}
