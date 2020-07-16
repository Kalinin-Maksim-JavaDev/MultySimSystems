/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

/**
 *
 * @author kalinin
 */
public interface IApplication {

    void run();

    interface events {

        void onExit();

        void onGameStarted();

        void onDraw(boolean b);
    }
}
