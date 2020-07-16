/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Game.World.Unit.MovementsAlgoritms;

import Game.Model.Game.IRatio;

/**
 *
 * @author Adm
 */
public abstract class AlgMove {

    

    protected IRatio ratio;

    public AlgMove(IRatio ratio) {
        this.ratio = ratio;
    }



    public abstract void CalculatePosition();
}
