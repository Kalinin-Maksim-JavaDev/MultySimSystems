/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.Control;

import Application.Build.Control.VirtualPad.IVitrualPad;

/**
 *
 * @author User
 */
public interface IInputReciver {

    withVPad setPad(IVitrualPad pad);

    interface keyboard {

        void keyPressed(int code, int[] arg);

        void keyReleased(int code, int[] arg);
    }

    interface withVPad extends keyboard{

        void pointerPressed(int button, int x, int y);

        void pointerDragged(int button, int x, int y, int x0, int y0);

        void pointerReleased(int button, int x, int y);

        void mouseMoved(int i, int x, int y);

        public void drawPad(Object tool);
    }
}
