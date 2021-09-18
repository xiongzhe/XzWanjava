package com.xiongz.wanjava.common.upgrade;

/**
 * 更新文本
 *
 * @author xiongz
 * @date 2020/6/29
 */
class UpgradeEntity {

    // app名
    private String app_name;
    // app版本信息
    private AppVersionInfoBean app_version_info;
    // 服务器信息
    private ServerInfoBean server_info;

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public AppVersionInfoBean getApp_version_info() {
        return app_version_info;
    }

    public void setApp_version_info(AppVersionInfoBean app_version_info) {
        this.app_version_info = app_version_info;
    }

    public ServerInfoBean getServer_info() {
        return server_info;
    }

    public void setServer_info(ServerInfoBean server_info) {
        this.server_info = server_info;
    }

    public static class AppVersionInfoBean {
        // apk下载地址
        private String download_url;
        // 最新版本
        private int new_version;
        // 更新文本
        private String upgrade_content;
        // 是否强制更新
        private int is_forced_upgrade;

        public String getDownload_url() {
            return download_url;
        }

        public void setDownload_url(String download_url) {
            this.download_url = download_url;
        }

        public int getNew_version() {
            return new_version;
        }

        public void setNew_version(int new_version) {
            this.new_version = new_version;
        }

        public String getUpgrade_content() {
            return upgrade_content;
        }

        public void setUpgrade_content(String upgrade_content) {
            this.upgrade_content = upgrade_content;
        }

        public int getIs_forced_upgrade() {
            return is_forced_upgrade;
        }

        public void setIs_forced_upgrade(int is_forced_upgrade) {
            this.is_forced_upgrade = is_forced_upgrade;
        }
    }

    public static class ServerInfoBean {
        // 服务器是否正在更新
        private int server_is_update;
        // 服务器更新文本
        private String server_update_content;

        public int getServer_is_update() {
            return server_is_update;
        }

        public void setServer_is_update(int server_is_update) {
            this.server_is_update = server_is_update;
        }

        public String getServer_update_content() {
            return server_update_content;
        }

        public void setServer_update_content(String server_update_content) {
            this.server_update_content = server_update_content;
        }
    }
}
