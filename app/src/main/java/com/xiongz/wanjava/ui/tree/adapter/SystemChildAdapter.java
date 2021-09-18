package com.xiongz.wanjava.ui.tree.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xiongz.android.core.ui.recycler.ViewHolder;
import com.xiongz.wanjava.R;
import com.xiongz.wanjava.common.utils.ColorUtil;
import com.xiongz.wanjava.ui.tree.entity.SystemEntity;

import java.util.List;

/**
 * 体系子适配器
 *
 * @author xiongz
 * @date 2021/9/1
 */
public class SystemChildAdapter extends BaseQuickAdapter<SystemEntity.ChildrenBean, ViewHolder> {

    private Context mContext;
    private List<SystemEntity.ChildrenBean> mData;

    public SystemChildAdapter(Context context, int layoutResId, List<SystemEntity.ChildrenBean> datas) {
        super(layoutResId, datas);
        mContext = context;
        mData = datas;
    }

    @Override
    protected void convert(ViewHolder helper, final SystemEntity.ChildrenBean item) {
        helper.setText(R.id.flow_tag, item.getName());
        helper.setTextColor(R.id.flow_tag, ColorUtil.getRandomColorWithOutWhite());
    }
}