/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.Strategy;

import MVC.Model.IDomainModel;
import MVC.View.IViewSource;

/**
 *
 * @author dkx6r0c
 */
public interface IMVCFactory {

    IDomainModel CreateModel();

    IViewSource CreatePresenter();

    
}
