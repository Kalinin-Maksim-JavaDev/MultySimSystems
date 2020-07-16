/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.Strategy;

import Logic.Model.IDataSource;
import MVC.Control.IControl;
import MVC.Control.IControler;
import MVC.Model.IDomainModel;
import MVC.View.IViewSource;
import Platform.Core.IMotion;
import Platform.Core.Motion.Motion;

/**
 *
 * @author dkx6r0c
 */
public class MVCFuncional implements IMVCStrategy {

    private IViewSource presenter;
    private IDomainModel domainModel;
    private IMotion initMotion;
    private IControl control;
    private IControler controler;

    public MVCFuncional(IMVCFactory factory, IControl control) {
        this.control = control;

        presenter = factory.CreatePresenter();
        domainModel = factory.CreateModel();
        controler = control.createControler(domainModel.getControllerSource());
    }

    public IMotion init(final IDataSource _dataSource) {

        initMotion = new Motion(1, "Init") {

            IDataSource dataSource = _dataSource;

            @Override
            public void MotionMethod() {

                MCVLoop.create().with(domainModel.createUnits(dataSource).imaginateUnits(presenter)).with(presenter.getRender()).with(control.controled(controler.with(domainModel.getControllerSource().getControlMotionsProducer()).withStartedManipulator(domainModel.getControllerSource().getAction()))).runOn(getReciver());
            }
        };

        return initMotion;
    }

    public IDomainModel getDomainModel() {
        return domainModel;
    }

    public IViewSource getPresenter() {
        return presenter;
    }
}
