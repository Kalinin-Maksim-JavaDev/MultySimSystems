/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Models.Scenarios;

import Game.Model.Game.World.Interaction.ICollisionMap;
import Model.Models.Scenarios.Acts.ScenarioAct;
import Model.Models.Scenarios.Acts.FolowMainStrategy;
import Logic.Model.Game.IGame;
import Logic.Model.Game.IWaponScenario;
import Logic.Model.Game.World.IAgent;
import Logic.Model.IWaponGame;
import Logic.Model.Scenarios.IScenario;
import Platform.Concurrent.Factory;
import Platform.Concurrent.ILathce;
import Platform.DataStructers.ISelection;
import Platform.DataStructers.LinkedContainers;
import Platform.DataStructers.LinkedList;
import Platform.Util.Area.Area;

/**
 *
 * @author kalinin
 */
public class Hunt extends SystemMScenario implements IWaponScenario {

    public ILathce ballLanding;
    public ILathce granateExplouse;
    private LinkedList agentsList;
    private IGame.unit target;
    private IGame game;
    private Area finalarea;
    IWaponGame waponSystem;

    public Hunt(ICollisionMap iMap, IGame game, LinkedContainers agentsList, IGame.unit target, Area finalarea, IWaponGame waponSystem) {
        super(iMap, game.getPathMotionReciver(), game.getRatio(), game.getLogCanvas());
        ballLanding = Factory.createLathce(false, true);
        granateExplouse = Factory.createLathce(true, true);
        this.agentsList = agentsList;
        this.target = target;
        this.game = game;
        this.finalarea = finalarea;
        this.waponSystem = waponSystem;
    }

    public ScenarioAct createMainAct() {
//            int fcount = 0;
//            GroupUnit c_ = blockGroupFundament.lastUnit.next;
//            do {
//                fcount++;
//                c_ = c_.next;
//            } while (c_ != blockGroupFundament.lastUnit);
//            GroupUnit fund[] = new GroupUnit[fcount];
//
//            fcount = 0;
//            c_ = blockGroupFundament.lastUnit.next;
//            do {
//                fund[fcount++] = c_;
//                c_ = c_.next;
//            } while (c_ != blockGroupFundament.lastUnit);



        IAgent agents[] = new IAgent[agentsList.size()];
        int index = 0;

        ISelection selection = agentsList.select();
        while (selection.getNext()){
            agents[index++] = (IAgent) selection.getCurrent();

        }

        return new FolowMainStrategy(game, agents, (IScenario) this);

    }

    public void OnStop() {
        ballLanding.synchronizedRelease();
        ILathce granateExplouse_ = granateExplouse;
        granateExplouse = Factory.createLathce(false, false);
        granateExplouse_.synchronizedRelease();
        getPathGuid().Stop();
    }

    public String SetThreadName() {
        return "scenario";
    }

    public boolean find(int x1, int y1, int x2, int y2) {
        boolean find = true;
        for (int i = 0; i < finalarea.size(); i++) {
            find = !Platform.Util.Area.PolygonSimple.Transection(4 * x1, 4 * y1, 4 * x2, 4 * y2, finalarea.getPolygon(i)) && find;
        }
        return find;
    }

    public IGame.unit getHero() {
        return target;
    }

    public IWaponGame getWaponSystem() {
        return waponSystem;
    }
}
