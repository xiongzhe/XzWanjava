package com.xiongz.wanjava.common.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.joanzapata.iconify.IconDrawable;
import com.xiongz.wanjava.R;
import com.xiongz.wanjava.common.icon.SfaIcons;

/**
 * 主界面底部导航
 * Created by xiongz on 2015/10/28.
 */
public class MenuBarView extends FrameLayout implements View.OnClickListener {

    private Button[] mBtns;
    private IconDrawable[] mDrawables = new IconDrawable[5];
    private OnMenuItemClickListener mClickListener;
    private Context mContext;

    /**
     * @param context
     */
    public MenuBarView(Context context) {
        super(context);
        initView(context);
    }

    /**
     * @param context
     * @param attributeSet
     */
    public MenuBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void initView(Context context) {
        mContext = context;

        // 设置图标
        mDrawables[0] = new IconDrawable(mContext, SfaIcons.ic_menu_home).colorRes(R.color.colorPrimary).actionBarSize();
        mDrawables[1] = new IconDrawable(mContext, SfaIcons.ic_menu_msg).colorRes(R.color.icon_gray).actionBarSize();
        mDrawables[2] = new IconDrawable(mContext, SfaIcons.ic_menu_contacts).colorRes(R.color.icon_gray).actionBarSize();
        mDrawables[3] = new IconDrawable(mContext, SfaIcons.ic_menu_form).colorRes(R.color.icon_gray).actionBarSize();
        mDrawables[4] = new IconDrawable(mContext, SfaIcons.ic_menu_mine).colorRes(R.color.icon_gray).actionBarSize();
        int size = mDrawables.length;

        mBtns = new Button[size];
        View view = LayoutInflater.from(context).inflate(R.layout.view_menu_bar, this, true);
        mBtns[0] = view.findViewById(R.id.btn_menu_home);
        mBtns[0].setOnClickListener(this);
        mBtns[1] = view.findViewById(R.id.btn_menu_msg);
        mBtns[1].setOnClickListener(this);
        mBtns[2] = view.findViewById(R.id.btn_menu_contacts);
        mBtns[2].setOnClickListener(this);
        mBtns[3] = view.findViewById(R.id.btn_menu_form);
        mBtns[3].setOnClickListener(this);
        mBtns[4] = view.findViewById(R.id.btn_menu_mine);
        mBtns[4].setOnClickListener(this);

        // 默认显示首页
        setCurrentMenuItem(0);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (mClickListener != null) {
            mClickListener.onMenuItemClick(viewId);
        }
    }

    /**
     * 改变menu
     *
     * @param viewId
     */
    public void setMenu(int viewId) {
        for (int i = 0; i < mBtns.length; i++) {
            Drawable drawableTop;
            if (mBtns[i].getId() == viewId) {
                mBtns[i].setTextSize(12.0f);
                mBtns[i].setTextColor(getResources().getColor(R.color.colorPrimary));
                drawableTop = mDrawables[i].colorRes(R.color.colorPrimary);
            } else {
                mBtns[i].setTextSize(10.0f);
                mBtns[i].setTextColor(getResources().getColor(R.color.icon_gray));
                drawableTop = mDrawables[i].colorRes(R.color.icon_gray);
            }
            mBtns[i].setCompoundDrawablePadding(10);
            mBtns[i].setCompoundDrawables(null, drawableTop, null, null);
        }
    }

    public interface OnMenuItemClickListener {
        void onMenuItemClick(int viewId);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        mClickListener = onMenuItemClickListener;
    }

    /**
     * 设置当前item
     *
     * @param position 0~4
     */
    public void setCurrentMenuItem(int position) {
        if (position < 0 || position > mDrawables.length) {
            position = 0;
        }
        onClick(mBtns[position]);
    }
}
