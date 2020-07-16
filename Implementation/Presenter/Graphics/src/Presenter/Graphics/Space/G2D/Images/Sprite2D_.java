/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter.Graphics.Space.G2D.Images;

import Logic.Reflections.Space.ISpace;
import Presenter.Graphics.Space.G2D.Space2D;
import Presenter.Graphics.Space.G2D.WhiteFilter;
import Render.Graphics.Geometry.ICoordinates;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author kalinin
 */
public class Sprite2D_ extends ImageL {

    final public BufferedImage[] images;
    final int w;
    final int h;
    final public int period;
    final int delay;

    public Sprite2D_(String sourceName, int w_, int h_, int grad_, int count_, int delay_, ISpace space_) {
        super(0, space_);
        ImageFilter filter = new WhiteFilter();
        w = w_;
        h = h_;
        delay = delay_;
        period = count_ * delay_;
        BufferedImage image_ = null;

        try {
            image_ = ImageIO.read(Global.Tools.getResourceAsStream(sourceName));
        } catch (IOException ex) {
            Logger.getLogger(Space2D.class.getName()).log(Level.SEVERE, null, ex);
        }

        images = new BufferedImage[count_];
        for (int i = 0; i < count_; i++) {
            BufferedImage b_ = null;
            if (grad_ == 0) {
                b_ = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                Graphics2D grph = (Graphics2D) b_.getGraphics();
                //grph.drawImage(image_.getSubimage(w * i, 0, w, h), 0, 0, null);
                grph.drawImage(Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image_.getSubimage(w * i, 0, w, h).getSource(), filter)), 0, 0, null);
            } else {
                b_ = new BufferedImage(grad_, grad_, BufferedImage.TYPE_INT_ARGB);
                Graphics2D grph = (Graphics2D) b_.getGraphics();
                //grph.drawImage(image_.getSubimage(w * i, 0, w, h).getScaledInstance(grad_, grad_, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
                grph.drawImage(Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image_.getSubimage(w * i, 0, w, h).getScaledInstance(grad_, grad_, java.awt.Image.SCALE_SMOOTH).getSource(), filter)), 0, 0, null);
            }
            images[i] = b_;
        }

    }

    public BufferedImage GetFrame(int time_) {
        int n = (int) ((float) time_ / (float) delay);
        return images[n];
    }

    @Override
    public void PutImage(ICoordinates coor) {
    }
}
