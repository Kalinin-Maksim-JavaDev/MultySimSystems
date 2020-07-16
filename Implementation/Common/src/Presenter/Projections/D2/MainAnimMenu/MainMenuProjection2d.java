package Presenter.Projections.D2.MainAnimMenu;

import Logic.Reflections.Projections.IImaginator.charged;
import View.Presenter.Projections.ProjectionD;
import View.Presenter.Projections.Imaginator;
import Model.Game.Presenter.Space.CommonProjectionInfo;
import Controler.Menu.MainAnimMenu.IParametr;
import Model.Geometry.D2.Coordinates2d;
import Logic.Reflections.Space.ISpace;
import Presenter.Graphics.Space.G2D.Space2D;
import Presenter.Graphics.Space.G2D.Images.Sprite2D_;
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

    final Sprite2D_ Start;
    final Sprite2D_ Exit;
    final Sprite2D_ StartExit;
    final Sprite2D_ New;
    final Sprite2D_ Load;
    final Sprite2D_ NewLoad;
    int dx;

    public MainMenuProjection2d(ISpace space_, int dx) {
        super(space_);
        this.dx = dx;
        // Space2D space = (Space2D) art.space;
        getSpace().setPri(new CommonProjectionInfo() {

            public void CollectcommonProjectionInfo() {
                viewOffsetX = 0;
            }
        });
        Start = Space2D.newSprite(getSpace(), "/Menu/Start.png", 240, 320, 0, 1, 1000);
        Exit = Space2D.newSprite(getSpace(), "/Menu/Exit.png", 240, 320, 0, 1, 1000);
        StartExit = Space2D.newSprite(getSpace(), "/Menu/StartExit.png", 240, 320, 0, 1, 1000);
        New = Space2D.newSprite(getSpace(), "/Menu/New.png", 240, 320, 0, 1, 1000);
        Load = Space2D.newSprite(getSpace(), "/Menu/Load.png", 240, 320, 0, 1, 1000);
        NewLoad = Space2D.newSprite(getSpace(), "/Menu/NewLoad.png", 240, 320, 0, 1, 1000);
    }

    protected DecorationsListManager setDecorationsImaginators() {

        DecorationsListManager result = new DecorationsListManager();


        return result;
    }

    public IImaginatorsFactorys createImaginatorsFactorys() {

        ImaginatorsFactorys result = new ImaginatorsFactorys();

        result.startFactory = new IImaginatorFactory() {

            public charged getImaginator(IViewUnit viewUnit, ICoordinates position) {
                return new SingleImaginator(MainMenuProjection2d.this, 0 + dx, 320).SetImage(Space2D.newAnimeImage(getSpace(), Start, 0, 0), 0).SetImage(Space2D.newAnimeImage(getSpace(), StartExit, 0, 0), 1).setName("Start");
            }
        };

        result.newGameFactory = new IImaginatorFactory() {

            public charged getImaginator(IViewUnit viewUnit, ICoordinates position) {
                return new SingleImaginator(MainMenuProjection2d.this, 0 + dx, 320).SetImage(Space2D.newAnimeImage(getSpace(), New, 0, 0), 0).SetImage(Space2D.newAnimeImage(getSpace(), NewLoad, 0, 0), 1).setName("New game");
            }
        };

        result.loadFactory = new IImaginatorFactory() {

            public charged getImaginator(IViewUnit viewUnit, ICoordinates position) {
                return new SingleImaginator(MainMenuProjection2d.this, 0 + dx, 320).SetImage(Space2D.newAnimeImage(getSpace(), Load, 0, 0), 0).SetImage(Space2D.newAnimeImage(getSpace(), NewLoad, 0, 0), 1).setName("Load");
            }
        };

        result.exitFactory = new IImaginatorFactory() {

            public charged getImaginator(IViewUnit viewUnit, ICoordinates position) {
                return new SingleImaginator(MainMenuProjection2d.this, 0 + dx, 320).SetImage(Space2D.newAnimeImage(getSpace(), Exit, 0, 0), 0).SetImage(Space2D.newAnimeImage(getSpace(), StartExit, 0, 0), 1).setName("Exit");
            }
        };

        return result;
    }

    @Override
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

class ImaginatorsFactorys implements IImaginatorsFactorys {

    IImaginatorFactory startFactory;
    IImaginatorFactory newGameFactory;
    IImaginatorFactory loadFactory;
    IImaginatorFactory exitFactory;

    static public enum Type {

        start, newGame, load, exit;

        static public Type get(int id) {

            Type result = null;

            if (id == 1) {
                result = start;
            } else if (id == 2) {
                result = newGame;
            } else if (id == 3) {
                result = load;
            } else if (id == 4) {
                result = exit;
            }

            if (result.getId() != id) {
            }
            return result;
        }

        public int getId() {
            int result = -1;

            if (this == start) {
                result = 1;
            } else if (this == newGame) {
                result = 2;
            } else if (this == load) {
                result = 3;
            } else if (this == exit) {
                result = 4;
            }

            return result;
        }
    }

    public IImaginatorFactory getFactory(int typeID) {

        Type type = Type.get(typeID);

        IImaginatorFactory result = null;

        if (type == type.start) {

            result = startFactory;
        } else if (type == type.newGame) {

            result = newGameFactory;
        } else if (type == type.load) {

            result = loadFactory;
        } else if (type == type.exit) {

            result = exitFactory;
        }

        return result;
    }
}
