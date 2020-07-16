/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Platform.Util.Profiler.observercode.Diagram;

import pphys2d.phys2d.math.ROVector2f;
import pphys2d.phys2d.raw.Body;
import pphys2d.phys2d.raw.SpringJoint;

/**
 *
 * @author kalinin
 */
public class SpringJoint_ extends SpringJoint{

    public int strong;
    //int color;
    public static float stretchedSpringConst=100;
    public SpringJoint_(Body b1, Body b2, ROVector2f anchor1, ROVector2f anchor2) {
        super(b1, b2, anchor1, anchor2);
    }

}
