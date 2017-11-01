
package com.cong.chenchong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cong.chenchong.R;
import com.cong.chenchong.adapter.MainAdapter;
import com.cong.chenchong.bean.AndroidKernel;
import com.cong.chenchong.global.Debug;
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

    private List<AndroidKernel> mAndroidKernelList;
    private long clickTime = 0;
    private LinearLayout layoutExitTips;
    private TextView tvExitTips;
    private Subject<Void, Void> exitWatcher = new SerializedSubject<>(PublishSubject.create());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
        initExitWatcher();
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

        layoutExitTips = (LinearLayout) findViewById(R.id.layout_exit_tips);
        tvExitTips = (TextView) findViewById(R.id.tv_exit_tips);

        ListView lvMain = (ListView) findViewById(R.id.lv_main);
        MainAdapter mainAdapter = new MainAdapter(this, mAndroidKernelList);
        lvMain.setAdapter(mainAdapter);
        lvMain.setOnItemClickListener(this);
    }

    /**
     * 再按一次 退出程序
     */
    private void initExitWatcher() {
        exitWatcher.asObservable()
                .compose(bindToLifecycle())
                .throttleFirst(EXIT_INTERVAL, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(it -> {
                            layoutExitTips.setVisibility(View.VISIBLE);
                            tvExitTips.setText(R.string.exit_app_tip);
                            layoutExitTips.postDelayed(() -> layoutExitTips.setVisibility(View.GONE), EXIT_INTERVAL);
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

    @Override
    protected void onStart() {
        if (Debug.enable)
            Log.v("cc", "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        if (Debug.enable)
            Log.v("cc", "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (Debug.enable)
            Log.v("cc", "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (Debug.enable)
            Log.v("cc", "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (Debug.enable)
            Log.v("cc", "onDestroy");
        super.onDestroy();
        if (exitWatcher != null) {
            exitWatcher.onCompleted();
            exitWatcher = null;
        }
    }

    @Override
    protected void onRestart() {
        if (Debug.enable)
            Log.v("cc", "onRestart");
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        if (exitWatcher != null) {
            exitWatcher.onNext(null);
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
//            layoutExitTips.setVisibility(View.VISIBLE);
//            tvExitTips.setText(R.string.exit_app_tip);
//            layoutExitTips.postDelayed(() -> layoutExitTips.setVisibility(View.GONE), EXIT_INTERVAL);
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
