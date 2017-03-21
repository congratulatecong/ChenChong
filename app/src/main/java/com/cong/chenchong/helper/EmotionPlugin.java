
package com.cong.chenchong.helper;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

public abstract class EmotionPlugin {
    public interface EmotionSelectedHandler {
        public void onEmotionSelected(int type, String content, Bundle data);
    }

    public static final int TYPE_TEXT = 1;
    public static final int TYPE_VOICE = 2;

    protected String mTag = "";
    protected EmotionSelectedHandler mEmotionSelectedHandler;
    protected Context mContext;

    public EmotionPlugin(Context c, EmotionSelectedHandler handler) {
        mContext = c;
        mEmotionSelectedHandler = handler;
    }

    public String getTag() {
        return mTag;
    }

    public abstract View getView();
}
