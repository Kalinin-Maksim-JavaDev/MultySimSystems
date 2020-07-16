package Application.MVC.Control.VPad.Bottons;

import Application.Build.Control.VirtualPad.IVrtualPadMotionsReciver;
import MVC.Control.IInputCollector;
import Application.Build.Control.VirtualPad.IBottonsChain;
import Application.Build.Control.VirtualPad.IButton;
import Platform.Concurrent.Factory;
import Platform.Concurrent.ILathce;

public class Button extends ButtonStructure implements IButton {

    int x;
    int y;
    int w;
    int h;
    public ILathce keyLatche = Factory.createLathce(true, true);

    public Button(int code_, float xk_, float yk_, int w_, int h_, IBottonsChain pad, IVrtualPadMotionsReciver motionsReciver, IInputCollector input) {
        this.chain = pad;
        this.motionsReciver = motionsReciver;
        this.input = input;
        code = code_;
        w = w_;
        h = h_;

        BottonScope scope = (BottonScope) pad;

        x = (int) (scope.x + scope.w * xk_ - w / 2);
        y = (int) (scope.y + scope.h * yk_ - h / 2);
    }

    public void Draw(boolean clear) {
        //        virtualPad.g.setColor(Color.white);
        //        virtualPad.g.clearRect(x, y, w, h);
        //        virtualPad.g.fillRect(x, y, w, h);
        int iclear;
        if (clear) {
            iclear = 1;
        } else {
            iclear = 0;
        }
        int[] arg_ = {x, y, w, h, iclear};
        ((BottonScope) chain).painter.addToPictureList("DrawButton", 0, arg_, null, false);
    }

    public void keyPressed() {
        input.addPressedKey(0, code, new int[4]);
        motionsReciver.AddMotions(new MotionBtn(this, ((BottonScope) chain).painter, 10, 0) {

            @Override
            public void MotionMethod() {
                super.MotionMethod();
                if (isFinal()) {
                    keyLatche.synchronizedRelease();
                }
            }
        });
        //   virtualPad.formCanva.flushGraphics();
    }

    public void keyReleased() {
        input.addReleasedKey(0, code, new int[4]);
        keyLatche.synchronizedWait();
        motionsReciver.AddMotions(new MotionBtn(this, ((BottonScope) chain).painter, 10, 1) {

            @Override
            public void MotionMethod() {
                super.MotionMethod();
                if (getCounter() == 1) {
                    Draw(true);
                }
            }
        });
        //    virtualPad.formCanva.flushGraphics();
    }

    public void SetNext(IButton btn) {
        nextBtn = btn;
    }

    public IButton getNext() {
        return nextBtn;
    }
}
