package com.lbs.apps.common;

/**
 * <p>
 * Title: 空值标志类
 * </p>
 * <p>
 * Description: 因为Java中的基本类型没有null值，在这里设置了int、double、long的特殊值， 作为这些基本类型的空值标志
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

	/** int 空值标志值：-2^31 */
	public static final int INTNULL = -2147483648;

	/** double 空值标志值：-0.0000000001 */
	public static final double DOUBLENULL = -0.0000000001d;

	/** long 空值标志值：-2^63 */
	public static final long LONGNULL = -9223372036854775808L;

	/**
	 * 判断是否与int空值标志相等
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
	 * 判断输入的对象是否为空
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
	 * 判断是否与double空值标志相等
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
	 * 判断是否与long空值标志相等
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