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
public interface IMotionRepetable extends IMotion {

    void BeginOperation();

    boolean EndCondition();

    void EndOperation();
}
