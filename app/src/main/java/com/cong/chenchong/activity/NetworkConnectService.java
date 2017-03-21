package com.cong.chenchong.activity;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.widget.Toast;

import com.cong.chenchong.util.ContextUtils;

public class NetworkConnectService extends Service {

    private BroadcastReceiver mNetworkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
                return;
            }
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
                switch (ContextUtils.getDetailNetWorkType(NetworkConnectService.this)) {
                    case ContextUtils.NETWORKTYPE_INVALID:
                        Toast.makeText(NetworkConnectService.this, "网络连接异常", Toast.LENGTH_SHORT).show();
                        sendBroadcast(new Intent(MainActivity.ACTION_NETWORKTYPE_INVALID));
                        break;
                    case ContextUtils.NETWORKTYPE_WAP:
                    case ContextUtils.NETWORKTYPE_2G:
                        Toast.makeText(NetworkConnectService.this, ("2G/3G网络"), Toast.LENGTH_SHORT).show();
                        sendBroadcast(new Intent(MainActivity.ACTION_NETWORKTYPE_2G));
                        break;

                    default:
                        break;
                }
            }
        }
    };

    public NetworkConnectService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mNetworkReceiver != null) {
            unregisterReceiver(mNetworkReceiver);
        }
    }
}
