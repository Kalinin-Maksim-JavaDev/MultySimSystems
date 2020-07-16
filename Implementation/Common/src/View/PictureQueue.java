/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Render.View.IWorksQueue;
import Render.View.IPictureQueue;
import Model.Game.Presenter.Space.Picture;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author kalinin
 */
public abstract class PictureQueue implements IPictureQueue, IWorksQueue {

    protected abstract Object getPictureList(int layerID);

    protected abstract void onDraw(boolean b);
    private Object tool;
    private ArrayList storage = new ArrayList();

    public PictureQueue(Object tool) {
        this.tool = tool;
    }

    public boolean isEmpty() {
        return storage.isEmpty();
    }

    public int size() {
        return storage.size();
    }

    public void draw(Object tool, boolean simple) {

        for (int i = 0; i < storage.size(); i++) {

            ((Picture) storage.get(i)).Draw(tool);//tool<-Space.im[0]<-Space.im[1]<-...

            //space_.painter.Draw(tool);

        }
        //pictureQueue_.clear();

    }

    public void flush(boolean b) {
        draw(tool, b);
        onDraw(b);
        storage.clear();
    }

    public void fill(int layerID) {

        Object pictureList = getPictureList(layerID);

        if (((Collection) pictureList).isEmpty()) {
            //           Tools.UnsupportedOperationException();
        } else {
            storage.addAll((Collection) pictureList);
        }
    }
}
