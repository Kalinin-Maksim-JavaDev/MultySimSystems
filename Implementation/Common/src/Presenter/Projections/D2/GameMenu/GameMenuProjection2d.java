/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter.Projections.D2.GameMenu;

import View.Presenter.Projections.ProjectionD;
import View.Presenter.Projections.Imaginator;
import Model.Game.Presenter.Space.AmbientImage;
import Model.Game.Presenter.Space.CommonProjectionInfo;
import Controler.Menu.GameMenu.IGParametr;
import Model.Geometry.D2.Coordinates2d;
import Logic.Reflections.Space.ISpace;
import Presenter.Graphics.Space.G2D.Images.GradientArea;
import Presenter.Graphics.Space.G2D.Space2D;
import Presenter.Graphics.Space.G2D.Text;
import Render.Graphics.Geometry.ICoordinates;
import Render.View.IImaginatorFactory;
import Render.View.IImaginatorsFactorys;
import View.Presenter.Projections.IViewUnit;
import View.Presenter.Projections.IViewUnit.imaginated;

/**
 *
 * @author kalinin
 */
public abstract class GameMenuProjection2d extends ProjectionD {

    int x0 = 50;
    int y0 = 50;
    GradientArea Gradient1;

    public GameMenuProjection2d(ISpace space_) {
        super(space_);
        //Space2D space = (Space2D) art.space;
        getSpace().setPri(new CommonProjectionInfo() {

            public void CollectcommonProjectionInfo() {
                viewOffsetX = 0;
            }
        });

        Gradient1 = new GradientArea(0, getSpace(), 0, 255, 0, 0, 0, 255);
        Gradient1.AddShape(x0 + 50, y0 + 140, 80, 100);
    }

    public IImaginatorsFactorys createImaginatorsFactorys() {
       return new IImaginatorsFactorys() {

            public IImaginatorFactory getFactory(int typeID) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    protected DecorationsListManager setDecorationsImaginators() {

        DecorationsListManager result = new DecorationsListManager();

        imaginated ResumeImaginator = result.add(new Decoration(new SingleImaginator("Resume", this, 60, 120)));
        imaginated OpenSaveImaginator = result.add(new Decoration(new SingleImaginator("Save", this, 60, 100)));
        imaginated OpenLoadImaginator = result.add(new Decoration(new SingleImaginator("Load", this, 60, 80)));
        imaginated MainMenuImaginator = result.add(new Decoration(new SingleImaginator("MainMenu", this, 60, 60)));
        imaginated AmbientImaginator = result.add(new Decoration(new SingleImaginator("Fon", this, x0, y0)));

        for (int i = 0; i < 2; i++) {
            MainMenuImaginator.getImaginator().SetImage(new Text("Main menu", 2 + i, 1, getSpace()), i);
            ResumeImaginator.getImaginator().SetImage(new Text("Resume", 2 + i, 1, getSpace()), i);
            OpenSaveImaginator.getImaginator().SetImage(new Text("Save game", 2 + i, 1, getSpace()), i);
            OpenLoadImaginator.getImaginator().SetImage(new Text("Open game", 2 + i, 1, getSpace()), i);
            AmbientImaginator.getImaginator().SetImage(new AmbientImage(((Space2D) getSpace())) {

                public void PutImage(ICoordinates coor) {
                    //counter++;
                    Gradient1.Move();
                    //System.out.println(Thread.currentThread().getName()+"|");
                    Gradient1.PutImage(null);
                    //System.out.println(Thread.currentThread().getName()+".");
                }
            }, i);
        }


//      art.AddMotions(new Motion(1, "Gradient1") {
//
//            public void MotionMethod() {
//                counter++;
//                Gradient1.Move();
//                Gradient1.PutImage(null);
//            }
//        });

        return result;
    }

    @Override
    public void onImaginatorCreation(IViewUnit unit, ICoordinates position, IImaginatorFactory imagefactory) {
    }

    class SingleImaginator extends Imaginator {

        public SingleImaginator(String name_, ProjectionD prD_, int x_, int y_) {
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
