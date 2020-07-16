package Application.MVC.Control.VPad.Bottons;

import Application.Build.Control.VirtualPad.IBottonsChain;
import Application.Build.Control.VirtualPad.IButton;
import Logic.Reflections.Space.IPainter;

public class BottonScope extends BottonsChain implements IBottonsChain {

    int x;
    int y;
    int w;
    int h;

    //  public BufferedImage img;
    public BottonScope(IPainter painter, int x_, int y_, int w_, int h_) {
        super(painter);

        x = x_;
        y = y_;
        w = w_;
        h = h_;

    }

    public void Add(IButton btn) {
        if (btns.last == null) {
            btns.last = btn;
            btns.last.SetNext(btns.last);
        } else {
            btn.SetNext(btns.last.getNext());
            btns.last.SetNext(btn);
            btns.last = btn;
        }
        btns.last = btn;
    }

    public boolean keyPressed(int x, int y) {
        boolean ok = false;
        btns.pressed = null;
        Button last = (Button) btns.last;
        do {
            if ((x >= last.x) && (x < last.x + last.w) && (y >= last.y) && (y < last.y + last.h)) {
                last.keyPressed();
                btns.pressed = last;
                ok = true;
                break;
            }
            last = (Button) last.getNext();
        } while (last != btns.last);
        return ok;
    }

    public void keyReleased() {
        if (btns.pressed != null) {
            btns.pressed.keyReleased();
            btns.pressed = null;
        }
        if (btns.draggeg != null) {
            btns.draggeg.keyReleased();
            btns.draggeg = null;
        }
    }

    public void DrawButtons() {
        //     g.setColor(0, 0, 0);
        //       g.drawRect(x + 1, y + 1, width - 2, height - 2);
        IButton c_ = btns.last;
        do {
            c_.Draw(false);
            c_ = c_.getNext();
        } while (c_ != btns.last);
    }

    public void pointerDragged(int x, int y) {
        Button last = (Button) btns.last;
        do {
            if ((x >= last.x) && (x < last.x + last.w) && (y >= last.y) && (y < last.y + last.h)) {
                if ((btns.pressed != null) && (btns.pressed != last)) {
                    if ((btns.draggeg != null) && (btns.draggeg != last)) {
                        btns.draggeg.keyReleased();
                    }
                    last.keyPressed();
                    btns.draggeg = last;
                    break;
                }
            }
            last = (Button) last.getNext();
        } while (last != btns.last);
    }
}
