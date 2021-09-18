package com.xiongz.wanjava.ui.me.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xiongz.android.core.ui.recycler.ViewHolder;
import com.xiongz.wanjava.R;
import com.xiongz.wanjava.ui.me.entity.NetRouteEntity;

import java.util.List;

/**
 * 网络连接线路选择列表适配器
 *
 * @author xiongz
 * @date 2019/1/31
 */
public class SelectNetAdapter extends BaseQuickAdapter<NetRouteEntity, ViewHolder> {

    private Context mContext;
    private List<NetRouteEntity> mData;

    public SelectNetAdapter(Context context, int layoutResId, List<NetRouteEntity> datas) {
        super(layoutResId, datas);
        mContext = context;
        mData = datas;
    }

    @Override
    protected void convert(final ViewHolder helper, final NetRouteEntity item) {
        helper.setText(R.id.tv_item_net_route, item.getNetRoute());
        float delay = item.getNetDelay();
        TextView tvDelay = helper.getView(R.id.tv_item_net_delay);
        if (delay > 0) {
            String netLevel;
            if (delay <= 100) {
                netLevel = "较快";
                tvDelay.setTextColor(mContext.getResources().getColor(R.color.colors_5CBF29));
            } else if (delay > 100 && delay <= 200) {
                netLevel = "较慢";
                tvDelay.setTextColor(mContext.getResources().getColor(R.color.colors_FF9900));
            } else {
                netLevel = "缓慢";
                tvDelay.setTextColor(mContext.getResources().getColor(R.color.colors_FF5733));
            }
            tvDelay.setText(TextUtils.concat(netLevel, "  网络延迟", String.valueOf(delay), "ms"));
        } else if (delay == -2.0f) {
            tvDelay.setTextColor(mContext.getResources().getColor(R.color.colors_FF5733));
            tvDelay.setText("连接中...");
        } else {
            tvDelay.setTextColor(mContext.getResources().getColor(R.color.colors_FF5733));
            tvDelay.setText("连接超时");
        }

        helper.setChecked(R.id.tv_item_net_route_select, item.isSelected());
    }
}
