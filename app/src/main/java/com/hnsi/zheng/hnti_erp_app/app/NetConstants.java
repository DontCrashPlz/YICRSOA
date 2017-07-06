package com.hnsi.zheng.hnti_erp_app.app;

/**
 * 存放一些网络请求需要的常量
 * Created by Zheng on 2016/5/16.
 */
public class NetConstants {

    /** 自动更新版本信息xml文件地址 */
    public static final String UPDATEINFOXML="/default/portal/appdownload/android-app/version.xml";

    /** 登录接口 */
    public static final String LOGIN_PORT="/default/mobile/user/com.hnsi.erp.mobile.user.LoginManager.login.biz.ext";
    /** 登录参数一 */
    public static final String LOGIN_PARAM_ONE="username";
    /** 登录参数二 */
    public static final String LOGIN_PARAM_TWO="password";

    /** 通讯录接口 */
    public static final String CONTACTS_PORT="/default/mobile/user/com.hnsi.erp.mobile.user.AddressListManager.list.biz.ext";

    /** 通讯录搜索接口 */
    public static final String CONTACTS_SEARCH_PORT="/default/mobile/user/com.hnsi.erp.mobile.user.AddressListManager.search.biz.ext";
    /** 通讯录搜索参数 */
    public static final String CONTACTS_SEARCH_PARAM="name";

    /** 注销接口 */
    public static final String LOGOUT_PORT="/default/mobile/user/com.hnsi.erp.mobile.user.LoginManager.logout.biz.ext";

    /** 修改密码接口 */
    public static final String CHANGE_PASSWORD_PORT="/default/mobile/user/com.hnsi.erp.mobile.user.LoginManager.updatePassword.biz.ext";
    /** 修改密码参数一 */
    public static final String CHANGE_PASSWORD_PARAM_ONE="newPassword";
    /** 修改密码参数二 */
    public static final String CHANGE_PASSWORD_PARAM_TWO="oldPassword";

    /** 新闻列表接口 */
    public static final String NEWS_LIST_PORT="/default/mobile/oa/com.hnsi.erp.mobile.oa.NewsSearch.list.biz.ext";
    /** 新闻列表参数一 */
    public static final String NEWS_LIST_PARAM_ONE="pageIndex";
    /** 新闻列表参数二 */
    public static final String NEWS_LIST_PARAM_TWO="classId";
    /** 新闻列表参数三 */
    public static final String NEWS_LIST_PARAM_THREE="type";

    /** 新闻详情接口 */
    public static final String NEWS_DETAIL_PORT="/default/mobile/oa/com.hnsi.erp.mobile.oa.NewsSearch.getNews.biz.ext";
    /** 新闻详情参数 */
    public static final String NEWS_DETAIL_PARAM="id";

    /** 规章制度列表接口 */
    public static final String RULES_LIST_PORT="/default/mobile/oa/com.hnsi.erp.mobile.oa.RulesSearch.list.biz.ext";
    /** 规章制度列表参数 */
    public static final String RULES_LIST_PARAM_ONE="pageIndex";

    /** 规章制度详情接口 */
    public static final String RULES_DETAIL_PORT="/default/mobile/oa/com.hnsi.erp.mobile.oa.RulesSearch.detail.biz.ext";
    /** 规章制度详情参数 */
    public static final String RULES_DETAIL_PARAM="id";
    /** 规章制度附件下载接口 */
    public static final String RULES_FILES_PORT="/default/erp/common/fileUpload/download.jsp?isOpen=false&fileid=";

    /** 审批页面待办事项列表接口 */
    public static final String UNFINISHED_LIST_PORT="/default/mobile/oa/com.hnsi.erp.mobile.oa.TaskAuditSearch.pendingList.biz.ext";
    /** 审批页面待办事项列表参数 */
    public static final String UNFINISHED_LIST_PARAM="pageIndex";

    /** 审批页面已办事项列表接口 */
    public static final String FINISHED_LIST_PORT="/default/mobile/oa/com.hnsi.erp.mobile.oa.TaskAuditSearch.finishedList.biz.ext";
    /** 审批页面已办事项列表参数 */
    public static final String FINISHED_LIST_PARAM="pageIndex";


    /** 登录网络请求的Tag标签 */
    public static final String TAG_LOGIN="tag_login";
    /** 注销网络请求的Tag标签 */
    public static final String TAG_LOGOUT="tag_logout";
    /** 修改密码网络请求的Tag标签 */
    public static final String TAG_CHANGE_PASSWORD="tag_change_password";
    /** 通讯录网络请求的Tag标签 */
    public static final String TAG_CONTACTS="tag_contact";
    /** 公告列表网络请求的Tag标签 */
    public static final String TAG_NEWS_LIST="tag_news_list";
    /** 公告详情网络请求的Tag标签 */
    public static final String TAG_NEWS_DETAIL="tag_news_detail";


    /** 审批记录列表接口 */
    public static final String APPROVAL_HISTORY_PORT="/default/mobile/oa/com.hnsi.erp.mobile.oa.TaskAuditSearch.queryDisposeLog.biz.ext";
    /** 审批记录列表参数 */
    public static final String APPROVAL_HISTORY_PARAM="processInstID";

    /** 待办事项数量统计接口 */
    public static final String MATTER_NUM_PORT="/default/mobile/oa/com.hnsi.erp.mobile.oa.TaskSearch.count.biz.ext";

    /** 代办流程数量统计 */
    public static final String FLOW_NUM_PORT="/default/mobile/oa/com.hnsi.erp.mobile.oa.TaskAuditSearch.count.biz.ext";


    /** 系统信息列表接口 */
    public static final String MESSAGE_LIST_PORT="/default/mobile/oa/com.hnsi.erp.mobile.oa.TaskSearch.msgList.biz.ext";
    /** 系统信息列表参数 */
    public static final String MESSAGE_LIST_PARAM="pageIndex";

    /** 系统信息详情接口 */
    public static final String MESSAGE_DETAIL_PORT="/default/mobile/oa/com.hnsi.erp.mobile.common.MsgManager.getData.biz.ext";
    /** 系统信息详情参数 */
    public static final String MESSAGE_DETAIL_PARAM="id";

    /** 头像上传接口 */
    public static final String UPLOADING_HEADIMG_PORT="/default/mobile/user/com.hnsi.erp.mobile.user.AddressListManager.upload.biz.ext";

    /** 天气接口 */
    public static final String WEATHER_PORT="http://c.3g.163.com/nc/weather/";

    /** 附件下载接口 */
    public static final String FILES_DOWNLOAD_PORT="/default/mobile/oa/download.jsp?isOpen=false&fileid=";

}
