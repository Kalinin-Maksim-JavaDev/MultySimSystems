/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.Build.Panels.Model;

import Application.ILoger;
import Logic.Model.Game.World.PathFindng.IPathMotionReciver;
import Logic.Model.Scenarios.IGameMotionReciver;
import Platform.Core.IArgument;
import Platform.Core.IMotionReciver;

/**
 *
 * @author ASUS
 */
public interface IEnvironment {

    interface ICommon {

        ILoger getLogger();

        void RegBeg(String string);

        void RegEnd(String string);
    }

    interface control extends ICommon {

        IMotionReciver getModelsMotionsReciver();

        IArgument[] ArgToCoor(int[] arg);

        public IGameMotionReciver getGameMotionReciver();

        public IPathMotionReciver getPathMotionReciver();

        
    }
}
