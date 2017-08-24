package com.lbs.commons;

import java.lang.reflect.InvocationTargetException;

import org.hibernate.type.Type;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.FastArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * Title: leaf framework (lemis core platform)
 * </p>
 * <p>
 * Description: ��ѯ��䴦������
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: LBS
 * </p>
 *
 * @author LBS POC TEAM
 * @version 1.0
 */

public class QueryFactory {
	private Log logger = LogFactory.getLog(QueryFactory.class);
	//    static String baseExp =
	// "[a-z]{2}[a-z0-9]{2}\\.[a-z]{3}[a-z0-9]*\\s*((like)|=|(>)|(<)|(>=)|(<=)|(!=))\\s*:\\s*";
	//    static String baseExp =
	// "([a-zA-Z]+[0-9]*)\\.([a-zA-Z]+[0-9]*)\\s*((like)|=|(>)|(<)|(>=)|(<=)|(!=))\\s*:\\s*";
	static String baseExp = "\\s*((like)|=|(>)|(<)|(>=)|(<=)|(!=))\\s*:";
	static String baseAnd = "\\s*(and)?\\s*";

	public QueryFactory() {

	}

	/**
	 * �������������Բ�ѯHQL���к���
	 * ����baseParaNames�Բ������ƵĶ��壬ȡ��form�е�ֵ���滻��ѯ���baseQueryString�еĲ���
	 *
	 * @param baseQueryString -
	 *            ��ѯ���
	 * @param form -
	 *            ����ֵBean
	 * @param baseParaNames -
	 *            ���������б�
	 * @return �ѽ������滻Ϊ����ֵ�Ĳ�ѯ���
	 * @throws Exception
	 */
	private static String getQueryString(String baseQueryString, Object form,
			String[] baseParaNames) throws Exception {
		String result = baseQueryString;

		String paraName;
		String regExp;
		if (null == baseParaNames) {
			return baseQueryString;
		} else {
			for (int i = 0; i < baseParaNames.length; i++) {
				paraName = baseParaNames[i];

				regExp = new StringBuffer("[(]?([a-zA-Z]+[a-zA-Z0-9]*)[.]?").append(
						"([a-zA-Z]+[a-zA-Z0-9]*)").append(baseExp).append(paraName).append(
						"[)]?").append(baseAnd).toString();

				try {
					if ((null == BeanUtils.getProperty(form, paraName))
							|| (""
									.equals(BeanUtils.getProperty(form,
											paraName)))) {
						result = StringHelper.regexReplace(regExp, "", result);
					}
				} catch (NoSuchMethodException ex) {
					ex.printStackTrace();
					throw new Exception(ex);
				} catch (InvocationTargetException ex) {
					ex.printStackTrace();
					throw new Exception(ex);
				} catch (IllegalAccessException ex) {
					ex.printStackTrace();
					throw new Exception(ex);
				}
			}

			//ȥ������ġ�and���͡�where��,"or"
			boolean flag = true;
			while (flag) {
				if (result.trim().endsWith("and")) {
					result = result.substring(0, result.lastIndexOf("and"));
				} else {
					flag = false;
				}
			}
			flag = true;
			while (flag) {
				if (result.trim().endsWith("where")) {
					result = result.substring(0, result.lastIndexOf("where"));
				} else {
					flag = false;
				}
			}
			flag = true;
			while (flag) {
				if (result.trim().endsWith("or")) {
					result = result.substring(0, result.lastIndexOf("or"));
				} else {
					flag = false;
				}
			}
			return result;
		}
	}

	/**
	 * ���˲����б���ȡ��Ч�����Ĳ��������б�
	 *
	 * @param form -
	 *            ��Ų���ֵ��form����
	 * @param baseParaNames -
	 *            ָ�����п��ܵĲ��������б�
	 * @return ������Ч�����Ĳ��������б�
	 * @throws Exception
	 */
	//�õ�����������
	private static String[] getParaNames(Object form, String[] baseParaNames)
			throws Exception {
		FastArrayList paraNames = new FastArrayList();
		String paraName;
//		int i = 0;
		if (null == baseParaNames) {
			return null;
		} else {
			for (int j = 0; j < baseParaNames.length; j++) {
				paraName = baseParaNames[j];
				try {
					if (!((null == BeanUtils.getProperty(form, paraName)) || (""
							.equals(BeanUtils.getProperty(form, paraName))))) {
						paraNames.add(paraName);
//						i++;
					}
				} catch (NoSuchMethodException ex) {
					ex.printStackTrace();
					throw new Exception(ex);
				} catch (InvocationTargetException ex) {
					ex.printStackTrace();
					throw new Exception(ex);
				} catch (IllegalAccessException ex) {
					ex.printStackTrace();
					throw new Exception(ex);
				}
			}
			String[] result = new String[paraNames.size()];
//			for (i = 0; i < paraNames.size(); i++) {
//				result[i] = (String) paraNames.get(i);
//			}
            paraNames.toArray(result);
			return result;
		}
	}

	/**
	 * ��ȡ����ֵ����
	 *
	 * @param queryString -
	 *            ��ѯ���
	 * @param form -
	 *            ��Ų���ֵ��Bean
	 * @param paraNames -
	 *            ���������б�
	 * @param paraTypes -
	 *            �����������б��������Ϳ�����string,integer,date,boolean,big_decimal
	 * @return @throws
	 *         Exception
	 */
	//�õ���������
	private static String[] getParaValues(String queryString, Object form,
			String[] paraNames, Type[] paraTypes) throws Exception {
		FastArrayList paraValues = new FastArrayList();
		String paraName;
		Type paraType;
		if (null == paraNames) {
			return null;
		} else {
			for (int i = 0; i < paraNames.length; i++) {
				paraName = paraNames[i];
				paraType = paraTypes[i];
				try {
					Object obj = PropertyUtils.getProperty(form, paraName);
					//@todo
					if (paraType.getName().equals("string")) {
						StringBuffer buff = new StringBuffer((String) obj);
                        String rightLike = new StringBuffer("like\\s*:").append(paraName).append("(\\s*[+]+\\s*['%']+)+").toString();
                        String leftLike = new StringBuffer("like\\s*(['%']+\\s*[+]+\\s*)+:").append(paraName).toString();
                        String like = new StringBuffer("like\\s*:").append(paraName).toString();
                        //��like
                        if (StringHelper.exist(rightLike, queryString)) {
                          buff.insert(0, "'").append("%'");
                          paraValues.add(buff.toString());
                          continue;
                        }

                        //��like
                        if(StringHelper.exist(leftLike, queryString)){
                          buff.insert(0, "'%").append("'");
                          paraValues.add(buff.toString());
                          continue;
                        }
                        //like
                        if (StringHelper.exist(like, queryString)) {
                          buff.insert(0, "'%").append("%'");
                          paraValues.add(buff.toString());
                          continue;
                        }

                        buff.insert(0, "'").append("'");
                        paraValues.add(buff.toString());
                        continue;

					}  else if (paraType.getName().equals("date")) {
						StringBuffer sb = new StringBuffer("to_date('");
						sb.append(DateUtil.dateToString(new java.util.Date(((java.sql.Date) obj).getTime()),"YYYY-MM-DD"));
						sb.append("','YYYY-MM-DD')");
						paraValues.add(i, sb.toString());
						continue;
					}  else {
						paraValues.add(obj.toString());
					}

				} catch (NoSuchMethodException ex) {
					ex.printStackTrace();
					throw new Exception(ex);
				} catch (InvocationTargetException ex) {
					ex.printStackTrace();
					throw new Exception(ex);
				} catch (IllegalAccessException ex) {
					ex.printStackTrace();
					throw new Exception(ex);
				}
			}
			String[] result = new String[paraValues.size()];
//			for (int i = 0; i < paraValues.size(); i++) {
//				result[i] = (String) paraValues.get(i);
//			}
            paraValues.toArray(result);
			return result;
		}
	}

	/**
	 * ��ȡ������������
	 *
	 * @param paraNames -
	 *            ���������б�
	 * @param baseParaNames -
	 *            ���п��ܵĲ��������б�
	 * @param baseParaTypes -
	 *            ���п��ܵĲ��������б�
	 * @return - ��paraNames��ƥ��Ĳ��������б�
	 * @throws Exception
	 */
	//�õ�������������
	private static Type[] getParaTypes(String[] paraNames,
			String[] baseParaNames, Type[] baseParaTypes) throws Exception {
		FastArrayList paraTypes = new FastArrayList();
		String paraName;
		if (null == baseParaNames) {
			return null;
		} else {
			for (int i = 0; i < paraNames.length; i++) {
				paraName = paraNames[i];
				for (int j = 0; j < baseParaNames.length; j++) {
					if (paraName.equals(baseParaNames[j])) {
						paraTypes.add(baseParaTypes[j]);
						break;
					}
				}
			}
			Type[] result = new Type[paraTypes.size()];
//			for (int i = 0; i < paraTypes.size(); i++) {
//				result[i] = (Type) paraTypes.get(i);
//			}
            paraTypes.toArray(result);
			return result;
		}
	}

	/**
	 * ��ȡ��ѯ��HQL���
	 *
	 * @param queryString -
	 *            ��ѯ���
	 * @param paraNames
	 * @param paraValues
	 * @return @throws
	 *         Exception
	 */
	private static String getQueryHQL(String queryString, String[] paraNames,
			String[] paraValues) throws Exception {
		String paraName = null;
		String paraValue = null;
		String result = queryString;
        String regExpBegin = "(['%']+\\s*[+]+\\s*)?:";
        String regExpEnd = "(\\s*[+]+\\s*['%']+)?";
		for (int i = 0; i < paraNames.length; i++) {
			paraName = paraNames[i];
			paraValue = (String) paraValues[i];
			try {
				result = StringHelper
						.regexReplace(new StringBuffer(regExpBegin).append(paraName).append(regExpEnd).toString(), paraValue,result);
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new Exception(ex);
			}
		}
		return result;

	}

	/**
	 * ����ָ���Ĳ�����bean��ռλhql����hql
	 *
	 * @param baseQueryString
	 *            String ���硰from Ac01 code where (1 = 1) and code.aab001 like
	 *            :aab001 and code.aac006 = :aac006 "and code.aac034 =
	 *            :aac034"������ռλ����
	 * @param baseParaNames
	 *            String[] ռλ�������飺String[]{"aab001","aac006","aac034"}
	 * @param baseParaTypes
	 *            Type[]
	 *            ռλ�����������飺Type[]{Hibernate.STRING,Hibernate.DATE,Hibernate.SHORT}
	 * @param bean
	 *            Object bean ��ָ����DTO����Ų���ֵ
	 * @return String ����hql���磺from Ac01 code where (1 = 1) and code.aab001 like
	 *         '%00000000074123%' and code.aac006 =
	 *         to_date('1957-01-05','YYYY-MM-DD') and code.aac034 = 167.0
	 */
	public static synchronized String getHQL(String baseQueryString, String[] baseParaNames,
			Type[] baseParaTypes, Object bean) throws Exception {
		if (baseParaNames.length != baseParaTypes.length)
			throw new Exception("������������ĳ����������������ĳ��Ȳ�ƥ�䣡");
		baseQueryString = StringHelper.dealOrderBy(baseQueryString);
		String orderStr = "";
		if (baseQueryString.indexOf(GlobalNames.ORDER_BY) > -1) {
			orderStr = baseQueryString.substring(baseQueryString
					.lastIndexOf(GlobalNames.ORDER_BY));
			baseQueryString = baseQueryString.substring(0, baseQueryString
					.lastIndexOf(GlobalNames.ORDER_BY));
		}
		String[] paraNames = getParaNames(bean, baseParaNames);
		Type[] paraTypes = getParaTypes(paraNames, baseParaNames, baseParaTypes);

		String queryHql = getQueryString(baseQueryString, bean, baseParaNames);
		String[] paraValues = getParaValues(queryHql, bean, paraNames,
				paraTypes);

		String hql = getQueryHQL(queryHql, paraNames, paraValues);
		return (new StringBuffer(hql).append(orderStr)).toString();
	}

}
