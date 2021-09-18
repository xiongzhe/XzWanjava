package com.xiongz.wanjava.launcher;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xiongz.android.core.activities.JzActivity;
import com.xiongz.android.core.app.FirstOpenManager;
import com.xiongz.android.core.app.IFirstChecker;
import com.xiongz.android.core.ui.image.GlideUtil;
import com.xiongz.wanjava.R;
import com.xiongz.wanjava.common.constant.Const;
import com.xiongz.wanjava.common.constant.GlideOptions;
import com.xiongz.wanjava.common.timer.BaseTimerTask;
import com.xiongz.wanjava.common.timer.ITimerListener;
import com.xiongz.wanjava.common.utils.StringUtil;
import com.xiongz.wanjava.main.MainActivity;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

import static com.xiongz.android.core.ui.launcher.LauncherStartTag.MAIN;
import static com.xiongz.android.core.ui.launcher.LauncherStartTag.SCROLL;

/**
 * 欢迎页
 *
 * @author xiongz
 * @date 2018/9/4
 */
public class LauncherActivity extends JzActivity {

    @BindView(R.id.iv_launcher_image)
    ImageView ivImage;
    @BindView(R.id.tv_launcher_timer)
    TextView tvTimer;

    //计时器
    private Timer mTimer;
    //倒计时3秒钟
    private int mCount = 3;

    @Override
    protected Object getContentView() {
        return R.layout.activity_launcher;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mIsFullScreen = true;
        super.onCreate(savedInstanceState);

        ActivityUtils.finishOtherActivities(LauncherActivity.class);
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        initView();
        agreePrivacy();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        GlideUtil.withOption(this, Const.welcome_image, ivImage, GlideOptions.getInstance().getNoCacheOption());
    }

    // 定时器监听
    private ITimerListener mTimerListener = new ITimerListener() {
        @Override
        public void onTimer() {
            timer();
        }
    };

    @Override
    protected void onDestroy() {
        if (mTimerListener != null) mTimerListener = null;
        if (mTimer != null) mTimer = null;
        super.onDestroy();
    }

    /**
     * 判断是否同意隐私政策
     */
    private void agreePrivacy() {
        boolean isAgreePrivacy = SPUtils.getInstance().getBoolean(Const.SP_PRIVACY_TAG);
        if (isAgreePrivacy) { // 已同意隐私政策走正常登陆流程
            checkFirstOpen();
        } else {  // 没有同意隐私政策跳转到登录页面
            privacyDialog();
        }
    }

    /**
     * 初始化倒计时
     */
    private void initTimer() {
        tvTimer.setVisibility(View.VISIBLE);
        mTimer = new Timer();
        BaseTimerTask task = new BaseTimerTask(mTimerListener);
        mTimer.schedule(task, 0, 1000);
    }

    /**
     * 定时操作
     */
    private void timer() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (tvTimer != null) {
                        if (mCount > 0) {
                            tvTimer.setText(MessageFormat.format("跳过\n{0}s", mCount));
                        } else if (mCount == 0) {
                            tvTimer.setText("正在跳转");
                        }
                        mCount--;
                        if (mCount < 0) {
                            if (mTimer != null) {
                                mTimer.cancel();
                                mTimer = null;
                            }
                            toActivity(MAIN);
                            FirstOpenManager.setFirst(false);
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * check是否是第一次打开app
     */
    public void checkFirstOpen() {
        // 判断账号是否登录
        FirstOpenManager.checkFirstOpen(new IFirstChecker() {
            @Override
            public void onFirstOpen() {
                toActivity(SCROLL);
            }

            @Override
            public void onNotFirstOpen() {
                toActivity(MAIN);
            }
        });
    }

    @OnClick(R.id.tv_launcher_timer)
    public void onViewClicked() {
        checkFirstOpen();
    }

    /**
     * 跳转到不同的Activity
     */
    private void toActivity(int tag) {
        switch (tag) {
            //滑动
            case SCROLL:
                initTimer();
                break;
            //主页面
            case MAIN:
                ActivityUtils.startActivity(MainActivity.class);
                break;
        }
    }

    /**
     * 隐私政策弹窗
     */
    private void privacyDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("温馨提示");
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_privacy, null);
        TextView tvPrivacy = view.findViewById(R.id.tv_privacy);
        String string = getResources().getString(R.string.privacy6);
        tvPrivacy.setText(StringUtil.getClickableSpan(this, string));
        //设置超链接可点击
        tvPrivacy.setMovementMethod(LinkMovementMethod.getInstance());
        alertBuilder.setView(view);
        alertBuilder.setPositiveButton("同意", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SPUtils.getInstance().put(Const.SP_PRIVACY_TAG, true);
                dialog.dismiss();
                // 走正常流程
                checkFirstOpen();
            }
        });
        alertBuilder.setNegativeButton("不同意", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onBackPressed();
            }
        });
        AlertDialog dialog = alertBuilder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.gray));
    }
}
