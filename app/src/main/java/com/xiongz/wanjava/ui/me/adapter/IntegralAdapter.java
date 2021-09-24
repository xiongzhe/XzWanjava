package com.xiongz.wanjava.ui.me.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xiongz.android.core.ui.recycler.ViewHolder;
import com.xiongz.wanjava.R;
import com.xiongz.wanjava.ui.me.entity.IntegralEntity;

import java.util.List;

/**
 * 积分适配器
 *
 * @author xiongz
 * @date 2021/9/1
 */
public class IntegralAdapter extends BaseQuickAdapter<IntegralEntity, ViewHolder> {

    private Context mContext;
    private List<IntegralEntity> mData;
    private int mRank;

    public IntegralAdapter(Context context, int layoutResId, List<IntegralEntity> datas, int rank) {
        super(layoutResId, datas);
        mContext = context;
        mData = datas;
        mRank = rank;
    }

    @Override
    protected void convert(ViewHolder helper, final IntegralEntity item) {
        if (mRank == helper.getAdapterPosition() + 1) {
            helper.setTextColor(R.id.item_integral_rank, mContext.getResources().getColor(R.color.colorPrimary));
            helper.setTextColor(R.id.item_integral_name, mContext.getResources().getColor(R.color.colorPrimary));
            helper.setTextColor(R.id.item_integral_count, mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            helper.setTextColor(R.id.item_integral_rank, ContextCompat.getColor(mContext, R.color.colorBlack333));
            helper.setTextColor(R.id.item_integral_name, ContextCompat.getColor(mContext, R.color.colorBlack666));
            helper.setTextColor(R.id.item_integral_count, ContextCompat.getColor(mContext, R.color.textHint));
        }
        helper.setText(R.id.item_integral_rank, String.valueOf(helper.getAdapterPosition() + 1));
        helper.setText(R.id.item_integral_name, item.getUsername());
        helper.setText(R.id.item_integral_count, String.valueOf(item.getCoinCount()));

    }
}