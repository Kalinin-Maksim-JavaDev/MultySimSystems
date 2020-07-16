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

public class TwoBalks extends AbstractDemo {

    protected void init(final World world) {

        StaticBody floor=new StaticBody(new Box(400,5));
        floor.setPosition(280, 300);
       
        final Body c11 = new StaticBody("c11", new Circle(20F));
        c11.setPosition(200F, 100F);
        
        final Body c12 = new Body("c12", new Circle(20F), 100F);
        c12.setPosition(200F, 200F);
        
        final Body c13 = new Body("c13", new Circle(20F), 100F);
        c13.setPosition(200F, 150F);
      
       int x_=00;

        final Body c21 = new Body("c21", new Circle(20F), 100F);
        c21.setPosition(250F+x_, 150F);
        
        final Body c22= new Body("c22", new Circle(20F), 100F);
        c22.setPosition(150F+x_, 150F);        

        final Body c23 = new Body("c23", new Circle(20F), 100F);
        c23.setPosition(200F+x_, 150F);

        Joint j11=new BasicJoint(c11,c12,new Vector2f(c11.getPosition().getX(),c11.getPosition().getY()));
        Joint j12=new FixedJoint(c12,c13);
        Joint j21=new FixedJoint(c21,c22);
        Joint j22=new FixedJoint(c22,c23);

        Joint j1323=new BasicJoint(c23,c13,new Vector2f(c13.getPosition().getX(),c13.getPosition().getY()));

        world.add(c11,1);
        world.add(c12,1);        

        world.add(c21,2);
        world.add(c22,2);

        world.add(c13,1);
        world.add(c23,2);

        world.add(j11);
        world.add(j12);
        world.add(j21);
        world.add(j22);
        world.add(j1323);

        world.add(floor,0);

       // world.setGravity(0, 0);


        c12.addForce(new Vector2f(-1000000,0));
        c22.addForce(new Vector2f(0,100000));
    }

    public static void main(String argv[]) {
        TwoBalks demo = new TwoBalks();
        demo.start();
    }
}


