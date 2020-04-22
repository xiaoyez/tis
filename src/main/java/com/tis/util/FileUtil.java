package com.tis.util;

import java.io.File;

public class FileUtil {
    public static String getFileExtName(File file)
    {
        String name = file.getName();
        String extName = name.substring(name.lastIndexOf("."));
        return extName;
    }

    public static String getFileExtName(String filename)
    {
        String name = filename;
        int i = name.lastIndexOf(".");
        if (i < 0)
            return "";
        String extName = name.substring(i);
        return extName;
    }

}
