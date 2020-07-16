/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Game.World.PathFindng.Guid;

import Logic.Game.World.PathFindng.Path.Path;
import Logic.Model.Game.World.Collisions.IBodie;
import Logic.Model.Game.World.IAgent;
import Logic.Model.Game.World.IWalker;
import Logic.Model.Game.World.PathFindng.IWalkAdviser;
import Logic.Model.Game.World.PathFindng.Path.TrackPoint.ITrackPoint;
import Platform.ILogCanvas;

/**
 *
 * @author kalinin
 */
public class WalkerPath extends Path {
    private IBodie[][] map;
    private ILogCanvas log;
    private IWalker walker;
    private IWalkAdviser adviser;
    //final private IGameSystem game;

    public WalkerPath(int fallLimit_, IBodie[][] map, int mapW_, int mapH_, ILogCanvas log) {
        super(fallLimit_, mapW_, mapH_);
        this.map = map;
        this.log = log;
        //game.getInteractive().getCollisionMap().preCompliteUnitsMap(0),
        //game.getGrad()
    }

    public void CalculatePath(ITrackPoint start_, ITrackPoint target_, IWalker walker, IWalkAdviser rule) {
        this.walker = walker;
        this.adviser = rule;
        super.CalculatePath(start_, target_);
    }

    public void DrawCells() {
    }

    public void RefreshGraphics() {
        log.Refresh();
    }

    public void drawLine(int x1, int y1, int x2, int y2, int r, int g, int b) {
        if (r == 0) {
            log.drawLine(x1, y1, x2, y2, r, g, b);
        }
    }

    public boolean IsMapsCellFree(int x_, int y_) {
        IBodie unit = map[y_][x_];
        if (unit == null) {
            return true;
        } else {
            if (unit instanceof IAgent) {
                IAgent agent = (IAgent) unit;
                if (agent == walker) {
                    return false;
                } else {
                    return adviser.canWalkThrough(agent);
                }
            } else {
                return false;
            }
        }
    }
}
