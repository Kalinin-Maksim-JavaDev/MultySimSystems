/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import Builds.PanelsFactoryJupmer;
import Application.Build.Panels.Model.IInteractvePanel.Window;
import Application.Consrtuction.IApplicationBuilder;
import Application.Consrtuction.IConfiguration;
import Application.Control.IInputOwner;
import Application.DEBUG.MapPainter;
import Application.View.IScreen;
import Application.Build.Control.Command.IComandsDispatching;
import Application.Build.Control.Command.ICommandListsFactory;
import Application.Build.Control.Command.ICommandMediator;
import Application.Build.Control.VirtualPad.IVitrualPad;
import Application.Build.Control.VirtualPad.IVrtualPadMotionsReciver;
import Application.CommandInterface.CommandListsFactory;
import Application.CommandInterface.ICommand;
import Application.CommandInterface.Strategy.SerialComandsDispatching;
import Application.Consrtuction.Configurations.DispatchingType;
import Application.Consrtuction.Configurations.GraphicType;
import Application.Consrtuction.Configurations.Storys;
import Application.Panels.IPanelsFactory;
import MVC.Control.IInputCollector;
import MVC.Control.IInputReciver;
import Controler.InputReciver;
import Controler.VPad.VitrualJoystick;
import Game.Model.Game.World.Interaction.IMapPainter;
import Logic.Environment.Menu.MainAnimMenu.IMenuCommonCommandsList.Main;
import Platform.Core.Motion.Motion;
import Platform.Util.Random;
import View.SpaceManager;
import View.PictureQueue;
import java.util.logging.Logger;

/**
 *
 * @author kalinin
 */
public class Builder implements IApplicationBuilder {

    private boolean oneThread = false;
    private int dx = 120;

    static public IApplicationBuilder with(IConfiguration configuration) {
        return new Builder(configuration);
    }
    IConfiguration configuration;
    Application appl;
    SpaceManager spaceManager;
    private Factory factory = new Factory();

    public Builder(IConfiguration configuration) {

        this.configuration = configuration;
    }

    public IApplication createApplication() {

        IScreen screen = configuration.getScreen().prepare();
        IInputOwner inputOwner = configuration.getInputOwner();

        Manager manager = factory.createPanelsStackManager(screen);

        spaceManager = factory.Create(screen, configuration.getGraphicType());
        IComandsDispatching commandDispatching = factory.createStrategyDispatching(configuration.getCommandsDispatchingType(), manager.getCommandsReciver());
        ICommandListsFactory ñommandListsFactory = factory.createCommandListsFactory(commandDispatching);

        IPanelsFactory panelsFactory = factory.createPanelsFactory(configuration.getStory(), oneThread);
        IPanelsFactory.Ready factoryComplited = panelsFactory.setGameCommonCommandsList(ñommandListsFactory.createGameCommonCommandsList()).setMenuCommonCommandsList(ñommandListsFactory.createMenuCommonCommandsList()).finish();
        IVitrualPad pad = new VitrualJoystick(screen.getWidth(), screen.getHeight(), manager.getVrtualPadMotionsReciver());
        appl = new Application(screen, inputOwner, manager, pad);

        manager.bind(factoryComplited, appl);


        return appl;
    }

    class Factory {

        IComandsDispatching createStrategyDispatching(DispatchingType type, ICommandMediator.commandsReciver commandsReciver) {
            if (type == DispatchingType.Synchrone) {
                return new SerialComandsDispatching(commandsReciver) {

                    @Override
                    public void StartNewGame() {
                        super.StartNewGame();
                        new DEBUG().runMadMonkey();
                    }

                    class DEBUG {

                        public IInputCollector getInputCollector() {
                            return (IInputCollector) getInputCollector();
                        }

                        protected void runMadMonkey() {
                            new Thread(new Runnable() {

                                public void run() {
                                    while (true) {
                                        int[] arg = new int[4];
                                        getInputCollector().addPressedKey(1, 27, arg);
                                        Pause();
                                        getInputCollector().addReleasedKey(1, 27, arg);
                                        Pause();
                                    }
                                }

                                public void Pause() {
                                    try {
                                        Thread.sleep((new Random()).nextInt(50), 0);
                                    } catch (InterruptedException ex) {
                                        Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                                    }
                                }
                            }, "MadMonkey"); //.start();
                        }
                    }
                };
            }
            return null;
        }

        ICommandListsFactory createCommandListsFactory(ICommandMediator.commandsProxy proxy) {

            return new CommandListsFactory(proxy) {

                /*@Override
                public IPanelsStack getPanelsStack() {
                return connector.getPanelsStack();
                }*/
            };
        }

        Manager createPanelsStackManager(IScreen screen) {

            return new Manager(factory.createPictureQueue(screen.getGraphicTool()));
        }

        PictureQueue createPictureQueue(Object tool) {

            return new PictureQueue(tool) {

                @Override
                protected void onDraw(boolean b) {

                    appl.onDraw(b);
                }

                @Override
                protected Object getPictureList(int layerID) {
                    return spaceManager.getPictureList(layerID).get();
                }
            };
        }

        IPanelsFactory createPanelsFactory(Storys story, boolean oneThread) {
            if (story == Storys.Jupmer) {
                return new PanelsFactoryJupmer(oneThread, spaceManager, dx);
            }
            return null;
        }

        IInputReciver createInputReciver() {

            return new InputReciver() {

                @Override
                protected IInputCollector getInputCollector() {
                    return appl.getInputCollector();
                }

                @Override
                protected IVrtualPadMotionsReciver getVrtualPadMotionsReciver() {
                    return appl.getVrtualPadMotionsReciver();
                }
            };
        }

        public SpaceManager Create(final IScreen screen, GraphicType _GRAPHIC) {

            return new SpaceManager(screen.getWidth(), screen.getHeight()) {

                @Override
                protected void onRender(int layer) {
                    appl.update(layer);
                }
            };
        }
    }

    private class Application implements IApplication, IApplication.events {

        private IScreen screen;
        private IInputOwner inputOwner;
        private Manager manager;
        private IMapPainter mapPainter;
        private IVitrualPad pad;

        public Application(IScreen screen, IInputOwner inputOwner, Manager manager, IVitrualPad pad) {
            this.screen = screen;
            this.inputOwner = inputOwner;
            this.manager = manager;
            this.pad = pad;
        }

        public void run() {
            manager.StartFromMainMenu(new ICommand() {

                public void perform() {
                    manager.unwanted_AddMotions(new Motion(1, "Listen") {

                        @Override
                        public void MotionMethod() {
                            Window currentPanel = manager.getCurrentPanel();
                            pad.setInput(currentPanel.getInputCollector());
                            IInputReciver inputReciver = factory.createInputReciver();
                            inputOwner.prepare(inputReciver.setPad(pad));
                            inputOwner.Listen();
                        }
                    });
                }
            });
        }

        public void onExit() {
            mapPainter.close();
        }

        public void onGameStarted() {
            mapPainter = new MapPainter("MapPainter") {

                @Override
                public Object createImage() {
                    return screen.createImage_(screen.getWidth(), screen.getHeight());
                }

                @Override
                public Object getGraphics() {
                    return screen.getGraphics_();
                }

                @Override
                protected Object[][] getMap() {
                    return manager.getMap();
                }
            };
            mapPainter.go();
        }

        private void update(int layer) {
            manager.update(layer);
        }

        public void onDraw(boolean b) {

            screen.update(b);
        }

        private IInputCollector getInputCollector() {
            return manager.getCurrentPanel().getInputCollector();
        }

        private IVrtualPadMotionsReciver getVrtualPadMotionsReciver() {
            return manager.getVrtualPadMotionsReciver();
        }
    }
}
