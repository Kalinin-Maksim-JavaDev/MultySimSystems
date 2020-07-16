/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter.Graphics.Space.G2D;

import pphys2d.bodies.PPLine;
import pphys2d.phys2d.math.Vector2f;
import pphys2d.phys2d.raw.Body;
import pphys2d.phys2d.raw.BodyList;
import pphys2d.phys2d.raw.CollisionContext;
import pphys2d.phys2d.raw.CollisionSpace;
import pphys2d.phys2d.raw.StaticBody;
import pphys2d.phys2d.raw.World;
import pphys2d.phys2d.raw.shapes.Box;
import pphys2d.phys2d.raw.shapes.Circle;
import pphys2d.phys2d.raw.strategies.QuadSpaceStrategy;

public class Demo1old extends AbstractDemo {

    public Demo1old() {
        world = new World(new Vector2f(0.0F, 10), 10, new QuadSpaceStrategy(20, 5) {

            public void collideBodies(CollisionContext context, BodyList bodies, float dt) {
                ((CollisionSpace)context).resolve(bodies, dt);
            }
        });

        running = true;
        normals = true;
        contacts = true;
        this.title = title;
    }


    protected void init(World world) {
        Body body1 = new StaticBody("Ground1", new Box(400F, 20F));
        body1.setPosition(250F, 400F);
        world.add(body1);
        Body body3 = new StaticBody("Ground2", new Box(200F, 20F));
        body3.setPosition(360F, 380F);
        world.add(body3);
        Body body5 = new StaticBody("Ground3", new Box(20F, 100F));
        body5.setPosition(200F, 300F);
        world.add(body5);
        Body body6 = new StaticBody("Ground3", new Box(20F, 100F));
        body6.setPosition(400F, 300F);
        world.add(body6);

//        Body body2 = new Body("Mover1", new Box(50F, 50F), 100F);
//        body2.setPosition(210F, 60F);
//        body2.setRotation((float) (Math.PI / 3));
//        world.add(body2);
        Body body4 = new Body("Mover2", new Circle(25F), 100F);
        body4.setPosition(230F, 60F);
        world.add(body4);
        Body body8 = new Body("Mover3", new Box(25F, 50F), 100F);
        body8.setPosition(280F, 60F);
        world.add(body8);

        PPLine l1 = new PPLine(10, 100, 200, 50);
        l1.setStaticBody(true);
        world.add(l1);

    }

    public static void main(String argv[]) {
        Demo1old demo = new Demo1old();
        demo.start();
    }
}
