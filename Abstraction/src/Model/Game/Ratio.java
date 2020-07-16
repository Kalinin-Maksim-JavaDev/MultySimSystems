/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Game;

import Game.Model.Game.IRatio;

/**
 *
 * @author kalinin
 */
public class Ratio implements IRatio {

    protected double moveperiod;
    protected double quant;
    protected double dt;
    protected int grad;

    public Ratio(double moveperiod, double quant, double dt, int grad) {
        this.moveperiod = moveperiod;
        this.quant = quant;
        this.dt = dt;
        this.grad = grad;
    }

    public int getGrad() {
        return grad;
    }

    public double getMoveperiod() {
        return moveperiod;
    }

    public double getQuant() {
        return quant;
    }

    public double getDt() {
        return dt;
    }
}
