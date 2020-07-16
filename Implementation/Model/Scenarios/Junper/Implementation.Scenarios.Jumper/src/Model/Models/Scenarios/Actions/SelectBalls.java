/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Models.Scenarios.Actions;

import Controler.Coordinates2dArgument;
import Logic.Model.Game.IGame;
import Logic.Model.Game.World.IWalker;
import Logic.Model.Scenarios.IScenario;
import Logic.Model.Scenarios.IScenarioMotionReciver;
import Platform.Core.IArgument;
import Platform.DataStructers.ISelection;
import Platform.DataStructers.LinkedList;

/**
 *
 * @author kalinin
 */
public class SelectBalls extends ActionScenario {

    public int countballs;
    LinkedList balls;

    public SelectBalls(int grad, IScenario scenario, IScenarioMotionReciver motionsReciver, LinkedList balls) {
        super(grad,scenario ,motionsReciver);
        this.scenario = scenario;
        this.balls = balls;
    }

    public void Do(IArgument[] arg_) {
        arg_[1] = arg_[0];
        Selecting(arg_);
        if (countballs > 0) {
            Select(arg_);
        }
    }

    public void Abort(IArgument[] arg_) {
    }

    public void Select(IArgument[] arg_) {
        manipulator.bindAction(new TargetingBalls(balls, grad, scenario,scenario.getMotionReciver()));
        System.out.println("Targeting/");
    }

    public void Selecting(IArgument[] arg_) {
        int x1 = (int) (((Coordinates2dArgument) arg_[0]).x / grad);
        int y1 = (int) (((Coordinates2dArgument) arg_[0]).y / grad);
        int x2 = (int) (((Coordinates2dArgument) arg_[1]).x / grad);
        int y2 = (int) (((Coordinates2dArgument) arg_[1]).y / grad);
        //    System.out.println(x1+", "+y1+ "  -   "+x2+", "+y2);
        if (x1 > x2) {
            int x_ = x1;
            x1 = x2;
            x2 = x_;
        }
        if (y1 > y2) {
            int y_ = y1;
            y1 = y2;
            y2 = y_;
        }
        countballs = 0;
        ISelection selection = balls.select();

        while (selection.getNext()) {
            IGame.unit unit = (IGame.unit) selection.getCurrent();
            IWalker walker = unit.GetWalker();
            unit.GetWalker().setSelected((walker.GetIntX() >= x1) && (unit.GetWalker().GetIntX() <= x2) && (unit.GetWalker().GetIntY() >= y1) && (unit.GetWalker().GetIntY() <= y2));
            if (unit.GetWalker().isSelected()) {
                countballs++;
            }
        }
    }

    public void Move(IArgument[] arg_) {
    }
}
