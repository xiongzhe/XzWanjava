package com.xiongz.wanjava.application;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.xiongz.android.core.BuildConfig;
import com.xiongz.android.core.app.Xz;
import com.xiongz.wanjava.common.icon.SfaFontModule;
import com.xiongz.wanjava.db.DbManager;
import com.xiongz.wanjava.main.MainActivity;
import com.llew.huawei.verifier.LoadedApkHuaWei;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.tinker.entry.DefaultApplicationLike;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * App代理类
 * Created by xiongz on 2017/12/13
 */
@SuppressWarnings("unused")
public class WanAppLike extends DefaultApplicationLike {

    public static final String TAG = "Tinker";

    public WanAppLike(Application application,
                      int tinkerFlags,
                      boolean tinkerLoadVerifyFlag,
                      long applicationStartElapsedTime,
                      long applicationStartMillisTime,
                      Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag,
                applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }


    @Override
    public void onCreate() {
        super.onCreate();

        Application application = getApplication();

        // Bugly初始化
        Beta.autoCheckUpgrade = true; // 设置自动检查
        Beta.canShowUpgradeActs.add(MainActivity.class);
        Beta.initDelay = 2 * 1000; // 两秒后初始化
        Beta.autoDownloadOnWifi = true; // wifi环境下自动下载
        // Beta.canNotShowUpgradeActs.add(MainActivity.class); // 只在MainActivity提示弹窗
        // Beta.upgradeCheckPeriod = 60 * 60 * 1000; // 更新周期一个小时
        Bugly.init(application, "07460d32e6", BuildConfig.isDebug);
        // 定义开发设备
        Bugly.setIsDevelopmentDevice(application.getApplicationContext(), BuildConfig.isDebug);

        // App配置初始化
        Xz.init(application)
                .withIcon(new FontAwesomeModule())
                .withIcon(new SfaFontModule())
                .withApplication(application)
                .withLoaderDelayed(200)
                .withApiHost("http://www.baidu.com")
                .withInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC));

        // logger初始化
        Logger.addLogAdapter(new AndroidLogAdapter());

        if (BuildConfig.isDebug) {
            // 调试工具初始化(浏览器 chrome://inspect 查看)
            Stetho.initializeWithDefaults(application);
            Xz.init(application).withNetworkInterceptor(new StethoInterceptor());
        }

        // 初始化配置完成
        Xz.init(application).configure();

        // 解决华为手机出现Register too many Broadcast Receivers的问题
        LoadedApkHuaWei.hookHuaWeiVerifier(application);

        // 数据库初始化
        DbManager.getInstance().init(application);

        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            }

            @Override
            public void onCoreInitFinished() {
            }
        };

        //x5内核初始化接口
        QbSdk.initX5Environment(application.getApplicationContext(), cb);
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);

        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
        // 安装tinker
        Beta.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }

}