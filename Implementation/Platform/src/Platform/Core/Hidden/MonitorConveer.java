/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Platform.Core.Hidden;

import Platform.Concurrent.Monitor_;

/**
 *
 * @author kalinin
 */
public class MonitorConveer extends Monitor_ {

    ConveerTrue conveer;

    public MonitorConveer(ConveerTrue conveer_) {
        conveer = conveer_;
    }

    public synchronized void synchronizedWait() {
        {
            while (!conveer.LastItterationComplite()) {
                //   long time_ = System.currentTimeMillis();

                //     System.out.println("Timer sleep");
                Wait();

                //     System.out.println(System.currentTimeMillis() - time_);
                //     System.out.println(System.currentTimeMillis() - time_);
                //     System.out.println(System.currentTimeMillis() - time_);
                //     System.out.println(System.currentTimeMillis() - time_);
                //     System.out.println(System.currentTimeMillis() - time_);
                //     System.out.println(System.currentTimeMillis() - time_);
                //     System.out.println(System.currentTimeMillis() - time_);
                //     System.out.println(System.currentTimeMillis() - time_);
            }
            conveer.incTimerCounter();
        }
    }

    public synchronized void synchronizedRelease() {
        conveer.IncM();
        Release();
    }
}
