/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Util.Profiler.observercode.Diagram;

import java.awt.Color;
import java.util.ArrayList;
import pphys2d.bodies.PPCircle;
import pphys2d.phys2d.raw.SpringJoint;

/**
 *
 * @author kalinin
 */
public class PPCircle_ extends PPCircle {

    ArrayList<SpringJoint> springJoint;
    ArrayList<PPCircle_> linkTo;
    public String name;
    public int inPutAttempt=0;
    public static float defmass=100;
    public PPCircle_(float radius) {
        super(radius);
        springJoint = new ArrayList<SpringJoint>();
        linkTo = new ArrayList<PPCircle_>();
        this.setMass(defmass);
    }

    Color GetColor() {
        int color = 200 - 1 * springJoint.size();
        return new Color(color, color, color);
    }


}
