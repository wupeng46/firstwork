package com.lbs.apps.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.lbs.apps.system.po.Sysgroup;
import com.lbs.apps.system.po.Sysuser;

/**
 * <p>Title: </p>
 * <p>Description: 当前用户信息</p>
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
  private Long id;//记录登录日志的id
  private Map bizObjects = new HashMap();//存储业务相关的数据对象
  
  private String suppplierid;             //用户隶属厂商ID
  private String employeeno;              //用户隶属职员ID
  private Integer srid;                    //获取隶属SR人员ID

  
/**
 * @return 得到到业务的相关的对象
 */
public Map getBizObjects() {
	return bizObjects;
}

public Object getBizObject(Object key) {
	return this.bizObjects.get(key);
}


/**
 * 在CurrentUser中缓存业务对象
 * @param key 业务对象的key
 * @param value 业务对象
 */
public void setBizObject(Object key, Object value) {
	this.bizObjects.put(key,value);
}

/**
 * 得到培训机构
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
   * 读取ip
 * @return String
 */
public String getIp() {
    return ip;
  }

  /**
   * 读取当前用户
 * @return Sysuser
 */
public Sysuser getUser() {
    return user;
  }

  /**
   * 设置ip
 * @param ip String
 */
public void setIp(String ip) {
    this.ip = ip;
  }

  /**
   * 设置用户
 * @param user Sysuser
 */
public void setUser(Sysuser user) {
    this.user = user;
  }

    /**
     * 读取时间
     * @return String
     */
    public String getLogonTime() {
        return logonTime;
    }

    /**
     * 设置时间
     * @param logonTime String
     */
    public void setLogonTime(String logonTime) {
        this.logonTime = logonTime;
    }

  /**
   * 读取id
 * @return Long
 */
public Long getId() {
    return id;
  }
  /**
   * 设置id
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
