/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Application.MVC.Event;
import MVC.Control.IInputCollector;
import MVC.Control.IInputSource;
import java.util.ArrayList;

/**
 *
 * @author kalinin
 */
public class InputCollector implements IInputCollector, IInputSource {

    ArrayList<Event> pressEvents;
    ArrayList<Event> releaseEvents;

    public InputCollector() {
        pressEvents = new ArrayList<Event>();
        releaseEvents = new ArrayList<Event>();
    }

    public Object getEvents() {

        ArrayList[] events_ = new ArrayList[2];

        for (int i = 0; i < events_.length; i++) {
            events_[i] = new ArrayList<Event>();
        }

        events_[0].addAll(pressEvents);
        events_[1].addAll(releaseEvents);

        pressEvents.clear();
        releaseEvents.clear();

        return events_;

    }

    public void addPressedKey(int device, int code, int[] arg) {

        pressEvents.add(new Event(device, code, arg));

    }

    public void addReleasedKey(int device, int code, int[] arg) {

        releaseEvents.add(new Event(device, code, arg));

    }

    public IInputSource getInputSource() {
        return this;
    }
}
