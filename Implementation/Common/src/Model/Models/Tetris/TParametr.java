package Model.Models.Tetris;

import Model.Game.GParametr;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalinin
 */
public class TParametr {

//    public static int Block11Index = 0;
//    public static int Block12Index = 1;
//    public static int Block13Index = 2;
//    public static int Block21Index = 3;
//    public static int Block22Index = 4;
//    public static int Block23Index = 5;
    //  public final static int k = 20;
    public final static int grad = 20;
    public final static int h = GParametr.h / grad;
    public final static int w = GParametr.w / grad;
    public final static int grad3d = 1;



    public final static int a = 8*2;
    public final static int b = 12;

    public final static int x= (w-a)/2;
    public final static int y = h-b;
    public static int graphicsLevel = 3;
}
