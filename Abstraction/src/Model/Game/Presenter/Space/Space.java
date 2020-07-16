/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Game.Presenter.Space;

import Platform.ILogCanvas;
import Logic.Reflections.Space.ICommonProjectionInfo;
import Logic.Reflections.Space.IPainter;
import Logic.Reflections.Space.ISpace;

/**
 *
 * @author Adm
 */
public abstract class Space implements ISpace {

    public ILogCanvas logCanvas;
    public int quant;
    public IPainter painter;
    public ICommonProjectionInfo pri;

    public Space(int quant_) {
        this.quant = quant_;

    }
    public ILogCanvas getLogCanvas() {
        return logCanvas;
    }

    public IPainter getPainter() {
        return painter;
    }

    public void setPainter(IPainter painter) {
        this.painter = painter;
    }

    public ICommonProjectionInfo getPri() {
        return pri;
    }

    public void setPri(ICommonProjectionInfo pri) {
        this.pri = pri;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }
}
