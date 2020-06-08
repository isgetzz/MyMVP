package com.baselib.util;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author : CC
 * e-mail : 849199845@qq.com
 * time   : 2020/05/27  10:37
 * desc   : 讯飞语音科技
 */

public class VoiceUtil {
    private static volatile VoiceUtil ttsUtil = null;
    private SpeechSynthesizer mTts;
    private boolean isInitSuccess = false;
    //此处需要将语音添加到队列，保证一条播放完再播放下一条
    //必须第一次收到消息才能在这播放，以后每次收到消息将消息添加到队列，每次播放完后去队列里面取；
    private static Queue<String> queue = new LinkedList<>();

    public VoiceUtil() {
    }

    public static VoiceUtil getInstance() {
        if (ttsUtil == null) {
            synchronized (VoiceUtil.class) {
                if (ttsUtil == null) {
                    ttsUtil = new VoiceUtil();
                }
            }
        }
        return ttsUtil;
    }

    public void init(String msg, Context context) {
        mTts = SpeechSynthesizer.createSynthesizer(context, new MyInitListener(msg, context));
        initSpeechSynthesizer();
    }

    private void initSpeechSynthesizer() {
        if (mTts != null) {
            mTts.setParameter(SpeechConstant.PARAMS, null);
            mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
            mTts.setParameter(SpeechConstant.SPEED, "60");//速度
            mTts.setParameter(SpeechConstant.PITCH, "50");//语调
            mTts.setParameter(SpeechConstant.VOLUME, "50");//音量
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);   //引擎类型
            mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");//设置播放合成音频打断音乐播放，默认为true
            //mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflyket.pcm");    //声音文件地址
        }
    }

    private class MyInitListener implements InitListener {
        private String msg;
        private Context context;

        public MyInitListener(String msg, Context context) {
            this.msg = msg;
            this.context = context;
        }

        @Override
        public void onInit(int code) {
            if (code == ErrorCode.SUCCESS) {
                isInitSuccess = true;
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
                speak(msg, context);
            }
        }
    }

    public void speak(String msg, Context context) {
        if (mTts != null && isInitSuccess) {
            queue.offer(msg);
            if (!mTts.isSpeaking()) {
                //stop();
                mTts.startSpeaking(queue.poll(), synthesizerListener);
            }
        } else {
            init(msg, context);
        }
    }

    public void stop() {
        mTts.stopSpeaking();
    }

    private SynthesizerListener synthesizerListener = new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {

        }

        @Override
        public void onCompleted(SpeechError error) {
            Log.d("MyInitListener", "语音初始化成功:" + error);
            if (error == null) {
                if (!queue.isEmpty()) {
                    mTts.startSpeaking(queue.poll(), synthesizerListener);
                }
            } else {
                Log.d("MyInitListener", "语音初始化成功:" + error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //  if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //      String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //      Log.d(TAG, "session id =" + sid);
            //  }
        }
    };

    public void pause() {
        mTts.pauseSpeaking();
    }

    public void resume() {
        mTts.resumeSpeaking();
    }

    public void release() {
        if (null != mTts) {
            mTts.stopSpeaking();
            mTts.destroy();  //退出时释放
        }
    }
}
