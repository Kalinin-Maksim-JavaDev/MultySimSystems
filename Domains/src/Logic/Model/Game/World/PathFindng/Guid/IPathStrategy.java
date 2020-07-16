/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Model.Game.World.PathFindng.Guid;

/**
 *
 * @author kalinin
 */
public interface IPathStrategy {

    boolean abort();

    boolean retry();

    boolean forceFinshed();

    boolean beginning();

    void finishated();
}
