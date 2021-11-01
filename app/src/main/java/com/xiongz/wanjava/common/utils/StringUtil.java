package com.xiongz.wanjava.common.utils;

import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.xiongz.wanjava.R;
import com.xiongz.wanjava.common.net.ConstUrl;
import com.xiongz.wanjava.common.web.CommonWebActivity;

import java.util.Random;

/**
 * 字符串工具类
 */
public class StringUtil {

    public static final int[][] OTC_CULTURE_RES = {
            {R.string.otc_culture1_1, R.string.otc_culture1_2, R.string.otc_culture1_3, R.string.otc_culture1_4},
            {R.string.otc_culture2_1, R.string.otc_culture2_2, R.string.otc_culture2_3, R.string.otc_culture2_4},
            {R.string.otc_culture3_1, R.string.otc_culture3_2, R.string.otc_culture3_3, R.string.otc_culture3_4, R.string.otc_culture3_5},
            {R.string.otc_culture4_1, R.string.otc_culture4_2, R.string.otc_culture4_3}};

    // 前一种颜色
    private static int preInt;

    /**
     * 随机获取公司文化
     *
     * @return
     */
    public static int[] getRandomOTCCulture() {
        int randomInt = new Random().nextInt(OTC_CULTURE_RES.length);
        if (randomInt == preInt) {
            return getRandomOTCCulture();
        } else {
            preInt = randomInt;
            return OTC_CULTURE_RES[randomInt];
        }
    }

    /**
     * 获取隐私政策可点击的SpannableString
     *
     * @return
     */
    public static SpannableString getClickableSpan(Context context, String text) {
        int index1 = text.indexOf("用户协议");
        int index2 = text.indexOf("隐私政策");
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Bundle bundle = new Bundle();
                bundle.putString(CommonWebActivity.WEB_TITLE, "用户协议");
                // bundle.putString(CommonWebActivity.WEB_URL, ConstUrl.PROTOCOL);
                bundle.putString(CommonWebActivity.WEB_URL, "https://www.baidu.com");
                ActivityUtils.startActivity(bundle, CommonWebActivity.class);
            }
        }, index1 - 1, index1 + 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary)),
                index1 - 1, index1 + 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Bundle bundle = new Bundle();
                bundle.putString(CommonWebActivity.WEB_TITLE, "隐私政策");
                // bundle.putString(CommonWebActivity.WEB_URL, ConstUrl.PRIVACY);
                bundle.putString(CommonWebActivity.WEB_URL, "https://www.baidu.com");
                ActivityUtils.startActivity(bundle, CommonWebActivity.class);
            }
        }, index2 - 1, index2 + 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary)),
                index2 - 1, index2 + 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 获取设备唯一标识
     *
     * @return
     */
    public static String getImei() {
        return DeviceUtils.getUniqueDeviceId();
    }
}
