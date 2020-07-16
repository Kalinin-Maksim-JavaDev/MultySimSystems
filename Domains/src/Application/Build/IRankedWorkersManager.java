package Application.Build;

import Application.Build.Control.VirtualPad.IVrtualPadMotionsReciver;
import Render.View.IWorksQueue;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author User
 */
public interface IRankedWorkersManager {

    interface binded {
    }

    void enableWorker(int rank);

    void disableWorker(int rank);

    void close();

    void OnWorkerIsReady(int rank);

    IRankedWorkersManager.binded setWorksQueue(IWorksQueue worksQueue);

    IVrtualPadMotionsReciver getVrtualPadMotionsReciver();

    void resetWorker(int layer);
}
