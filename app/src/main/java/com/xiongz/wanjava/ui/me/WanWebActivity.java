package com.xiongz.wanjava.ui.me;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.xiongz.android.core.activities.JzWebActivity;
import com.xiongz.wanjava.R;
import com.xiongz.wanjava.ui.login.LoginCallback;
import com.xiongz.wanjava.ui.login.LoginManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 玩Android网站
 *
 * @author xiongz
 * @date 2021/9/22
 */
public class WanWebActivity extends JzWebActivity implements Toolbar.OnMenuItemClickListener {

    public static final String WEB_TITLE = "web_title";
    public static final String WEB_URL = "web_url";

    private String mUrl;
    private String mTitle;
    private boolean isFavorite;

    @Override
    protected Object getContentView() {
        return R.layout.activity_web_wan;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        mUrl = bundle.getString(WEB_URL);
        mTitle = bundle.getString(WEB_TITLE);

        Toolbar toolbar = setTitle(this, mTitle);
        toolbar.inflateMenu(R.menu.menu_web);
        toolbar.setOnMenuItemClickListener(this);

        setTitle(this, mTitle);
        setLoadUrl(this, mUrl);
    }

    @Override
    public void download(String url, String fileName) {

    }

    @Override
    public boolean onMenuItemClick(final MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            // 喜欢
            case R.id.action_favorite:
                LoginManager.login(new LoginCallback() {
                    @Override
                    public void doSomething() {
                        if (isFavorite) {
                            isFavorite = false;
                            menuItem.setIcon(R.drawable.ic_favorite2);
                        } else {
                            isFavorite = true;
                            menuItem.setIcon(R.drawable.ic_favorite_red2);
                        }
                    }
                });
                break;
            // 分享
            case R.id.action_share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, mTitle + ":" + mUrl);
                startActivity(Intent.createChooser(shareIntent, mTitle));
                break;
            // 刷新
            case R.id.action_refresh:
                setLoadUrl(this, mUrl);
                break;
            // 用浏览器打开
            case R.id.action_browser:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mUrl));
                ActivityUtils.startActivity(browserIntent);
                break;
        }
        return true;
    }
}
