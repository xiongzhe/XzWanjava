package com.xiongz.wanjava.ui.tree.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.xiongz.android.core.ui.recycler.ViewHolder;
import com.xiongz.wanjava.R;
import com.xiongz.wanjava.ui.tree.entity.SystemEntity;

import java.util.List;

/**
 * 体系适配器
 *
 * @author xiongz
 * @date 2021/9/1
 */
public class SystemAdapter extends BaseQuickAdapter<SystemEntity, ViewHolder> {

    private Context mContext;
    private List<SystemEntity> mData;

    public SystemAdapter(Context context, int layoutResId, List<SystemEntity> datas) {
        super(layoutResId, datas);
        mContext = context;
        mData = datas;
    }

    @Override
    protected void convert(ViewHolder helper, final SystemEntity item) {
        helper.setText(R.id.item_system_title, item.getName());
        RecyclerView rv = helper.getView(R.id.item_system_rv);
        FlexboxLayoutManager manager = new FlexboxLayoutManager(mContext);
        manager.setFlexDirection(FlexDirection.ROW);
        manager.setJustifyContent(JustifyContent.FLEX_START);
        rv.setLayoutManager(manager);
        rv.setHasFixedSize(true);
        rv.setItemViewCacheSize(200);
        rv.setNestedScrollingEnabled(false);
        SystemChildAdapter adapter = new SystemChildAdapter(
                mContext, R.layout.flow_layout, item.getChildren());
        rv.setAdapter(adapter);
    }
}