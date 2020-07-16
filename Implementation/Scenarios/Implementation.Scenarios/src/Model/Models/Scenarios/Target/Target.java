/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Models.Scenarios.Target;

import Logic.Model.Game.World.Exployer.Target.ITarget;
import Logic.Model.Game.World.PathFindng.IPathPointsFactory;
import Platform.Concurrent.Factory;
import Platform.Concurrent.IAtomicInteger;

/**
 *
 * @author kalinin
 */
public abstract class Target implements ITarget {

    protected int n;
    protected IPathPointsFactory pointsFactory;
    private boolean achivied;
    private int subTargetsCount;
    private ITarget superTarget;
    private ITarget[] subTargets;
    private IAtomicInteger complTargetsCount;

    public abstract void WhenAchieved();

    public abstract boolean Condithion();

    public Target(IPathPointsFactory pointsFactory) {
        this.pointsFactory = pointsFactory;
        subTargets = new Target[200];
        complTargetsCount = Factory.newAtomicIntegerS();
    }

    public void AddSubTarget(ITarget sub) {
        sub.setSuperTarget(this);
        sub.setN(subTargetsCount);
        subTargets[subTargetsCount] = sub;
        subTargetsCount++;
    }

    public void SubTargetAchieved(int n) {

        if (complTargetsCount.incrementAndGet_() == subTargetsCount) {
            achivied = true;
            WhenAchieved();
        }
    }

    public void CheckAchieved() {
        if (!achivied) {
            if (Condithion()) {
                if (superTarget != null) {
                    superTarget.SubTargetAchieved(n);
                }
                achivied = true;
                WhenAchieved();
            }
        }
    }

    public boolean isAchivied() {
        return achivied;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setSuperTarget(ITarget target) {
        this.superTarget = target;
    }
}
