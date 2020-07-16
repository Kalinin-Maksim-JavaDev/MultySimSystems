package Platform.Util.Area;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import Platform.Util.File;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author kalinin
 */
public class Area {

    int grad = 5;
    protected ArrayListPure<PolygonArea> paList;
    BufferedImage img;
    JFrame frame;

    public Area() {
        paList = new ArrayListPure<PolygonArea>();
    }

    public int size() {
        return paList.size();
    }

    void RunFrame() {
        frame = new JFrame() {

            public void paint(Graphics g) {
                g.drawImage(img, 0, 0, null);
            }
        };
        frame.setSize(600, 800);
        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                frame.show(false);
                System.exit(0);
            }
        });
        frame.setVisible(true);
        new Thread(new Runnable() {

            public void run() {
                while (true) {
                    Graphics2D g2 = (Graphics2D) img.getGraphics();
                    g2.setBackground(Color.white);
                    for (int p = 0; p < paList.size(); p++) {
                        ArrayListPure<Line> lines = paList.get(p).lines;

                        for (int i = 0; i < lines.size(); i++) {

                            lines.get(i).Draw(g2);
                            lines.get(i).DrawDirection(g2);
                            lines.get(i).Number(g2, i);
                        }

                        //for (int i = 0; i < polygons.size(); i++) {
                        g2.setPaint(new GradientPaint(0, 0, Color.red, 100, 100, Color.green, false));

                        Polygon pol = new Polygon(paList.get(p).xpoints, paList.get(p).ypoints, paList.get(p).npoints);
                        PolygonSimple.Scale(pol.xpoints, pol.ypoints, pol.npoints, grad, -grad);
                        pol.translate(20, 200);
                        g2.fillPolygon(pol);

                        g2.setColor(Color.black);
                        g2.drawPolygon(pol);

                        // }
                    }

                    frame.update(g2);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Area.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }

    public PolygonArea get(int i) {
        return paList.get(i);
    }

    public ArrayListPure<PolygonArea> GetPolygonArea() {
        return paList;
    }

    public boolean Join(PolygonSimple pol) {
        boolean contacted = false;
        if ((paList.isEmpty())) {
            //PolygonAreaG p = new PolygonAreaG(pol);
            paList.add(new PolygonArea(pol));
            contacted = false;
        } else {

            for (int i = 0; i < paList.size(); i++) {
                contacted = paList.get(i).Contact(pol);
                if (contacted) {
                    break;
                }
            }
            if (!contacted) {
                //PolygonAreaG p = new PolygonAreaG(pol);
                paList.add(new PolygonArea(pol));
            }
        }
        return contacted;
    }

    public PolygonSimple getPolygon(int i) {
        return paList.get(i);
    }

    public void Glide() {
        for (int i = 0; i < paList.size(); i++) {
            //  System.out.print(pa.get(i).polygons.npoints+"->");
            paList.get(i).UpDatePointArray();
            // System.out.println(pa.get(i).polygons.npoints);
        }
    }

    public Area Complite(int n) {
        if (n == 0) {
            return this;
        }
        boolean end = true;
        Area areanew = new Area();
        // area2.RunFrame();
        for (int i = 0; i < paList.size(); i++) {
            boolean res1 = areanew.Join(getPolygon(i));
            end = end && res1;
        }
        if (!end) {
            areanew.Complite(--n);
        }
        return areanew;

    }
//    class PolygonAreaG extends PolygonArea {
//
//        Polygon polygonsG;
//
//        public PolygonAreaG(PolygonSimple pol_) {
//            super(pol_);
//        }
//
//        @Override
//        public void UpDatePointArray() {
//            polygonsG = new java.awt.Polygon();
//            super.UpDatePointArray();
//        }
//
//        @Override
//        public void AddPoint(int x, int y) {
//            super.AddPoint(x, y);
//            polygonsG.addPoint(20 + x * 5, 200 - y * 5);
//        }
//    }
    static int[] p1x = {0, 1, 1, 0};
    static int[] p1y = {1, 1, 2, 2};
    static int[] p2x = {0, 1, 1, 0};
    static int[] p2y = {0, 0, 1, 1};
    static int[] p3x = {1, 2, 2, 1};
    static int[] p3y = {2, 2, 1, 1};
    static int[] p4x = {1 + 4, 2 + 4, 2 + 4, 1 + 4};
    static int[] p4y = {2, 2, 1, 1};

    public static void main(String[] args) {

        Area area = new Area();
        area.RunFrame();
        InputStream res = null;

        res = area.getClass().getResourceAsStream("/Util/map.PNG");

        byte[] arg = new byte[5];
        arg[0] = 1;
        byte[][] coor = File.LoadGameMap(res, 40, 40, arg);

        for (int j = 0; j < coor.length; j++) {
            for (int i = 0; i < coor[0].length; i++) {
                if (coor[j][i] == 1) {
                    PolygonSimple p_ = new PolygonSimple(false);
                    p_.addPoint(i, j);
                    p_.addPoint(i + 1, j);
                    p_.addPoint(i + 1, j + 1);
                    p_.addPoint(i, j + 1);
                    area.Join(p_);
                }

            }
        }


        Area finalarea = area.Complite(3);

        finalarea.RunFrame();
        finalarea.Glide();

//
//

//        area.Add(new Polygon(p1x, p1y, p1x.length));
//        area.Add(new Polygon(p2x, p2y, p2x.length));
//        area.Add(new Polygon(p3x, p3y, p3x.length));
//        area.Add(new Polygon(p4x, p4y, p4x.length));
    }
}
