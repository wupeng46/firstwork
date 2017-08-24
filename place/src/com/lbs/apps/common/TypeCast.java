package com.lbs.apps.common;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.lbs.apps.common.DateUtil;

/**
 * <p>
 * Title: 类型转换类
 * </p>
 * <p>
 * Description: 类型转换类，用于把String类型的数据和int、double、Date等类型的互相转换。
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: BJLBS
 * </p>
 * 
 * @author BJLBS </a>
 * @version 1.0
 */

public class TypeCast {
	/**
	 * 把一个String类型的数据，转换成int型的。如果转换失败，抛出ApplicationException
	 * 
	 * @param str
	 * @param paraName
	 * @param isCanNull
	 * @return
	 * @throws ApplicationException
	 */
	public static int stringToInt(String str, String paraName, boolean isCanNull)
			throws ApplicationException {
		if (str == null || str.equals("")) {
			if (isCanNull)
				return NullFlag.INTNULL;
			else
				throw new ApplicationException(" 传入的参数：" + paraName
						+ "为空，无法转换成 int 型 ");
		}
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			throw new ApplicationException("传入的参数：" + paraName
					+ "错误，无法转换成 int 型 ");
		}
	}

	/**
	 * 把一个String类型的数据转换成double型的。如果转换失败，抛出ApplicationException
	 * 
	 * @param str
	 * @param paraName
	 * @param isCanNull
	 * @return
	 * @throws ApplicationException
	 */
	public static double stringToDouble(String str, String paraName,
			boolean isCanNull) throws ApplicationException {
		if (str == null || str.equals("")) {
			if (isCanNull)
				return NullFlag.DOUBLENULL;
			else
				throw new ApplicationException(" 传入的参数：" + paraName
						+ "为空，无法转换成 double 型 ");
		}
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			throw new ApplicationException(" 传入的参数：" + paraName
					+ "错误，无法转换成 double 型 ");
		}
	}

	/**
	 * This method format a string to a java.sql.Date's object.The string must
	 * like "yyyy-MM-dd". For Example:
	 * TypeCast.stringToDate("1995-1-5","parameter name",false);
	 * 
	 * @param strDate
	 *            A String whose should be parsed.
	 * @param paraName
	 * @param isCanNull
	 *            if this parameter is false,strDate must be not null.
	 * @return
	 * @throws ApplicationException
	 */
	public static Date stringToDate(String strDate, String paraName,
			boolean isCanNull) throws ApplicationException {
		Date targetDate = null;
		if (strDate == null || strDate.equals("")) {
			if (isCanNull)
				return null;
			else
				throw new ApplicationException("传入的参数：" + paraName
						+ "为空，无法转换成 Date 型 ");
		}
		if (strDate.indexOf("-") == -1)
			throw new ApplicationException(" 传入的参数：" + paraName
					+ "格式不对，无法转换成 Date 型 ");
		int yyyy, MM, dd;
		try {
			yyyy = Integer.parseInt(strDate.substring(0, strDate.indexOf("-")));
			MM = Integer.parseInt(strDate.substring(strDate.indexOf("-") + 1,
					strDate.lastIndexOf("-")));
			dd = Integer.parseInt(strDate.substring(
					strDate.lastIndexOf("-") + 1, strDate.length()));

			targetDate = DateUtil.getDate(yyyy, MM, dd);
		} catch (NumberFormatException nfe) {
			throw new ApplicationException(" 传入的参数：" + paraName
					+ "错误，无法转换成 Date 型 ");
		} catch (IllegalArgumentException e) {
			throw new ApplicationException(" 传入的参数：" + paraName
					+ "错误，无法转换成 Date 型 ");
		}
		return targetDate;
	}

	/**
	 * This method format a string to a java.sql.Date's object.The string must
	 * like "yyyy-MM-dd HH:mm:ss". For Example:
	 * TypeCast.StringToDateTime("1995-10-5 16:24:15","parameter name",false);
	 * 
	 * @param strDate
	 * @param paraName
	 * @param isCanNull
	 * @return
	 * @throws ApplicationException
	 */
	public static Date stringToDateTime(String strDate, String paraName,
			boolean isCanNull) throws ApplicationException {
		Date targetDate = null;
		if (strDate == null || strDate.equals("")) {
			if (isCanNull)
				return null;
			else
				throw new ApplicationException(" 传入的参数：" + paraName
						+ "为空，无法转换成 Date 型 ");
		}
		DateFormat dFor = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			targetDate = new Date(dFor.parse(strDate).getTime());
		} catch (ParseException parseE) {
			throw new ApplicationException(" 传入的参数：" + paraName
					+ "错误，无法转换成 Date 型 ");
		}
		return targetDate;
	}

	/**
	 * 把字符串转换成 符合某种Mask 格式的字符串 。 例如：把身高的 165厘米转换成 165.0 或165.01 厘米等。
	 * 
	 * @param str
	 *            需要转换的字符串 （只能含有数字和小数点）
	 * @param n
	 *            转换后小数点后的位数
	 * @return String
	 */
	public static String stringToStrForMask(String str, int n) {
		if (str != null && !"".equals(str.trim())) {
			int index = str.indexOf(".");
			if (index == -1) {
				str = str + ".";
				for (int i = 0; i < n; i++) {
					str = str + "0";
				}
			} else {
				for (int i = 0; i < n; i++) {
					str = str + "0";
				}
				str = str.substring(0, index + n + 1);
			}
		}
		return str;
	}

	/**
	 * int类型转换成String类型。
	 * 
	 * @param para
	 * @return
	 */
	public static String intToString(int para) {
		return "" + para;
	}

	/**
	 * int类型转换成Integer类型。
	 * 
	 * @param para
	 * @return
	 */
	public static Integer intToInteger(int para) {
		return Integer.getInteger("" + para);
	}

	/**
	 * 把一个double型的数据转换成一个字符串，用于显示。 按照货币表示：0为0.00； 19为19.00；24.1为24.10；
	 * 25.225为25.23。
	 * 
	 * @param para
	 * @return
	 * @modify 饶福华
	 */
	private static String doubleToString(double para) {
		// modify by 饶福华
		NumberFormat nf = new DecimalFormat("##0.00");
		String strValue = nf.format(para);
		return strValue;
	}
	
	/*
	 * 把一个double型的数据转换成一个字符串，用于显示
	 * 入参：数据，小数点后位数
	 *  modify by 文辉东
	 */
	private static String doubleToString(double para,int ws) {		
		String ls_ws="";
		while (ls_ws.length()<ws){
			ls_ws=ls_ws+"0";
		}
		String ls_format="##0."+ls_ws;
		NumberFormat nf = new DecimalFormat(ls_format);
		String strValue = nf.format(para);
		return strValue;
	}

	/**
	 * format a Date to String. the String like "yyyy-MM-dd". if the Date is
	 * null,return a empty String
	 * 
	 * @param date
	 * @return
	 */
	public static String simpleDateToString(Date date) {
		if (date == null)
			return "";
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		return format.format(date);
	}

	/**
	 * format a Date to String. the String like "yyyy-MM-dd HH:mm:ss" if the
	 * Date is null,return a empty String
	 * 
	 * @param date
	 * @return
	 */
	public static String dateTimeToString(Date date) {
		if (date == null)
			return "";
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	/**
	 * 字符串转换，如果是null，允许为空，返回""； 不允许为空，throws
	 * ApplicationException，字符串不为空，返回strSource.trim()
	 * 
	 * @param strSource
	 * @param paraName
	 * @param isCanNull
	 * @return
	 * @throws ApplicationException
	 */
	public static String stringToString(String strSource, String paraName,
			boolean isCanNull) throws ApplicationException {
		if (strSource == null) {
			if (isCanNull)
				return "";
			else
				throw new ApplicationException(" 传入的参数：" + paraName
						+ "为空，无法转换成 String 型 ");
		}
		return strSource;
	}

	/**
	 * 字符串转换，如果是null，返回empty String
	 * 
	 * @param strSource
	 * @return
	 */
	public static String stringToString(String strSource) {
		if (strSource == null)
			return "";
		else
			return strSource;
	}

	/**
	 * 字符串转换成Integer，输入必须是
	 * 
	 * @param strSource
	 *            String
	 * @return Integer
	 */
	public static Integer stringToInteger(String strSource) {
		if (strSource == null || strSource.trim().equals("")){
			return new Integer("0");
		}else if(strSource.indexOf("年")>1||strSource.indexOf("月")>1||strSource.indexOf("日")>1){
		    strSource.replaceAll("年","");
		    strSource.replaceAll("月","");
		    strSource.replaceAll("日","");
		    return new Integer(strSource);
		}else {
			if (CommonVerify.numberVerify(strSource)) {
				return new Integer(strSource);
			} else {
				return new Integer("0");
			}
		}
	}

	/**
	 * 把一个String类型的数据转换成long型。如果转换失败，抛出ApplicationException
	 * 
	 * @param str
	 * @param paraName
	 * @param isCanNull
	 * @return
	 * @throws ApplicationException
	 */
	public static long stringToLong(String str, String paraName,
			boolean isCanNull) throws ApplicationException {
		if (str == null || str.equals("")) {
			if (isCanNull)
				return NullFlag.LONGNULL;
			else
				throw new ApplicationException(" 传入的参数：" + paraName
						+ "为空，无法转换成 long 型 ");
		}
		try {
			return Long.parseLong(str);
		} catch (NumberFormatException nfe) {
			throw new ApplicationException(" 传入的参数：" + paraName
					+ "错误，无法转换成 long 型 ");
		}
	}

	/**
	 * long型转换成String类型。
	 * 
	 * @param para
	 * @return
	 */
	public static String longToString(long para) {
		return "" + para;
	}

	/**
	 * 非金额的double类型数据转换成String类型，直接转换，不定义格式
	 * 
	 * @param para
	 * @return
	 */
	public static String notMoneyDoubleToString(double para) {
		return "" + para;
	}
	
	

	/**
	 * Integer to String
	 * 
	 * @param i
	 *            Integer
	 * @return String
	 */
	public static String integerToString(Integer i) {
		if (i == null)
			return "";
		return i.intValue() + "";
	}

	/**
	 * Integer to String
	 * 
	 * @param i
	 *            Integer
	 * @return String
	 */
	public static String bigDecimalToString(BigDecimal i) {
		if (i == null)
			return "";
		return i.toString();
	}

	/**
	 * 将输入的Integer类型的月数转化成"X年X月"格式的字符串
	 * 
	 * @param month
	 *            Integer
	 * @return String
	 */
	public static String month2YearMonth(Integer month) {
		String yearMonth = "";
		int smonth = 0;
		int year = 0;
		int rmonth = 0;

		if ((month == null) || (month.equals(new Integer("0")))) {
			return "0月";
		}

		smonth = month.intValue();
		year = smonth / 12;
		rmonth = smonth - 12 * year;

		if (year > 0) {
			yearMonth = intToString(year) + "年";
		}
		if (rmonth > 0) {
			yearMonth += intToString(rmonth) + "个月";
		}

		return yearMonth;
	}

	/**
	 * 将输入的费款所属期转化成"XXXX年XX月"格式的字符串
	 * 
	 * @param month
	 *            Integer
	 * @return String
	 */
	public static String period2YearMonth(String ym) {
		if (ym == null) {
			return "";
		} else {
			String year = ym.substring(0, 4);
			String month = ym.substring(4, 6);
			return year + "年" + month + "月";
		}
	}

	/**
	 * 把一个double型的数据转换成一个字符串，用于显示。 按照货币表示：0为0.00； 19为19.00；24.1为24.10；
	 * 25.225为25.23。
	 * 
	 * @param para
	 * @return
	 */
	public static String moneyDoubleToString(double para) {
		return doubleToString(para);
	}
	
	public static String moneyDoubleToString(double para,int ws) {
		return doubleToString(para,ws);
	}

	/**
	 * 将BigDecimal转化为Money。
	 * 
	 * @param money
	 *            BigDecimal
	 * @return Money
	 * @author raofh
	 */
	public static Money bigDecimal2Money(BigDecimal money) {
		if (money == null) {
			return Money.ZERO;
		} else if (money.intValue() < 0) {
			return Money.ZERO;
		} else {
			return new Money(money);
		}
	}

	/**
	 * 将输入的参数转化为非空(null)的字符串
	 * 
	 * @param obj
	 *            Object
	 * @return String
	 * @author raofh
	 */
	public static String objToString(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return obj.toString();
		}
	}

	public static String array2String(String[] array, String separator) {
		StringBuffer ret = new StringBuffer("");

		if (array == null || array.length <= 0) {
			return "";
		}

		for (int i = 0; i < array.length; i++) {
			ret.append(separator).append(array[i]);
		}

		return ret.substring(separator.length());
	}

	/**
	 * 返回Money值
	 * 
	 * @param obj
	 *            Object
	 * @return String
	 */
	public static Money objToMoney(Object obj) {
		if (obj == null) {
			return Money.ZERO;
		} else {
			try {
				if (obj instanceof BigDecimal) {
					return new Money((BigDecimal) obj);
				} else if (obj instanceof String) {
					return new Money(new BigDecimal((String) obj));
				} else {
					return Money.ZERO;
				}
			} catch (Exception e) {
				return Money.ZERO;
			}
		}
	}

	/**
	 * 返回BigDecimal值
	 * 
	 * @param obj
	 *            Object
	 * @return String
	 */
	public static BigDecimal objToBigDecimal(Object obj) {
		if (obj == null) {
			return BigDecimal.valueOf(0);
		} else {
			try {
				if (obj instanceof BigDecimal) {
					return (BigDecimal) obj;
				} else if (obj instanceof String) {
					return new BigDecimal((String) obj);
				} else {
					return BigDecimal.valueOf(0);
				}
			} catch (Exception e) {
				return BigDecimal.valueOf(0);
			}
		}
	}

	/**
	 * 将输入的参数转化为非空(null)的字符串
	 * 
	 * @param obj
	 *            Object
	 * @return String
	 * @author raofh
	 */
	public static String aab301Trim(String aab301) {
		if (aab301 == null || "".equals(aab301.trim())) {
			return "";
		} else {
			while (aab301.substring(aab301.length() - 1, aab301.length())
					.equals("0")) {
				aab301 = aab301.substring(0, aab301.length() - 1);
			}
			return aab301;
		}
	}
	
	public static String numtochinese(String input){ 
		String s1="零壹贰叁肆伍陆柒捌玖"; 
		String s4="分角整元拾佰仟万拾佰仟亿拾佰仟"; 
		String temp=""; 
		String result=""; 
		if (input==null) return "输入字串不是数字串只能包括以下字符（?0?～?9?，?.?)，输入字串最大只能精确到仟亿，小数点只能两位！"; 
		temp=input.trim(); 
		float f; 
		try{ 
		f=Float.parseFloat(temp); 

		}catch(Exception e){return "输入字串不是数字串只能包括以下字符（?0?～?9?，?.?)，输入字串最大只能精确到仟亿，小数点只能两位！";} 
		int len=0; 
		if (temp.indexOf(".")==-1) len=temp.length(); 
		else len=temp.indexOf("."); 
		if(len>s4.length()-3) return("输入字串最大只能精确到仟亿，小数点只能两位！"); 
		int n1,n2=0; 
		String num=""; 
		String unit=""; 

		for(int i=0;i<temp.length();i++){ 
		if(i>len+2){break;} 
		if(i==len) {continue;} 
		n1=Integer.parseInt(String.valueOf(temp.charAt(i))); 
		num=s1.substring(n1,n1+1); 
		n1=len-i+2; 
		unit=s4.substring(n1,n1+1); 
		result=result.concat(num).concat(unit); 
		} 
		if ((len==temp.length()) || (len==temp.length()-1)) result=result.concat("整"); 
		if (len==temp.length()-2) result=result.concat("零分"); 
		return result; 
	} 



	public static void main(String[] args) {
		//    //String strDate = "2003-1-8";
		//    //java.text.SimpleDateFormat format = new
		// java.text.SimpleDateFormat("yyyy-MM-dd");
		//    //NumberFormat nf = new DecimalFormat("###.##");
		//    Integer para = new Integer("0");
		//    Integer para1 = null;
		//    Integer para2 = new Integer("2");
		//    Integer para3 = new Integer("23");
		//    //String strValue = nf.format(para);
		//
		//    System.out.println(month2YearMonth(para));
		//    System.out.println(month2YearMonth(para1));
		//    System.out.println(month2YearMonth(para2));
		//    System.out.println(month2YearMonth(para3));
		//    String strDate = "1998-02-01 18:34:60";
		//    try{
		//    System.out.println(dateTimeToString(stringToDateTime(strDate,"aaa",true)));
		//    }
		//    catch(Exception e)
		//    {
		//      e.printStackTrace();
		//    }
		String xxje = "200405.32";  //小写金额
		System.out.println("大写:"+numtochinese(xxje));
	}
}