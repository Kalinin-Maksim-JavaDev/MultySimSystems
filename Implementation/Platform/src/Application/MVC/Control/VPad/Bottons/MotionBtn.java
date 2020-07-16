/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.MVC.Control.VPad.Bottons;

import Logic.Reflections.Space.IPainter;
import Platform.Core.Motion.Motion;

/**
 *
 * @author kalinin
 */
public class MotionBtn extends Motion {

    Button button;
    IPainter painter;
    //Graphics2D vpImgGr_ = (Graphics2D) virtualPad.formCanva.vk.vpImg.getGraphics();
    int color;

    public MotionBtn(Button button,IPainter painter,int iterates_, int color_) {
        super(iterates_, "flashBtn");
        this.button = button;
        this.painter = painter;
        color = color_;

    }

    @Override
    public void MotionMethod() {
//            if (counter == 1) {
//                vpImgGr_.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
//                vpImgGr_.setColor(color_);
//            }
//            vpImgGr_.fillRect(x + 2, y + 2, w - 3, h - 3);

        int[] arg_ = {getCounter(), button.x, button.y, button.w, button.h, color};
        painter.addToPictureList("flashBtn", 0, arg_, null, false);
    }
}
