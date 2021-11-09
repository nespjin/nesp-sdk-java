package com.nesp.sdk.java.io;

import java.io.*;

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/8/10 20:18
 * Description:
 **/
public final class FileUtils {

    private static final String TAG = "FileUtils";

    private FileUtils() {
    }

    public static boolean copy(File source, File destination) {
        return copy(source, destination, true);
    }

    public static boolean copy(File source, File destination, boolean overwrite) {
        if (overwrite && destination.exists()) {
            destination.deleteOnExit();
        }

        try (
                InputStream sourceFileInputStream = new FileInputStream(source);
                OutputStream destinationFileOutputStream = new FileOutputStream(destination)
        ) {
            byte[] buffer = new byte[1024];

            while (sourceFileInputStream.read(buffer) != -1) {
                destinationFileOutputStream.write(buffer);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
