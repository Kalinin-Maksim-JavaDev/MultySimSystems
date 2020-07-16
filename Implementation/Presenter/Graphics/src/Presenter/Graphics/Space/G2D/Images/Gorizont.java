/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter.Graphics.Space.G2D.Images;

import Model.Game.Presenter.Space.Space;
import Presenter.Graphics.Space.G2D.Space2D;
import Render.Graphics.Geometry.ICoordinates;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;

/**
 *
 * @author kalinin
 */
public class Gorizont extends GradientArea {
//    int rsky;
//    int gsky;
//    int bsky;
//    int rground;
//    int gground;
//    int bground;
    final Color sky=new Color(0x0c4473);
    final Color ground=new Color(0x104f21);
    public Gorizont(int layer_, Space space_) {
        super(layer_, space_,0,0,0,0,0,0);
        y1 = 0;
        y2 = 320;
        r=50;
    }

    @Override
    public void PutImage(ICoordinates coor) {
        Move();
        Graphics2D bufferGraphics = ((Space2D) space).bufferGraphics[layer];
        bufferGraphics.setPaint(new GradientPaint(x1a, Tr(y1a), ground, x2a, Tr(y2a),sky, false));
        bufferGraphics.fill(shape);

    }

    public void Move() {
        x1a = 240;
        y1a = y1 + (int) (r * Math.sin(Math.toRadians(angle)));
        x2a = 240;
        y2a = y2 + (int) (r * Math.sin(Math.toRadians(angle)));

        angle += 2;
        if (angle >= 360) {
            angle = 0;
        }
    }
}
