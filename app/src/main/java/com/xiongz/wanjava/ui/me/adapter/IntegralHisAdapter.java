package com.xiongz.wanjava.ui.me.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xiongz.android.core.ui.recycler.ViewHolder;
import com.xiongz.android.core.util.time.TimeUtil;
import com.xiongz.wanjava.R;
import com.xiongz.wanjava.ui.me.entity.IntegralEntity;
import com.xiongz.wanjava.ui.me.entity.IntegralHisEntity;

import java.util.List;

/**
 * 积分记录适配器
 *
 * @author xiongz
 * @date 2021/9/1
 */
public class IntegralHisAdapter extends BaseQuickAdapter<IntegralHisEntity, ViewHolder> {

    private Context mContext;
    private List<IntegralHisEntity> mData;

    public IntegralHisAdapter(Context context, int layoutResId, List<IntegralHisEntity> datas) {
        super(layoutResId, datas);
        mContext = context;
        mData = datas;
    }

    @Override
    protected void convert(ViewHolder helper, final IntegralHisEntity item) {
//        val descStr =
//        if (desc.contains("积分")) desc.subSequence(desc.indexOf("积分"), desc.length) else ""
//        helper.setText(R.id.item_integralhistory_title, reason + descStr)
//        helper.setText(R.id.item_integralhistory_date, TimeUtil.getTimeFormatText(item.getDate());
//        helper.setText(R.id.item_integralhistory_count, "+$coinCount")
//        helper.setTextColor(R.id.item_integralhistory_count, mContext.getResources().getColor(R.color.colorPrimary));
    }
}