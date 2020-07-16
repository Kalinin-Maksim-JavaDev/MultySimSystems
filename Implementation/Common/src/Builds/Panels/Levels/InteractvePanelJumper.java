/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Builds.Panels.Levels;

import MVC.View.IViewSource;
import Model.Game.GParametr;
import Presenter.Platformer.JParametr;
import Presenter.Platformer.JumperProjection2d;
import Builds.InteractvePanel;
import Application.View.ISpaceManager;
import Model.Game.GameFabric;
import Application.Build.Panels.Model.IInteractvePanel;
import Logic.IGameCommonCommandsList;
import Logic.Reflections.Space.ISpace;
import MVC.Model.IDomainModel;

/**
 *
 * @author kalinin
 */
public abstract class InteractvePanelJumper extends InteractvePanel implements IInteractvePanel.Window.Graphical {

    abstract protected IGameCommonCommandsList getCommandsList();
    byte[][] decorations;
    final ISpace space;

    public Object[][] DEBUG_getMap() {
        return DEBUG_getDomainModel().DEBUG_getMap();
    }

    /**
     * @param args the command line arguments
     */
    public InteractvePanelJumper(byte[][] decorations_, boolean oneThread_, ISpaceManager spaceManager) {
        super(oneThread_, GParametr.quant);
        space = spaceManager.SetSpace2D(JParametr.graphicsLevel, JParametr.grad, GParametr.quant, "Game", 20, getLayer(), false);
        decorations = decorations_;
    }

    public IDomainModel CreateModel() {
        return GameFabric.CreateJumper(this, getCommandsList(), InteractvePanelJumper.this, space.getLogCanvas());
    }

    public IViewSource CreatePresenter() {
        return new JumperProjection2d(space, decorations);
    }

    public void GameMenu() {
        getCommandsList().OpenGameMenu();
    }

    public void MainMenu() {
        getCommandsList().ExitToMainMenuFromGame();
    }
}
