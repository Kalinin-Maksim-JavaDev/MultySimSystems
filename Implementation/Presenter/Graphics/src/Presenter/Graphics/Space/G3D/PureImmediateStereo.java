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
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.util.Map;
import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.GraphicsContext3D;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * Pure immediate mode stereo example program for stereo. In pure immediate
 * mode, the renderer must be stopped on the Canvas being rendered into. In our
 * example, this is done immediately after the canvas is created. A separate
 * thread is started up to do the immediate mode rendering.
 */
public class PureImmediateStereo extends Applet implements Runnable {
    // Set this to true when the graphics card use shared z buffer
    // in stereo mode.

    public static String defaultSharedStereoZbuffer = Boolean.TRUE.toString();
    private boolean sharedStereoZbuffer;
    private boolean stereoSupport;
    private Canvas3D canvas;
    private GraphicsContext3D gc;
    private Shape3D leftConeBody, rightConeBody;
    private Shape3D leftConeCap, rightConeCap;
    private Transform3D cmt = new Transform3D();
    private Vector3f leftTrans, rightTrans;
    // One rotation (2*PI radians) every 6 seconds
    private Alpha rotAlpha = new Alpha(-1, 6000);
    private SimpleUniverse u = null;
    private double angle;
    // Compute data which is common for both
    // left and right eye

    void computeSharedData() {
        // Compute angle of rotation based on alpha value
        angle = rotAlpha.value() * 2.0 * Math.PI;
        cmt.rotY(angle);
    }
    // Render the geometry in right eye

    void renderLeft() {
        cmt.setTranslation(leftTrans);
        gc.setModelTransform(cmt);
        if (sharedStereoZbuffer) {
            // Graphics card shared same z buffer in stereo mode,
            // in this case we have to explicitly clearing both
            // frame buffers.
            gc.clear();
        }
        gc.draw(leftConeBody);
        gc.draw(leftConeCap);
    }
    // Render the geometry for right eye

    void renderRight() {
        cmt.setTranslation(rightTrans);
        gc.setModelTransform(cmt);
        if (sharedStereoZbuffer) {
            // Graphics card shared same z buffer in stereo mode,
            // in this case we have to explicitly clearing both
            // frame buffers.
            gc.clear();
        }
        gc.draw(rightConeBody);
        gc.draw(rightConeCap);
    }
    //
    // Run method for our immediate mode rendering thread.
    //

    public void run() {
        // Set up Graphics context
        gc = canvas.getGraphicsContext3D();
        // We always need to set this for PureImmediate
        // stereo mode
        gc.setBufferOverride(true);
        Color3f lightColor = new Color3f(1, 1, 1);
        Vector3f lightDir = new Vector3f(0, 0, -1);
        DirectionalLight light = new DirectionalLight(lightColor, lightDir);
        gc.addLight(light);
        Appearance redApp = new Appearance();
        Appearance greenApp = new Appearance();
        Color3f ambientColor = new Color3f(0, 0, 0);
        Color3f emissiveColor = new Color3f(0, 0, 0);
        Color3f diffuseColor = new Color3f(1, 0, 0);
        Color3f specularColor = new Color3f(1, 1, 1);
        redApp.setMaterial(new Material(ambientColor, emissiveColor,
                diffuseColor, specularColor, 5));
        diffuseColor = new Color3f(0, 1, 0);
        greenApp.setMaterial(new Material(ambientColor, emissiveColor,
                diffuseColor, specularColor, 5));
        // Set up geometry
        Cone leftCone = new Cone(0.4f, 0.6f, Primitive.GENERATE_NORMALS, redApp);
        Cone rightCone = new Cone(0.4f, 0.6f, Primitive.GENERATE_NORMALS,
                greenApp);
        leftConeBody = leftCone.getShape(Cone.BODY);
        leftConeCap = leftCone.getShape(Cone.CAP);
        rightConeBody = rightCone.getShape(Cone.BODY);
        rightConeCap = rightCone.getShape(Cone.CAP);
        leftTrans = new Vector3f(-0.6f, 0, 0);
        rightTrans = new Vector3f(0.6f, 0, 0);
        while (true) {
            // compute data which is can be used
            // for both left and right eye
            computeSharedData();
            if (stereoSupport) {
                if (!sharedStereoZbuffer) {
                    gc.setStereoMode(GraphicsContext3D.STEREO_BOTH);
                    // This clear both left and right buffers, we
                    // must set STEREO_BOTH before it. Otherwise
                    // it only clear LEFT or RIGHT buffer unless
                    // this is invoke twice for each buffer.
                    gc.clear();
                }
                gc.setStereoMode(GraphicsContext3D.STEREO_LEFT);
                renderLeft();
                gc.setStereoMode(GraphicsContext3D.STEREO_RIGHT);
                renderRight();
            } else {
                gc.clear();
                renderLeft();
            }
            // This swap both left and right buffers so
            // there is no need to set STEREO_BOTH before it
            canvas.swap();
            // Be polite to other threads !
            Thread.yield();
        }
    }

    public PureImmediateStereo() {
    }
    //
    // init: create the canvas, stop the renderer,
    // create the universe, and start the drawing thread.
    //

    public void init() {
        setLayout(new BorderLayout());
        // Preferred to use Stereo
        GraphicsConfigTemplate3D gct = new GraphicsConfigTemplate3D();
        gct.setStereo(GraphicsConfigTemplate3D.PREFERRED);
        GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getBestConfiguration(gct);
        canvas = new Canvas3D(config);
        Map map = canvas.queryProperties();
        stereoSupport = canvas.getStereoAvailable();
        if (stereoSupport) {
            System.out.println("This machine support stereo, you should see a red cone on the left and green cone on the right.");
            // User can overide the above default behavior using
            // java3d property.
            String str = System.getProperty("j3d.sharedstereozbuffer",
                    defaultSharedStereoZbuffer);
            sharedStereoZbuffer = (new Boolean(str)).booleanValue();
        } else {
            System.out.println("Stereo is not support, you should only see the left red cone.");
        }
        if (!canvas.getDoubleBufferAvailable()) {
            System.out.println("Double buffer is not support !");
        }
        // we must stop the Renderer in PureImmediate mode
        canvas.stopRenderer();
        add("Center", canvas);
        // Create the universe and viewing branch
        u = new SimpleUniverse(canvas);
        // This will move the ViewPlatform back a bit so the
        // objects in the scene can be viewed.
        u.getViewingPlatform().setNominalViewingTransform();
        // Start a new thread that will continuously render
        (new Thread(this)).start();
    }
    // Cleanup all Java3D threads and memory when this applet exit

    public void destroy() {
        u.cleanup();
    }
    //
    // The following allows PureImmediateStereo to be run as an application
    // as well as an applet
    //

    public static void main(String[] args) {
        new MainFrame(new PureImmediateStereo(), 512, 256);
    }
}
