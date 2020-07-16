package Platform.Util.Area;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author kalinin
 */
public class Line {

    public static boolean Transection(Line l1, Line l2) {
        double v1 = (l2.x2 - l2.x1) * (l1.y1 - l2.y1) - (l2.y2 - l2.y1) * (l1.x1 - l2.x1);
        double v2 = (l2.x2 - l2.x1) * (l1.y2 - l2.y1) - (l2.y2 - l2.y1) * (l1.x2 - l2.x1);
        double v3 = (l1.x2 - l1.x1) * (l2.y1 - l1.y1) - (l1.y2 - l1.y1) * (l2.x1 - l1.x1);
        double v4 = (l1.x2 - l1.x1) * (l2.y2 - l1.y1) - (l1.y2 - l1.y1) * (l2.x2 - l1.x1);
       
        return ((v1 * v2 <= 0) && (v3 * v4 <= 0));
    }
    float x1;
    float y1;
    float x2;
    float y2;
    Line prev;
    Line next;

    public Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public boolean equals(Line line) {
        return (((line.x1 == x1) && (line.y1 == y1) && (line.x2 == x2) && (line.y2 == y2))
                || ((line.x1 == x2) && (line.y1 == y2) && (line.x2 == x1) && (line.y2 == y1)));
    }


    @Override
    public boolean equals(Object obj) {
        return this.equals((Line)obj);
    }

    public boolean Conected(Line line) {
        return (((line.x1 == x1) && (line.y1 == y1)) || //1==1
                ((line.x1 == x2) && (line.y1 == y2)) || //1==2
                ((line.x2 == x1) && (line.y2 == y1)) || //2==1
                ((line.x2 == x2) && (line.y2 == y2)));   //2==2
    }

    public boolean Conect(Line line) {
        if ((next == line) || (prev == line)) {
            return false;
        }
        if (next == null) {
            if (line.prev == null) {
                next = line;
                line.prev = this;
                return true;
            }
            if (line.next == null) {
                next = line;
                line.next = this;
                return true;
            }

        }
        if (prev == null) {
            if (line.next == null) {
                prev = line;
                line.next = this;
                return true;
            }
            if (line.prev == null) {
                prev = line;
                line.prev = this;
                return true;
            }
        }
        return false;

    }

    public void SetDidconect() {
        next = null;
        prev = null;
    }

    public boolean IsFree() {
        return ((next == null) || (prev == null));
    }

    public int GetFrameX(float x) {
        return (int) (10 + x * 10);
    }

    public int GetFrameY(float y) {
        return (int) (600 - y * 10);
    }

    public void Draw(Graphics2D g) {
        g.setColor(Color.black);
        g.drawLine(GetFrameX(x1), GetFrameY(y1), GetFrameX(x2), GetFrameY(y2));
    }

    public void DrawDirection(Graphics2D g) {
        if (next != null) {
            {
                float begx = (x1 + x2) / 2;
                float begy = (y1 + y2) / 2;
                float endx = (next.x1 + next.x2) / 2;
                float endy = (next.y1 + next.y2) / 2;
                g.setColor(Color.red);
                g.drawLine(GetFrameX(begx), GetFrameY(begy), GetFrameX(endx), GetFrameY(endy));
                g.drawRect(GetFrameX(endx), GetFrameY(endy), 1, 1);
            }

        }

    }

    @Override
    public String toString() {
        return " (" + x1 + ", " + y1 + ")-(" + x2 + ", " + y2 + ")";
    }

    public void Number(Graphics2D g, int i) {
        float begx = (x1 + x2) / 2;
        float begy = (y1 + y2) / 2;
        g.setColor(Color.black);
        g.drawString(String.valueOf(i), GetFrameX(begx), GetFrameY(begy));
    }

    public boolean Forward() {
        return ((x2 == next.x1) && ((y2 == next.y1)));
    }
}
