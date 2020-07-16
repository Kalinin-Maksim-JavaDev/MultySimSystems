/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Builds;

import Logic.IGameData;
import Logic.Environment.Menu.MainAnimMenu.IMenuCommonCommandsList.Main;
import Application.Build.Panels.Model.IInteractvePanel;
import Model.Game.GParametr;
import Platform.Util.File;
import Presenter.Platformer.JParametr;
import Builds.Panels.Dialogs.PanelGlobalMenu;
import Builds.Panels.Dialogs.InternalMenu;
import Builds.Panels.Dialogs.Load;
import Builds.Panels.Dialogs.Save;
import Builds.Panels.Levels.InteractvePanelJumper;
import Model.Game.GameFabric;
import Model.DataSourceFromBinaryData;
import Application.Panels.IPanelsFactory;
import Application.View.ISpaceManager;
import Logic.Environment.Menu.MainAnimMenu.IMenuCommonCommandsList;
import Logic.Environment.Menu.MainAnimMenu.IMenuCommonCommandsList.Internal;
import Logic.IGameCommonCommandsList;
import Logic.Model.IDataSource;

/**
 *
 * @author User
 */
public class PanelsFactoryJupmer implements IPanelsFactory {

    private ISpaceManager spaceManager;
    private int dx;
    private boolean oneThread;
    private IMenuCommonCommandsList globalMenuCommandList;
    private IGameCommonCommandsList gameMenuCommandList;

    public PanelsFactoryJupmer(boolean oneThread, ISpaceManager spaceManager, int dx) {
        this.oneThread = oneThread;
        this.spaceManager = spaceManager;
        this.dx = dx;
    }

    public IPanelsFactory.Ready finish() {
        return new IPanelsFactory.Ready() {

            public IDataSource CreateDataSource(byte[] Data) {
                return new DataSourceFromBinaryData(Data);
            }

            public IDataSource CreateDataSource() {
                return new DataSourceFromBinaryData(getDefData());
            }

            public IInteractvePanel CreateMainMenu() {

                return new PanelGlobalMenu(oneThread, spaceManager, dx) {

                    public void AfterModRun() {
                        throw new UnsupportedOperationException("Not supported yet.");
                    }

                    @Override
                    protected IMenuCommonCommandsList.Main getCommandList() {

                        return (Main) globalMenuCommandList;
                    }
                };

            }

            public IInteractvePanel CreateGame(IGameData data) {

                return new InteractvePanelJumper(data.getDecorations(), oneThread, spaceManager) {

                    public int getLayer() {
                        return 0;
                    }

                    @Override
                    protected IGameCommonCommandsList getCommandsList() {
                        return gameMenuCommandList;
                    }
                };
            }

            public IInteractvePanel CreateGameMenu() {
                return new InternalMenu(oneThread, spaceManager) {

                    public int getLayer() {
                        return 1;
                    }

                    @Override
                    protected IMenuCommonCommandsList.Internal getInternalCommandsList() {

                        return (Internal) globalMenuCommandList;
                    }
                };
            }

            public IInteractvePanel CreateSaveDialog() {
                return new Save(oneThread, spaceManager) {

                    public int getLayer() {
                        return 2;
                    }

                    @Override
                    protected IMenuCommonCommandsList.Internal getInternalCommandsList() {

                        return (Internal) globalMenuCommandList;
                    }
                };
            }

            public IInteractvePanel CreateLoadDialog() {
                return new Load(oneThread, spaceManager) {

                    public int getLayer() {
                        return 3;
                    }

                    @Override
                    protected IMenuCommonCommandsList.Internal getInternalCommandsList() {

                        return (Internal) globalMenuCommandList;
                    }
                };
            }
        };
    }

    public IPanelsFactory setGameCommonCommandsList(IGameCommonCommandsList list) {

        this.gameMenuCommandList = list;
        return this;
    }

    public IPanelsFactory setMenuCommonCommandsList(IMenuCommonCommandsList list) {

        this.globalMenuCommandList = list;
        return this;
    }

    public byte[] getDefData() {

        byte[] arg = new byte[7];
        arg[0] = (byte) GameFabric.unitsType.fundament2.getId();
        arg[1] = (byte) GameFabric.unitsType.fundament.getId();
        arg[2] = (byte) GameFabric.unitsType.jumper.getId();
        arg[3] = (byte) GameFabric.unitsType.ball.getId();
        arg[4] = 4;
        arg[5] = 6;
        String file = "/Platformer/map.png";
        String st = "/Platformer/mapPhys.png";
        byte[][] gameMap = File.LoadGameMap(file, GParametr.w, GParametr.h, arg);
        byte[][] decorations = File.LoadGameMap(st, JParametr.w, JParametr.h, arg);
        byte data[] = new byte[gameMap.length * gameMap[0].length + decorations.length * decorations[0].length];

        int k = 0;
        for (int j = 0; j < gameMap.length; j++) {
            for (int i = 0; i < gameMap[j].length; i++) {
                data[k++] = gameMap[j][i];
            }
        }
        for (int j = 0; j < decorations.length; j++) {
            for (int i = 0; i < decorations[j].length; i++) {
                data[k++] = decorations[j][i];
            }
        }

        return data;

    }
}
