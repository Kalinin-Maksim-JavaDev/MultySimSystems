/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter.Platformer;

import Model.Game.Presenter.Projections.UnitProjectionInfo;
import Render.Graphics.Geometry.ICoordinates;
import Render.View.IImaginatorFactory;

/**
 *
 * @author Adm
 */
public class JumperProjectionInfo extends UnitProjectionInfo {

    public IImaginatorFactory imagefactory;

    public JumperProjectionInfo(ICoordinates position_, IImaginatorFactory modelCreator_) {
        super(position_);
        imagefactory = modelCreator_;
    }
}
