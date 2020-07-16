/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter.Graphics.Space.G2D;

import pphys2d.bodies.PPBox;
import pphys2d.bodies.PPCircle;
import pphys2d.phys2d.math.Vector2f;
import pphys2d.phys2d.raw.Body;
import pphys2d.phys2d.raw.BodyList;
import pphys2d.phys2d.raw.CollisionContext;
import pphys2d.phys2d.raw.CollisionSpace;
import pphys2d.phys2d.raw.DistanceJoint;
import pphys2d.phys2d.raw.World;
import pphys2d.phys2d.raw.strategies.QuadSpaceStrategy;

/**
 *
 * @author kalinin
 */
public class ChainsDemo extends AbstractDemo {

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

    public void AddChain(Vector2f begChain, Vector2f endChain) {
        PPCircle b1 = new PPCircle(5F);
        b1.setStaticBody(true);
        b1.setPosition((int) (begChain.x), (int) (begChain.y));
        world.add(b1);
        PPCircle b2 = new PPCircle(5F);
        b2.setPosition((int) (endChain.x), (int) (endChain.y));
        b2.setStaticBody(true);
        world.add(b2);
        AddChain(b1, b2, (int) Math.abs(endChain.x - begChain.x - 1) / 10, 0);
    }

    protected void init(World world) {

        AddChain(new Vector2f(100, 300), new Vector2f(400, 300));
    }

    public static void main(String argv[]) {
        ChainsDemo demo = new ChainsDemo();
        demo.start();
    }
}
