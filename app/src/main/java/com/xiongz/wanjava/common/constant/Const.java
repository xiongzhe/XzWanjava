package com.xiongz.wanjava.common.constant;

import com.xiongz.wanjava.common.net.ConstUrl;

import java.util.Arrays;
import java.util.List;

/**
 * 相关常量
 *
 * @author xiongz
 * @date 2018/9/5
 */
public class Const {

    // 首页欢迎图片
    public static final String welcome_image = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic3.zhimg.com%2F50%2Fv2-e438405c36345785c3ca590962d3835b_hd.jpg&refer=http%3A%2F%2Fpic3.zhimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1632985320&t=08c0eeff5ada401461febce1efb7a88b";

    // 上传图片广播接收action
    public final static String IMAGE_UPLOAD_ACTION = "image_upload_status";

    // 是否同意隐私政策
    public static final String SP_PRIVACY_TAG = "privacy";

    // 查看薪资是否要二次验证
    public static final String SP_SALARY_CHECK_TAG = "salary_check";

    // 是否有物料
    public static String[] MATERIAL_ITEMS = {"无", "有"};
    // 覆盖情况
    public static String[] COVER_ITEMS;
    // 陈列等级
    public static String[] DISPLAY_ITEMS;


    // 定位类型对照
    public static final String LOCATION_TYPE_JSON = "{" +
            "\"0\":\"定位失败\", " +
            "\"1\":\"GPS定位\", " +
            "\"2\":\"前次定位\", " +
            "\"4\":\"缓存定位\", " +
            "\"5\":\"Wifi定位\", " +
            "\"6\":\"基站定位\", " +
            "\"8\":\"离线定位\", " + "}";

    // 性别
    public final static String[] GENDER = {"女", "男"};


    // 页记录数
    public static final int PAGE_SIZE = 10;

    // 报表最顶层parentId
    public static final int FORM_TOP_PARENT_ID = 1276;


    // 上传定位操作actionType
    public static class LocationActionType {
        public static final int ACTION_TYPE_ACTUAL = 1;        // 实时
        public static final int ACTION_TYPE_VISIT_SIGN_IN = 2; // 拜访签到
        public static final int ACTION_TYPE_BUS_SIGN_IN = 3;   // 外勤签到
    }

    // 消息类型Type
    public static class MsgType {
        public static final String MSG_NOTICE = "oa_notice";        // 通知
        public static final String MSG_SYSTEM = "system";           // 系统
        public static final String MSG_CONFERENCE = "oa_conference";// 会议
        public static final String MSG_AGENDA = "oa_agenda";        // 日程
    }

    // 客户类型Type
    public static class ClientType {
        public static final int CLIENT_TYPE_FACTORY = 402;    // 厂家客户
        public static final int CLIENT_TYPE_BUSINESS = 403;   // 商业客户
        public static final int CLIENT_TYPE_CHAIN = 404;      // 连锁总部
        public static final int CLIENT_TYPE_TERMINAL = 405;   // 终端客户
    }

    // 文件类型
    public static class FileType {
        public static final int FILE_TYPE_WAVE = 611;            // 浪花报id
        public static final int FILE_TYPE_NEW_YOUTH = 612;       // 新青年id
    }

    // 业务线id
    public static class BusinessType {
        public static final int BUSINESS_LINE_OTC = 19;             // OTC业务线id
        public static final int BUSINESS_LINE_CHUYUAN = 20;         // 初元业务线id
        public static final int BUSINESS_LINE_CHUFANGYAO = 23;      // 处方药业务线id
    }

    // 系统参数
    public static class SystemParam {
        public static final int SYSTEM_PARAM_CLIENT_TYPE_PID = 401;      // 客户类型树形
        public static final int SYSTEM_PARAM_CLIENT_LEVEL_PID = 461;     // 客户等级
        public static final int SYSTEM_PARAM_BUSLINE_PID = 1;            // 业务线
        public static final int SYSTEM_PARAM_BUS_AREA_PID = 422;         // 营业面积
        public static final int SYSTEM_PARAM_POST_PID = 439;             // 联系人岗位
        public static final int SYSTEM_PARAM_DISPLAY_PID = 483;          // 陈列等级
        public static final int SYSTEM_PARAM_COVER_PID = 488;            // 覆盖情况
        public static final int SYSTEM_PARAM_TAKE_PHOTO_TYPE = 451;      // 门店拍照类型
        public static final int SYSTEM_PARAM_SIGN_IN_TYPE = 475;         // 签到类型
        public static final int SYSTEM_PARAM_BANK_TYPE = 440;            // 银行类型
        public static final int SYSTEM_PARAM_TERMINAL_TYPE = 405;        // 终端类型id
        public static final int SYSTEM_PARAM_NEWS_TYPE = 605;            // 新闻类型id
        public static final int SYSTEM_PARAM_FILE_TYPE = 606;            // 文件类型id
        public static final int SYSTEM_PARAM_NATION_TYPE = 6;            // 民族类型
        public static final int SYSTEM_PARAM_MATERIAL_TYPE = 768;        // 门店物资类型
        public static final int SYSTEM_PARAM_DAY_DETECTION_TYPE_DEV = 649;          // 日检测量(处方药拜访)测试环境
        public static final int SYSTEM_PARAM_DAY_TEACH_TYPE_DEV = 654;              // 日教育人次(处方药拜访) 测试环境
        public static final int SYSTEM_PARAM_DAY_DETECTION_TYPE_RELEASE = 649;      // 日检测量(处方药拜访)
        public static final int SYSTEM_PARAM_DAY_TEACH_TYPE_RELEASE = 654;          // 日教育人次(处方药拜访)

        // 获取APP需要的所有系统参数
        public static final String SYSTEM_PARAMS = "1,401,402,403,404,405,461,422,439,483,488,451,475,440,605,606,6,726,731,649,654,768";
    }

    // 老接口加密密钥
    public static class Signature {
        public static final String API_KEY = "8B510E8C16224E566EAD4CD9357E2E30"; // API密钥
        public static final String API_CODE = "1"; //  api版本号
    }

    // 职位类型
    public static class PostType {
        public static final int KA_LEADER = 240;        // KA主管
        public static final int SALE_STAFF = 45;       // 销售代表
        public static final int SALE_LEADER = 304;      // 销售主管
        public static final int AREA_MANAGER = 306;     // 区域经理
        public static final int PROVINCE_MANAGER = 307; // 省经理
        public static final int AREA_DIRECTOR = 503;    // 大区总监
        public static final int GENERAL_MANAGER = 504;  // 总经理
    }

    // 虚拟软件包名
    public static List<String> MockApp = Arrays.asList(
            "top.nightfarmer.vcamera"  // 虚拟相机
    );

    // 上传图片类型
    public static class UploadImgType {
        public static final String VISIT = "visit";                     // 拜访图片
        public static final String OUTSIGN = "outsign";                 // 外勤签到
        public static final String CUSTOMER = "customer";               // 客户门头
        public static final String BUS_LICENSE = "business_license";    // 营业执照
        public static final String GUIDE = "guide";                     // 挂金人员
        public static final String STAFF_FACADE = "guide_avatar";       // 店员照片
        public static final String SOCIAL_SECURITY = "guide_social_security";             // 店员社保证明
        public static final String CUS_COMPLAINT = "compliant";         // 客户投诉
        public static final String MARKET_CHECK = "sampling";           // 市场抽检
        public static final String MATERIALS = "materials";             // 门店物资
        public static final String WORK_REPORT = "work_report";         // 工作报告
        public static final String WORK_AVATAR = "employee/avatar";         // 我的证件照
    }

    // 网络线路下标
    public final static String NET_ROUTE_INDEX = "net_route_index";
    // 网络线路列表
    public final static String[] NET_ROUTES = {"服务线路1", "服务线路2", "服务线路3", "服务线路4"};
    public final static String[] NET_PING = {ConstUrl.PING_10000_1, ConstUrl.PING_10000_2, ConstUrl.PING_10010_1,ConstUrl.PING_10010_2};
    public final static String[] NET_ROUTES_DEV = {"测试服务", "服务线路1", "服务线路2", "服务线路3", "服务线路4"};
    public final static String[] NET_PING_DEV = {ConstUrl.PING_DEV, ConstUrl.PING_10000_1, ConstUrl.PING_10000_2, ConstUrl.PING_10010_1,ConstUrl.PING_10010_2};
}
