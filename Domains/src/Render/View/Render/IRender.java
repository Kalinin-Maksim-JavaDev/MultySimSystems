/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Render.View.Render;

import Platform.Core.IExecuter;
import Platform.Core.IMotion;
import Platform.Core.IMotionReciver;

/**
 *
 * @author kalinin
 */
public interface IRender extends IMotionReciver {

    public void Start();

    public void Stop();

    public void Resume();

    public IExecuter getCommandExecuter(int id);

    public void motionSequenceRepeat();
}
