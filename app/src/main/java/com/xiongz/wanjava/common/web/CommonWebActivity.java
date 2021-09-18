package com.xiongz.wanjava.common.web;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.xiongz.android.core.activities.JzWebActivity;
import com.xiongz.wanjava.R;

/**
 * @描述 基本WebActivity
 * @创建人 winkey
 * @创建日期 2018/11/13
 */
public class CommonWebActivity extends JzWebActivity {

    public static final String WEB_TITLE = "web_title";
    public static final String WEB_URL = "web_url";

    private String mUrl;
    private String mTitle;

    @Override
    protected Object getContentView() {
        return R.layout.activity_web_common;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        mUrl = bundle.getString(WEB_URL);
        mTitle = bundle.getString(WEB_TITLE);
        setTitle(this, mTitle);
        setLoadUrl(this, mUrl);
    }

    @Override
    public void download(String url, String fileName) {

    }
}
