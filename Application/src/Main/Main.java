/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Application.Builder;
import Application.Consrtuction.Configurations.DispatchingType;
import Application.Consrtuction.Configurations.GraphicType;
import Application.Consrtuction.Configurations.Storys;
import Application.Configuration;
import Application.Consrtuction.IConfiguration;
import Application.IApplication;
import Platform.Util.Profiler.observercode.Diagram.DataBlock;

/**
 *
 * @author kalinin
 */
public class Main {

    public static void main(String[] args) {
        new Main();
    }

    Main() {
        DataBlock.diargamma.start();

        IConfiguration configuration = Configuration.basedOn(new NewJFrame())
                                                    .about(Storys.Jupmer)
                                                    .lookLike(GraphicType._2D)
                                                    .commanded(DispatchingType.Synchrone);
        IApplication appl = Builder.with(configuration).createApplication();
        appl.run();
    }
}
