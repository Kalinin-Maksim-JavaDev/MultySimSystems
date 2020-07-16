/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Builds.Panels.Dialogs;

import MVC.Control.IControler;
import MVC.View.IViewSource;
import Logic.Environment.Menu.MainAnimMenu.IMenuCommonCommandsList.Internal;
import Presenter.Projections.D2.GameMenu.GameMenuProjection2d;
import Controler.Menu.GameMenu.IGParametr;
import Model.Game.GParametr;
import Builds.InteractvePanel;
import Application.View.ISpaceManager;
import Controler.Menu.GameMenu.GameMenu;
import Logic.Environment.Menu.MainAnimMenu.IMenuCommonCommandsList;
import Logic.Reflections.Space.ISpace;
import MVC.Model.IDomainModel;

/**
 *
 * @author kalinin
 */
public abstract class InternalMenu extends InteractvePanel {

    abstract protected IMenuCommonCommandsList.Internal getInternalCommandsList();
    private final ISpace space;

    /**
     * @param args the command line arguments
     */
    public InternalMenu(boolean oneThread_, ISpaceManager spaceManager) {

        super(oneThread_, GParametr.quant);

        space = spaceManager.SetSpace2D(IGParametr.graphicsLevel, 1, GParametr.quant, "GameMenu", 80, getLayer(), false);
    }

    public IDomainModel CreateModel() {
        return new GameMenu(getMod()) {

            public String SetThreadName() {
                return "GameMenuMOD";
            }

            @Override
            protected Internal getCommandList() {
                return getInternalCommandsList();
            }

            public void PATCH_setUp(IControler controler) {

            }
        };
    }

    public IViewSource CreatePresenter() {
        return new GameMenuProjection2d(space) {

            public String SetThreadName() {
                return "GameMenuDIS";
            }
        };
    }
}
