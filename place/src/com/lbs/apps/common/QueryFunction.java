package com.lbs.apps.common;


import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Hibernate;
import org.hibernate.type.Type;

import com.lbs.commons.QueryFactory;
import com.lbs.commons.StringHelper;
import com.lbs.commons.op.HibernateSession;


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
 * @author �ĻԶ� <raofh@bjlbs.com.cn>
 * @version 1.0
 */

public class QueryFunction {
	private static String IGNOREWORD = " '(%+"; //	�����ַ�
	private static Map Entity = new HashMap(); 
	
	/*
	 * ȥ��SQL����ж���Ķ���
	 * update ab01 set aab00z='323',,,Ϊupdate aa01 set aab00z='323'
	 */
	public static String TrimString(String s){
		String sour=s;
		String temp;
		int i=20;
		while(i>=1){
			temp="";
			for (int j=1;j<=i;j++){
				temp=temp+",";
			}
			sour=sour.replaceAll(temp,",");			
			i--;
		}				
		sour=sour.replaceAll(", ","");
		sour=sour.replaceAll(" ,","");
		return sour;
	}
	/**
	 * ���ز�ѯ��hql���
	 * 
	 * @param String
	 *            hql
	 * @param Object
	 *            dto
	 * @return string
	 * @throws ApplicationException
	 */
	public static String getHQL(String hql, Object dto)
			throws ApplicationException {
		String para[] = getParaNames(hql);
		Type types[] = getTypes(dto, para);
		String hql1 = "";

		try {
			hql1 = QueryFactory.getHQL(hql, para, types, dto);
		} catch (Exception e) {
			throw new ApplicationException("���ɲ�ѯ���ʱ����", e);
		}

		return hql1;
	}

	/**
	 * ����HQL�����Ϣ������HQL�д��ڵĲ�����Ϣ��������д˳��һ��
	 * 
	 * @param hql
	 * @return
	 */
	public static String[] getParaNames(String hql) {
		String prop = "";
		int index = -1;
		ArrayList al = new ArrayList();

		index = hql.indexOf(":", index);

		while (index > 0) {
			index = index + 1;
			// ����β��":"�ŵĶ���ĺ����ַ�
			while (IGNOREWORD.indexOf(hql.substring(index, index + 1)) >= 0) {
				index++;
			}
			prop = hql.substring(index, index + 6);
			al.add(prop);
			index = hql.indexOf(":", index + 6);
		}

		String paraNames[] = new String[al.size()];
		al.toArray(paraNames);

		return paraNames;
	}

	/**
	 * ��д�����Ĳ�������
	 * 
	 * @return Type[]
	 */
	private static Type getType(Class obj) {
		Type type = Hibernate.STRING;
		if (obj.equals(Date.class)) {
			type = Hibernate.DATE;
		} else if (obj.equals(String.class)) {
			type = Hibernate.STRING;
		} else if (obj.equals(Double.class)) {
			type = Hibernate.DOUBLE;
		} else if (obj.equals(Integer.class)) {
			type = Hibernate.INTEGER;
		} else if (obj.equals(BigDecimal.class)) {
			type = Hibernate.BIG_DECIMAL;
		}else if (obj.equals(Short.class)){
			type=Hibernate.SHORT;
		}else {
			type = Hibernate.STRING;
		}

		return type;
	}

	/**
	 * ��д�����Ĳ�������
	 * 
	 * @param Object
	 *            entity
	 * @param String[]
	 *            props
	 * @return Type[]
	 * @throws ApplicationException
	 */
	public static Type[] getTypes(Object entity, String paras[])
			throws ApplicationException {
		if (paras == null || paras.length < 1)
			return null;
		
		ArrayList al = new ArrayList();

		for (int i = 0; i < paras.length; i++) {
			Class temp = null;
			try {
				temp = PropertyUtils.getPropertyType(entity, paras[i]);
			} catch (IllegalAccessException iae) {
				throw new ApplicationException("�Ƿ����ʳ���!", iae);
			} catch (InvocationTargetException ite) {
				throw new ApplicationException("����Ŀ�����!", ite);
			} catch (NoSuchMethodException nsme) {
				throw new ApplicationException("û��ָ���ķ�������!", nsme);
			}
			al.add(getType(temp));
		}

		Type[] types = new Type[al.size()];
		al.toArray(types);

		return types;
	}
	
	/**
	 * ��ȡHQL�����select��䲿�ְ���������ʵ��
	 * @param HQL
	 * @return
	 * @author �ĻԶ�
	 */
	public static String getSelectEntitys(String HQL) {
	    String sep	=	",";
	    StringBuffer rtn = new StringBuffer("");
	    String hqlSelect = "";
	    
	    hqlSelect = getSelectHQL(HQL); 
	    
	    if (NullFlag.isObjNull(hqlSelect)) {
	        return hqlSelect;
	    }
	    
	    String[] selects = hqlSelect.split(sep);
	    for (int i = 0; i < selects.length; i++) {
            String select = selects[i];
            if (select.indexOf(StringHelper.DOT) < 0) {
                rtn.append(",").append(select);
            }
        }
	    
	    return rtn.substring(1);
	}
	
	/**
	 * ��ȡHQL�����select��䲿�ְ���������ʵ��
	 * @param HQL
	 * @return
	 * @throws ApplicationException
	 * @author �ĻԶ�
	 */
	public static String getFullSelect(String HQL) throws ApplicationException {
	    String sep	=	",";
	    StringBuffer hqlSelect = new StringBuffer("");
	    String tmpSelect = "";
	    
	    tmpSelect = getSelectHQL(HQL); 
	    
	    if (NullFlag.isObjNull(tmpSelect)) {
	        return hqlSelect.toString();
	    }
	    
	    String[] selects = tmpSelect.split(sep);
	    for (int i = 0; i < selects.length; i++) {
            String select = selects[i];            
            if (select.indexOf(StringHelper.DOT) < 0) {
        	    String left = "";
        	    String right = "";
        	    int indexBlank = 0;
        	    int indexSKh = 0;
        	    int indexEKh = 0;
        	    int max = 0;
        	    indexBlank = select.lastIndexOf(" ");
        	    indexSKh	= select.lastIndexOf("(");
        	    if (indexBlank > indexSKh) {
        	        max = indexBlank;
        	    } else {
        	        max = indexSKh;
        	    }
        	    if (indexSKh > 0) {
        	        indexEKh = select.indexOf(")");
        	    }
        	    if (max > 0) {
        	        left = select.substring(0,max);
        	        if (indexEKh > 0) {
        		        right = select.substring(indexEKh);
        	            select = select.substring(max+1,indexEKh).trim();
        	        } else {
        	            select = select.substring(max+1).trim(); 
        	        }
        	    }
                hqlSelect.append(sep)
                	.append(left)
                	.append(getEntitySelect(TypeCast.objToString(Entity.get(select.trim())),select.trim()))
                	.append(right)
                	;
            } else {
                hqlSelect.append(sep).append(select);
            }
        }
	    
	    return hqlSelect.substring(1);
	}
	
	/**
	 * ��ȡʵ�������·��
	 * @param entity
	 * @return
	 * @throws ApplicationException
	 * @author �ĻԶ�
	 */
	public static String getFullEntity(String entity) throws ApplicationException {
//	    Configuration conf = new Configuration();
	    String ent = "";
//	    try {
//	        SessionFactory sf =conf.configure().buildSessionFactory();
	        ent	=	TypeCast.objToString(HibernateSession.getConfiguration().getImports().get(entity));
//        } catch (HibernateException he) {
//            throw new ApplicationException("Hibernate����ʱ����",he);
//        }
	    
	    return ent;
	}
	
	/**
	 * ��ȡʵ����������ԣ���HQL��Select���������дһ��
	 * @param entity
	 * @param aliasName
	 * @return
	 * @throws ApplicationException
	 * @author �ĻԶ�
	 */
	public static String getEntitySelect(String entity,String aliasName) throws ApplicationException {
	    Object obj;
	    StringBuffer result	=	new StringBuffer("");
	    
	    try {
            obj = Class.forName(getFullEntity(entity)).newInstance();
            PropertyDescriptor[] pd = PropertyUtils.getPropertyDescriptors(obj);
            for (int i = 0; i < pd.length; i++) {
                if (!pd[i].getName().equals("class"))
                    result.append(",").append(aliasName).append(StringHelper.DOT).append(pd[i].getName());
            }
        } catch (InstantiationException ie) {
            throw new ApplicationException("ʵ����ʵ��ʱ����",ie);
        } catch (IllegalAccessException iae) {
            throw new ApplicationException("���Ϸ��ķ��ʣ�",iae);
        } catch (ClassNotFoundException cnfe) {
            throw new ApplicationException("�����ڵ����ͣ�",cnfe);
        } catch (ApplicationException ae) {
            throw ae;
        }
	    
	    return result.substring(1);
	}
	
	/**
	 * �õ�ĳ������HQL��ѯ����е���������
	 * @param String	hql // ���ڲ�ѯ��HQL���
	 * @param String	property // ��Ҫ������������ 
	 * @return int // ��ѯ�еڼ������ԣ����û���򷵻�-1
	 * @author �ĻԶ�
	 */
	public static int getIndex(String hql,String property) {
	    String sep	=	",";
	    int rtn = -1;
	    String hqlSelect = hql.toLowerCase();
	    String selectBefore	=	"";
	    
	    if (NullFlag.isObjNull(hql)) {
	        return rtn;
	    }
	    if (NullFlag.isObjNull(property)) {
	        return rtn;
	    }
	    if (hqlSelect.indexOf("from")>0) {
	        hqlSelect = hqlSelect.substring(0,hqlSelect.indexOf("from"));
	    } else {
	        hqlSelect = "";
	    }
	    
	    if (hqlSelect.indexOf(property.toLowerCase())>=0) {
	        selectBefore = hqlSelect.substring(0,hqlSelect.indexOf(property.toLowerCase()) + 1);
	        String[] seleBef= selectBefore.split(sep);
	        rtn	=	seleBef.length - 1;
	    }
	    
	    return rtn;
	}

	/**
	 * ��ȡHQL����Select���ֵ����
	 * @param hql
	 * @return
	 * @author �ĻԶ�
	 */
	public static String getSelectHQL(String hql) {
	    String hqlSelect = hql;
	    int select = 0;
	    int from = 0;
	    
	    if (NullFlag.isObjNull(hql)) {
	        return null;
	    }
	    
	    select	=	hqlSelect.toLowerCase().indexOf("select");
	    from	=	hqlSelect.toLowerCase().indexOf("from");
	    if (select < 0) { 
	        hqlSelect = getFromHQL(hql);
		    String sep = " ";
		    StringBuffer sb = new StringBuffer("");
		    String[] entity = hqlSelect.split(",");
		    for (int i = 0; i < entity.length; i++) {
	            String[] alias = entity[i].split(sep);
	            sb.append(",").append(alias[alias.length -1]);
	        }
		    hqlSelect = sb.substring(1);
	    }else {
	        if (from > 0) {
		        if (select < from) {
		            hqlSelect = hqlSelect.substring(select + 6,from).trim();
		        } else {
		            hqlSelect = hqlSelect.substring(0,from).trim();
		        }
		    } else {
		        return "";
		    }
	        getFromHQL(hql);
	    }
	    
	    return hqlSelect;
	}

	/**
	 * ��ȡHQL����Select���ֵ����
	 * @param hql
	 * @return
	 * @author �ĻԶ�
	 */
	public static String getFromHQL(String hql) {
	    String hqlFrom = hql;
	    int from = 0;
	    int where = 0;
	    
	    if (NullFlag.isObjNull(hql)) {
	        return null;
	    }
	    
	    from	=	hqlFrom.toLowerCase().indexOf("from");
	    where	=	hqlFrom.toLowerCase().indexOf("where");
	    if (where > 0) {
	        if (from < where) {
	            hqlFrom = hqlFrom.substring(from + 4,where).trim();
	        } else {
	            hqlFrom = hqlFrom.substring(0,where).trim();
	        }
	    } else {
	        if (from >= 0) 
	            hqlFrom = hqlFrom.substring(from + 4).trim();
	    }
	    
	    if (hqlFrom.length() > 0) {
	        setEntityMap(hqlFrom);
	    }
	    
	    return hqlFrom;
	}
	
	/**
	 * �����е�ʵ�����ʵ���Ӧ�ı������浽��̬������
	 * @param entitys
	 * @author �ĻԶ�
	 */
	private static void setEntityMap(String entitys) {
	    String sep = " ";
	    String[] entity = entitys.split(",");
	    for (int i = 0; i < entity.length; i++) {
            String[] alias = entity[i].split(sep);
            Entity.put(alias[alias.length -1],alias[0]);
        }
	}
	
	/**
	 * ��HQL����е�ʵ��ת����Selectʵ�����ʽ��
	 * ��From Employer ab01-->select ab01.aab001,ab01.aab002 ...... from Employer ab01
	 * @param hql
	 * @return
	 * @throws ApplicationException
	 * @author �ĻԶ�
	 */
	public static String getFullHQL(String hql) throws ApplicationException {
	    String rtn = "";
	    String afterFrom = "";
	    String select = "";
	    int indexFrom = 0;
	    
	    indexFrom = hql.toLowerCase().indexOf("from");
	    if (indexFrom < 0) {
	        throw new ApplicationException("HQL��䲻������������from�Ӿ�");
	    } else {
	        afterFrom = hql.substring(indexFrom);
	    }
	    select = getFullSelect(hql);
	    
	    return "select "+select+" "+afterFrom;
	}

	private static String getEntity(String ent) {
	    String entity = ent;
	    String left = "";
	    String right = "";
	    int indexBlank = 0;
	    int indexSKh = 0;
	    int indexEKh = 0;
	    int max = 0;
	    indexBlank = entity.lastIndexOf(" ");
	    indexSKh	= entity.lastIndexOf("(");
	    if (indexBlank > indexSKh) {
	        max = indexBlank;
	    } else {
	        max = indexSKh;
	    }
	    if (indexSKh > 0) {
	        indexEKh = entity.indexOf(")");
	    }
	    if (max > 0) {
	        left = entity.substring(0,max);
	        if (indexEKh > 0) {
		        right = entity.substring(indexEKh);
	            entity = entity.substring(max+1,indexEKh).trim();
	        } else {
	            entity = entity.substring(max+1).trim(); 
	        }
	    }
	    return entity;
	}
}