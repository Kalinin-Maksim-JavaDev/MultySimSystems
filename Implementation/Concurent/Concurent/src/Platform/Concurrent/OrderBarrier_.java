package Platform.Concurrent;

import Global.Tools;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author kalinin
 */
public class OrderBarrier_ {

    public Lathce_[] lacthes;
    public int counterMax;
    volatile public int finalLatche;

    public OrderBarrier_(int counter_) {
        if (counter_ < 2) {
            Tools.UnsupportedOperationException();
        }
        counterMax = counter_;
        finalLatche = counterMax - 1;
        lacthes = new Lathce_[counterMax];
        for (int i = 0; i < counterMax; i++) {
            DependLayer(i) ;
        }
    }

    public void DependLayer(int i) {
        lacthes[i] = new Lathce_(i != 0, i);
    }

    public void InDependLayer(int i) {
        Lathce_ l_ = lacthes[i];
        lacthes[i] = new Lathce_(true, i) {

            @Override
            public synchronized void synchronizedWait() {
            }
        };
        l_.synchronizedRelease();
    }

    public void Close() {

        for (int i = 0; i < counterMax; i++) {
            InDependLayer(i);
        }
    }

    public class Lathce_ extends Lathce {

        int i;

        public Lathce_(boolean initState_, int i_) {
            super(initState_, false);
            i = i_;
        }

        public void Next() {
            if (i == counterMax - 1) {
                lacthes[0].synchronizedRelease();
            } else {
                lacthes[i + 1].synchronizedRelease();
            }
        }
    }
}
