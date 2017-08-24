package com.lbs.apps.common;


import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.lbs.commons.log.LogHelper;
import com.lbs.commons.op.OPException;
import com.lbs.commons.op.OPManager;

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

public class CommonSum {
	private LogHelper log = new LogHelper(this.getClass());
	private OPManager op = new OPManager();
	public CommonSum() {
	}

	/**
	 * ��ָ��HQL��ָ���н�����ʹ���
	 * 
	 * @param hql
	 * @param sumCols
	 * @return @throws
	 *         ApplicationException
	 */
	public String[] querySum(String hql, String sumCols[])
			throws ApplicationException {
		int index = hql.toLowerCase().indexOf(" from ");
		StringBuffer hqlf = new StringBuffer("");
		List list = null;

		hqlf.append(" SELECT sum(").append(
				TypeCast.array2String(sumCols, "),sum(")).append(") ").append(
				hql.substring(index));

		log.info("HQL is:" + hqlf);

		try {
			list = op.retrieveObjs(hqlf.toString());
		} catch (OPException oe) {
			throw new ApplicationException("��ȡ����ʱ����", oe);
		}

		String totalSum[] = new String[sumCols.length];
		if (list != null) {
			if (sumCols.length == 1) {
				totalSum[0] = TypeCast.objToString(list.get(0));
			} else {
				Object obj[] = (Object[]) list.get(0);
				for (int i = 0; i < obj.length; i++) {
					totalSum[i] = TypeCast.objToString(obj[i]);
				}
			}
		} else {
			for (int i = 0; i < totalSum.length; i++) {
				totalSum[i] = "0";
			}
		}

		//	  	totalSum = TypeCast.array2String(temp,";");

		return totalSum;
	}
	
	/**
	 * ��ָ��HQL��ָ���н������¼��������
	 * 
	 * @param hql
	 * @param sumCols
	 * @return @throws
	 *         ApplicationException
	 */
	public String[] queryTotal(String hql, String sumCols[])
			throws ApplicationException {
		int index = hql.toLowerCase().indexOf(" from ");
		StringBuffer hqlf = new StringBuffer("");
		List list = null;

		hqlf.append(" SELECT count(").append(
				TypeCast.array2String(sumCols, "),sum(")).append(") ").append(
				hql.substring(index));

		log.info("HQL is:" + hqlf);

		try {
			list = op.retrieveObjs(hqlf.toString());
		} catch (OPException oe) {
			throw new ApplicationException("��ȡ����ʱ����", oe);
		}

		String totalSum[] = new String[sumCols.length];
		if (list != null) {
			if (sumCols.length == 1) {
				totalSum[0] = TypeCast.objToString(list.get(0));
			} else {
				Object obj[] = (Object[]) list.get(0);
				for (int i = 0; i < obj.length; i++) {
					totalSum[i] = TypeCast.objToString(obj[i]);
				}
			}
		} else {
			for (int i = 0; i < totalSum.length; i++) {
				totalSum[i] = "0";
			}
		}

		//	  	totalSum = TypeCast.array2String(temp,";");

		return totalSum;
	}

	public static Map getSummary(String[] viewCols, String[] sum) {
		Map summary = new LinkedHashMap();
		String col = "";
		int index = -1;

		//��Ӻϼ���Ϣ��keyֵ GlobalNames.TOTAL_SUM �Ǻϼ�

		for (int i = 0; i < sum.length; i++) {
			col = viewCols[i];
			index = col.indexOf(".");
			if (index > 0) {
				col = col.substring(index + 1);
			}
			summary.put(col, sum[i]);
		}

		return summary;
	}
	/**
	 * ���������ַ����ĺ�
	 * 
	 * @param String
	 *            total �����ַ���
	 * @param String
	 *            separator �ָ���
	 * @return
	 */
	public static BigDecimal getPageSum(String total, String separator) {
		BigDecimal sum = new BigDecimal(0.0);
		if (NullFlag.isObjNull(total))
			return sum;
		String[] tot = total.split(separator);

		for (int i = 0; i < tot.length; i++) {
			sum = sum.add(new BigDecimal(tot[i]));
		}

		return sum;
	}

	/**
	 * ���������ַ����ĺͣ�Ĭ�Ϸָ���Ϊ����;��
	 * 
	 * @param String
	 *            total �����ַ���
	 * @return
	 */
	public static BigDecimal getPageSum(String total) {
		return getPageSum(total, ";");
	}

	/**
	 * ��List�е�ֵ��ȡ���������һ��ָ���ָ����ָ����ַ�
	 * 
	 * @param list
	 *            List ��ȡֵ��List����
	 * @param prop
	 *            String ������
	 * @throws ApplicationException
	 * @return String
	 */
	public static BigDecimal getPageSum(List list, String prop) {
		String total = "";
		BigDecimal sum = new BigDecimal(0);

		try {
			total = ListUtil.listToString(list, prop, ";");
			sum = getPageSum(total, ";");

		} catch (ApplicationException e) {
			return new BigDecimal(-1);
		}

		return sum;
	}

	/**
	 * ��List�е�ֵ��ȡ���������һ��ָ���ָ����ָ����ַ�
	 * 
	 * @param list
	 *            List ��ȡֵ��List����
	 * @param prop
	 *            String ������
	 * @throws ApplicationException
	 * @return String
	 */
	public static BigDecimal getPageSum(List list, String prop, QueryInfo qi) {
		String total = "";
		BigDecimal sum = new BigDecimal(0);

		try {
			total = ListUtil.listToString(list, prop, ";", qi);
			sum = getPageSum(total, ";");

		} catch (ApplicationException e) {
			return new BigDecimal(-1);
		}

		return sum;
	}

	/**
	 * ��List�е�ֵ��ȡ���������һ��ָ���ָ����ָ����ַ�
	 * 
	 * @param list
	 *            List ��ȡֵ��List����
	 * @param prop
	 *            String ������
	 * @throws ApplicationException
	 * @return String
	 */
	public static String[] getPageSum(List list, String[] prop, QueryInfo qi) {
		String total = "";
		if (prop == null || prop.length < 1)
			return null;
		String[] pageSum = new String[prop.length];

		try {
			for (int i = 0; i < prop.length; i++) {
				total = ListUtil.listToString(list, prop[i], ";", qi);
				BigDecimal sum = getPageSum(total, ";");
				pageSum[i] = sum.toString();
			}
		} catch (ApplicationException e) {
			return null;
		}

		return pageSum;
	}

	/**
	 * ��List�е�ֵ��ȡ���������һ��ָ���ָ����ָ����ַ�
	 * 
	 * @param list
	 *            List ��ȡֵ��List����
	 * @param index
	 *            int �ڼ���ֵ
	 * @throws ApplicationException
	 * @return String
	 */
	public static BigDecimal getPageSum(List list, int index) {
		String total = "";
		BigDecimal sum = new BigDecimal(0);

		try {
			total = ListUtil.listToString(list, index, ";");
			sum = getPageSum(total, ";");

		} catch (ApplicationException e) {
			return new BigDecimal(-1);
		}

		return sum;
	}
}