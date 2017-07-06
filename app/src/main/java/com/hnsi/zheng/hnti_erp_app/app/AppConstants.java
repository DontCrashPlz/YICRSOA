package com.hnsi.zheng.hnti_erp_app.app;


import android.os.Environment;

/**
 * 用于放置APP中使用的一些常数
 * Created by Zheng on 2016/4/29.
 */
public class AppConstants {
    //碎片界面（信息）的索引
    public static final int FRAGMENT_MSG_INDEX=0;
    //碎片界面（通讯录）的索引
    public static final int FRAGMENT_CONTACTS_INDEX=1;
    //碎片界面（应用）的索引
    public static final int FRAGMENT_APPLY_INDEX=2;
    //碎片界面（我的）的索引
    public static final int FRAGMENT_MINE_INDEX=3;

    //待办事项列表向事件详情页跳转时Intent携带数据的key
    public static final String UNFINISHED_MATTER_ENTITY="unfinished_matter_entity";
    //已办事项列表向事件详情页跳转时Intent携带数据的key
    public static final String FINISHED_MATTER_ENTITY="finished_matter_entity";
    //向事件详情页跳转时Intent携带的processInstID
    public static final String MATTER_PROCESSINSTID="matter_processinstid";

    //待办事项列表附表页跳转时Intent携带HashMap的key
    public static final String MATTER_TABLEVIEW_LINKEDHASHMAP="matter_tableview_linkedhashmap";
    //待办事项列表附表页跳转时Intent携带title的key
    public static final String MATTER_TABLEVIEW_TITLE="matter_tableview_title";

    //map中的null的标识
    public static final String VALUE_NULL_IN_MAP="nul";

    //跳转到html详情页时携带数据的key
    public static final String HTML_CONTENT="html_content";

    //跳转到审批记录界面时携带的数据的key
    public static final String HISTORY_LIST="history_list";
    public static final String MATTER_TITLE="matter_title";

    //全部新闻公告classId
    public static final int NOTICE_NEWS_ALL=0;
    //公司通知classId
    public static final int NOTICE_COMPANY=1;
    //部门通知classId
    public static final int NOTICE_DEPARTMENT=2;
    //内部新闻classId
    public static final int NEWS_INSIDE =3;
    //外部新闻classId
    public static final int NEWS_OUTER =4;

    /** 用于访问新闻接口的参数type，type=1访问公告列表，type=2访问新闻列表 */
    //公告列表type
    public static final int NOTICE_TYPE=1;
    //新闻列表type
    public static final int NEWS_TYPE=2;

    //SDcard路径
    public final static String SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    //app根文件夹
    public final static String APP_ROOT_FOLDER = SDCARD_PATH + "/YIRC_SOA/";
    //图片加载路径
    public final static String APP_COVER_IMAGE_FOLDER = APP_ROOT_FOLDER + "covers/";
    //附件加载路径
    public final static String APP_FILE_DOWNLOAD_FOLDER = APP_ROOT_FOLDER + "downloads/";

    //通讯录人员数量key
    public final static String CONTACTS_SUM="contacts_sum";

    //更新信息key
    public final static String UPDATE_INFO_KEY="update_info";
    //版本信息实体key
    public final static String UPDATE_INFO_ENTITY_KEY="update_info_entity";
    //更新信息value,需要更新
    public final static String UPDATE_NEED="update_need";
    //更新信息value，不需要更新
    public final static String UPDATE_UNNEED="update_unneed";
    //更新信息value，更新服务异常
    public final static String UPDATE_EXCEPTION="update_exception";

    /** 审批列表跳转审批详情界面的请求码 */
    public final static int UNFINISHED_REQUESTCODE=100;
    /** 审批详情成功的返回码 */
    public final static int UNFINISHED_RESULTCODE=101;

}
