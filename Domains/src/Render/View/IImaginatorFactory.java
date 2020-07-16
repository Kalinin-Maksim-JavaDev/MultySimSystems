/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Render.View;

import Logic.Reflections.Projections.IImaginator;
import Render.Graphics.Geometry.ICoordinates;
import View.Presenter.Projections.IViewUnit;

/**
 *
 * @author dkx6r0c
 */
public interface IImaginatorFactory {

    IImaginator.charged getImaginator(IViewUnit viewUnit, ICoordinates position);
}
