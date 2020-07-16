/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Core.Systems;

import Platform.Core.ISystemM;
import Platform.Core.ISystemOwner;

/**
 *
 * @author kalinin
 */
public class SystemFactory {

    public static ISystemM CreateSystemM(String name, final ISystemOwner wrap) {
        return new SystemM(name) {

            public String SetThreadName() {
                return wrap.SetThreadName();
            }
        };
    }

    public static ISystemM CreateSystemM(String name, final String threadName) {
        return new SystemM(name) {

            String _threadName = threadName;

            @Override
            public String SetThreadName() {
                return _threadName;
            }
        };
    }
}
