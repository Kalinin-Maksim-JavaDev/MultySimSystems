/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Util.Profiler.observercode.Diagram;

import Global.Tools;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Администратор
 */
public class DataBlock {

    public static Diagram diargamma = new Diagram();
    public static HashMap<String, ArrayList> methodThreads = new HashMap<String, ArrayList>();
    public static ThreadRegisters threadRegister = new ThreadRegisters();
    public Object value[];
    //  HashSet<String> writers;
    //  HashSet<String> readers;
    public String name[];
    int[] readCounter;
    int[] writeCounter;

    private static void AddUpperMethod(StackTraceElement[] ste, String thread, String motion, int n) {

        for (int i = n; i < ste.length - 1; i++) {
            boolean brk = false;
            String prevmethod = getMethodName(ste[i + 1]);
            String method = getMethodName(ste[i]);
            if (method.equals("MotionMethod")) {
                break;
//                method = thread + "__" + motion;
//                method = method.toUpperCase();
//                brk = true;
            } else {
                if (prevmethod.equals("MotionMethod")) {
                    prevmethod = motion;
                    brk = true;
                }
            }
            method = thread + "__" + method;
            prevmethod = thread + "__" + prevmethod;


            method = Clean(method);
            prevmethod = Clean(prevmethod);
            AddAccociation(thread, prevmethod, method, 1, GetLabel(prevmethod, thread), GetLabel(method, thread));
            if (brk) {
                break;
            }
        }
    }

    private static String GetLabel(String method, String thread) {
        String color_ = ThreadRegisters.threadColor.get(thread);
        if (color_ == null) {
            Tools.UnsupportedOperationException();
        }
        return method + " [shape=box,fillcolor=" + color_ + ",style=filled,rounded]";
    }

    private static String Clean(String method) {
        return method.replaceAll(" ", "").replaceAll("\\<", "").replaceAll("\\>", "");
    }

    private static String getMethodName(StackTraceElement stackTraceElement) {
        String res = stackTraceElement.getMethodName();
        if (res.equalsIgnoreCase("<INIT>")) {
            res = stackTraceElement.getClassName();
            int point1_ = res.lastIndexOf(".") + 1;
            int point2_ = res.lastIndexOf("$");
            if (point2_ > 0) {
                res = res.substring(point1_, point2_);
            } else {
                res = res.substring(point1_);
            }
        }

        return res;
    }

    private static boolean AddAccociation(String thread, String elem1, String elem2, int i, String label, String labe2) {

//        elem1=AddThreadName(thread,elem1);
//        elem2=AddThreadName(thread,elem2);
        return diargamma.AddAccociation(elem1, elem2, i, label, labe2);

    }

    private static String AddThreadName(String thread, String elem) {
        ArrayList threads_ = methodThreads.get(elem);
        if (threads_ != null) {
            if (threads_.isEmpty()) {
                threads_.add(thread);
            } else {
                elem = thread + elem;
            }
        } else {
            threads_ = new ArrayList();
            threads_.add(thread);
        }
        return elem;
    }

    public DataBlock(String[] name_) {
        name = new String[name_.length];
        value = new Object[name_.length];
        writeCounter = new int[name_.length];
        readCounter = new int[name_.length];
        for (int i = 0; i < name_.length; i++) {
            name[i] = "var_" + name_[i];
        }
        //  writers = new HashSet();
        //  readers = new HashSet();
    }

//    private Object Get() {
//        return value;
//    }
//
//    private void Set(Object o) {
//        value = o;
//    }
    static String GetMethodName(StackTraceElement[] ste, String thread, String motion, int i) {

        String method = getMethodName(ste[i]);
        if (method.equals("MotionMethod")) {
            method = motion;
        }
        return method;
    }

    public synchronized void Set(int key, Object value_, int i) {
        Throwable thr = new Throwable();
        StackTraceElement[] ste = thr.getStackTrace();
        String thread = Thread.currentThread().getName();
        String motion = ("Motion_" + ThreadRegisters.motionName.get(thread)).toUpperCase();
        String method = GetMethodName(ste, thread, motion, i + 1);
        String variable = name[key];
        value[key] = value_;
//        if (key.writers.contains(method)) {
//        } else {
        //   key.writers.add(method);
        AddUpperMethod(ste, thread, motion, i + 1);
        method = thread + "__" + method;

        method = Clean(method);
        if (AddAccociation(thread, method, variable, 1, GetLabel(method, thread), "")) {
            writeCounter[key]++;
        }


        // }
    }

    public synchronized Object Get(int key, int i) {
        Object res = null;

        //if (key instanceof ObjectValue) {
        Throwable thr = new Throwable();
        StackTraceElement[] ste = thr.getStackTrace();
        //  ObjectValue key_ = ((ObjectValue) key);
        String thread = Thread.currentThread().getName();
        String motion = ("Motion_" + ThreadRegisters.motionName.get(thread)).toUpperCase();
        ;
        String method = GetMethodName(ste, thread, motion, i + 1);
        String variable = name[key];
//            if (key_.readers.contains(method)) {
//            } else {
//                key_.readers.add(method);
        AddUpperMethod(ste, thread, motion, i + 1);
        method = thread + "__" + method;
        method = Clean(method);
        if (AddAccociation(thread, variable, method, 1, "", GetLabel(method, thread))) {
            readCounter[key]++;
        }
        // }
        res = value[key];
//        } else {
//            res = key;
//        }
        return res;
    }

    public void Set(int key, Object value) {
        Set(key, value, 1);
    }

    public Object Get(int key) {
        return Get(key, 1);
    }

}
