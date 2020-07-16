package Main;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import Application.Builder;
import Application.Consrtuction.Configurations.DispatchingType;
import Application.Consrtuction.Configurations.GraphicType;
import Application.Consrtuction.Configurations.Storys;
import Application.Configuration;
import Application.Consrtuction.IConfiguration;
import Application.IApplication;

/**
 *
 * @author kalinin
 */
public class MainJApplet extends NewJApplet {

    @Override
    public void init() {
        super.init();
        IConfiguration configuration = Configuration.basedOn(new NewJFrame())
                                                    .about(Storys.Jupmer)
                                                    .lookLike(GraphicType._2D)
                                                    .commanded(DispatchingType.Synchrone);
        IApplication appl = Builder.with(configuration).createApplication();
        appl.run();
    }
}
