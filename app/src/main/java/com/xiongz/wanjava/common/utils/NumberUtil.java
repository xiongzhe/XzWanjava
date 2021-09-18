package com.xiongz.wanjava.common.utils;

import java.text.DecimalFormat;

/**
 * number工具类
 * Created by xiongz on 2018/1/10.
 */
public class NumberUtil {

    /**
     * 保留1位小数
     *
     * @return
     */
    public static String getDoubleOneDecimals(double data) {
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        String result = "0.0";
        if (data != 0) {

            result = decimalFormat.format(data);
        }
        return result;
    }

    /**
     * 保留两位小数
     *
     * @return
     */
    public static String getDoubleTwoDecimals(double data) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String result = "0.00";
        if (data != 0) {
            result = decimalFormat.format(data);
        }
        return result;
    }

    /**
     * 处理超过一万字符
     *
     * @return
     */
    public static String dealOutWanStr(float targetFloat) {
        if (targetFloat < 10000) { //处理目标业绩,如果小于一百万
            return String.format("%.2f", targetFloat);
        } else { //如果大于万
            float tmp = targetFloat / 10000;
            return String.format("%.2f", tmp) + "万";
        }
    }

    /**
     * 获取六位数的随机数
     *
     * @return
     */
    public static int getRandomSixNumber() {
        return (int)((Math.random()*9+1)*100000);
    }
}
