package com.xiongz.wanjava.ui.tree.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xiongz.android.core.ui.recycler.ViewHolder;
import com.xiongz.wanjava.R;
import com.xiongz.wanjava.common.utils.ColorUtil;
import com.xiongz.wanjava.ui.tree.entity.NavigationEntity;

import java.util.List;

/**
 * 导航子适配器
 *
 * @author xiongz
 * @date 2021/9/1
 */
public class NavigationChildAdapter extends BaseQuickAdapter<NavigationEntity.ArticlesBean, ViewHolder> {

    private Context mContext;
    private List<NavigationEntity.ArticlesBean> mData;

    public NavigationChildAdapter(Context context, int layoutResId, List<NavigationEntity.ArticlesBean> datas) {
        super(layoutResId, datas);
        mContext = context;
        mData = datas;
    }

    @Override
    protected void convert(ViewHolder helper, final NavigationEntity.ArticlesBean item) {
        helper.setText(R.id.flow_tag, item.getTitle());
        helper.setTextColor(R.id.flow_tag, ColorUtil.getRandomColorWithOutWhite());
    }
}