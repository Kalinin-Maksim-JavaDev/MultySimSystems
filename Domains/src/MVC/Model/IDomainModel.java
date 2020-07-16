/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.Model;

import Application.Build.Panels.Control.Manipulator.IManipulator.IAction;
import Game.Model.Game.World.Interaction.ICollisionMap;
import Logic.Model.Game.World.Collisions.IBodie;
import Logic.Model.IDataSource;
import MVC.Control.IControler;
import MVC.Control.IKeysMap;
import MVC.View.IViewSource;
import Platform.Core.IMotionsProducer;
import Platform.Core.Unit.IUnit;
import View.Presenter.Projections.IViewUnit;

/**
 *
 * @author kalinin
 */
public interface IDomainModel {

    String SetThreadName();

    controllerSource getControllerSource();

    void PATCH_setUp(IControler controler);

    interface controllerSource {

        IKeysMap getKeysMap();

        IMotionsProducer getControlMotionsProducer();

        IAction getAction();
    }

    withUnits createUnits(IDataSource dataSource);

    interface withUnits {

        IViewUnit.list getViewUnitList();

        Imaginated imaginateUnits(IViewSource viewSource);
    }

    interface Imaginated {

        IUnit.drawable.projection[] activeProjections();

        IUnit.drawable.projection.list getImaginatedProjectionsList();
    }

    interface asGame extends Imaginated {

        ICollisionMap getCollisionMap();

        Object[][] DEBUG_getMap();

        byte getId(IBodie gr, int x, int y);
    }
}
