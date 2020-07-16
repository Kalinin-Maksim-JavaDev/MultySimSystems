/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Model.Game.World.Collisions.Resolvers;

import Logic.Model.Game.World.Collisions.IBodie;


/**
 *
 * @author Adm
 */
public interface IResolverDispather {

    public void ResolveCollisionWith(IBodie unit, double x, double y);

    public void ResolveCollisionOverScope(boolean inGorscope, boolean inVertscope);
}
