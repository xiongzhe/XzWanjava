package com.xiongz.android.core.net.rx;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.xiongz.android.core.app.ConfigKeys;
import com.xiongz.android.core.app.Xz;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * retrofit单例
 *
 * @author xiongz
 * @date 2018/11/29
 */
public class RetrofitManager {

    private static RetrofitManager mInstance = null;
    // retrofit
    private Retrofit mRetrofitClient;
    // cookieJar
    private PersistentCookieJar mPersistentCookieJar;

    /**
     * 获取单例
     *
     * @return
     */
    public static RetrofitManager getInstance() {
        if (mInstance == null) {
            synchronized (RxApiManager.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 创建网络
     */
    public Retrofit createRetrofit() {
        if (mRetrofitClient == null) {
            String baseUrl = Xz.getConfiguration(ConfigKeys.API_HOST);
            mPersistentCookieJar = new PersistentCookieJar(new SetCookieCache(),
                    new SharedPrefsCookiePersistor(Xz.getApplicationContext()));
            OkHttpClient okHttpClient = addInterceptor()
                    .retryOnConnectionFailure(true)
                    .cookieJar(mPersistentCookieJar)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .proxy(Proxy.NO_PROXY) // 防止抓包，绕过代理
                    .build();
            mRetrofitClient = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    //.addConverterFactory(GsonConverterFactory.create())  // gson转换器
                    .addConverterFactory(ScalarsConverterFactory.create()) // 直接返回json串
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return mRetrofitClient;
    }


    /**
     * 添加拦截器
     */
    private OkHttpClient.Builder addInterceptor() {
        OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        ArrayList<Interceptor> INTERCEPTORS = Xz.getConfiguration(ConfigKeys.INTERCEPTOR);
        ArrayList<Interceptor> NETWORK_INTERCEPTORS = Xz.getConfiguration(ConfigKeys.NETWORK_INTERCEPTOR);

        //添加拦截器
        if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()) {
            for (Interceptor interceptor : INTERCEPTORS) {
                BUILDER.addInterceptor(interceptor);
            }
        }
        //添加网络拦截器
        if (NETWORK_INTERCEPTORS != null && !NETWORK_INTERCEPTORS.isEmpty()) {
            for (Interceptor interceptor : NETWORK_INTERCEPTORS) {
                BUILDER.addNetworkInterceptor(interceptor);
            }
        }

        return BUILDER;
    }

    // 清除cookie
    public void clearPersistentCookieJar() {
        mPersistentCookieJar.clear();
    }
}
