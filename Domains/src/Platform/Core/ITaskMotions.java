/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Core;

/**
 *
 * @author kalinin
 */
public interface ITaskMotions extends IMotionReciver{

    public void start();

    public Thread Getblinker();

    public void Resume();

    public void Repeat(boolean repeat);

    public void SetBlinker(Thread thread);

    public void motionSequenceRepeat();

    public void SetMotionSequence(IMotionSequence GetMotionSequence);

    public String GetMotionSec();

    public IMotionSequence GetMotionSequence();

    public void setName(String name);

    public String getName();

}
