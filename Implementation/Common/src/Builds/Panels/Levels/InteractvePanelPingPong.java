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
import Model.Models.PingPong.PParametr;
import Platform.Core.ISystemM;
import Presenter.Projections.D2.PingPong.PingPongProjection2d;

/**
 *
 * @author kalinin
 */
public abstract class InteractvePanelPingPong extends InteractvePanel implements IEnvironment {

    abstract protected IGameCommonCommandsList getCommandsList();
    final ISpace space;

    public int setLayer() {
        return 0;
    }

    /**
     * @param args the command line arguments
     */
    public InteractvePanelPingPong(boolean oneThread_, ISpaceManager spaceManager) {
        super(oneThread_, GParametr.quant);
//        f_.GetPanel2().setSize(f_.GetPanel2().getWidth()+PParametr.grad, f_.GetPanel2().getHeight()+PParametr.grad);
//        ((PingPong)mod).g=f_.GetPanel2().getGraphics();
//        ((PingPong)mod).im=f_.GetPanel2().createImage(f_.GetPanel2().getWidth(),f_.GetPanel2().getHeight());
//        ((PingPong)mod).g2=((PingPong)mod).im.getGraphics();
//        ((PingPong)mod).IMap.s.start();


        space = spaceManager.SetSpace2D(PParametr.graphicsLevel, PParametr.grad, GParametr.quant, "Game", 20, getLayer(), false);
    }

    public IDomainModel CreateModel() {
        return GameFabric.CreatePingPong(this, space.getLogCanvas());
    }

    public IViewSource CreatePresenter() {
        IViewSource _presenter = new PingPongProjection2d(space) {

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
