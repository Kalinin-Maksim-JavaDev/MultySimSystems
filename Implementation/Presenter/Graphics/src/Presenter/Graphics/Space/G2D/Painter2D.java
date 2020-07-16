/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter.Graphics.Space.G2D;

import Presenter.Graphics.Space.G2D.Images.Sprite2D_;
import Presenter.Graphics.Space.G2D.Images.GradientArea;
import Model.Game.Presenter.Space.Painter;
import Model.Game.Presenter.Space.Picture;
import java.util.Random;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import pphys2d.phys2d.math.Vector2f;
import pphys2d.phys2d.raw.Body;
import pphys2d.phys2d.raw.BodyList;
import pphys2d.phys2d.raw.World;
import pphys2d.phys2d.raw.shapes.Box;

/**
 *
 * @author User
 */
public class Painter2D extends Painter {

    public BufferedImage[] image;
    Graphics2D[] gimage;
    public int grad;
    boolean clearAfterDraw;
    private Color[] dustColors;

    abstract class Picture2D extends Picture {

        int[] iarg;
        Object[] oarg;

        public Picture2D(int[] iarg_, Object[] oarg_) {

            iarg = iarg_;
            oarg = oarg_;

        }

        public void Draw(Object tool) {

            Graphics2D g = (Graphics2D) tool;

            Draw(g);

        }

        public int Tr(int y) {
            return image[0].getHeight() - y - grad;
        }

        abstract void Draw(Graphics2D g);
    }

    public Painter2D(String name, int layers_, int grad_, int[] iarg) {

        grad = grad_;

        picturesList = new ArrayList[layers_];
        image = new BufferedImage[layers_];
        gimage = new Graphics2D[layers_];

        for (int i = 0; i < picturesList.length; i++) {
            picturesList[i] = new ArrayList<Picture>();


            if (name == "vp") {

                image[i] = new BufferedImage(iarg[0], iarg[1], BufferedImage.TYPE_INT_ARGB);
                gimage[i] = (Graphics2D) image[i].getGraphics();
                gimage[i].setBackground(new Color(0, true));
                gimage[i].setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                clearAfterDraw = false;

            } else if (name == "Space2D") {

                image[i] = new BufferedImage(iarg[0], iarg[1], BufferedImage.TYPE_INT_ARGB);// CreateImage(width, height);
                gimage[i] = (Graphics2D) image[i].getGraphics();
                gimage[i].setBackground(new Color(0, true));
                clearAfterDraw = true;

            }

        }
        Random random = new Random();
        dustColors = new Color[200];
        for (int i = 0; i < 200; i++) {
            dustColors[i] = new Color(random.nextInt());
        }

    }

    public void Draw(Object tool) {

        Graphics2D g = (Graphics2D) tool;

        for (int i = 0; i < getPictureList().length; i++) {

            BufferedImage image_ = image[i];
            Graphics2D gimage_ = gimage[i];
            ArrayList<Picture> queue_ = getPictureList()[i];

            gimage_.clearRect(0, 0, image_.getWidth(), image_.getHeight());
            //System.out.println(Thread.currentThread().getName()+"Draw(Tool):"+i+"." + queue_.size());
            for (int j = 0; j < queue_.size(); j++) {

                queue_.get(j).Draw(gimage_);

            }

            if (clearAfterDraw) {

                queue_.clear();

            }


        }

        for (int i = 1; i < getPictureList().length; i++) {

            gimage[0].drawImage(image[i], 0, 0, null);
        }

        g.drawImage(image[0], 0, 0, null);

    }

//    void ClearQueus() {
//        for (int i = 0; i < queue.length; i++) {
//
//            ArrayList<Picture> queue_ = queue[i];
//
//            System.out.println("Clear"+queue_.size());
//            queue_.clear();
//
//        }
//    }
    public synchronized void addToPictureList(String disription, int layer, int[] iarg, Object[] oarg, final boolean debug) {

        if (disription == "FlashOval") {

            getPictureList()[layer].add(new Picture2D(iarg, oarg) {

                private int counter = iarg[0];
                private int x = iarg[1];
                private int y = iarg[2];

                @Override
                void Draw(Graphics2D g) {


                    if (counter == 20) {

                        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));

                    } else {

                        if (counter > 10) {
                            g.setColor(Color.pink);
                        } else {
                            g.setColor(Color.white);
                        }
                        g.fillOval(x - 5, y - 5, 10, 10);

                        if (counter == 1) {
                            g.setBackground(new Color(0, true));
                            g.clearRect(x - 5, y - 5, 10, 10);
                        }
                    }

                }
            });

        } else if (disription == "DrawButton") {
            getPictureList()[layer].add(new Picture2D(iarg, oarg) {

                private int x = iarg[0];
                private int y = iarg[1];
                private int w = iarg[2];
                private int h = iarg[3];
                private int clear = iarg[4];

                @Override
                void Draw(Graphics2D g) {

                    if (clear == 1) {
                        g.setBackground(new Color(0, true));
                    }
                    g.setColor(Color.white);
                    g.clearRect(x, y, w, h);
                    g.fillRect(x, y, w, h);

                }
            });


        } else if (disription == "flashBtn") {

            getPictureList()[layer].add(new Picture2D(iarg, oarg) {

                private int counter = iarg[0];
                private int x = iarg[1];
                private int y = iarg[2];
                private int w = iarg[3];
                private int h = iarg[4];
                private int color_ = iarg[5];

                @Override
                void Draw(Graphics2D g) {

                    if (counter == 1) {
                        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
                        Color color;
                        if (color_ == 0) {
                            color = Color.black;
                        } else {
                            color = Color.white;
                        }

                        g.setColor(color);
                    }
                    g.fillRect(x + 2, y + 2, w - 3, h - 3);
                }
            });


        } else if (disription == "AnimeImage2D") {
            
            
            getPictureList()[layer].add(new Picture2D(iarg, oarg) {

                private int time = iarg[0];
                private int x = iarg[1];
                private int y = iarg[2];
                Sprite2D_ sprite = (Sprite2D_) oarg[0];

                @Override
                void Draw(Graphics2D g) {

                    g.drawImage(sprite.GetFrame(time), x, y, null);
                }
            });
        } else if (disription == "GradientArea") {
            //System.out.println(Thread.currentThread().getName()+"GradientArea");
            getPictureList()[layer].add(new Picture2D(iarg, oarg) {

                GradientArea area = (GradientArea) oarg[0];
//                Area shape = (Area) oarg[0];
//                ArrayList<Polygon> canva = (ArrayList<Polygon>) oarg[1];
//                int x1 = 100;
//                int y1 = 0;
//                int x2 = 0;
//                int y2 = 200;
//                int r = 100;
//                double angle = 0;
//                protected int x1a = iarg[0];
//                int y1a = iarg[1];
//                protected int x2a = iarg[2];
//                int y2a = iarg[3];
//                int r1 = iarg[4];
//                int g1 = iarg[5];
//                int b1 = iarg[6];
//                int r2 = iarg[7];
//                int g2 = iarg[8];
//                int b2 = iarg[9];

                @Override
                void Draw(Graphics2D g) {

                    //System.out.println(Thread.currentThread().getName()+"GradientArea->");
                    area.Move();


                    Composite oldcomp = g.getComposite();
                    g.setPaint(new GradientPaint(area.x1a, area.Tr(area.y1a), new Color(area.r1, area.g1, area.b1), area.x2a, area.Tr(area.y2a), new Color(area.r2, area.g2, area.b2), false));
                    g.fill(area.shape);

                    area.Move();
                    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                    g.setPaint(new GradientPaint(area.Tr(area.y1a), 0, new Color(area.r2, area.g2, area.b2), area.Tr(area.y2a), 0, new Color(area.r1, area.g1, area.b1), false));
                    g.fill(area.shape);
                    g.setComposite(oldcomp);

                    g.setColor(Color.black);//setPaint(new GradientPaint(x1a, Tr(y1a), Color.GRAY, x2a, Tr(y2a), Color.BLACK, false));
                    //bufferGraphics.fill(canva);
                    for (int i = 0; i < area.canva.size(); i++) {
                        g.drawPolygon(area.canva.get(i));
                    }

                }
            });

        } else if (disription == "Dust") {
            getPictureList()[layer].add(new Picture2D(iarg, oarg) {

                int dustCounter = iarg[0];
                Body[] dusts = (Body[]) oarg[0];

                @Override
                void Draw(Graphics2D g) {
                    for (int i = 0; i < dustCounter; i++) {
                        Body body = dusts[i];
                        Vector2f pts[] = ((Box) body.getShape()).getPoints(body.getPosition(), body.getRotation());
                        Vector2f v1 = pts[0];
                        Vector2f v2 = pts[1];
                        Vector2f v3 = pts[2];
                        Vector2f v4 = pts[3];

                        Polygon pol = new Polygon();
                        pol.addPoint((int) v1.x, (int) v1.y);
                        pol.addPoint((int) v2.x, (int) v2.y);
                        pol.addPoint((int) v3.x, (int) v3.y);
                        pol.addPoint((int) v4.x, (int) v4.y);
                        g.setColor(Color.black);
                        g.drawLine((int) v1.x, (int) v1.y, (int) v2.x, (int) v2.y);
                        g.drawLine((int) v2.x, (int) v2.y, (int) v3.x, (int) v3.y);
                        g.drawLine((int) v3.x, (int) v3.y, (int) v4.x, (int) v4.y);
                        g.drawLine((int) v4.x, (int) v4.y, (int) v1.x, (int) v1.y);
                        g.setColor(Painter2D.this.dustColors[i]);
                        // shape.add(new Area(pol));
                        // g.setPaint(new GradientPaint(0, 0, Color.BLUE, 300, 300, Color.red, false));
                        g.fillPolygon(pol);
                    }
                }
            });

        } else if (disription == "Clouds") {
            //System.out.println("Clouds");
            getPictureList()[layer].add(new Picture2D(iarg, oarg) {

                int dustCounter = iarg[0];
                ArrayList<Cloud> clouds = (ArrayList<Cloud>) oarg[0];

                @Override
                void Draw(Graphics2D g) {
                    System.out.println("->Clouds");
                    for (int i = 0; i < clouds.size(); i++) {
                        clouds.get(i).Draw(g);
                    }

                }
            });
        } else if (disription == "draw") {
            getPictureList()[layer].add(new Picture2D(iarg, oarg) {

                Phys2DShow phys = (Phys2DShow) oarg[0];

                @Override
                void Draw(Graphics2D g) {

                    BodyList bodies = phys.world.getBodies();
                    for (int i = 0; i < bodies.size(); i++) {
                        Body body = bodies.get(i);
                        phys.drawBody(g, body);
                    }

                }
            });
        } else if (disription == "Chains") {
            getPictureList()[layer].add(new Picture2D(iarg, oarg) {

                int chainsCounter = iarg[0];
                Body[] chains = (Body[]) oarg[0];

                @Override
                void Draw(Graphics2D g) {

                    Area shape = new Area();

                    for (int i = 0; i < chainsCounter; i++) {
                        Body body = chains[i];
                        Vector2f pts[] = ((Box) body.getShape()).getPoints(body.getPosition(), body.getRotation());
                        Vector2f v1 = pts[0];
                        Vector2f v2 = pts[1];
                        Vector2f v3 = pts[2];
                        Vector2f v4 = pts[3];
                        Polygon pol = new Polygon();
                        pol.addPoint((int) v1.x, (int) v1.y);
                        pol.addPoint((int) v2.x, (int) v2.y);
                        pol.addPoint((int) v3.x, (int) v3.y);
                        pol.addPoint((int) v4.x, (int) v4.y);

                        g.setColor(Color.black);
                        g.drawLine((int) v1.x, (int) v1.y, (int) v2.x, (int) v2.y);
                        g.drawLine((int) v2.x, (int) v2.y, (int) v3.x, (int) v3.y);
                        g.drawLine((int) v3.x, (int) v3.y, (int) v4.x, (int) v4.y);
                        g.drawLine((int) v4.x, (int) v4.y, (int) v1.x, (int) v1.y);
                        shape.add(new Area(pol));
                    }
                    g.setPaint(new GradientPaint(0, 150, Color.green, 300, 150, Color.red, false));
                    g.fill(shape);

                }
            });
        } else if (disription == "Map") {
            getPictureList()[layer].add(new Picture2D(iarg, oarg) {

                World world = (World) oarg[0];

                @Override
                void Draw(Graphics2D g) {

                    Body b_;
                    g.setColor(Color.GREEN);
                    for (int dy = 0; dy < world.map.length; dy++) {
                        for (int dx = 0; dx < world.map[0].length; dx++) {
                            b_ = world.map[dy][dx];
                            if (b_ != null) {
                                g.drawRect(dx, dy, 0, 0);
                            }
                        }
                    }
                    g.setColor(Color.black);

                }
            });
        } else if (disription == "Text") {
            //System.out.println((String) oarg[1]);
            getPictureList()[layer].add(new Picture2D(iarg, oarg) {

                int a = iarg[0];
                private int x = iarg[1];
                private int y = iarg[2];
                Color color = (Color) oarg[0];
                String st = (String) oarg[1];

                @Override
                void Draw(Graphics2D g) {
                    System.out.println("->" + st);
                    g.setColor(color);
                    g.drawString(st, x - a, y - a);
                }
            });
        }
    }
}
