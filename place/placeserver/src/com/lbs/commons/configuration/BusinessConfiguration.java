package com.lbs.commons.configuration;

/**
 * <p>Title: LEMIS</p>
 * <p>Description: LEMIS Core platform</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: lbs</p>
 * @author hanvey
 * @version 1.0
 * modify by chenkl
 * <p>�������Ҫ�Ǵ������ļ��ж�ȡ������Ϣ����ҵ��ϵͳ��ѯ��Щ���ò���</p>
 *          <p>
 *          ����ϵͳ���ǵ�ʵ���ģ���Ҫ��ϵͳ����ʱ��ʼ��
 *          </p>
 */
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.lbs.commons.DOMUtil;

public class BusinessConfiguration extends BaseConfiguration {

  //�������û���
  private HashMap servicesConf;
  //���ҷ������õ�key
  private static String SEARCH_BY;
  //Ĭ�ϲ��ҷ�������key
  private static String DEFAULT_SEARCH_BY="id";
  //��־����
  private Logger log=Logger.getLogger(getClass());
  /**
   * <p>���캯��</p>
   * ��ȡ����
   */

  public BusinessConfiguration(InputStream serviceFile) throws Exception {

      try {
        loadConfig(serviceFile);
      } catch (Exception ex) {
        ex.printStackTrace();
        throw ex;
      }
  }

  /**
   *���������ļ�
   */
  private void loadConfig(InputStream serviceFile) throws Exception
  {
    try {
      Document doc = DOMUtil.loadDocumentFromInputStream(serviceFile);
      getServiceNode(doc);
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    }

  }
  /**
   *
   * @param doc Document
   * @param componentName String
   * @throws Exception
   * @return Node
   */
  private void getServiceNode(Document doc) throws Exception{
        NodeList components = DOMUtil.getMultiNodes(doc, "service");
        Node component = null;
        SEARCH_BY = DOMUtil.getSingleNodeValue(doc,"searchby");
        if (SEARCH_BY==null)
          SEARCH_BY = DEFAULT_SEARCH_BY;
        servicesConf = new HashMap();
        for(int i = 0; i < components.getLength(); i++){
            component = components.item(i);
            ServiceConfig cfg=new ServiceConfig();
            String id,type,pojo,desc,ejb;
            try{
              id = (String)DOMUtil.getAttributeValue((Element)component,"id");
              cfg.setID(id);
              type = (String)DOMUtil.getAttributeValue((Element)component,"type");
              cfg.setType(type);
              desc = (String)DOMUtil.getAttributeValue((Element)component,"desc");
              cfg.setDesc(desc);
              if (type.equals("1")){
                ejb = (String) DOMUtil.getAttributeValue((Element)component,"ejbname");
                cfg.setEJB(ejb);
              }
              else
              {
                pojo = (String)DOMUtil.getAttributeValue((Element)component,"pojoname");
                cfg.setPOJO(pojo);
              }
            }catch(Exception e)
            {
              e.printStackTrace();
            }
            servicesConf.put((String)DOMUtil.getAttributeValue((Element)component,SEARCH_BY),cfg);
        }
    }
  /**
   * ���ݷ����ID��ȡ����������Ϣ
 * @param key - ����ID
 * @return - ServiceConfig
 * @throws Exception
 */
  public ServiceConfig getConfig(String key) throws Exception
  {
    ServiceConfig cfg=(ServiceConfig)servicesConf.get(key);
    if (cfg==null){
      String msg ="Can not find the service ["+key+"] configuration in the config files!";
      log.error(msg);
      throw new Exception(msg);
    }
    return cfg;
  }
  /**
   * ��ȡȫ������������Ϣ 
 * @return Iterator
 */
  public Iterator getAllConfig()
  {
    return servicesConf.values().iterator();
  }
}
