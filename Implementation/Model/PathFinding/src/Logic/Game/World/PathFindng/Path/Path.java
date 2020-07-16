/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Game.World.PathFindng.Path;

import Logic.Game.World.PathFindng.Path.Vertice.PathPointsFactory;
import Logic.Model.Game.World.PathFindng.IPathPointsFactory;
import Logic.Model.Game.World.PathFindng.Path.IPath;
import Logic.Model.Game.World.PathFindng.Path.TrackPoint.ITrackPoint;

/**
 *
 * @author kalinin
 */
public abstract class Path implements IPath {

    private static final int grad = 10;

    /**
     * @return the grad
     */
    public static int getGrad() {
        return grad;
    }
    private int len;
    private int mapH;
    private int mapW;
    private int fallLimit;
    private final int maxdirectCount;
    private int maxForkWays = 2;
    private double minlength;
    private double currentlength;
    private BusyPointsMap bpm;
    private ITrackPoint start;
    private ITrackPoint finish;
    private Cahsdirections cahsdirections;
    private ITrackPoint preTarget;
    private final PathPointsFactory pointsFactory;
//Object[][] map;
//int[] directionX = {1, -1, 1, -1, 1, -1};
//int[] directionY = {-1, -1, 0, 0, 1, 1};

    protected Path(int fallLimit_, int mapW_, int mapH_) {
        pointsFactory = new PathPointsFactory();
        fallLimit = fallLimit_;
        mapW = mapW_;
        mapH = mapH_;
        maxdirectCount = 2 * (3 + 1 + fallLimit);
        cahsdirections = new Cahsdirections(maxdirectCount);


        //   map = map_;


        bpm = new BusyPointsMap(getMapW(), getMapH());


//        im = im_;
//        g = (Graphics2D) im.getGraphics();
//        g.setColor(Color.BLACK);
//        g2 = (Graphics2D) im.getGraphics();
//        g2.setColor(Color.red);

        //g.setColor(Color.green);


    }

    public void CalculatePath(ITrackPoint start_, ITrackPoint target_) {

        start = start_;
        setTarget(target_);
        minlength = 999999999;
        bpm.RefreshBusyPointsMap();
        RefreshGraphics();
        DrawCells();

        setLen(0);
        //System.out.println("start " + start);
        if (start.equal(getTarget())) {
        } else {
            ContinuePath(start);

            if (start.getNext() != null) {
                RefreshGraphics();
                DrawCells();

                ITrackPoint a_ = start;
                ITrackPoint b_ = start.getNext();

                while (b_ != null) {
                    setLen(getLen() + S(b_, a_));
                    drawLine(getGrad() * a_.getX() + getGrad() / 2, Tr(getGrad() * a_.getY() + getGrad() / 2), getGrad() * b_.getX() + getGrad() / 2, Tr(getGrad() * b_.getY() + getGrad() / 2), 0, 255, 0);
                    setPreTarget(a_);
                    a_ = b_;

                    b_ = b_.getNext();
                }

                //   System.out.println(len);
            }
        }
    }

    protected int Tr(int y_) {
        return Path.getGrad() * getMapH() - y_;
    }

    private final void ContinuePath(ITrackPoint enter) {


        ITrackPoint[] directions = GetAviableDirrection(enter);

        if (directions == null) {
            enter.setNext(null);
            return;
        }



        ITrackPoint nextTrack = null;


        for (int i = 0; i < directions.length; i++) {

            nextTrack = directions[i];
            double addlength = S(nextTrack, enter);
            if (currentlength + addlength > minlength) {
                continue;
            }
            currentlength += addlength;

            drawLine(getGrad() * enter.getX() + getGrad() / 2, Tr(getGrad() * enter.getY() + getGrad() / 2), getGrad() * nextTrack.getX() + getGrad() / 2, Tr(getGrad() * nextTrack.getY() + getGrad() / 2), 0, 255, 0);
            if (nextTrack.equal(getTarget())) {
                minlength = currentlength;
                enter.setNext(getTarget());
                break;
            } else {
                bpm.Set(nextTrack.getX(), nextTrack.getY());
                ContinuePath(nextTrack);
                currentlength -= addlength;
                if (nextTrack.getNext() != null) {
                    enter.setNext(nextTrack);
                } else {
                    drawLine(getGrad() * enter.getX() + getGrad() / 2, Tr(getGrad() * enter.getY() + getGrad() / 2), getGrad() * nextTrack.getX() + getGrad() / 2, Tr(getGrad() * nextTrack.getY() + getGrad() / 2), 255, 0, 0);

                }
                if (enter == start) {
                    bpm.RefreshBusyPointsMap();
                }
            }
        }
    }

    void FillAviableDirection(int deltex, int x, int y) {

        int dx = x + deltex;

        //Up
        for (int dy = y + 1; dy
                < y + 3; dy++) {
            if (BusyMap(x, dy)) {
                break;
            }

            if (BusyMap(dx, dy))//((map[dy][dx] == Jumper.f) || (map[dy][dx] == Jumper.h))
            {
                continue;
            }
            if (BusyMap(dx, dy - 1)) {//((map[dy - 1][dx] == Jumper.f) || (map[dy - 1][dx] == Jumper.h)) {

                if (!bpm.Busy(dx, dy)) {
                    cahsdirections.Add(dx, dy);
                }
            }
        }

        //Forward
        {
            int dy = y;
            if (BusyMap(dx, dy))//((map[dy][dx] == Jumper.f) || (map[dy][dx] == Jumper.h))
            {
                //�����
            } else {
                if (BusyMap(dx, dy - 1)) {//((map[dy - 1][dx] == Jumper.f) || (map[dy - 1][dx] == Jumper.h)) {

                    if (!bpm.Busy(dx, dy)) {
                        cahsdirections.Add(dx, dy);
                    }


                }
            }
        }
        //Down
//        for (int dy = y - 1; dy > y - 3; dy--) {
//
//
//            if (BusyMap(dx, dy)) {//((map[dy][dx] == Jumper.f) || (map[dy][dx] == Jumper.h)) {
//                //���
//                break;
//            }
//            if (BusyMap(dx, dy - 1)) {//((map[dy - 1][dx] == Jumper.f) || (map[dy - 1][dx] == Jumper.h)) {
//
//                if (!bpm.Busy(dx, dy)) {
//                    cahsdirections.Add(dx, dy);
//                }
//            }
//        }



        //Fall
        for (int dx2 = x + deltex; dx2 != x + 3 * deltex; dx2 += deltex) {

            if (BusyMap(dx2, y)) {
                //�����
                break;


            }

            boolean falled = false;


            int dy = y;


            while ((dy - 1 >= 0) && !BusyMap(dx2, dy - 1)) {
                falled = true;
                dy--;
            }
            if ((dy > y - getFallLimit()) && (falled)) {
                if (!bpm.Busy(dx2, dy)) {
                    cahsdirections.Add(dx, dy);
                }
            }
        }
    }

    private ITrackPoint[] GetAviableDirrection(ITrackPoint enter) {
        cahsdirections.reset();


        int x = enter.getX();
        int y = enter.getY();
        //Go left and right
        for (int dx = - 1; dx
                <= 1; dx += 2) {
            FillAviableDirection(dx, x, y);
        }

        //Jump over
        for (int dx = x - 2; dx
                <= x + 2; dx += 4) {
            int dy = y;


            if (!BusyMap((dx + x) / 2, dy - 1)) {
                if (BusyMap(x, dy + 1)) {
                    //����� �������
                    continue;
                }
                if (BusyMap((dx + x) / 2, dy + 1)) {
                    //����� �������
                    continue;
                }
                if (BusyMap(dx, dy)) {
                    //�����
                    continue;
                }
                if (BusyMap(dx, dy - 1)) {

                    if (!bpm.Busy(dx, dy)) {
                        cahsdirections.Add(dx, dy);

                    }

                }
            }
        }




        if (cahsdirections.isEmpty()) {
            return null;
        }


        return Sorting(cahsdirections);


    } //    double Min(double a, double b) {
    //       if (a<b){
    //           return a;
    //       }else{
    //           return b;
    //       }
    //    }
public IPathPointsFactory getPathPointsFactory() {
        return pointsFactory;

    }
    protected ITrackPoint[] Sorting(Cahsdirections cahsdirections) {
        ITrackPoint[] directions = new ITrackPoint[cahsdirections.j];


        for (int i = 0; i < cahsdirections.j; i++) {
            directions[i] = getPathPointsFactory().Create(cahsdirections.directions[i].getX(), cahsdirections.directions[i].getY());
        } //Sorting by target
        ITrackPoint c_;


        boolean nosort = true;
        while (nosort) {
            nosort = false;
            for (int i = 0; i < cahsdirections.j - 1; i++) {
                if (S(getTarget(), directions[i]) > S(getTarget(), directions[i + 1])) {
                    //�������� ������� �������� A[j] � A[j+1]
                    c_ = directions[i];
                    directions[i] = directions[i + 1];
                    directions[i + 1] = c_;
                    nosort = true;
                }
            }
        }
        return directions;
    }

    int S(ITrackPoint tp2, ITrackPoint tp1) {
        double dx = tp2.getX() - tp1.getX();


        double dy = tp2.getY() - tp1.getY();


        return (int) Math.sqrt(dx * dx + dy * dy);


    }

    private boolean BusyMap(int x_, int y_) {
        //return (map[y_][x_] == Jumper.f) || (map[y_][x_] == Jumper.h) || (map[y_][x_] == Jumper.b);
        //return (map[y_][x_] == 1);
        if ((start.getX() == x_) && (start.getY() == y_)) {
            return false;


        } else {
            return !IsMapsCellFree(x_, y_);


        }

    }

    protected abstract boolean IsMapsCellFree(int x_, int y_);

    protected abstract void DrawCells();

    protected abstract void RefreshGraphics();

    protected abstract void drawLine(int x1, int y1,
            int x2, int y2, int r, int g, int b);

    /**
     * @return the target
     */
    public ITrackPoint getTarget() {
        return finish;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(ITrackPoint target) {
        this.finish = target;
    }

    /**
     * @return the fallLimit
     */
    public int getFallLimit() {
        return fallLimit;
    }

    /**
     * @return the mapH
     */
    public int getMapH() {
        return mapH;
    }

    /**
     * @param mapH the mapH to set
     */
    public void setMapH(int mapH) {
        this.mapH = mapH;
    }

    /**
     * @return the mapW
     */
    public int getMapW() {
        return mapW;
    }

    /**
     * @param mapW the mapW to set
     */
    public void setMapW(int mapW) {
        this.mapW = mapW;
    }

    /**
     * @return the len
     */
    public int getLen() {
        return len;
    }

    /**
     * @param len the len to set
     */
    public void setLen(int len) {
        this.len = len;
    }

    /**
     * @return the preTarget
     */
    public ITrackPoint getPreTarget() {
        return preTarget;
    }

    /**
     * @param preTarget the preTarget to set
     */
    public void setPreTarget(ITrackPoint preTarget) {
        this.preTarget = preTarget;
    }

    class BusyPointsMap {

        int x;
        int y;
        boolean[][] busyPoints;

        BusyPointsMap(int x_, int y_) {
            x = x_;
            y = y_;
        }

        void RefreshBusyPointsMap() {
            busyPoints = new boolean[y][x];
            busyPoints[start.getY()][start.getX()] = true;
        }

        void Set(int x_, int y_) {
            busyPoints[y_][x_] = true;

        }

        private void Free(int x_, int y_) {
            busyPoints[y_][x_] = false;
        }

        boolean Busy(int x_, int y_) {
            return busyPoints[y_][x_];
        }
    }

    private class Cahsdirections {

        ITrackPoint[] directions;
        private int j = 0;

        private Cahsdirections(int n_) {
            directions = new ITrackPoint[n_];
            for (int i = 0; i < n_; i++) {
                directions[i] = getPathPointsFactory().Create(0, 0);
            }
        }

        private void Add(int dx, int dy) {
            directions[j].setX(dx);
            directions[j].setY(dy);
            j++;
        }

        private void reset() {
            j = 0;
        }

        private boolean isEmpty() {
            return j == 0;
        }
    }

    
}
