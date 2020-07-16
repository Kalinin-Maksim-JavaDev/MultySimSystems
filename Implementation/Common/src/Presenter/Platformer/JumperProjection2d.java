package Presenter.Platformer;

import Logic.Reflections.Projections.IImaginator;
import View.Presenter.Projections.Imaginator;
import Model.Game.Presenter.Projections.ArgumentInt;
import Platform.Core.Motion.Motion;
import Platform.Core.IMotion;
import View.Presenter.Projections.ProjectionD;
import Model.Game.Presenter.Space.AmbientImage;
import Model.Game.Presenter.Space.CommonProjectionInfo;
import Platform.Util.Area.PolygonSimple;
import Presenter.Platformer.JumperProjection2d.SingleImaginatorNexus;
import Model.Geometry.D2.Coordinates2d;
import Presenter.Graphics.Space.G2D.Images.GradientArea;
import Presenter.Graphics.Space.G2D.Phys2DShow;
import Presenter.Graphics.Space.G2D.Space2D;
import Presenter.Graphics.Space.G2D.Images.Sprite2D_;
import Platform.Util.Area.Area;
import Platform.Util.Random;
import Logic.Reflections.Space.IImage;
import Logic.Reflections.Space.ISpace;
import View.Presenter.Projections.MotionProjectEndLess;
import Platform.Core.ISystemM;
import Render.Graphics.Geometry.ICoordinates;
import Render.Graphics.Space.G2D.IPhys2DShow;
import Render.View.IImaginatorFactory;
import Presenter.Projections.JumperImaginatorsFactorys;
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
public class JumperProjection2d extends ProjectionD {

    public static Random random = new Random();
    public ICoordinates jumperPosition;
    //  public double viewOffsetX = 0;
    // public double viewOffsetY = 0;
    private Images images;
    //final Sprite2D_ itemsmenu_;
    GradientArea fundGradient;
    Phys2DShow[][] phys2D;
    //int currendDemo;
    private final byte Jumperh = 5;
    private final byte Jumperf = 1;
    private final byte Jumperj = 2;
    private final byte Jumperb = 3;
    private final byte chainBeg = 4;
    private final byte chainEnd = 6;
    Phys2DShow fonWorld;

    public String SetThreadName() {
        return "GameDIS";
    }

    public JumperProjection2d(ISpace space_, byte[][] decorations) {
        super(space_);
        phys2D = new Phys2DShow[1][1];
        for (int j = 0; j < phys2D.length; j++) {
            for (int i = 0; i < phys2D[j].length; i++) {
                phys2D[j][i] = new Phys2DShow(3, (Space2D) getSpace(), 10);
            }
        }

        ((ISystemM) art).getTaskMotions().setName("Render game");
        space_.setPri(new CommonProjectionInfo() {

            public void CollectcommonProjectionInfo() {
                double w2 = JParametr.w * JParametr.grad / 4;
                double h2 = JParametr.h * JParametr.grad / 2;

                double xv = ((Coordinates2d) jumperPosition).x;
                double deltx = xv - w2 - viewOffsetX;
                viewOffsetX += deltx * 0.05;
                if (viewOffsetX < 0) {
                    viewOffsetX = 0;
                }
                if (viewOffsetX >= 2 * w2) {
                    viewOffsetX = (int) 2 * w2;
                }



//                double yv = ((Coordinates2d) jumper.position).y;
//                double delty = yv - h2 - viewOffsetY;
//                viewOffsetY += delty * 0.05;
//                if (viewOffsetY < 0) {
//                    viewOffsetY = 0;
//                }
//                if (viewOffsetY >= h2/16) {
//                    viewOffsetY = (int) h2/16;
//                }

            }
        });
        //  Space2D space = (Space2D) art.space;




        images = new Images(JParametr.grad);

//        itemsmenu_ = Space2D.newSprite(getSpace(),"Platformer/Images/itemsmenu.png", 240 * 2, 320, 0, 3, 2000);
        fundGradient = new GradientArea(1, getSpace(), 0, 255, 0, 0, 0, 255);
        //  Say("set units");

//        currendDemo = 0;
        Coordinates2d begChain = new Coordinates2d();
        Coordinates2d endChain = new Coordinates2d();

        Area area = new Area();
        // area.RunFrame();
        for (int j = 0; j < decorations.length; j++) {
            for (int i = 0; i < decorations[j].length; i++) {
                {
                    if (decorations[j][i] == Jumperf) {
                        fundGradient.AddShape((int) (i * JParametr.grad), (int) (j * JParametr.grad), JParametr.grad, JParametr.grad);

                        if ((decorations[j + 1][i] != Jumperf)) {
                            GetWorld(i, j).AddBox(i, j);
                            if ((random.nextDouble() < 0.75)) {
                                GetWorld(i, j).AddDust(i, j);
                            }
                        }

                        PolygonSimple p_ = new PolygonSimple(false);
                        int x_ = (int) phys2D[0][0].GetX(i);
                        int y_ = (int) phys2D[0][0].GetY(j - 1);
                        int x2_ = (int) phys2D[0][0].GetX(i + 1);
                        int y2_ = (int) phys2D[0][0].GetY(j);
                        p_.addPoint(x_, y_);
                        p_.addPoint(x2_, y_);
                        p_.addPoint(x2_, y2_);
                        p_.addPoint(x_, y2_);
                        area.Join(p_);

                    } else if (decorations[j][i] == Jumperh) {
                        if (j < JParametr.h - 1) {
                            if ((decorations[j + 1][i] != Jumperh)) {
                                GetWorld(i, j).AddBox(i, j);
                                GetWorld(i, j).AddDust(i, j);
                            }
                        }
                    } else if (decorations[j][i] == Jumperb) {
                    } else if (decorations[j][i] == chainBeg) {
                        begChain.x = (i);
                        begChain.y = (j);
                    } else if (decorations[j][i] == chainEnd) {
                        endChain.x = (i);
                        endChain.y = (j);
                        (GetWorld((begChain.x + endChain.x) / 2, (begChain.y + endChain.y) / 2)).AddChain(begChain, endChain);
                    }
//                    if (demo[currendDemo].bodyCount > 500) {
//                        currendDemo++;
//                    }
                }
            }
        }
        Area finalarea = area.Complite(3);
        //finalarea.RunFrame();
        finalarea.Glide();

        for (int i = 0; i < finalarea.size(); i++) {
            //    phys2D[0][0].AddPolygon(resArea.getPolygon(i));
            fundGradient.AddCanva(finalarea.getPolygon(i));
        }

        fonWorld = new Phys2DShow(0, (Space2D) getSpace(), 0);
        fonWorld.AddCloud(20, 15, 1);
        fonWorld.AddCloud(10, 15, 1);
        fonWorld.AddCloud(20, 5, 1);
        int n = 10; //fon = new Gorizont(0, getSpace());
        //fon.AddShape(0,320, 480,320);

    }

    @Override
    protected IImaginatorsFactorys createImaginatorsFactorys() {

        JumperImaginatorsFactorys result = new JumperImaginatorsFactorys();

        result.AnimatedFundamentFactory = new IImaginatorFactory() {

            public IImaginator.charged getImaginator(IViewUnit viewUnit, ICoordinates position) {
                return new EndLessImaginator(viewUnit, position, JumperProjection2d.this) {

                    @Override
                    public IMotion CreateMotionImageGenerator(Object[] arg_) {
                        return new Motion(1, "not") {

                            public void MotionMethod() {
                            }
                        };
                    }
                }.SetImage(Space2D.newAnimeImage(getSpace(), images.sprites.fund, 1, random.nextInt(images.sprites.fund.period)), 0).setName(" draw " + " Fund " + viewUnit);

            }
        };
        result.WallFactory = new IImaginatorFactory() {

            public IImaginator.charged getImaginator(IViewUnit viewUnit, ICoordinates position) {
                return new EndLessImaginator(viewUnit, position, JumperProjection2d.this).SetImage(Space2D.newAnimeImage(getSpace(), images.sprites.wall, 1, random.nextInt(images.sprites.wall.period)), 0).setName(" draw " + " Wall " + viewUnit);
            }
        };
        result.BallFactory = new IImaginatorFactory() {

            public IImaginator.charged getImaginator(IViewUnit viewUnit, ICoordinates position) {
                return new SingleImaginatorNexus(viewUnit, position, JumperProjection2d.this) {

                    float xw;
                    float yw;

                    public void PutImage(int n) {
                        super.PutImage(n);
                        int xw_ = (int) ((Coordinates2d) OriginalPosition).x;
                        int yw_ = (int) ((Coordinates2d) OriginalPosition).y;
                        if ((xw_ != xw) || (yw_ != yw)) {

                            float grad = JParametr.grad;
                            for (int j = 0; j < phys2D.length; j++) {
                                for (int i = 0; i < phys2D[j].length; i++) {
                                    phys2D[j][i].Wind((int) (xw_ / grad), (int) (yw_ / grad) - 1);
                                }
                            }
                        }
                        xw = xw_;
                        yw = yw_;
                    }
                }.SetImage(Space2D.newAnimeImage(getSpace(), images.sprites.ball, 1, random.nextInt(images.sprites.ball.period)), 0).SetImage(Space2D.newAnimeImage(getSpace(), images.sprites.ballselected, 1, random.nextInt(images.sprites.ballselected.period)), 1).SetImage(Space2D.newAnimeImage(getSpace(), images.sprites.ballfall, 1, random.nextInt(images.sprites.ballselected.period)), 2);

                //  GetWorld((int) ((Coordinates2d) position.position).xv,(int) ((Coordinates2d) position.position).yv).AddCircle((int) (((Coordinates2d) position.position).xv), (int) (((Coordinates2d) position.position).yv ));
//            if (demo[currendDemo].bodyCount > 500) {
//                currendDemo++;
//            }

            }
        };
        result.JumperFactory = new IImaginatorFactory() {

            public IImaginator.charged getImaginator(IViewUnit viewUnit, ICoordinates position) {

                jumperPosition = position;

                return new SingleImaginatorNexus(viewUnit, position, JumperProjection2d.this) {

                    public void Translate() {
                        super.Translate();
                    }
                    float xw;
                    float yw;

                    public void PutImage(int n) {
                        super.PutImage(n);
                        int xw_ = (int) ((Coordinates2d) OriginalPosition).x;
                        int yw_ = (int) ((Coordinates2d) OriginalPosition).y;
                        if ((xw_ != xw) || (yw_ != yw)) {

                            float grad = JParametr.grad;
                            for (int j = 0; j < phys2D.length; j++) {
                                for (int i = 0; i < phys2D[j].length; i++) {
                                    phys2D[j][i].Wind((int) (xw_ / grad), (int) (yw_ / grad) - 1);
                                }
                            }
                        }
                        xw = xw_;
                        yw = yw_;
                    }
                }.SetImage(Space2D.newAnimeImage(getSpace(), images.sprites.state, 1, 0), IViewUnit.states.Jumper_State.getId()).SetImage(Space2D.newAnimeImage(getSpace(), images.sprites.run, 1, 0), IViewUnit.states.Jumper_Run.getId()).SetImage(Space2D.newAnimeImage(getSpace(), images.sprites.jump, 1, 0), IViewUnit.states.Jumper_Jump.getId()).SetImage(Space2D.newAnimeImage(getSpace(), images.sprites.jump, 1, 0), IViewUnit.states.Jumper_Fall.getId()).setName(" draw " + " jump " + viewUnit);

            }
        };

        result.GranateFactory = new IImaginatorFactory() {

            public IImaginator.charged getImaginator(IViewUnit viewUnit, ICoordinates position) {
                return new SingleImaginatorNexus(viewUnit, position, JumperProjection2d.this).SetImage(Space2D.newAnimeImage(getSpace(), images.sprites.gran, 2, random.nextInt(images.sprites.gran.period)), 0).SetImage(Space2D.newAnimeImage(getSpace(), images.sprites.expl, 2, random.nextInt(images.sprites.expl.period)), 1).setName(" draw " + " jumper " + viewUnit);
            }
        };
        return result;
    }

    protected DecorationsListManager setDecorationsImaginators() {

        DecorationsListManager result = new DecorationsListManager();

       result.add(new Decoration(new AmbientImaginator(null, this).SetImage(new AmbientImage(((Space2D) getSpace())) {

            public void PutImage(ICoordinates coor) {
                images.fon.PutImage(coor);
                images.map.PutImage(coor);
                fundGradient.PutImage(coor);


                for (int j = 0; j < phys2D.length; j++) {
                    for (int i = 0; i < phys2D[j].length; i++) {
                        phys2D[j][i].Next();
                    }
                }
                fonWorld.Wind(20, 15);
                fonWorld.Next();

            }
        }, 0).setName(" draw " + " ambi ")));

        result.add(new Decoration(new EndLessImaginator(new Coordinates2d(), this) {

            public IMotion CreateMotionImageGenerator(final Object[] arg_) {
                if (arg_.length == 1) {
                    return super.CreateMotionImageGenerator(arg_);
                } else {

                    return new Motion(1, "chg target position") {

                        Object[] arg = arg_;

                        public void MotionMethod() {
                            ((Coordinates2d) OriginalPosition).x = (((ArgumentInt) arg_[0]).n);
                            ((Coordinates2d) OriginalPosition).y = (((ArgumentInt) arg_[1]).n);
                        }
                    };
                }
            }

            public void PutImage(int n) {
                currentImage.PutImage(getPosition());
            }
        }.SetImage(Space2D.newAnimeImage(getSpace(), images.sprites.trgt_, 2, 0), 0).setName(" draw " + " targ ")));
//Say("set ambinet");


        JParametr.EndSceneID = result.add(new Decoration(new EndLessImaginator(new Coordinates2d(), this) {

            public ICoordinates CreatePosition() {
                ((Coordinates2d) OriginalPosition).x = (0);
                ((Coordinates2d) OriginalPosition).y = (JParametr.h);
                return super.CreatePosition();

            }

            public void PutImage(int n) {
                super.PutImage(n);
            }
        }.SetImage(Space2D.newAnimeImage(getSpace(), images.sprites.end_, 3, 0), 0).setName(" draw " + " end ")));


        JParametr.WinSceneID = result.add(new Decoration(new EndLessImaginator(new Coordinates2d(), this) {

            public ICoordinates CreatePosition() {
                ((Coordinates2d) OriginalPosition).x = (0);
                ((Coordinates2d) OriginalPosition).y = (JParametr.h);
                return super.CreatePosition();

            }

            public void PutImage(int n) {
                super.PutImage(n);
            }
        }.SetImage(Space2D.newAnimeImage(getSpace(), images.sprites.win, 3, 0), 0).setName(" draw " + " win ")));

        final Sprite2D_ chgT = Space2D.newSprite(getSpace(), "/Platformer/Images/TurnsBoard.png", 60 * 2, 80, 0, 6, 500);
        result.add(new Decoration(new SingleImaginator(new Coordinates2d(), this) {

            public void Translate() {
                super.Translate();
                ((Coordinates2d) position).x = (((Coordinates2d) getPosition()).x + getSpace().getPri().getViewOffsetX());
                ((Coordinates2d) position).y = (((Coordinates2d) getPosition()).y + getSpace().getPri().getViewOffsetY());
            }

            public ICoordinates CreatePosition() {
                ((Coordinates2d) OriginalPosition).x = (JParametr.w / 8);
                ((Coordinates2d) OriginalPosition).y = (JParametr.h / 2);
                return super.CreatePosition();

            }

            public void PutImage(int n) {
                super.PutImage(n);
            }
        }.SetImage(Space2D.newAnimeImage(getSpace(), chgT, 3, 0), 0)));

        return result;
    }

    @Override
    public void onImaginatorCreation(IViewUnit unit, ICoordinates position, IImaginatorFactory imagefactory) {
    }

    final private Phys2DShow GetWorld(int x, int y) {
        int j = (phys2D.length - 1) * y / (JParametr.h - 1);
        int i = (phys2D[j].length - 1) * x / (JParametr.w - 1);

        // System.out.println(x+", "+res);
        return phys2D[j][i];
    }

    private IPhys2DShow GetWorld(double x, double y) {
        return GetWorld((int) x, (int) y);
    }

    private class SingleImaginator extends Imaginator {

        public SingleImaginator(ICoordinates position, ProjectionD prD_) {
            super(0, position, prD_);
        }

        public SingleImaginator(IViewUnit viewUnit, ICoordinates position, ProjectionD prD_) {
            super(viewUnit.getIndex(), position, prD_);
        }

        public void Project() {

            PutImage(0);
        }

        public void Translate() {
            ((Coordinates2d) position).x = ((int) (((Coordinates2d) OriginalPosition).x));
            ((Coordinates2d) position).y = ((int) (((Coordinates2d) OriginalPosition).y));
        }

        public ICoordinates CreatePosition() {
            return new Coordinates2d();
        }

        public void Project(int n) {
            PutImage(n);
        }
    }

    class SingleImaginatorNexus extends SingleImaginator {

        public SingleImaginatorNexus(IViewUnit viewUnit, ICoordinates position, ProjectionD prD_) {
            super(viewUnit, position, prD_);
        }

        public void Translate() {
            super.Translate();
            {
                int x = (int) (((Coordinates2d) getPosition()).x + JParametr.grad / 2);
                int y = (int) (((Coordinates2d) getPosition()).y - JParametr.grad);

                {
                    for (int i = JParametr.grad - 1; i > 0; i--) {
                        //int c_ = Space2D.getRGB(map_, 0, x, ((ImageL) this.currentImage).Tr(y + i));
                        int c1_ = 0;//Space2D.getRGB((phys2D[0][0].img[0], 0, x, ((ImageL) this.currentImage).Tr(y + i));
                        if (c1_ != 0) {
                            ((Coordinates2d) position).y = (((Coordinates2d) getPosition()).y + i);
                            break;
                        }

                    }
                }

            }
        }
    }

    private class EndLessImaginator extends SingleImaginator {

        public EndLessImaginator(ICoordinates position, ProjectionD prD_) {
            super(position, prD_);
        }

        public EndLessImaginator(IViewUnit viewUnit, ICoordinates position, ProjectionD prD_) {
            super(viewUnit, position, prD_);
        }

        public IMotion CreateMotionImageGenerator(Object[] arg_) {
            return new MotionProjectEndLess(this);
        }
    }

    private class AmbientImaginator extends Imaginator {

        public AmbientImaginator(ICoordinates position, ProjectionD prD_) {
            super(0, position, prD_);
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
            PutImage(n);
        }

        public void Translate() {
        }
    }

    private class Images {

        private IImage fon;
        private IImage map;
        private IImage turnsBoaurd;
        private Sprites sprites;

        public Images(int grad) {
            sprites = new Sprites(grad);
            fon = Space2D.newAnimeImage(getSpace(), sprites.fon, 0, 0);
            map = Space2D.newAnimeImage(getSpace(), sprites.map, 0, 0);

        }

        private class Sprites {

            private Sprite2D_ fund;
            private Sprite2D_ wall;
            private Sprite2D_ jump;
            private Sprite2D_ state;
            private Sprite2D_ run;
            private Sprite2D_ ball;
            private Sprite2D_ ballselected;
            private Sprite2D_ ballfall;
            private Sprite2D_ expl;
            private Sprite2D_ gran;
            private Sprite2D_ fon;
            private Sprite2D_ trgt_;
            private Sprite2D_ end_;
            private Sprite2D_ win;
            private Sprite2D_ map;

            public Sprites(int grad) {
                fund = Space2D.newSprite(getSpace(), "/Platformer/Images/fund.png", 40, 32, grad, 16, 800);
                wall = Space2D.newSprite(getSpace(), "/Platformer/Images/wall.png", 40, 32, grad, 8, 400);
                jump = Space2D.newSprite(getSpace(), "/Platformer/Images/jump.png", 40, 32, grad, 8, 400);
                state = Space2D.newSprite(getSpace(), "/Platformer/Images/state.png", 40, 32, grad, 8, 400);
                run = Space2D.newSprite(getSpace(), "/Platformer/Images/run.png", 40, 32, grad, 8, 400);
                ball = Space2D.newSprite(getSpace(), "/Platformer/Images/ball.png", 40, 32, grad, 16, 800);
                ballselected = Space2D.newSprite(getSpace(), "/Platformer/Images/ballsel.png", 40, 32, grad, 16, 800);
                ballfall = Space2D.newSprite(getSpace(), "/Platformer/Images/ballFall.png", 40, 32, grad, 16, 800);
                expl = Space2D.newSprite(getSpace(), "/Platformer/Images/expl.PNG", 40, 32, grad, 8, 400);
                gran = Space2D.newSprite(getSpace(), "/Platformer/Images/gran.png", 40, 32, grad, 8, 400);
                fon = Space2D.newSprite(getSpace(), "/Platformer/Images/fon.png", 240 * 2, 320, 0, 3, 2000);
                trgt_ = Space2D.newSprite(getSpace(), "/Platformer/Images/trgt.png", 40, 32, grad, 8, 400);
                end_ = Space2D.newSprite(getSpace(), "/Platformer/Images/end.png", 240 * 2, 320, 0, 3, 2000);
                win = Space2D.newSprite(getSpace(), "/Platformer/Images/win.png", 240 * 2, 320, 0, 3, 2000);
                map = Space2D.newSprite(getSpace(), "/Platformer/mapAdvance.png", 240 * 2, 320, 0, 1, 2000);
            }
        }
    }
}
