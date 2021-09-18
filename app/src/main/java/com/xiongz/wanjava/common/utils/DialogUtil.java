package com.xiongz.wanjava.common.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.xiongz.android.core.util.time.TimeUtil;
import com.xiongz.wanjava.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 弹窗工具类
 *
 * @author xiongz
 * @date 2018/12/20
 */
public class DialogUtil {

    /**
     * 功能开发中弹窗
     */
    public static void showUnOpenDialog(Context context) {
        final Dialog dialog = new Dialog(context, R.style.dialog_black);
        dialog.setContentView(R.layout.dialog_unopen);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        ImageView ivClose = dialog.findViewById(R.id.iv_unopen_close);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView tvClose = dialog.findViewById(R.id.tv_unopen_close);
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
