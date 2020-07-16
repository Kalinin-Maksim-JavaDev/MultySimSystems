/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Game;

import Logic.IGameData;
import Game.Model.Game.IRatio;
import Logic.Model.Game.World.Collisions.IBodie;
import MVC.Model.IDomainModel;
import Presenter.Platformer.JParametr;

/**
 *
 * @author Adm
 */
public class GameData implements IGameData {

    final public byte[][] gameMap;// = {l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19, l20, l21, l22, l23, l24, l25, l26, l27, l28, l29, l30, l31, l32};
    final public byte[][] decorations;

    public GameData(byte[] data) {

        gameMap = new byte[GParametr.h][GParametr.w];
        int k = 0;
        for (int j = 0; j < gameMap.length; j++) {
            for (int i = 0; i < gameMap[0].length; i++) {
                gameMap[j][i] = data[k++];
            }
        }
        decorations = new byte[JParametr.h][JParametr.w];
        for (int j = 0; j < decorations.length; j++) {
            for (int i = 0; i < decorations[0].length; i++) {
                decorations[j][i] = data[k++];
            }
        }
    }

    public byte[][] getDecorations() {
        return decorations;
    }

    public int DataSize() {
        int res = gameMap.length * gameMap[0].length + decorations.length * decorations[0].length;
        return res;
    }

    public byte[] ToData() {
        byte[] data = new byte[DataSize()];
        int k = 0;
        for (int j = 0; j < gameMap.length; j++) {
            for (int i = 0; i < gameMap[0].length; i++) {
                data[k++] = gameMap[j][i];
            }
        }
        for (int j = 0; j < decorations.length; j++) {
            for (int i = 0; i < decorations[0].length; i++) {
                data[k++] = decorations[j][i];
            }
        }
        return data;
    }

    public void UpDate(IDomainModel.asGame game, IRatio ratio) {
        for (int j = 0; j < JParametr.h; j++) {
            for (int i = 0; i < JParametr.w; i++) {
                IBodie gr = game.getCollisionMap().getDot(i * ratio.getGrad(), j * ratio.getGrad());


                gameMap[j][i] = game.getId(gr, i, j);

            }
        }
    }
}
