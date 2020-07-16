/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Platform.Concurrent;

import Platform.Concurrent.IMonitor;

/**
 *
 * @author kalinin
 */
public class LathceStructure {

    
    boolean allowedDoubleRelease;
    protected boolean locked;
    String master = "";
    String ownerThreadName = "";
    protected IMonitor monitor;
}
