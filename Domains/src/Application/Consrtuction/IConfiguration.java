/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.Consrtuction;

import Application.Consrtuction.Configurations.DispatchingType;
import Application.Consrtuction.Configurations.GraphicType;
import Application.Consrtuction.Configurations.Storys;
import Application.Control.IInputOwner;
import Application.View.IScreen;

/**
 *
 * @author kalinin
 */
public interface IConfiguration {

    IScreen.UnInit getScreen();

    IInputOwner getInputOwner();

    Storys getStory();

    DispatchingType getCommandsDispatchingType();

    GraphicType getGraphicType();

    interface Phases {

        interface Hidden {

            Hidden with(IScreen.UnInit screen);

            Hidden with(IInputOwner inputOwner);
        }

        interface One {

            Two about(Storys storys);
        }

        interface Two {

            ConfigurePhase lookLike(GraphicType graphic);
        }

        interface ConfigurePhase {

            IConfiguration commanded(DispatchingType commandDispacherType);
        }
    }
}
