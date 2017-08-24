package com.lbs.apps.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.lbs.apps.system.po.Sysgroup;
import com.lbs.apps.system.po.Sysuser;

/**
 * <p>Title: </p>
 * <p>Description: ��ǰ�û���Ϣ</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: LBS</p>
 * @author chenkl <chenkl@bjlbs.com.cn>
 * @version 1.0
 */

public class CurrentUser
    implements Serializable {

  private Sysuser user;
  //private FunctionDTO curFunction;
  private Sysgroup group = null;
  
  private String ip;
  private String logonTime;
  private Long id;//��¼��¼��־��id
  private Map bizObjects = new HashMap();//�洢ҵ����ص����ݶ���
  
  private String suppplierid;             //�û���������ID
  private String employeeno;              //�û�����ְԱID
  private Integer srid;                    //��ȡ����SR��ԱID

  
/**
 * @return �õ���ҵ�����صĶ���
 */
public Map getBizObjects() {
	return bizObjects;
}

public Object getBizObject(Object key) {
	return this.bizObjects.get(key);
}


/**
 * ��CurrentUser�л���ҵ�����
 * @param key ҵ������key
 * @param value ҵ�����
 */
public void setBizObject(Object key, Object value) {
	this.bizObjects.put(key,value);
}

/**
 * �õ���ѵ����
 * @return
 */
public String getTrainOrg(){
	return (String)this.getBizObject("trainOrg");
}
/**
 * @param group The group to set.
 */
public void setGroup(Sysgroup group) {
	this.group = group;
}
/**
 * @return Returns the groupid.
 */
public String getGroupid() {
	if(null == group)
		return null;
	return (group.getGroupid()+"");
}


  /**
   * ��ȡip
 * @return String
 */
public String getIp() {
    return ip;
  }

  /**
   * ��ȡ��ǰ�û�
 * @return Sysuser
 */
public Sysuser getUser() {
    return user;
  }

  /**
   * ����ip
 * @param ip String
 */
public void setIp(String ip) {
    this.ip = ip;
  }

  /**
   * �����û�
 * @param user Sysuser
 */
public void setUser(Sysuser user) {
    this.user = user;
  }

    /**
     * ��ȡʱ��
     * @return String
     */
    public String getLogonTime() {
        return logonTime;
    }

    /**
     * ����ʱ��
     * @param logonTime String
     */
    public void setLogonTime(String logonTime) {
        this.logonTime = logonTime;
    }

  /**
   * ��ȡid
 * @return Long
 */
public Long getId() {
    return id;
  }
  /**
   * ����id
 * @param id Long
 */
public void setId(Long id) {
    this.id = id;
  }



public String getSuppplierid() {
	return suppplierid;
}

public void setSuppplierid(String suppplierid) {
	this.suppplierid = suppplierid;
}

public String getEmployeeno() {
	return employeeno;
}

public void setEmployeeno(String employeeno) {
	this.employeeno = employeeno;
}

public Integer getSrid() {
	return srid;
}

public void setSrid(Integer srid) {
	this.srid = srid;
}

public Sysgroup getGroup() {
	return group;
}

public void setBizObjects(Map bizObjects) {
	this.bizObjects = bizObjects;
}


}
