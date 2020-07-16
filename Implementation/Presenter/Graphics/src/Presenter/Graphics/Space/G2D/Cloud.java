package Presenter.Graphics.Space.G2D;

import java.awt.Color;
import java.awt.Graphics2D;
import pphys2d.bodies.PPBox;
import pphys2d.phys2d.math.Vector2f;
import pphys2d.phys2d.raw.Body;
import pphys2d.phys2d.raw.DistanceJoint;

public class Cloud {

    int n = 6;
    PPBox[] part = new PPBox[n + 1];
    PPBox center;
    Phys2DShow outer;

    public Cloud(int x, int y, float r, Phys2DShow outer) {
        this.outer = outer;
        int k = 0;
        center = new PPBox(outer.grad / 2, outer.grad / 2);
        center.setPosition(outer.GetX(x), outer.GetY(y));
        center.setRotatable(false);
        outer.world.add(center);
        part[k++] = center;
        for (int i = 0; i < n; i++) {
            PPBox circle = new PPBox(outer.grad / 2, outer.grad / 2);
            circle.setPosition(outer.GetX(x) + (float) (outer.grad * r * Math.cos(Math.toRadians(360 / n * i))), outer.GetY(y) + (float) (outer.grad * r * Math.sin(Math.toRadians(360 / n * i))));
            part[k++] = circle;
            circle.setRotatable(false);
            outer.world.add(circle);
            DistanceJoint dj = new DistanceJoint(center, circle, new Vector2f(0, 0.0F), new Vector2f(-r / 2, 0.0F), r * outer.grad);
            outer.world.add(dj);
        }
    }

    public void Draw(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fillOval((int) center.getPosition().getX() - 10, (int) center.getPosition().getY() - 10, 20, 20);
        for (int i = 0; i < n; i++) {
            g.fillOval((int) part[i].getPosition().getX(), (int) part[i].getPosition().getY(), 20, 20);
        }
        g.setColor(Color.white);
        g.fillOval((int) center.getPosition().getX() - 9, (int) center.getPosition().getY() - 9, 20, 20);
        for (int i = 0; i < n; i++) {
            g.fillOval((int) part[i].getPosition().getX(), (int) part[i].getPosition().getY(), 18, 18);
        }
    }

    public void Wind(int x_, int y_) {
        Body body = center;
        if (((int) (body.getPosition().getX() / outer.grad) == x_) && ((int) ((310 - body.getPosition().getY()) / outer.grad)) == y_) {
            body.addForce(new Vector2f((float) (10000 * (1 - 2 * outer.random.nextDouble())), -(float) (10000 * (1 - 2 * outer.random.nextDouble()))));
            body.setRotation((float) (body.getRotation() + Math.PI * outer.random.nextDouble()));
        }
    }
}
