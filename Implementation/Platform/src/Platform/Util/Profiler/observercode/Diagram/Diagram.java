package Platform.Util.Profiler.observercode.Diagram;

import Global.Tools;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pphys2d.bodies.PPBody;
import pphys2d.bodies.PPLine;
import pphys2d.phys2d.math.Vector2f;
import pphys2d.phys2d.raw.Body;
import pphys2d.phys2d.raw.BodyList;
import pphys2d.phys2d.raw.Joint;
import pphys2d.phys2d.raw.JointList;
import pphys2d.phys2d.raw.SpringJoint;
import pphys2d.phys2d.raw.SpringyAngleJoint;
import pphys2d.phys2d.raw.World;

/**
 * A test to demonstrate the distance constraint
 *
 * @author guRuQu
 */
public class Diagram extends AbstractDemo {

    /**
     * Create a new test
     */
    String deliver = "->";
    HashMap<String, PPBody> blocks = new HashMap<String, PPBody>();
    Random random = new Random();
    float radius = 10;
    Vector2f lastGenPos = new Vector2f(0, 0);
    float lastGenRadius = radius;
    double lastGenAngle = 10;
    ArrayList<PPCircle_> selected = new ArrayList<PPCircle_>();
    private double __angle = 0;
    String firstold = "";
    int currentTextLine = 0;
    int linkCounter = 0;

    public Diagram() {
        super("Joint test");

    }

    protected void init(final World world) {
//        lastGenPos.x = radius +  (frame.GetPanel().getWidth() - 2 * radius)/2;
//        lastGenPos.y = radius + (frame.GetPanel().getHeight() - 2 * radius)/2;
        /* {
        Body b1 = new PPCircle(20);
        b1.setPosition(100, 200);
        world.add(b1);

        Body b2 = new PPCircle(20);
        b2.setPosition(300, 200);
        world.add(b2);

        final Body b3 = new PPCircle(20);
        b3.setPosition(100, 300);
        world.add(b3);

        final Body b4 = new PPCircle(20);
        b4.setPosition(200, 300);
        world.add(b4);
        final Body b5 = new PPCircle(20);
        b5.setPosition(250, 350);
        world.add(b5);

        AddLink(b1, b2);
        AddLink(b1, b3);
        AddLink(b2, b4);
        AddLink(b1, b4);
        AddLink(b5, b4);

        }*/


        //ReadText();

//        BodyList bodies = world.getBodies();
//        for (int i = 0; i < bodies.size(); i++) {
//            Body body = bodies.get(i);
//            if (body instanceof PPCircle_) {
//                body.setPosition(frame.GetPanel().getWidth()/2,frame.GetPanel().getWidth()/2);
//            }
//        }

        // world.setDamping(100);
        world.setGravity(0, 0);
        final float l = 5;
        float titleH = 0;
        float w = frame.GetPanel().getWidth() - l;
        float h = frame.GetPanel().getHeight() - l;
        PPLine l1 = new PPLine(l, l + titleH, w - l, 0);
        PPLine l2 = new PPLine(w, l + titleH, 0, h - l);
        PPLine l3 = new PPLine(l, h, w - l, 0);
        PPLine l4 = new PPLine(l, l + titleH, 0, h - l - titleH);

        l1.setStaticBody(true);
        l2.setStaticBody(true);
        l3.setStaticBody(true);
        l4.setStaticBody(true);
        world.add(l1);
        world.add(l2);
        world.add(l3);
        world.add(l4);
        SetNewStartGenPos();

        MouseAdapter ml = new MouseAdapter() {

            int x;
            int y;
            int x0;
            int y0;
            boolean singleSelect = true;
            // PPCircle_ body;

            public void mousePressed(MouseEvent e) {

                //   body = null;
//                BodyList bodies = world.getBodies();
//                for (int i = 0; i < bodies.size(); i++) {
//                    Body body_ = bodies.get(i);
//                    if (body_ instanceof PPCircle_) {
//                        if (body_.getPosition().distance(new Vector2f(x, y)) < 10) {
//                            body = (PPCircle_) body_;
//                        }
//                    }
//                }

                // if (body == null) {
                synchronized (selected) {
                    if (singleSelect) {
                        SelectOne(e);
                        if (selected.size() == 1) {
                            selected.get(0).setEnabled(false);
                        } else {
                            singleSelect = false;
                        }
                    } else {
                        if (selected.size() > 0) {
                            singleSelect = true;
                            for (int i = 0; i < selected.size(); i++) {
                                selectedSetPosition(selected.get(i), selected.get(i).getX() + e.getX() - x0, selected.get(i).getY() + e.getY() - y0);
                            }
                            selected.clear();
                        }

                    }
                }
                // }
                x = e.getX();
                y = e.getY();
            }

            public void mouseReleased(MouseEvent e) {

//                if (body != null) {
//                    if (e.getButton() == 1) {
//
//                        body.setPosition(e.getX(), e.getY());
//                        for (int i = 0; i < body.springJoint.size(); i++) {
//                            body.springJoint.get(i).setSpringSize(body.getPosition().distance(body.linkTo.get(i).getPosition()));
//                        }
//                    }
//                } else {
                synchronized (selected) {
                    if (singleSelect) {
                        //     selected.get(0).setPosition(selected.get(0).getX() + e.getX() - x, selected.get(0).getY() + e.getY() - y);
                        if (selected.size() == 1) {
                            selected.get(0).setEnabled(true);
                            selected.clear();
                        }

                    } else {
//                        SelectScope(e, x, y);
//                        x0 = (x + e.getX()) / 2;
//                        y0 = (y + e.getY()) / 2;
//                        System.out.println(selected.size());
                        //  }
                    }

                }

            }

            @Override
            public void mouseDragged(MouseEvent e) {
                synchronized (selected) {
                    if (singleSelect) {
                        if (selected.size() == 1) {
                            selectedSetPosition(selected.get(0), e.getX(), e.getY());
                        }
                        //  selected.clear();
                    } else {
                        selected.clear();
                        SelectScope(e, x, y);
                        x0 = (x + e.getX()) / 2;
                        y0 = (y + e.getY()) / 2;
                        //  System.out.println(selected.size());
                    }
                }
            }

            private void SelectScope(MouseEvent e, int x, int y) {
                BodyList bodies = world.getBodies();
                for (int i = 0; i < bodies.size(); i++) {
                    Body body_ = bodies.get(i);
                    if (body_ instanceof PPCircle_) {
                        if ((Math.min(x, e.getX()) <= body_.getPosition().getX())
                                && (Math.max(x, e.getX()) >= body_.getPosition().getX())
                                && (Math.min(y, e.getY()) <= body_.getPosition().getY())
                                && (Math.max(y, e.getY()) >= body_.getPosition().getY())) {
                            selected.add((PPCircle_) body_);
                        }
                    }
                }
                //  System.out.println(selected.size());
            }

            private void SelectOne(MouseEvent e) {
                BodyList bodies = world.getBodies();
                for (int i = 0; i < bodies.size(); i++) {
                    Body body_ = bodies.get(i);
                    if (body_ instanceof PPCircle_) {
                        if ((body_.getPosition().distance(new Vector2f(e.getX(), e.getY())) < radius)) {
                            selected.add((PPCircle_) body_);
                        }
                    }
                }
                // System.out.println(selected.size());
            }

            private void selectedSetPosition(PPCircle_ body, float x, float y) {
                body.setPosition(x, y);
                for (int i = 0; i < body.springJoint.size(); i++) {
                    if (body.linkTo.get(i).linkTo.size() > 1) {
                        body.springJoint.get(i).setSpringSize(body.getPosition().distance(body.linkTo.get(i).getPosition()));
                    }
                }

            }
        };
        frame.GetPanel().addMouseMotionListener(ml);
        frame.GetPanel().addMouseListener(ml);



    }

    PPCircle_ Getneighbor(PPCircle_ b1, PPCircle_ exludedBody) {
        int n = -1;
        for (int j = 0; j < b1.linkTo.size(); j++) {
            if (b1.linkTo.get(j) == exludedBody) {
                n = j + 1;
                if (n > 1) {
                    n = 0;
                }
            }
        }
        if (n < 0) {
            Tools.UnsupportedOperationException();
        }
        return b1.linkTo.get(n);
    }

    public void SplitLink() {
        for (int i = 0; i < world.getJoints().size(); i++) {
            Joint joint_ = world.getJoints().get(i);
            if (joint_ instanceof SpringJoint_) {
                PPCircle_ b1 = (PPCircle_) ((SpringJoint_) joint_).getBody1();
                PPCircle_ b2 = (PPCircle_) ((SpringJoint_) joint_).getBody2();
                if ((b1.linkTo.size() == 2) && (b2.linkTo.size() == 2)) {


                    PPCircle_ b1_neighbor = Getneighbor(b1, b2);
                    PPCircle_ b2_neighbor = Getneighbor(b2, b1);
                    world.remove(joint_);
                    AddLink(b1_neighbor, b2_neighbor, ((SpringJoint_) joint_).strong);
                }
            }
        }
    }

    /**
     * Entry point to the test
     *
     * @param args
     */
    public static void main(String[] args) {
        (new Diagram()).start();
    }

    private boolean AddLink(PPCircle_ b1, PPCircle_ b2, int strong) {
        if (!b1.linkTo.contains(b2)) {
            if (strong > maxstrong) {
                maxstrong = strong;
            }
            b1.linkTo.add(b2);
            b2.linkTo.add(b1);
            // b1.setMass(b1.getMass()+100);
            //   b2.setMass(b2.getMass()+100);
            SpringyAngleJoint saj1 = new SpringyAngleJoint(b1, b2, new Vector2f(), new Vector2f(), 0, (float) Math.PI * 2);
            SpringyAngleJoint saj2 = new SpringyAngleJoint(b1, b2, new Vector2f(), new Vector2f(), 0, (float) Math.PI * 2);
            SpringJoint_ daj = new SpringJoint_(b1, b2, new Vector2f(b1.getPosition()), new Vector2f(b2.getPosition()));
            daj.setCompressedSpringConst(0);
            daj.setStretchedSpringConst(SpringJoint_.stretchedSpringConst);
            //   daj.setSpringSize(5 * radius);
            //daj.setMaxSpringSize(5 * radius);
            daj.strong = strong;
            world.add(daj);
            world.add(saj1);
            world.add(saj2);
            b1.springJoint.add(daj);
            b2.springJoint.add(daj);

            b1.setMass(b1.getMass() + PPCircle_.defmass / 10);

            linkCounter++;
            //   sgv.g.addEdge(String.valueOf(linkCounter), b1.name, b2.name);

            return true;
        } else {
            SpringJoint daj = b1.springJoint.get(b1.linkTo.indexOf(b2));
            daj.setStretchedSpringConst(daj.getStretchedSpringConst() + SpringJoint_.stretchedSpringConst / 10);
            return false;
        }
    }

    private void setRandomPosition(Body body, int n) {

        body.setPosition((float) (lastGenPos.x + n * radius * Math.cos(Math.toRadians(__angle))), (float) (lastGenPos.y + n * radius * Math.sin(Math.toRadians(__angle))));
        IncAngle();
        BodyList bodies = world.getBodies();
        for (int i = 0; i < bodies.size(); i++) {
            Body body_ = bodies.get(i);
            if (body_ instanceof PPCircle_) {
                if (body.getPosition().distance(body_.getPosition()) < 2 * radius) {
                    setRandomPosition(body, n + 1);
                }
            }
        }
//        lastGenPos.x = body.getPosition().getX();
//        lastGenPos.y = body.getPosition().getY();
    }

    protected void ReadText() {

        String[] st = frame.getText1();

        for (; currentTextLine < st.length; currentTextLine++) {

            String first = GetString("", st[currentTextLine], deliver);
            String second = GetString(deliver, st[currentTextLine], " *\\[");
            String strong = GetString("\\[label=\"[А-Яа-яёЁ]*", st[currentTextLine], "\"\\]");

            if ((first != null) && (second != null) && (strong != null)) {
                strong = strong.replaceAll(" *", "");


                AddAccociation(first, second, Integer.valueOf(strong), "", "");

            }
        }

        JointList joints = world.getJoints();

//        for (int i = 0; i < joints.size(); i++) {
//            Joint joint = joints.get(i);
//            if (joint instanceof SpringJoint_) {
//                ((SpringJoint_) joint).color = 255 * ((SpringJoint_) joint).strong / maxstrong;
//            }
//        }


//         BodyList bodies = world.getBodies();
//                for (int i = 0; i < bodies.size(); i++) {
//                    Body body_ = bodies.get(i);
//                    if (body_ instanceof PPCircle_) {
//                        for (int j= 0; j < ((PPCircle_)body_).springJoint.size(); j++) {
//                         ((PPCircle_)body_).springJoint.get(j).setSpringSize(radius*((PPCircle_)body_).springJoint.size());
//                        }
//
//                    }
//                }
    }

    private String GetString(String left, String string, String right) {
        String res = null;
        Pattern pattern = Pattern.compile(left + ".*" + right);
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            res = matcher.group().replaceAll(left, "").replaceAll(right, "");
        }
        return res;
    }

    private PPCircle_ newPPCircle_(float radius) {
        return new PPCircle_(radius) {

            @Override
            Color GetColor() {
                synchronized (selected) {
                    if (selected.contains(this)) {
                        return Color.green;
                    } else {
                        return super.GetColor();
                    }
                }
            }
        };
    }

    private void SetNewStartGenPos_() {


        lastGenPos.y += 50;


    }

    private void IncAngle_() {
//        __angle += 10;
//        if (__angle >= 360) {
//            __angle = 0;
//        }
    }

    private void SetNewStartGenPos() {

        lastGenPos.x = (float) (radius + (frame.GetPanel().getWidth() - 2 * radius) / 2 + lastGenRadius * Math.cos(Math.toRadians(lastGenAngle)));
        lastGenPos.y = (float) (radius + (frame.GetPanel().getHeight() - 2 * radius) / 2 + lastGenRadius * Math.sin(Math.toRadians(lastGenAngle)));
        lastGenAngle += 10;
        if (lastGenAngle >= 360) {
            lastGenAngle = 0;
            lastGenRadius += 2 * radius;
        }

    }

    private void IncAngle() {
        __angle += 10;
        if (__angle >= 360) {
            __angle = 0;
        }
    }

    public synchronized boolean AddAccociation(String first, String second, int strong, String label1, String label2) {
        boolean res = false;
        if (!running) {
            res = false;
        } else {


            if ((!first.equalsIgnoreCase(second))) {
                String first_mod = first;

                if (!firstold.matches(first_mod)) {
                    firstold = first_mod;

                    //   System.out.println(firstold);
                }

                PPCircle_ b1 = (PPCircle_) blocks.get(first);
                if (b1 == null) {
                    b1 = newPPCircle_(radius);
                    b1.name = first;
                    //  sgv.g.addVertex(b1.name);
                    setRandomPosition(b1, 0);
                    world.add(b1);
                    blocks.put(first, b1);
                    labels = labels + "\n" + (label1);
                }
                PPCircle_ b2 = (PPCircle_) blocks.get(second);
                if (b2 == null) {
                    b2 = newPPCircle_(radius);
                    b2.name = second;
                    //    sgv.g.addVertex(b2.name);
                    setRandomPosition(b2, 0);
                    world.add(b2);
                    blocks.put(second, b2);
                    labels = labels + "\n" + label2;
                }

                b2.inPutAttempt++;
                if (AddLink(b1, b2, strong)) {
                    //   sgv.layout.initialize();
                    outputing.synchronizedRelease();
                    //System.out.println(first + " -> " + second);//+" [label="+linkCounter+"] ");// + " [" + strong + "]");
                    SetNewStartGenPos();
                    res = true;
                }

            }
        }

        return res;
    }
}
