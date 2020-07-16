/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Environment.Motions;

import Logic.Model.Scenarios.IGameMotionReciver;
import Logic.Environment.Motions.IMotionReciversDispatcer;
import Logic.Model.Game.World.PathFindng.IPathMotionReciver;
import Platform.Core.IMotion;
import Platform.Core.IMotionReciver;

/**
 *
 * @author dkx6r0c
 */
public class MotionReciversDispatcer implements IMotionReciversDispatcer {

    IMotionReciver reciver;
    IPathMotionReciver pathMotionReciver;
    IGameMotionReciver gameMotionReciver;

    public MotionReciversDispatcer(IMotionReciver reciver) {
        this.reciver = reciver;


        gameMotionReciver = new IGameMotionReciver() {

            public void AddMotions(IMotion motion) {
                MotionReciversDispatcer.this.reciver.AddMotions(motion);
            }

            public void Resume() {
                MotionReciversDispatcer.this.reciver.Resume();
            }
        };

        pathMotionReciver = new IPathMotionReciver() {

            public void AddMotions(IMotion motion) {
                MotionReciversDispatcer.this.reciver.AddMotions(motion);
            }

            public void Resume() {
                MotionReciversDispatcer.this.reciver.Resume();
            }
        };
    }

    public IMotionReciver getMotionReciver() {
        return reciver;
    }
    public IPathMotionReciver getPathMotionReciver() {
        return pathMotionReciver;
    }

    public IGameMotionReciver getGameMotionReciver() {
        return gameMotionReciver;
    }

}
