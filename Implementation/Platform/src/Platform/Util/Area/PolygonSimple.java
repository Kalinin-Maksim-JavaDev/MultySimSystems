/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Util.Area;

/**
 *
 * @author kalinin
 */
public class PolygonSimple {

    boolean glide;

    public PolygonSimple(boolean glide_) {
        this(10);
        glide = glide_;
    }
    public static void Scale(int[] xpoints, int[] ypoints, int npoints, int kx, int ky) {
        for (int i= 0; i < npoints; i++) {
            xpoints[i]*=kx;
            ypoints[i]*=ky;
        }
    }
    public static boolean Transection(int x1, int y1, int x2, int y2, PolygonSimple polygon) {
        boolean res = true;
        Line l1 = new Line(x1, y1, x2, y2);
        int i = 0;
        for (; i < polygon.npoints - 1; i++) {
            Line l2 = new Line(polygon.xpoints[i], polygon.ypoints[i], polygon.xpoints[i + 1], polygon.ypoints[i + 1]);
            res = !Line.Transection(l1, l2) && res;
        }
        Line l2 = new Line(polygon.xpoints[i], polygon.ypoints[i], polygon.xpoints[0], polygon.ypoints[0]);
        res = !Line.Transection(l1, l2) && res;
        return !res;
    }

    public void addPoint(int x, int y) {
        if ((glide) && (npoints > 1)) {
            int xLast = xpoints[npoints - 2];
            int yLast = ypoints[npoints - 2];
            if ((xLast == x) || (yLast == y)) {
                xpoints[npoints - 1] = x;
                ypoints[npoints - 1] = y;
            } else {
                addPoint_(x, y);
            }
        } else {
            addPoint_(x, y);
        }
    }
    public int npoints;
    public int xpoints[];
    public int ypoints[];

    public PolygonSimple(int maxLength) {
        xpoints = new int[maxLength];
        ypoints = new int[maxLength];
    }

    public PolygonSimple() {
        this(2000);
    }

    public void addPoint_(int x, int y) {
        ensureCapacity(npoints + 1);
        xpoints[npoints] = x;
        ypoints[npoints] = y;
        npoints++;
    }

    public void ensureCapacity(int minCapacity) {
        int oldCapacity = xpoints.length;
        if (minCapacity > oldCapacity) {
            int newCapacity = (oldCapacity * 3) / 2 + 1;
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            // minCapacity is usually close to size, so this is a win:
            xpoints = copyOf(xpoints, newCapacity);
        }

        oldCapacity = ypoints.length;
        if (minCapacity > oldCapacity) {
            int newCapacity = (oldCapacity * 3) / 2 + 1;
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            // minCapacity is usually close to size, so this is a win:
            ypoints = copyOf(ypoints, newCapacity);
        }
    }

    public static int[] copyOf(int[] original, int newLength) {
        int[] copy = new int[newLength];
        System.arraycopy(original, 0, copy, 0,
                Math.min(original.length, newLength));
        return copy;
    }

    public void reset() {
        npoints = 0;
    }
}
