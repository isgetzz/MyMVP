package com.baselib.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;

/**
 * 安装apk
 */
public class InstallApkUtil {
    public static void Install(Context context, String filePath) {
        if (TextUtils.isEmpty(filePath))
            return;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        // setFlags要放在addFlags之前
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //7.0以上不能直接访问Uri
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            Uri uri = FileProvider.getUriForFile(context, "com.app.ccmvp.fileprovider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        } else {
            //打开访问地址 {".apk",    "application/vnd.android.package-archive"},
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }
}
