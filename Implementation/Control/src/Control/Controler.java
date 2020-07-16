/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Application.Build.Panels.Control.Manipulator.IManipulator.binded;
import Application.Build.Panels.Model.IEnvironment;
import MVC.Control.IControler;
import Control.Manipulator.ManipulatorSystem;
import Application.Build.Panels.Control.Manipulator.IManipulator;
import Global.Tools;
import MVC.Control.IKeysMap;
import Platform.Core.IExecuter;
import Platform.Core.IMotion;
import Platform.Core.IMotionReciver;
import Platform.Core.IMotionsProducer;
import Platform.Core.Motion.Executer;

/**
 *
 * @author User
 */
public class Controler implements IControler {

    private IEnvironment.control environment;
    private IExecuter[][] executers;
    private IExecuter executerEsc;
    private ManipulatorSystem manipulator;

    public Controler(IEnvironment.control environment, IKeysMap keysMap) {
        this.environment = environment;

        manipulator = new ManipulatorSystem();

        executers = new Executer[2][];
        executers[0] = new Executer[200];
        executers[1] = new Executer[7];
        for (int i = 0; i < executers[0].length; i++) {
            executers[0][i] = MovementsFactory.CreateNothingExecuter();


        }
        for (int i = 0; i < executers[1].length; i++) {
            executers[1][i] = MovementsFactory.CreateNothingExecuter();
        }



        int[] keyboardKeys = {65, 68, 87, 83, KeysID.Space, KeysID.Enter, KeysID.Esc, KeysID.F5};

        for (int i = 0; i < keyboardKeys.length; i++) {
            int code = keyboardKeys[i];
            String name = keysMap.getName(code);

            if (name != null) {
                IExecuter CreateExecuter = MovementsFactory.CreateExecuter(name);

                if (CreateExecuter == null) {
                    executers[0][code] = MovementsFactory.CreateNothingExecuter();
                } else {
                    executers[0][code] = CreateExecuter;
                    if (name.equals("Esc")) {
                        executerEsc = executers[0][code];
                    }
                }
            }
        }

        int[] mouseKeys = {1, 3, 0, 6, 5};
        for (int i = 0; i < mouseKeys.length; i++) {
            int code = mouseKeys[i];
            String name = keysMap.getName(code);

            if (name != null) {
                executers[1][code] = MovementsFactory.CreateExecuter(name);
                IExecuter CreateExecuter = MovementsFactory.CreateExecuter(name);

                if (CreateExecuter == null) {
                    executers[1][code] = MovementsFactory.CreateNothingExecuter();
                } else {
                    executers[1][code] = CreateExecuter;
                }
            }
        }
    }

    public IManipulator getManipulator() {
        return manipulator;
    }

    public IControler.Ready with(IMotionsProducer producer) {

        IExecuter[] devExc = executers[0];

        for (int i = 0; i < devExc.length; i++) {
            IExecuter exec = devExc[i];
            if (!MovementsFactory.isNothing(exec)) {
                exec.setProducer(producer);
            }
        }

        return new ReadyInstance();
    }

    public IExecuter getExecutor(String name) {

        if (name.equals("Esc")) {
            return executerEsc;

        }
        return null;
    }

    /**
     * @return the manipulator
     */
    private class ReadyInstance implements Ready, Ready.reciving {

        public ReadyInstance() {
        }

        public Ready.reciving withStartedManipulator(IManipulator.IAction action) {

            binded m = manipulator.bindAction(action);

            IExecuter[] devExc = executers[1];

            for (int i = 0; i < devExc.length; i++) {
                IExecuter exec = devExc[i];
                if (!MovementsFactory.isNothing(exec)) {
                    m = m.setProducer(exec);
                }
            }

            m.Start();

            return this;
        }

        public IManipulator.binded getManipulator() {
            return manipulator;
        }

        private IMotionReciver getMotionReciver(int d) {
            IMotionReciver motionReciver = null;
            switch (d) {
                case 0:
                    motionReciver = environment.getModelsMotionsReciver();
                    break;
                case 1:
                    motionReciver = (IMotionReciver) getManipulator();
                    break;
            }
            return motionReciver;
        }

        public boolean keyPress(int d, int key, int[] arg) {

            IExecuter[] deviceExecuters = executers[d];
            if (deviceExecuters.length > key) {
                IExecuter exec = deviceExecuters[key];

                if (exec == null) {
                    Tools.UnsupportedOperationException();
                }
                IMotionReciver motionReciver = getMotionReciver(d);

                while (exec != null) {

                    if (!MovementsFactory.isNothing(exec)) {

                        if (exec.canOccupy()) {

                            IMotion _m = exec.CreateMotion(environment.ArgToCoor(arg));

                            if (_m != null) {
                                motionReciver.AddMotions(_m);
                            }
                        }
                        exec = exec.getNext();
                    }
                }
                motionReciver.Resume();
                return true;
            }
            return false;
        }

        public void keyRelease(int d, int key, int[] arg) {

            IExecuter[] deviceExecuters = executers[d];
            if (deviceExecuters.length > key) {
                IExecuter exec = deviceExecuters[key];

                if (exec == null) {
                    Tools.UnsupportedOperationException();
                }

                IMotionReciver motionReciver = getMotionReciver(d);

                while (exec != null) {

                    if (!MovementsFactory.isNothing(exec)) {

                        exec.release();

                        IMotion _m = exec.StopMotion(environment.ArgToCoor(arg));

                        if (_m != null) {
                            motionReciver.AddMotions(_m);
                        }
                    }
                    exec = exec.getNext();
                }
                motionReciver.Resume();
            }
        }
    }
}
