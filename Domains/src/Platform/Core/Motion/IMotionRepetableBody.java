/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Platform.Core.Motion;

/**
 *
 * @author kalinin
 */
public interface IMotionRepetableBody {

    void BeginOperation();

    boolean EndCondition();

    void EndOperation();

}
