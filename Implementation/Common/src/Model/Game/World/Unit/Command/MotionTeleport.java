/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Game.World.Unit.Command;

import Platform.Core.Motion.Motion;
import Model.Game.World.Unit.GameUnit;

/**
 *
 * @author Adm
 */
public class MotionTeleport extends Motion {

    final GameUnit unit;
    final int x;
    final int y;

    public MotionTeleport(int x_, int y_, GameUnit unit_) {
        super(1, "Teleport");
        unit = unit_;
        x = x_;
        y = y_;
    }

    public void MotionMethod() {
        unit.game.getCollisionMap().PreMoveUnit(unit.as2Dbodie(), x, y);
    }
}
