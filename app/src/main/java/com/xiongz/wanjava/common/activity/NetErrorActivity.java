package com.xiongz.wanjava.common.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.blankj.utilcode.util.ActivityUtils;
import com.xiongz.android.core.activities.JzActivity;
import com.xiongz.wanjava.R;
import com.xiongz.wanjava.launcher.LauncherActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 网络异常
 *
 * @author xiongz
 * @date 2018/11/23
 */
public class NetErrorActivity extends JzActivity {

    @BindView(R.id.btn_net_error)
    Button btnNetError;

    @Override
    protected Object getContentView() {
        return R.layout.activity_net_error;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(this, "网络异常");
        ActivityUtils.finishActivity(LauncherActivity.class);
    }

    @OnClick(R.id.btn_net_error)
    public void onViewClicked() {
        ActivityUtils.startActivity(LauncherActivity.class);
    }
}
