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
public class Sphere_ extends Image3D {

    public Sphere_(int color_, ISpace space_) {
        super(space_);
        // transform.setTranslation(vector3f);
        // unit.setTransform(transform);
        // unit.addChild(new Sphere(0.5f));
        //contents.addChild(unit);
        //geom = new Sphere(0.5f).getGeometry();
        geom = ((Space3D) space).ballMesh.getGeometry();
//            TextureAttributes texAttr = new TextureAttributes();
//            texAttr.setTextureMode(TextureAttributes.MODULATE);
        Appearance aprnc = new Appearance();
//            aprnc.setTexture(textball);
        // aprnc.setTextureAttributes(texAttr);
        aprnc.setMaterial(new Material(((Space3D) space).red, ((Space3D) space).black, ((Space3D) space).red, ((Space3D) space).black, 1.0f));


        shape = new Shape3D(geom, aprnc);

        ((Space3D) space).images[((Space3D) space).curnum] = this;
        ((Space3D) space).curnum++;
    }
}
