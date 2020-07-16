/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.Build.Panels.Control.Manipulator;

import MVC.Control.MovementsProducers.Manipulator.IProducer.IMouseLeftClickProducer;
import MVC.Control.MovementsProducers.Manipulator.IProducer.IMouseMoveProducer;
import MVC.Control.MovementsProducers.Manipulator.IProducer.IMouseRightClickProducer;
import MVC.Control.MovementsProducers.Manipulator.IProducer.IMouseSelectProducer;
import MVC.Control.MovementsProducers.Manipulator.IProducer.IMouseSelectingProducer;
import Platform.Core.IArgument;
import Platform.Core.IExecuter;
import Platform.Core.IMotionsProducer;

/**
 *
 * @author kalinin
 */
public interface IManipulator extends IMouseLeftClickProducer, IMouseRightClickProducer, IMouseSelectingProducer, IMouseSelectProducer, IMouseMoveProducer {

    interface IAction {

        void Abort(IArgument[] arg_);

        void Do(IArgument[] arg_);

        void Move(IArgument[] arg_);

        void Select(IArgument[] arg_);

        void Selecting(IArgument[] arg_);

        void setManipulator(IManipulator manipulator);
    }

    binded bindAction(IAction action);

    interface binded extends IMotionsProducer {

        binded setProducer(IExecuter executer);

        void Start();

        interface started {

            void Stop();
        }
    }
}
