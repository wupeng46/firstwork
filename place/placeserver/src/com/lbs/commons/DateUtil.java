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
 * Description: �ṩ�й����ڵ�ʵ�÷�����
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
	 * �������ݿ�ϵͳ��ǰ������
	 *
	 * @return Date - java.sql.Date�����ݿ�������ĵ�ǰ����
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
			logger.error("�쳣");
		}
		return new java.sql.Date(stringToDate(s, "yyyy-mm-dd").getTime());

	}
	/**
	 * �������֤�����ȡ����
	 *
	 * @param id
	 *            String ���֤��
	 * @throws Exception
	 *             ���֤�Ŵ���ʱ����
	 * @return int - ����
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
			throw new Exception("��������֤��");
		}
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		age = currentYear - (new Integer(birthday)).intValue();
		return age;
	}

	/**
	 * ���������ȡ�������
	 *
	 * @param age
	 *            int ����
	 * @return Date - �������
	 */
	public static java.sql.Date getDateByAge(int age) {
		Calendar calendar = Calendar.getInstance(Locale.CHINESE);
		long current = calendar.getTimeInMillis();
		calendar.set(calendar.get(Calendar.YEAR) - age, calendar
				.get(Calendar.MONTH), calendar.get(Calendar.DATE));
		return new java.sql.Date((calendar.getTimeInMillis()));
	}

	/**
	 * �����ڱ���ת��Ϊ"YYYY��MM��DD��"��ʽ
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
		return dtrDate.substring(0, 4) + "��"
				+ Integer.parseInt(dtrDate.substring(4, 6)) + "��"
				+ Integer.parseInt(dtrDate.substring(6, 8)) + "��";
	}

	/**
	 * ��ȡ��ǰ����Ϊ�ַ���
	 *
	 * @return String - ��ǰ�����ַ�������ʽΪYYMMDD
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
	 * ����ָ����ʽ��ȡ��ǰ����Ϊ�ַ���
	 *
	 * @param strFormat
	 *            String - ���ڸ�ʽ����
	 * @return String ��ǰ�����ַ���
	 */
	public static String getCurrentDate_String(String strFormat) {
		Calendar cal = Calendar.getInstance();

		String currentDate = null;
		Date d = cal.getTime();

		return getDate(d, strFormat);
	}

	/**
	 * �Ƚ���������(�����ͣ���ʽΪYYYYMM)֮������·�
	 *
	 * @param dealMonth -
	 *            ��ʼ����
	 * @param alterMonth -
	 *            ��������
	 * @return alterMonth-dealMonth��������
	 */
	public static int calBetweenTwoMonth(String dealMonth, String alterMonth) {
		int length = 0;
		if ((dealMonth.length() != 6) || (alterMonth.length() != 6)) {
			//				System.out.println("�Ƚ������ַ����ĳ��Ȳ���ȷ");
			length = -1;

		} else {
			int dealInt = Integer.parseInt(dealMonth);
			int alterInt = Integer.parseInt(alterMonth);
			if (dealInt < alterInt) {
				//					System.out.println("��һ�����±���Ӧ���ڻ���ڵڶ������±���");
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
	 * �õ������е����
	 *
	 * @param date
	 * @return yyyy��ʽ�����
	 */
	public static int convertDateToYear(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy",
				new DateFormatSymbols());
		return Integer.parseInt(df.format(date));
	}

	/**
	 * �õ�������������ɵ��ַ���
	 *
	 * @param d
	 * @return yyyyMM��ʽ�������ַ���
	 */
	public static String convertDateToYearMonth(Date d) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM",
				new DateFormatSymbols());
		return df.format(d);
	}

	/**
	 * �õ���������������ɵ��ַ���
	 *
	 * @param d
	 * @return yyyyMMdd��ʽ���������ַ���
	 */
	public static String convertDateToYearMonthDay(Date d) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd",
				new DateFormatSymbols());
		return df.format(d);
	}

	/**
	 * �õ���������֮����������
	 *
	 * @param newDate
	 * @param oldDate
	 * @return newDate-oldDate��������
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
	 * ȡ����ԭ�������һ�����������ڣ�����Date������
	 *
	 * @param date
	 * @param intBetween
	 * @return date����intBetween��������
	 */
	public static Date getDateBetween(Date date, int intBetween) {
		Calendar calo = Calendar.getInstance();
		calo.setTime(date);
		calo.add(Calendar.DATE, intBetween);
		return calo.getTime();
	}

	/**
	 * ��ָ����ʽȡ����ԭ�������һ�����������ڣ�����String������
	 *
	 * @param date
	 * @param intBetween
	 * @param strFromat
	 * @return date����intBetween��������
	 */
	public static String getDateBetween_String(Date date, int intBetween,
			String strFromat) {
		Date dateOld = getDateBetween(date, intBetween);
		return getDate(dateOld, strFromat);
	}

	/**
	 * �õ����������ַ�������1�º�������ַ���
	 *
	 * @param yearMonth
	 *            yyyyMM��ʽ
	 * @return yearMonth����һ���º�����ڣ�yyyyMM��ʽ
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
	 * �õ����������ַ�������ָ��������������ַ���
	 *
	 * @param yearMonth -
	 *            yyyyMM��ʽ
	 * @return - yearMonth����addMonth���º�����ڣ�yyyyMM��ʽ
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
	 * �õ����������ַ�����ȥ1�º�������ַ���
	 *
	 * @param yearMonth -
	 *            yyyyMM��ʽ
	 * @return - yearMonth����һ���µ����ڣ�yyyyMM��ʽ
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
	//     * �õ����������ַ�������1�º�������ַ���
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
	//     * �õ���������������������ַ���
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
	//        return dtrDate.substring(0, 4) + "��" +
	//            Integer.parseInt(dtrDate.substring(4, 6)) + "��" +
	//            Integer.parseInt(dtrDate.substring(6, 8)) + "��";
	//    }

	/**
	 * ��ȡ��ǰ����Ϊ������
	 *
	 * @return ��ǰ���ڣ�java.util.Date����
	 */
	public static Date getCurrentDate() {
		Calendar cal = Calendar.getInstance();

		String currentDate = null;
		Date d = cal.getTime();

		return d;
	}
	
	//��ȡ��ǰʱ���һ����ʱ��
	public static Date getAfterYearDate(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.YEAR,1);
		Date date=cal.getTime(); 
		return date;
	}
	/**
	 * ��ȡ��ǰ���µ��ַ���
	 *
	 * @return ��ǰ���£�yyyyMM��ʽ
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
	 * ��ȡ��ǰ��Ϊ����
	 *
	 * @return ��ȡ��ǰ�����е��꣬int��
	 */
	public static int getCurrentYear() {
		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(Calendar.YEAR);
		return currentYear;
	}

	/**
	 * ��ָ����ʽ���ַ���ת��Ϊ������
	 *
	 * @param strDate -
	 *            ����
	 * @param oracleFormat
	 *            --oracle�����ڸ�ʽ
	 * @return ת���õ�������
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

		if (s.indexOf("��") != -1)
			h.put(new Integer(s.indexOf("��")), "��");
		if (s.indexOf("��") != -1)
			h.put(new Integer(s.indexOf("��")), "��");
		if (s.indexOf("��") != -1)
			h.put(new Integer(s.indexOf("��")), "��");
		if (s.indexOf("ʱ") != -1)
			h.put(new Integer(s.indexOf("ʱ")), "ʱ");
		if (s.indexOf("��") != -1)
			h.put(new Integer(s.indexOf("��")), "��");
		if (s.indexOf("��") != -1)
			h.put(new Integer(s.indexOf("��")), "��");

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
	 * ��ȡ�����ʽ�������ַ������ַ�����ѭOracle��ʽ
	 *
	 * @param d -
	 *            ����
	 * @param format -
	 *            ָ�����ڸ�ʽ����ʽ��д��ΪOracle��ʽ
	 * @return ��ָ�������ڸ�ʽת����������ַ���
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

		if (s.indexOf("��") != -1)
			h.put(new Integer(s.indexOf("��")), "��");
		if (s.indexOf("��") != -1)
			h.put(new Integer(s.indexOf("��")), "��");
		if (s.indexOf("��") != -1)
			h.put(new Integer(s.indexOf("��")), "��");
		if (s.indexOf("ʱ") != -1)
			h.put(new Integer(s.indexOf("ʱ")), "ʱ");
		if (s.indexOf("��") != -1)
			h.put(new Integer(s.indexOf("��")), "��");
		if (s.indexOf("��") != -1)
			h.put(new Integer(s.indexOf("��")), "��");

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
	 * ��ȡ�����ʽ�������ַ������ַ�����ѭOracle��ʽ
	 *
	 * @param d -
	 *            ����
	 * @param format -
	 *            ָ�����ڸ�ʽ����ʽ��д��ΪOracle��ʽ
	 * @return ��ָ�������ڸ�ʽת����������ַ���
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

		if (s.indexOf("��") != -1)
			h.put(new Integer(s.indexOf("��")), "��");
		if (s.indexOf("��") != -1)
			h.put(new Integer(s.indexOf("��")), "��");
		if (s.indexOf("��") != -1)
			h.put(new Integer(s.indexOf("��")), "��");
		if (s.indexOf("ʱ") != -1)
			h.put(new Integer(s.indexOf("ʱ")), "ʱ");
		if (s.indexOf("��") != -1)
			h.put(new Integer(s.indexOf("��")), "��");
		if (s.indexOf("��") != -1)
			h.put(new Integer(s.indexOf("��")), "��");

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
	 * �Ƚ��������������ڵĴ�С�����ڸ�ʽΪyyyyMM �����ִ�6λ��ǰ4�����꣬��2�����£� <br>
	 * IF ��һ�������ʱ�� > �ڶ��������ʱ�䣬�����棬ELSE ���ؼ� <br>
	 *
	 * @param s1
	 * @param s2
	 * @return ���s1���ڵ���s2�򷵻��棬���򷵻ؼ�
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
	 * �Ƚ��������������ڵĴ�С�����ڸ�ʽΪyyyyMM �����ִ�6λ��ǰ4�����꣬��2�����£� <br>
	 * IF ��һ�������ʱ�� > �ڶ��������ʱ�䣬�����棬ELSE ���ؼ� <br>
	 *
	 * @param s1
	 * @param s2
	 * @return ���s1����s2�򷵻��棬���򷵻ؼ�
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
	 * ������������ת����OracleҪ��ı�׼��ʽ���ַ���
	 *
	 * @return
	 */
	public static String getOracleFormatDateStr(Date d) {
		return getDate(d, "YYYY-MM-DD HH24:MI:SS");
	}

	/**
	 * �ִ�6λ��ǰ4�����꣬��2�����£� <br>
	 * ���ظ��������е��·��е����һ�� param term(YYYYMMDD)
	 *
	 * @param term -
	 *            ���£���ʽΪyyyyMM
	 * @return String ָ�������и��·ݵ�����
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
		return String.valueOf(getYear) + "��" + String.valueOf(getMonth) + "��"
				+ getLastDay + "��";
	}

	//    /**
	//     * ����ָ��������������ַ���
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
	 * ������������(���磺200206)֮���������������¸�ʽΪyyyyMM
	 *
	 * @param strDateBegin -
	 *            String
	 * @param strDateEnd
	 *            String
	 * @return String strDateEnd-strDateBegin��������
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
	 * ��yyyyMMDD��ʽ������ת��Ϊyyyy-MM-DD��ʽ������ ���ش�'-'������(���磺20020612 ת��Ϊ 2002-06-12)
	 *
	 * @param strDate
	 *            String yyyyMMDD��ʽ������
	 * @return String - yyyy-MM-DD��ʽ������
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
	 * ȡ�õ�ǰ���ڵ���һ���µĵ�һ�� add by yaojp 2002-10-08
	 *
	 * @return ��ǰ���ڵ��¸��µĵ�һ�죬��ʽΪyyyyMMDD
	 */
	public static String getFirstDayOfNextMonth() {
		String strToday = getCurrentDate_String();
		return increaseYearMonth(strToday.substring(0, 6)) + "01";
	}

    /**
       * ��yyyyMM��ʽת����yyyy��MM�¸�ʽ
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
        return new StringBuffer(year).append("��").append(month).append("��").toString();
      }

      /**
  * �������Integer���͵�����ת����"X��X��"��ʽ���ַ���
  * @param month Integer
  * @return String
  */
 public static String month2YearMonth(String month) {
   String yearMonth = "";
   int smonth = 0;
   int year = 0;
   int rmonth = 0;

   if ((null == month) || ("0".equals(month)) || "".equals(month.trim())) {
     return "0��";
   }

   smonth = Integer.parseInt(month);
   year = smonth/12;
   rmonth = smonth%12;

   if (year > 0) {
     yearMonth = year + "��";
   }
   if (rmonth > 0) {
     yearMonth += rmonth + "����";
   }

   return yearMonth;
 }

      /**
    * ��yyyyMM��ʽת����yyyy��MM�¸�ʽ
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
     return new StringBuffer(year).append("��").append(month).append("��").toString();
   }

   /**
    * �õ���date����ָ���������date
    *
    * @param date
    * @param intBetween
    * @return date����intBetween�����������
    */
   public static Date increaseMonth(Date date, int intBetween) {
       Calendar calo = Calendar.getInstance();
       calo.setTime(date);
       calo.add(Calendar.MONTH, intBetween);
       return calo.getTime();
   }

   /**
    * �õ���date����ָ���������date
    *
    * @param date
    * @param intBetween
    * @return date����intBetween�����������
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
			System.out.println("�ҵ����䣺" + DateUtil.getAge("210202720508171"));
			System.out.println("�ҵ����䣺" + DateUtil.getAge("210202197205081712"));
			int x = 31;
			java.sql.Date date = DateUtil.getDateByAge(x);
			System.out.println("31���˵ĳ������ڣ�" + date.toString());
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
