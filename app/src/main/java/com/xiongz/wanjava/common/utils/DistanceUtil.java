package com.xiongz.wanjava.common.utils;

import static com.blankj.utilcode.util.ObjectUtils.isEmpty;

/**
 * 距离工具类
 *
 * @author xiongz
 * @date 2018/9/9
 */
public class DistanceUtil {

    /**
     * 米 => 千米
     */
    public static String meterToKilometer(double meter) {
        if (meter >= 1000) {
            double result = meter / 1000.0f;
            return NumberUtil.getDoubleOneDecimals(result) + "km";
        } else {
            return meter + "m";
        }
    }

    /**
     * 返回两个经纬度距离
     */
    public static double calculateJWD(Double longitude, Double latitude, Double longitude2, Double latitude2) {
        try {
            if (isEmpty(latitude)
                    || isEmpty(longitude)
                    || (longitude == 0.0 && latitude == 0.0)
                    || isEmpty(longitude2)
                    || isEmpty(latitude2)) {
                return -1;
            }
            double radLat1 = (latitude * Math.PI / 180.0);
            double radLat2 = (latitude2 * Math.PI / 180.0);
            double a = radLat1 - radLat2;
            double b = (longitude - longitude2) * Math.PI / 180.0;
            double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                    + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
            s = s * 6378137; // 地球半径
            s = Math.round(s * 10000) / 10000;
//            return String.valueOf(meterToKilometer((long) s));
            return s;
        } catch (Exception e) {
            return -1;
        }
    }
}
