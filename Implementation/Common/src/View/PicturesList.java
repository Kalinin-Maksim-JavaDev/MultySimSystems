/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Render.View.IPicturesList;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author dkx6r0c
 */
public class PicturesList implements IPicturesList {

    ArrayList storage = new ArrayList();

    public PicturesList() {
    }

    public void addAll(Object collection) {
        storage.addAll((Collection) collection);
    }

    public Object get() {
        return storage;
    }

}
