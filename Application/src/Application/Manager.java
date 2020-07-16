/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import Application.Build.Control.Command.ICommandMediator;
import Application.Build.Control.VirtualPad.IVrtualPadMotionsReciver;
import Application.Build.IRankedWorkersManager;
import Application.IApplication.events;
import Application.Panels.IPanelsFactory;
import Logic.IGameData;
import Application.CommandInterface.ICommand;
import Control.KeysID;
import Application.Panels.IPanelsManager;
import MVC.Control.IInputCollector;
import Application.Build.Panels.Model.IInteractvePanel;
import Application.Build.Panels.Model.IInteractvePanel.Window;
import Application.Build.Panels.Model.IInteractvePanel.Window.Graphical;
import Logic.Model.IDataSource;
import Platform.Core.IMotion;
import Platform.Util.Profiler.ClassAssociations;
import Render.View.IWorksQueue;
import View.RankedWorkersManager;

/**
 *
 * @author kalinin
 */
public class Manager implements IPanelsManager, IPanelsManager.binded, ICommandMediator, ICommandMediator.commandsReciver {

    private IApplication.events applEvents;
    protected IPanelsFactory.Ready factory;
    protected IInputCollector eventsCollector;
    private IRankedWorkersManager rankedWorkersManager;
    private IInteractvePanel.Window currentPanel;
    private IInteractvePanel.Window mainMenu;
    private IInteractvePanel.Window gameMenu;
    private IInteractvePanel.Window saveDialog;
    private IInteractvePanel.Window loadDialog;
    private IInteractvePanel.Window.Graphical game;

    public Manager(IWorksQueue worksQueue) {
        ClassAssociations.PrintAssociaties();
        //this.layersManager = layersManager;
        rankedWorkersManager = new RankedWorkersManager(4);
        rankedWorkersManager.setWorksQueue(worksQueue);
    }
   
    public IPanelsManager.binded bind(IPanelsFactory.Ready factory,IApplication.events applEvents){
        this.factory = factory;
        this.applEvents = applEvents;
        return this;
    }

    public IVrtualPadMotionsReciver getVrtualPadMotionsReciver() {
        return rankedWorkersManager.getVrtualPadMotionsReciver();
    }

    public void reset(int layer) {
        rankedWorkersManager.resetWorker(layer);
    }

    public void close() {
        rankedWorkersManager.close();
    }

    public void LoadGame(int slot) {

        ReturnToGameFrom(loadDialog);
        int[] arg = new int[4];
        arg[0] = slot;
        eventsCollector.addPressedKey(0, KeysID.F5, arg);
        eventsCollector.addReleasedKey(0, KeysID.F5, arg);
    }

    public void SaveGame(int slot) {
        ReturnToGameFrom(saveDialog);
        int[] arg = new int[4];
        arg[0] = slot;
        eventsCollector.addPressedKey(0, KeysID.F5, arg);
        eventsCollector.addReleasedKey(0, KeysID.F5, arg);
    }

    public void ReturnToGame() {
        ReturnToGameFrom(gameMenu);
    }

    public void StartNewGame() {
        reset(mainMenu.getLayer());
        mainMenu.Stop();
        mainMenu = null;
        GS();

        StopGame();



        IDataSource dataSource = factory.CreateDataSource();
        StartGame((Graphical) factory.CreateGame((IGameData) dataSource.GetData()).start(dataSource));
        // LoadGame(0);
    }

    public void OpenGameMenu() {
        setCurrentPanel(gameMenu);
        display(gameMenu.getLayer());
        //  System.out.println("gameMenu Resume");
        gameMenu.Resume();
    }

    public void EnterToMainMenu(ICommand calling) {

        mainMenu = factory.CreateMainMenu().start().setCurrentFor(this);
        display(mainMenu.getLayer());
        calling.perform();

    }

    public IInteractvePanel.Window StartFromMainMenu(ICommand calling) {

        EnterToMainMenu(calling);
        return getCurrentPanel();
    }

    public void ExitToMainMenuFromGame() {

        StopGame();
        ExitToMainMenuFromInternalMenu();
    }

    public void ExitToMainMenuFromGame(ICommand calling) {
        ExitToMainMenuFromGame();
        calling.perform();
    }

    public void OpenSaveDialog() {
        setCurrentPanel(saveDialog);
        display(saveDialog.getLayer());
        saveDialog.Resume();
        doNotDisplay(gameMenu.getLayer());
        gameMenu.Pause();
    }

    public void OpenLoadDialog() {
        setCurrentPanel(loadDialog);
        display(loadDialog.getLayer());
        loadDialog.Resume();
        doNotDisplay(gameMenu.getLayer());
        gameMenu.Pause();
    }

    public void ExitToSystem() {

        StopGame();
        mainMenu.Stop();
        GS();

        applEvents.onExit();
    }

    public IRankedWorkersManager getRankedWorkersManager() {
        return rankedWorkersManager;
    }

    public void update(int layer) {
        rankedWorkersManager.OnWorkerIsReady(layer);
    }

    public void display(int layer) {
        rankedWorkersManager.enableWorker(layer);
    }

    public void doNotDisplay(int layer) {
        rankedWorkersManager.disableWorker(layer);
    }

    public ICommandMediator getCommandPerformer() {
        return this;
    }

    public commandsReciver getCommandsReciver() {
        return this;
    }

    public commandsProxy getÑommandsProxy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object[][] getMap() {
        return game.DEBUG_getMap();
    }

    public void ExitToMainMenuFromInternalMenu() {
        gameMenu.Stop();
        setCurrentPanel(mainMenu);
    }

    public void StartGame(Graphical _game) {

        this.game = _game;

        // reflectionManager.display(game.getLayer());

        gameMenu = factory.CreateGameMenu().start();
        doNotDisplay(gameMenu.getLayer());
        gameMenu.PauseResume();

        saveDialog = factory.CreateSaveDialog().start();
        doNotDisplay(saveDialog.getLayer());
        saveDialog.PauseResume();

        loadDialog = factory.CreateLoadDialog().start();
        doNotDisplay(loadDialog.getLayer());
        loadDialog.PauseResume();

        applEvents.onGameStarted();
    }

    public void StopGame() {

        if (game != null) {
            close();

            gameMenu.Pause();
            saveDialog.Pause();
            loadDialog.Pause();
            //  game.PauseResume();

            gameMenu.Stop();
            saveDialog.Stop();
            loadDialog.Stop();
            game.Stop();


            saveDialog = null;
            loadDialog = null;
            gameMenu = null;
            game = null;
            GS();

        }
    }

    public void ReturnToGameFrom(Window panel) {
        int[] arg = new int[4];
        arg[0] = 1;//resume calculate in Game.mod        
        eventsCollector.addPressedKey(0, KeysID.Esc, arg);
        eventsCollector.addReleasedKey(0, KeysID.Esc, arg);
        //System.out.println("gameMenu Pause");

        doNotDisplay(panel.getLayer());
        panel.Pause();

        setCurrentPanel(game);
    }

    private void GS() {
        new Thread("Init GS") {

            int n = 5;

            public void run() {
                while (n-- >= 0) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    System.gc();
                }
            }
        }.start();

    }

    public Window getCurrentPanel() {
        return currentPanel;
    }

    public void setCurrentPanel(IInteractvePanel.Window currentForm) {

        this.currentPanel = currentForm;
    }

    public void unwanted_AddMotions(IMotion motion) {
        mainMenu.AddMotions(motion);
    }
}
