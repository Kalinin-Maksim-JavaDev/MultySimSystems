/*
 * Phys2D - a 2D physics engine based on the work of Erin Catto. The
 * original source remains:
 *
 * Copyright (c) 2006 Erin Catto http://www.gphysics.com
 *
 * This source is provided under the terms of the BSD License.
 *
 * Copyright (c) 2006, Phys2D
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 *  * Redistributions of source code must retain the above
 *    copyright notice, this list of conditions and the
 *    following disclaimer.
 *  * Redistributions in binary form must reproduce the above
 *    copyright notice, this list of conditions and the following
 *    disclaimer in the documentation and/or other materials provided
 *    with the distribution.
 *  * Neither the name of the Phys2D/New Dawn Software nor the names of
 *    its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 */
package Platform.Util.Profiler.observercode.Diagram;

import Platform.Concurrent.Factory;
import Platform.Concurrent.ILathce;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import pphys2d.phys2d.math.MathUtil;
import pphys2d.phys2d.math.Matrix2f;
import pphys2d.phys2d.math.ROVector2f;
import pphys2d.phys2d.math.Vector2f;
import pphys2d.phys2d.raw.AngleJoint;
import pphys2d.phys2d.raw.Arbiter;
import pphys2d.phys2d.raw.ArbiterList;
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
import pphys2d.phys2d.raw.shapes.Box;
import pphys2d.phys2d.raw.shapes.Circle;
import pphys2d.phys2d.raw.shapes.Line;
import pphys2d.phys2d.raw.shapes.Polygon;
import pphys2d.phys2d.raw.strategies.QuadSpaceStrategy;

/**
 * A common demo box super class.
 *
 * @author Kevin Glass
 */
public abstract class AbstractDemo {

    /** The frame displaying the demo */
    public NewJFrame frame;
    BufferedImage[] img;
    Graphics2D[] imgGraphics;
    int layers = 3;
    /** The title of the current demo */
    protected String title;
    /** The world containing the physics model */
    protected World world ;
    /** True if the simulation is running */
    protected boolean running = false;
    /** The rendering strategy */
    /** True if we should reset the demo on the next loop */
    protected boolean needsReset;
    /** True if we should render normals */
    private boolean normals = true;
    /** True if we should render contact points */
    private boolean contacts = true;
    int maxstrong = 0;
    SimpleGraphView sgv = new SimpleGraphView(); //We create our graph in here
    ILathce outputing = Factory.createLathce(true, true);
    String labels = "";

    /**
     * Create a new demo
     *
     * @param title The title of the demo
     */
    public AbstractDemo(String title) {
        this.title = title;
        world = new World(new Vector2f(0.0f, 10.0f), 10, new QuadSpaceStrategy(20, 5));
        world.map= new Body[800][800];
    }

    /**
     * Retrieve the title of the demo
     *
     * @return The title of the demo
     */
    public String getTitle() {
        return title;
    }

    /**
     * Notification that a key was pressed
     *
     * @param c The character of key hit
     */
    protected void keyHit(char c) {
        if (c == 'r') {
            needsReset = true;
        }
        if (c == 'c') {
            normals = !normals;
            contacts = !contacts;
        }
    }

    /**
     * Initialise the GUI
     */
    private void initGUI() {
        frame = new NewJFrame() {

            @Override
            public void ReadText() {
                AbstractDemo.this.ReadText();
            }
        };
        // frame.setResizable(false);
        //   frame.setIgnoreRepaint(true);

        frame.GetPanel().setSize(world.map[0].length, world.map.length);
        img = new BufferedImage[layers];
        imgGraphics = new Graphics2D[layers];
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < img.length; i++) {
            img[i] = new BufferedImage(frame.GetPanel().getWidth(), frame.GetPanel().getHeight(), BufferedImage.TYPE_INT_ARGB);
            imgGraphics[i] = (Graphics2D) img[i].getGraphics();
            imgGraphics[i].setBackground(new Color(0, true));
            imgGraphics[i].setColor(Color.white);
            imgGraphics[i].clearRect(0, 0, frame.GetPanel().getWidth(), frame.GetPanel().getHeight());
            imgGraphics[i].setRenderingHints(rh);
        }
        imgGraphics[0].setBackground(Color.white);
        imgGraphics[0].clearRect(0, 0, frame.GetPanel().getWidth(), frame.GetPanel().getHeight());



        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                running = false;
                System.exit(0);
            }
        });
        frame.GetPanel().addKeyListener(new KeyAdapter() {

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
        frame.GetPanel().setFocusable(true);

        //sgv.Show();


    }

    abstract public void SplitLink();

    /**
     * Start the simulation running
     */
    public void start() {
        initGUI();
        initDemo();


        new Thread(new Runnable() {

            float target = 1000 / 60.0f;
            float frameAverage = target;
            long lastFrame = System.currentTimeMillis();
            float yield = 10000f;
            float damping = 0.1f;
            long renderTime = 0;
            long logicTime = 0;
            int counter = 0;

            public void run() {
                running = true;
                new Thread(new Runnable() {

                    public void run() {

                        while (running) {
                            outputing.synchronizedWait();

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(AbstractDemo.class.getName()).log(Level.SEVERE, null, ex);
                            }
//                            synchronized (AbstractDemo.this) {
//                                SplitLink();
//                            }
                            String text_ = "digraph g{";

                            text_ = text_ + "\n" + labels;
                            for (int i = 0; i < world.getJoints().size(); i++) {
                                Joint joint_ = world.getJoints().get(i);
                                if (joint_ instanceof SpringJoint_) {
                                    PPCircle_ b1 = ((PPCircle_) ((SpringJoint_) joint_).getBody1());
                                    PPCircle_ b2 = ((PPCircle_) ((SpringJoint_) joint_).getBody2());
                                    boolean print_ = true;
                                    if (b2.name.indexOf("var")>-1) {
                                        if (b2.inPutAttempt < 0) {
                                            print_ = false;
                                        } else {
                                             text_ = text_ + "\n"+b2.name+"[label=\""+b2.name+" ("+b2.inPutAttempt+" writes)\"]";
                                        }
                                    }
                                    if (print_) {
                                        text_ = text_ + "\n" + (b1.name + " -> " + b2.name);//+" [label="+linkCounter+"] ");// + " [" + strong + "]");
                                    }
                                }
                            }
                            text_ = text_ + "\n" + "}";
                            frame.GetTextArea().setText(text_);
                        }
                    }
                }).start();
                while (running) {
                    // adaptive timing loop from Master Onyx
                    long timeNow = System.currentTimeMillis();
                    frameAverage = (frameAverage * 10 + (timeNow - lastFrame)) / 11;
                    lastFrame = timeNow;

                    yield += yield * ((target / frameAverage) - 1) * damping + 0.05f;

                    for (int i = 0; i < yield; i++) {
                        Thread.yield();
                    }

                    // render
                    long beforeRender = System.currentTimeMillis();
                    Graphics2D g = (Graphics2D) imgGraphics[1];


                    draw(g, (Graphics2D) imgGraphics[2]);
//            renderGUI(g);
//            g.setColor(Color.black);
//            g.drawString("FAv: " + frameAverage, 10, 50);
//            g.drawString("FPS: " + (int) (1000 / frameAverage), 10, 70);
//            g.drawString("Yield: " + yield, 10, 90);
//            g.drawString("Arbiters: " + world.getArbiters().size(), 10, 110);
//            g.drawString("Bodies: " + world.getBodies().size(), 10, 130);
//            g.drawString("R: " + renderTime, 10, 150);
//            g.drawString("L: " + logicTime, 10, 170);
//            g.drawString("Energy: " + world.getTotalEnergy(), 10, 190);

                    Draw();
                    renderTime = System.currentTimeMillis() - beforeRender;

                    // update data model
                    long beforeLogic = System.currentTimeMillis();
                    for (int i = 0; i < 5; i++) {

                        synchronized (AbstractDemo.this) {
                            world.step();
                        }
                        ApplyFrictionForce();
                    }
//            counter++;
//
//            if (counter > 10) {
//
//                for (int j = 0; j < world.getJoints().size(); j++) {
//                    Joint joint = world.getJoints().get(j);
//                    if (joint instanceof SpringJoint) {
//                        ((SpringJoint) joint).setSpringSize(joint.getBody1().getPosition().distance(joint.getBody2().getPosition()));
//                    }
//                }
//                counter = 0;
//            }

                    logicTime = System.currentTimeMillis() - beforeLogic;

                    if (needsReset) {
                        world.clear();
                        initDemo();
                        needsReset = false;
                        frameAverage = target;
                        yield = 10000f;
                    }

                    update();
                }
                outputing.synchronizedRelease();
            }
        }).start();
    }

    void ApplyFrictionForce() {
        float k = 100;
        BodyList bodies = world.getBodies();
        for (int j = 0; j < bodies.size(); j++) {
            Body body_ = bodies.get(j);
            if (body_ instanceof PPCircle_) {
                //  k=((PPCircle_) body_).linkTo.size()*1000;
                body_.addForce(new Vector2f(-k * body_.getVelocity().getX(), -k * body_.getVelocity().getY()));
            }
        }
//        JointList joints = world.getJoints();
//        for (int j = 0; j < joints.size(); j++) {
//
//            Joint joint =joints.get(j);
//            if (joint instanceof SpringJoint ){
//            ((SpringJoint)joint).setSpringSize(0.9f*((SpringJoint)joint).getSpringSize());
//            }
//
//        }
    }

    /**
     * Update the demo - just in case we want to add anything over
     * the top
     */
    protected void update() {
    }

    /**
     * Demo customisable GUI render
     *
     * @param g The graphics context to use for rendering here
     */
    protected void renderGUI(Graphics2D g) {
        g.setColor(Color.black);
        g.drawString("R - Restart Demo", 15, 430);
    }

    /**
     * Draw a specific contact point determined from the simulation
     *
     * @param g The graphics context on which to draw
     * @param contact The contact to draw
     */
    protected void drawContact(Graphics2D g, Contact contact) {
        int x = (int) contact.getPosition().getX();
        int y = (int) contact.getPosition().getY();
        if (contacts) {
            g.setColor(Color.blue);
            g.fillOval(x - 3, y - 3, 6, 6);
        }

        if (normals) {
            int dx = (int) (contact.getNormal().getX() * 10);
            int dy = (int) (contact.getNormal().getY() * 10);
            g.setColor(Color.darkGray);
            g.drawLine(x, y, x + dx, y + dy);
        }
    }

    /**
     * Draw a body
     *
     * @param g The graphics contact on which to draw
     * @param body The body to be drawn
     */
    protected void drawBody(Graphics2D g, Body body) {
        if (body instanceof PPCircle_) {

            float x = body.getPosition().getX();
            float y = body.getPosition().getY();
            float r = body.getShape().getBounds().getHeight();
            int conSize = ((PPCircle_) body).springJoint.size();

            g.setFont(new Font(Font.DIALOG, Font.BOLD, 7 + conSize));
            int color = 128 - 1 * conSize;
            if (color < 0) {
                color = 0;
            }
            g.setColor(new Color(255, color, color));
            g.drawString(String.valueOf(((PPCircle_) body).name), (int) (x - r) - ((PPCircle_) body).name.length() * g.getFont().getSize() / 10, (int) (y - r));

            g.setColor(((PPCircle_) body).GetColor());


        }

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

    /**
     * Draw a polygon into the demo
     *
     * @param g The graphics to draw the poly onto
     * @param body The body describing the poly's position
     * @param poly The poly to be drawn
     */
    protected void drawPolygonBody(Graphics2D g, Body body, Polygon poly) {
        //      g.setColor(Color.black);

        ROVector2f[] verts = poly.getVertices(body.getPosition(), body.getRotation());
        for (int i = 0, j = verts.length - 1; i < verts.length; j = i, i++) {
            g.drawLine(
                    (int) (0.5f + verts[i].getX()),
                    (int) (0.5f + verts[i].getY()),
                    (int) (0.5f + verts[j].getX()),
                    (int) (0.5f + verts[j].getY()));
        }
    }

    /**
     * Draw a line into the demo
     *
     * @param g The graphics to draw the line onto
     * @param body The body describing the line's position
     * @param line The line to be drawn
     */
    protected void drawLineBody(Graphics2D g, Body body, Line line) {
//        g.setColor(Color.black);
//
//		float x = body.getPosition().getX();
//		float y = body.getPosition().getY();
//		float dx = line.getDX();
//		float dy = line.getDY();
//
//		g.drawLine((int) x,(int) y,(int) (x+dx),(int) (y+dy));
        Vector2f[] verts = line.getVertices(body.getPosition(), body.getRotation());
        g.drawLine(
                (int) verts[0].getX(),
                (int) verts[0].getY(),
                (int) verts[1].getX(),
                (int) verts[1].getY());
    }

    /**
     * Draw a circle in the world
     *
     * @param g The graphics contact on which to draw
     * @param body The body to be drawn
     * @param circle The shape to be drawn
     */
    protected void drawCircleBody(Graphics2D g, Body body, Circle circle) {
        // g.setColor(Color.black);
        float x = body.getPosition().getX();
        float y = body.getPosition().getY();
        float r = circle.getRadius();
        float rot = body.getRotation();
        float xo = (float) (Math.cos(rot) * r);
        float yo = (float) (Math.sin(rot) * r);

        g.drawOval((int) (x - r), (int) (y - r), (int) (r * 2), (int) (r * 2));
        g.drawLine((int) x, (int) y, (int) (x + xo), (int) (y + yo));


    }

    /**
     * Draw a box in the world
     *
     * @param g The graphics contact on which to draw
     * @param body The body to be drawn
     * @param box The shape to be drawn
     */
    protected void drawBoxBody(Graphics2D g, Body body, Box box) {
        Vector2f[] pts = box.getPoints(body.getPosition(), body.getRotation());

        Vector2f v1 = pts[0];
        Vector2f v2 = pts[1];
        Vector2f v3 = pts[2];
        Vector2f v4 = pts[3];

        // g.setColor(Color.black);
        g.drawLine((int) v1.x, (int) v1.y, (int) v2.x, (int) v2.y);
        g.drawLine((int) v2.x, (int) v2.y, (int) v3.x, (int) v3.y);
        g.drawLine((int) v3.x, (int) v3.y, (int) v4.x, (int) v4.y);
        g.drawLine((int) v4.x, (int) v4.y, (int) v1.x, (int) v1.y);
    }

    /**
     * Draw a joint
     *
     * @param g The graphics contact on which to draw
     * @param j The joint to be drawn
     */
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
            g.drawLine((int) p1.x, (int) p1.y, (int) (p1.x + VA.x * 20), (int) (p1.y + VA.y * 20));
            g.drawLine((int) p1.x, (int) p1.y, (int) (p1.x + VB.x * 20), (int) (p1.y + VB.y * 20));
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
        if (j instanceof SpringJoint_) {
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


            int color = 200;// - 255 * ((SpringJoint_) j).strong / maxstrong;;
            if (color < 0) {
                color = 0;
            }
            g.setColor(new Color(color, color, color));
            g.drawLine((int) x1.getX(), (int) x1.getY(), (int) p1.x, (int) p1.y);
            g.drawLine((int) p1.x, (int) p1.y, (int) p2.getX(), (int) p2.getY());
            g.drawLine((int) p2.getX(), (int) p2.getY(), (int) x2.getX(), (int) x2.getY());
        }
    }

    /**
     * Draw the whole simulation
     *
     * @param g The graphics context on which to draw
     */
    protected void draw(Graphics2D g, Graphics2D g2) {
        BodyList bodies = world.getBodies();

        for (int i = 0; i < bodies.size(); i++) {
            Body body = bodies.get(i);

            drawBody(g, body);
        }

        JointList joints = world.getJoints();

        for (int i = 0; i < joints.size(); i++) {
            Joint joint = joints.get(i);

            drawJoint(g2, joint);
        }

        ArbiterList arbs = world.getArbiters();

        for (int i = 0; i < arbs.size(); i++) {
            Arbiter arb = arbs.get(i);

            Contact[] contacts = arb.getContacts();
            int numContacts = arb.getNumContacts();

            for (int j = 0; j < numContacts; j++) {
                drawContact(g, contacts[j]);
            }
        }
    }

    /**
     * Initialise the demo - clear the world
     */
    public final void initDemo() {
        world.clear();
        world.setGravity(0, 10);

        System.out.println("Initialising:" + getTitle());
        init(world);
    }

    /**
     * Should be implemented by the demo, add the bodies/joints
     * to the world.
     *
     * @param world The world in which the simulation is going to run
     */
    protected abstract void init(World world);

    private void Draw() {
        for (int i = 1; i < img.length; i++) {
            imgGraphics[0].drawImage(img[i], 0, 0, null);
            imgGraphics[i].clearRect(0, 0, frame.GetPanel().getWidth(), frame.GetPanel().getHeight());
        }
        frame.GetPanel().getGraphics().drawImage(img[0], 0, 0, null);
        imgGraphics[0].clearRect(0, 0, frame.getWidth(), frame.getHeight());
    }

    protected void ReadText() {
    }
}
