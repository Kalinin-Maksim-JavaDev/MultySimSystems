/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter.Graphics.Space.G3D;

import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;
import javax.vecmath.Point3f;
import javax.vecmath.TexCoord2f;

/**
 *
 * @author kalinin
 */
public class SurFace extends Image3D {

    java.awt.image.BufferedImage image;

    public SurFace(Space3D space_, java.awt.image.BufferedImage image_) {
        super(space_);
        image = image_;


        QuadArray polygon1 = new QuadArray(4, QuadArray.COORDINATES | GeometryArray.TEXTURE_COORDINATE_2);
        polygon1.setCoordinate(0, new Point3f(0f, 0f, 0f));
        polygon1.setCoordinate(1, new Point3f(1f, 0f, 0f));
        polygon1.setCoordinate(2, new Point3f(1f, 1f, 0f));
        polygon1.setCoordinate(3, new Point3f(0f, 1f, 0f));

        polygon1.setTextureCoordinate(0, 0, new TexCoord2f(0.0f, 0.0f));
        polygon1.setTextureCoordinate(0, 1, new TexCoord2f(1.0f, 0.0f));
        polygon1.setTextureCoordinate(0, 2, new TexCoord2f(1.0f, 1.0f));
        polygon1.setTextureCoordinate(0, 3, new TexCoord2f(0.0f, 1.0f));

        geom = polygon1;



        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);
        Appearance aprnc = new Appearance();
        aprnc.setTextureAttributes(texAttr);
       // aprnc.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
     //   aprnc.setTexture(((Space3D) space).textball);

        shape = new Shape3D(geom, aprnc);

        ((Space3D) space).images[((Space3D) space).curnum] = this;
        ((Space3D) space).curnum++;
    }

    public void UpDate() {
         TextureLoader loader = new TextureLoader(image,TextureLoader.BY_REFERENCE);
        Texture2D tex = (Texture2D) loader.getTexture();
        shape.getAppearance().setTexture(tex);
    }
}
