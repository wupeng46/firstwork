package com.lbs.commons;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Title: fcmis Core platform
 * </p>
 * <p>
 * Description: 系统配置信息常量集
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: lbs
 * </p>
 * @modified by chenkl
 * @version 1.0
 */

public class GlobalNames {

	/**
	 * 应用服务器JMS工厂
	 */
	public static String JMS_CONNECTION_FACTROY = "weblogic.jms.ConnectionFactory";
	/**
	 * 消息队列的JNDIname
	 */
	public static String JNDI_QUEUE_NAME = "LogMessageQueue";
	/**
	 * 应用服务器初始化工厂
	 */
	public static String INITIAL_FACTORY = "weblogic.jndi.WLInitialContextFactory";

	/**
	 * JMS的初试化URL
	 */
	public static String JMS_URL = "http://127.0.0.1:7001";

	/**
	 * 应用服务器的URL
	 */
	public static String URL_PROVIDER = "http://127.0.0.1:7001";
	/**
	 * 服务配置文件名
	 */
	public static String SERVICE_CONFIG_FILE = "/WEB-INF/conf/ServiceConfig.xml";
	/**
	 * 系统配置文件名
	 */
    public static String CONFIG_FILE = "/WEB-INF/conf/Config.xml";
    /**
     * license file
     */
    public static String LICENSE="/WEB-INF/conf/license.lbs";
	/**
	 * 缓存中的服务缓存的名字
	 */
	public static String SERVICE_CACHE_NAME = "serviceCache";
	/**
	 * EJB缓存的配置文件
	 */
	public static String EJBCACHE_CFG_FILE = "/WEB-INF/conf/EJBCache.xml";
	/**
	 * EJB缓存的名字
	 */
	public static String EJBCACHE_NAME = "ejbCache";

	//SafetyFilter中使用的常量
	/**
	 *web应用的名称 
	 */
	public static String WEB_APP = "/jtvshop";
	/**
	 * 首页面 
	 */	
	public static String INDEX_PAGE = "/Index.jsp";
	/**
	 * 左边的菜单页面
	 */
	public static String MENU_PAGE = "/Left.jsp";
	/**
	 * 登录页面
	 */
	public static String LOGON_PAGE = "/LogonDialog.jsp";
	
	/**
	 * 重登录页面
	 */
	public static String RELOGON_PAGE = "/login.html";
	/**
	 * 下线页面
	 */
	public static String LOGOFF_PAGE = "/Main.htm";
	/**
	 * 登录dialog
	 */
	public static String LOGON_DIALOG_PAGE = "/LogonDialog.jsp";
	/**
	 * 工作区
	 */
	public static String MAIN_PAGE = "/Main.jsp";
	/**
	 * 用户的权限列表，存放在Session中
	 */
	public static String FUNCTION_LIST = "function_list";
	/**
	 * 用户id
	 */
	public static String USERID = "userid";
	/**
	 * 用户密码
	 */
	public static String PASSWD = "passwd";
	/**
	 * 请求action的方法
	 */
	public static String METHOD = "method";
	/**
	 * 登录action
	 */
	public static String LOGON_ACTION = "/logonAction.do";
	/**
	 * 下线action
	 */
	public static String LOGOFF_ACTION = "/logoffAction.do";
	/**
	 * 重登录action
	 */
	public static String RELOGON_ACTION = "/relogonAction.do";
	/**
	 * 清理session中的form
	 */
	public static String CLEAN_SESSION_ACTION = "/cleanSessionAction.do";
	/**
	 * 存放当前用户的相关信息，存放在Session中
	 */
	public static String CURRENT_USER = "current_user";
	/**
	 * 编码
	 */
	public static String ENCODING = "encoding";
	/**
	 * 编码值
	 */
	public static String DEFAULT_ENCODING = "UTF-8";
	/**
	 * 分页的每页记录数,缺省为每页10条记录
	 */
	public static int PAGE_SIZE = 10;
	/**
	 * 是否能够清除session中的form，只有在请求菜单叶子时为true其它为false
	 */
	public static String CAN_CLEAR_FORM = "canclearform";
	/**
	 * 图形校验码在Session中的Key值
	 */
	public static String VERIFY_CODE="verifycode";
	/**
	 * 菜单请求
	 */
	public static String MENU_ACTION = "/menuAction.do";
	/**
	 * 权限列表的整棵树,存放在Session中
	 */
	public static String MENU_TREE = "menutree";
	/**
	 * 菜单叶子
	 */
	public static String MENU_LEAF = "0";
	/**
	 * 菜单节点
	 */
	public static String MENU_NODE = "1";
	/**
	 * 页面按钮
	 */
	public static String MENU_BUTTON = "2";
	/**
	 * top主菜单，存放在Session中
	 */
	public static String MAIN_MENU = "mainmenu";
	/**
	 * 左边菜单的xml，存放在Session中
	 */
	public static String MENU_XML = "menuxml";
	/**
	 * 对应"/menuAction.do?"后的参数名称
	 */
	public static String MENU_NAME = "menu";
	/**
	 * 授权的级别，"2"为按钮一级，"0"为菜单一级
	 */
	public static String RIGHT_LEVEL = "2";
	/**
	 * 树的类型是普通树
	 */
	public static String COMMON_TREE = "CommonTree";
	/**
	 * 树的类型是选择树
	 */
	public static String SELECT_TREE = "SelectTree"; 
	/**
	 * 权限树，用于用户授权
	 */
	public static String RIGHT_TREE_XML = "rightTreeXml"; 
	//exception key of fcmis
	/**
	 * 异常key
	 */
	public static String EXCEPTION_KEY = "com.lbs.lemis.Exception";
	/**
	 * 业务流转页面
	 */
	public static String BIZ_FORWARD = "com.lbs.lemis.bizforward";
	/**
	 * 业务流转消息
	 */
	public static String BIZ_FORWARD_MSG = "com.lbs.lemis.bizforwardmsg";
	/**
	 * 业务操作是否在action中记日志，“1”代表记日志，其它为否
	 */
	public static String ACTION_LOG = "1"; 
	/**
	 * 是否异步记录日志
	 */
	public static boolean LOG_IS_ASYNCH = false;
	/**
	 * 文件名的长度
	 */
	public static String TEMP_FILE_NAME_LENGTH = "9";
	/**
	 * 存放报表临时文件的文件夹名称
	 */
	public static String TEMP_FILE_PATH = "ReportTemp/";
	/**
	 * 存放图片临时文件的文件夹名称
	 */
    public static String TEMP_PHOTO_PATH = "/photo";
    /**
     * 放在request中的报表数据
     */
	public static String REPORT_DATA = "reportdata";
	/**
	 * 放在request中的通用查询数据
	 */
	public static String QUERY_DATA = "querydata"; 
	/**
	 * 放在request中的通用查询信息如总页数，当前页，总条数，hql等
	 */
	public static String QUERY_INFO = "queryinfo"; 
	/**
	 * 分页查询动作的参数名
	 */
	public static String PAGE_QUERY_ACTION = "pageQueryAction";
	/**
	 * 放在request中的用于更新的数据
	 */
	public static String UPDATE_DATA = "updatedata";
	/**
	 * 导出的放在request中的日志数据
	 */
	public static String LOG_DATA = "logdata";
	/**
	 * 用户的重置密码
	 */
    public static String RESET_PASSWD = "888888";
    
    /**
     * 无需过虑的请求
     */
    public static String[] FREE_REQUESTS;
    /**
     * LookupDispatchAction方法中英文对照表
     */
    public static String METHOD_MAP = "methodmap";
    /**
     * hql排序
     */
    public static String ORDER_BY = "order by";
    /**
     * 超级用户
     */
    public static String SUPER_USER = "admin"; 
    /**
     * 防止重复提交的key
     */
    public static String TOKEN_KEY="com.lbs.token.key";
    /**
     * 通用查询的hql的所存放的map，存放在Session中
     */
    public static String HQL_MAP = "hqlmap"; 
    /**
     * 导航的key值
     */
    public static String NAVIGATION = "navigation";
    /**
     * 显示table底边的小计信息
     */
    public static String PAGE_SUM = "pagesum";
    /**
     * 显示table底边的合计信息
     */
    public static String TOTAL_SUM = "totalsum";
    /**
     * 每日生成流水号
     */
    public static String DAILY_SEQUENCE_FILE = "C:\\cvs119\\leaf\\dailysequence";
    /**
     * 下一步的url
     */
    public static String NEXT_WIZARD = "nextwizard";
    /**
     * 向导在session中的key值
     */
    public static String WIZARD_URL = "wizardURL";
   /**
    * 关闭提示信息
    */
    public static String ALERT_MSG_OFF = "alertmsgoff";
    
//  数据导入校验配置
    /**
     * 将校验的规则配置缓存在ServletContext中的key值
     */
	public static String VALIDATE_RULES = "validateRules";
	/**
	 * 将校验的配置缓存在ServletContext中key值
	 */
	public static String VALIDATE_CONFIG = "validateConfig";
	/**
	 * jdbc batch size
	 */
	public static int JDBC_BATCH_SIZE = 50;
	
	/**
	 * 机构列表，存放在Session中(主要用于EXT树形结构显示)
	 */
	public static String SYSGROUP_LIST = "sysgroup_list";
	
	public static String COMM_CHECK_KEY_SET="1";  //1代表不加密，3代表加密
	public static String AESPASSWORD="bi/0GYzmwiWiE6vqYmHsbQ4gDZg0ouAW";
	public static String SHA512PASSWORD="HxMv40BMnW347w6nx/TyyBbL91RoNxY7";
	
	public static String BUY_ROLEID="3";  //委托方管理员角色编码
	public static String SALE_ROLEID="6";  //承运方管理员角色编码
	public static String PLATFORM_WORKTYPE="101"; //平台业务模式 (101自营 102 撮合)
	public static String PLATFORM_MEMBERID="1";       //平台默认委托方编码
	public static String PLATFORM_COMPANYID="1";      //平台默认承运商编码      
	public static String PLATFORM_AES_KEY="c5d608eb97d94123";  //平台交易aes加密key
	public static String PLATFORM_BALANCE_URL="http://foodeco.chinacloudapp.cn/v1/b2b/balance";  //资金平台查询余额URL
	public static String PLATFORM_APPID="wuliu";  //平台APPID
	public static String PLATFORM_RUNMODE="1";  //平台运行模式　1:与平台整合接口运行 2:系统独立运行
	
	public static String YUNSIGN_DOMAIN="https://www.yunsigntest.com";  //云签域名
	public static String YUNSIGN_REGUSER_URL="https://www.yunsigntest.com/mmecserver3.0/webservice/Common?wsdl";  //云签注册用户接口
	public static String YUNSIGN_APPID="5tJ8tSA51W";
	public static String YUNSIGN_KEY="vXm9lF9wfG5SsqK849sT";
	
	//阿里大鱼短信平台参数
	public static String SMS_TAOBAO_URL="http://gw.api.taobao.com/router/rest";
	public static String SMS_TAOBAO_APPKEY="23419355";
	public static String SMS_TAOBAO_SECRET="4d5b1a196e83f591a4762cfa0700cdfa";
	public static String SMS_TAOBAO_EXTEND="123456";
	public static String SMS_TAOBAO_FREESIGNNAME="\u4E1C\u5317\u7CAE\u7F51\u6D4B\u8BD5";   //"东北粮网测试";
	public static String SMS_TAOBAO_REGVERIFY_TEMPLATECODE="SMS_25115202";  //注册验证码模板代码
	public static String SMS_TAOBAO_FINDPASSWORDVERIFY_TEMPLATECODE="SMS_25250167";  //找回密码验证码模板代码

}
