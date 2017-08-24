package com.lbs.apps.common;

/**
 * <p>
 * Title: ��ֵ��־��
 * </p>
 * <p>
 * Description: ��ΪJava�еĻ�������û��nullֵ��������������int��double��long������ֵ�� ��Ϊ��Щ�������͵Ŀ�ֵ��־
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author BJLBS </a>
 * @version 1.0
 */

public class NullFlag {

	public NullFlag() {
	}

	/** int ��ֵ��־ֵ��-2^31 */
	public static final int INTNULL = -2147483648;

	/** double ��ֵ��־ֵ��-0.0000000001 */
	public static final double DOUBLENULL = -0.0000000001d;

	/** long ��ֵ��־ֵ��-2^63 */
	public static final long LONGNULL = -9223372036854775808L;

	/**
	 * �ж��Ƿ���int��ֵ��־���
	 * 
	 * @param intPara
	 * @return
	 */
	public static boolean isIntNull(int intPara) {
		if (intPara == INTNULL)
			return true;
		else
			return false;
	}

	/**
	 * �ж�����Ķ����Ƿ�Ϊ��
	 * 
	 * @param Object
	 *            obj
	 * @return @author raofh
	 */
	public static boolean isObjNull(Object obj) {
		if (obj == null || "".equals(obj.toString())) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * �ж��Ƿ���double��ֵ��־���
	 * 
	 * @param doublePara
	 * @return
	 */
	public static boolean isDoubleNull(double doublePara) {
		if (doublePara == DOUBLENULL)
			return true;
		else
			return false;
	}

	/**
	 * �ж��Ƿ���long��ֵ��־���
	 * 
	 * @param longPara
	 * @return
	 */
	public static boolean isLongNull(long longPara) {
		if (longPara == LONGNULL)
			return true;
		else
			return false;
	}

	public static void main(String[] args) {
		int a = -2147483648;
		long b = -9223372036854775808L;
		double c = -0.0000000001;

		System.out.println("int null:" + a);
		System.out.println("is null?:" + isIntNull(a));
		System.out.println("long value:" + LONGNULL);
		System.out.println("is long null:" + isLongNull(b));
		System.out.println("double value:" + c);
		System.out.println("is double?" + isDoubleNull(c));
	}
}