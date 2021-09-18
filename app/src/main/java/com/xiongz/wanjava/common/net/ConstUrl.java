package com.xiongz.wanjava.common.net;

/**
 * 请求url
 * Created by xiongz on 2017/12/14.
 */
public class ConstUrl {

    // ping地址
    public static String PING_DEV = "www.wanandroid.com";            // 开发环境
    public static String PING_10000_1 = "www.wanandroid.com";        // 正式环境-电信1
    public static String PING_10000_2 = "www.wanandroid.com";        // 正式环境-电信2
    public static String PING_10010_1 = "www.wanandroid.com";        // 正式环境-联通1
    public static String PING_10010_2 = "www.wanandroid.com";        // 正式环境-联通2


    /********** wan url ************/

    // 文章列表
    public static final String ARTICLE_LIST = "/article/list/{page}/json";
    // 首页轮播图
    public static final String HOME_BANNER = "/banner/json";
    // 项目分类
    public static final String PROJECT_TREE = "/project/tree/json";
    // 最新项目列表
    public static final String PROJECT_LIST = "/article/listproject/{page}/json";
    // 根据分类id获取项目列表
    public static final String PROJECT_LIST_BY_TREE = "/project/list/{page}/json";
    // 公众号分类
    public static final String WX_ARTICLE_TREE = "/wxarticle/chapters/json";
    // 根据分类id获取公众号列表
    public static final String WX_LIST_BY_TREE = "/wxarticle/list/{id}/{page}/json";
    // 广场列表数据
    public static final String PLAZA_LIST_BY_TREE = "/user_article/list/{page}/json";
    // 每日一问列表数据
    public static final String ASK_LIST_BY_TREE = "/wenda/list/{page}/json";
    // 体系数据
    public static final String SYSTEM_TREE = "/tree/json";
    // 导航数据
    public static final String NAVIGATION_TREE = "/navi/json";
    // 登录
    public static final String APP_LOGIN = "/user/login";
    // 获取当前账户的个人积分
    public static final String INTEGRAL = "/lg/coin/userinfo/json";



    /********** 部分web/图片url ************/

    // 关于我们
    public static final String ABOUT_US = "http://oa.jzjt.com:8084/sale_app/gywm.html";
    // 免责声明
    public static final String STATEMENT = "http://oa.jzjt.com:8084/sale_app/mzsm.html";
    // 法律声明
    public static final String LAW = "http://moa.crjz.com:7073/law";
    // 用户协议
    public static final String PROTOCOL = "http://moa.crjz.com:7073/protocol";
    // 隐私政策 1 江中e网通 2 挂金App 3 营销e网通 4 智造e网通
    public static final String PRIVACY = "http://moa.crjz.com:7073/privacy?type=3";

}
