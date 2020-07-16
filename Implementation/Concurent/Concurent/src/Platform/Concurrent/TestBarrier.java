package Platform.Concurrent;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kalinin
 */
public class TestBarrier {

    static int n = 20;
    static OrderBarrier_ bar = new OrderBarrier_(n);
    static Random random = new Random();
    static boolean[] live = new boolean[n];

    public static void main(String[] args) {
        for (int i = 0; i < n; i++) {
            live[i] = true;
            Thread tr = new ThreadImpl(i);
            tr.start();
        }

        new Thread() {

            @Override
            public void run() {
                //  while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TestBarrier.class.getName()).log(Level.SEVERE, null, ex);
                }

                bar.Close();
                for (int i = 0; i < n; i++) {
                    live[i] = false;
                }
                //    }

            }
        }.start();
    }

    private static class ThreadImpl extends Thread {

        public int i;

        public ThreadImpl(int i_) {
            super(String.valueOf(i_));
            i = i_;
        }

        @Override
        public void run() {
            while (live[i]) {
                bar.lacthes[i].synchronizedWait();
                try {
                    Thread.sleep(random.nextInt(40), 0);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TestBarrier.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("run " + i);

                bar.lacthes[i].Next();
                if (i==bar.finalLatche){
                    System.out.println("   next cicle");
                }
            }
        }
    }
}
