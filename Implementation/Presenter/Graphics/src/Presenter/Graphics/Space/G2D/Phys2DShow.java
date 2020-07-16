// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 27.02.2012 11:13:22
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3)
// Source File Name:   AbstractDemo.java
package Presenter.Graphics.Space.G2D;

import Presenter.Graphics.Space.G2D.Images.ImageL;
import Platform.Concurrent.ILathce;
import Platform.Util.Area.PolygonSimple;
import Global.Tools;
import Model.Geometry.D2.Coordinates2d;
import Logic.Reflections.Space.IPainter;
import Platform.Concurrent.Factory;
import Render.Graphics.Geometry.ICoordinates;
import Render.Graphics.Space.G2D.IPhys2DShow;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import pphys2d.bodies.*;
import pphys2d.phys2d.math.*;
import pphys2d.phys2d.raw.*;
import pphys2d.phys2d.raw.shapes.Box;
import pphys2d.phys2d.raw.shapes.Circle;
import pphys2d.phys2d.raw.shapes.Line;
import pphys2d.phys2d.raw.strategies.QuadSpaceStrategy;
import pphys2d.shapes.PPPolyShape;

public class Phys2DShow extends ImageL implements IPhys2DShow {

    ILathce begin;
    ILathce end;
    Body[] dusts;
    Body[] chains;
    //public BufferedImage img;
    //public Graphics2D g;
    public IPainter painter;
    int dustCounter = 0;
    int chainsCounter = 0;
    PPPoly pPoly;
    float xl = -1;
    float yl = -1;
    Polygon poly = new Polygon();
    ArrayList<Cloud> clouds;
//    private Color[] dustColors;
    Random random = new Random();

    public Phys2DShow(int layer_, Space2D space_, float gravity) {
        super(layer_, space_);
        world = new World(new Vector2f(0.0F, gravity), 10, new QuadSpaceStrategy(20, 5) {

            public void collideBodies(CollisionContext context, BodyList bodies, float dt) {
                ((CollisionSpace) context).resolveWithHash(bodies, dt);
            }
        });
        world.map = new Body[space_.height][space_.width];
        begin = Factory.createLathce(true, false);
        end = Factory.createLathce(true, false);
        dusts = new Body[200];
//        dustColors = new Color[dusts.length];
//        for (int i= 0; i < dusts.length; i++) {
//            dustColors[i]=new Color(random.nextInt());
//        }

        chains = new Body[200];
        //img = space_.im[layer_];//new BufferedImage(480, 320, BufferedImage.TYPE_INT_ARGB);
        //g = space_.bufferGraphics[layer_];
        painter = space_.painter;

        grad = ((Space2D) space).grad;
        clouds = new ArrayList<Cloud>();
        {
            float w = space_.width - 10;
            float h = space_.height - 10;
            PPLine l1 = new PPLine(10, 10, w - 10, 0);
            PPLine l2 = new PPLine(w, 10, 0, h - 10);
            PPLine l3 = new PPLine(10, h, w - 10, 0);
            PPLine l4 = new PPLine(10, 10, 0, h - 10);

            l1.setStaticBody(true);
            l2.setStaticBody(true);
            l3.setStaticBody(true);
            l4.setStaticBody(true);
//            world.add(l1);
//            world.add(l2);
//            world.add(l3);
//            world.add(l4);
        }
    }

    protected void update() {
    }

    protected void renderGUI(Graphics2D g) {
        g.setColor(Color.black);
        g.drawString("R - Restart Demo", 15, 430);
    }

    protected void drawBody(Graphics2D g, Body body) {
        if (body.getShape() instanceof Box) {
            drawBoxBody(g, body, (Box) body.getShape());
        }
        if (body.getShape() instanceof Circle) {
            drawCircleBody(g, body, (Circle) body.getShape());
        }
        if (body.getShape() instanceof Line) {
            drawLineBody(g, body, (Line) body.getShape());
        }
        if (body.getShape() instanceof PPPolyShape) {
            drawPolygonBody(g, body, (PPPolyShape) body.getShape());
        }
    }

    protected void drawPolygonBody(Graphics2D g, Body body, PPPolyShape poly) {

        ROVector2f verts[] = poly.getVertices(body.getPosition(), body.getRotation());
        int i = 0;
        int j = verts.length - 1;
        for (; i < verts.length; i++) {
            g.setColor(Color.black);
            g.drawLine((int) (0.5F + verts[i].getX()), (int) (0.5F + verts[i].getY()), (int) (0.5F + verts[j].getX()), (int) (0.5F + verts[j].getY()));
            g.setColor(Color.green);
            g.drawRect((int) (0.5F + verts[j].getX()), (int) (0.5F + verts[j].getY()), 1, 1);
            j = i;
        }

    }

    protected void drawLineBody(Graphics2D g, Body body, Line line) {
        g.setColor(Color.GREEN);
        Vector2f verts[] = line.getVertices(body.getPosition(), body.getRotation());
        g.drawLine((int) verts[0].getX(), (int) verts[0].getY(), (int) verts[1].getX(), (int) verts[1].getY());
    }

    protected void drawCircleBody(Graphics2D g, Body body, Circle circle) {
        g.setColor(Color.red);
        float x = body.getPosition().getX();
        float y = body.getPosition().getY();
        float r = circle.getRadius();
        float rot = body.getRotation();
        float xo = (float) (Math.cos(rot) * (double) r);
        float yo = (float) (Math.sin(rot) * (double) r);
        g.drawOval((int) (x - r), (int) (y - r), (int) (r * 2.0F), (int) (r * 2.0F));
        g.drawLine((int) x, (int) y, (int) (x + xo), (int) (y + yo));
    }

    protected void drawBoxBody(Graphics2D g, Body body, Box box) {
        Vector2f pts[] = box.getPoints(body.getPosition(), body.getRotation());
        Vector2f v1 = pts[0];
        Vector2f v2 = pts[1];
        Vector2f v3 = pts[2];
        Vector2f v4 = pts[3];
        g.setColor(Color.black);
        g.drawLine((int) v1.x, (int) v1.y, (int) v2.x, (int) v2.y);
        g.drawLine((int) v2.x, (int) v2.y, (int) v3.x, (int) v3.y);
        g.drawLine((int) v3.x, (int) v3.y, (int) v4.x, (int) v4.y);
        g.drawLine((int) v4.x, (int) v4.y, (int) v1.x, (int) v1.y);

    }

    public void drawJoint(Graphics2D g, Joint j) {
        if (j instanceof FixedJoint) {
            FixedJoint joint = (FixedJoint) j;
            g.setColor(Color.red);
            float x1 = joint.getBody1().getPosition().getX();
            float x2 = joint.getBody2().getPosition().getX();
            float y1 = joint.getBody1().getPosition().getY();
            float y2 = joint.getBody2().getPosition().getY();
            g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
        }
        if (j instanceof SlideJoint) {
            SlideJoint joint = (SlideJoint) j;
            Body b1 = joint.getBody1();
            Body b2 = joint.getBody2();
            Matrix2f R1 = new Matrix2f(b1.getRotation());
            Matrix2f R2 = new Matrix2f(b2.getRotation());
            ROVector2f x1 = b1.getPosition();
            Vector2f p1 = MathUtil.mul(R1, joint.getAnchor1());
            p1.add(x1);
            ROVector2f x2 = b2.getPosition();
            Vector2f p2 = MathUtil.mul(R2, joint.getAnchor2());
            p2.add(x2);
            Vector2f im = new Vector2f(p2);
            im.sub(p1);
            im.normalise();
            g.setColor(Color.red);
            g.drawLine((int) p1.x, (int) p1.y, (int) (p1.x + im.x * joint.getMinDistance()), (int) (p1.y + im.y * joint.getMinDistance()));
            g.setColor(Color.blue);
            g.drawLine((int) (p1.x + im.x * joint.getMinDistance()), (int) (p1.y + im.y * joint.getMinDistance()), (int) (p1.x + im.x * joint.getMaxDistance()), (int) (p1.y + im.y * joint.getMaxDistance()));
        }
        if (j instanceof AngleJoint) {
            AngleJoint angleJoint = (AngleJoint) j;
            Body b1 = angleJoint.getBody1();
            Body b2 = angleJoint.getBody2();
            float RA = j.getBody1().getRotation() + angleJoint.getRotateA();
            float RB = j.getBody1().getRotation() + angleJoint.getRotateB();
            Vector2f VA = new Vector2f((float) Math.cos(RA), (float) Math.sin(RA));
            Vector2f VB = new Vector2f((float) Math.cos(RB), (float) Math.sin(RB));
            Matrix2f R1 = new Matrix2f(b1.getRotation());
            Matrix2f R2 = new Matrix2f(b2.getRotation());
            ROVector2f x1 = b1.getPosition();
            Vector2f p1 = MathUtil.mul(R1, angleJoint.getAnchor1());
            p1.add(x1);
            ROVector2f x2 = b2.getPosition();
            Vector2f p2 = MathUtil.mul(R2, angleJoint.getAnchor2());
            p2.add(x2);
            g.setColor(Color.red);
            g.drawLine((int) p1.x, (int) p1.y, (int) (p1.x + VA.x * 20F), (int) (p1.y + VA.y * 20F));
            g.drawLine((int) p1.x, (int) p1.y, (int) (p1.x + VB.x * 20F), (int) (p1.y + VB.y * 20F));
        }
        if (j instanceof BasicJoint) {
            BasicJoint joint = (BasicJoint) j;
            Body b1 = joint.getBody1();
            Body b2 = joint.getBody2();
            Matrix2f R1 = new Matrix2f(b1.getRotation());
            Matrix2f R2 = new Matrix2f(b2.getRotation());
            ROVector2f x1 = b1.getPosition();
            Vector2f p1 = MathUtil.mul(R1, joint.getLocalAnchor1());
            p1.add(x1);
            ROVector2f x2 = b2.getPosition();
            Vector2f p2 = MathUtil.mul(R2, joint.getLocalAnchor2());
            p2.add(x2);
            g.setColor(Color.red);
            g.drawLine((int) x1.getX(), (int) x1.getY(), (int) p1.x, (int) p1.y);
            g.drawLine((int) p1.x, (int) p1.y, (int) x2.getX(), (int) x2.getY());
            g.drawLine((int) x2.getX(), (int) x2.getY(), (int) p2.x, (int) p2.y);
            g.drawLine((int) p2.x, (int) p2.y, (int) x1.getX(), (int) x1.getY());
        }
        if (j instanceof DistanceJoint) {
            DistanceJoint joint = (DistanceJoint) j;
            Body b1 = joint.getBody1();
            Body b2 = joint.getBody2();
            Matrix2f R1 = new Matrix2f(b1.getRotation());
            Matrix2f R2 = new Matrix2f(b2.getRotation());
            ROVector2f x1 = b1.getPosition();
            Vector2f p1 = MathUtil.mul(R1, joint.getAnchor1());
            p1.add(x1);
            ROVector2f x2 = b2.getPosition();
            Vector2f p2 = MathUtil.mul(R2, joint.getAnchor2());
            p2.add(x2);
            g.setColor(Color.red);
            g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.x, (int) p2.y);
        }
        if (j instanceof SpringJoint) {
            SpringJoint joint = (SpringJoint) j;
            Body b1 = joint.getBody1();
            Body b2 = joint.getBody2();
            Matrix2f R1 = new Matrix2f(b1.getRotation());
            Matrix2f R2 = new Matrix2f(b2.getRotation());
            ROVector2f x1 = b1.getPosition();
            Vector2f p1 = MathUtil.mul(R1, joint.getLocalAnchor1());
            p1.add(x1);
            ROVector2f x2 = b2.getPosition();
            Vector2f p2 = MathUtil.mul(R2, joint.getLocalAnchor2());
            p2.add(x2);
            g.setColor(Color.red);
            g.drawLine((int) x1.getX(), (int) x1.getY(), (int) p1.x, (int) p1.y);
            g.drawLine((int) p1.x, (int) p1.y, (int) p2.getX(), (int) p2.getY());
            g.drawLine((int) p2.getX(), (int) p2.getY(), (int) x2.getX(), (int) x2.getY());
        }
    }

    protected void drawDust() {

        // Area shape = new Area();

//        for (int i = 0; i < dustCounter; i++) {
//            Body body = dusts[i];
//            Vector2f pts[] = ((Box) body.getShape()).getPoints(body.getPosition(), body.getRotation());
//            Vector2f v1 = pts[0];
//            Vector2f v2 = pts[1];
//            Vector2f v3 = pts[2];
//            Vector2f v4 = pts[3];
//            Polygon pol = new Polygon();
//            pol.addPoint((int) v1.x, (int) v1.y);
//            pol.addPoint((int) v2.x, (int) v2.y);
//            pol.addPoint((int) v3.x, (int) v3.y);
//            pol.addPoint((int) v4.x, (int) v4.y);

//            g.setColor(Color.black);
//            g.drawLine((int) v1.x, (int) v1.y, (int) v2.x, (int) v2.y);
//            g.drawLine((int) v2.x, (int) v2.y, (int) v3.x, (int) v3.y);
//            g.drawLine((int) v3.x, (int) v3.y, (int) v4.x, (int) v4.y);
//            g.drawLine((int) v4.x, (int) v4.y, (int) v1.x, (int) v1.y);
//            g.setColor(dustColors[i]);
//            // shape.add(new Area(pol));
//           // g.setPaint(new GradientPaint(0, 0, Color.BLUE, 300, 300, Color.red, false));
//            g.fillPolygon(pol);

//        }

        int[] iarg = {dustCounter};
        Object[] oarg = {dusts};
        painter.addToPictureList("Dust", layer, iarg, oarg, false);

    }

    private void drawClouds() {
//        for (int i = 0; i < clouds.size(); i++) {
//            clouds.get(i).Draw(g);
//        }
        int[] iarg = {dustCounter};
        Object[] oarg = {clouds};
        painter.addToPictureList("Clouds", layer, iarg, oarg, false);
    }

    protected void drawChains() {

//        Area shape = new Area();
//
//        for (int i = 0; i < chainsCounter; i++) {
//            Body body = chains[i];
//            Vector2f pts[] = ((Box) body.getShape()).getPoints(body.getPosition(), body.getRotation());
//            Vector2f v1 = pts[0];
//            Vector2f v2 = pts[1];
//            Vector2f v3 = pts[2];
//            Vector2f v4 = pts[3];
//            Polygon pol = new Polygon();
//            pol.addPoint((int) v1.x, (int) v1.y);
//            pol.addPoint((int) v2.x, (int) v2.y);
//            pol.addPoint((int) v3.x, (int) v3.y);
//            pol.addPoint((int) v4.x, (int) v4.y);
//
//            g.setColor(Color.black);
//            g.drawLine((int) v1.x, (int) v1.y, (int) v2.x, (int) v2.y);
//            g.drawLine((int) v2.x, (int) v2.y, (int) v3.x, (int) v3.y);
//            g.drawLine((int) v3.x, (int) v3.y, (int) v4.x, (int) v4.y);
//            g.drawLine((int) v4.x, (int) v4.y, (int) v1.x, (int) v1.y);
//            shape.add(new Area(pol));
//    }
//        g.setPaint(new GradientPaint(0, 150, Color.green, 300, 150, Color.red, false));
//        g.fill(shape);

        int[] iarg = {chainsCounter};
        Object[] oarg = {chains};
        painter.addToPictureList("Chains", layer, iarg, oarg, false);
    }

    public void draw() {
//        BodyList bodies = world.getBodies();
//        for (int i = 0; i < bodies.size(); i++) {
//            Body body = bodies.get(i);
//            drawBody(g, body);
//        }


        Object[] oarg = {this};
        painter.addToPictureList("draw", layer, null, oarg, false);
//        JointList joints = world.getJoints();
//        for (int i = 0; i < joints.size(); i++) {
//            Joint joint = joints.get(i);
//            drawJoint(g, joint);
//        }
//
//        ArbiterList arbs = world.getArbiters();
//        for (int i = 0; i < arbs.size(); i++) {
//            Arbiter arb = arbs.get(i);
//            Contact contacts[] = arb.getContacts();
//            int numContacts = arb.getNumContacts();
//            for (int j = 0; j < numContacts; j++) {
//                drawContact(g, contacts[j]);
//            }
//
//        }

    }

    public final void initDemo() {
        world.clear();
        world.setGravity(0.0F, 10F);
    }

    public float GetX(int x) {
        return x * grad;
    }

    public float GetY(int y) {
        return ((Space2D) space).height - y * grad - grad;
    }

    public void AddBox(int x_, int y_) {

        {
            PPBox b = new PPBox(grad, grad);
            b.setPosition(GetX(x_) + grad / 2, GetY(y_) + grad / 2);
            b.setStaticBody(true);
            world.add(b);
        }


    }

    public void AddDust(int x_, int y_) {


        if (dustCounter < dusts.length) {
            Body dust = new PPBox(3, 5);
            dust.setPosition(GetX(x_), GetY(y_));
            dust.setRotation((float) random.nextDouble());
            world.add(dust);

            dusts[dustCounter++] = dust;
        }

    }

    public void AddCircle(int x_, int y_) {


        {
            PPCircle b = new PPCircle(grad);
            b.setPosition(GetX(x_) + grad / 2, GetY(y_) + grad / 2);
            world.add(b);
        }


    }
    int period = 2;

    public void Next() {
        //   if (period == 2) {
        // begin.synchronizedRelease();

        // for (int i = 0; i < 5; i++) {
        world.step(0.1f);
        //   }

//        }
//        if (period == 0) {
//            // end.synchronizedWait();
//            period = 3;
//        }
        // long t = System.currentTimeMillis();
        {
            //draw();
            drawClouds();
            drawDust();
            drawChains();
        }
        {
            //  DrawMap();
        }
        // t = System.currentTimeMillis() - t;
        // System.out.println(t);
        period--;
    }
    public World world;

    public void PutImage(ICoordinates coor) {
        Tools.UnsupportedOperationException();
    }
    float grad;

    public void Wind(int x_, int y_) {

        for (int i = 0; i < dustCounter; i++) {
            Body body = dusts[i];
            if (((int) (body.getPosition().getX() / grad) == x_) && ((int) ((310 - body.getPosition().getY()) / grad)) == y_) {
                body.addForce(new Vector2f((float) (10000 * (1 - 2 * random.nextDouble())), -10000));
                body.setRotation((float) (body.getRotation() + Math.PI * random.nextDouble()));
            }
        }

        for (int i = 0; i < clouds.size(); i++) {
            clouds.get(i).Wind(x_, y_);

        }
    }

    public void AddChain(Body body1, Body body2, int N, float initRot) {


        Vector2f p1 = new Vector2f(body1.getPosition());
        Vector2f p2 = new Vector2f(body2.getPosition());
        float l = p1.distance(p2) / (2 * N + 1);

        Vector2f direction = new Vector2f(p2);
        direction.sub(p1);
        direction.normalise();
        direction.scale(2 * l);
        PPBox bodies[] = new PPBox[N];
        Vector2f pos = new Vector2f(p1);
        for (int i = 0; i < N; i++) {
            PPBox body = new PPBox(2 * l, 1);
            body.setDamping(0.05F);
            // body.setRotation(initRot);
            for (int j = 0; j < i; j++) {
                body.addExcludedBody(bodies[j]);
            }

            pos.add(direction);
            body.setPosition(pos.x, pos.y);
            bodies[i] = body;
            world.add(body);
            chains[chainsCounter++] = body;
        }

        for (int i = 1; i < N; i++) {
            DistanceJoint dj = new DistanceJoint(bodies[i - 1], bodies[i], new Vector2f(l / 2, 0.0F), new Vector2f(-l / 2, 0.0F), l);
            world.add(dj);
        }

        DistanceJoint dj = new DistanceJoint(body1, bodies[0], new Vector2f(0, 0.0F), new Vector2f(-l / 2, 0.0F), l);

        world.add(dj);
        dj = new DistanceJoint(body2, bodies[N - 1], new Vector2f(0, 0.0F), new Vector2f(l / 2, 0.0F), l);
        world.add(dj);
    }

    public void AddChain(ICoordinates begChain, ICoordinates endChain) {

        Coordinates2d begChain2d = (Coordinates2d) begChain;
        Coordinates2d endChain2d = (Coordinates2d) endChain;
        PPCircle b1 = new PPCircle(5F);
        b1.setStaticBody(true);
        b1.setPosition((int) (begChain2d.x * grad) + grad / 2, ((Space2D) space).height - grad - (int) (begChain2d.y * grad) + grad / 2);
        world.add(b1);
        PPCircle b2 = new PPCircle(5F);
        b2.setPosition((int) (endChain2d.x * grad) + grad / 2, ((Space2D) space).height - grad - (int) (endChain2d.y * grad) + grad / 2);
        b2.setStaticBody(true);
        world.add(b2);
        AddChain(b1, b2, (int) Math.abs(endChain2d.x - begChain2d.x - 1), 0);
    }

    public void AddCloud(int x, int y, float r) {
        clouds.add(new Cloud(x, y, r, this));
    }

    public void AddPolygon(PolygonSimple polygon) {
        PPPoly poly = new PPPoly();
        int x;
        int y;
        for (int i = 0; i < polygon.npoints; i++) {
            x = (int) (polygon.xpoints[i]);
            y = (int) (polygon.ypoints[i]);
            poly.vertex((int) x, (int) y);
        }
        poly.setStaticBody(true);
        world.add(poly);
    }

    public void DrawMap() {
//        Body b_;
//        g.setColor(Color.GREEN);
//        for (int dy = 0; dy < world.map.length; dy++) {
//            for (int dx = 0; dx < world.map[0].length; dx++) {
//                b_ = world.map[dy][dx];
//                if (b_ != null) {
//                    g.drawRect(dx, dy, 0, 0);
//                }
//            }
//        }
//        g.setColor(Color.black);

        Object[] oarg = {world};
        painter.addToPictureList("Map", layer, null, oarg, false);
    }
}
