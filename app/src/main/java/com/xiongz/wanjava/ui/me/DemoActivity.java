package com.xiongz.wanjava.ui.me;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xiongz.android.core.activities.JzActivity;
import com.xiongz.wanjava.R;

/**
 * 一些示例
 *
 * @author xiongz
 * @date 2021/9/24
 */
public class DemoActivity extends JzActivity {

    @Override
    protected Object getContentView() {
        return R.layout.activity_demo;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(this, "一些示例");
    }
}