package Global;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.InputStream;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Adm
 */
public class Tools {

    public static Random random = new Random();

    public static void Pause1() {
//        try {
//            Thread.sleep(0,random.nextInt(5));
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Pause.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public static void Pause2() {
        try {
            Thread.sleep(random.nextInt(50), 0);
        } catch (InterruptedException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static void UnsupportedOperationException() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static InputStream getResourceAsStream(String name) {
        return Tools.class.getResourceAsStream(name);
    }

    public static void UnsupportedOperationException(String string) {
        throw new UnsupportedOperationException(string);
    }
}
