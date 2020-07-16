/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler.Menu.SaveLoadDialog;

import Collaborations.MVC.Control.Menu;
import Logic.Environment.Menu.MainAnimMenu.IMenuCommonCommandsList;
import Platform.Core.Motion.Motion;
import Platform.Core.IMotion;
import Global.Tools;
import Platform.Core.IMotionReciver;

/**
 *
 * @author kalinin
 */
public abstract class SaveLoadDialog extends Menu {

    abstract protected IMenuCommonCommandsList.Internal getCommandList();
    public static int Save = 1;
    public static int Load = 2;
    int mod;

    public SaveLoadDialog(IMotionReciver reciver, int mod_) {
        super(reciver);
        mod = mod_;


    }

    public String SetThreadName() {
        return "SaveDialogMOD";
    }

    public void SetUnits() {
        ISLDParametr.Slot0 = units().add(new CommandSlot(0));
        ISLDParametr.Slot1 = units().add(new CommandSlot(1));
        ISLDParametr.Slot2 = units().add(new CommandSlot(2));
        ISLDParametr.Slot3 = units().add(new CommandSlot(3));
        ISLDParametr.Slot4 = units().add(new CommandSlot(4));
        ISLDParametr.AmbientIndex = units().add(new Command() {

            public void Enter() {

                Tools.UnsupportedOperationException();
            }

            public IMotion StopMotionEsc(Object[] arg) {

                Tools.UnsupportedOperationException();
                return null;

            }
        });

        ((Command) (ISLDParametr.Slot0)).LinkTo((Command) (ISLDParametr.Slot1));
        ((Command) (ISLDParametr.Slot1)).LinkTo((Command) (ISLDParametr.Slot2));
        ((Command) (ISLDParametr.Slot2)).LinkTo((Command) (ISLDParametr.Slot3));
        ((Command) (ISLDParametr.Slot3)).LinkTo((Command) (ISLDParametr.Slot4));
        ((Command) (ISLDParametr.Slot4)).LinkTo((Command) (ISLDParametr.Slot0));

        ((Command) (ISLDParametr.Slot0)).setVisible(true);
        ((Command) (ISLDParametr.Slot1)).setVisible(true);
        ((Command) (ISLDParametr.Slot2)).setVisible(true);
        ((Command) (ISLDParametr.Slot3)).setVisible(true);
        ((Command) (ISLDParametr.Slot4)).setVisible(true);
        ((Command) (ISLDParametr.AmbientIndex)).setVisible(true);

        SetFocus((Command) (ISLDParametr.Slot0));

    }

//    public void Start(StartStopPauseSystem s) {
//
//    }
    public void SetSlotsAtributs(String[] slots) {
    }

    class MotionResumeGame extends Motion {

        public MotionResumeGame() {
            super(1, "ResumeGame");
        }

        public void MotionMethod() {
            getCommandList().ReturnToGame();
        }
    }

    private class CommandSlot extends Command {

        int slot;

        private CommandSlot(int slot_) {
            slot = slot_;
        }

        public void Enter() {
            reciver.AddMotions(new Motion(1, "Go") {

                public void MotionMethod() {
                    if (isFinal()) {
                        SetFocus(CommandSlot.this);
                        if (mod == Load) {
                            getCommandList().LoadGame(slot);
                        }
                        if (mod == Save) {
                            getCommandList().SaveGame(slot);
                        }
                    }
                }
            });
        }

        public IMotion StopMotionEsc(Object[] arg) {
            return new MotionResumeGame();
        }
    }
}
