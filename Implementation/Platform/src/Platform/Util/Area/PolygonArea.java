package Platform.Util.Area;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalinin
 */
public class PolygonArea extends PolygonSimple {

    public ArrayListPure<Line> lines;

    public PolygonArea(PolygonSimple pol_) {
        lines = new ArrayListPure<Line>();

        FillLinesArray(lines, pol_);
        UpDatePointArray();
    }

    public boolean Contact(PolygonSimple pol_) {
        boolean contacted = false;
        ArrayListPure<Line> newpol = new ArrayListPure<Line>();
        FillLinesArray(newpol, pol_);

        for (int i = 0; i < newpol.size(); i++) {
            if (lines.XOR(newpol.get(i))) {
                contacted = true;
            }
        }
        if (!contacted) {
            lines.removeAll(newpol);
        }
        if (contacted) {
            UpDatePointArray();
        }
        return contacted;
    }

    protected void UpDatePointArray() {
        reset();
        for (int i = 0; i < lines.size(); i++) {
            lines.get(i).SetDidconect();
        }
        // System.out.println("------------");
        {
            Line l_ = lines.get(0);
            for (int j = 0; j < lines.size(); j++) {
                Line l2 = lines.get(j);
                if (l_ != l2) {
                    if (l_.Conected(l2)) {
                        if (l_.Conect(l2)) {
                            //   System.out.println(l_ + " conected " + l2);
                        } else {
                            //   System.out.println("    " + l_ + " not conected " + l2);
                        }
                        if (!l_.IsFree()) {
                            l_ = l_.next;
                            if (l_ == lines.get(0)) {
                                break;
                            }
                            j = 0;
                        }

                    }
                }
            }
        }
        {
            Line l_ = lines.get(0);
            do {
                //   System.out.println(l_.toString() + " " + l_.Forward());
                if ((npoints > 0) && ((xpoints[npoints - 1] == l_.x2) && ((ypoints[npoints - 1] == l_.y2)))) {
                    AddPoint((int) l_.x1, (int) l_.y1);
                } else {
                    AddPoint((int) l_.x2, (int) l_.y2);
                }

                l_ = l_.next;
                if ((l_ == null) || (l_.next == null)) {
                    System.out.println(lines.indexOf(l_) + " " + l_.toString() + " ");
                }
            } while (l_ != lines.get(0));
        }
    }

    public void AddPoint(int x, int y) {
        addPoint(x, y);
    }

    private static void FillLinesArray(ArrayListPure<Line> newpol, PolygonSimple pol) {
        for (int i = 1; i < pol.npoints; i++) {
            newpol.add(new Line(pol.xpoints[i - 1], pol.ypoints[i - 1], pol.xpoints[i], pol.ypoints[i]));
        }
        newpol.add(new Line(pol.xpoints[pol.npoints - 1], pol.ypoints[pol.npoints - 1], pol.xpoints[0], pol.ypoints[0]));
    }
}
