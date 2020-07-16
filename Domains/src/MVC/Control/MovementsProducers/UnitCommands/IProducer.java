/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.Control.MovementsProducers.UnitCommands;

import Platform.Core.IMotion;

/**
 *
 * @author dkx6r0c
 */
public interface IProducer {

    interface IBasicMoves extends MoveLeft,MoveRight,MoveDown,MoveUp{

}

    interface MoveUp {

        abstract IMotion CreateMotionMoveUp();

        abstract IMotion StopMotionMoveUp();
    }

    interface MoveDown {

        abstract IMotion CreateMotionMoveDown();

        abstract IMotion StopMotionMoveDown();
    }

    interface MoveLeft {

        abstract IMotion CreateMotionMoveLeft();

        abstract IMotion StopMotionMoveLeft();
    }

    interface MoveRight {

        abstract IMotion CreateMotionMoveRight();

        abstract IMotion StopMotionMoveRight();
    }

    interface Rotate {

        abstract IMotion CreateMotionMoveRotate(Object[] arg);

        abstract IMotion StopMotionMoveRotate(Object[] arg);
    }
}
