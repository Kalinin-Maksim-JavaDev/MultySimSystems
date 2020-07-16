package Presenter.Projections.D2.PingPong;

import View.Presenter.Projections.Imaginator;
import Model.Game.Presenter.Space.CommonProjectionInfo;
import View.Presenter.Projections.ProjectionD;
import Model.Geometry.D2.Coordinates2d;
import Logic.Reflections.Space.ISpace;
import Model.Models.PingPong.PParametr;
import Render.Graphics.Geometry.ICoordinates;
import Render.View.IImaginatorFactory;
import Render.View.IImaginatorsFactorys;
import View.Presenter.Projections.IViewUnit;
import java.util.Random;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalinin
 */
public abstract class PingPongProjection2d extends ProjectionD {

    public static Random random = new Random();

    public PingPongProjection2d(ISpace space_) {
        super(space_);

        //  Space2D space = (Space2D) art.space;
        getSpace().setPri(new CommonProjectionInfo() {

            public void CollectcommonProjectionInfo() {
                viewOffsetX = 0;
            }
        });

    }

    protected DecorationsListManager setDecorationsImaginators() {

        DecorationsListManager result = new DecorationsListManager();

        return result;
    }

    public void f() {
    }

    public IImaginatorsFactorys createImaginatorsFactorys() {
        throw new UnsupportedOperationException("Not supported yet.");
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
            ((Coordinates2d) position).x = ((int) (((Coordinates2d) OriginalPosition).x * PParametr.grad));
            ((Coordinates2d) position).y = ((int) (((Coordinates2d) OriginalPosition).x * PParametr.grad));
        }

        public ICoordinates CreatePosition() {
            return new Coordinates2d();
        }

        public void Project(int n) {
            PutImage(n);
        }
    }
}
