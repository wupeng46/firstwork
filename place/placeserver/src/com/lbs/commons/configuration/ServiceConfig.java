package com.lbs.commons.configuration;

/**
 * <p>
 * Title: LEMIS
 * </p>
 * <p>
 * Description: LEMIS Core platform
 * </p>
 * ��������
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: lbs
 * </p>
 *
 * @author hanvey
 * @version 1.0
 */

public class ServiceConfig implements java.io.Serializable {
	/**
	 * Comment for <code>ID</code> ����ID
	 */
	private String ID;
	/**
	 * Comment for <code>POJO_NAME</code> �ṩ�����POJO����
	 */
	private String POJO_NAME;
	/**
	 * Comment for <code>EJB_NAME</code> �ṩ�����EJB����
	 */
	private String EJB_NAME;
	/**
	 * Comment for <code>TYPE</code> ��������
	 */
	private String TYPE;
	/**
	 * Comment for <code>DESC</code> ��������
	 */
	private String DESC;
	/**
	 * Comment for <code>JNDI_NAME</code> ������JNDI���е�����
	 */
	private String JNDI_NAME;

	public ServiceConfig() {
	}
	public ServiceConfig(String id, String pojo, String ejb, String type,
			String desc) {
		ID = id;
		POJO_NAME = pojo;
		TYPE = type;
		DESC = desc;
		EJB_NAME = ejb;
	}
	public String getJNDI() {
		return JNDI_NAME;
	}
	public void setJNDI(String jndiname) {
		JNDI_NAME = jndiname;
	}
	public String getID() {
		return ID;
	}
	public void setEJB(String ejbname) {
		EJB_NAME = ejbname;
	}
	public String getEJB() {
		return EJB_NAME;
	}
	public String getPOJO() {
		return POJO_NAME;
	}
	public String getType() {
		return TYPE;
	}
	public String getDesc() {
		return DESC;
	}
	public void setID(String id) {
		ID = id.trim();
	}
	public void setPOJO(String pojo) {
		POJO_NAME = pojo.trim();
	}
	public void setType(String type) {
		TYPE = type.trim();
	}
	public void setDesc(String desc) {
		DESC = desc.trim();
	}
}
