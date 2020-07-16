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
public interface IResolverCollisionWithBall extends ICollisionResolver {

    public void ResolveCollisionWithBall(ICollisionResolver unit_, double x, double y);
}
