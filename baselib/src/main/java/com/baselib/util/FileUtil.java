package com.baselib.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

import static android.os.Environment.MEDIA_MOUNTED;

public class FileUtil {
    public static String getFilePath(Context context, String dir) {
        String directoryPath = "";
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {//判断外部存储是否可用
            directoryPath = context.getExternalFilesDir(dir).getAbsolutePath();
        } else {//没外部存储就使用内部存储
            directoryPath = context.getFilesDir() + File.separator + dir;

        }
        File file = new File(directoryPath);
        if (!file.exists()) {//判断文件目录是否存在
            file.mkdirs();
        }
        return directoryPath;

    }

    /**
     * @param context  c
     * @param dir      目录
     * @param FimeName 文件名称
     * @return
     */
    public static File getFilePath(Context context, String dir, String FimeName) {
        File file = new File(getFilePath(context, dir), FimeName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

}
