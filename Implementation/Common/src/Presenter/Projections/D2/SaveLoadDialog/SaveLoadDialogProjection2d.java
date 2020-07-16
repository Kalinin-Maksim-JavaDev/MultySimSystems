/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter.Projections.D2.SaveLoadDialog;

import View.Presenter.Projections.ProjectionD;
import View.Presenter.Projections.Imaginator;
import Model.Game.Presenter.Space.CommonProjectionInfo;
import Controler.Menu.SaveLoadDialog.ISLDParametr;
import Model.Geometry.D2.Coordinates2d;
import Logic.Reflections.Space.ISpace;
import Model.Game.Presenter.Space.AmbientImage;
import Presenter.Graphics.Space.G2D.Images.GradientArea;
import Presenter.Graphics.Space.G2D.Text;
import Render.Graphics.Geometry.ICoordinates;
import Render.View.IImaginatorFactory;
import Render.View.IImaginatorsFactorys;
import View.Presenter.Projections.IViewUnit;
import View.Presenter.Projections.IViewUnit.imaginated;
import java.awt.Color;

/**
 *
 * @author kalinin
 */
public class SaveLoadDialogProjection2d extends ProjectionD {

    int x0 = 100;
    int y0 = 0;
    GradientArea Gradient1;
    IViewUnit[] units;

    public SaveLoadDialogProjection2d(ISpace space_) {
        super(space_);
        //Space2D space = (Space2D) art.space;
        getSpace().setPri(new CommonProjectionInfo() {

            public void CollectcommonProjectionInfo() {
                viewOffsetX = 0;
            }
        });

        Gradient1 = new GradientArea(0, getSpace(), 0, 255, 255, 0, 0, 255);
        Gradient1.AddShape(x0 + 50, y0 + 240, 120, 160);
    }

    protected DecorationsListManager setDecorationsImaginators() {

        DecorationsListManager result = new DecorationsListManager();

        imaginated Slot0Im = result.add(new Decoration(new SingleImaginator(this, 60, 180).setName("Slot0")));
        imaginated Slot1Im = result.add(new Decoration(new SingleImaginator(this, 60, 160).setName("Slot1")));
        imaginated Slot2Im = result.add(new Decoration(new SingleImaginator(this, 60, 140).setName("Slot2")));
        imaginated Slot3Im = result.add(new Decoration(new SingleImaginator(this, 60, 120).setName("Slot3")));
        imaginated Slot4Im = result.add(new Decoration(new SingleImaginator(this, 60, 100).setName("Slot4")));
        imaginated AmbientIm = result.add(new Decoration(new SingleImaginator(this, x0, y0).setName("Fon")));

        for (int i = 0; i < 2; i++) {
            Slot0Im.getImaginator().SetImage(new Text("Slot 0", 2 + i, 1, getSpace()), i);
            Slot1Im.getImaginator().SetImage(new Text("Slot 1", 2 + i, 1, getSpace()), i);
            Slot2Im.getImaginator().SetImage(new Text("Slot 2", 2 + i, 1, getSpace()), i);
            Slot3Im.getImaginator().SetImage(new Text("Slot 3", 2 + i, 1, getSpace()), i);
            Slot4Im.getImaginator().SetImage(new Text("Slot 4", 2 + i, 1, getSpace()), i);
            AmbientIm.getImaginator().SetImage(new AmbientImage(getSpace()) {

                public void PutImage(ICoordinates coor) {
                    //counter++;
                    Gradient1.Move();
                    Gradient1.PutImage(null);
                }

                public Color GetColor() {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            }, i);
        }



//        art.AddMotions(new Motion(1, "Gradient1") {
//
//            public void MotionMethod() {
//                counter++;
//                Gradient1.Move();
//                Gradient1.PutImage(null);
//            }
//        });

        return result;
    }

    public String SetThreadName() {
        return "SaveDialogDIS";
    }

    public IImaginatorsFactorys createImaginatorsFactorys() {
        return new IImaginatorsFactorys() {

            public IImaginatorFactory getFactory(int typeID) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    public void onImaginatorCreation(IViewUnit unit, ICoordinates position, IImaginatorFactory projectionCreator) {
    }

    class SingleImaginator extends Imaginator {

        public SingleImaginator(ProjectionD prD_, int x_, int y_) {
            super(0, null, prD_);

            ((Coordinates2d) position).x = (x0 + x_);
            ((Coordinates2d) position).y = (y0 + y_);
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
