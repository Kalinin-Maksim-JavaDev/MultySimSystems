/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Models.Scenarios;

import Game.Model.Game.IRatio;
import Game.Model.Game.World.Interaction.ICollisionMap;
import Logic.Game.World.PathFindng.Guid.PathGuid;
import Logic.Game.World.PathFindng.Guid.PathGuidManager;
import Logic.Model.Scenarios.IScenarioMotionReciver;
import Logic.Model.Scenarios.IScenario;
import Logic.Model.Scenarios.IScenarioAct;
import Model.Models.Scenarios.Acts.ScenarioAct;
import Logic.Model.Game.World.Exployer.Target.IPathGuidManager;
import Logic.Model.Game.World.IWalker;
import Logic.Model.Game.World.PathFindng.IPathGuid;
import Logic.Model.Game.World.PathFindng.IPathMotionReciver;
import Logic.Model.Game.World.PathFindng.Path.TrackPoint.ITrackPoint;
import Model.Models.PathGuidSimpleManager;
import Platform.Core.IMotion;
import Platform.Core.IMotionBody;
import Platform.Core.IMotionTemplate;
import Platform.Core.ISystemM;
import Platform.Core.ISystemOwner;
import Platform.Core.Motion.MotionFactory;
import Platform.Core.MotionsDispatcher;
import Platform.Core.Systems.SystemFactory;
import Platform.ILogCanvas;

/**
 *
 * @author kalinin
 */
abstract class SystemMScenario implements ISystemOwner, IScenario, IScenarioMotionReciver {

    public IPathGuid pathGuid;
    public ICollisionMap iMap;
    ScenarioAct motion;
    ISystemM sys;
    IPathMotionReciver pathMotionReciver;

    protected abstract ScenarioAct createMainAct();

    public abstract void OnStop();

    protected abstract boolean find(int x1, int y1, int x2, int y2);

    SystemMScenario(ICollisionMap iMap, IPathMotionReciver pathMotionReciver, IRatio ratio, ILogCanvas logCanvas) {

        sys = SystemFactory.CreateSystemM("A\t\t\t\t\t\t\t\t\t|", SystemMScenario.this);

        pathGuid = (IPathGuid) new PathGuid(iMap, ratio, logCanvas);
        ((PathGuid) pathGuid).Start();

        this.iMap = iMap;
        this.pathMotionReciver = pathMotionReciver;
        //   this.autorepeat=true;
    }

    public String SetThreadName() {
        return "scenario";
    }

    public ICollisionMap getMap() {
        return iMap;
    }

    public void begin() {
        addAct(createMainAct());
    }

    public void addAct(IScenarioAct act) {

        MotionsDispatcher.addMotion(this, act.getMotion());
    }

    @Override
    public void end() {
        motion.finish();
        OnStop();
        sys.Stop();
    }

    public IPathGuid getPathGuid() {
        return pathGuid;
    }

    public IPathGuidManager createPathGuidManager(IWalker walker, IPathMotionReciver motionReciver, int grad, IPathGuid pathGuid) {
        return new PathGuidManager(walker, motionReciver, grad, pathGuid, null);
    }

    public IPathGuidManager createPathGuidSimpleManager(IWalker walker, ITrackPoint finish_, IPathMotionReciver motionsReciver, int grad, IPathGuid pathGuid) {
        return new PathGuidSimpleManager(walker, motionsReciver, grad, pathGuid);
    }

    public IScenarioMotionReciver getMotionReciver() {
        return this;
    }

    public IPathMotionReciver getPathGuidMotionReciver() {
        return pathMotionReciver;
    }

    public void Start() {
        sys.Start();
    }

    public void AddMotions(IMotion motion) {
        sys.AddMotions(motion);
    }

    public void AddMotions(String name, IMotionBody command) {
        sys.AddMotions(MotionFactory.newMotion(1, name, command));
    }

    public void AddMotions(IMotionTemplate template) {
        sys.AddMotions(MotionFactory.newMotion(template));
    }

    public void Resume() {
        sys.Resume();
    }

    public void Stop() {
        sys.Stop();
    }
}
