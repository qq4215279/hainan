package com.gobestsoft.common.constant;

/**
 * 缓存名称
 * create by gutongwei
 * on 2018/8/15 下午8:19
 */
public class CacheConstant {

    /**
     * App配置文件缓存名称
     */
    public static final String APP_CONFIG_JSON = "APP_CONFIG_JSON";

    /**
     * 首页V2接口
     */
    public static final String API_HOME_V2 = "API_HOME_V2";


    /**
     * 心灵驿站
     */
    public static final String APP_HEART_POST = "APP_HEART_POST_";

    /**
     * 消息缓存
     */
    static final String APP_MESSAGE_CACHE = "APP_MESSAGE_CACHE_";

    /**
     * 法律援助和困难帮扶所有的字典
     */
    public static final String APP_SRV_PARAMETERS = "APP_SRV_PARAMETERS";


    /**
     * 法律课程首页
     */
    public static final String APP_LAW_FORUM = "APP_LAW_FORUM";

    /**
     * APP字典管理
     */
    public static final String APP_DICT_CACHE = "APP_DICT_CACHE_";


    /**
     * 政务平台首页
     */
    public static final String ZWPT_BLACKBOARD = "ZWPT_BLACKBOARD";

    /**
     * 组织信息
     */
    static final String DEPT = "DEPT_";


    /**
     * 后台管理
     */
    public static final String SETTING_CONFIG = "SETTING_CONFIG";

    /**
     * word下载 -- 困难帮扶、法律援助
     */
    public static final String Straitened_Contact = "Straitened_Contact_";
    public static final String Straitened_Study = "Straitened_Study_";
    public static final String Straitened_Medical = "Straitened_Medical_";
    public static final String Law_Support = "Law_Support_";
    public static final String Law_Support_Contacts = "Law_Support_Contacts_";

    /**
     * 各市县产业系统工会实名制数据
     */
    public static final String MEMBER_REAL_DATA = "Member_Real_Data";

    /**
     * 获取组织数名
     *
     * @param orgId
     * @return
     */
    public static String getDeptTreeCache(Integer orgId) {
        return DEPT + "TREE_" + orgId;
    }

    /**
     * 当下部门的tree名
     *
     * @param orgId
     * @return
     */
    public static String getNoDeptTreeCache(Integer orgId) {
        return DEPT + "NO_TREE_" + orgId;
    }


    /**
     * 字典
     */
    static final String DICT = "DICT_";


    /**
     * 所有的字段列表
     *
     * @param
     * @return
     */
    public static String getAllDict() {
        return DICT;
    }


    /**
     * 字典组名称
     *
     * @param groupCode
     * @return
     */
    public static String getDictGroupName(String groupCode) {
        return DICT + groupCode;
    }

    /**
     * 字典组单数据名称
     *
     * @param groupCode
     * @return
     */
    public static String getDictSingleName(String groupCode, String code) {
        return DICT + groupCode + "_" + code;
    }


    /**
     * 获取消息缓存名称
     *
     * @param auid
     * @return
     */
    public static String getMessageCache(String auid) {
        return APP_MESSAGE_CACHE + auid;
    }

    /**
     * 获取组织树列表
     */
    public static String getDeptTreeListCache(Integer deptId){
        return DEPT + "TREELIST_" + deptId;
    }
}
