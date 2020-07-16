package Presenter.Platformer;

import Logic.Model.Game.IGame;
import Model.Game.GParametr;
import View.Presenter.Projections.IViewUnit;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 *
 *
 * @author kalinin
 */
public class JParametr {

    public final static int grad = 10;
    public final static int h = GParametr.h / grad;
    public final static int w = GParametr.w / grad;
    public final static int grad3d = 1;
    public final static int graphicsLevel = 5;
    public static IViewUnit.imaginated WinSceneID;
    public static IViewUnit.imaginated EndSceneID;
    public static IGame.unit Granade;    
}
