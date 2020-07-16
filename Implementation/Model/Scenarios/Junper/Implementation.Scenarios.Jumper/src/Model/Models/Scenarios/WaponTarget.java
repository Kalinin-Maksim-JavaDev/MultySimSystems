package Model.Models.Scenarios;

import Logic.Model.Game.World.IWalker;
import Logic.Model.Game.World.PathFindng.IPathPointsFactory;
import Logic.Model.Game.World.PathFindng.Path.TrackPoint.ITrackPoint;
import Logic.Model.IWaponMotionReciver;
import Model.Models.Scenarios.Acts.GoToFlat;
import Platform.Core.IArgument;
import Platform.Core.Motion.Motion;
import Model.Models.Scenarios.Target.WalkerTarget;
import Platform.Core.MotionsDispatcher;

public class WaponTarget extends WalkerTarget {

    final GoToFlat scenarioGoToFlat;
    private IWaponMotionReciver motionReciver;

    public WaponTarget(IWalker agent_, ITrackPoint pos_, GoToFlat scenarioGoToFlat, IPathPointsFactory pointsFactory,
            IWaponMotionReciver motionReciver) {
        super(agent_, pos_, pointsFactory);
        this.scenarioGoToFlat = scenarioGoToFlat;
        this.motionReciver = motionReciver;
    }

    public void WhenAchieved() {
        System.out.println(n + " achieved...");
        MotionsDispatcher.addMotion(motionReciver,new Motion(1, "openFair") {

            public void MotionMethod() {
                //    agent.OpenFair();
            }
        });
        motionReciver.Resume();
        IArgument[] arg = new IArgument[2];
        scenarioGoToFlat.mainTarget.setX(walker.GetIntX());
        scenarioGoToFlat.mainTarget.setY(walker.GetIntY() + 1);
    }

    public boolean Condithion() {
        return (walker).Arrive(position.getX(), position.getY() + 1);
    }
}
