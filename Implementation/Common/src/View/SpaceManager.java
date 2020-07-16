/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Application.View.ISpaceManager;
import Platform.Util.Profiler.ClassAssociations;
import Render.View.IPicturesList;
import Logic.Reflections.Space.IPainter;
import Logic.Reflections.Space.ISpace;
import Presenter.Graphics.Space.G2D.Space2D;

public abstract class SpaceManager implements ISpaceManager {

    protected abstract void onRender(int layer);

    protected ISpace[] space = new ISpace[10];
    protected int width, heidht;

    public SpaceManager(int width, int heidht) {
        ClassAssociations.PrintAssociaties();

        this.width = width;
        this.heidht = heidht;
       // this.notificator = notificator;
    }

    public ISpace CreateSpace(int grad_, int quant_) {
        return null;
    }

    public ISpace SetSpace2D(int layersCount_, int grad_, int quant_, String spaceName_, int ylogger_, final int layer_, boolean simpleDraw) {
        if (simpleDraw) {
            space[layer_] = Create(layersCount_, width, heidht, grad_, quant_, spaceName_, ylogger_, layer_);
        } else {
            space[layer_] = CreateShared(this, layersCount_, width, heidht, grad_, quant_, spaceName_, ylogger_, layer_);
        }
        return space[layer_];
    }

    public ISpace SetSpace3D(int grad_, int quant_) {
        return CreateSpace(grad_, quant_);
    }

/*    public void draw(Object tool) {
        ISpace space_ = space[0];
        //space_.Draw(tool);//tool<-Space.im[0]<-Space.im[1]<-...
        space_.getPainter().Draw(tool);
        //targetG.drawImage(vk.vpImg, 0, 0, null);
    }*/

    public IPicturesList getPictureList(int layer) {
        PicturesList picturesList = new PicturesList();
        IPainter painter_ = space[layer].getPainter();
        synchronized (painter_) {
            for (int i = 0; i < painter_.getPictureList().length; i++) {
                picturesList.addAll(painter_.getPictureList()[i]);
                //System.out.println("painter "+layer+" pictureQueue["+i+"].clear()");
                painter_.getPictureList()[i].clear();
            }
        }
        return picturesList;
    }

    public ISpace Create(int layersCount_, int width, int height, int grad_, int quant_, String spaceName_, int ylogger_, final int layer_) {
        return new Space2DImpl(layersCount_, width, height, grad_, quant_, spaceName_, ylogger_, layer_);
    }

    public Space2D CreateShared(ISpaceManager canvas, int layersCount_, int width, int height, int grad_, int quant_, String spaceName_, int ylogger_, int layer_) {
        return new Space2DShared(layersCount_, width, height, grad_, quant_, spaceName_, ylogger_, layer_);
    }

    class Space2DImpl extends Space2D {

        private final int layer;

        public Space2DImpl(int layersCount_, int width, int height, int grad_, int quant_, String spaceName_, int ylogger_, int layer_) {
            super(layersCount_, width, height, grad_, quant_, spaceName_, ylogger_);
            this.layer = layer_;
        }

        public void Render() {
            onRender(layer);
        }
    }

    class Space2DShared extends Space2D {

        public final int layer;

        public Space2DShared(int layersCount_, int width_, int height_, int grad_, int quant_, String spaceName_, int yLoger_, int layer_) {
            super(layersCount_, width_, height_, grad_, quant_, spaceName_, yLoger_);
            layer = layer_;
        }

        public void Render() {
            //Draw(tool); //tool<-Space.im[0]<-Space.im[1]<-...
            onRender(layer);
            //PostDraw();
        }
    }
//    public void ClearQeue(int layer) {
//         space[layer].ClearPainterQueue();
//
//    }
//    public Space3D CreateSpace3D(int grad_, int quant_, final int layer_, final OrderBarrier barrier_) {
//
//
//        //  setSize((TParametr.w + 1) * TParametr.grad, (TParametr.h + 1) * TParametr.grad);
//        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
//        canvas3D = new Canvas3D(config);
//        canvas3D.setSize(getWidth(), getHeight());
//
//
//        add(canvas3D);
//
//
////        canvas3D.addMouseListener(new java.awt.event.MouseAdapter() {
////
////            public void mouseClicked(java.awt.event.MouseEvent evt) {
////             //   if (UserSeeActualData()) {
////                    Point3d eye_pos = new Point3d();
////                    Point3d mousePosn = new Point3d();
////
////                    //get the eye point and mouse click point
////                    canvas3D.getCenterEyeInImagePlate(eye_pos);
////                    canvas3D.getPixelLocationInImagePlate(evt.getX(), evt.getY(),
////                            mousePosn);
////
////                    //Transform from ImagePlate coordinates to Vworld coordinates
////                    Transform3D motion = new Transform3D();
////                    canvas3D.getImagePlateToVworld(motion);
////
////                    motion.transform(eye_pos);
////                    motion.transform(mousePosn);
////
////                    //calculate the intersection point on Z=0
////                    double x = (-eye_pos.z / (mousePosn.z - eye_pos.z))
////                            * (mousePosn.x - eye_pos.x) + eye_pos.x;
////                    double y = (-eye_pos.z / (mousePosn.z - eye_pos.z))
////                            * (mousePosn.y - eye_pos.y) + eye_pos.y;
////
////                    int[] arg = new int[2];
////                    arg[0] = Math.round((float) x);
////                    arg[1] = Math.round((float) y);
////                    mod.KeyPressed(1, evt.getButton(), arg, itr.GetIteration());
////                }
////            //}
////        });
//
//
//        //  canvas3D.addMouseMotionListener(new MouseMotionAdapterImpl(10 * TParametr.grad, 10 * TParametr.grad));
//
//
//        //  listeners.add(canvas3D);
//
//        if (barrier_ != null) {
//            return new Space3D(canvas3D, quant_, grad_) {
//
//                int layer = layer_;
//                OrderBarrier barrier = barrier_;
//
//                public void Render() {
//                    barrier.Wait(layer);
//                    if (barrier.Next()) {
//                        super.Update();
//                        super.Render();
//                        barrier.End();
//                    }
//                }
//            };
//        } else {
//            return new Space3D(canvas3D, quant_, grad_);
//        }
//
//
//
//
//    }
//    public void Paint() {
//        target.getGraphics().drawImage(im, 0, 0, null);
//        getGraphics().drawImage(target, 0, 0, GetPanel());
//    }
//    public void Paint() {
//
//
//
//        drawSys.AddMotions(new Motion(1, "draw") {
//
//            @Override
//            public void MotionMethod() {
//
//
//
//            }
//        });
//        drawSys.AddMotions(new Motion(1, "repeat") {
//
//            @Override
//            public void MotionMethod() {
//
//            }
//        });
//        drawSys.Resume();
//    }
}
