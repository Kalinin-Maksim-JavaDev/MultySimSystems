/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.Strategy;

import MVC.Control.IControl;
import MVC.Control.IInputEvent;
import MVC.Control.IInputSource;
import MVC.Model.IDomainModel;
import MVC.View.IMVC_View;
import Platform.Core.IMotionReciver;
import Platform.Core.Motion.EndLessMotion;
import java.util.ArrayList;

/**
 *
 * @author dkx6r0c
 */
public class MCVLoop implements IMCVLoop {

    static IMCVLoop create() {
        return new MCVLoop();
    }
    private IDomainModel.Imaginated model;
    private IMVC_View view;
    private IControl.Ready control;

    public IMCVLoop with(IControl.Ready control) {
        this.control = control;
        return this;
    }

    public IMCVLoop with(IDomainModel.Imaginated model) {
        this.model = model;
        return this;
    }

    public IMCVLoop with(IMVC_View view) {
        this.view = view;
        return this;
    }

    public void runOn(IMotionReciver reciver) {
        reciver.AddMotions(new EndLessMotion("Iteration") {

            @Override
            protected void EndLessMethod() {
                acceptInput(control.ReadInput(view.reflect(model.activeProjections()).Render()));
            }
        });
    }

    private void acceptInput(IInputSource inputSource) {
        boolean[][] keyPressed = new boolean[2][200];

        ArrayList[] events = (ArrayList[]) inputSource.getEvents();

        ArrayList pressEvents = events[0];
        for (int i = 0; i < pressEvents.size(); i++) {

            IInputEvent pressEvent = (IInputEvent) pressEvents.get(i);
            int device = pressEvent.getDevice();
            int keyCode = pressEvent.getKeyCode();

            if (!keyPressed[device][keyCode]) {

                if (control.getRecivingControler().keyPress(device, keyCode, pressEvent.getArg())) {

                    keyPressed[device][keyCode] = true;

                }
            }
        }
        ArrayList releaseEvents = events[1];

        for (int i = 0; i < releaseEvents.size(); i++) {

            IInputEvent releaseEvent = (IInputEvent) releaseEvents.get(i);
            int device = releaseEvent.getDevice();
            int keyCode = releaseEvent.getKeyCode();

            control.getRecivingControler().keyRelease(device, keyCode, releaseEvent.getArg());

            keyPressed[device][keyCode] = false;

        }
    }
}
