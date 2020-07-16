/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Platform.Core;

/**
 *
 * @author kalinin
 */
public interface IMotionSequence extends IMotionReciver {

    boolean TryDelMotions(IMotion motion_);

    void Repeat_();

    void SetCurrent(IMotion motion);

    public IMotion getCurrent();

    public IMotion getNext();


}
