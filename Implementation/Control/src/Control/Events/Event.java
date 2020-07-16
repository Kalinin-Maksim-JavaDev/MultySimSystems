/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Events;

import Logic.Model.Game.World.Exployer.Target.IEvent;

/**
 *
 * @author kalinin
 */
public abstract class Event implements IEvent {

    protected boolean appl;

    public boolean isAppl() {
        return appl;
    }

    public void setAppl(boolean appl) {
        this.appl = appl;
    }
}
