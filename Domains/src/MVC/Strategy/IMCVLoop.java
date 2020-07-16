/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.Strategy;

import MVC.Control.IControl;
import MVC.Model.IDomainModel;
import MVC.View.IMVC_View;
import Platform.Core.IMotionReciver;

/**
 *
 * @author dkx6r0c
 */
public interface IMCVLoop {

    IMCVLoop with(IDomainModel.Imaginated model);

    IMCVLoop with(IMVC_View view);

    IMCVLoop with(IControl.Ready control);

    void runOn(IMotionReciver reciver);
}
