/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Presenter.Projections;

import Logic.Reflections.Projections.IImaginator;
import Platform.Core.Unit.INumered;
import Platform.DataStructers.ILinkedElement;
import Render.Graphics.Geometry.IPointed;

/**
 *D
 * @author dkx6r0c
 */
public interface IViewUnit extends INumered, IPointed {

    int getTypeID();

    public enum states {

        zero, one, two, Jumper_State, Jumper_Run, Jumper_Jump, Jumper_Fall;

        public int getId() {
            if (this == states.zero) {
                return 0;
            } else if (this == states.one) {
                return 1;
            } else if (this == states.two) {
                return 2;
            } else if (this == states.Jumper_State) {
                return 0;
            } else if (this == states.Jumper_Run) {
                return 1;
            } else if (this == states.Jumper_Jump) {
                return 2;
            } else if (this == states.Jumper_Fall) {
                return 3;
            } else {
                return -1;
            }
        }
    }

    ILinkedElement asLinkedElement();

    interface list {

        IViewUnit add(IViewUnit e);

        selection select();

        interface selection {

            boolean end();

            IViewUnit get();
        }
    }

    imaginated setImaginator(IImaginator.charged imaginator);

    interface imaginated {

        states getViewState();

        IImaginator.charged getImaginator();

        //ILinkedElement asLinkedElement();
        interface list {

            selection select();

            interface selection {

                boolean end();

                imaginated get();
            }
        }
    }
}
