/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Collaborations.MVC.Control;

import Application.Build.Panels.Control.Manipulator.IManipulator.IAction;
import MVC.Control.IControler.Ready;
import MVC.Control.IKeysMap;
import MVC.Control.IMenuElement;
import Application.ILoger;
import Model.Geometry.UnitPoint;
import Control.KeysID;
import Platform.Core.ISystemM;
import Platform.DataStructers.ILinkedElement;
import Platform.DataStructers.ISelection;
import Model.Geometry.D2.Coordinates2d;
import MVC.Model.IDomainModel;
import MVC.View.IViewSource;
import Platform.Core.IMotionsProducer;
import Platform.Core.IArgument;
import Platform.Core.IMotion;
import Platform.Core.ISystemMContainer;
import Platform.Core.Motion.Motion;
import Platform.Core.Unit.IUnit;
import Platform.DataStructers.IFilter;
import Platform.DataStructers.ILinkedList;
import Platform.DataStructers.LinkedList;
import Game.Model.Game.World.Interaction.ICollisionMap;
import Logic.Model.IDataSource;
import Logic.Reflections.Projections.IImaginator;
import MVC.Control.MovementsProducers.MenuCommands.IProducers.Enter;
import MVC.Control.MovementsProducers.MenuCommands.IProducers.Esc;
import MVC.Control.MovementsProducers.MenuCommands.IProducers.NextCommand;
import MVC.Control.MovementsProducers.MenuCommands.IProducers.PreviosCommand;
import Model.Models.Scenarios.Actions.ActionScenario;
import Platform.Core.IMotionReciver;
import Platform.Core.Systems.SystemM;
import Platform.Core.Unit.IUnit.drawable;
import Platform.Core.Unit.IUnit.drawable.projection;
import Platform.DataStructers.IContainer;
import Render.Graphics.Geometry.ICoordinates;
import View.Presenter.Projections.IViewUnit;
import View.Presenter.Projections.IViewUnit.imaginated;

/**
 *
 * @author kalinin
 */
public abstract class Menu extends MenuStructure implements IDomainModel, IDomainModel.withUnits, IDomainModel.Imaginated {

    protected abstract void SetUnits();

    interface MenuCommands extends IMotionsProducer, NextCommand, PreviosCommand, Enter, Esc {
    }
    protected IMotionReciver reciver;
    protected Command focusedCommmand;
    private ILinkedList unitsList;
    private UnitsListManager unitsListManager;
    private ProjectionsListManager projectionsManager;

    public Menu(IMotionReciver reciver) {
        this.reciver = reciver;
        viewVector = new ICoordinates[]{new Coordinates2d(), new Coordinates2d()};

        unitsList = new LinkedList();
        unitsListManager = new UnitsListManager();
        projectionInfo = new LinkedList();


    }

    private Command getFocusedCommmand() {

        return focusedCommmand;
    }

    public withUnits createUnits(IDataSource dataSource) {

        SetUnits();

        ISelection selection = unitsList.select();
        {
            int i = 0;
            while (selection.getNext()) {
                ((IUnit) selection.getCurrent()).setIndex(i);
                i++;
            }
        }
        return this;
    }

    public Imaginated imaginateUnits(IViewSource viewSource) {

        viewSource.imaginate(getViewUnitList());
        return this;
    }

    public void Start(IDataSource dataSource_, IViewSource viewSource_) {
    }

    public ICollisionMap getCollisionMap() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected UnitsListManager units() {
        return unitsListManager;
    }

    public controllerSource getControllerSource() {
        return new controllerSource() {

            public IKeysMap getKeysMap() {

                return new IKeysMap() {

                    public String getName(int code) {
                        if (code == 65) {
                            return "NextCommand";
                        }

                        if (code == 68) {
                            return "PreviosCommand";
                        }

                        if (code == 87) {
                            return "PreviosCommand";
                        }

                        if (code == 83) {
                            return "NextCommand";
                        }

                        if (code == KeysID.Enter) {
                            return "Enter";
                        }

                        if (code == KeysID.Esc) {
                            return "Esc";
                        }
                        return null;
                    }
                };
            }

            public IMotionsProducer getControlMotionsProducer() {
                return new MenuCommands() {

                    public IMotion CreateMotionNextCommand(Object[] arg) {
                        return new MotionTransportateFocus(1) {//, ((Coordinates2d) nextc.position).x, ((Coordinates2d) nextc.position).y) {

                            public void MotionMethod() {
                                super.MotionMethod();
                                getFocusedCommmand().PreSetFocus(getFocusedCommmand().nextc);
                                if (isFinal()) {
                                    SetFocus(getFocusedCommmand().nextc);
                                }
                            }
                        };
                    }

                    public IMotion CreateMotionPreviosCommand(Object[] arg) {
                        return new MotionTransportateFocus(1) {//, ((Coordinates2d) prevc.position).x, ((Coordinates2d) prevc.position).y) {

                            public void MotionMethod() {
                                super.MotionMethod();
                                getFocusedCommmand().PreSetFocus(getFocusedCommmand().prevc);
                                if (isFinal()) {
                                    SetFocus(getFocusedCommmand().prevc);
                                }
                            }
                        };
                    }

                    public IMotion CreateMotionEnter(Object[] arg) {
                        return new Motion(1, "Enter") {

                            public void MotionMethod() {
                                Command focusedCommmand = getFocusedCommmand();
                                focusedCommmand.PreSetFocus(null);
                                SetFocus(null);
                                focusedCommmand.Enter();
                            }
                        };
                    }

                    public IMotion StopMotionNextCommand(Object[] arg) {
                        return null;
                    }

                    public IMotion StopMotionPreviosCommand(Object[] arg) {
                        return null;
                    }

                    public IMotion StopMotionEsc(Object[] arg) {
                        return null;
                    }

                    public IMotion StopMotionEnter(Object[] arg) {
                        return null;
                    }

                    public IMotion CreateMotionEsc(Object[] arg) {
                        return new Motion(1, "not") {

                            public void MotionMethod() {
                            }
                        };
                    }
                };
            }

            public IAction getAction() {
                return ActionScenario.Blank();

            }
        };
    }

    public void SetFocus(Command c_) {

        focusedCommmand = c_;
    }

    public int GetViewOffset() {
        return 0;
    }

    public ICoordinates[] GetViewVector() {
        return viewVector;
    }

    public IArgument[] one() {
        return one;
    }

    public IArgument[] zero() {
        return zero;
    }

    public class MotionTransportateFocus extends Motion {

        int[] x;
        int[] y;

        public MotionTransportateFocus(int iterates_) {//, int x_, int y_) {
            super(iterates_, "transoptate focus");
            x = new int[iterates_];
            y = new int[iterates_];
//            float xx = x_ - ((Coordinates2d) ((Unit2d) units[IParametr.FocusId]).position).x;
//            float yy = y_ - ((Coordinates2d) ((Unit2d) units[IParametr.FocusId]).position).y;
//
//            for (int i = 0; i < iterates_; i++) {
//                float k = ((float) (i + 1)) / (float) iterates_;
//                x[iterates_ - i - 1] = Math.round((float) ((((Coordinates2d) ((Unit2d) units[IParametr.FocusId]).position).x) + xx * k));
//                y[iterates_ - i - 1] = Math.round((float) ((((Coordinates2d) ((Unit2d) units[IParametr.FocusId]).position).y) + yy * k));
//            }

        }

        public void MotionMethod() {
            //   ((Unit2d) units[IParametr.FocusId]).Set_(x[counter - 1], y[counter - 1]);
        }
    }

    public IUnit.drawable.projection[] activeProjections() {

        IContainer[] containers = unitsList.math(new IFilter() {

            public boolean suit(ILinkedElement e) {

                return ((Command) e).isVisible();
            }
        });

        projection[] result = new projection[containers.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = (projection) containers[i];
        }

        return result;
    }

    public IViewUnit.list getViewUnitList() {
        return getImaginatedProjectionsList().asViewUnitslist();
    }

    public ProjectionsListManager getImaginatedProjectionsList() {

        if (projectionsManager == null) {
            projectionsManager = new ProjectionsListManager();
        }
        return projectionsManager;
    }

    public UnitsListManager getUnitsListManager() {

        return unitsListManager;
    }

    public Ready getReadyControler() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void updateUnitsPositions() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected class UnitsListManager implements IUnit.list {

        public IUnit add(IUnit e) {
            return (IUnit) unitsList.add((ILinkedElement) e);
        }

        public selection select() {
            return new selection() {

                ISelection select = unitsList.select();

                public boolean end() {

                    return !select.getNext();
                }

                public IUnit get() {
                    return (IUnit) select.getCurrent();
                }
            };
        }

        public IViewUnit.list asViewUnitslist() {
            return new IViewUnit.list() {

                public IViewUnit add(IViewUnit e) {
                    return (IViewUnit) unitsList.add((ILinkedElement) e);
                }

                public selection select() {
                    return new IViewUnit.list.selection() {

                        ISelection select = unitsList.select();

                        public boolean end() {

                            return !select.getNext();
                        }

                        public IViewUnit get() {
                            return (IViewUnit) select.getCurrent();
                        }
                    };
                }
            };
        }
    }

    protected class ProjectionsListManager implements IUnit.drawable.projection.list {

        public IUnit.drawable.projection.list.selection select() {
            return new IUnit.drawable.projection.list.selection() {

                ISelection select = unitsList.select();

                public boolean end() {

                    return !select.getNext();
                }

                public IUnit.drawable.projection get() {
                    return (IUnit.drawable.projection) select.getCurrent();
                }
            };
        }

        public int size() {
            return unitsList.size();
        }

        public IViewUnit.list asViewUnitslist() {
            return new IViewUnit.list() {

                public ILinkedList getList() {
                    return unitsList;
                }

                public IViewUnit add(IViewUnit e) {
                    return (IViewUnit) unitsList.add((ILinkedElement) e);
                }

                public selection select() {
                    return new IViewUnit.list.selection() {

                        ISelection select = unitsList.select();

                        public boolean end() {

                            return !select.getNext();
                        }

                        public IViewUnit get() {
                            return (IViewUnit) select.getCurrent();
                        }
                    };
                }
            };
        }
    }

    protected class SystemModelMenu extends SystemM {

        public SystemModelMenu(String sysName_) {
            super(sysName_);
        }

        public String SetThreadName() {
            return Menu.this.SetThreadName();
        }
    }

    protected abstract class Command extends UnitPoint implements IMenuElement, drawable, IViewUnit.imaginated, IContainer {

        protected abstract void Enter();
        
        private boolean visible = false;
        private IImaginator.charged imaginator;
        Command prevc;
        Command nextc;

        protected void PreSetFocus(Command c_) {
        }

        public void LinkTo(Command c_) {
            nextc = c_;
            c_.prevc = this;
        }

        public ICoordinates GetPosition() {
            return getPosition();
        }

        public boolean isVisible() {
            return visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }

        public states getViewState() {
            if (this == focusedCommmand) {
                return IViewUnit.states.zero;
            } else {
                return IViewUnit.states.one;
            }
        }

        public IViewUnit.imaginated setImaginator(IImaginator.charged imaginator) {
            this.imaginator = imaginator;
            return this;
        }

        public IImaginator.charged getImaginator() {
            return imaginator;
        }

        public imaginated asImaginated() {
            return this;
        }
    }
}
