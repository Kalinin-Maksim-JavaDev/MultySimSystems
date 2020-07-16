/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Render.View.IWorksQueue;
import Application.Build.Control.VirtualPad.IVrtualPadMotionsReciver;
import Application.Build.IRankedWorkersManager;
import Platform.Core.IMotion;
import Platform.Core.ISystemM;
import Global.Tools;
import Platform.Concurrent.Factory;
import Platform.Concurrent.ILathce;
import Platform.Core.Motion.Motion;
import Platform.Core.Motion.MotionFabric;
import Platform.Core.Systems.SystemM;
import Platform.Util.Profiler.ClassAssociations;
import java.util.ArrayList;

/**
 *
 * @author kalinin
 */
public class RankedWorkersManager implements IRankedWorkersManager, IRankedWorkersManager.binded, IVrtualPadMotionsReciver {

    private final int maxRank;
    private final WorkFactory[] workFactorys;
    // private final WorkersManager activeWorkersManager;
    private final ISystemM performer;
    private ILathce[] workLathces;
    private IWorksQueue worksBuffer;
    private Worker[] workersIndex;
    private WorkersArray activeWorkers = new WorkersArray();
    private WorkersArray preparedWorkers = new WorkersArray();
    private WorkersArray waitedWorkers = new WorkersArray();
    private ClassAssociations classAssociations;

    public RankedWorkersManager(int maxRank) {
        classAssociations = new ClassAssociations();
        classAssociations.PrintAssociaties();
        this.maxRank = maxRank;

        workLathces = new ILathce[maxRank];
        workersIndex = new Worker[maxRank];
        workFactorys = new WorkFactory[maxRank];
        for (int i = 0; i < maxRank; i++) {
            Worker worker = new Worker(i);
            workersIndex[i] = worker;
            workFactorys[i] = new WorkFactory(worker);
            workLathces[i] = Factory.createLathce(true, true);

            // activeWorkers.add(worker);
        }

        //    activeWorkersManager = new WorkersManager();

        performer = new SystemM("d\t|") {

            public String SetThreadName() {
                return "drawSys";
            }
        };

        performer.Start();

    }

    public IVrtualPadMotionsReciver getVrtualPadMotionsReciver() {
        return this;
    }

    public IRankedWorkersManager.binded setWorksQueue(IWorksQueue worksQueue) {
        this.worksBuffer = worksQueue;
        return this;
    }

    public void resetWorker(final int _rank) {

        performer.AddMotions(new Motion(1, "DisableWork") {

            int rank = _rank;

            public void MotionMethod() {

                ILathce l_ = workLathces[rank];
                workLathces[rank] = Factory.createLathce(true, true);
                l_.synchronizedRelease();
            }
        });
        performer.Resume();
    }

    public void disableWorker(final int _rank) {

        performer.AddMotions(new Motion(1, "DisableWork") {

            int rank = _rank;

            public void MotionMethod() {

                if (!activeWorkers.contains(workersIndex[rank])) {
                    //  System.out.println("__WORK: !!!!!!!!!!!!!Worker " + rank + " disabled!");
                } else {
                    //System.out.println("__WORK: DisableWorker " + rank);
                    activeWorkers.remove(workersIndex[rank]);
                }
            }
        });
        performer.Resume();
    }

    public void enableWorker(final int _rank) {

        performer.AddMotions(new Motion(1, "enableWorker") {

            int rank = _rank;

            public void MotionMethod() {
                if (activeWorkers.contains(workersIndex[rank])) {
                    Tools.UnsupportedOperationException("__WORK: !!!!!!!!!!!!!Worker " + rank + " enabled!");
                } else {
                    //System.out.println("__WORK: enableWorker " + rank);
                    activeWorkers.add(workersIndex[rank]);
                }
            }
        });
        performer.Resume();
    }

    public void close() {
        for (int rank = 0; rank < maxRank; rank++) {
            ILathce l_ = workLathces[rank];
            workLathces[rank] = Factory.createLathce(true, false);
            l_.synchronizedRelease();
        }
        performer.Stop();
    }

    public void OnWorkerIsReady(int rank) {

        //System.out.println("__WORK: Worker " + rank + " work and sleep");
        performer.AddMotions(workFactorys[rank].GetMotion(1));
        performer.Resume();

        workLathces[rank].synchronizedWait();

        //System.out.println("__WORK: Worker " + rank + " wakeup");
    }

    public void AddMotions(IMotion motion) {
        performer.AddMotions(motion);
    }

    private class WorkFactory extends MotionFabric {

        Worker worker;

        public WorkFactory(Worker worker) {
            super(1);
            this.worker = worker;
        }

        public Motion NewMotion(int iterates_) {
            return new MotionWork();
        }

        class MotionWork extends Motion {

            public MotionWork() {
                super(1, "MotionWork");
            }

            WorkersArray workersAbove(Worker worker, WorkersArray wokers) {

                WorkersArray workersAbove = new WorkersArray();

                for (int i = 0; i < wokers.size(); i++) {

                    Worker w = wokers.get(i);

                    if (w.rank > worker.rank) {

                        workersAbove.add(w);
                    }
                }

                return workersAbove;
            }

            public void MotionMethod() {

                //activeWorkersManager.enableDisable();

                if (activeWorkers.contains(worker)) {

                    waitedWorkers.add(worker);

                    WorkersArray readyWorkers = new WorkersArray();

                    readyWorkers.add(worker);

                    WorkersArray topRankWorkers = workersAbove(worker, waitedWorkers.sub(preparedWorkers));

                    topRankWorkers.sort();

                    readyWorkers.addAll(topRankWorkers);
                    //System.out.println("__WORK: worker ready " + worker.rank);
                    for (int i = 0; i < readyWorkers.size(); i++) {

                        Worker e = readyWorkers.get(i);

                        if ((preparedWorkers.maxRank(activeWorkers) == e.rank)) {

                            worksBuffer.fill(e.rank);

                            preparedWorkers.add(e);

                            //System.out.println("__WORK: worker " + e.rank + " is prepared");

                        } else {
                            Tools.UnsupportedOperationException();
                        }
                    }

                    if (preparedWorkers.size() > activeWorkers.size()) {
                        Tools.UnsupportedOperationException();
                    }
                    if (preparedWorkers.size() == activeWorkers.size()) {

                        for (int i = 0; i < preparedWorkers.size(); i++) {

                            Worker worker = preparedWorkers.get(i);

                            if (!activeWorkers.contains(worker)) {

                                Tools.UnsupportedOperationException();
                            }

                        }

                        if (worksBuffer.isEmpty()) {
                            //    Tools.UnsupportedOperationException();
                        } else {
                            //System.out.println("Update pictureQueue = " + pictureQueue.size());
                            worksBuffer.flush(false);
                            
                        }
                        preparedWorkers.clear();
                        waitedWorkers.clear();

                        //System.out.println("__WORK: Release workers " + activeWorkers.size());

                        for (int i = 0; i < activeWorkers.size(); i++) {

                            Worker e = activeWorkers.get(i);

                            workLathces[e.rank].synchronizedRelease();

                        }

                        performer.Repeat(true);
                    } else {

                        //System.out.println("__WORK: prepared Workers " + preparedWorkers.size());
                        //System.out.println("__WORK: active Workers " + activeWorkers.size());


                    }
                } else {

                    //System.out.println("__WORK: active Workers don't containes " + worker.rank);

                }


            }
        }
    }

    private static class Worker {

        public int rank;

        public Worker(int rank) {

            this.rank = rank;
        }
    }

    class WorkersArray extends ArrayList<Worker> {

        WorkersArray sub(WorkersArray workers) {

            WorkersArray sub = new WorkersArray();

            for (int i = 0; i < size(); i++) {

                Worker e = get(i);

                if (!workers.contains(e)) {

                    sub.add(e);
                }

            }

            return sub;
        }

        private int maxRank(WorkersArray workers) {

            int result = -1;

            if (isEmpty()) {

                result = 0;

            } else {

                result = get(size() - 1).rank + 1;

                while (!workers.containsRank(result)) {

                    result++;

                    if ((result) >= workers.size()) {
                        Tools.UnsupportedOperationException();
                    }

                }

                return result;

            }

            return result;
        }

        void sort() {

            for (int i = 0; i < size() - 1; i++) {
                for (int j = 0; j < size() - i - 1; j++) {
                    if (get(j).rank > get(j + 1).rank) {
                        swap(j, j + 1);
                    }
                }
            }

        }

        private void swap(int left, int right) {

            Worker e = get(left);

            set(left, get(right));

            set(right, e);

        }

        boolean containsRank(int rank) {

            return contains(workersIndex[rank]);

        }
    }
}
