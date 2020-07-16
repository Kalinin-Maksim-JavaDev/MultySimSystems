/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.View;

import Platform.Core.Unit.IUnit;
import Render.View.IRenderResult;

/**
 *
 * @author User
 */
public interface IMVC_View {

    reflected reflect(IUnit.drawable.projection[] projections);

    interface reflected {

        IRenderResult Render();
    }

    IViewSource getViewSource();
}
