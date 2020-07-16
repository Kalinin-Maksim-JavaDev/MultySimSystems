/*
 * Phys2D - a 2D physics engine based on the work of Erin Catto.
 * 
 * This source is provided under the terms of the BSD License.
 * 
 * Copyright (c) 2006, Phys2D
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or 
 * without modification, are permitted provided that the following 
 * conditions are met:
 * 
 *  * Redistributions of source code must retain the above 
 *    copyright notice, this list of conditions and the 
 *    following disclaimer.
 *  * Redistributions in binary form must reproduce the above 
 *    copyright notice, this list of conditions and the following 
 *    disclaimer in the documentation and/or other materials provided 
 *    with the distribution.
 *  * Neither the name of the Phys2D/New Dawn Software nor the names of 
 *    its contributors may be used to endorse or promote products 
 *    derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND 
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, 
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS 
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, 
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY 
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR 
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY 
 * OF SUCH DAMAGE.
 */
package pphys2d.phys2d.raw.shapes;

import pphys2d.phys2d.raw.Body;
import pphys2d.*;


/**
 * Super class of generic shapes in the engine
 * 
 * @author Kevin Glass
 */
public strictfp abstract class AbstractShape implements Shape {

    /** The circular bounds that fit the shape based on the position of the body */
    protected AABox bounds;

    /**
     * Construct a new shape as subclas swhich will specified it's
     * own bounds
     */
    protected AbstractShape() {
    }

    /**
     * Create a shape
     *
     * @param bounds The bounds of the shape
     */
    public AbstractShape(AABox bounds) {
        this.bounds = bounds;
    }

    /**
     * @see net.phys2d.raw.shapes.Shape#getBounds()
     */
    public AABox getBounds() {
        return bounds;
    }

    public void setBounds(AABox _bounds) {
        bounds = _bounds;
    }
    //Kalinin MP
    // float xmin;
    //  float xmax;
    int ymin;
    int ymax;
    public int[][] bound;
    public int[] l;
    public boolean[] bounseted;
    public ArrayListPure maskX = new ArrayListPure();
    public ArrayListPure maskY = new ArrayListPure();

    private int sign(int x) {
        return (x > 0) ? 1 : (x < 0) ? -1 : 0;
        //возвращает 0, если аргумент (x) равен нулю; -1, если x < 0 и 1, если x > 0.
    }

    public void drawBresenhamLine(int xstart, int ystart, int xend, int yend, Body[][] map, Body body) /**
     * xstart, ystart - начало;
     * xend, yend - конец;
     * "g.drawLine (x, y, x, y);" используем в качестве "setPixel (x, y);"
     * Можно писать что-нибудь вроде g.fillRect (x, y, 1, 1);
     */
    {
        for (int j = 0; j < bounseted.length; j++) {
            bounseted[j] = false;
        }
        int x, y, dx, dy, incx, incy, pdx, pdy, es, el, err;

        dx = xend - xstart;//проекция на ось икс
        dy = yend - ystart;//проекция на ось игрек

        incx = sign(dx);
        /*
         * Определяем, в какую сторону нужно будет сдвигаться. Если dx < 0, т.е. отрезок идёт
         * справа налево по иксу, то incx будет равен -1.
         * Это будет использоваться в цикле постороения.
         */
        incy = sign(dy);
        /*
         * Аналогично. Если рисуем отрезок снизу вверх -
         * это будет отрицательный сдвиг для y (иначе - положительный).
         */

        if (dx < 0) {
            dx = -dx;//далее мы будем сравнивать: "if (dx < dy)"
        }
        if (dy < 0) {
            dy = -dy;//поэтому необходимо сделать dx = |dx|; dy = |dy|
        }        //эти две строчки можно записать и так: dx = Math.abs(dx); dy = Math.abs(dy);

        if (dx > dy) //определяем наклон отрезка:
        {
            /*
             * Если dx > dy, то значит отрезок "вытянут" вдоль оси икс, т.е. он скорее длинный, чем высокий.
             * Значит в цикле нужно будет идти по икс (строчка el = dx;), значит "протягивать" прямую по иксу
             * надо в соответствии с тем, слева направо и справа налево она идёт (pdx = incx;), при этом
             * по y сдвиг такой отсутствует.
             */
            pdx = incx;
            pdy = 0;
            es = dy;
            el = dx;
        } else//случай, когда прямая скорее "высокая", чем длинная, т.е. вытянута по оси y
        {
            pdx = 0;
            pdy = incy;
            es = dx;
            el = dy;//тогда в цикле будем двигаться по y
        }

        x = xstart;
        y = ystart;
        err = el / 2;


        AddLinePoint(x, y, map, body, dx, dy);
        //все последующие точки возможно надо сдвигать, поэтому первую ставим вне цикла


        for (int t = 0; t < el - 1; t++)//идём по всем точкам, начиная со второй и до последней
        {
            err -= es;
            if (err < 0) {
                err += el;
                x += incx;//сдвинуть прямую (сместить вверх или вниз, если цикл проходит по иксам)
                y += incy;//или сместить влево-вправо, если цикл проходит по y
            } else {
                x += pdx;//продолжить тянуть прямую дальше, т.е. сдвинуть влево или вправо, если
                y += pdy;//цикл идёт по иксу; сдвинуть вверх или вниз, если по y

            }

            AddLinePoint(x, y, map, body, dx, dy);
        }

    }

    void AddLinePoint(int x, int y, Body[][] map, Body body, int dx, int dy) {
        if ((y < map.length) && ((x < map[0].length))
                && (y > -1) && ((x > -1))) {

            if ((l[y - ymin] < 2) && (!bounseted[y - ymin])) {
                bound[y - ymin][l[y - ymin]++] = x;
                bounseted[y - ymin] = true;
            }
            map[y][x] = body;
            //    maskY.add(y);
            //     maskX.add(x);
            if (dx > dy) {
                maskY.add(y + 1);
                maskX.add(x);
                maskY.add(y - 1);
                maskX.add(x);
            } else {
                maskY.add(y);
                maskX.add(x + 1);
                maskY.add(y);
                maskX.add(x - 1);
            }
        }
    }

    public void ClearMap(Body[][] map, Body b) {
        //MarkMap(map, b, null);
        int y_ = 0;
        int x_ = 0;
        for (int i = 0; i < maskY.size(); i++) {
            y_ = ((Integer) maskY.get(i)).intValue();
            x_ = ((Integer) maskX.get(i)).intValue();
            if ((x_ > -1) && (y_ > -1) && (x_ < map[0].length) && (y_ < map.length)) {
                map[y_][x_] = null;
            }
        }
    }

    public void FillMap(Body[][] map, Body b) {
    }
    //Kalinin MP
}
