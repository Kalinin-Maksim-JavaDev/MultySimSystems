/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Model.Game.World;

import Logic.Model.Game.World.Exployer.Target.ITarget;
import Logic.Model.Game.World.Unit.IBalisticsStrategics;
import Platform.Concurrent.ILathce;
import Render.Graphics.Geometry.IVectorD;

/**
 *
 * @author kalinin
 */
public interface IWalker {

    boolean Arrive(int x, int y);

    boolean ArriveX(int x);

    boolean ArriveX(int x, boolean fromleft);

    boolean ArriveY(int y);

    boolean ArriveY(int y, boolean fromeAbove);

    void Stand();

    void StandX();

    void StandY();

    int GetIntX();

    int GetIntY();

    void SetTarget(ITarget target);

    ITarget GetTarget();

    IBalisticsStrategics getBalisticsStrategy();

    IVectorD getSpeed();

    int getJumpSpeed();

    void stop();

    boolean fromeAbove();

    void TurnBack();

    ILathce onWay();

    void setSelected(boolean value);

    boolean isSelected();

    void setSelected();

    void setUnselected();
}
