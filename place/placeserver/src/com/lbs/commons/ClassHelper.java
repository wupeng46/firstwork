package com.lbs.commons;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lbs.apps.common.AesTools;
import com.lbs.apps.common.ApplicationException;
import com.lbs.commons.converter.Convert;

/**
 * <p>
 * Title: leaf framework (lemis core platform)
 * </p>
 * <p>
 * Description: ��Ҫʵ�����ࡢ�õ�JavaBean�����ԺͿ���JavaBean������
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: LBS
 * </p>
 *
 * @author LBS POC TEAM
 * @author chenkl <chenkl@bjlbs.com.cn>
 * @version 1.0
 */

public class ClassHelper {
	private static Log logger = LogFactory.getLog(ClassHelper.class);

	public ClassHelper() {
	}

	/**
	 * ʵ����һ����
	 *
	 * @param name -
	 *            ����
	 * @return ʵ������
	 */
	public static Object getClassInstance(String name) {
		try {
			return Thread.currentThread().getContextClassLoader().loadClass(
					name).newInstance();
		} catch (Exception e) {
			try {
				return Class.forName(name).newInstance();
			} catch (ClassNotFoundException ex) {
				logger.error(ex.toString());
				return null;
			} catch (IllegalAccessException ex) {
				logger.error(ex.toString());
				return null;
			} catch (InstantiationException ex) {
				logger.error(ex.toString());
				return null;
			}
		}
	}

	/**
	 * ��HttpServletRequest�еĲ�����װ��ָ��ʵ�弯�ϣ�Ҫ��ʵ��ֻʵ��java.io.Serializble
	 *
	 * @param bean
	 *            Object
	 * @param entity
	 *            Class
	 * @return Collection
	 */
	public static Collection getEntities(SubmitDataMap parameters, Class entity) {
		try {
			return Convert.getCollection(parameters, entity);
		} catch (IllegalAccessException ex) {
			logger.error(ex.toString());
			return null;
		} catch (InstantiationException ex) {
			logger.error(ex.toString());
			return null;
		} catch (InvocationTargetException ex) {
			logger.error(ex.toString());
			return null;
		}
	}

	/**
	 * ��FormBeanת��ΪDTO,��װbeanutil���copyproperty����
	 *
	 * @param bean
	 *            Object
	 * @param entity
	 *            Class
	 */
	public static void copyProperties(Object from, Object to) {
		try {
			//BeanUtils.copyProperties(to, from);
			BeanUtilEx.copyProperties(to, from);  //modify by whd 2016.7.28 ���ԭ��ܶ�sql���ڽ���쳣����
		} catch (IllegalAccessException ex) {
			logger.error(ex.toString());
		} catch (InvocationTargetException ex) {
			logger.error(ex.toString());
		}
	}
	
	/**
	 * ��json�ַ���ת��ΪDTO,��װbeanutil���copyproperty����
	 *
	 * @param bean
	 *            Object
	 * @param entity
	 *            Class
	 */
	public static void copyProperties(String from, Object to) {
		String msgdata=from;  			 
		try{
			JSONObject obj = JSONObject.fromObject(msgdata);   //����JSON���ݰ�		
			ClassHelper.copyProperties(obj, to);
		}catch(Exception e){
			logger.error(e.toString());			
		}
	}

	/**
	 * �õ�javabean���е�����,Ҫ��javabean�����������Ǵ�Object�̳������ġ�
	 *
	 * @param bean
	 *            beanʵ��
	 * @return ���硰name=value,name1=value1����Stringֵ
	 */
	public static synchronized String getProperties(Object bean) {
		StringBuffer sb = new StringBuffer(512);
		int index = 0;
		Method[] methodsOfBean = bean.getClass().getMethods();
		String property = null;
		String objStr = null;
		String nameGet = null;
		String nameGetAttr = null;
		try {
			for (int i = 0; i < methodsOfBean.length; i++) {
				nameGet = methodsOfBean[i].getName().substring(0, 3);
				nameGetAttr = methodsOfBean[i].getName().substring(3);

				if (("get".equalsIgnoreCase(nameGet))
						&& !("class".equalsIgnoreCase(nameGetAttr))
						&& !("SERVLETWRAPPER".equalsIgnoreCase(nameGetAttr
								.toUpperCase()))
						&& !("MULTIPARTREQUESTHANDLER")
								.equalsIgnoreCase(nameGetAttr.toUpperCase())) {
					Object args = methodsOfBean[i].invoke(bean, null);
					if (args != null) {
						objStr = args.toString();
					} else {
						objStr = "null";
					}

					property = getObj(args, objStr);

					if (index == 0) {
						sb.append(nameGetAttr.toUpperCase() + "=" + property);
						index++;
					} else {
						sb.append("," + nameGetAttr.toUpperCase() + "="
								+ property);
					}
				}
			}
		} catch (InvocationTargetException ex) {
			logger.error(ex.toString());
		} catch (IllegalAccessException ex) {
			logger.error(ex.toString());
		} catch (IllegalArgumentException ex) {
			logger.error(ex.toString());
		}

		return sb.toString();
	}
	/**
	 * �õ������ÿ��Ԫ�ص�ֵ
	 *
	 * @param obj
	 *            Object[] ��������
	 * @return String
	 */
	private static String getArray(Object[] obj) { //array
		StringBuffer sb = new StringBuffer(512);
		String property;
		String objStr = null;
		for (int index = 0; index < obj.length; index++) {
			objStr = obj[index].toString();
			property = getObj(obj[index], objStr);

			if (index == 0) {
				sb.append("[" + index + "]=" + property);
			} else {
				sb.append(",[" + index + "]=" + property);
			}
		}
		return sb.toString();
	}

	/**
	 * �õ�map��ÿ��Ԫ�ص�ֵ
	 *
	 * @param map
	 *            Map
	 * @return String
	 */
	private static String getMap(Map map) { //map
		return getIterator(map.values().iterator());
	}

	/**
	 * ��ȡIterator����Ԫ�ص�ֵ
	 *
	 * @param iterator
	 * @return �ԡ�,��������Ԫ��ֵ�ַ���
	 */
	private static String getIterator(Iterator iterator) { //iterator
		StringBuffer sb = new StringBuffer(512);
		String property = null;
		String objStr = null;
		int index = 0;
		for (Iterator it = iterator; it.hasNext();) {
			Object obj = it.next();
			objStr = obj.toString();
			property = getObj(obj, objStr);

			if (index == 0) {
				sb.append(property);
				index++;
			} else {
				sb.append("," + property);
			}
		}
		return sb.toString();
	}
	/**
	 * �õ�����ָ��������ֵ
	 *
	 * @param obj
	 *            Object
	 * @param property
	 *            String
	 * @return String
	 */
	private static String getObj(Object obj, String property) { //����
		if ((property.indexOf(".") != -1) && (property.indexOf("@") != -1)
				&& !(property.startsWith("[")) && !(property.startsWith("{"))) {
			property = "<" + getProperties(obj) + ">";
		}

		if (property.startsWith("[") && property.endsWith("]")) {
			Collection collection = (Collection) obj;
			property = "<coll: " + getIterator(collection.iterator())
					+ " coll>";
		}

		if (property.startsWith("{") && property.endsWith("}")) {
			Map map = (Map) obj;
			property = "<map: " + getMap(map) + " map>";

		}

		if (property.startsWith("[") && !property.endsWith("]")) {
			Object[] arr = ((Object[]) obj);
			property = "<arr: " + getArray(arr) + " arr>";
		}

		return property;
	}

	/**
	 * ��һ��bean����������trim
	 *
	 * @param from
	 *            Դbean
	 * @param to
	 *            Ŀ��bean
	 */
	public static synchronized void trimProperties(Object bean) {
		Method[] methods = bean.getClass().getMethods();
		String nameGet = null;
		String nameGetAttr = null;
		Class[] tmpTypes = new Class[methods.length];
		String[] tmpNames = new String[methods.length];
		Method[] tmpSetMethods = new Method[methods.length];
		int index = 0;
		for (int i = 0; i < methods.length; i++) {
			nameGet = methods[i].getName().substring(0, 3);
			nameGetAttr = methods[i].getName().substring(3);
			if ("set".equalsIgnoreCase(nameGet)) {
				tmpTypes[index] = methods[i].getParameterTypes()[0];
				tmpNames[index] = nameGetAttr;
				tmpSetMethods[index] = methods[i];
				index++;
			}
		}

		Class[] types = new Class[index];
		String[] names = new String[index];
		Method[] setMethods = new Method[index];
		for (int i = 0; i < index; i++) {
			types[i] = tmpTypes[i];
			names[i] = tmpNames[i];
			setMethods[i] = tmpSetMethods[i];
		}

		Method[] methodsOfFrom = bean.getClass().getMethods();

		Object arg = null;
		Object encoded = null;

		try {
			for (int i = 0; i < names.length; i++) {
				for (int j = 0; j < methodsOfFrom.length; j++) {
					nameGet = methodsOfFrom[j].getName().substring(0, 3);
					nameGetAttr = methodsOfFrom[j].getName().substring(3);
					if (names[i].equalsIgnoreCase(nameGetAttr)
							&& "get".equalsIgnoreCase(nameGet)) {
                          Class returnType = methodsOfFrom[j].getReturnType();
                        if(returnType.equals(String.class)){
                          arg = methodsOfFrom[j].invoke(bean, null);                          
                          encoded = Convert.convert( (arg == null) ? null :((String)arg).trim(), types[i]);
                          setMethods[i].invoke(bean, new Object[] {encoded});
                        }
					}
				}
			}
		} catch (InvocationTargetException ex) {
			logger.error(ex.toString());
		} catch (IllegalArgumentException ex) {
			logger.error(ex.toString());
		} catch (IllegalAccessException ex) {
			logger.error(ex.toString());
		}
	}

}
