package com.xiongz.wanjava.common.utils;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 视图工具类
 *
 * @author xiongz
 * @date 2018/9/8
 */
public class ViewUtil {

    /**
     * 获取所有子视图
     *
     * @param view
     * @return
     */
    public static List<View> getChildrenViews(View view) {
        List<View> children = new ArrayList<>();
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View child = vp.getChildAt(i);
                children.add(child);
                children.addAll(getChildrenViews(child));
            }
        }
        return children;
    }

    /**
     * 获取所有RecyclerView子视图
     *
     * @param view
     * @return
     */
    public static List<RecyclerView> getChildrenRecyclerViews(View view) {
        List<RecyclerView> children = new ArrayList<>();
        List<View> views = ViewUtil.getChildrenViews(view);
        for (int i = 0; i < views.size(); i++) {
            View child = views.get(i);
            if (child instanceof RecyclerView) {
                children.add((RecyclerView) child);
            }
        }
        return children;
    }

    /**
     * 获取所有TextView子视图
     *
     * @param view
     * @return
     */
    public static List<TextView> getChildrenTextViews(View view) {
        List<TextView> children = new ArrayList<>();
        List<View> views = ViewUtil.getChildrenViews(view);
        for (int i = 0; i < views.size(); i++) {
            View child = views.get(i);
            if (child instanceof TextView && !(child instanceof IconTextView)) {
                children.add((TextView) child);
            }
        }
        return children;
    }

    /**
     * IconTextView
     *
     * @param view
     * @return
     */
    public static List<IconTextView> getChildrenIconTextViews(View view) {
        List<IconTextView> children = new ArrayList<>();
        List<View> views = ViewUtil.getChildrenViews(view);
        for (int i = 0; i < views.size(); i++) {
            View child = views.get(i);
            if (child instanceof IconTextView) {
                children.add((IconTextView) child);
            }
        }
        return children;
    }

    /**
     * 获取所有tag子视图
     *
     * @param view
     * @param tag
     * @return
     */
    public static List<View> getChildrenViews(View view, String tag) {
        List<View> children = new ArrayList<>();
        String viewTag = String.valueOf(view.getTag());
        if (TextUtils.equals(tag, viewTag)) {
            children.add(view);
            return children;
        } else {
            if (view instanceof ViewGroup) {
                ViewGroup vp = (ViewGroup) view;
                for (int i = 0; i < vp.getChildCount(); i++) {
                    View child = vp.getChildAt(i);
                    String childTag = String.valueOf(child.getTag());
                    if (TextUtils.equals(tag, childTag)) {
                        children.add(child);
                    } else {
                        children.addAll(getChildrenViews(child, tag));
                    }
                }
            }
        }
        return children;
    }
}
