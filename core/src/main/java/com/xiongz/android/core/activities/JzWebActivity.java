package com.xiongz.android.core.activities;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewConfiguration;

import com.blankj.utilcode.util.ToastUtils;
import com.xiongz.android.core.R;
import com.xiongz.android.core.permissions.PermissionCallBack;
import com.xiongz.android.core.ui.webview.X5ProgressWebView;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.Timer;
import java.util.TimerTask;

/**
 * webview的Activity基类
 *
 * @author xiongz
 * @date 2019-05-22
 */
public abstract class JzWebActivity extends JzActivity {

    // 是否是邮箱webview
    protected boolean mIsEmail;

    private X5ProgressWebView mWebView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        super.onCreate(savedInstanceState);
    }

    /**
     * 设置web url
     *
     * @param activity
     * @param url
     */
    protected void setLoadUrl(final Activity activity, String url) {
        mWebView = activity.findViewById(R.id.x5_web_view);
        initWebViewSettings();
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(final String url, String userAgent,
                                        final String contentDisposition,
                                        String mimetype, long contentLength) {
                startStorage(new PermissionCallBack() {
                    @Override
                    public void handle() {
                        try {
                            if (mIsEmail) {
                                download(url, contentDisposition, activity);
                            } else {
                                String[] strs = url.split("/");
                                String fileName = strs[strs.length - 1];
                                download(url, fileName);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.loadUrl(url);
    }

    /**
     * WebSettings 配置
     */
    private void initWebViewSettings() {
        WebSettings webSetting = mWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);//允许js调用
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        webSetting.setAllowFileAccess(true);//在File域下，能够执行任意的JavaScript代码，同源策略跨域访问能够对私有目录文件进行访问等
        webSetting.setSupportZoom(true);//支持页面缩放
        webSetting.setBuiltInZoomControls(true);//进行控制缩放
        webSetting.setAllowContentAccess(true);//是否允许在WebView中访问内容URL（Content Url），默认允许
        webSetting.setUseWideViewPort(true);//设置缩放密度
        webSetting.setSupportMultipleWindows(false);//设置WebView是否支持多窗口,如果为true需要实现onCreateWindow(WebView, boolean, boolean, Message)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //两者都可以
            webSetting.setMixedContentMode(webSetting.getMixedContentMode());//设置安全的来源
        }
        webSetting.setAppCacheEnabled(true);//设置应用缓存
        webSetting.setDomStorageEnabled(true);//DOM存储API是否可用
        webSetting.setGeolocationEnabled(true);//定位是否可用
        webSetting.setLoadWithOverviewMode(true);//是否允许WebView度超出以概览的方式载入页面，
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);//设置应用缓存内容的最大值
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);//设置是否支持插件
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);//重写使用缓存的方式
        webSetting.setAllowUniversalAccessFromFileURLs(true);//是否允许运行在一个file schema URL环境下的JavaScript访问来自其他任何来源的内容
        webSetting.setAllowFileAccessFromFileURLs(true);//是否允许运行在一个URL环境
    }

    public abstract void download(String url, String fileName);

    /**
     * 邮箱中文件的下载流程
     *
     * @param url
     * @param contentDisposition
     * @param activity
     */
    private void download(String url, String contentDisposition, Activity activity) {
        String newUrl = url.replace("https://mail.crjz.com/m/read","http://mail.crjz.com/js6/read"); // 改成pc的下载地址
        final DownloadManager downloadManager = ((DownloadManager) getSystemService(Activity.DOWNLOAD_SERVICE));
        final DownloadManager.Request request = new DownloadManager.Request(Uri.parse(newUrl));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setVisibleInDownloadsUi(true);
        //传cookie
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);// 允许接受 Cookie
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(mWebView, true);// 跨域cookie读取
        }
        String CookieStr = CookieManager.getInstance().getCookie(newUrl);
        request.addRequestHeader("Cookie", CookieStr);
        //contentDisposition本身带有att...一串字符,中文和非中文还不一样
        String fileName;
        if (contentDisposition.contains("filename*=UTF-8")) {
            fileName = contentDisposition.substring(contentDisposition.indexOf("'") + 2);
        } else {
            fileName = contentDisposition.substring(contentDisposition.indexOf("=") + 1).replace("\"", "");
            if (fileName.equals("")) {
                fileName = newUrl.substring(newUrl.lastIndexOf("/") + 1);
            }
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            new AlertDialog.Builder(activity)
                    .setTitle("下载附件")
                    .setMessage("立刻下载附件\"" + fileName + "\"？")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            downloadManager.enqueue(request);
                            ToastUtils.showLong("文件下载中...");
                            dialogInterface.dismiss();
                        }
                    }).create().show();
        }
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.getSettings().setBuiltInZoomControls(true);
            mWebView.setVisibility(View.GONE);
            long timeout = ViewConfiguration.getZoomControlsTimeout();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mWebView.destroy();
                        }
                    });
                }
            }, timeout);
        }
        super.onDestroy();
    }
}
