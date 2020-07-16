/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter.Graphics.Space.G2D;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import pphys2d.phys2d.math.MathUtil;
import pphys2d.phys2d.math.Matrix2f;
import pphys2d.phys2d.math.ROVector2f;
import pphys2d.phys2d.math.Vector2f;
import pphys2d.phys2d.raw.AngleJoint;
import pphys2d.phys2d.raw.BasicJoint;
import pphys2d.phys2d.raw.Body;
import pphys2d.phys2d.raw.BodyList;
import pphys2d.phys2d.raw.Contact;
import pphys2d.phys2d.raw.DistanceJoint;
import pphys2d.phys2d.raw.FixedJoint;
import pphys2d.phys2d.raw.Joint;
import pphys2d.phys2d.raw.JointList;
import pphys2d.phys2d.raw.SlideJoint;
import pphys2d.phys2d.raw.SpringJoint;
import pphys2d.phys2d.raw.World;
import pphys2d.phys2d.raw.shapes.AbstractShape;
import pphys2d.phys2d.raw.shapes.Box;
import pphys2d.phys2d.raw.shapes.Circle;
import pphys2d.phys2d.raw.shapes.Line;
import pphys2d.phys2d.raw.strategies.QuadSpaceStrategy;
import java.awt.Frame;
import java.util.Random;

public abstract class AbstractDemo {

    Random random = new Random();

    public AbstractDemo() {
        world = new World(new Vector2f(0.0F, 10), 10, new QuadSpaceStrategy(20, 5) {

//           public void collideBodies(CollisionContext context, BodyList bodies, float dt) {
//                ((CollisionSpace) context).resolveWithHash(bodies, dt);
//            }
        });

        running = true;
        normals = true;
        contacts = true;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected void keyHit(char c) {
        if (c == 'r') {
            needsReset = true;
        }
        if (c == 'c') {
            normals = !normals;
            contacts = !contacts;
        }
    }

    private void initGUI() {
        frame = new Frame(title);
        frame.setResizable(false);
        frame.setIgnoreRepaint(true);
        frame.setSize(500, 500);
        int x = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 500D) / 2;
        int y = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 500D) / 2;
        frame.setLocation(x, y);
        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                frame.show(false);
                System.exit(0);
            }
        });
        frame.addKeyListener(new KeyAdapter() {

            public void keyTyped(KeyEvent e) {
                keyHit(e.getKeyChar());
            }

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 27) {
                    System.exit(0);
                }
            }
        });
        frame.setVisible(true);
        frame.createBufferStrategy(2);
        strategy = frame.getBufferStrategy();
    }

    public void start() {
        initGUI();
        initDemo();
        float target = 16.66667F;
        float frameAverage = target;
        long lastFrame = System.currentTimeMillis();
        float yield = 10000F;
        float damping = 0.1F;
        long renderTime = 0L;
        long logicTime = 0L;
        Body b_;
        while (running) {
            long timeNow = System.currentTimeMillis();
            frameAverage = (frameAverage * 10F + (float) (timeNow - lastFrame)) / 11F;
            lastFrame = timeNow;
            yield += yield * (target / frameAverage - 1.0F) * damping + 0.05F;
            for (int i = 0; (float) i < yield; i++) {
                Thread.yield();
            }

            long beforeRender = System.currentTimeMillis();
            Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
            g.setColor(Color.white);
            g.fillRect(0, 0, 500, 500);
            draw(g);
            g.setColor(Color.GREEN);
//            {for (int dy = 0; dy < world.map.length; dy++) {
//                for (int dx = 0; dx < world.map[0].length; dx++) {
//                    b_=world.map[dy][dx];
//                    if (b_!=null){
//                        g.drawRect(dx, dy, 0, 0);
//                    }
//                }
//            } }
            {
                for (int i = 0; i < world.getBodies().size(); i++) {
                    Body bi = world.getBodies().get(i);
                    for (int i_ = 0; i_ < ((AbstractShape) bi.getShape()).maskY.size(); i_++) {
                        g.drawRect(((Integer) ((AbstractShape) bi.getShape()).maskX.get(i_)).intValue(), ((Integer) ((AbstractShape) bi.getShape()).maskY.get(i_)).intValue(), 0, 0);
                    }
                }
            }
            g.setColor(Color.black);
            renderGUI(g);
            g.setColor(Color.black);
            g.drawString("FAv: " + frameAverage, 10, 50);
            g.drawString("FPS: " + (int) (1000F / frameAverage), 10, 70);
            g.drawString("Yield: " + yield, 10, 90);
            g.drawString("Arbiters: " + world.getArbiters().size(), 10, 110);
            g.drawString("Bodies: " + world.getBodies().size(), 10, 130);
            g.drawString("R: " + renderTime, 10, 150);
            g.drawString("L: " + logicTime, 10, 170);
            g.drawString("Energy: " + world.getTotalEnergy(), 10, 190);
            g.dispose();
            strategy.show();
            renderTime = System.currentTimeMillis() - beforeRender;
            long beforeLogic = System.currentTimeMillis();
            for (int i = 0; i < 5; i++) {
                world.step();
            }
            {
//                if (random.nextDouble()<0.25){
//                Body body4 = new Body("dust", new Box(5, 3), 100F);
//                body4.setPosition((float) (100 + 300 * random.nextDouble()), 60F);
//                world.add(body4);}
            }
            logicTime = System.currentTimeMillis() - beforeLogic;
            if (needsReset) {
                world.clear();
                initDemo();
                needsReset = false;
                frameAverage = target;
                yield = 10000F;
            }
            update();
        }
    }

    protected void update() {
    }

    protected void renderGUI(Graphics2D g) {
        g.setColor(Color.black);
        g.drawString("R - Restart Demo", 15, 430);
    }

    protected void drawContact(Graphics2D g, Contact contact) {
        int x = (int) contact.getPosition().getX();
        int y = (int) contact.getPosition().getY();
        if (contacts) {
            g.setColor(Color.blue);
            g.fillOval(x - 3, y - 3, 6, 6);
        }
        if (normals) {
            int dx = (int) (contact.getNormal().getX() * 10F);
            int dy = (int) (contact.getNormal().getY() * 10F);
            g.setColor(Color.darkGray);
            g.drawLine(x, y, x + dx, y + dy);
        }
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
        if (body.getShape() instanceof Polygon) {
            drawPolygonBody(g, body, (Polygon) body.getShape());
        }
    }

    protected void drawPolygonBody(Graphics2D g, Body body, Polygon poly) {
//        g.setColor(Color.black);
//        ROVector2f verts[] = poly.getVertices(body.getPosition(), body.getRotation());
//        int i = 0;
//        int j = verts.length - 1;
//        for (; i < verts.length; i++) {
//            g.drawLine((int) (0.5F + verts[i].getX()), (int) (0.5F + verts[i].getY()), (int) (0.5F + verts[j].getX()), (int) (0.5F + verts[j].getY()));
//            j = i;
//        }
    }

    protected void drawLineBody(Graphics2D g, Body body, Line line) {
        g.setColor(Color.black);
        Vector2f verts[] = line.getVertices(body.getPosition(), body.getRotation());
        g.drawLine((int) verts[0].getX(), (int) verts[0].getY(), (int) verts[1].getX(), (int) verts[1].getY());
    }

    protected void drawCircleBody(Graphics2D g, Body body, Circle circle) {
        g.setColor(Color.black);
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

    protected void draw(Graphics2D g) {
        BodyList bodies = world.getBodies();
        for (int i = 0; i < bodies.size(); i++) {
            Body body = bodies.get(i);
            drawBody(g, body);
        }

        JointList joints = world.getJoints();
        for (int i = 0; i < joints.size(); i++) {
            Joint joint = joints.get(i);
            drawJoint(g, joint);
        }

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
        System.out.println("Initialising:" + getTitle());
        init(world);
    }

    protected abstract void init(World world1);
    protected Frame frame;
    protected String title;
    protected World world;
    public boolean running;
    private BufferStrategy strategy;
    protected boolean needsReset;
    public boolean normals;
    public boolean contacts;
}
