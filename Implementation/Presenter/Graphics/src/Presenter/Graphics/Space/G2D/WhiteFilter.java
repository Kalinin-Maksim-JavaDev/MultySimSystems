/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Presenter.Graphics.Space.G2D;

import java.awt.Color;
import java.awt.image.RGBImageFilter;

/**
 *
 * @author kalinin
 */
public class WhiteFilter extends RGBImageFilter {

    private int markerRGB = Color.white.getRGB();

    public WhiteFilter() {
        canFilterIndexColorModel = true;
    }

    public int filterRGB(int x, int y, int rgb) {

        if ((rgb) == markerRGB) {
            // Mark the alpha bits as zero - transparent
            return 0x00FFFFFF & rgb;
        } else {
            // nothing to do
            return rgb;
        }

    }
}
