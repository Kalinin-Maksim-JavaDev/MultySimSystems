/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter.Graphics.Space.G3D;

/**
 *
 * @author kalinin
 */
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.geometry.Primitive;
import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.PointLight;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Alpha;
import javax.media.j3d.RotationInterpolator;
import javax.vecmath.Vector3f;
import javax.vecmath.Point3f;
import javax.vecmath.Point3d;
import javax.vecmath.Color3f;
import java.awt.Frame;
import java.awt.Label;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//This is the top-level driver class for this program.
// This program could be written without the use of this
// driver class.  However, I decided to keep it intact
// for future expansions that require a user input GUI.
public class Java3D005 extends Frame {

    public static void main(String[] args) {
        Java3D005 thisObj = new Java3D005();
    }//end main
    //----------------------------------------------------//

    public Java3D005() {//constructor
        setTitle("Copyright 2007, R.G.Baldwin");
        add(new Label("You can build a GUI here."));
        setBounds(236, 0, 235, 75);
        setVisible(true);

        //Instantiate the object in which the Java 3D
        // universe will be displayed.
        TheScene theScene = new TheScene();

        //This window listener is used to terminate the
        // program when the user clicks the X button.
        addWindowListener(
                new WindowAdapter() {

                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }//end windowClosing
                }//end new WindowAdapter
                );//end addWindowListener

    }//end constructor
    //----------------------------------------------------//

    //This is an inner class, from which the object in which
    // the Java 3D universe will be displayed is
    // instantiated.
    class TheScene extends Frame {

        TheScene() {//constructor

            //Create a Canvas3D object to be used for rendering
            // the Java 3D universe. Place it in the CENTER of
            // the Frame.
            Canvas3D canvas3D = new Canvas3D(
                    SimpleUniverse.getPreferredConfiguration());
            add(BorderLayout.CENTER, canvas3D);

            //Construct the objects that will be displayed in
            // the scene

            //Create and set properties for the large yellow
            // sphere.
            //Begin by describing the appearance of the surface
            // of the large sphere.  Make the color of the large
            // sphere yellow.
            Material yellowSphMaterial = new Material();
            yellowSphMaterial.setDiffuseColor(1.0f, 1.0f, 0.0f);
            Appearance yellowSphAppearance = new Appearance();
            yellowSphAppearance.setMaterial(yellowSphMaterial);

            //Now instantiate the large yellow sphere with 9
            // divisions.  Set the radius to 0.5. The reason for
            // setting GENERATE_NORMALS is unclear at this time.
            Sphere yellowSph = new Sphere(
                    0.5f,
                    Primitive.GENERATE_NORMALS,
                    9,
                    yellowSphAppearance);

            //Now create a small white sphere with 50 divisions.
            Material whiteSphMaterial = new Material();
            whiteSphMaterial.setDiffuseColor(1.0f, 1.0f, 1.0f);
            Appearance whiteSphAppearance = new Appearance();
            whiteSphAppearance.setMaterial(whiteSphMaterial);
            Sphere whiteSph = new Sphere(
                    0.10f,
                    Primitive.GENERATE_NORMALS,
                    50,
                    whiteSphAppearance);

            //Translate the location of the white sphere to make
            // it closer to the viewer than the yellow sphere at
            // the origin.
            Transform3D whiteTransform = new Transform3D();
            //The following is a modification to the original
            // virtual universe that causes the white sphere to
            // be in the horizontal plane.  This causes the
            // white sphere to later be animated so as to appear
            // to be in a synchronous orbit around the yellow
            // sphere.
            whiteTransform.setTranslation(
                    new Vector3f(-0.5f, -0.0f, 0.5f));
            TransformGroup whiteTransformGroup =
                    new TransformGroup();
            whiteTransformGroup.setTransform(whiteTransform);
            whiteTransformGroup.addChild(whiteSph);


            //Now create a small green sphere located up to the
            // right and behind the yellow sphere.
            Material greenSphMaterial = new Material();
            greenSphMaterial.setDiffuseColor(0.0f, 1.0f, 0.0f);
            Appearance greenSphAppearance = new Appearance();
            greenSphAppearance.setMaterial(greenSphMaterial);
            Sphere greenSph = new Sphere(
                    0.10f,
                    Primitive.GENERATE_NORMALS,
                    50,
                    greenSphAppearance);
            Transform3D greenTransform = new Transform3D();
            greenTransform.setTranslation(
                    new Vector3f(0.5f, 0.5f, -0.5f));
            TransformGroup greenTransformGroup =
                    new TransformGroup();
            greenTransformGroup.setTransform(greenTransform);
            greenTransformGroup.addChild(greenSph);


            //Add a white point light, in front of, to the
            // right of, and above the yellow sphere.
            Color3f pointLightColor =
                    new Color3f(1.0f, 1.0f, 1.0f);
            Point3f pointLightPosition =
                    new Point3f(1.0f, 1.0f, 2.0f);
            Point3f pointLightAttenuation =
                    new Point3f(1.0f, 0.0f, 0.0f);

            PointLight pointLight = new PointLight(
                    pointLightColor,
                    pointLightPosition,
                    pointLightAttenuation);

            //Create a BoundingSphere object and use it to
            // determine which objects to light.  Also use it
            // later to determine which objects to animate.
            BoundingSphere boundingSphere =
                    new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 1.0);
            pointLight.setInfluencingBounds(boundingSphere);


            //Create an empty Java 3D universe and associate it
            // with the Canvas3D object in the CENTER of the
            // frame.
            SimpleUniverse simpleUniverse =
                    new SimpleUniverse(canvas3D);

            //Create and populate a BranchGroup object.
            BranchGroup branchGroup = new BranchGroup();
            //Add objects to the branchGroup. Note that the
            // yellow and white spheres are no longer added to
            // the branchGroup object, but rather are later
            // added to a group that causes them to be animated.
            branchGroup.addChild(greenTransformGroup);
            //If you disable the following statement and enable
            // a statement later that adds the pointLight to
            // the rotationXform, you will see a very different
            // effect.  This will cause the light source to
            // rotate with the yellow sphere causing the shadows
            // to move across the spheres.
            branchGroup.addChild(pointLight);


            //THE CODE THAT IMPLEMENTS THE ANIMATION BEGINS HERE

            //Create a transform group that will be populated
            // with the yellow sphere and the white sphere
            // transform group. Objects or groups of objects
            // belonging to this group will be animated.
            TransformGroup rotationXform = new TransformGroup();
            rotationXform.setCapability(
                    TransformGroup.ALLOW_TRANSFORM_WRITE);

            //Create an Alpha object that will be used to cause
            // the objects in the rotationXform group to
            // complete one animation cycle in 20 seconds.
            Alpha rotationAlpha = new Alpha(1, 20000);

            //Create an Interpolator object that will cause the
            // objects in the rotationXform group to rotate 360
            // degrees about the vertical axis in the time
            // specified for one cycle by the rotationAlpha
            // object.
            RotationInterpolator rotator =
                    new RotationInterpolator(
                    rotationAlpha, rotationXform);

            //Specify a region in 3D space containing the
            // objects or groups of objects that will be
            // animated.
            rotator.setSchedulingBounds(boundingSphere);

            //Add the objects to the group that controls
            // the animation.
            rotationXform.addChild(rotator);
            rotationXform.addChild(yellowSph);
            rotationXform.addChild(whiteTransformGroup);
            //Disable the earlier statement that adds the
            // pointLight to the branchGroup and enable the
            // following statement to get a very different
            // effect
            //rotationXform.addChild(pointLight);

            //Add the group that will be animated to the main
            // branch of the scene graph.
            branchGroup.addChild(rotationXform);


            //THE CODE THAT IMPLEMENTS THE ANIMATION ENDS HERE


            //Specify the apparent location of the viewer's eye.
            simpleUniverse.getViewingPlatform().
                    setNominalViewingTransform();

            //Populate the universe by adding the branch group
            // that contains the objects.
            simpleUniverse.addBranchGraph(branchGroup);

            //Do the normal GUI stuff.
            setTitle("Copyright 2007, R.G.Baldwin");
            setBounds(0, 0, 235, 235);
            setVisible(true);

            //This listener is used to terminate the program
            // when the user clicks the X-button on the Frame.
            addWindowListener(
                    new WindowAdapter() {

                        public void windowClosing(WindowEvent e) {
                            System.exit(0);
                        }//end windowClosing
                    }//end new WindowAdapter
                    );//end addWindowListener

        }//end constructor
        //--------------------------------------------------//
    }//end inner class TheScene
}//end class Java3D005
