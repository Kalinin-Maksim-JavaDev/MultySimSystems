/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.View;

import Logic.Reflections.Space.ISpace;
import Render.View.IPicturesList;

/**
 *
 * @author dkx6r0c
 */
public interface ISpaceManager {

    ISpace SetSpace2D(int graphicsLevel, int grad, int quant, String string, int i, int layer, boolean b);

    ISpace SetSpace3D(int grad3d, int quant);

  //  void draw(Object tool);

  //  void update(int layer);

//    public void ClearQeue(int layer);
    IPicturesList getPictureList(int layer);
}
