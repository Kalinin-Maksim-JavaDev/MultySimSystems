/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Game.World.Unit.Behaviors.Physics;

import Logic.Model.Game.World.Unit.IMotionDriver;
import Logic.Model.Game.World.IWalker;
import Logic.Model.Game.World.Unit.IBalisticsStrategics;
import Model.Geometry.VectorD;
import Platform.Util.Profiler.observercode.Diagram.DataBlock;
import Render.Graphics.Geometry.D2.IVector2d;



/**
 *
 * @author kalinin
 */
public class BalisticsStrategics implements IBalisticsStrategics {

    public IMotionDriver gravity;
    public IMotionDriver jump;
    private double dt;
    public DataBlock data;

    public BalisticsStrategics(DataBlock data, double dt) {
        this.data = data;
        this.dt = dt;
    }
    private boolean landed;

    public void SetFreeFall() {
        gravity = GetGravityMotionProducer();
        jump = GetNonMotionProducer();
        landed = false;
    }

    public void StopFreeFall() {
        gravity = GetNonMotionProducer();
        jump = GetJumpMotionProducer();
        landed = true;
    }

    IMotionDriver GetGravityMotionProducer() {
        return new IMotionDriver() {

            public void Go(IWalker walker) {
                IVector2d speed = (IVector2d) walker.getSpeed();
                speed.setY(speed.getY() - 0.00020 * dt);
            }
        };
    }

    IMotionDriver GetJumpMotionProducer() {
        return new IMotionDriver() {

            public void Go(IWalker walker) {
                // if (speed.y == 0) {
                IVector2d speed = (IVector2d) walker.getSpeed();
                speed.Add((VectorD) data.Get(walker.getJumpSpeed()));
                //  onGrav = false;
                walker.getBalisticsStrategy().SetFreeFall();
            }
        };
    }

    IMotionDriver GetNonMotionProducer() {
        return new IMotionDriver() {

            public void Go(IWalker walker) {
                
            }
        };
    }

    public boolean Landed() {
        return landed;
    }

    public IMotionDriver getGravity() {
        return gravity;
    }

    public IMotionDriver getJump() {
        return jump;
    }
}
