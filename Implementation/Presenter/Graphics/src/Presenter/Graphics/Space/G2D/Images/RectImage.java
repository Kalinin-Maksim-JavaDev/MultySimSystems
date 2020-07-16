/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Presenter.Graphics.Space.G2D.Images;

import Model.Game.Presenter.Space.Space;
import Model.Geometry.D2.Coordinates2d;
import Presenter.Graphics.Space.G2D.Space2D;
import Render.Graphics.Geometry.ICoordinates;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author kalinin
 */
public class RectImage extends ImageL {

        int index;

        public RectImage(int index_, int layer_,Space space_) {
            super(layer_,space_);
            index = index_;
        }

        @Override
        public void PutImage(ICoordinates coor) {
            int a=((Space2D)space).a;
            Graphics2D bufferGraphics=((Space2D)space).bufferGraphics[layer];
            bufferGraphics.setColor(Color.red);
            bufferGraphics.drawRect((int) ((Coordinates2d) coor).x - a, Tr((int) ((Coordinates2d) coor).y) - a, a * 2, a * 2);
            bufferGraphics.drawRect((int) ((Coordinates2d) coor).x - a + 2, Tr((int) ((Coordinates2d) coor).y) - a + 2, a * 2 - 4, a * 2 - 4);
            bufferGraphics.drawString(String.valueOf(index), (int) ((Coordinates2d) coor).x + a - 2, Tr((int) ((Coordinates2d) coor).y) - a + 2);
        }
    }
