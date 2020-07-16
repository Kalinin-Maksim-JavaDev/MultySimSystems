/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.Control;

import MVC.Model.IDomainModel;
import Render.View.IRenderResult;

/**
 *
 * @author User
 */
public interface IControl {

    IControler createControler(IDomainModel.controllerSource controllerSource);

    Ready controled(IControler.Ready.reciving controller);
    
    interface Ready {
        
        IInputSource ReadInput(IRenderResult picture);

        IControler.Ready.reciving getRecivingControler();
    }
}
