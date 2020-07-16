/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Reflections.Space;

import Platform.ILogCanvas;

/**
 *
 * @author kalinin
 */
public interface ISpace {

    void Render();

    ILogCanvas getLogCanvas();

    IPainter getPainter();

    ICommonProjectionInfo getPri();

    int getQuant();

    void setPainter(IPainter painter);

    void setPri(ICommonProjectionInfo pri);

    void setQuant(int quant);
}
