/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Core;

/**
 *
 * @author kalinin
 */
public interface IMotion {

    void MotionMethod();

    void setNext(IMotion next);

    void setPrev(IMotion prev);

    void ativate();

    void deativate();

    boolean isActive();

    int getCounter();

    IMotion getNext();

    IMotion getPrev();

    /**
     * @return the motionName
     */
    String getMotionName();

    boolean Iterate();

    /**
     * @param motionName the motionName to set
     */
    void setMotionName(String motionName);

    boolean isFinal();

    void incCounter();

    void setCounter(int value);

    IMotionReciver add(IMotionReciver reciver);

    void useNext(IMotion motion);

    IMotionReciver getReciver();
}
