package com.xiongz.wanjava.common.views;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.xiongz.wanjava.R;

/**
 * 验证码倒计时计数器
 * 绑定控件使用的时候，只能绑定TextView及其子控件
 * Created by xiongz on 2018/1/18.
 */
public class CodeCountDownTimer<T extends TextView> extends CountDownTimer {

    private Context mContext;
    private T mView;//显示的控件

    public CodeCountDownTimer(Context context, T view, long millisInFuture, long countDownInterval) {
        this(millisInFuture, countDownInterval);
        mContext = context;
        mView = view;
    }

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public CodeCountDownTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {//计时过程
        mView.setEnabled(false);//防止重复点击
        mView.setText(millisUntilFinished / 1000 + "s");
        mView.setTextColor(mContext.getResources().getColor(R.color.text_gray));
    }

    @Override
    public void onFinish() {//计时完毕
        mView.setText("获取验证码");
        mView.setEnabled(true);
        mView.setTextColor(mContext.getResources().getColor(R.color.white));
    }

}
