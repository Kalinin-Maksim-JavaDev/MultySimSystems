/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter.Graphics.Space.G3D;

/**
 *
 * @author kalinin
 */
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfigTemplate;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.net.URL;
import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.AudioDevice;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.GraphicsContext3D;
import javax.media.j3d.Group;
import javax.media.j3d.Light;
import javax.media.j3d.Locale;
import javax.media.j3d.Material;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.PointArray;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.media.j3d.VirtualUniverse;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import com.sun.j3d.audioengines.javasound.JavaSoundMixer;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;

/**
 * This example mixes rendering in immediate and retained (scenegraph) mode to
 * produce a composite rendered frame.
 */
public class MixedTest extends Java3dApplet {

    private static int m_kWidth = 400;
    private static int m_kHeight = 400;

    public MixedTest() {
        initJava3d();
    }

    protected Canvas3D createCanvas3D() {
        // overidden this method to create a custom
        // Canvas3D that will implement the Immediate Mode rendering
        GraphicsConfigTemplate3D gc3D = new GraphicsConfigTemplate3D();
        gc3D.setSceneAntialiasing(GraphicsConfigTemplate.PREFERRED);
        GraphicsDevice gd[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        ImmediateCanvas3D c3d = new ImmediateCanvas3D(gd[0].getBestConfiguration(gc3D));
        c3d.setSize(getCanvas3dWidth(c3d), getCanvas3dHeight(c3d));
        return (Canvas3D) c3d;
    }

    protected BranchGroup createSceneBranchGroup() {
        BranchGroup objRoot = super.createSceneBranchGroup();
        TransformGroup objTrans = new TransformGroup();
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
                100.0);
        Transform3D yAxis = new Transform3D();
        Alpha rotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE, 0, 0,
                4000, 0, 0, 0, 0, 0);
        RotationInterpolator rotator = new RotationInterpolator(rotationAlpha,
                objTrans, yAxis, 0.0f, (float) Math.PI * 2.0f);
        rotator.setSchedulingBounds(bounds);
        objTrans.addChild(rotator);
        objTrans.addChild(new ColorCube());
        objRoot.addChild(objTrans);
        return objRoot;
    }

    public static void main(String[] args) {
        MixedTest mixedTest = new MixedTest();
        mixedTest.saveCommandLineArguments(args);
        new MainFrame(mixedTest, m_kWidth, m_kHeight);
    }
}

class ImmediateCanvas3D extends Canvas3D {

    //private long m_nRender = 0;
   // private long m_StartTime = 0;
    private static final int nGridSize = 50;
    private static final int m_kReportInterval = 50;
    private PointArray m_PointArray = new PointArray(nGridSize * nGridSize,
            GeometryArray.COORDINATES);
    private Transform3D m_t3d = new Transform3D();
    private float m_rot = 0.0f;

    ImmediateCanvas3D(java.awt.GraphicsConfiguration graphicsConfiguration) {
        super(graphicsConfiguration);
        // create the PointArray that we will be rendering
        int nPoint = 0;
        for (int n = 0; n < nGridSize; n++) {
            for (int i = 0; i < nGridSize; i++) {
                Point3f point = new Point3f(n - nGridSize / 2, i - nGridSize
                        / 2, 0.0f);
                m_PointArray.setCoordinate(nPoint++, point);
            }
        }
    }

    public void renderField(int fieldDesc) {
        super.renderField(fieldDesc);
        GraphicsContext3D g = getGraphicsContext3D();
        // first time initialization
   //     if (m_nRender == 0) {
            // set the start time
     //       m_StartTime = System.currentTimeMillis();
            // add a light to the graphics context
            DirectionalLight light = new DirectionalLight();
            light.setEnable(true);
            g.addLight((Light) light);
            // create the material for the points
            Appearance a = new Appearance();
            Material mat = new Material();
            mat.setLightingEnable(true);
            mat.setAmbientColor(0.5f, 1.0f, 1.0f);
            a.setMaterial(mat);
            a.setColoringAttributes(new ColoringAttributes(1.0f, 0.5f, 0.5f,
                    ColoringAttributes.NICEST));
            // enlarge the points
            a.setPointAttributes(new PointAttributes(4, true));
            // make the appearance current in the graphics context
            g.setAppearance(a);
   //     }
        // set the current transformation for the graphics context
        g.setModelTransform(m_t3d);
        // finally render the PointArray
        g.draw(m_PointArray);
        // calculate and display the Frames Per Second for the
        // Immediate Mode rendering of the PointArray
//        m_nRender++;
//        if ((m_nRender % m_kReportInterval) == 0) {
//            float fps = (float) 1000.0f
//                    / ((System.currentTimeMillis() - m_StartTime) / (float) m_kReportInterval);
//            System.out.println("FPS:\t" + fps);
//            m_StartTime = System.currentTimeMillis();
//        }
    }

    public void preRender() {
        super.preRender();
        // update the model transformation to rotate the PointArray
        // about the Y axis.
        m_rot += 0.1;
        m_t3d.rotY(m_rot);
        // move the transform back so we can see the points
        m_t3d.setTranslation(new Vector3d(0.0, 0.0, -20.0));
        // scale the transformation down so we can see all of the points
        m_t3d.setScale(0.3);
        // force a paint (will call renderField)
        paint(getGraphics());
    }
}
