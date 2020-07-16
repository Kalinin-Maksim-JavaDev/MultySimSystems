package Game.Model.Game.World.Interaction;

import Logic.Model.Game.World.Collisions.IBodie;
import Platform.Concurrent.IAtomicReferenceObject;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalinin
 */
public interface ICollisionMap {

    public IBodie getDot(int x, int y);

    public int getWeight();

    public int getHight();

    public IBodie[][] preCompliteUnitsMap(int level);

    public void PreMoveUnit(IBodie bodi, double x, double y);

    public IAtomicReferenceObject currentToCheck();

    public void CompliteGroupSet(IBodie bodi);

    public IBodie[] CheckVectorsCollisionAndCompliteUnitsMove(int size);

    public Object[][] getMap();
}
