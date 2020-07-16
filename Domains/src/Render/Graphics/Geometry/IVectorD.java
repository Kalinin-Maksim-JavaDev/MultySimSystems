/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Render.Graphics.Geometry;

/**
 *
 * @author kalinin
 */
public interface IVectorD {

    void SetNull();

    void SetFrom(IVectorD v);

    void Add(IVectorD v);

    void Sub(IVectorD v);

    double Module();

    void Norm();
}
