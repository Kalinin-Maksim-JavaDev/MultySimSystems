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
public class OrderBarrier {

    AtomicIntegerS counter;
    Lathce[] lacthes;
    public int counterMax;

    public OrderBarrier(int counter_) {
        counter = new AtomicIntegerS();
        counterMax = counter_;
        counter.set(1);
        lacthes = new Lathce[counterMax];
        for (int i = 1; i < counterMax; i++) {
            lacthes[i] = new Lathce(true, false);
        }
        lacthes[0] = new Lathce(false, false);
    }

    public void Wait(int n_) {
       // Pause.Pause2();

        lacthes[n_].synchronizedWait();
      //  System.out.println(n_);
    }

    public boolean Next() {
      //  Pause.Pause2();
        int n_ = counter.getAndIncrement();
        if (n_ != counterMax) {
          //  Pause.Pause2();
            lacthes[n_].synchronizedRelease();
            // lacthes[n_-1].synchronizedWait();
            return false;
        } else {          
            //lacthes[counterMax-1].synchronizedWait();
            return true;
        }
    }
    public void End(){
          counter.set(1);
         //   Pause.Pause2();
          lacthes[0].synchronizedRelease();
    }

    public void Close() {
         for (int i = 1; i < counterMax; i++) {
             Lathce l_=  lacthes[i];
            lacthes[i] = new Lathce(true, false){

                @Override
                public synchronized void synchronizedWait() {
                   Tools.UnsupportedOperationException();
                }

            };
            l_.synchronizedRelease();
        }
    }
}
