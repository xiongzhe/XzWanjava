package com.xiongz.wanjava.ui.me.entity;

/**
 * 网络线路
 *
 * @author xiongz
 * @date 2019/1/31
 */
public class NetRouteEntity {

    // 网络线路
    private String netRoute;
    // 网络延迟
    private float netDelay = -2.0f;
    // 是否选择
    private boolean isSelected;

    public String getNetRoute() {
        return netRoute;
    }

    public void setNetRoute(String netRoute) {
        this.netRoute = netRoute;
    }

    public float getNetDelay() {
        return netDelay;
    }

    public void setNetDelay(float netDelay) {
        this.netDelay = netDelay;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
