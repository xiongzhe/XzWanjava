package com.xiongz.wanjava.common.images;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * DialogFragment
 * 防止出现 IllegalStateException: Can not perform this action after onSaveInstanceState 异常
 *
 * @author xiongz
 * @date 2019/1/18
 */
public class BaseDialogFragment extends DialogFragment {

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            Class c = Class.forName("android.support.v4.app.DialogFragment");
            Constructor con = c.getConstructor();
            Object obj = con.newInstance();
            Field dismissed = c.getDeclaredField("mDismissed");
            dismissed.setAccessible(true);
            dismissed.set(obj, false);
            Field shownByMe = c.getDeclaredField("mShownByMe");
            shownByMe.setAccessible(true);
            shownByMe.set(obj, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }
}
