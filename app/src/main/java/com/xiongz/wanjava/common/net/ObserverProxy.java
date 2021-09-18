package com.xiongz.wanjava.common.net;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xiongz.android.core.net.rx.RxApiManager;
import com.xiongz.android.core.ui.loader.XzLoader;
import com.xiongz.android.core.util.json.FastjsonUtil;
import com.xiongz.android.core.util.log.XzLogger;
import com.xiongz.wanjava.common.activity.NetErrorActivity;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Observer代理类（封装Observer实现类，可对网络请求做一些公共的处理，eg:加上loader处理）
 *
 * @author xiongz
 * @date 2021/9/18
 */
public class ObserverProxy implements Observer<String> {

    // 上下文
    private Context mContext;
    // 被观察者
    private Observer<String> mObserver;

    public ObserverProxy(Context context, Observer<String> observer) {
        mContext = context;
        mObserver = observer;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (NetworkUtils.isConnected()) {
            RxApiManager.getInstance().add(mContext, d);
            mObserver.onSubscribe(d);
        } else {
            XzLoader.dismissDialog();
            ActivityUtils.startActivity(NetErrorActivity.class);
        }
    }

    @Override
    public void onNext(String s) {
        try {
            JSONObject js = (JSONObject) JSON.parse(s);
            int errorCode = js.getInteger("errorCode");
            String errorMsg = js.getString("errorMsg");
            if (errorCode != 0) {
                XzLoader.dismissDialog();
                ToastUtils.showLong(errorMsg);
            } else {
                mObserver.onNext(s);
            }
        } catch (Exception e) {
            XzLogger.e("网络请求", e.getMessage());
            e.printStackTrace();
            XzLoader.dismissDialog();
            if (e instanceof JSONException) {
                ToastUtils.showLong("解析错误");
            } else {
                ToastUtils.showLong("服务器错误");
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            ToastUtils.showLong("网络超时,请稍后再试!" + e.getMessage());
        } else if (e instanceof ConnectException) {
            ActivityUtils.startActivity(NetErrorActivity.class);
        } else if (e instanceof HttpException) {
            try {
                HttpException exception = (HttpException) e;
                JSONObject jsonObject = new FastjsonUtil().parseObject(exception.response().errorBody().string());
                String code;
                if (jsonObject != null) {
                    code = jsonObject.getString("code");
                    if (code == null) {
                        ToastUtils.showLong("请求错误");
                    } else if (!code.equals("200")) {
                        String message = jsonObject.getString("message");
                        if (TextUtils.isEmpty(message)) {
                            ToastUtils.showLong("服务器未知错误");
                        } else {
                            ToastUtils.showLong(message);
                        }
                    }
                } else {
                    ToastUtils.showLong("服务器未知错误");
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                ToastUtils.showLong("服务器未知错误");
            }
        }
        XzLoader.dismissDialog();
        mObserver.onError(e);
    }

    @Override
    public void onComplete() {
        mObserver.onComplete();
    }
}
