/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import MVC.Control.IControl;
import MVC.Control.IControler;
import MVC.Control.IInputCollector;
import MVC.Control.IInputSource;
import Render.View.IRenderResult;
import Application.Build.Panels.Model.IEnvironment;
import MVC.Model.IDomainModel;

/**
 *
 * @author kalinin
 */
public class Control implements IControl, IControl.Ready {

    private IEnvironment.control environment;
    private IInputCollector inputCollector;
    private IControler controler;
    private IControler.Ready.reciving recivingControler;

    public Control() {

        this.inputCollector = new InputCollector();
    }

    public IControl setEnvironmentControl(IEnvironment.control environment) {
        this.environment = environment;
        return this;
    }

    public IInputCollector getInputCollector() {
        return inputCollector;
    }

    public IInputSource ReadInput(IRenderResult picture) {

        return inputCollector.getInputSource();
    }

    public IControler createControler(IDomainModel.controllerSource controllerSource) {
        controler = new Controler(environment, controllerSource.getKeysMap());
        return controler;
    }

    public IControler.Ready.reciving getRecivingControler() {
        return recivingControler;
    }

    public Ready controled(IControler.Ready.reciving controler) {

        recivingControler = controler;
        return this;
    }

    public IControler getControler() {
        return controler;
    }
}
