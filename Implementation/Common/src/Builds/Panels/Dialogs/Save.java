/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Builds.Panels.Dialogs;

import MVC.View.IViewSource;
import Logic.Environment.Menu.MainAnimMenu.IMenuCommonCommandsList.Internal;
import Presenter.Projections.D2.SaveLoadDialog.SaveLoadDialogProjection2d;
import Controler.Menu.SaveLoadDialog.ISLDParametr;
import Model.Game.GParametr;
import Builds.InteractvePanel;


import Application.View.ISpaceManager;
import Logic.Environment.Menu.MainAnimMenu.IMenuCommonCommandsList;
import Controler.Menu.SaveLoadDialog.SaveLoadDialog;
import Logic.Reflections.Space.ISpace;
import MVC.Control.IControler;
import MVC.Model.IDomainModel;

/**
 *
 * @author kalinin
 */
public abstract class Save extends InteractvePanel {

    abstract protected IMenuCommonCommandsList.Internal getInternalCommandsList();
    ISpace space;

    /**
     * @param args the command line arguments
     */
    public Save(boolean oneThread_, ISpaceManager canva) {
        super(oneThread_, GParametr.quant);

        space = canva.SetSpace2D(ISLDParametr.graphicsLevel, 1, GParametr.quant, "GameMenu", 80, getLayer(), false);
    }

    public IDomainModel CreateModel() {
        return new SaveLoadDialog(getMod(), SaveLoadDialog.Save) {

            @Override
            protected Internal getCommandList() {
                return getInternalCommandsList();
            }

            public void PATCH_setUp(IControler controler) {
            }
        };
    }

    public IViewSource CreatePresenter() {
        return new SaveLoadDialogProjection2d(space);
    }
//    public void Start(StartStopPauseSystem lev_) {
//        Start();
//    }
}
