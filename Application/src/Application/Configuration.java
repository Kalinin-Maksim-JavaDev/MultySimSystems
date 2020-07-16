/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import Application.Consrtuction.Configurations.DispatchingType;
import Application.Consrtuction.Configurations.GraphicType;
import Application.Consrtuction.Configurations.Storys;
import Application.Consrtuction.IConfiguration;
import Application.Consrtuction.IConfiguration.Phases;
import Application.Control.IInputOwner;
import Application.Platform.IWorkPlace;
import Application.View.IScreen;

/**
 *
 * @author kalinin
 */
public class Configuration implements IConfiguration, Phases.Hidden, Phases.One, Phases.Two, Phases.ConfigurePhase {

    private IInputOwner inputOwner;
    private IScreen.UnInit screen;
    private Storys story;
    private DispatchingType commandDispatchingType;
    private GraphicType _GRAPHIC;



    public Configuration() {
    }

    public static Phases.One basedOn(IWorkPlace workPlace) {

        return (Phases.One) new Configuration().with(workPlace.getScreen()).with(workPlace.getInputOwner());
    }

    public Phases.Hidden with(IScreen.UnInit screen) {

        this.screen = screen;

        return this;
    }

    public Phases.Hidden with(IInputOwner inputOwner) {

        this.inputOwner = inputOwner;
        return this;
    }

    public Phases.Two about(Storys story) {

        this.story = story;
        return this;
    }

    public Phases.ConfigurePhase lookLike(GraphicType type) {

        if (type == GraphicType._2D) {

            _GRAPHIC = type;

        }
        return this;
    }

    public IConfiguration commanded(DispatchingType type) {

        commandDispatchingType = DispatchingType.Synchrone;

        return (IConfiguration) this;
    }

    public IApplication build() {

        return new Builder(this).createApplication();

    }

    public IScreen.UnInit getScreen() {
        return screen;
    }

    public IInputOwner getInputOwner() {
        return inputOwner;
    }

    public Storys getStory() {
        return story;
    }

    public DispatchingType getCommandsDispatchingType() {
        return commandDispatchingType;
    }

     public GraphicType getGraphicType() {
        return _GRAPHIC;
    }
}
