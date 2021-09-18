package com.xiongz.wanjava.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

/**
 * gps工具类
 *
 * @author xiongz
 * @date 2020-01-20
 */
public class GpsUtil {


    /**
     * 判断是否打开GPS
     *
     * @param context
     */
    public static void initGPS(final Activity context, GpsCallBack callBack) {
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        // 判断GPS模块是否开启，如果没有则开启
        if (!locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle("提示");
            dialog.setMessage("该功能需要使用位置信息,请问是否打开GPS?");
            dialog.setPositiveButton("是",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // 转到手机设置界面，用户设置GPS
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            // 设置完成后返回到原来的界面
                            context.startActivityForResult(intent, 0);
                        }
                    });
            dialog.setNeutralButton("否", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            dialog.show();
        } else {
            if (callBack != null) callBack.onGps();
        }
    }

    public interface GpsCallBack {
        void onGps();
    }
}
