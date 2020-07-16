/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Util.Profiler;

import Platform.Util.Profiler.observercode.Diagram.Diagram;

/**
 *
 * @author Администратор
 */
public class ClassAssociations {

    public static Diagram diargamma = new Diagram();

    static public void PrintAssociaties() {
        Throwable thr = new Throwable();
        StackTraceElement[] ste = thr.getStackTrace();
        String callerClassName = null;
        int i = ste.length - 2;
        while (i >= 0) {
            StackTraceElement master = ste[i + 1];
            StackTraceElement slave = ste[i];

            if (!slave.getClassName().equalsIgnoreCase(ClassAssociations.class.getName())) {
                    diargamma.AddAccociation(master.getFileName().replaceAll(".java", ""), slave.getFileName().replaceAll(".java", ""), 1,"","");
            }
            i--;

        }
//                diargamma.AddAccociation(ste[2].getFileName(), ste[1].getFileName(), 1);
    }

    
}
