package com.lbs.apps.common;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;



/**
 * <p>
 * Title: �Ͷ����г���Ϣϵͳ
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: BJLBS
 * </p>
 * 
 * @author �ĸ��� <raofh@bjlbs.com.cn>
 * @version 1.0
 */

public class ListUtil {
	public ListUtil() {
	}

	/**
	 * ��List��ʵ����ĳ�����Ե�ֵ��ȡ���������һ��ָ���ָ����ָ����ַ���
	 * 
	 * @param list
	 *            List ��ȡֵ��List����
	 * @param prop
	 *            String ������
	 * @param separator
	 *            String �ָ���
	 * @throws ApplicationException
	 * @return String
	 */
	public static String listToString(List list, String prop, String separator)
			throws ApplicationException {
		StringBuffer ret = new StringBuffer("");
		String property = "";

		if (list == null) {
			throw new ApplicationException("������б�Ϊ�գ�");
		}
		for (int i = 0; i < list.size(); i++) {
			Object entity = list.get(i);
			try {
				property = BeanUtils.getSimpleProperty(entity, prop);
			} catch (NoSuchMethodException ex) {
				throw new ApplicationException("û��ָ����" + prop + "������", ex);
			} catch (InvocationTargetException ex) {
				throw new ApplicationException("û��ָ����" + prop + "Ŀ�꣡", ex);
			} catch (IllegalAccessException ex) {
				throw new ApplicationException("����" + prop + "����ʱ����", ex);
			}

			if ((property == null) || "".equals(property))
				throw new ApplicationException("������б�����Ŀ��ֵΪ�գ�");
			ret.append(separator).append(property);
		}

		return ret.substring(separator.length());
	}

	/**
	 * ��List�в�ѯ�����ĳ�����Ե�ֵ��ȡ���������һ��ָ���ָ����ָ����ַ�
	 * 
	 * @param list
	 *            List ��ȡֵ��List����
	 * @param prop
	 *            String ������
	 * @param separator
	 *            String �ָ���
	 * @throws ApplicationException
	 * @return String
	 */
	public static String listToString(List list, String prop, String separator,
			QueryInfo qi) throws ApplicationException {
		StringBuffer ret = new StringBuffer("");
		String property = "";

		if (list == null) {
			throw new ApplicationException("������б�Ϊ�գ�");
		}
		for (int i = 0; i < list.size(); i++) {
			Object entity = list.get(i);
			try {
				if (entity instanceof Object[]) {
					//property = TypeCast.objToString(((Object[]) entity)[ActionUtil.getIndex(qi, prop)]);   
					property = BeanUtils.getSimpleProperty(entity, prop);   //modify by whd 20130914 ��ȡ���ϰ汾����QUERYINFO�н��д���Ļ���
				} else {
					property = BeanUtils.getSimpleProperty(entity, prop);
				}

			} catch (NoSuchMethodException ex) {
				throw new ApplicationException("û��ָ����" + prop + "������", ex);
			} catch (InvocationTargetException ex) {
				throw new ApplicationException("û��ָ����" + prop + "Ŀ�꣡", ex);
			} catch (IllegalAccessException ex) {
				throw new ApplicationException("����" + prop + "����ʱ����", ex);
			}

			if ((property == null) || "".equals(property))
				property = "0";
			ret.append(separator).append(property);
		}

		return ret.substring(separator.length());
	}

	/**
	 * ��List�е�ֵ��ȡ���������һ��ָ���ָ����ָ����ַ�
	 * 
	 * @param list
	 *            List ��ȡֵ��List����
	 * @param index
	 *            int ������
	 * @param separator
	 *            String �ָ���
	 * @throws ApplicationException
	 * @return String
	 */
	public static String listToString(List list, int index, String separator)
			throws ApplicationException {
		StringBuffer ret = new StringBuffer("");
		String property = "";

		if (list == null) {
			throw new ApplicationException("������б�Ϊ�գ�");
		}
		for (int i = 0; i < list.size(); i++) {
			Object[] entity = (Object[]) list.get(i);
			property = TypeCast.objToString(entity[index]);

			if ((property == null) || "".equals(property))
				throw new ApplicationException("������б�����Ŀ��ֵΪ�գ�");
			ret.append(separator).append(property);
		}

		return ret.substring(separator.length());
	}

	/**
	 * ��List�б����ҵ�ָ���������У�����ֵΪValue��������
	 * 
	 * @param list
	 *            List ���Ҷ���List
	 * @param prop[]
	 *            String ���ҵ�����
	 * @param value
	 *            String �������Ե�ֵ
	 * @return @throws
	 *         ApplicationException
	 */
	public static int getIndex(List list, String prop, String value)
			throws ApplicationException {
		int index = -1;
		Object ent = null;
		if (list == null)
			return index;
		if (isNull(prop))
			throw new ApplicationException("���Բ���Ϊ�գ�");
		if (isNull(value))
			throw new ApplicationException("����ֵΪ�գ�");

		try {
			for (int i = 0; i < list.size(); i++) {
				ent = list.get(i);
				if (value.equals(BeanUtils.getSimpleProperty(ent, prop))) {
					index = i;
					i = list.size();
				}
			}
		} catch (NoSuchMethodException ex) {
			throw new ApplicationException("û��ָ����" + prop + "������", ex);
		} catch (InvocationTargetException ex) {
			throw new ApplicationException("û��ָ����" + prop + "Ŀ�꣡", ex);
		} catch (IllegalAccessException ex) {
			throw new ApplicationException("����" + prop + "����ʱ����", ex);
		}
		return index;
	}

	/**
	 * ��List�б����ҵ�ָ���������У�����ֵΪValue��������
	 * 
	 * @param list
	 *            List ���Ҷ���List
	 * @param prop[]
	 *            String ���ҵ�����
	 * @param value[]
	 *            String �������Ե�ֵ
	 * @return @throws
	 *         ApplicationException
	 */
	public static int getIndex(List list, String prop[], String value[])
			throws ApplicationException {
		int index = -1;
		Object ent = null;
		if (list == null)
			return index;
		if (NullFlag.isObjNull(prop))
			throw new ApplicationException("���Բ���Ϊ�գ�");
		if (NullFlag.isObjNull(value))
			throw new ApplicationException("����ֵΪ�գ�");

		if (prop.length != value.length) {
			throw new ApplicationException("�����������ֵ��ƥ�䣡");
		}

		try {
			for (int i = 0; i < list.size(); i++) {
				boolean flag = true;
				ent = list.get(i);
				for (int j = 0; j < value.length; j++) {
					if (!value[j].equals(BeanUtils.getSimpleProperty(ent,
							prop[j]))) {
						j = value.length;
						flag = false;
					}
				}
				if (flag) {
					index = i;
					i = list.size();
				}
			}
		} catch (NoSuchMethodException ex) {
			throw new ApplicationException("û��ָ����" + prop + "������", ex);
		} catch (InvocationTargetException ex) {
			throw new ApplicationException("û��ָ����" + prop + "Ŀ�꣡", ex);
		} catch (IllegalAccessException ex) {
			throw new ApplicationException("����" + prop + "����ʱ����", ex);
		}
		return index;
	}

	/**
	 * ȡ��һ���б���Ԫ����ĳ���ֶ������Ǹ�Ԫ��
	 * 
	 * @param list
	 *            Ҫʹ�õ��б�
	 * @param prop
	 *            �Ƚϵ��ֶ���
	 * @return int ֵ��� ���Ǹ��б�Ԫ������
	 * @throws ApplicationException
	 */
	public static int getPropertyMaxValue(List list, String prop)
			throws ApplicationException {
		Object obj = null, t;
		Comparable value, oldvalue = null;
		int rtn	=	-1;
		Iterator it = list.iterator();
		try {
			for (int i = 0; i < list.size(); i++) {
	            obj =	list.get(i);
	            t	=	PropertyUtils.getSimpleProperty(obj,prop);
	            if (t instanceof Comparable) {
	                value = (Comparable) t;
				} else {
					throw new ApplicationException("ָ�������Բ��ܱȽϴ�С��");
				}
	            
	            if (NullFlag.isObjNull(oldvalue)||!NullFlag.isObjNull(value) && value.compareTo(oldvalue) > 0) {
					oldvalue = value;
					rtn	=	i;
				}
	        }
		} catch (Exception e) {
			throw new ApplicationException(e);
		}
		
		if (NullFlag.isObjNull(oldvalue)) {
		    rtn	=	-1;
		}
		return rtn;
	}
	
	/**
	 * ȡ��һ���б���Ԫ����ĳ���ֶ������Ǹ�Ԫ��
	 * 
	 * @param list
	 *            Ҫʹ�õ��б�
	 * @param prop
	 *            �Ƚϵ��ֶ���
	 * @return int ֵ��С���Ǹ��б�Ԫ������
	 * @throws ApplicationException
	 */
	public static int getPropertyMinValue(List list, String prop)
			throws ApplicationException {
		Object obj = null, t;
		Comparable value, oldvalue = null;
		int rtn	=	-1;
		Iterator it = list.iterator();
		try {
			for (int i = 0; i < list.size(); i++) {
	            obj =	list.get(i);
	            t	=	PropertyUtils.getSimpleProperty(obj,prop);
	            if (t instanceof Comparable) {
	                value = (Comparable) t;
				} else {
					throw new ApplicationException("ָ�������Բ��ܱȽϴ�С��");
				}
	            
	            if (NullFlag.isObjNull(oldvalue)||!NullFlag.isObjNull(value) && value.compareTo(oldvalue) > 0) {
					oldvalue = value;
					rtn	=	i;
				}
	        }
		} catch (Exception e) {
			throw new ApplicationException(e);
		}
		
		if (NullFlag.isObjNull(oldvalue)) {
		    rtn	=	-1;
		}
		return rtn;
	}
	

	/**
	 * ȡ��һ���б���Ԫ����ĳ���ֶ������Ǹ�Ԫ��
	 * 
	 * @param list
	 *            Ҫʹ�õ��б�
	 * @param prop
	 *            �Ƚϵ��ֶ���
	 * @return int ֵ��� ���Ǹ��б�Ԫ������
	 * @throws ApplicationException
	 */
	public static int getPropertyMaxValue(List list, int	index)
			throws ApplicationException {
		Object	obj	=	null;
		Object	t	=	null;
		Comparable value, oldvalue = null;
		int rtn	=	-1;
		
		try {
			for (int i = 0; i < list.size(); i++) {
	            obj =	list.get(i);
	            if (obj instanceof Object[]) {
		            t	=	((Object[])obj)[index];
		            
		            if (t instanceof Comparable) {
		                value = (Comparable) t;
					} else {
						throw new ApplicationException("ָ�������Բ��ܱȽϴ�С��");
					}
		            
		            if (NullFlag.isObjNull(oldvalue)||!NullFlag.isObjNull(value) && value.compareTo(oldvalue) > 0) {
						oldvalue = value;
						rtn	=	i;
					}
		        }
			}
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

        if (NullFlag.isObjNull(oldvalue)) {
            rtn = -1;
        }
        return rtn;
	}
	
	/**
	 * ȡ��һ���б���Ԫ����ĳ���ֶ������Ǹ�Ԫ��
	 * 
	 * @param list
	 *            Ҫʹ�õ��б�
	 * @param prop
	 *            �Ƚϵ��ֶ���
	 * @return int ֵ��С���Ǹ��б�Ԫ������
	 * @throws ApplicationException
	 */
	public static int getPropertyMinValue(List list, int	index)
			throws ApplicationException {
		Object obj = null, t;
		Comparable value, oldvalue = null;
		int rtn	=	-1;
		Iterator it = list.iterator();
		try {
			for (int i = 0; i < list.size(); i++) {
	            obj =	list.get(i);
	            if (obj instanceof Object[]) {
		            t	=	((Object[])obj)[index];
		            
		            if (t instanceof Comparable) {
		                value = (Comparable) t;
					} else {
						throw new ApplicationException("ָ�������Բ��ܱȽϴ�С��");
					}
		            
		            if (NullFlag.isObjNull(oldvalue)||!NullFlag.isObjNull(value) && value.compareTo(oldvalue) > 0) {
						oldvalue = value;
						rtn	=	i;
					}
		        }
			}
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

        if (NullFlag.isObjNull(oldvalue)) {
            rtn = -1;
        }
		return rtn;
	}
	
	/**
	 * �������е�ֵ������Class��Ӧ�������У�Ҫ��������ֵ������˳����Class����˳��һ��
	 * 
	 * @param Object[] obj
	 * @param Class obj1
	 * @return 
	 * @throws ApplicationException
	 */
	public static List trimList(List list) throws ApplicationException {
		List result = new Vector();
		for (int i = 0; i < list.size(); i++) {
			Object obj = (Object) list.get(i);
			if (obj instanceof Object[]) {
				Object[] obj1 = (Object[]) obj;
				for (int j = 0; j < obj1.length; j++) {
					if (obj1[j] instanceof String) {
						obj1[j] = TypeCast.objToString(obj1[j]).trim();
					}
				}
			}
			result.add(obj);
		}
		return result;
	}

	/**
	 * �ж�����Ķ����Ƿ�Ϊ��
	 * 
	 * @param obj
	 * @return
	 */
	private static boolean isNull(Object obj) {
		boolean ret = false;

		if (obj == null) {
			ret = true;
		} else if ("".equals(obj.toString())) {
			ret = true;
		}

		return ret;
	}

}