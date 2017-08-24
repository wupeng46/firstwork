package com.lbs.commons;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Title: fcmis Core platform
 * </p>
 * <p>
 * Description: ϵͳ������Ϣ������
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
	 * Ӧ�÷�����JMS����
	 */
	public static String JMS_CONNECTION_FACTROY = "weblogic.jms.ConnectionFactory";
	/**
	 * ��Ϣ���е�JNDIname
	 */
	public static String JNDI_QUEUE_NAME = "LogMessageQueue";
	/**
	 * Ӧ�÷�������ʼ������
	 */
	public static String INITIAL_FACTORY = "weblogic.jndi.WLInitialContextFactory";

	/**
	 * JMS�ĳ��Ի�URL
	 */
	public static String JMS_URL = "http://127.0.0.1:7001";

	/**
	 * Ӧ�÷�������URL
	 */
	public static String URL_PROVIDER = "http://127.0.0.1:7001";
	/**
	 * ���������ļ���
	 */
	public static String SERVICE_CONFIG_FILE = "/WEB-INF/conf/ServiceConfig.xml";
	/**
	 * ϵͳ�����ļ���
	 */
    public static String CONFIG_FILE = "/WEB-INF/conf/Config.xml";
    /**
     * license file
     */
    public static String LICENSE="/WEB-INF/conf/license.lbs";
	/**
	 * �����еķ��񻺴������
	 */
	public static String SERVICE_CACHE_NAME = "serviceCache";
	/**
	 * EJB����������ļ�
	 */
	public static String EJBCACHE_CFG_FILE = "/WEB-INF/conf/EJBCache.xml";
	/**
	 * EJB���������
	 */
	public static String EJBCACHE_NAME = "ejbCache";

	//SafetyFilter��ʹ�õĳ���
	/**
	 *webӦ�õ����� 
	 */
	public static String WEB_APP = "/jtvshop";
	/**
	 * ��ҳ�� 
	 */	
	public static String INDEX_PAGE = "/Index.jsp";
	/**
	 * ��ߵĲ˵�ҳ��
	 */
	public static String MENU_PAGE = "/Left.jsp";
	/**
	 * ��¼ҳ��
	 */
	public static String LOGON_PAGE = "/LogonDialog.jsp";
	
	/**
	 * �ص�¼ҳ��
	 */
	public static String RELOGON_PAGE = "/login.html";
	/**
	 * ����ҳ��
	 */
	public static String LOGOFF_PAGE = "/Main.htm";
	/**
	 * ��¼dialog
	 */
	public static String LOGON_DIALOG_PAGE = "/LogonDialog.jsp";
	/**
	 * ������
	 */
	public static String MAIN_PAGE = "/Main.jsp";
	/**
	 * �û���Ȩ���б������Session��
	 */
	public static String FUNCTION_LIST = "function_list";
	/**
	 * �û�id
	 */
	public static String USERID = "userid";
	/**
	 * �û�����
	 */
	public static String PASSWD = "passwd";
	/**
	 * ����action�ķ���
	 */
	public static String METHOD = "method";
	/**
	 * ��¼action
	 */
	public static String LOGON_ACTION = "/logonAction.do";
	/**
	 * ����action
	 */
	public static String LOGOFF_ACTION = "/logoffAction.do";
	/**
	 * �ص�¼action
	 */
	public static String RELOGON_ACTION = "/relogonAction.do";
	/**
	 * ����session�е�form
	 */
	public static String CLEAN_SESSION_ACTION = "/cleanSessionAction.do";
	/**
	 * ��ŵ�ǰ�û��������Ϣ�������Session��
	 */
	public static String CURRENT_USER = "current_user";
	/**
	 * ����
	 */
	public static String ENCODING = "encoding";
	/**
	 * ����ֵ
	 */
	public static String DEFAULT_ENCODING = "UTF-8";
	/**
	 * ��ҳ��ÿҳ��¼��,ȱʡΪÿҳ10����¼
	 */
	public static int PAGE_SIZE = 10;
	/**
	 * �Ƿ��ܹ����session�е�form��ֻ��������˵�Ҷ��ʱΪtrue����Ϊfalse
	 */
	public static String CAN_CLEAR_FORM = "canclearform";
	/**
	 * ͼ��У������Session�е�Keyֵ
	 */
	public static String VERIFY_CODE="verifycode";
	/**
	 * �˵�����
	 */
	public static String MENU_ACTION = "/menuAction.do";
	/**
	 * Ȩ���б��������,�����Session��
	 */
	public static String MENU_TREE = "menutree";
	/**
	 * �˵�Ҷ��
	 */
	public static String MENU_LEAF = "0";
	/**
	 * �˵��ڵ�
	 */
	public static String MENU_NODE = "1";
	/**
	 * ҳ�水ť
	 */
	public static String MENU_BUTTON = "2";
	/**
	 * top���˵��������Session��
	 */
	public static String MAIN_MENU = "mainmenu";
	/**
	 * ��߲˵���xml�������Session��
	 */
	public static String MENU_XML = "menuxml";
	/**
	 * ��Ӧ"/menuAction.do?"��Ĳ�������
	 */
	public static String MENU_NAME = "menu";
	/**
	 * ��Ȩ�ļ���"2"Ϊ��ťһ����"0"Ϊ�˵�һ��
	 */
	public static String RIGHT_LEVEL = "2";
	/**
	 * ������������ͨ��
	 */
	public static String COMMON_TREE = "CommonTree";
	/**
	 * ����������ѡ����
	 */
	public static String SELECT_TREE = "SelectTree"; 
	/**
	 * Ȩ�����������û���Ȩ
	 */
	public static String RIGHT_TREE_XML = "rightTreeXml"; 
	//exception key of fcmis
	/**
	 * �쳣key
	 */
	public static String EXCEPTION_KEY = "com.lbs.lemis.Exception";
	/**
	 * ҵ����תҳ��
	 */
	public static String BIZ_FORWARD = "com.lbs.lemis.bizforward";
	/**
	 * ҵ����ת��Ϣ
	 */
	public static String BIZ_FORWARD_MSG = "com.lbs.lemis.bizforwardmsg";
	/**
	 * ҵ������Ƿ���action�м���־����1���������־������Ϊ��
	 */
	public static String ACTION_LOG = "1"; 
	/**
	 * �Ƿ��첽��¼��־
	 */
	public static boolean LOG_IS_ASYNCH = false;
	/**
	 * �ļ����ĳ���
	 */
	public static String TEMP_FILE_NAME_LENGTH = "9";
	/**
	 * ��ű�����ʱ�ļ����ļ�������
	 */
	public static String TEMP_FILE_PATH = "ReportTemp/";
	/**
	 * ���ͼƬ��ʱ�ļ����ļ�������
	 */
    public static String TEMP_PHOTO_PATH = "/photo";
    /**
     * ����request�еı�������
     */
	public static String REPORT_DATA = "reportdata";
	/**
	 * ����request�е�ͨ�ò�ѯ����
	 */
	public static String QUERY_DATA = "querydata"; 
	/**
	 * ����request�е�ͨ�ò�ѯ��Ϣ����ҳ������ǰҳ����������hql��
	 */
	public static String QUERY_INFO = "queryinfo"; 
	/**
	 * ��ҳ��ѯ�����Ĳ�����
	 */
	public static String PAGE_QUERY_ACTION = "pageQueryAction";
	/**
	 * ����request�е����ڸ��µ�����
	 */
	public static String UPDATE_DATA = "updatedata";
	/**
	 * �����ķ���request�е���־����
	 */
	public static String LOG_DATA = "logdata";
	/**
	 * �û�����������
	 */
    public static String RESET_PASSWD = "888888";
    
    /**
     * ������ǵ�����
     */
    public static String[] FREE_REQUESTS;
    /**
     * LookupDispatchAction������Ӣ�Ķ��ձ�
     */
    public static String METHOD_MAP = "methodmap";
    /**
     * hql����
     */
    public static String ORDER_BY = "order by";
    /**
     * �����û�
     */
    public static String SUPER_USER = "admin"; 
    /**
     * ��ֹ�ظ��ύ��key
     */
    public static String TOKEN_KEY="com.lbs.token.key";
    /**
     * ͨ�ò�ѯ��hql������ŵ�map�������Session��
     */
    public static String HQL_MAP = "hqlmap"; 
    /**
     * ������keyֵ
     */
    public static String NAVIGATION = "navigation";
    /**
     * ��ʾtable�ױߵ�С����Ϣ
     */
    public static String PAGE_SUM = "pagesum";
    /**
     * ��ʾtable�ױߵĺϼ���Ϣ
     */
    public static String TOTAL_SUM = "totalsum";
    /**
     * ÿ��������ˮ��
     */
    public static String DAILY_SEQUENCE_FILE = "C:\\cvs119\\leaf\\dailysequence";
    /**
     * ��һ����url
     */
    public static String NEXT_WIZARD = "nextwizard";
    /**
     * ����session�е�keyֵ
     */
    public static String WIZARD_URL = "wizardURL";
   /**
    * �ر���ʾ��Ϣ
    */
    public static String ALERT_MSG_OFF = "alertmsgoff";
    
//  ���ݵ���У������
    /**
     * ��У��Ĺ������û�����ServletContext�е�keyֵ
     */
	public static String VALIDATE_RULES = "validateRules";
	/**
	 * ��У������û�����ServletContext��keyֵ
	 */
	public static String VALIDATE_CONFIG = "validateConfig";
	/**
	 * jdbc batch size
	 */
	public static int JDBC_BATCH_SIZE = 50;
	
	/**
	 * �����б������Session��(��Ҫ����EXT���νṹ��ʾ)
	 */
	public static String SYSGROUP_LIST = "sysgroup_list";
	
	public static String COMM_CHECK_KEY_SET="1";  //1�������ܣ�3�������
	public static String AESPASSWORD="bi/0GYzmwiWiE6vqYmHsbQ4gDZg0ouAW";
	public static String SHA512PASSWORD="HxMv40BMnW347w6nx/TyyBbL91RoNxY7";
	
	public static String BUY_ROLEID="3";  //ί�з�����Ա��ɫ����
	public static String SALE_ROLEID="6";  //���˷�����Ա��ɫ����
	public static String PLATFORM_WORKTYPE="101"; //ƽ̨ҵ��ģʽ (101��Ӫ 102 ���)
	public static String PLATFORM_MEMBERID="1";       //ƽ̨Ĭ��ί�з�����
	public static String PLATFORM_COMPANYID="1";      //ƽ̨Ĭ�ϳ����̱���      
	public static String PLATFORM_AES_KEY="c5d608eb97d94123";  //ƽ̨����aes����key
	public static String PLATFORM_BALANCE_URL="http://foodeco.chinacloudapp.cn/v1/b2b/balance";  //�ʽ�ƽ̨��ѯ���URL
	public static String PLATFORM_APPID="wuliu";  //ƽ̨APPID
	public static String PLATFORM_RUNMODE="1";  //ƽ̨����ģʽ��1:��ƽ̨���Ͻӿ����� 2:ϵͳ��������
	
	public static String YUNSIGN_DOMAIN="https://www.yunsigntest.com";  //��ǩ����
	public static String YUNSIGN_REGUSER_URL="https://www.yunsigntest.com/mmecserver3.0/webservice/Common?wsdl";  //��ǩע���û��ӿ�
	public static String YUNSIGN_APPID="5tJ8tSA51W";
	public static String YUNSIGN_KEY="vXm9lF9wfG5SsqK849sT";
	
	//����������ƽ̨����
	public static String SMS_TAOBAO_URL="http://gw.api.taobao.com/router/rest";
	public static String SMS_TAOBAO_APPKEY="23419355";
	public static String SMS_TAOBAO_SECRET="4d5b1a196e83f591a4762cfa0700cdfa";
	public static String SMS_TAOBAO_EXTEND="123456";
	public static String SMS_TAOBAO_FREESIGNNAME="\u4E1C\u5317\u7CAE\u7F51\u6D4B\u8BD5";   //"������������";
	public static String SMS_TAOBAO_REGVERIFY_TEMPLATECODE="SMS_25115202";  //ע����֤��ģ�����
	public static String SMS_TAOBAO_FINDPASSWORDVERIFY_TEMPLATECODE="SMS_25250167";  //�һ�������֤��ģ�����

}
