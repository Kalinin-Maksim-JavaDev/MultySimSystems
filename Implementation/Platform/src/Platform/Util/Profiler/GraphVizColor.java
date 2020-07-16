/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Util.Profiler;

import Platform.Concurrent.AtomicIntegerS;

/**
 *
 * @author Администратор
 */
public class GraphVizColor {

    static String[] color = {"lightsalmon1","lightskyblue","lightslateblue","lightsteelblue2","lightyellow2","magenta","maroon","mediumaquamarine","mediumorchid3","mediumpurple3","mediumturquoise","mistyrose1","navajowhite","olivedrab1","orange1","orangered1","orchid1","palegreen","paleturquoise","palevioletred","papayawhip","peachpuff4","pink3","plum3","purple2","red2","rosybrown2","royalblue2","salmon1","seagreen","seashell","sienna","skyblue","slateblue","slategray","slategrey","snow4","springgreen4","steelblue4","tan4","thistle4","tomato4","turquoise3","violetred2","wheat2","yellow","yellowgreen"};



    static AtomicIntegerS counter = new AtomicIntegerS();

    public static String GetColor() {
        String res = color[Math.min(counter.get(),color.length-1)];
        counter.incrementAndGet();
        return res;
    }
}
