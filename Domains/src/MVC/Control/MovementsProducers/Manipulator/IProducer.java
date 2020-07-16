/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.Control.MovementsProducers.Manipulator;

import Platform.Core.IArgument;
import Platform.Core.IMotion;
import Platform.Core.IMotionsProducer;

/**
 *
 * @author dkx6r0c
 */
public interface IProducer {

    interface IMouseMoveProducer extends IMotionsProducer {

        IMotion MoveMouse(IArgument[] arg);

        IMotion StopMoveMouse(IArgument[] arg);
    }

    interface IMouseSelectProducer extends IMotionsProducer {

        IMotion MouseSelect(IArgument[] arg);

        IMotion StopMouseSelect(IArgument[] arg);
    }

    interface IMouseSelectingProducer extends IMotionsProducer {

    abstract IMotion MouseSelecting(IArgument[] arg);

    abstract IMotion StopMouseSelecting(IArgument[] arg);
}
     interface IMouseRightClickProducer extends IMotionsProducer {

    abstract IMotion MouseClickRight(IArgument[] arg);

    abstract IMotion StopMouseClickRight(IArgument[] arg);
}

     interface IMouseLeftClickProducer extends IMotionsProducer {

    abstract IMotion MouseClickLeft(IArgument[] arg);

    abstract IMotion StopMouseClickLeft(IArgument[] arg);
}
}
