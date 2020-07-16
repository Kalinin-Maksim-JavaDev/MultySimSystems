/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Platform.Core.Motion;

import Platform.Core.IMotion;

/**
 *
 * @author kalinin
 */
public class MotionStructure implements IMotionStructure {

    int counter;
    boolean insec;
    String motionName;
    IMotion next;
    IMotion prev;

}
