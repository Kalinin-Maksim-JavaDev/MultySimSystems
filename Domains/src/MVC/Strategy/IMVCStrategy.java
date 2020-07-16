/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.Strategy;

import Logic.Model.IDataSource;
import MVC.Model.IDomainModel;
import MVC.View.IViewSource;
import Platform.Core.IMotion;

/**
 *
 * @author dkx6r0c
 */
public interface IMVCStrategy {

    IMotion init(IDataSource _dataSource);

    IDomainModel getDomainModel();

    IViewSource getPresenter();
}
