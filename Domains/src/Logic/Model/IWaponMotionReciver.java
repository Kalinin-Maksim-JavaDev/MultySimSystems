/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Logic.Model;

import Platform.Core.IMotion;

/**
 *
 * @author kalinin
 */
public interface IWaponMotionReciver {

    void AddMotions(IMotion motion);

    void Resume();
}
