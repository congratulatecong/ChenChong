
package com.cong.chenchong.base;

import com.cong.chenchong.bean.CallshowExtItem;
import com.cong.chenchong.robot.ChatMessage;
import com.cong.chenchong.robot.HttpUtils;
import com.cong.chenchong.widget.BottomQuickAction;
import com.cong.chenchong.widget.BottomQuickAction.BottomItem;
import com.cong.chenchong.widget.BottomQuickAction.OnActionItemClickListener;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 公共账户工具类
 *
 * @author xilei
 */
public class SingleChatPubNumHelper {

    private static SingleChatPubNumHelper mInstance = null;

    private SingleChatPubNumHelper() {

    }

    public static SingleChatPubNumHelper getInstance() {
        if (mInstance == null) {
            synchronized (SingleChatPubNumHelper.class) {
                if (mInstance == null) {
                    mInstance = new SingleChatPubNumHelper();
                }
            }
        }
        return mInstance;
    }

    public OnActionItemClickListener getItemClickListener(final Context context, final Handler handler) {
        return new BottomQuickAction.OnActionItemClickListener() {
            @Override
            public void onItemClick(final BottomItem ceiIn) {
                if (!(ceiIn.data instanceof CallshowExtItem)) {
                    return;
                }
                CallshowExtItem cei = (CallshowExtItem) ceiIn.data;
                switch (cei.operateType) {
                    // case 11:
                    // Log.v("cc", "cei.clientType->" + cei.clientType);
                    // new SendMessageTask(handler).execute(cei.clientType);
                    // break;
                    // case 12:
                    // new SendMessageTask(handler).execute(cei.clientType);
                    // break;
                    // case 13:
                    // Log.v("cc", "cei.clientType->" + cei.clientType);
                    // new SendMessageTask(handler).execute(cei.clientType);
                    // break;
                    // case 14:
                    // Log.v("cc", "cei.clientType->" + cei.clientType);
                    // new SendMessageTask(handler).execute(cei.clientType);
                    // break;
                    // case 15:
                    // new SendMessageTask(handler).execute(cei.clientType);
                    // break;
                    // case 21:
                    // Log.v("cc", "cei.clientType->" + cei.clientType);
                    // new SendMessageTask(handler).execute(cei.clientType);
                    // break;
                    // case 22:
                    // Log.v("cc", "cei.clientType->" + cei.clientType);
                    // new SendMessageTask(handler).execute(cei.clientType);
                    // break;
                    // case 23:
                    // new SendMessageTask(handler).execute(cei.clientType);
                    // break;
                    // case 0:
                    // Log.v("cc", "cei.clientType->" + cei.clientType);
                    // new SendMessageTask(handler).execute(cei.clientType);
                    // break;
                    default:
                        Log.v("cc", "cei.clientType->" + cei.clientType);
                        new SendMessageTask(handler).execute(cei.clientType);
                        break;
                }
            }
        };
    }

    private class SendMessageTask extends AsyncTask<String, Void, Void> {

        private Handler mHandler;

        public SendMessageTask(Handler mHandler) {
            super();
            this.mHandler = mHandler;
        }

        @Override
        protected Void doInBackground(String... message) {
            ChatMessage fromMessage = HttpUtils.sendMessage(message[0]);
            Message m = mHandler.obtainMessage();
            m.obj = fromMessage;
            mHandler.sendMessage(m);
            return null;
        }

    }

}
