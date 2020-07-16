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
public interface IResolverCollisionWithFundament extends ICollisionResolver{

    public void ResolveCollisionWithfundament(double x, double y);
}
