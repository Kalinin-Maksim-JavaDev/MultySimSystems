/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.DEBUG;

import Game.Model.Game.World.Interaction.IMapPainter;
import java.awt.*;

/**
 *
 * @author dkx6r0c
 */
public abstract class MapPainter extends Thread implements IMapPainter {

    abstract protected Object getGraphics();

    abstract protected Object createImage();

    abstract protected Object[][] getMap();
    private Graphics2D g;
    private Image im;
    private boolean showMap = true;

    public MapPainter(String name) {
        super(name);
    }

    public void setShowMap(boolean showMap) {
        this.showMap = showMap;
    }

    @Override
    public void run() {
        int mapgrad = 1; // game.grad;
        // game.grad;
        
        while (showMap) {

            IColoredPoint[][] map = (IColoredPoint[][]) getMap();
            IColoredPoint node_;
            Graphics g2 = im.getGraphics();
            g2.clearRect(0, 0, map[0].length * mapgrad + mapgrad, map.length * mapgrad + mapgrad);
            for (int j = 0; j < map.length; j++) {
                for (int i = 0; i < map[0].length; i++) {
                    node_ = map[j][i];
                    if (node_ != null) {
                        // GroupUnit u_ = node_.GetUnit(i, j);
                        // if (u_ != null) {
                        //     g2.setColor(Color.GRAY);
                        //     g2.drawOval(i, GParametr.h - j, mapgrad, mapgrad);
                        //                                        if (!u_.Monument()) {
                        //   g2.setColor(Color.black);
                        //     g2.drawLine(i * mapgrad + mapgrad / 2, GParametr.h - j * mapgrad - mapgrad / 2, (int) (((Coordinates2d) u_.position).xd * mapgrad) + mapgrad / 2, GParametr.h - (int) ((Coordinates2d) u_.position).yd * mapgrad - mapgrad / 2);
                        //   }
                        //   }
                        g2.setColor(node_.GetColor());
                        g2.drawRect(i * mapgrad, (map.length - j) * mapgrad, mapgrad / 2, mapgrad / 2);
                        //                                g2.setColor(Color.BLACK);
                        //                                g2.drawRect((int) ((Coordinates2d) node_.position).x*mapgrad, GParametr.h * mapgrad - (int) ((Coordinates2d) node_.position).y*mapgrad - game.grad*mapgrad, game.grad*mapgrad, game.grad*mapgrad);
                        //                                g2.setColor(Color.gray);
                        //                                g2.drawRect((int) ((Coordinates2d) node_.position).xd*mapgrad, GParametr.h * mapgrad - (int) ((Coordinates2d) node_.position).yd*mapgrad - game.grad*mapgrad, game.grad*mapgrad, game.grad*mapgrad);
                    }
                    //   g2.setColor(Color.GRAY);
                    //   g2.drawRect(i * mapgrad, GParametr.h - j * mapgrad, 0, 0);
                }
            }
            g.drawImage(im, 0, 0, null);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void go() {
        im = (Image) createImage();
        g = (Graphics2D) getGraphics();
        start();
    }

    public void close() {
        setShowMap(false);
    }
}
