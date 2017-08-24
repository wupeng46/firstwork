package com.lbs.commons.configuration;

/**
 * <p>Title: LEMIS</p>
 * <p>Description: LEMIS Core platform</p>
 * 加载全局配置
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: lbs</p>
 * @author hanvey
 * modify by chenkl
 * @version 1.0
 */

import java.io.InputStream;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.lbs.commons.DOMUtil;
import com.lbs.commons.GlobalNames;

public class GlobalConfiguration
    extends BaseConfiguration {

  private Logger log = Logger.getLogger(getClass());

  //配置信息暂存
  private Hashtable confs;

  /**
   * 加载全局配置
 * @param conFile
 * @throws Exception
 */
//构造函数
  public GlobalConfiguration(InputStream conFile)throws Exception {
    try {
      loadConfig(conFile);
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    }
  }

  //加载配置文件
  private void loadConfig(InputStream conFile) throws Exception {

    //加载配置信息到Hash表中
    Document doc = DOMUtil.loadDocumentFromInputStream(conFile);
    NodeList cfgs = DOMUtil.getMultiNodes(doc, "config");
    confs = new Hashtable();
    for (int i = 0; i < cfgs.getLength(); i++) {
      String confname = DOMUtil.getAttributeValue( (Element) cfgs.item(i),
                                                  "name");
      String confvalue = DOMUtil.getAttributeValue( (Element) cfgs.item(i),
          "value");
      confs.put(confname, confvalue);
      log.info(confname + " = " + confvalue);
    }
    //给GlobalNames赋值
    setGlobalName();
  }

  private void setGlobalName() {
    GlobalNames.JMS_CONNECTION_FACTROY =
        (String) confs.get("JMS_CONNECTION_FACTROY");
    GlobalNames.JNDI_QUEUE_NAME =
        (String) confs.get("JNDI_QUEUE_NAME");
    GlobalNames.INITIAL_FACTORY =
        (String) confs.get("INITIAL_FACTORY");
    GlobalNames.JMS_URL = (String) confs.get("JMS_URL");
    GlobalNames.URL_PROVIDER = (String) confs.get("URL_PROVIDER");
    GlobalNames.SERVICE_CONFIG_FILE = (String) confs.get("SERVICE_CONFIG_FILE");
    GlobalNames.CONFIG_FILE = (String) confs.get("CONFIG_FILE");
    GlobalNames.EJBCACHE_CFG_FILE = (String) confs.get("EJBCACHE_CFG_FILE");
    GlobalNames.TEMP_FILE_PATH = (String) confs.get("TEMP_FILE_PATH");
    GlobalNames.TEMP_PHOTO_PATH = (String) confs.get("TEMP_PHOTO_PATH");
    GlobalNames.WEB_APP = (String) confs.get("WEB_APP");
    GlobalNames.INDEX_PAGE = (String) confs.get("INDEX_PAGE");
    GlobalNames.MENU_PAGE = (String) confs.get("MENU_PAGE");
    GlobalNames.LOGON_PAGE = (String) confs.get("LOGON_PAGE");
    GlobalNames.RELOGON_PAGE = (String) confs.get("RELOGON_PAGE");
    GlobalNames.MAIN_PAGE = (String) confs.get("MAIN_PAGE");
    GlobalNames.PAGE_SIZE = Integer.parseInt( (String) confs.get("PAGE_SIZE"));
    GlobalNames.LOG_IS_ASYNCH = Boolean.valueOf( (String) confs.get("LOG_IS_ASYNCH")).
        booleanValue();
    String freeRequests = (String)confs.get("FREE_REQUESTS");
    GlobalNames.FREE_REQUESTS = freeRequests.split(",");
    GlobalNames.RESET_PASSWD = (String) confs.get("RESET_PASSWD");
    GlobalNames.SUPER_USER = (String) confs.get("SUPER_USER");

  }

}
