/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter.Graphics.Space.G2D.Images;

import Platform.Util.Area.PolygonSimple;
import Logic.Reflections.Space.ISpace;
import Presenter.Graphics.Space.G2D.Space2D;
import Render.Graphics.Geometry.ICoordinates;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.ArrayList;

/**
 *
 * @author kalinin
 */
public class GradientArea extends ImageL {

    public Area shape;
    public ArrayList<Polygon> canva;
    public int x1 = 100;
    public int y1 = 0;
    public int x2 = 0;
    public int y2 = 200;
    public int r = 100;
    double angle = 0;
    public int x1a;
    public int y1a;
    public int x2a;
    public int y2a;
    public int r1;
    public int g1;
    public int b1;
    public int r2;
    public int g2;
    public int b2;

    public GradientArea(int layer_, ISpace space_, int r1_, int g1_, int b1_, int r2_, int g2_, int b2_) {
        super(layer_, space_);
        shape = new Area();
        canva = new ArrayList<Polygon>();
        r1 = r1_;
        g1 = g1_;
        b1 = b1_;

        r2 = r2_;
        g2 = g2_;
        b2 = b2_;
    }

    public GradientArea(int layer_, ISpace space_, int r1_, int g1_, int b1_, int r2_, int g2_, int b2_, int[] x1, int[] y1, int[] w, int[] h) {
        this(layer_, space_, r1_, g1_, b1_, r2_, g2_, b2_);


        for (int i = 0; i < x1.length; i++) {
            AddShape(x1[i], y1[i], w[i], h[i]);
        }
    }

    public void AddShape(int x1, int y1, int w, int h) {
        shape.add(new Area(new Rectangle(x1, Tr(y1), w, h)));
    }

    public void AddCanva(PolygonSimple canva_) {
        canva.add(new Polygon(canva_.xpoints, canva_.ypoints, canva_.npoints));
    }

    @Override
    public void PutImage(ICoordinates coor) {
//        Move();
//        Graphics2D bufferGraphics = ((Space2D) space).bufferGraphics[layer];
//
//        Composite oldcomp = bufferGraphics.getComposite();
//
//
//        bufferGraphics.setPaint(new GradientPaint(x1a, Tr(y1a), new Color(r1, g1, b1), x2a, Tr(y2a), new Color(r2, g2, b2), false));
//        bufferGraphics.fill(shape);
//
//        Move();
//        bufferGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
//        bufferGraphics.setPaint(new GradientPaint(Tr(y1a), 0, new Color(r2, g2, b2), Tr(y2a), 0, new Color(r1, g1, b1), false));
//        bufferGraphics.fill(shape);
//        bufferGraphics.setComposite(oldcomp);
//
//        bufferGraphics.setColor(Color.black);//setPaint(new GradientPaint(x1a, Tr(y1a), Color.GRAY, x2a, Tr(y2a), Color.BLACK, false));
//        //bufferGraphics.fill(canva);
//        for (int i = 0; i < canva.size(); i++) {
//            bufferGraphics.drawPolygon(canva.get(i));
//        }

        Object[] oarg = {this};
        ((Space2D) space).painter.addToPictureList("GradientArea", layer, null, oarg, coor == null);



    }

    public void Move() {
        x1a = x1 + (int) (r * Math.cos(Math.toRadians(angle)));
        y1a = y1 + (int) (r * Math.sin(Math.toRadians(angle)));
        x2a = x2 + (int) (r * Math.cos(Math.toRadians(angle)));
        y2a = y2 + (int) (r * Math.sin(Math.toRadians(angle)));

        angle += 2;
        if (angle >= 360) {
            angle = 0;
        }
    }
}
