package com.lbs.commons;

import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.lbs.commons.op.HibernateSession;

/**
 * <p>
 * Title: leaf framework (lemis core platform)
 * </p>
 * <p>
 * Description: 提供有关日期的实用方法集
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
public class DateUtil {

	private static Log logger = LogFactory.getLog(DateUtil.class);

	public DateUtil() {
	}
	/**
	 * 返回数据库系统当前的日期
	 *
	 * @return Date - java.sql.Date，数据库服务器的当前日期
	 */
	public static java.sql.Date getDBDate() {
		String s = null;
		try {
			Session sess = HibernateSession.currentSession();
			java.sql.Connection conn = sess.connection();
			java.sql.Statement st = conn.createStatement();
			java.sql.ResultSet rs = st
					.executeQuery("select to_char(sysdate,'yyyy-mm-dd') from dual");
			while (rs.next()) {
				s = rs.getString(1);
			}
			if(null != rs)
				rs.close();
			if(null != st)
				st.close();
			HibernateSession.closeSession();
		} catch (Exception ex) {
			logger.error("异常");
		}
		return new java.sql.Date(stringToDate(s, "yyyy-mm-dd").getTime());

	}
	/**
	 * 根据身份证号码获取年龄
	 *
	 * @param id
	 *            String 身份证号
	 * @throws Exception
	 *             身份证号错误时发生
	 * @return int - 年龄
	 */

	public static int getAge(String id) throws Exception {
		int age = -1;
		int length = id.length();
		String birthday = "";
		if (length == 15) {
			birthday = id.substring(6, 8);
			birthday = "19" + birthday;
		} else if (length == 18) {
			birthday = id.substring(6, 10);
		} else {
			throw new Exception("错误的身份证号");
		}
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		age = currentYear - (new Integer(birthday)).intValue();
		return age;
	}

	/**
	 * 根据年龄获取出生年份
	 *
	 * @param age
	 *            int 年龄
	 * @return Date - 出生年份
	 */
	public static java.sql.Date getDateByAge(int age) {
		Calendar calendar = Calendar.getInstance(Locale.CHINESE);
		long current = calendar.getTimeInMillis();
		calendar.set(calendar.get(Calendar.YEAR) - age, calendar
				.get(Calendar.MONTH), calendar.get(Calendar.DATE));
		return new java.sql.Date((calendar.getTimeInMillis()));
	}

	/**
	 * 将日期变量转换为"YYYY年MM月DD日"形式
	 *
	 * @param d
	 *            Date
	 * @return String
	 */
	public static String getChineseDate(Date d) {
		if (d == null)
			return new String();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd",
				new DateFormatSymbols());

		String dtrDate = df.format(d);
		return dtrDate.substring(0, 4) + "年"
				+ Integer.parseInt(dtrDate.substring(4, 6)) + "月"
				+ Integer.parseInt(dtrDate.substring(6, 8)) + "日";
	}

	/**
	 * 获取当前日期为字符串
	 *
	 * @return String - 当前日期字符串，格式为YYMMDD
	 */
	public static String getCurrentDate_String() {
		Calendar cal = Calendar.getInstance();

		String currentDate = null;
		Date d = cal.getTime();

		String currentYear = (new Integer(cal.get(Calendar.YEAR))).toString();
		String currentMonth = null;
		String currentDay = null;
		if (cal.get(Calendar.MONTH) < 9)
			currentMonth = "0"
					+ (new Integer(cal.get(Calendar.MONTH) + 1)).toString();
		else
			currentMonth = (new Integer(cal.get(Calendar.MONTH) + 1))
					.toString();
		if (cal.get(Calendar.DAY_OF_MONTH) < 10)
			currentDay = "0"
					+ (new Integer(cal.get(Calendar.DAY_OF_MONTH))).toString();
		else
			currentDay = (new Integer(cal.get(Calendar.DAY_OF_MONTH)))
					.toString();
		currentDate = currentYear + currentMonth + currentDay;
		return currentDate;
	}

	/**
	 * 按照指定格式获取当前日期为字符串
	 *
	 * @param strFormat
	 *            String - 日期格式定义
	 * @return String 当前日期字符串
	 */
	public static String getCurrentDate_String(String strFormat) {
		Calendar cal = Calendar.getInstance();

		String currentDate = null;
		Date d = cal.getTime();

		return getDate(d, strFormat);
	}

	/**
	 * 比较两个日期(年月型，格式为YYYYMM)之间相差月份
	 *
	 * @param dealMonth -
	 *            开始年月
	 * @param alterMonth -
	 *            结束年月
	 * @return alterMonth-dealMonth相差的月数
	 */
	public static int calBetweenTwoMonth(String dealMonth, String alterMonth) {
		int length = 0;
		if ((dealMonth.length() != 6) || (alterMonth.length() != 6)) {
			//				System.out.println("比较年月字符串的长度不正确");
			length = -1;

		} else {
			int dealInt = Integer.parseInt(dealMonth);
			int alterInt = Integer.parseInt(alterMonth);
			if (dealInt < alterInt) {
				//					System.out.println("第一个年月变量应大于或等于第二个年月变量");
				length = -2;
			} else {
				int dealYearInt = Integer.parseInt(dealMonth.substring(0, 4));
				int dealMonthInt = Integer.parseInt(dealMonth.substring(4, 6));
				int alterYearInt = Integer.parseInt(alterMonth.substring(0, 4));
				int alterMonthInt = Integer
						.parseInt(alterMonth.substring(4, 6));
				length = (dealYearInt - alterYearInt) * 12
						+ (dealMonthInt - alterMonthInt);
			}
		}

		return length;
	}

	/**
	 * 得到日期中的年份
	 *
	 * @param date
	 * @return yyyy格式的年份
	 */
	public static int convertDateToYear(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy",
				new DateFormatSymbols());
		return Integer.parseInt(df.format(date));
	}

	/**
	 * 得到日期中年月组成的字符串
	 *
	 * @param d
	 * @return yyyyMM格式的年月字符串
	 */
	public static String convertDateToYearMonth(Date d) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM",
				new DateFormatSymbols());
		return df.format(d);
	}

	/**
	 * 得到日期中年月日组成的字符串
	 *
	 * @param d
	 * @return yyyyMMdd格式的年月日字符串
	 */
	public static String convertDateToYearMonthDay(Date d) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd",
				new DateFormatSymbols());
		return df.format(d);
	}

	/**
	 * 得到两个日期之间相差的天数
	 *
	 * @param newDate
	 * @param oldDate
	 * @return newDate-oldDate相差的天数
	 */
	public static int daysBetweenDates(Date newDate, Date oldDate) {
		int days = 0;
		Calendar calo = Calendar.getInstance();
		Calendar caln = Calendar.getInstance();
		calo.setTime(oldDate);
		caln.setTime(newDate);
		int oday = calo.get(Calendar.DAY_OF_YEAR);
		int nyear = caln.get(Calendar.YEAR);
		int oyear = calo.get(Calendar.YEAR);
		while (nyear > oyear) {
			calo.set(Calendar.MONTH, 11);
			calo.set(Calendar.DATE, 31);
			days = days + calo.get(Calendar.DAY_OF_YEAR);
			oyear = oyear + 1;
			calo.set(Calendar.YEAR, oyear);
			//System.out.println("YEAR:"+calo.get(Calendar.YEAR)+"MONTH:"+calo.get(Calendar.MONTH)+"DAY:"+calo.get(Calendar.DATE));

		}
		int nday = caln.get(Calendar.DAY_OF_YEAR);
		days = days + nday - oday;

		/*
		 * System.out.println("day:"+calo.get(Calendar.DAY_OF_YEAR));
		 * calo.set(Calendar.YEAR,2000);
		 * System.out.println("day:"+caln.get(Calendar.DAY_OF_YEAR));
		 * ((GregorianCalendar)calo).isLeapYear(1998);
		 * System.out.println("YEAR:"+calo.get(Calendar.YEAR)+"MONTH:"+calo.get(Calendar.MONTH)+"DAY:"+calo.get(Calendar.DATE));
		 * for(int i=1;i <30;i++){ calo.add(Calendar.DATE,1);
		 * System.out.println("start");
		 * System.out.println("YEAR:"+calo.get(Calendar.YEAR)+"MONTH:"+calo.get(Calendar.MONTH)+"DAY:"+calo.get(Calendar.DATE)); }
		 */
		return days;
	}

	/**
	 * 取得与原日期相差一定天数的日期，返回Date型日期
	 *
	 * @param date
	 * @param intBetween
	 * @return date加上intBetween天后的日期
	 */
	public static Date getDateBetween(Date date, int intBetween) {
		Calendar calo = Calendar.getInstance();
		calo.setTime(date);
		calo.add(Calendar.DATE, intBetween);
		return calo.getTime();
	}

	/**
	 * 按指定格式取得与原日期相差一定天数的日期，返回String型日期
	 *
	 * @param date
	 * @param intBetween
	 * @param strFromat
	 * @return date加上intBetween天后的日期
	 */
	public static String getDateBetween_String(Date date, int intBetween,
			String strFromat) {
		Date dateOld = getDateBetween(date, intBetween);
		return getDate(dateOld, strFromat);
	}

	/**
	 * 得到将年月型字符串增加1月后的日期字符串
	 *
	 * @param yearMonth
	 *            yyyyMM格式
	 * @return yearMonth增加一个月后的日期，yyyyMM格式
	 */
	public static String increaseYearMonth(String yearMonth) {
		int year = (new Integer(yearMonth.substring(0, 4))).intValue();
		int month = (new Integer(yearMonth.substring(4, 6))).intValue();
		month = month + 1;
		if (month <= 12 && month >= 10)
			return yearMonth.substring(0, 4) + (new Integer(month)).toString();
		else if (month < 10)
			return yearMonth.substring(0, 4) + "0"
					+ (new Integer(month)).toString();
		else
			//if(month>12)
			return (new Integer(year + 1)).toString() + "0"
					+ (new Integer(month - 12)).toString();

	}

	/**
	 * 得到将年月型字符串增加指定月数后的日期字符串
	 *
	 * @param yearMonth -
	 *            yyyyMM格式
	 * @return - yearMonth增加addMonth个月后的日期，yyyyMM格式
	 */
	public static String increaseYearMonth(String yearMonth, int addMonth) {
		int year = (new Integer(yearMonth.substring(0, 4))).intValue();
		int month = (new Integer(yearMonth.substring(4, 6))).intValue();
		month = month + addMonth;
		year = year + month / 12;
		month = month % 12;
		if (month <= 12 && month >= 10)
			return year + (new Integer(month)).toString();
		else
			return year + "0" + (new Integer(month)).toString();

	}

	/**
	 * 得到将年月型字符串减去1月后的日期字符串
	 *
	 * @param yearMonth -
	 *            yyyyMM格式
	 * @return - yearMonth减少一个月的日期，yyyyMM格式
	 */
	public static String descreaseYearMonth(String yearMonth) {
		int year = (new Integer(yearMonth.substring(0, 4))).intValue();
		int month = (new Integer(yearMonth.substring(4, 6))).intValue();
		month = month - 1;
		if (month >= 10)
			return yearMonth.substring(0, 4) + (new Integer(month)).toString();
		else if (month > 0 && month < 10)
			return yearMonth.substring(0, 4) + "0"
					+ (new Integer(month)).toString();
		else
			//if(month>12)
			return (new Integer(year - 1)).toString()
					+ (new Integer(month + 12)).toString();

	}

	//    /**
	//     * 得到将年月型字符串加上1月后的日期字符串
	//     * @param yearMonth
	//     * @return
	//     */
	//    public static String addYearMonth(String yearMonth) {
	//        int year = (new Integer(yearMonth.substring(0, 4))).intValue();
	//        int month = (new Integer(yearMonth.substring(4, 6))).intValue();
	//        month = month + 1;
	//        if (month >= 10 && month < 12)
	//            return yearMonth.substring(0, 4) + (new Integer(month)).toString();
	//        else if (month > 0 && month < 10)
	//            return yearMonth.substring(0, 4) + "0" + (new Integer(month)).toString();
	//        else //if(month>12)
	//            return (new Integer(year + 1)).toString() + "0" + (new Integer(month -
	// 12)).toString();
	//
	//    }
	//
	//    /**
	//     * 得到带有中文日月年的日期字符串
	//     * @param d
	//     * @return
	//     */
	//    public static String getChineseDate_Date(Date d) {
	//        if (d == null)
	//            return new String();
	//        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", new
	// DateFormatSymbols());
	//
	//        String dtrDate = df.format(d);
	//        return dtrDate.substring(0, 4) + "年" +
	//            Integer.parseInt(dtrDate.substring(4, 6)) + "月" +
	//            Integer.parseInt(dtrDate.substring(6, 8)) + "日";
	//    }

	/**
	 * 获取当前日期为日期型
	 *
	 * @return 当前日期，java.util.Date类型
	 */
	public static Date getCurrentDate() {
		Calendar cal = Calendar.getInstance();

		String currentDate = null;
		Date d = cal.getTime();

		return d;
	}
	
	//获取当前时间的一年后的时间
	public static Date getAfterYearDate(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.YEAR,1);
		Date date=cal.getTime(); 
		return date;
	}
	/**
	 * 获取当前年月的字符串
	 *
	 * @return 当前年月，yyyyMM格式
	 */
	public static String getCurrentYearMonth() {
		Calendar cal = Calendar.getInstance();
		String currentYear = (new Integer(cal.get(Calendar.YEAR))).toString();
		String currentMonth = null;
		if (cal.get(Calendar.MONTH) < 9)
			currentMonth = "0"
					+ (new Integer(cal.get(Calendar.MONTH) + 1)).toString();
		else
			currentMonth = (new Integer(cal.get(Calendar.MONTH) + 1))
					.toString();
		return (currentYear + currentMonth);
	}

	/**
	 * 获取当前年为整型
	 *
	 * @return 获取当前日期中的年，int型
	 */
	public static int getCurrentYear() {
		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(Calendar.YEAR);
		return currentYear;
	}

	/**
	 * 将指定格式的字符串转换为日期型
	 *
	 * @param strDate -
	 *            日期
	 * @param oracleFormat
	 *            --oracle型日期格式
	 * @return 转换得到的日期
	 */
	public static Date stringToDate(String strDate, String oracleFormat) {
		//SimpleDateFormat df=new SimpleDateFormat(javaFormat,new
		// DateFormatSymbols());
		//SimpleDateFormat df = new SimpleDateFormat(javaFormat);
		if (strDate == null)
			return null;
		Hashtable h = new Hashtable();
		String javaFormat = new String();
		String s = oracleFormat.toLowerCase();
		if (s.indexOf("yyyy") != -1)
			h.put(new Integer(s.indexOf("yyyy")), "yyyy");
		else if (s.indexOf("yy") != -1)
			h.put(new Integer(s.indexOf("yy")), "yy");
		if (s.indexOf("mm") != -1)
			h.put(new Integer(s.indexOf("mm")), "MM");

		if (s.indexOf("dd") != -1)
			h.put(new Integer(s.indexOf("dd")), "dd");
		if (s.indexOf("hh24") != -1)
			h.put(new Integer(s.indexOf("hh24")), "HH");
		if (s.indexOf("mi") != -1)
			h.put(new Integer(s.indexOf("mi")), "mm");
		if (s.indexOf("ss") != -1)
			h.put(new Integer(s.indexOf("ss")), "ss");

		int intStart = 0;
		while (s.indexOf("-", intStart) != -1) {
			intStart = s.indexOf("-", intStart);
			h.put(new Integer(intStart), "-");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf("/", intStart) != -1) {
			intStart = s.indexOf("/", intStart);
			h.put(new Integer(intStart), "/");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf(" ", intStart) != -1) {
			intStart = s.indexOf(" ", intStart);
			h.put(new Integer(intStart), " ");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf(":", intStart) != -1) {
			intStart = s.indexOf(":", intStart);
			h.put(new Integer(intStart), ":");
			intStart++;
		}

		if (s.indexOf("年") != -1)
			h.put(new Integer(s.indexOf("年")), "年");
		if (s.indexOf("月") != -1)
			h.put(new Integer(s.indexOf("月")), "月");
		if (s.indexOf("日") != -1)
			h.put(new Integer(s.indexOf("日")), "日");
		if (s.indexOf("时") != -1)
			h.put(new Integer(s.indexOf("时")), "时");
		if (s.indexOf("分") != -1)
			h.put(new Integer(s.indexOf("分")), "分");
		if (s.indexOf("秒") != -1)
			h.put(new Integer(s.indexOf("秒")), "秒");

		int i = 0;
		while (h.size() != 0) {
			Enumeration e = h.keys();
			int n = 0;
			while (e.hasMoreElements()) {
				i = ((Integer) e.nextElement()).intValue();
				if (i >= n)
					n = i;
			}
			String temp = (String) h.get(new Integer(n));
			h.remove(new Integer(n));

			javaFormat = temp + javaFormat;
		}
		//System.out.println(javaFormat);
		SimpleDateFormat df = new SimpleDateFormat(javaFormat);

		Date myDate = new Date();
		try {
			myDate = df.parse(strDate);
		} catch (Exception e) {
			return null;
		}

		return myDate;
	}

	/**
	 * 获取输入格式的日期字符串，字符串遵循Oracle格式
	 *
	 * @param d -
	 *            日期
	 * @param format -
	 *            指定日期格式，格式的写法为Oracle格式
	 * @return 按指定的日期格式转换后的日期字符串
	 */
	public static String dateToString(Date d, String format) {
		if (d == null)
			return "";
		Hashtable h = new Hashtable();
		String javaFormat = new String();
		String s = format.toLowerCase();
		if (s.indexOf("yyyy") != -1)
			h.put(new Integer(s.indexOf("yyyy")), "yyyy");
		else if (s.indexOf("yy") != -1)
			h.put(new Integer(s.indexOf("yy")), "yy");
		if (s.indexOf("mm") != -1)
			h.put(new Integer(s.indexOf("mm")), "MM");

		if (s.indexOf("dd") != -1)
			h.put(new Integer(s.indexOf("dd")), "dd");
		if (s.indexOf("hh24") != -1)
			h.put(new Integer(s.indexOf("hh24")), "HH");
		if (s.indexOf("mi") != -1)
			h.put(new Integer(s.indexOf("mi")), "mm");
		if (s.indexOf("ss") != -1)
			h.put(new Integer(s.indexOf("ss")), "ss");

		int intStart = 0;
		while (s.indexOf("-", intStart) != -1) {
			intStart = s.indexOf("-", intStart);
			h.put(new Integer(intStart), "-");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf("/", intStart) != -1) {
			intStart = s.indexOf("/", intStart);
			h.put(new Integer(intStart), "/");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf(" ", intStart) != -1) {
			intStart = s.indexOf(" ", intStart);
			h.put(new Integer(intStart), " ");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf(":", intStart) != -1) {
			intStart = s.indexOf(":", intStart);
			h.put(new Integer(intStart), ":");
			intStart++;
		}

		if (s.indexOf("年") != -1)
			h.put(new Integer(s.indexOf("年")), "年");
		if (s.indexOf("月") != -1)
			h.put(new Integer(s.indexOf("月")), "月");
		if (s.indexOf("日") != -1)
			h.put(new Integer(s.indexOf("日")), "日");
		if (s.indexOf("时") != -1)
			h.put(new Integer(s.indexOf("时")), "时");
		if (s.indexOf("分") != -1)
			h.put(new Integer(s.indexOf("分")), "分");
		if (s.indexOf("秒") != -1)
			h.put(new Integer(s.indexOf("秒")), "秒");

		int i = 0;
		while (h.size() != 0) {
			Enumeration e = h.keys();
			int n = 0;
			while (e.hasMoreElements()) {
				i = ((Integer) e.nextElement()).intValue();
				if (i >= n)
					n = i;
			}
			String temp = (String) h.get(new Integer(n));
			h.remove(new Integer(n));

			javaFormat = temp + javaFormat;
		}
		SimpleDateFormat df = new SimpleDateFormat(javaFormat,
				new DateFormatSymbols());

		return df.format(d);
	}

	/**
	 * 获取输入格式的日期字符串，字符串遵循Oracle格式
	 *
	 * @param d -
	 *            日期
	 * @param format -
	 *            指定日期格式，格式的写法为Oracle格式
	 * @return 按指定的日期格式转换后的日期字符串
	 */
	public static String getDate(Date d, String format) {
		if (d == null)
			return "";
		Hashtable h = new Hashtable();
		String javaFormat = new String();
		String s = format.toLowerCase();
		if (s.indexOf("yyyy") != -1)
			h.put(new Integer(s.indexOf("yyyy")), "yyyy");
		else if (s.indexOf("yy") != -1)
			h.put(new Integer(s.indexOf("yy")), "yy");
		if (s.indexOf("mm") != -1)
			h.put(new Integer(s.indexOf("mm")), "MM");

		if (s.indexOf("dd") != -1)
			h.put(new Integer(s.indexOf("dd")), "dd");
		if (s.indexOf("hh24") != -1)
			h.put(new Integer(s.indexOf("hh24")), "HH");
		if (s.indexOf("mi") != -1)
			h.put(new Integer(s.indexOf("mi")), "mm");
		if (s.indexOf("ss") != -1)
			h.put(new Integer(s.indexOf("ss")), "ss");

		int intStart = 0;
		while (s.indexOf("-", intStart) != -1) {
			intStart = s.indexOf("-", intStart);
			h.put(new Integer(intStart), "-");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf("/", intStart) != -1) {
			intStart = s.indexOf("/", intStart);
			h.put(new Integer(intStart), "/");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf(" ", intStart) != -1) {
			intStart = s.indexOf(" ", intStart);
			h.put(new Integer(intStart), " ");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf(":", intStart) != -1) {
			intStart = s.indexOf(":", intStart);
			h.put(new Integer(intStart), ":");
			intStart++;
		}

		if (s.indexOf("年") != -1)
			h.put(new Integer(s.indexOf("年")), "年");
		if (s.indexOf("月") != -1)
			h.put(new Integer(s.indexOf("月")), "月");
		if (s.indexOf("日") != -1)
			h.put(new Integer(s.indexOf("日")), "日");
		if (s.indexOf("时") != -1)
			h.put(new Integer(s.indexOf("时")), "时");
		if (s.indexOf("分") != -1)
			h.put(new Integer(s.indexOf("分")), "分");
		if (s.indexOf("秒") != -1)
			h.put(new Integer(s.indexOf("秒")), "秒");

		int i = 0;
		while (h.size() != 0) {
			Enumeration e = h.keys();
			int n = 0;
			while (e.hasMoreElements()) {
				i = ((Integer) e.nextElement()).intValue();
				if (i >= n)
					n = i;
			}
			String temp = (String) h.get(new Integer(n));
			h.remove(new Integer(n));

			javaFormat = temp + javaFormat;
		}
		SimpleDateFormat df = new SimpleDateFormat(javaFormat,
				new DateFormatSymbols());

		return df.format(d);
	}

	/**
	 * 比较两个年月型日期的大小，日期格式为yyyyMM 两个字串6位，前4代表年，后2代表月， <br>
	 * IF 第一个代表的时间 > 第二个代表的时间，返回真，ELSE 返回假 <br>
	 *
	 * @param s1
	 * @param s2
	 * @return 如果s1大于等于s2则返回真，否则返回假
	 */
	public static boolean yearMonthGreatEqual(String s1, String s2) {
		String temp1 = s1.substring(0, 4);
		String temp2 = s2.substring(0, 4);
		String temp3 = s1.substring(4, 6);
		String temp4 = s2.substring(4, 6);

		if (Integer.parseInt(temp1) > Integer.parseInt(temp2))
			return true;
		else if (Integer.parseInt(temp1) == Integer.parseInt(temp2)) {
			if (Integer.parseInt(temp3) >= Integer.parseInt(temp4))
				return true;
			else
				return false;
		} else
			return false;
	}

	/**
	 * 比较两个年月型日期的大小，日期格式为yyyyMM 两个字串6位，前4代表年，后2代表月， <br>
	 * IF 第一个代表的时间 > 第二个代表的时间，返回真，ELSE 返回假 <br>
	 *
	 * @param s1
	 * @param s2
	 * @return 如果s1大于s2则返回真，否则返回假
	 */
	public static boolean yearMonthGreater(String s1, String s2) {
		String temp1 = s1.substring(0, 4);
		String temp2 = s2.substring(0, 4);
		String temp3 = s1.substring(4, 6);
		String temp4 = s2.substring(4, 6);

		if (Integer.parseInt(temp1) > Integer.parseInt(temp2))
			return true;
		else if (Integer.parseInt(temp1) == Integer.parseInt(temp2)) {
			if (Integer.parseInt(temp3) > Integer.parseInt(temp4))
				return true;
			else
				return false;
		} else
			return false;
	}

	/**
	 * 将日期型数据转换成Oracle要求的标准格式的字符串
	 *
	 * @return
	 */
	public static String getOracleFormatDateStr(Date d) {
		return getDate(d, "YYYY-MM-DD HH24:MI:SS");
	}

	/**
	 * 字串6位，前4代表年，后2代表月， <br>
	 * 返回给定日期中的月份中的最后一天 param term(YYYYMMDD)
	 *
	 * @param term -
	 *            年月，格式为yyyyMM
	 * @return String 指定年月中该月份的天数
	 */
	public static String getLastDay(String term) {

		int getYear = Integer.parseInt(term.substring(0, 4));
		int getMonth = Integer.parseInt(term.substring(4, 6));

		String getLastDay = "";

		if (getMonth == 2) {
			if (getYear % 4 == 0 && getYear % 100 != 0 || getYear % 400 == 0) {
				getLastDay = "29";
			} else {
				getLastDay = "28";
			}
		} else if (getMonth == 4 || getMonth == 6 || getMonth == 9
				|| getMonth == 11) {
			getLastDay = "30";
		} else {
			getLastDay = "31";
		}
		return String.valueOf(getYear) + "年" + String.valueOf(getMonth) + "月"
				+ getLastDay + "日";
	}

	//    /**
	//     * 返回指定数量后的日期字符串
	//     * @param strDate String
	//     * @param intDiff int
	//     * @return String
	//     */
	//    public static String getMonthBetween(String strDate, int intDiff) {
	//        try {
	//            int intYear = Integer.parseInt(strDate.substring(0, 4));
	//            int intMonth = Integer.parseInt(strDate.substring(4, 6));
	//            String strDay = "";
	//            if (strDate.length() > 6) {
	//                strDay = strDate.substring(6, strDate.length());
	//            }
	//            intMonth = intMonth + intDiff;
	//            while (intMonth <= 0) {
	//                intYear = intYear - 1;
	//                intMonth = intMonth + 12;
	//            } while (intMonth > 12) {
	//                intYear = intYear + 1;
	//                intMonth = intMonth - 12;
	//            }
	//            if (intMonth < 10)
	//                return Integer.toString(intYear) + "0" + Integer.toString(intMonth) +
	// strDay;
	//            else
	//                return Integer.toString(intYear) + Integer.toString(intMonth) + strDay;
	//        } catch (Exception e) {
	//            return "";
	//        }
	//    }

	/**
	 * 返回两个年月(例如：200206)之间相差的月数，年月格式为yyyyMM
	 *
	 * @param strDateBegin -
	 *            String
	 * @param strDateEnd
	 *            String
	 * @return String strDateEnd-strDateBegin相差的月数
	 */
	public static String getMonthBetween(String strDateBegin, String strDateEnd) {
		try {
			int intMonthBegin;
			int intMonthEnd;
			String strOut;
			if (strDateBegin.equals("") || strDateEnd.equals("")
					|| strDateBegin.length() != 6 || strDateEnd.length() != 6)
				strOut = "";
			else {
				intMonthBegin = Integer.parseInt(strDateBegin.substring(0, 4))
						* 12 + Integer.parseInt(strDateBegin.substring(4, 6));
				intMonthEnd = Integer.parseInt(strDateEnd.substring(0, 4)) * 12
						+ Integer.parseInt(strDateEnd.substring(4, 6));
				strOut = String.valueOf(intMonthBegin - intMonthEnd);
			}
			return strOut;
		} catch (Exception e) {
			return "0";
		}
	}

	/**
	 * 将yyyyMMDD格式的日期转换为yyyy-MM-DD格式的日期 返回带'-'的日期(例如：20020612 转换为 2002-06-12)
	 *
	 * @param strDate
	 *            String yyyyMMDD格式的日期
	 * @return String - yyyy-MM-DD格式的日期
	 */
	public static String getStrHaveAcross(String strDate) {
		try {
			return strDate.substring(0, 4) + "-" + strDate.substring(4, 6)
					+ "-" + strDate.substring(6, 8);
		} catch (Exception e) {
			return strDate;
		}
	}

	/**
	 * 取得当前日期的下一个月的第一天 add by yaojp 2002-10-08
	 *
	 * @return 当前日期的下个月的第一天，格式为yyyyMMDD
	 */
	public static String getFirstDayOfNextMonth() {
		String strToday = getCurrentDate_String();
		return increaseYearMonth(strToday.substring(0, 6)) + "01";
	}

    /**
       * 将yyyyMM各式转换成yyyy年MM月格式
       * @param dateStr
       * @return
       * @throws AppException
       */
      public static String getYearAndMonth(String yearMonth) {
        if(null == yearMonth) return "";
        String ym = yearMonth.trim();
        if(6 != ym.length()) return ym;
        String year = ym.substring(0,4);
        String month = ym.substring(4);
        return new StringBuffer(year).append("年").append(month).append("月").toString();
      }

      /**
  * 将输入的Integer类型的月数转化成"X年X月"格式的字符串
  * @param month Integer
  * @return String
  */
 public static String month2YearMonth(String month) {
   String yearMonth = "";
   int smonth = 0;
   int year = 0;
   int rmonth = 0;

   if ((null == month) || ("0".equals(month)) || "".equals(month.trim())) {
     return "0月";
   }

   smonth = Integer.parseInt(month);
   year = smonth/12;
   rmonth = smonth%12;

   if (year > 0) {
     yearMonth = year + "年";
   }
   if (rmonth > 0) {
     yearMonth += rmonth + "个月";
   }

   return yearMonth;
 }

      /**
    * 将yyyyMM各式转换成yyyy年MM月格式
    * @param dateStr
    * @return
    * @throws AppException
    */
   public static String getYearMonthByMonth(String month) {
     if(null == month) return null;
     String ym = month.trim();
     if(6 != ym.length()) return ym;
     String year = ym.substring(0,4);
     String month1 = ym.substring(4);
     return new StringBuffer(year).append("年").append(month).append("月").toString();
   }

   /**
    * 得到将date增加指定月数后的date
    *
    * @param date
    * @param intBetween
    * @return date加上intBetween月数后的日期
    */
   public static Date increaseMonth(Date date, int intBetween) {
       Calendar calo = Calendar.getInstance();
       calo.setTime(date);
       calo.add(Calendar.MONTH, intBetween);
       return calo.getTime();
   }

   /**
    * 得到将date增加指定月数后的date
    *
    * @param date
    * @param intBetween
    * @return date加上intBetween年数后的日期
    */
   public static Date increaseYear(Date date, int intBetween) {
       Calendar calo = Calendar.getInstance();
       calo.setTime(date);
       calo.add(Calendar.YEAR, intBetween);
       return calo.getTime();
   }
   
   public static Timestamp strToTimestamp(String datestr){
	   Timestamp ts = new Timestamp(System.currentTimeMillis());
		//String datestr = "2011-05-09 11:49:45";
		try {
			ts = Timestamp.valueOf(datestr);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ts;
   }
   
   public static Timestamp dateToTimestamp(Date date){
	   Timestamp ts = new Timestamp(System.currentTimeMillis());
		String datestr = dateToString(date,"yyyy-mm-dd hh24:mi:ss");
		try {
			ts = Timestamp.valueOf(datestr);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ts;
   }
   
   public static String  timestampToString(Timestamp time){
	   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	   return df.format(time);
   }
	public static void main(String[] args) {
		try {
			/*System.out.println("strToTimestamp(2011-05-09 11:49:45):"+strToTimestamp("2011-05-09 11:49:45"));
			System.out.println("我的年龄：" + DateUtil.getAge("210202720508171"));
			System.out.println("我的年龄：" + DateUtil.getAge("210202197205081712"));
			int x = 31;
			java.sql.Date date = DateUtil.getDateByAge(x);
			System.out.println("31岁人的出生日期：" + date.toString());
            System.out.println("========getYearMonth:" + DateUtil.getYearAndMonth("200406") );
            System.out.println("====" + DateUtil.month2YearMonth("15"));
            System.out.println("========increaseMonth: " + DateUtil.increaseMonth(DateUtil.getCurrentDate(),2));*/
			
			//Timestamp now = new Timestamp(System.currentTimeMillis());
			Date today=DateUtil.getCurrentDate();
			
			System.out.println(DateUtil.dateToString(today, "yyyymmdd"));
		} catch (Exception ex) {
		}
	}
}
