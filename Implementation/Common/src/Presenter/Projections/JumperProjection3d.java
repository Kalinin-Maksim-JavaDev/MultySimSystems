/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter.Projections;

import Logic.Reflections.Projections.IImaginator;
import View.Presenter.Projections.ProjectionD;
import View.Presenter.Projections.Imaginator;
import Model.Geometry.D2.Coordinates2d;
import Platform.Core.IMotion;
import Platform.DataStructers.ISelection;
import Model.Geometry.UnitPoint;
import Presenter.Graphics.Space.G3D.CommonProjectionInfo3d;
import Presenter.Graphics.Space.G3D.Cube;
import Presenter.Graphics.Space.G3D.Image3D;
import Presenter.Graphics.Space.G3D.Space3D;
import Presenter.Graphics.Space.G3D.Sphere_;
import Presenter.Graphics.Space.G3D.SpotLight;
import Presenter.Graphics.Space.G3D.Sprite3D_;
import Presenter.Graphics.Space.G3D.SpriteImage;
import Presenter.Platformer.JParametr;
import Model.Game.Presenter.Space.AmbientImage;
import Model.Game.Presenter.Space.Anime;
import Model.Game.Presenter.Space.Space;
import Model.Models.Unit.D3.Coordinates3d;
import Platform.DataStructers.ILinkedList;
import Platform.Util.Random;
import Logic.Reflections.Space.IImage;
import Logic.Reflections.Space.ISpace;
import View.Presenter.Projections.MotionProjectEndLess;
import Render.Graphics.Geometry.ICoordinates;
import Render.View.IImaginatorFactory;
import Render.View.IImaginatorsFactorys;
import View.Presenter.Projections.IViewUnit;
import View.Presenter.Projections.IViewUnit.states;

/**
 *
 * @author Adm
 */
public abstract class JumperProjection3d extends ProjectionD {

    // ProjectionObject focusProjection;
    Random random = new Random();
    public ICoordinates jumperPosition;
    public double viewOffsetX = 0;
    public double viewOffsetY = 0;
    SpotLight l1;
    SpotLight l2;
    SpotLight l3;
    SingleImaginator l4;
    final Sprite3D_ fund;
    final Sprite3D_ wall;
    final Sprite3D_ jump;
    final Sprite3D_ state;
    final Sprite3D_ run;
    final Sprite3D_ ball;

    public JumperProjection3d(Space space_) {
        super(space_);
        // Space3D space = (Space3D) art.space;
        space.setPri(new CommonProjectionInfo3dJumper(this, null));
        fund = ((Space3D) space).newSprite("Platformer/Images/fund.png", 40, 32, 16, 800);
        wall = ((Space3D) space).newSprite("Platformer/Images/wall.png", 40, 32, 8, 400);
        jump = ((Space3D) space).newSprite("Platformer/Images/jump.png", 40, 32, 8, 400);
        state = ((Space3D) space).newSprite("Platformer/Images/state.png", 40, 32, 8, 400);
        run = ((Space3D) space).newSprite("Platformer/Images/run.png", 40, 32, 8, 400);
        ball = ((Space3D) space).newSprite("Platformer/Images/ball.png", 40, 32, 16, 800);


        Coordinates3d coor = new Coordinates3d();
        int z = 0;
        for (int i = 0; i < 1; i++) {
            Image3D s_ = new SpriteImage("Platformer/Images/fund.png", space);
            coor.xv = random.nextInt(JParametr.w);
            coor.yv = random.nextInt(JParametr.h);
            if (random.nextInt(2) == 0) {
                z = 3 + random.nextInt(7);
            } else {
                z = -3 - random.nextInt(7);
            }
            coor.zv = z;
            s_.PutImage(coor);
        }



        l1 = new SpotLight(JParametr.h * 1 / 4, JParametr.h, 0, 90, 0, 2 * (1 - 2 * random.nextFloat()), space);
        l2 = new SpotLight(JParametr.h * 2 / 4, JParametr.h, 0, 90, 0, 2 * (1 - 2 * random.nextFloat()), space);
        l3 = new SpotLight(JParametr.h * 3 / 4, JParametr.h, 0, 90, 0, 2 * (1 - 2 * random.nextFloat()), space);
        l4 = new SingleImaginator(null, this);
        l4.SetImage(new SpotLight(0, 0, 0, 0, -45, 0, space), 0);

        l4.setPosition(((CommonProjectionInfo3d) space_.pri).lightVector[0]);
        ((Space3D) space).Complete();
    }

    protected DecorationsListManager setDecorationsImaginators() {

        DecorationsListManager result = new DecorationsListManager();
        result.add(new Decoration(new AmbientImageProject(null, this).SetImage(new AmbientImageJumper(space, this), 0)));

return result;
        
    }

    public IImaginatorsFactorys createImaginatorsFactorys() {

        JumperImaginatorsFactorys result = new JumperImaginatorsFactorys();
        result.AnimatedFundamentFactory = new IImaginatorFactory() {

            public IImaginator.charged getImaginator(IViewUnit viewUnit, ICoordinates position) {

                return new EndLessImaginator(viewUnit, position, JumperProjection3d.this).SetImage(new Cube(0x0000ffff, space), 0).setName(" draw " + " block " + viewUnit);

            }
        };
        result.WallFactory = new IImaginatorFactory() {

            public IImaginator.charged getImaginator(IViewUnit viewUnit, ICoordinates position) {
                return new EndLessImaginator(viewUnit, position, JumperProjection3d.this).SetImage(new Cube(0x0000ff00, space), 0).setName(" draw " + " block " + viewUnit);

            }
        };
        result.BallFactory = new IImaginatorFactory() {

            public IImaginator.charged getImaginator(IViewUnit viewUnit, ICoordinates position) {
                return new EndLessImaginator(viewUnit, position, JumperProjection3d.this).SetImage(new Sphere_(0x0000ff00, space), 0).setName(" draw " + " ball " + viewUnit);

            }
        };
        result.JumperFactory = new IImaginatorFactory() {

            public IImaginator.charged getImaginator(IViewUnit viewUnit, ICoordinates position) {


                double[] xsjs = {-2, 2, 0, 0, -2, 2, 0};
                double[] ysjs = {-4, -4, -1, 1, 2, 2, 3};
                double[] kjs = {1, 1, 1, 1, 1, 1, 1};
                double[] kjr = {0.3, 0.3, 0.4, 0.5, 1, 1, 0.6};
                double[] kjj = {0.3, 0.3, 0.4, 0.5, 1, 1, 0.6};
                double[] kjf = {1, 1, 0.8, 0.8, 0.4, 0.4, 0.8};
                int[] cjs = {0, 0, 128, 128, 200, 64, 200, 64, 255};

                Coordinates2d[] pj = {new Coordinates2d(), new Coordinates2d(), new Coordinates2d(), new Coordinates2d(), new Coordinates2d(), new Coordinates2d(), new Coordinates2d(), new Coordinates2d(), new Coordinates2d()};
                Coordinates2d[] cpj = {new Coordinates2d(), new Coordinates2d(), new Coordinates2d(), new Coordinates2d(), new Coordinates2d(), new Coordinates2d(), new Coordinates2d(), new Coordinates2d(), new Coordinates2d()};

                for (int ii = 0; ii < xsjs.length; ii++) {
                    pj[ii].xd = (pj[ii].xd + (xsjs[ii]) / 10);
                    pj[ii].yd = (pj[ii].yd + (ysjs[ii]) / 10);
                }
                Coordinates2d coor_ = new Coordinates2d();

                jumperPosition = position;

                return new EndLessImaginator(viewUnit, position, JumperProjection3d.this).SetImage(((Space3D) space).newBallsConf(0, coor_, kjs, cjs, pj, cpj), 0).SetImage(((Space3D) space).newBallsConf(0, coor_, kjr, cjs, pj, cpj), 0).SetImage(((Space3D) space).newBallsConf(0, coor_, kjj, cjs, pj, cpj), 0).SetImage(((Space3D) space).newBallsConf(0, coor_, kjf, cjs, pj, cpj), 0).setName(" draw " + " jump " + viewUnit);
            }
        };
        return result;
    }

    public void onImaginatorCreation(IViewUnit unit, ICoordinates position, IImaginatorFactory imagefactory) {

    }

    class CommonProjectionInfo3dJumper extends CommonProjectionInfo3d {

        JumperProjection3d jp3d;
        private UnitPoint jumper;
        ILinkedList units;

        public CommonProjectionInfo3dJumper(JumperProjection3d jp3d_, ILinkedList units_) {
            jp3d = jp3d_;
            units = units_;
        }

        public void CollectcommonProjectionInfo() {
            Coordinates3d jpos = (Coordinates3d) jp3d.jumperPosition;
            //
            //        if (jumper.speed.y == 0) {
            //            double x0 = jpos.xv - jpos.x;
            //            double a =-360* x0;
            //            jumperViewVector[0].xv = jpos.xv+0.5*Math.cos(a*Math.PI/180);
            //            jumperViewVector[0].yv = jpos.yv+0.5*Math.sin(a*Math.PI/180);
            //            jumperViewVector[1].xv=a;
            //        } else {
            //            jumperViewVector[0].xv = jpos.xv;
            //            jumperViewVector[0].yv = jpos.yv-0.5;
            //            jumperViewVector[1].xv = -90;
            //        }
            double xv = jpos.xv;
            double deltx = xv - viewOffsetX;
            viewOffsetX += deltx * 0.05;
            double yv = jpos.yv;
            double delty = yv - viewOffsetY;
            viewOffsetY += delty * 0.05;

            Coordinates3d vv = (Coordinates3d) viewVector[0];
            vv.xv = viewOffsetX;
            vv.yv = viewOffsetY;
            vv.zv = 10;

            Coordinates3d lv = (Coordinates3d) lightVector[0];
            lv.xv = viewOffsetX;
            lv.yv = viewOffsetY;
            lv.zv = 10;
            jp3d.l4.Project();
            renderedObject = 0;
            double wa = viewOffsetX - 3;
            double wb = viewOffsetX + 3;
            double ha = viewOffsetY - 3;
            double hb = viewOffsetY + 3;
            ISelection selection = units.select();
            while (selection.getNext()) {
                IImage img_ = ((Imaginator) selection.getCurrent()).GetImage();
                if (img_ != null) {
                    Coordinates3d pos = (Coordinates3d) ((Imaginator) selection.getCurrent()).getPosition();
                    if ((pos.xv > wa) && (pos.xv < wb) && (pos.yv > ha) && (pos.yv < hb)) {
                        ((Image3D) img_).setRenderingEnable(true);
                        renderedObject++;
                    } else {
                        ((Image3D) img_).setRenderingEnable(false);
                    }
                }
            }
        }
    }

    private class EndLessImaginator extends SingleImaginator {

        public EndLessImaginator(IViewUnit viewUnit, ICoordinates position, ProjectionD prD_) {
            super(viewUnit, position, prD_);
        }

        public IMotion CreateMotionImageGenerator(Object[] arg_) {
            return new MotionProjectEndLess(this);
        }
    }

    private class AmbientImaginator extends Imaginator {

        public AmbientImaginator(IViewUnit viewUnit, ICoordinates position, ProjectionD prD_) {
            super(viewUnit.getIndex(), position, prD_);
        }

        public IMotion CreateMotionImageGenerator(Object[] arg_) {
            return new MotionProjectEndLess(this);
        }

        public ICoordinates CreatePosition() {
            Coordinates2d coor_ = new Coordinates2d();
            coor_.x = (0);
            coor_.y = (JParametr.h * JParametr.grad - JParametr.grad);
            return coor_;
        }

        public void Project() {
            PutImage(0);
        }

        public void Project(int n) {
        }

        public void Translate() {
        }
    }
}

class AmbientImageJumper extends AmbientImage {

    JumperProjection3d jp3d;

    public AmbientImageJumper(ISpace space_, JumperProjection3d jp3d_) {
        super(space_);
        jp3d = jp3d_;
    }

    public void PutImage(ICoordinates coor) {
        jp3d.l1.Move();
        jp3d.l2.Move();
        jp3d.l3.Move();
        ((Anime) jp3d.l4.currentImage).Move();
        //((Anime) (jp3d.jumper).currentImage).Move();
    }
}

class SingleImaginator extends Imaginator {


    public SingleImaginator(IViewUnit viewUnit, ICoordinates position, ProjectionD prD_) {
            super(viewUnit.getIndex(), position, prD_);
        }

    public SingleImaginator(ICoordinates position, ProjectionD prD_) {
        super(0,position, prD_);
    }

    public void Project() {
        PutImage(0);
    }

    public void Translate() {
        ((Coordinates3d) position).xv = ((Coordinates2d) OriginalPosition).x;
        ((Coordinates3d) position).yv = ((Coordinates2d) OriginalPosition).y;
    }

    public ICoordinates CreatePosition() {
        return new Coordinates3d();
    }

    public void Project(int n) {
        PutImage(n);
    }

    public void PutImage(int n) {
        ((Image3D) currentImage).setRenderingEnable(false);
        super.PutImage(n);
        ((Image3D) currentImage).setRenderingEnable(true);
    }
//
//        public void SetImage(Image im_, int n) {
//            super.SetImage(im_, n);
//            ((Space3D.Image3D) currentImage).setRenderingEnable(true);
//        }
}

class AmbientImageProject extends Imaginator {

    public AmbientImageProject(ICoordinates position, ProjectionD prD_) {
        super(0, position, prD_);
    }

    @Override
    public IMotion CreateMotionImageGenerator(states states) {
        return new MotionProjectEndLess(this);
    }

    public ICoordinates CreatePosition() {
        return null;
    }

    public void Project() {
        PutImage(0);
    }

    public void Project(int n) {
    }

    public void Translate() {
    }
}
