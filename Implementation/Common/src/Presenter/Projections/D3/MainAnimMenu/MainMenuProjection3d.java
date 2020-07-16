package Presenter.Projections.D3.MainAnimMenu;

import View.Presenter.Projections.ProjectionD;
import View.Presenter.Projections.Imaginator;
import Model.Game.Presenter.Space.CommonProjectionInfo;
import Model.Game.Presenter.Space.Space;
import Controler.Menu.MainAnimMenu.IParametr;
import Model.Geometry.D2.Coordinates2d;
import Model.Models.Unit.D3.Coordinates3d;
import Presenter.Graphics.Space.G3D.Cube;
import Render.Graphics.Geometry.ICoordinates;
import Render.View.IImaginatorFactory;
import View.Presenter.Projections.IViewUnit;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalinin
 */
public abstract class MainMenuProjection3d extends ProjectionD {

    public MainMenuProjection3d(Space space_) {
        super(space_);
        //  Space3D space = (Space3D) art.space;
        getSpace().setPri(new CommonProjectionInfo() {

            public void CollectcommonProjectionInfo() {
                viewOffsetX = 0;
            }
        });
 }

    protected DecorationsListManager setDecorationsImaginators() {

        DecorationsListManager result = new DecorationsListManager();

        result.add(new Decoration(new SingleImaginator(this, 25, 170)
                .SetImage(new Cube(0x00ffffff, getSpace()), 0)
                .SetImage(new Cube(0x00ffffff, getSpace()), 1)
                .setName("Panel1")));

        result.add(new Decoration(new SingleImaginator(this, 25, 110)
                .SetImage(new Cube(0x00ffffff, getSpace()), 0)
                .SetImage(new Cube(0x00ffffff, getSpace()), 1)
                .setName("Panel2")));

        result.add(new Decoration(new SingleImaginator(this, 20, 120)
                 .SetImage(new Cube(0x00ffffff, getSpace()), 0)
                .SetImage(new Cube(0x00ffffff, getSpace()), 1)
                .setName("Start")));

        result.add(new Decoration(new SingleImaginator(this, 40, 140)
                .SetImage(new Cube(0x00ffffff, getSpace()), 0)
                .SetImage(new Cube(0x00ffffff, getSpace()), 1)
                .setName("New game")));

        result.add(new Decoration(new SingleImaginator(this, 40, 80)
                .SetImage(new Cube(0x00ffffff, getSpace()), 0)
                .SetImage(new Cube(0x00ffffff, getSpace()), 1)
                .setName("Load")));

        result.add(new Decoration(new SingleImaginator(this, 20, 60)
                .SetImage(new Cube(0x00ffffff, getSpace()), 0)
                .SetImage(new Cube(0x00ffffff, getSpace()), 1)
                .setName("Exit")));

        return result;
    }

    public void onImaginatorCreation(IViewUnit unit, ICoordinates position, IImaginatorFactory imagefactory) {
    }

    class SingleImaginator extends Imaginator {

        public SingleImaginator(ProjectionD prD_, int x_, int y_) {
            super(0, null, prD_);
            ((Coordinates2d) position).x = (x_ / IParametr.grad3d);
            ((Coordinates2d) position).y = (y_ / IParametr.grad3d);
        }

        public void Project() {
            PutImage(0);
        }

        public ICoordinates CreatePosition() {
            return new Coordinates3d();
        }

        public void Project(int n) {
            PutImage(n);
        }

        public void Translate() {
        }
    }
}
