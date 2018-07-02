package com.chhd.android.common.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chhd.android.common.R;
import com.chhd.android.common.ui.activity.base.ToolbarActivity;
import com.chhd.android.common.ui.view.HorizontalProgressBar;

import java.util.HashMap;
import java.util.Map;

/**
 * H5Activity
 *
 * @author : 葱花滑蛋 (2018/03/18)
 */

@SuppressLint("SetJavaScriptEnabled")
public class H5Activity extends ToolbarActivity {

    /**
     * 启动界面
     *
     * @param context context
     * @param resId   主题
     * @param url     链接
     */
    public static void start(Context context, @StyleRes int resId, String url) {
        Intent intent = new Intent(context, H5Activity.class);
        intent.putExtra("resId", resId);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    /**
     * 启动界面
     *
     * @param context context
     * @param resId   主题
     * @param url     链接
     * @param headers 头部
     */
    public static void start(Context context, @StyleRes int resId,
                             String url, HashMap<String, String> headers) {
        Intent intent = new Intent(context, H5Activity.class);
        intent.putExtra("resId", resId);
        intent.putExtra("url", url);
        intent.putExtra("headers", headers);
        context.startActivity(intent);
    }

    /**
     * 启动界面
     *
     * @param context context
     * @param resId   主题
     * @param url     链接
     * @param title   标题
     */
    public static void start(Context context, @StyleRes int resId, String url, String title) {
        Intent intent = new Intent(context, H5Activity.class);
        intent.putExtra("resId", resId);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    /**
     * 启动界面
     *
     * @param context context
     * @param resId   主题
     * @param url     链接
     * @param headers 头部
     * @param title   标题
     */
    public static void start(Context context, @StyleRes int resId,
                             String url, HashMap<String, String> headers, String title) {
        Intent intent = new Intent(context, H5Activity.class);
        intent.putExtra("resId", resId);
        intent.putExtra("url", url);
        intent.putExtra("headers", headers);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    private HorizontalProgressBar progressBar;
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(getIntent().getIntExtra("resId", 0));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5);

        webView = findViewById(R.id.web_view);
        progressBar = findViewById(R.id.progress_bar);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (getIntent().getStringExtra("title") == null && !TextUtils.isEmpty(title)) {
                    setToolbarTitle(title);
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }
        });

        String url = getIntent().getStringExtra("url");
        HashMap<String, String> headers =
                (HashMap<String, String>) getIntent().getSerializableExtra("headers");
        webView.loadUrl(url, headers);
    }

    @Override
    protected CharSequence getToolbarTitle() {
        String title = getIntent().getStringExtra("title");
        if (title != null) {
            return title;
        } else {
            return super.getToolbarTitle();
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
