/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Core;

import Global.Tools;
import Logic.Model.Scenarios.IGameMotionReciver;
import Logic.Model.Game.World.PathFindng.IPathMotionReciver;
import Logic.Model.IWaponMotionReciver;
import Logic.Model.Scenarios.IScenarioMotionReciver;

/**
 *
 * @author kalinin
 */
public class MotionsDispatcher {

    static public void addMotion(IMotionReciver reciver, IMotion motion) {

        Tools.UnsupportedOperationException();
    }

    static public void addMotion(IGameMotionReciver reciver, IMotion motion) {

        reciver.AddMotions(motion);
    }

    static public void addMotion(IScenarioMotionReciver reciver, IMotion motion) {

        reciver.AddMotions(motion);
    }

    public static void addMotion(IPathMotionReciver reciver, IMotion motion) {

        reciver.AddMotions(motion);
    }

    public static void addMotion(IWaponMotionReciver reciver, IMotion motion) {

        reciver.AddMotions(motion);
    }
}
