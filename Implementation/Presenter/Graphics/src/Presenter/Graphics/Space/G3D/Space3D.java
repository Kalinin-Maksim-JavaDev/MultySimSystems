/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter.Graphics.Space.G3D;

import Model.Game.Presenter.Space.Anime;
import Model.Game.Presenter.Space.Image;
import Model.Game.Presenter.Space.Space;
import Model.Geometry.D2.Coordinates2d;
import Model.Models.Unit.D3.Coordinates3d;
import Render.Graphics.Geometry.ICoordinates;
import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.behaviors.mouse.MouseBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

/**
 *
 * @author Adm
 */
public class Space3D extends Space {

    int grad;
    Canvas3D canvas3d;
    SimpleUniverse universe;
    GraphicsContext3D gc;
    BranchGroup contents = new BranchGroup();
    Image3D[] images;
    //  BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
    int curnum = 0;
    ObjectFile f = new ObjectFile();
    DirectionalLight light1;
    final Shape3D ballMesh;
    final Shape3D cubeMesh;
    final Texture textball;
    //  final Texture2D textball;
    Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
    Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
    Color3f red = new Color3f(0.7f, .15f, .15f);
    AmbientLight lightA;
    final Shape3D planeMesh;
    public SpaceUpDateBridge spaceUpDateBridge;

    public Space3D(Canvas3D canvas_, int quant_, int grad_) {
        super(quant_);
        grad = grad_;
        this.canvas3d = canvas3d;
        canvas3d.stopRenderer();
        universe = new SimpleUniverse(canvas3d);

        images = new Image3D[1000];
        gc = canvas3d.getGraphicsContext3D();
        gc.setAppearance(new Appearance());

        //  
        TransformGroup viewTransformGroup = universe.getViewingPlatform().getViewPlatformTransform();
        universe.getViewingPlatform().setNominalViewingTransform();


        //  contents.addChild(light1);
        BoundingSphere mouseBouznds = new BoundingSphere(new Point3d(), 1000.0);
        BoundingSphere nds = new BoundingSphere();
        lightA = new AmbientLight();
        lightA.setInfluencingBounds(new BoundingSphere());
        // contents.addChild(lightA);
//        KeyNavigatorBehavior keyInteractor = new KeyNavigatorBehavior(viewTransformGroup);
//        keyInteractor.setSchedulingBounds(movingBounds);
//        contents.addChild(keyInteractor);

        MouseRotate myMouseRotate = new MouseRotate(MouseBehavior.INVERT_INPUT);
        myMouseRotate.setTransformGroup(viewTransformGroup);
        myMouseRotate.setSchedulingBounds(mouseBouznds);
        contents.addChild(myMouseRotate);

        MouseTranslate myMouseTranslate = new MouseTranslate(MouseBehavior.INVERT_INPUT);
        myMouseTranslate.setTransformGroup(viewTransformGroup);
        myMouseTranslate.setSchedulingBounds(mouseBouznds);
        contents.addChild(myMouseTranslate);

        MouseZoom myMouseZoom = new MouseZoom(MouseBehavior.INVERT_INPUT);
        myMouseZoom.setTransformGroup(viewTransformGroup);
        myMouseZoom.setSchedulingBounds(mouseBouznds);
        contents.addChild(myMouseZoom);

//        Color3f light1Color = new Color3f(1.0f, 1.0f, 1.0f);
//        Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
//        light1 = new DirectionalLight(light1Color, light1Direction);
        //  light1.setInfluencingBounds(bounds);
        gc.addLight(lightA);


        Scene buffer = null;
        f.setFlags(ObjectFile.RESIZE | ObjectFile.TRIANGULATE | ObjectFile.STRIPIFY);
        try {
            buffer = f.load(Space3D.class.getResource("/Space/Sphere.obj").getPath());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Space3D.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IncorrectFormatException ex) {
            Logger.getLogger(Space3D.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParsingErrorException ex) {
            Logger.getLogger(Space3D.class.getName()).log(Level.SEVERE, null, ex);
        }
        ballMesh = (Shape3D) buffer.getSceneGroup().getChild(0);

        try {
            buffer = f.load(Space3D.class.getResource("/Space/Cube.obj").getPath());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Space3D.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IncorrectFormatException ex) {
            Logger.getLogger(Space3D.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParsingErrorException ex) {
            Logger.getLogger(Space3D.class.getName()).log(Level.SEVERE, null, ex);
        }
        cubeMesh = (Shape3D) buffer.getSceneGroup().getChild(0);

        try {
            buffer = f.load(Space3D.class.getResource("/Space/Plane.obj").getPath());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Space3D.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IncorrectFormatException ex) {
            Logger.getLogger(Space3D.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParsingErrorException ex) {
            Logger.getLogger(Space3D.class.getName()).log(Level.SEVERE, null, ex);
        }
        planeMesh = (Shape3D) buffer.getSceneGroup().getChild(0);

        TextureLoader loader = new TextureLoader(Space3D.class.getResource("/Space/ball.png").getPath(), "RGB", null);
        textball = loader.getTexture();
        // textball.setBoundaryModeS(Texture.WRAP);
        //  textball.setBoundaryModeT(Texture.WRAP);
        //  textball.setBoundaryColor(new Color4f(0.0f, 1.0f, 0.0f, 0.0f));


    }

    public void Update() {
        spaceUpDateBridge.UpDate();
    }

    public void Render() {


        Coordinates3d[] coor = (Coordinates3d[]) ((CommonProjectionInfo3d) pri).viewVector;


        if ((coor[1].xv != 90) && (coor[1].xv != -90)) {
            double a = coor[1].xv * Math.PI / 180;
            Point3d lat = new Point3d(coor[0].xv + 0.5 * Math.cos(a), coor[0].yv + 0.5 * Math.sin(a), 0);
//            Transform3D lookAt = new Transform3D();
//            lookAt.lookAt(new Point3d(coor[0].xv, coor[0].yv, 0), lat, new Vector3d(0.0, 1.0, 0.0));
//            lookAt.invert();
//            universe.getViewingPlatform().getViewPlatformTransform().setTransform(lookAt);

//            light1.setBounds(new BoundingSphere(lat, 0));
//            light1.setDirection((float) Math.cos(a), (float) Math.sin(a), 0f);
        }



        // gc.setModelTransform(lookAt);

        gc.clear();

        //  gc.addLight(light1);

        for (int i = 0; i < curnum; i++) {

            gc.setModelTransform(images[i].trf);
            if (images[i].renderit) {
                gc.draw(images[i].shape);
            }
        }

        canvas3d.swap();
    }

    public void Complete() {
        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(contents);
    }

    public Sprite3D_ newSprite(String st_, int w_, int h_, int count_, int delay_) {
        return new Sprite3D_(st_, w_, h_, count_, delay_, this);
    }

    public Image newBallsConf(int i, Coordinates2d coor_, double[] kjs, int[] cjs, Coordinates2d[] pj, Coordinates2d[] cpj) {
        return new BallsConf3D(i, coor_, kjs, cjs, pj, cpj, this);
    }
}

class BallsConf3D extends Image3D implements Anime {

    Coordinates2d lastCoor = new Coordinates2d();
    public Ball lastBll;

    public BallsConf3D(int layer_, Coordinates2d lastCoor_, double[] k_, int[] cjs_, Coordinates2d[] position_, Coordinates2d[] currentPosition_, Space3D space3D_) {
        super(space3D_);
        lastCoor.setX(20);
        lastCoor.setY(10);
        for (int i = 0; i < k_.length; i++) {
            AddUnit(new Ball(k_[i], position_[i], currentPosition_[i]));
        }

    }

    public void AddUnit(Ball bll_) {

        if (lastBll == null) {
            lastBll = bll_;
            lastBll.nextBll = lastBll;
        } else {
            bll_.nextBll = lastBll.nextBll;
            lastBll.nextBll = bll_;
            lastBll = bll_;
        }
        lastBll = bll_;

    }

    public void PutImage(ICoordinates coor) {
        double dx = ((Coordinates3d) coor).x - lastCoor.getX();
        double dy = ((Coordinates3d) coor).y - lastCoor.getY();
        Ball c_ = lastBll;
        do {
            c_.position.setXd(c_.position.getXd() + dx);
            c_.position.setYd(c_.position.getYd() + dy);

            c_ = c_.nextBll;
        } while (c_ != lastBll);

        lastCoor.setX(((Coordinates3d) coor).x);
        lastCoor.setY(((Coordinates3d) coor).y);

    }

    public void Move() {
        double dx;
        double dy;
        Ball c_ = lastBll;
        do {
            dx = c_.position.getXd() - c_.currentPosition.getXd();
            dy = c_.position.getYd() - c_.currentPosition.getYd();
            c_.currentPosition.setXd(c_.currentPosition.getXd() + c_.k * dx);
            c_.currentPosition.setYd(c_.currentPosition.getYd() + c_.k * dy);


            c_ = c_.nextBll;
        } while (c_ != lastBll);
    }

    public class Ball {

        Coordinates2d position;// = new Coordinates2d();
        Coordinates2d currentPosition;//= new Coordinates2d();
        public Ball nextBll;
        double k;

        public Ball(double k_, Coordinates2d position_, Coordinates2d currentPosition_) {
            position = position_;
            currentPosition = currentPosition_;

            k = k_;


        }
    }
}
