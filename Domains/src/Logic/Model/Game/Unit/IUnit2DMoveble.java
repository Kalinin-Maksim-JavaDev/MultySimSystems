/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Model.Game.Unit;

import Logic.Model.Game.World.Collisions.IBodie;
import Logic.Model.Game.World.IWalker;
import MVC.Model.IUnit2D;

/**
 *
 * @author kalinin
 */
public interface IUnit2DMoveble extends IUnit2D  {

    bodie2D as2Dbodie();

    interface bodie2D extends IBodie {

        void PreMoveUnit(int x, int y);        
    }

    asWalker asWalker();

    interface asWalker extends IWalker {
    }
}
