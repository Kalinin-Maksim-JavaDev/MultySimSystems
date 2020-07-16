package Presenter.Projections.D2.MainMenu;

import View.Presenter.Projections.ProjectionD;
import View.Presenter.Projections.Imaginator;
import Model.Game.Presenter.Space.CommonProjectionInfo;
import Model.Game.Presenter.Space.Space;
import Controler.Menu.MainAnimMenu.IParametr;
import Model.Geometry.D2.Coordinates2d;
import Presenter.Graphics.Space.G2D.Images.AnimeFrame;
import Presenter.Graphics.Space.G2D.FillFrame;
import Presenter.Graphics.Space.G2D.Text;
import Render.Graphics.Geometry.ICoordinates;
import Render.View.IImaginatorFactory;
import Render.View.IImaginatorsFactorys;
import View.Presenter.Projections.IViewUnit;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalinin
 */
public abstract class MainMenuProjection2d extends ProjectionD {

    public MainMenuProjection2d(Space space) {
        super(space);
        // Space2D space = (Space2D) art.space;
        getSpace().setPri(new CommonProjectionInfo() {

            public void CollectcommonProjectionInfo() {
                viewOffsetX = 0;
            }
        });
    }

    protected DecorationsListManager setDecorationsImaginators() {

        DecorationsListManager result = new DecorationsListManager();

        result.add(new Decoration(new SingleImaginator(this, 25, 170).SetImage(new AnimeFrame(1000, 1, getSpace()), 0).SetImage(new FillFrame(1, getSpace()), 1).setName("Panel1")));

        result.add(new Decoration(new SingleImaginator(this, 25, 110).SetImage(new AnimeFrame(1000, 1, getSpace()), 0).SetImage(new FillFrame(1, getSpace()), 1).setName("Panel2")));

        result.add(new Decoration(new SingleImaginator(this, 20, 120).SetImage(new Text("Start", 0, 0, getSpace()), 0).SetImage(new Text("Start", 1, 0, getSpace()), 1).setName("Start")));

        result.add(new Decoration(new SingleImaginator(this, 40, 140).SetImage(new Text("New game", 0, 2, getSpace()), 0).SetImage(new Text("New game", 1, 2, getSpace()), 1).setName("New game")));

        result.add(new Decoration(new SingleImaginator(this, 40, 80).SetImage(new Text("Load", 0, 2, getSpace()), 0).SetImage(new Text("Load", 1, 2, getSpace()), 1).setName("Load")));

        result.add(new Decoration(new SingleImaginator(this, 20, 60).SetImage(new Text("Exit", 0, 0, getSpace()), 0).SetImage(new Text("Exit", 1, 0, getSpace()), 1).setName("Exit")));

        return result;
    }

    public IImaginatorsFactorys createImaginatorsFactorys() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onImaginatorCreation(IViewUnit unit, ICoordinates position, IImaginatorFactory imagefactory) {
    }

    class SingleImaginator extends Imaginator {

        public SingleImaginator(ProjectionD prD_, int x_, int y_) {
            super(0, null, prD_);
            ((Coordinates2d) position).x = (x_);
            ((Coordinates2d) position).y = (y_);
        }

        public void Project() {
            PutImage(0);
        }

        public ICoordinates CreatePosition() {
            return new Coordinates2d();
        }

        public void Project(int n) {
            PutImage(n);
        }

        public void Translate() {
        }
    }
}
