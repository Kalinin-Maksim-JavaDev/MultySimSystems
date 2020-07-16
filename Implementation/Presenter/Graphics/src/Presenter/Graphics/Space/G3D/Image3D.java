/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Presenter.Graphics.Space.G3D;

import Model.Game.Presenter.Space.Image;
import Model.Geometry.D2.Coordinates2d;
import Logic.Reflections.Space.ISpace;
import Model.Models.Unit.D3.Coordinates3d;
import Render.Graphics.Geometry.ICoordinates;
import javax.media.j3d.Geometry;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3d;

/**
 *
 * @author kalinin
 */
public abstract class Image3D extends Image {

    
    public Geometry geom;
    public Shape3D shape;
    public boolean renderit;
    Transform3D trf = new Transform3D();
    //TransformGroup unit = new TransformGroup();
    // Transform3D transform = new Transform3D();
    public Vector3d vector3f = new Vector3d(0, 0, 0);

    public Image3D(ISpace space_) {
        super(space_);
        //   unit.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
      
    }

    @Override
    public void PutImage(ICoordinates coor) {
        vector3f.x=(((Coordinates3d) coor).xv);
        vector3f.y=(((Coordinates3d) coor).yv);
        vector3f.z=(0);
        trf.setTranslation(vector3f);
        trf.setScale(0.5);
        //transform.setTranslation(vector3f);
        //    unit.setTransform(transform);
    }

    public void PutImage(ICoordinates coor, int z) {
        vector3f.x=(((Coordinates2d) coor).getX());
        vector3f.y=(((Coordinates2d) coor).getY());
        vector3f.z=(z);
        trf.setTranslation(vector3f);
        trf.setScale(0.5);
    }

    public void setRenderingEnable(boolean renderit_) {
        renderit = renderit_;
    }
}

