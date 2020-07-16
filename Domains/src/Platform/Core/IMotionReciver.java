/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Core;

/**
 *
 * @author kalinin
 */
public interface IMotionReciver {

    void AddMotions(IMotion motion);

    void Resume();
}
