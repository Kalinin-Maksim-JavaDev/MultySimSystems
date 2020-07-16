/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter.Graphics.Space.G2D;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import pphys2d.bodies.PPLine;
import pphys2d.phys2d.math.Vector2f;
import pphys2d.phys2d.raw.BasicJoint;
import pphys2d.phys2d.raw.Body;
import pphys2d.phys2d.raw.FixedJoint;
import pphys2d.phys2d.raw.Joint;
import pphys2d.phys2d.raw.StaticBody;
import pphys2d.phys2d.raw.World;
import pphys2d.phys2d.raw.shapes.Box;
import pphys2d.phys2d.raw.shapes.Circle;

public class TwoBox extends AbstractDemo {

    protected void init(final World world) {

        StaticBody floor=new StaticBody(new Box(400,5));
        floor.setPosition(280, 300);
        world.add(floor);
        final Body body4 = new Body("Mover2", new Box(25F, 50F), 100F);
        body4.setPosition(230F, 160F);
        
        world.add(body4);
        final Body body8 = new Body("Mover3", new Box(25F, 50F), 100F);
        body8.setPosition(280F, 160F);
        world.add(body8);
        final Body body5 = new Body("Mover2", new Box(25F, 50F), 100F);
        body5.setPosition(330F, 160F);
        world.add(body5);
      //  world.setGravity(0, 1);

        Joint j1=new BasicJoint(body4,floor,new Vector2f(100,250));
        world.add(j1);
        Joint j2=new BasicJoint(body5,floor,new Vector2f(400,250));
        world.add(j2);
        body4.setGravityEffected(false);
        body5.setGravityEffected(false);

        body4.setFriction(10);
        body5.setFriction(10);
        body8.setFriction(10);
        Thread tr =new Thread(new Runnable() {

            public void run() {
                while (true){
               if (body4.getPosition().distance(body8.getPosition())>1){
                   body4.addForce(new Vector2f(10000f,0));
               }
               if (body5.getPosition().distance(body8.getPosition())>1){
                   body5.addForce(new Vector2f(-10000f,0));
               }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(TwoBox.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        tr.start();
    }

    public static void main(String argv[]) {
        TwoBox demo = new TwoBox();
        demo.start();
    }
}
