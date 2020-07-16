/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Logic.Model.Game.World.Unit;

/**
 *
 * @author kalinin
 */
public interface IBalisticsStrategics {

    public void SetFreeFall();

    public boolean Landed();

    public IMotionDriver getJump();

    public IMotionDriver getGravity();

    public void StopFreeFall();

}
