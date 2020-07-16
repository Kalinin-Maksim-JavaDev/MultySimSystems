/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Model.Scenarios;

import Game.Model.Game.World.Interaction.ICollisionMap;
import Logic.Model.Game.World.Exployer.Target.IPathGuidManager;
import Logic.Model.Game.World.IWalker;
import Logic.Model.Game.World.PathFindng.IPathGuid;
import Logic.Model.Game.World.PathFindng.IPathMotionReciver;
import Logic.Model.Game.World.PathFindng.Path.TrackPoint.ITrackPoint;

/**
 *
 * @author kalinin
 */
public interface IScenario {

    ICollisionMap getMap();

    public IPathGuid getPathGuid();

    public IPathGuidManager createPathGuidManager(IWalker walker, IPathMotionReciver motionReciver, int grad, IPathGuid pathGuid);

    public IPathGuidManager createPathGuidSimpleManager(IWalker walker, ITrackPoint finish_, IPathMotionReciver motionsReciver, int grad, IPathGuid pathGuid);


    public void begin();

    public void end();

    public void addAct(IScenarioAct act);

    public IPathMotionReciver getPathGuidMotionReciver();

    public IScenarioMotionReciver getMotionReciver();

    

    
}
