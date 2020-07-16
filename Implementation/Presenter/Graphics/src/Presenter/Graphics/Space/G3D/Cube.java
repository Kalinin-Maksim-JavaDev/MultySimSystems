/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter.Graphics.Space.G3D;

import Logic.Reflections.Space.ISpace;
import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;

/**
 *
 * @author kalinin
 */
public class Cube extends Image3D {

    public Cube(int color_, ISpace space_) {
        super(space_);
        //transform.setTranslation(vector3f);
        //  unit.setTransform(transform);
        //  unit.addChild(new ColorCube(0.5));
        //contents.addChild(unit);
//            Shape3D s = new Shape3D();
//            s.setGeometry(createGeometry(
//                    new Point3f(-0.5f, 0.5f, 0.5f),
//                    new Point3f(-0.5f, -0.5f, 0.5f),
//                    new Point3f(0.5f, -0.5f, 0.5f),
//                    new Point3f(0.5f, 0.5f, 0.5f),
//                    new Point3f(-0.5f, 0.5f, -0.5f),
//                    new Point3f(-0.5f, -0.5f, -0.5f),
//                    new Point3f(0.5f, -0.5f, -0.5f),
//                    new Point3f(0.5f, 0.5f, -0.5f)));
//
//            geom = s.getGeometry();
        //  geom = new ColorCube(0.5f).getGeometry();
//
//           TriangleStripArray geomMesh  = (TriangleStripArray) cubeMesh.getGeometry();
//
//
//
//            QuadArray polygon1 = new QuadArray(4, QuadArray.COORDINATES | GeometryArray.TEXTURE_COORDINATE_2);
//            polygon1.setCoordinate(0, new Point3f(0f, 0f, 0f));
//            polygon1.setCoordinate(1, new Point3f(2f, 0f, 0f));
//            polygon1.setCoordinate(2, new Point3f(2f, 3f, 0f));
//            polygon1.setCoordinate(3, new Point3f(0f, 3f, 0f));
//
//            polygon1.setTextureCoordinate(0, new Point2f(0.0f, 0.0f));
//            polygon1.setTextureCoordinate(1, new Point2f(1.0f, 0.0f));
//            polygon1.setTextureCoordinate(2, new Point2f(1.0f, 1.0f));
//            polygon1.setTextureCoordinate(3, new Point2f(0.0f, 1.0f));
//        geom=polygon1;

        geom = ((Space3D) space).cubeMesh.getGeometry();
//            TextureAttributes texAttr = new TextureAttributes();
//            texAttr.setTextureMode(TextureAttributes.MODULATE);
        Appearance aprnc = new Appearance();
        //  aprnc.setTexture(textball);
        // aprnc.setTextureAttributes(texAttr);
        aprnc.setMaterial(new Material(((Space3D) space).white, ((Space3D) space).black, ((Space3D) space).white, ((Space3D) space).black, 1.0f));

        shape = new Shape3D(geom, aprnc);
        ((Space3D) space).images[((Space3D) space).curnum] = this;
        ((Space3D) space).curnum++;
    }
}
