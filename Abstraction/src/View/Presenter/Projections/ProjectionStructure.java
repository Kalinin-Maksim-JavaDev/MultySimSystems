/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Presenter.Projections;

import Platform.Core.Systems.SystemMContainer;
import Logic.Reflections.Space.ISpace;
import Render.View.Render.IRender;

/**
 *
 * @author kalinin
 */
abstract class ProjectionStructure extends SystemMContainer {

    int rendercount;
    public IRender art;
    protected ISpace space;

    ProjectionStructure() {
    }
}
