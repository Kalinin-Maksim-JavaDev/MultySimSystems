/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Builds.Panels.Dialogs;

import MVC.View.IViewSource;
import Logic.Environment.Menu.MainAnimMenu.IMenuCommonCommandsList;
import Logic.Environment.Menu.MainAnimMenu.IMenuCommonCommandsList.Main;
import Controler.Menu.MainAnimMenu.IParametr;
import Presenter.Projections.D2.MainAnimMenu.MainMenuProjection2d;
import Model.Game.GParametr;
import Builds.InteractvePanel;
import Application.View.ISpaceManager;
import Controler.Menu.MainAnimMenu.MainAnimMenu;
import Logic.Reflections.Space.ISpace;
import MVC.Control.IControler;
import MVC.Model.IDomainModel;

/**
 *
 * @author kalinin
 */
public abstract class PanelGlobalMenu extends InteractvePanel {

    abstract protected IMenuCommonCommandsList.Main getCommandList();
    ISpace space;
    int dx;

    /**
     * @param args the command line arguments
     */
    public PanelGlobalMenu(boolean oneThread_, ISpaceManager canva, int dx) {
        super(oneThread_, GParametr.quant);
//        Thread.currentThread().setName("*");
        this.dx = dx;

        space = canva.SetSpace2D(IParametr.graphicsLevel, 1, GParametr.quant, "GameMenu", 20, getLayer(), true);

    }

    public int getLayer() {
        return 0;
    }

    public IDomainModel CreateModel() {
        return new MainAnimMenu(getMod()) {

            public String SetThreadName() {
                return "MainMenuMOD";
            }

            @Override
            protected Main getCommandList() {
                return PanelGlobalMenu.this.getCommandList();
            }

            public void PATCH_setUp(IControler controler) {
            }
        };
    }

    public IViewSource CreatePresenter() {
        return new MainMenuProjection2d(space, dx) {

            public String SetThreadName() {
                return "MainMenuDIS";
            }
        };
    }
//    public void Start(StartStopPauseSystem lev_) {
//        Start();
//    }
}
