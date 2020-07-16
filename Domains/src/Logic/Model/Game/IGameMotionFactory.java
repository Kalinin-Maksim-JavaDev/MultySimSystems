package Logic.Model.Game;

import Logic.Model.Game.IGame.unit;
import Platform.Core.IArgument;
import Platform.Core.IExecuter;
import Platform.Core.IMotion;
import Platform.Core.IMotionsProducer;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dkx6r0c
 */
public interface IGameMotionFactory extends IMotionsProducer {

    IMotion newMotionGameSave(final IArgument[] arg);

    IMotion newMotionGameLoad(final IArgument[] arg);

    IMotion newMotionMoveWithSpeedAndGravity(int iterates_, unit gr_);

    IMotion NewMotionGamePauseResume(int i, String string, IExecuter ee_, IArgument[] arg);
}
