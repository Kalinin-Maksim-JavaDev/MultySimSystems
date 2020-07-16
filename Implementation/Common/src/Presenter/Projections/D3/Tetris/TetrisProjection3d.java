/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter.Projections.D3.Tetris;

import Model.Game.Presenter.Space.CommonProjectionInfo;
import Model.Game.Presenter.Space.Space;
import View.Presenter.Projections.ProjectionD;
import View.Presenter.Projections.Imaginator;
import Model.Geometry.D2.Coordinates2d;
import Render.Graphics.Geometry.ICoordinates;
import Render.View.IImaginatorFactory;
import View.Presenter.Projections.IViewUnit;

/**
 *
 * @author Adm
 */
public abstract class TetrisProjection3d extends ProjectionD {

    Imaginator focusProjection;

    public TetrisProjection3d(Space space_) {
        super(space_);
        //      Space3D space = (Space3D) art.space;
        getSpace().setPri(new CommonProjectionInfo() {

            public void CollectcommonProjectionInfo() {
                viewOffsetX = 0;
            }
        });



        // space.Complete();
    }

    public void createImaginators(IViewUnit.imaginated.list list) {
    }

    public void onImaginatorCreation(IViewUnit unit, ICoordinates position, IImaginatorFactory imagefactory) {

    }

    private class SingleImaginator extends Imaginator {

        public SingleImaginator(IViewUnit viewUnit, ICoordinates position, ProjectionD prD_) {
            super(viewUnit.getIndex(), position, prD_);
        }

        public void Project() {
            PutImage(0);
        }

        public void Translate() {
            ((Coordinates2d) position).x = (((Coordinates2d) OriginalPosition).x);
            ((Coordinates2d) position).y = (((Coordinates2d) OriginalPosition).y);
        }

        public ICoordinates CreatePosition() {
            return new Coordinates2d();
        }

        public void Project(int n) {
            PutImage(n);
        }
    }
}
