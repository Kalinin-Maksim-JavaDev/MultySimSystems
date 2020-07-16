/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Logic.Reflections.Space;

/**
 *
 * @author kalinin
 */
public interface ICommonProjectionInfo {

    void CollectcommonProjectionInfo();

    double getViewOffsetX();

    double getViewOffsetY();

    void setViewOffsetX(double viewOffsetX);

    void setViewOffsetY(double viewOffsetY);

}
