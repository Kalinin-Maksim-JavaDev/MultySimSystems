/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter.Projections;

import Global.Tools;
import Render.View.IImaginatorFactory;
import Render.View.IImaginatorsFactorys;

/**
 *
 * @author dkx6r0c
 */
public class JumperImaginatorsFactorys implements IImaginatorsFactorys {

    static public enum Type {

        fundament, jumper, ball, granate, fundament2;

        static public Type get(int id) {

            Type result = null;
            if (id == 1) {
                result = fundament;
            } else if (id == 2) {
                result = jumper;
            } else if (id == 3) {
                result = ball;
            } else if (id == 4) {
                result = granate;
            } else if (id == 5) {
                result = fundament2;
            }

            if ((result == null) || (result.getId() != id)) {
                Tools.UnsupportedOperationException();
            }
            return result;
        }

        public int getId() {
            int result = -1;
            if (this == fundament) {
                result = 1;
            } else if (this == jumper) {
                result = 2;
            } else if (this == ball) {
                result = 3;
            } else if (this == granate) {
                result = 4;
            } else if (this == fundament2) {
                result = 5;
            }
          
            return result;
        }
    }
    public IImaginatorFactory AnimatedFundamentFactory;
    public IImaginatorFactory WallFactory;
    public IImaginatorFactory JumperFactory;
    public IImaginatorFactory BallFactory;
    public IImaginatorFactory GranateFactory;

    public IImaginatorFactory getFactory(int typeID) {

        Type type = Type.get(typeID);

        IImaginatorFactory result = null;
        if (type == type.fundament) {

            result = AnimatedFundamentFactory;
        } else if (type == type.fundament2) {

            result = AnimatedFundamentFactory;
        } else if (type == type.granate) {

            result = GranateFactory;
        } else if (type == type.jumper) {

            result = JumperFactory;
        } else if (type == type.ball) {

            result = BallFactory;
        }
        return result;
    }
}
