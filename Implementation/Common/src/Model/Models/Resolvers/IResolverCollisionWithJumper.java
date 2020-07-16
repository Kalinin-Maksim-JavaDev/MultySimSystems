/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Models.Resolvers;

import Logic.Model.Game.World.Interaction.Resolvers.ICollisionResolver;

/**
 *
 * @author Dkx6r0c
 */
public interface IResolverCollisionWithJumper extends ICollisionResolver{

    public void ResolveCollisionWithjumper(double x, double y);
}
