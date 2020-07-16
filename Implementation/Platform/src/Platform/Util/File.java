package Platform.Util;

import Global.Tools;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Adm
 */
public class File {

    private static String GetDirNameFromDialog(String dirName) {
        FileDialog fd = new FileDialog(new Frame(), "Save to", FileDialog.SAVE);
        fd.setDirectory(dirName);
        fd.setLocation(50, 50);
        fd.setVisible(true);
        return fd.getDirectory() + System.getProperty("file.separator");
    }

    private static void WriteToFile(String filename, byte[] data) {

        OutputStream fo = null;
        try {
            fo = new FileOutputStream(filename);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fo.write(data);
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fo.close();
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static byte[] ReadFile(String filename) {


        FileInputStream fi = null;
        try {
            fi = new FileInputStream(filename);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] res = null;
        try {
            res = new byte[fi.available()];
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fi.read(res);
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fi.close();
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    private static String GetFileName(int slot) {
        String fileName = "";
        String dirName = System.getProperty("user.home") + System.getProperty("file.separator");
        if (slot == 0) //default dir
        {
            fileName = dirName;
        } else {
            fileName = dirName;//GetDirNameFromDialog();
        }
        fileName += String.valueOf(slot) + ".sav";
        return fileName;
    }

    public static byte[] Load(int n) {
        return ReadFile(GetFileName(n));
    }

    public static void Save(byte[] data, int n) {
        WriteToFile(GetFileName(n), data);
    }

    public static String[] GetSlots() {
        String[] res = new String[5];
        for (int i = 0; i < res.length; i++) {
            java.io.File file = null;
            file = new java.io.File(GetFileName(i));
            if (file.exists()) {
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                res[i] = file.getName() + " " + dateFormat.format(new Date(file.lastModified()));
            }
        }
        return res;
    }

    public static byte[][] LoadGameMap(InputStream st, int w, int h, byte[] arg) {

        byte[][] res = new byte[h][w];
        BufferedImage image_ = null;
        try {
            image_ = ImageIO.read(st);
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        }

        {
            for (int j = 0; j < res.length; j++) {
                for (int i = 0; i < res[0].length; i++) {
                    int c_ = image_.getRGB(i, res.length - 1 - j);
                    switch (c_) {
                        case -16777216:
                            res[j][i] = arg[0];
                            break;
                        case -8388608:
                            res[j][i] = arg[1];
                            break;
                        case -8126464:
                            res[j][i] = arg[1];
                            break;
                        case -16712704:
                            res[j][i] = arg[2];
                            break;
                        case -16711936:
                            res[j][i] = arg[2];
                            break;
                        case -524288:
                            res[j][i] = arg[3];
                            break;
                        case -65536:
                            res[j][i] = arg[3];
                            break;
                        case -16776961:
                            res[j][i] = arg[4];
                            break;
                        case -65281:
                            res[j][i] = arg[5];
                            break;
                    }
                }
            }
        }
        {
//            for (int j = 0; j < res.length; j++) {
//                for (int i = 0; i < res[0].length; i++) {
//
//                    int val = 0;
//                    {
//                        for (int j2 = 0; (val == 0) && (j2 < grad); j2++) {
//                            for (int i2 = 0; (val == 0) && (i2 < grad); i2++) {
//                                int c_ = image_.getRGB(i * grad + i2, (res.length - 1 - j) * grad + j2);
//                                switch (c_) {
//                                    case -16777216:
//                                        val = arg[0];
//                                        break;
//                                    case -16711681:
//                                        val = arg[1];
//                                        break;
//                                    case -16711936:
//                                        val = arg[2];
//                                        break;
//                                    case -65536:
//                                        val = arg[3];
//                                        break;
//                                }
//                            }
//                        }
//                    }
//                    res[j][i] = val;
//                }
//            }
        }
        return res;
    }

    public static byte[][] LoadGameMap(String st_, int w, int h, byte[] arg) {
        return LoadGameMap(Tools.getResourceAsStream(st_), w, h, arg);
    }
}
