/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform;

import Platform.Concurrent.IMonitor;
import Platform.Concurrent.IMonitorBody;
import Platform.Concurrent.Monitor_;

/**
 *
 * @author Adm
 */
public class RenderCounter {

    final int lim = 60000;
    int d = 0;
    int renderCounter = 0;
    public IMonitor monitor;

    public RenderCounter() {

        monitor = (new Monitor_Itr()).monitor;


    }

    public void IncD() {
        d++;
        if (d > lim) {
            d = 0;
        }
    }

//        public boolean RenderCompl() {
//            return d == a;
//        }
    public int GetIteration() {
        return d;
    }

    public void incRenderCounter() {
        renderCounter++;
        if (renderCounter > lim) {
            renderCounter = 0;
        }
    }

    private class Monitor_Itr implements IMonitorBody {

        IMonitor monitor;

        public Monitor_Itr() {
            monitor = new Monitor_() {

                public void synchronizedRelease() {
                    Monitor_Itr.this.synchronizedRelease();
                }

                public void synchronizedWait() {
                    Monitor_Itr.this.synchronizedWait();
                }
            };
        }

        public void synchronizedWait() {
            while (renderCounter != d) {

                synchronized (monitor) {
                    monitor.Wait();
                }
            }
        }

        public void synchronizedRelease() {
            synchronized (monitor) {
                monitor.Release();
            }
            // System.out.println("  " + ":" + currentTimeMillis() + " " + sysName + taskMotions.getName() + " notify");
        }
    }
}
