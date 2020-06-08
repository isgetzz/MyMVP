package com.baselib.util;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 录音stop 返回文件
 */
public class VideoUtil {
    private static MediaRecorder mediaRecorder;
    private static String p;
    private static boolean running = false;
    private static MediaPlayer mediaPlayer;

    public static boolean start() {
        if (running) {
            running = false;
            stop();
            return false;
        }
        running = true;
        if (mediaRecorder == null)
            mediaRecorder = new MediaRecorder();
        try {
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            String fileName = DateUtil.format(new Date(), "yyyyMMddHHmmss");
            File destDir = new File(Environment.getExternalStorageDirectory() + "/switching/");
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            String filePath = Environment.getExternalStorageDirectory() + "/switching/" + fileName + ".amr";
            p = filePath;
            mediaRecorder.setOutputFile(filePath);
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IllegalStateException e) {
            running = false;
        } catch (IOException e) {
            running = false;
        }
        return true;
    }

    public static File stop() {
        if (running) {
            running = false;
            try {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
                return new File(p);
            } catch (RuntimeException e) {
                mediaRecorder.reset();
                mediaRecorder.release();
                mediaRecorder = null;
                return null;
            }
        } else {
            return null;
        }
    }

    public static void play(String path) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {

        }
    }
}
