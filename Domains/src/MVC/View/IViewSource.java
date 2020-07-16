/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.View;

import Platform.Core.IArgument;
import Platform.Core.IExecuter;
import View.Presenter.Projections.IViewUnit;

/**
 *
 * @author User
 */
public interface IViewSource {

    IViewUnit.imaginated.list imaginate(IViewUnit.list list);
    
    void reflect(IViewUnit.imaginated unit, IViewUnit.states states);

    Object GetImage();

    IMVC_View getRender();

    IPerformer getPerformer();

    ISimplePerformer getSimplePerformer();
    
    interface IPerformer {

        IExecuter[] getExecuters();

        void execute(int viewUnit, IArgument[] arg);
    }

    interface ISimplePerformer {

        IExecuter[] getExecuters();

        void execute(int viewUnit);
    }

    static class ExecutersID {

        public static final int setImaginators = 0;
        public static final int maxID = 1;
    }
}
