/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Platform.Concurrent;

/**
 *
 * @author kalinin
 */
public class LathceStop extends Lathce implements ILathceStop {

    public boolean isContinue() {
        return Continue;
    }

    public void setContinue(boolean Continue) {
        this.Continue = Continue;
    }

        boolean Continue;

        public LathceStop(boolean initState_) {
            super(initState_, false);
        }

        public void Stop() {
            Continue = false;
            locked = true;
        }
    }
