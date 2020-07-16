/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter.Graphics.Space.G2D;

import Presenter.Graphics.Space.G2D.Images.Sprite2D_;
import Presenter.Graphics.Space.G2D.Images.AnimeImage2D;
import Logic.Reflections.Space.ISpace;
import Model.Game.Presenter.Space.LogCanvas;
import Model.Game.Presenter.Space.Space;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Adm
 */
public abstract class Space2D extends Space {

    final int layersCount;
    public Graphics2D[] bufferGraphics;
    public BufferedImage[] im;
    //Graphics2D bufferGraphicstmp;
    //java.awt.Image imtmp;
    int width;
    public int height;
    public int a;
    public int b;
    public int grad;
    //  public Graphics2D imageG;
    String spaceName;
    //long timeMod = System.currentTimeMillis();
    long timePreRender;
    // ImageFilter filter = new WhiteFilter();
    int yLoger;
    //  BufferedImage transparentImg;
    public boolean DrawlogCanvas = true;
    //Graphics2D targetG;

    public Space2D(int layersCount_, int width_, int height_, int grad_, int quant_, String spaceName_, int yLoger_) {
        super(quant_);


        layersCount = layersCount_;
        //targetG=targetG_;
        yLoger = yLoger_;
        //  imageG = imageG_;
        grad = grad_;

        a = grad / 2;
        b = grad / 2;
        width = width_;
        height = height_;
        //    transparentImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//        bufferGraphics = new Graphics2D[layersCount];
//        im = new BufferedImage[layersCount];
//        for (int i = 0; i < layersCount; i++) {
//
//            im[i] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);// CreateImage(width, height);
//            bufferGraphics[i] = (Graphics2D) im[i].getGraphics();
//            bufferGraphics[i].setBackground(new Color(0, true));
//
//        }
//        bufferGraphics[0].setBackground(Color.white);

        int[] arg_ = {width, height};
        painter = new Painter2D("Space2D", layersCount_, grad, arg_);

        logCanvas = new LogCanvas2D();
        //  imtmp = form.createImage(width + grad, height + grad);
        // bufferGraphicstmp = (Graphics2D) imtmp.getGraphics();
        //  bufferGraphicstmp.setBackground(Color.white);

        //

        // bufferGraphics[layersCount - 1].setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 0.5f));
        //      bufferGraphics[2].setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));


        spaceName = spaceName_;
    }

    //public abstract int GetViewOffset();
//    public void Draw(Object tool) {
//
//        Graphics2D g = (Graphics2D) tool;
//        //timeMod = System.currentTimeMillis() - timeMod;
//
//
//        //   imageG.clearRect(0, 0, width, height);
//
//
//        timePreRender = System.currentTimeMillis();
//        int grad_ = grad;
//
//
//        //    bufferGraphics[2].setColor(Color.gray);
//        //    bufferGraphics[2].fillRect((int) pri.viewOffsetX + width / 2, -(int) pri.viewOffsetY + grad_, width / 2, height);
//
//        //Composite oldcomp = bufferGraphics[0].getComposite();
//        // bufferGraphics[0].setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f));
//
//
//        //bufferGraphics[0].drawImage(im[1], 0, 0, form);
//
//        for (int i = 1; i < layersCount; i++) {
//            //  bufferGraphics[i-1].drawImage(Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(im[i].getSource(), filter)), 0, 0, null);
//            //     bufferGraphics[i-1].setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
//            bufferGraphics[0].drawImage(im[i], -(int) pri.viewOffsetX, (int) pri.viewOffsetY, null);
//        }
//
//        for (int i = 1; i < layersCount; i++) {
//            bufferGraphics[i].clearRect(0, 0, width, height);
//
//            //   im[i] =new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//            //    bufferGraphics[i] = (Graphics2D) im[i].getGraphics();
//            //   im[i].setData(transparentImg.getData());
//        }
//        //bufferGraphics[0].setComposite(oldcomp);
//
//        if (DrawlogCanvas){
//            bufferGraphics[0].drawImage(logCanvas.im, 0, 0, null);
//        }
//
//        //g.drawImage(im[0], 0, 0, null);
//
//        painter.Draw(g);
//
//
////        double a = ((Coordinates2d) coor[1]).xv * Math.PI / 180;
//////        bufferGraphics[0].drawOval((int) (coor[0].xv * grad),
//////                Tr((int) (coor[0].yv * grad)), 2, 2);
////        bufferGraphics[0].drawLine(
////                (int) (coor[0].xv * grad),
////                Tr((int) (coor[0].yv * grad)),
////                (int) (coor[0].xv * grad + 10 * Math.cos(a)),
////                Tr((int) (coor[0].yv * grad + 10 * Math.sin(a))));
////        bufferGraphics[0].drawString(String.valueOf(((Coordinates2d) coor[1]).xv), 0, 20 + grad);
//
//
//        timePreRender = System.currentTimeMillis() - timePreRender;
//
//
//
//
//
//        // java.awt.Image imTest = spaceForm.form.createImage(width, height);
//        // imTest.getGraphics().drawString(String.valueOf(spaceForm.layer), 10, 10);
//    }
    static public AnimeImage2D newAnimeImage(ISpace space, Sprite2D_ image_, int layer_, int time_) {
        return new AnimeImage2D(image_, layer_, time_, space);
    }

    static public Sprite2D_ newSprite(ISpace space, String st_, int w_, int h_, int grad_, int count_, int delay_) {
        return new Sprite2D_(st_, w_, h_, grad_, count_, delay_, space);
    }

//    public Image newBallsConf(int i, Coordinates2d coor_, double[] kjs, int[] cjs, Coordinates2d[] pj, Coordinates2d[] cpj) {
//        return new BallsConf(i, coor_, kjs, cjs, pj, cpj, this);
//    }
//
//    public Image newBlank() {
//        return new BlankImage2D(this);
//    }
    // public abstract java.awt.Image CreateImage(int width_, int height_);
//    private void ShowLog(Graphics2D g_) {
//
//        bufferGraphics[0].setColor(Color.white);
//        bufferGraphics[0].fillRect(0, 10 + yLoger, 80, 20);
//        g_.setColor(Color.red);
//        g_.drawString(spaceName, 0, 10 + yLoger);
//        //g_.drawString("Mod " + String.valueOf(timeMod), 0, 20 + yLoger);
//        g_.drawString("Art " + String.valueOf(timePreRender), 0, 30 + yLoger);
//
//    }
//    public void ClearPainterQueue() {
//        painter.ClearQueus();
//    }
//    private java.awt.Image Maze(BufferedImage img) {
//        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
//        Raster data1 = img.getData();
//        WritableRaster data2 = (WritableRaster) res.getData();
//        int[] iArray = new int[4];
//        int x=0;
//        int y=320;
//        for (int j = 0; j < data1.getHeight(); j++) {
//            for (int i = 0; i < data2.getWidth(); i++) {
//                data1.getPixel(i, j, iArray);
//
//                int i2=i;
//
//                int j2=j;
//                if (Math.sqrt(Math.pow(i2-x,2)+Math.pow(j2-y,2))<50){
//                    i2=j;
//                    j2=i;
//                }
//               
//                data2.setPixel(i2, j2, iArray);
//            }
//        }
//
//        res.setData(data2);
//        return res;
//    }
//    private java.awt.Image Maze(BufferedImage img) {
//        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
//        Graphics2D g2 = (Graphics2D) res.getGraphics();
//
//        AffineTransform affineTransform = new AffineTransform();
//
//      //  affineTransform.rotate(Math.toRadians(15));
//       // affineTransform.translate(0, -img.getHeight() / 4);
//       // affineTransform.scale(0.8, 0.8);
//        g2.drawImage(img, affineTransform, null);
//        return res;
//    }
    public class LogCanvas2D extends LogCanvas {

        BufferedImage im;
        //  private final BufferedImage transparentImg;
        Graphics2D gr;
        Graphics2D grLine;

        public LogCanvas2D() {
            im = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            gr = im.createGraphics();
            grLine = im.createGraphics();
            gr.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 0.5f));
            gr.setBackground(new Color(0, true));
            // transparentImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        }

        public void drawLine(int x1, int y1, int x2, int y2, int r, int g, int b) {
            grLine.setColor(new Color(r, g, b));
            grLine.drawLine(x1, y1, x2, y2);
        }

        public void Refresh() {
            // im.setData(transparentImg.getData());
            gr.clearRect(0, 0, width, height);
        }
    }

    public static int getRGB(Sprite2D_ map_, int i, int x, int y) {
        return map_.images[i].getRGB(x, y);
    }

    public static int getRGB(BufferedImage img_, int i, int x, int y) {
        return img_.getRGB(x, y);
    }
}
//class BallsConf extends ImageL implements Anime {
//
//    Coordinates2d lastCoor;// = new Coordinates2d();
//    public Ball lastBll;
//
//    public BallsConf(int layer_, Coordinates2d lastCoor_, double[] k_, int[] cjs_, Coordinates2d[] position_, Coordinates2d[] currentPosition_, Space space_) {
//        super(layer_, space_);
//        lastCoor = lastCoor_;
//        for (int i = 0; i < k_.length; i++) {
//            AddUnit(new Ball(k_[i], new Color(cjs_[i], 0, 0), position_[i], currentPosition_[i]));
//        }
//
//    }
//
//    public void AddUnit(Ball bll_) {
//
//        if (lastBll == null) {
//            lastBll = bll_;
//            lastBll.nextBll = lastBll;
//        } else {
//            bll_.nextBll = lastBll.nextBll;
//            lastBll.nextBll = bll_;
//            lastBll = bll_;
//        }
//        lastBll = bll_;
//
//    }
//
//    @Override
//    public void PutImage(Coordinates coor) {
//        double dx = ((Coordinates2d) coor).x - lastCoor.x;
//        double dy = ((Coordinates2d) coor).y - lastCoor.y;
//        Ball c_ = lastBll;
//        do {
//            c_.position.xd=(c_.position.xd + dx);
//            c_.position.yd=(c_.position.yd + dy);
//
//            c_ = c_.nextBll;
//        } while (c_ != lastBll);
//
//        lastCoor.x=(((Coordinates2d) coor).x);
//        lastCoor.y=(((Coordinates2d) coor).y);
//
//        Move();
//    }
//
//    public void Move() {
//        double dx;
//        double dy;
//        Ball c_ = lastBll;
//        Graphics2D[] bufferGraphics = ((Space2D) space).bufferGraphics;
//        do {
//            dx = c_.position.xd - c_.currentPosition.xd;
//            dy = c_.position.yd - c_.currentPosition.yd;
//            c_.currentPosition.xd=(c_.currentPosition.xd + c_.k * dx);
//            c_.currentPosition.yd=(c_.currentPosition.yd + c_.k * dy);
//
//
//            bufferGraphics[layer].setColor(c_.color);
//            bufferGraphics[layer].drawOval((int) c_.currentPosition.xd, Tr((int) c_.currentPosition.yd), 2, 2);
//            c_ = c_.nextBll;
//        } while (c_ != lastBll);
//    }
//
//    public class Ball {
//
//        Coordinates2d position;// = new Coordinates2d();
//        Coordinates2d currentPosition;//= new Coordinates2d();
//        public Ball nextBll;
//        double k;
//        Color color;
//
//        public Ball(double k_, Color color_, Coordinates2d position_, Coordinates2d currentPosition_) {
//            position = position_;
//            currentPosition = currentPosition_;
//
//            k = k_;
//            color = color_;
//        }
//    }
//}
//
//class BlankImage2D extends ImageL {
//
//    public BlankImage2D(Space space_) {
//        super(0, space_);
//    }
//
//    public void PutImage(Coordinates coor) {
//    }
//}

