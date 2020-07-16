/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Builds.Panels.Levels;

import Model.Game.GParametr;
import Builds.InteractvePanel;
import Application.Build.Panels.Model.IEnvironment;
import MVC.View.IViewSource;
import Application.View.ISpaceManager;
import Logic.IGameCommonCommandsList;
import Logic.Reflections.Space.ISpace;
import MVC.Model.IDomainModel;
import Model.Game.GameFabric;
import View.Presenter.Projections.ProjectionD;
import Model.Models.Tetris.TParametr;
import Platform.Core.ISystemM;
import Presenter.Projections.D2.Tetris.TetrisProjection2d;

/**
 *
 * @author kalinin
 */
public abstract class InteractvePanelTetris extends InteractvePanel implements IEnvironment {

    abstract protected IGameCommonCommandsList getCommandsList();
    final ISpace space;

    public int setLayer() {
        return 0;
    }

    /**
     * @param args the command line arguments
     */
    public InteractvePanelTetris(boolean oneThread_, ISpaceManager canva) {

        super(oneThread_, GParametr.quant);


//         f_.GetPanel2().setSize(f_.GetPanel2().getWidth()+TParametr.grad, f_.GetPanel2().getHeight()+TParametr.grad);
//        ((Tetris)mod).g=f_.GetPanel2().getGraphics();
//        ((Tetris)mod).im=f_.GetPanel2().createImage(f_.GetPanel2().getWidth(),f_.GetPanel2().getHeight());
//        ((Tetris)mod).g2=((Tetris)mod).im.getGraphics();
//        ((Tetris)mod).IMap.s.start();


        space = canva.SetSpace2D(TParametr.graphicsLevel, TParametr.grad, GParametr.quant, "Game", 20, getLayer(), false);

    }

    public void createImaginators() {
    }

    public  IDomainModel CreateModel() {
        return GameFabric.CreateTetris(this, space.getLogCanvas());
    }

    public IViewSource CreatePresenter() {
        IViewSource _presenter = new TetrisProjection2d(space) {

            public String SetThreadName() {
                return "GameDIS";
            }
        };
        ((ISystemM) ((ProjectionD) _presenter).art).getTaskMotions().setName("Render game");

        return _presenter;
    }

    public void ExitGame() {
        Stop();
    }
}
