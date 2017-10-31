
package com.cong.chenchong.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cong.chenchong.R;
import com.cong.chenchong.adapter.MainAdapter;
import com.cong.chenchong.bean.AndroidKernel;
import com.cong.chenchong.global.SlidingActivity;
import com.cong.chenchong.ui.UiActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

public class MainActivity extends SlidingActivity implements OnItemClickListener {

    private static final int EXIT_INTERVAL = 2000;
    public static final String ACTION_NETWORKTYPE_INVALID = "android.intent.action.NetworkTypeInvalid";
    public static final String ACTION_NETWORKTYPE_2G = "android.intent.action.NetworkType2G";

    private ListView mListView;

    private List<AndroidKernel> mAndroidKernelList;

    private MainAdapter mMainAdapter;

    private long clickTime = 0;

    private LinearLayout mLayoutExitTips;

    private TextView mTxtExitTips;

    private FrameLayout mFLayoutTips;

    private Handler mHandler = new Handler();

    private Subject<Void, Void> exitWatcher = new SerializedSubject<>(PublishSubject.create());

    private BroadcastReceiver mNetRecriver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
                return;
            }
            if (ACTION_NETWORKTYPE_INVALID.equals(action)) {
                showTips("检测到网络连接异常，请确认。去设置");
            } else if (ACTION_NETWORKTYPE_2G.equals(action)) {
                showTips("检测到当前网速较慢，不稳定。去设置");
            } else {
                mFLayoutTips.setVisibility(View.GONE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_NETWORKTYPE_INVALID);
        intentFilter.addAction(ACTION_NETWORKTYPE_2G);
        registerReceiver(mNetRecriver, intentFilter);
//        LocalBroadcastManager.getInstance(this).registerReceiver(mNetRecriver, intentFilter);

        initData();

        initView();

        initExitWatcher();

    }

    /**
     * 再按一次 退出程序
     */
    private void initExitWatcher() {
        exitWatcher.asObservable()
                .compose(bindToLifecycle())
                .throttleFirst(EXIT_INTERVAL, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(it -> {
                            mLayoutExitTips.setVisibility(View.VISIBLE);
                            mTxtExitTips.setText(R.string.exit_app_tip);
                            mLayoutExitTips.postDelayed(() -> mLayoutExitTips.setVisibility(View.GONE), EXIT_INTERVAL);
                        },
                        Throwable::printStackTrace);
        exitWatcher.asObservable()
                .compose(bindToLifecycle())
                .timeInterval(AndroidSchedulers.mainThread())
                .skip(1)
                .filter(it -> it.getIntervalInMilliseconds() < EXIT_INTERVAL)
                .subscribe(it -> finish(), Throwable::printStackTrace);
    }

    @Override
    protected void onStart() {
        Log.v("cc", "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.v("cc", "onResume");
        super.onResume();
//        sendBroadcast(new Intent(ACTION_CONNECT_NETWORK));
//        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ACTION_CONNECT_NETWORK));
    }

    @Override
    protected void onPause() {
        Log.v("cc", "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.v("cc", "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.v("cc", "onDestroy");
        super.onDestroy();
        if (mNetRecriver != null) {
            unregisterReceiver(mNetRecriver);
//            LocalBroadcastManager.getInstance(this).unregisterReceiver(mNetRecriver);
        }
        if (exitWatcher != null) {
            exitWatcher.onCompleted();
            exitWatcher = null;
        }
    }

    @Override
    protected void onRestart() {
        Log.v("cc", "onRestart");
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        if (exitWatcher != null) {
            exitWatcher.onNext(null);
        }
    }

    private void initData() {
        mAndroidKernelList = new ArrayList<>();
        AndroidKernel androidKernel = new AndroidKernel("树状可扩展ListView");
        mAndroidKernelList.add(androidKernel);
        androidKernel = new AndroidKernel("各种ScrollView");
        mAndroidKernelList.add(androidKernel);
        androidKernel = new AndroidKernel("各种下拉刷新");
        mAndroidKernelList.add(androidKernel);
        androidKernel = new AndroidKernel("各种设置界面");
        mAndroidKernelList.add(androidKernel);
        androidKernel = new AndroidKernel("各种辅助工具");
        mAndroidKernelList.add(androidKernel);
        androidKernel = new AndroidKernel("各种弹出窗口");
        mAndroidKernelList.add(androidKernel);
        androidKernel = new AndroidKernel("RippleEffect");
        mAndroidKernelList.add(androidKernel);
        androidKernel = new AndroidKernel("消除未读");
        mAndroidKernelList.add(androidKernel);
        androidKernel = new AndroidKernel("图灵机器人");
        mAndroidKernelList.add(androidKernel);
        androidKernel = new AndroidKernel("热门标签");
        mAndroidKernelList.add(androidKernel);
        androidKernel = new AndroidKernel("各种Tab");
        mAndroidKernelList.add(androidKernel);
        androidKernel = new AndroidKernel("异步加载");
        mAndroidKernelList.add(androidKernel);
        androidKernel = new AndroidKernel("各种UI");
        mAndroidKernelList.add(androidKernel);
        androidKernel = new AndroidKernel("桌面悬浮窗");
        mAndroidKernelList.add(androidKernel);
        androidKernel = new AndroidKernel("图片高斯模糊");
        mAndroidKernelList.add(androidKernel);
        androidKernel = new AndroidKernel("测试页面");
        mAndroidKernelList.add(androidKernel);
        androidKernel = new AndroidKernel("仿360手助旋转下载");
        mAndroidKernelList.add(androidKernel);
        androidKernel = new AndroidKernel("创意评价");
        mAndroidKernelList.add(androidKernel);
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
        toolbar.setTitle(R.string.main_title);

        mLayoutExitTips = (LinearLayout) findViewById(R.id.layout_exit_tips);
        mTxtExitTips = (TextView) findViewById(R.id.txt_exit_tips);

        mFLayoutTips = (FrameLayout) findViewById(R.id.fl_main_tips);

        mListView = (ListView) findViewById(R.id.listview_main);
        mMainAdapter = new MainAdapter(this, mAndroidKernelList);
        // mListView.setEmptyView(emptyView);
        mListView.setAdapter(mMainAdapter);
        mListView.setOnItemClickListener(this);
    }

    private void showTips(String tips) {
        LayoutInflater lf = LayoutInflater.from(this);
        RelativeLayout RLayoutSettingTips = (RelativeLayout) lf.inflate(R.layout.layout_setting_tips, null);
        TextView txtTipsInfo = (TextView) RLayoutSettingTips.findViewById(R.id.txt_tips_info);
        // txtTipsInfo.setText("360.cn" + "\n" + "18811790740" + "\n" +
        // "congratulatecong@163.com");
        // XML文件中对应为android:autoLink="all"
        // txtTipsInfo.setAutoLinkMask(Linkify.ALL);
        // txtTipsInfo.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableStringBuilder ssb = new SpannableStringBuilder(tips);
        int length = ssb.length();
        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#1a9df0")), length - 3, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new ClickableSpan() {

            @Override
            public void onClick(View widget) {
                // 进入无线网络配置界面
                // startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                // 进入手机中的wifi网络设置界面
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        }, length - 3, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtTipsInfo.setText(ssb);
        txtTipsInfo.setMovementMethod(LinkMovementMethod.getInstance());

        ImageView imgTipsClose = (ImageView) RLayoutSettingTips.findViewById(R.id.img_tips_close);
        imgTipsClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mFLayoutTips.setVisibility(View.GONE);
            }
        });

        mFLayoutTips.addView(RLayoutSettingTips);
        mFLayoutTips.setVisibility(View.VISIBLE);

        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                mFLayoutTips.setVisibility(View.GONE);
            }
        }, 5000);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent();
        switch (position) {
            case 0:
                intent.setClass(this, TreeViewerActivity.class);
                intent.putExtra("title", mAndroidKernelList.get(position).getTitle());
                startActivity(intent);
                break;
            case 1:
                intent.setClass(this, ScrollViewActivity.class);
                intent.putExtra("title", mAndroidKernelList.get(position).getTitle());
                startActivity(intent);
                break;
            case 2:
                intent.setClass(this, RefreshActivity.class);
                intent.putExtra("title", mAndroidKernelList.get(position).getTitle());
                startActivity(intent);
                break;
            case 3:
                intent.setClass(this, SettingActivity.class);
                intent.putExtra("title", mAndroidKernelList.get(position).getTitle());
                startActivity(intent);
                break;
            case 4:
                intent.setClass(this, ToolActivity.class);
                intent.putExtra("title", mAndroidKernelList.get(position).getTitle());
                startActivity(intent);
                break;
            case 5:
                intent.setClass(this, DialogActivity.class);
                intent.putExtra("title", mAndroidKernelList.get(position).getTitle());
                startActivity(intent);
                break;
            case 6:
                intent.setClass(this, RippleActivity.class);
                intent.putExtra("title", mAndroidKernelList.get(position).getTitle());
                startActivity(intent);
                break;
            case 7:
                intent.setClass(this, WaterDropActivity.class);
                intent.putExtra("title", mAndroidKernelList.get(position).getTitle());
                startActivity(intent);
                break;
            case 8:
                intent.setClass(this, RobotActivity.class);
                intent.putExtra("title", mAndroidKernelList.get(position).getTitle());
                startActivity(intent);
                break;
            case 9:
                intent.setClass(this, TagFlowActivity.class);
                intent.putExtra("title", mAndroidKernelList.get(position).getTitle());
                startActivity(intent);
                break;
            case 10:
                intent.setClass(this, BrandListActivity.class);
                intent.putExtra("title", mAndroidKernelList.get(position).getTitle());
                startActivity(intent);
                break;
            case 11:
                intent.setClass(this, AsyncTaskActivity.class);
                intent.putExtra("title", mAndroidKernelList.get(position).getTitle());
                startActivity(intent);
                break;
            case 12:
                intent.setClass(this, UiActivity.class);
                intent.putExtra("title", mAndroidKernelList.get(position).getTitle());
                startActivity(intent);
                break;
            case 13:
                intent.setClass(this, FloatWindowActivity.class);
                intent.putExtra("title", mAndroidKernelList.get(position).getTitle());
                startActivity(intent);
                break;
            case 14:
                intent.setClass(this, PhotoBlurActivity.class);
                intent.putExtra("title", mAndroidKernelList.get(position).getTitle());
                startActivity(intent);
                break;
            case 15:
                intent.setClass(this, TestActivity.class);
                intent.putExtra("title", mAndroidKernelList.get(position).getTitle());
                startActivity(intent);
                break;
            case 16:
                intent.setClass(this, RollingDownloadActivity.class);
                intent.putExtra("title", mAndroidKernelList.get(position).getTitle());
                startActivity(intent);
                break;
            case 17:
                intent.setClass(this, SmileAppraiseActivity.class);
                intent.putExtra("title", mAndroidKernelList.get(position).getTitle());
                startActivity(intent);
                break;

            default:
                break;
        }
    }


    /**
     * 再按一次 退出程序
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getKeyCode() != KeyEvent.KEYCODE_BACK || event.getAction() != KeyEvent.ACTION_DOWN) {
//            return super.dispatchKeyEvent(event);
//        }
//
//        if ((System.currentTimeMillis() - clickTime) > EXIT_INTERVAL) {
//            mLayoutExitTips.setVisibility(View.VISIBLE);
//            mTxtExitTips.setText(R.string.exit_app_tip);
//            mLayoutExitTips.postDelayed(() -> mLayoutExitTips.setVisibility(View.GONE), EXIT_INTERVAL);
//            clickTime = System.currentTimeMillis();
//            return true;
//        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }
}
