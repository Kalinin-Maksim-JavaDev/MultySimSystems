/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter.Projections.D3.GameMenu;

import View.Presenter.Projections.ProjectionD;
import View.Presenter.Projections.Imaginator;
import Model.Game.Presenter.Space.CommonProjectionInfo;
import Model.Game.Presenter.Space.Space;
import Controler.Menu.GameMenu.IGParametr;
import Model.Geometry.D2.Coordinates2d;
import Model.Models.Unit.D3.Coordinates3d;
import Presenter.Graphics.Space.G3D.Cube;
import Render.Graphics.Geometry.ICoordinates;
import Render.View.IImaginatorFactory;
import View.Presenter.Projections.IViewUnit;

/**
 *
 * @author kalinin
 */
public abstract class GameMenuProjection3d extends ProjectionD {

    public GameMenuProjection3d(Space space_) {
        super(space_);
        //Space3D space = (Space3D) art.space;
        getSpace().setPri(new CommonProjectionInfo() {

            public void CollectcommonProjectionInfo() {
                viewOffsetX = 0;
            }
        });
    }

    protected DecorationsListManager setDecorationsImaginators() {

        DecorationsListManager result = new DecorationsListManager();

        result.add(new Decoration(new SingleImaginator(this, 60, 100).SetImage(new Cube(0x00ffffff, getSpace()), 0).SetImage(new Cube(0x00ffffff, getSpace()), 1).setName("MainMenu")));

        result.add(new Decoration(new SingleImaginator(this, 60, 40).SetImage(new Cube(0x00ffffff, getSpace()), 0).SetImage(new Cube(0x00ffffff, getSpace()), 1).setName("Resume")));
        //    space.Complete();

        return result;
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
            return new Coordinates3d();
        }

        public void Project(int n) {
            PutImage(n);
        }

        public void Translate() {
        }
    }
}
