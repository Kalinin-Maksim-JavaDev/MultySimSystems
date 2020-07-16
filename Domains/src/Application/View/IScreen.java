/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.View;

/**
 *
 * @author kalinin
 */
public interface IScreen {

    interface UnInit {

        IScreen prepare();
    }

    public void update(boolean simple);

    public int getWidth();

    public int getHeight();

    public Object createImage_(int width, int height);

    public Object getGraphics_();

    public Object getGraphicTool();
}
