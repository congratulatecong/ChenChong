package com.cong.chenchong.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cong.chenchong.global.SlidingActivity;

import com.cong.chenchong.R;

public class ExplorerActivity extends SlidingActivity implements OnClickListener {

    private TextView mTxtTitle;
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private ImageView mImgBack;
    private ImageView mImgForward;
    private ImageView mImgRefresh;

    private String mTitle;
    private String mUrl;

    private boolean isRefreshable;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer);

        Intent intent = getIntent();
        mTitle = intent.getStringExtra("title");
        mUrl = intent.getStringExtra("url");

        mTxtTitle = (TextView) findViewById(R.id.txt_title);
        mTxtTitle.setText(mTitle);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mImgBack = (ImageView) findViewById(R.id.img_webview_back);
        mImgBack.setOnClickListener(this);
        mImgForward = (ImageView) findViewById(R.id.img_webview_forward);
        mImgForward.setOnClickListener(this);
        mImgRefresh = (ImageView) findViewById(R.id.img_webview_refresh);
        mImgRefresh.setOnClickListener(this);

        mWebView = (WebView) findViewById(R.id.webview_explorer);
        // mWebView.loadUrl(mUrl);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.setWebChromeClient(mWebChromeClient);
        mWebView.setDownloadListener(new MyDownload());
        mWebView.loadUrl(mUrl);

        initBtnStatus();
    }

    private void initBtnStatus() {
        if (mWebView.canGoBack()) {
            mImgBack.setClickable(true);
            mImgBack.setEnabled(true);
            mImgBack.setVisibility(View.VISIBLE);
        } else {
            mImgBack.setClickable(false);
            mImgBack.setEnabled(false);
            mImgBack.setVisibility(View.INVISIBLE);
        }
        if (mWebView.canGoForward()) {
            mImgForward.setVisibility(View.VISIBLE);
            mImgForward.setClickable(true);
            mImgForward.setEnabled(true);
        } else {
            mImgForward.setVisibility(View.INVISIBLE);
            mImgForward.setClickable(false);
            mImgForward.setEnabled(false);
        }
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.v("cc", "url->" + url);
//			if (url.endsWith(".apk")) {
//				Log.v("cc", "shouldOverrideUrlLoading");
//				Uri uri = Uri.parse(url);
//				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//				ExplorerActivity.this.startActivity(intent);
//				return true;
//			}
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            isRefreshable = false;
            mImgRefresh.setImageResource(R.drawable.img_webview_cancel);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            mProgressBar.setVisibility(View.GONE);

            // String title = mWebView.getTitle();
            // mTxtTitle.setText(TextUtils.isEmpty(title) ? "网页" : title);

            isRefreshable = true;
            mImgRefresh.setImageResource(R.drawable.img_webview_refresh);
            initBtnStatus();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            mProgressBar.setVisibility(View.GONE);
            // Log.v("cc", "errorCode->" + errorCode + " description->" +
            // description + " failingUrl->" + failingUrl);
            // mWebView.loadUrl("file:///android_asset/protocol.html");
        }
    };

    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            mTxtTitle.setText(title);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_webview_back:
                mProgressBar.setVisibility(View.VISIBLE);
                mWebView.goBack();
                break;
            case R.id.img_webview_forward:
                mProgressBar.setVisibility(View.VISIBLE);
                mWebView.goForward();
                break;
            case R.id.img_webview_refresh:
                if (isRefreshable) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mWebView.reload();
                } else {
                    mWebView.stopLoading();
                }
                break;

            default:
                break;
        }
    }

    class MyDownload implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            if (url.endsWith(".apk")) {
                Log.v("cc", "onDownloadStart");
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        }

    }
}
