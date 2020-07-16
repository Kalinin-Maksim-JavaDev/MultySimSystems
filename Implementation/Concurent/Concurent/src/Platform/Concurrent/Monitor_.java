package Platform.Concurrent;

import Platform.Concurrent.IMonitor;
import Platform.Concurrent.MonitorBasic;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalinin
 */
public abstract class Monitor_ extends MonitorBasic implements IMonitor {

    public void Release() {
        notify();
    }

    public void Wait() {

        try {
            wait();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        //System.out.println("go!");
    }
}
