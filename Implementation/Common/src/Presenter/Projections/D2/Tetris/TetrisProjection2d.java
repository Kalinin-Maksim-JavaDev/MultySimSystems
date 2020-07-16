package Presenter.Projections.D2.Tetris;

import Model.Models.Tetris.TParametr;
import Model.Game.Presenter.Space.CommonProjectionInfo;
import Presenter.Graphics.Space.G2D.Space2D;
import Presenter.Graphics.Space.G2D.Images.Sprite2D_;
import View.Presenter.Projections.ProjectionD;
import View.Presenter.Projections.Imaginator;
import Platform.Util.Random;
import Model.Geometry.D2.Coordinates2d;
import Logic.Reflections.Space.ISpace;
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
public abstract class TetrisProjection2d extends ProjectionD {

    public static Random random = new Random();
    String f = "F";
    String g = "G";
    final Sprite2D_ kolobok1;
    final Sprite2D_ kolobok2;

    public TetrisProjection2d(ISpace space) {
        super(space);

        // Space2D space = (Space2D) art.space;
        getSpace().setPri(new CommonProjectionInfo() {

            public void CollectcommonProjectionInfo() {
                viewOffsetX = 0;
            }
        });
        kolobok1 = Space2D.newSprite(getSpace(), "Tetris/Images/kolobok1.png", 39, 32, TParametr.grad, 8, 100);
        kolobok2 = Space2D.newSprite(getSpace(), "Tetris/Images/kolobok2.png", 43, 32, TParametr.grad, 8, 100);


    }

    protected DecorationsListManager setDecorationsImaginators() {

        DecorationsListManager result = new DecorationsListManager();

        return result;
    }

    public IImaginatorsFactorys createImaginatorsFactorys() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onImaginatorCreation(IViewUnit unit, ICoordinates position, IImaginatorFactory imagefactory) {

        unit.setImaginator(new SingleImaginator(unit, position, this)).getImaginator().SetImage(Space2D.newAnimeImage(getSpace(), kolobok1, 0, random.nextInt(kolobok1.period)), 0).SetImage(Space2D.newAnimeImage(getSpace(), kolobok2, 0, random.nextInt(kolobok2.period)), 1).setName(" draw " + "Block " + unit.getIndex());
        // ( units[i]).getImage()sCollection[0] = space.new CircleImage(0);
        //   ( units[i]).getImage()sCollection[1] = space.new CircleImage(0);

    }

    private class SingleImaginator extends Imaginator {

        public SingleImaginator(IViewUnit viewUnit, ICoordinates position, ProjectionD prD_) {
            super(viewUnit.getIndex(), position, prD_);
        }

        public void Project() {

            PutImage(0);
        }

        public void Translate() {
            ((Coordinates2d) position).x = ((int) (((Coordinates2d) OriginalPosition).x * TParametr.grad));
            ((Coordinates2d) position).y = ((int) (((Coordinates2d) OriginalPosition).y * TParametr.grad));
        }

        public ICoordinates CreatePosition() {
            return new Coordinates2d();
        }

        public void Project(int n) {
            PutImage(n);
        }
    }
}
