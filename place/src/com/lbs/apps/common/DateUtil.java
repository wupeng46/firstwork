package com.lbs.apps.common;

import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * Title: �Ͷ����г���Ϣϵͳ
 * </p>
 * <p>
 * Description:
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

public class DateUtil {

	/**
	 * �õ���ǰ����(java.sql.Date����)��ע�⣺û��ʱ�䣬ֻ������
	 * 
	 * @return ��ǰ����
	 */
	public static java.sql.Date getDate() {
		Calendar oneCalendar = Calendar.getInstance();
		return getDate(oneCalendar.get(Calendar.YEAR), oneCalendar
				.get(Calendar.MONTH) + 1, oneCalendar.get(Calendar.DATE));
	}
	/*
	 * ��������
	 * ���:�����ַ���,���ڸ�ʽ
	 */
	public static java.sql.Date getDate(String s,String format){
		Date date=com.lbs.commons.DateUtil.stringToDate(s,format);
		return new java.sql.Date(date.getTime());
	}

	/**
	 * ���������ꡢ�¡��գ��õ�����(java.sql.Date����)��ע�⣺û��ʱ�䣬ֻ�����ڡ�
	 * �ꡢ�¡��ղ��Ϸ�������IllegalArgumentException(����Ҫcatch)
	 * 
	 * @param yyyy
	 *            4λ��
	 * @param MM
	 *            ��
	 * @param dd
	 *            ��
	 * @return ����
	 */
	public static java.sql.Date getDate(int yyyy, int MM, int dd) {
		if (!verityDate(yyyy, MM, dd))
			throw new IllegalArgumentException("This is illegimate date!");

		Calendar oneCalendar = Calendar.getInstance();
		oneCalendar.clear();
		oneCalendar.set(yyyy, MM - 1, dd);
		return new java.sql.Date(oneCalendar.getTime().getTime());
	}

	/**
	 * ���������ꡢ�¡��գ������Ƿ�Ϊ�Ϸ����ڡ�
	 * 
	 * @param yyyy
	 *           4λ��
	 * @param MM
	 *            ��
	 * @param dd
	 *            ��
	 * @return
	 */
	public static boolean verityDate(int yyyy, int MM, int dd) {
		boolean flag = false;

		if (MM >= 1 && MM <= 12 && dd >= 1 && dd <= 31) {
			if (MM == 4 || MM == 6 || MM == 9 || MM == 11) {
				if (dd <= 30)
					flag = true;
			} else if (MM == 2) {
				if (yyyy % 100 != 0 && yyyy % 4 == 0 || yyyy % 400 == 0) {
					if (dd <= 29)
						flag = true;
				} else if (dd <= 28)
					flag = true;

			} else
				flag = true;

		}
		return flag;
	}

	/**
	 * ��������ַ����Ƿ���ȷ����ʽ��yyyy-MM-dd
	 * 
	 * @param dateString
	 * @return
	 */
	public static boolean checkDateString(String dateString) {
		boolean check = false;
		try {
			Date oneDay = TypeCast.stringToDate(dateString, "", true);
			Calendar ca = Calendar.getInstance();
			ca.clear();
			ca.setTime(oneDay);
			int yyyy = ca.get(Calendar.YEAR);
			if (yyyy >= 1000 && yyyy <= 9999)
				check = true;
		} catch (ApplicationException e) {
			check = false;
		}
		return check;
	}

	/**
	 * �õ������ַ������꣬������4λ���֡�
	 * 
	 * @param dateStr
	 * @return @throws
	 *         ApplicationException
	 */
	public static String getYear(String dateStr) throws ApplicationException {

		Date oneDay = TypeCast.stringToDate(dateStr, "", true);
		Calendar ca = Calendar.getInstance();
		ca.clear();
		ca.setTime(oneDay);
		int yyyy = ca.get(Calendar.YEAR);
		if (yyyy < 1000 || yyyy > 9999)
			throw new ApplicationException("������꣡");

		return "" + yyyy;

	}

	/**
	 * ������������ʼ,��ֹʱ��������������
	 * 
	 * @param startDate
	 * @param endDate
	 * @return �������
	 */
	public static int getIntervalDay(java.sql.Date startDate,
			java.sql.Date endDate) {
		long startdate = startDate.getTime();
		long enddate = endDate.getTime();
		long interval = enddate - startdate;
		int intervalday = (int) interval / (1000 * 60 * 60 * 24);
		return intervalday;
	}

	/**
	 * ��ȡ����ʱ��(Date��ֱ��ת�����ַ���)��n���µ�YYYYMM
	 * 
	 * @param inputDate
	 * @param n
	 * @return
	 */
	public static String getYearAndMonth(String dateStr, int n)
			throws ApplicationException {
		Date oneDay = TypeCast.stringToDate(dateStr, "", true);
		Calendar ca = Calendar.getInstance();
		ca.clear();
		ca.setTime(oneDay);

		ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) + n);

		int yyyy = ca.get(Calendar.YEAR);
		if (yyyy < 1000 || yyyy > 9999)
			throw new ApplicationException(dateStr + "�д��ڴ�����꣡");
		int MM = ca.get(Calendar.MONTH) + 1;
		String month = "" + MM;

		if (MM < 10)
			month = "0" + MM;
		return "" + yyyy + month;
	}

	/**
	 * ������������ʼ,��ֹʱ��������������
	 * 
	 * @param startDate
	 *            YYYYMM
	 * @param endDate
	 *            YYYYMM
	 * @return �������
	 */
	public static int getIntervalMonth(String startDate, String endDate) {
		int startYear = Integer.parseInt(startDate.substring(0, 4));
		int startMonth = 0;
		if (startDate.substring(4, 5).equals("0"))
			startMonth = Integer.parseInt(startDate.substring(5));
		startMonth = Integer.parseInt(startDate.substring(4, 6));

		int endYear = Integer.parseInt(endDate.substring(0, 4));
		int endMonth = 0;
		if (endDate.substring(4, 5).equals("0"))
			endMonth = Integer.parseInt(endDate.substring(5));
		endMonth = Integer.parseInt(endDate.substring(4, 6));

		int intervalMonth = (endYear * 12 + endMonth)
				- (startYear * 12 + startMonth);
		return intervalMonth;
	}

	/**
	 * �õ������գ�yyyyMMDD��ʽ
	 * 
	 * @param dateStr
	 * @return @throws
	 *         ApplicationException
	 */
	public static String getStrDate(Date date) throws ApplicationException {
		Date oneDay = date;
		Calendar ca = Calendar.getInstance();
		ca.clear();
		ca.setTime(oneDay);
		int yyyy = ca.get(Calendar.YEAR);
		if (yyyy < 1000 || yyyy > 9999)
			throw new ApplicationException("������꣡");
		int MM = ca.get(Calendar.MONTH) + 1;
		String month = "" + MM;
		if (MM < 10)
			month = "0" + MM;

		int DD = ca.get(Calendar.DAY_OF_MONTH);
		String day = "" + DD;
		if (DD < 10)
			day = "0" + DD;

		return "" + yyyy + month + day;
	}

	/**
	 * ��������
	 * 
	 * @param birthday
	 *            String
	 * @param endDate
	 *            String
	 * @return int
	 */
	public static int getAge(Date birthday, Date endDate)
			throws ApplicationException {
		String start = getStrDate(birthday);
		String end = getStrDate(endDate);

		int month = getIntervalMonth(start, end);
		int age = month / 12;

		if ((month % 12) == 0) {
			String sday = start.substring(6);
			String eday = end.substring(6);
			if (sday.compareTo(eday) > 0) {
				age = age - 1;
			}
		}

		return age;
	}

	/**
	 * ԭ�ں�i����֮ǰ����֮����ں�ֵ.200310��5��Ϊ200403
	 * 
	 * @param str
	 *            String
	 * @param how
	 *            int
	 * @return String
	 */
	public static String getAddIssue(String str, int how) {
		String issue = str; //ԭ�ںŸ�ʽΪ��200302
		int i = how; //i����֮��

		int n_year = Integer.parseInt(issue) / 100;
		int n_month = Integer.parseInt(issue) % 100;
		int aY = i / 12;
		int aM = i % 12;
		n_year = n_year + aY;
		n_month = n_month + aM;
		if (n_month > 12) {
			n_year = n_year + 1;
			n_month = n_month - 12;
		}
		if (n_month < 0) {
			n_year = n_year - 1;
			n_month = 12 + n_month;
		}
		if (n_month < 10) {
			issue = ((Integer.toString(n_year).trim()) + '0' + ((Integer
					.toString(n_month).trim())));
		} else {
			issue = ((Integer.toString(n_year).trim()) + ((Integer
					.toString(n_month).trim())));
		}

		return issue;
	}

	/**
	 * ���������ַ�������֮������� (��200310��200403֮��Ϊ5����)
	 * 
	 * @param startDate
	 *            String
	 * @param endDate
	 *            String
	 * @throws ApplicationException
	 * @return int
	 */
	public static int amoungMonths(String startDate, String endDate)
			throws ApplicationException {

		int aMonths = 0;
		int SDate = TypeCast.stringToInt(startDate, "", false);
		int EDate = TypeCast.stringToInt(endDate, "", false);
		int SYear = SDate / 100;
		int SMonths = SDate % 100;
		int EYear = EDate / 100;
		int EMonths = EDate % 100;
		aMonths = 12 * (EYear - SYear) + (EMonths - SMonths + 1);
		return aMonths;
	}

	/**
	 * �õ����£�yyyyMM��ʽ
	 * 
	 * @param dateStr
	 * @return @throws
	 *         ApplicationException
	 */
	public static String getYearAndMonth(String dateStr)
			throws ApplicationException {
		Date oneDay = TypeCast.stringToDate(dateStr, "", true);
		Calendar ca = Calendar.getInstance();
		ca.clear();
		ca.setTime(oneDay);
		int yyyy = ca.get(Calendar.YEAR);
		if (yyyy < 1000 || yyyy > 9999)
			throw new ApplicationException("������꣡");
		int MM = ca.get(Calendar.MONTH) + 1;
		String month = "" + MM;
		if (MM < 10)
			month = "0" + MM;
		return "" + yyyy + month;
	}

	/**
	 * ������������ʼʱ��,���������������ֹʱ��
	 * 
	 * @param startDate
	 * @param day
	 * @return ��ֹʱ��
	 */
	public static java.sql.Date getStepDay(java.sql.Date date, int step) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, step);
		return new java.sql.Date(calendar.getTime().getTime());
	}

	/**
	 * �õ���date����ָ���������date
	 * 
	 * @param date
	 * @param intBetween
	 * @return date����intBetween�����������
	 */
	public static java.sql.Date getStepMonth(Date date, int intBetween) {
		Calendar calo = Calendar.getInstance();
		calo.setTime(date);
		calo.add(Calendar.MONTH, intBetween);
		return new java.sql.Date(calo.getTime().getTime());
	}

	/**
	 * �õ���date����ָ���������date
	 * 
	 * @param date
	 * @param intBetween
	 * @return date����intBetween�����������
	 */
	public static java.sql.Date getStepYear(Date date, int intBetween) {
		Calendar calo = Calendar.getInstance();
		calo.setTime(date);
		calo.add(Calendar.YEAR, intBetween);
		return new java.sql.Date(calo.getTime().getTime());
	}
	// �������֤�����ȡ��������(��ȷ�����֤���س������ڣ�����ķ��ص�ǰ���ݿ������)
	public static String getBirtday(String id) throws Exception {
		String birthday = "";
		int idLength = id.length();
		String yy = "";
		int YY = 0;
		String mm = "";
		int MM = 0;
		String dd = "";
		int DD = 0;
		boolean leapYear = false;
		String today = (com.lbs.commons.DateUtil.dateToString(new Date(),
				"yyyy-mm-dd"));

		if (idLength == 15) {
			yy = "19" + id.substring(6, 8);
			mm = id.substring(8, 10);
			dd = id.substring(10, 12);
		} else if (idLength == 18) {
			yy = id.substring(6, 10);
			mm = id.substring(10, 12);
			dd = id.substring(12, 14);
		} else {
			return (com.lbs.commons.DateUtil.dateToString(new Date(),
					"yyyy-mm-dd"));
		}
		YY = (new Integer(yy)).intValue();
		MM = (new Integer(mm)).intValue();
		DD = (new Integer(dd)).intValue();
		if (YY < 1900 || YY > 2200) {
			return (today);
		}

		if (((YY % 4) != 0) && ((YY % 100) != 0)) { //�ж��Ƿ�Ϊ����
			leapYear = false;
		} else {
			leapYear = true;
		}
		if (MM == 2) {
			if (leapYear) {
				if (DD < 1 || DD > 29) {
					return (today);
				}
			} else {
				if (DD < 1 || DD > 28) {
					return (today);
				}
			}
		}
		if ((MM == 1) || (MM == 3) || (MM == 5) || (MM == 7) || (MM == 8)
				|| (MM == 10) || (MM == 12)) {
			if (DD < 1 || DD > 31) {
				return (today);
			}
		}
		if ((MM == 4) || (MM == 6) || (MM == 9) || (MM == 11)) {
			if (DD < 1 || DD > 30) {
				return (today);
			}
		}

		birthday = yy + "-" + mm + "-" + dd;
		return birthday;
	}
	// �������֤�����ȡ�Ա�(����ֵ��1���У�2��Ů����Ϊ���֤�������)
	public static String getGender(String iDCard) {
		int gender = 3;
		System.out.print(iDCard);
		if (iDCard.length() == 15) {
			gender = (new Integer(iDCard.substring(14, 15))).intValue() % 2;
		} else if (iDCard.length() == 18) {
			int number17 = (new Integer(iDCard.substring(16, 17))).intValue();
			gender = number17 % 2;
		}
		if (gender == 1) {
			return "1";
		} else if (gender == 0) {
			return "2";
		} else {
			return "";
		}
	}
	
	//	*****************************************************************************
	//	��ģ����ɼ���������¼��ϸ�����������һ����
	//	���� 1��yearMonth(��������)��months��������
	//	���� ��һ���µ�ʱ��
	//	*****************************************************************************

	public String getNextYearMonth(String yearMonth,int months){
	    yearMonth = yearMonth.trim();
	    if(yearMonth.equals("") || yearMonth == null || yearMonth.equals("null")){
	      return "";
	    }
	    int iYear = Integer.parseInt(yearMonth.substring(0,4));
	    int iMonth = Integer.parseInt(yearMonth.substring(4));

	    iYear = iYear+(months/12);
	    iMonth = iMonth+(months%12);

	    if(iMonth>12){
	      iYear = iYear+1;
	      iMonth = iMonth-12;
	    }

	    String getYearMonth = "";
	    String sYear = iYear + "";
	    String sMonth = "";

	    if (iMonth < 10) {
	      sMonth = "0" + iMonth;
	    }
	    else {
	      sMonth = "" + iMonth;
	    }
	    getYearMonth = sYear + sMonth;

	    return getYearMonth;
	  }
	
	public static void main(String[] args) {
		try {
			System.out.println(DateUtil.getAddIssue("200502", -11));

			System.out.println(DateUtil.getStepMonth(DateUtil.getDate(), 11));
			System.out.println(DateUtil.getStepYear(DateUtil.getDate(), 2)
					.toString());
			//      System.out.println("aaaaaaaaaaaaaaaaaa");
			//      System.out.println(System.currentTimeMillis());
			//      System.out.println(DateUtil.getStepDay( (new
			// java.sql.Date(System.
			//          currentTimeMillis())), 25).toString());
			//System.out.println(DateUtil.getYear("2003-01-21"));
			//      System.out.println(DateUtil.getIntervalDay(DateUtil.getDate(),
			//                                                 TypeCast.
			//                                                 stringToDate("2004-04-4", "", true)));
			//      System.out.println(DateUtil.getYearAndMonth("2003-01-01"));
			//      System.out.println(DateUtil.getYearAndMonth( (new
			// java.sql.Date(System.
			//          currentTimeMillis())).toString(), -1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//    java.text.SimpleDateFormat format = new
		// java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//
		//    System.out.println(format.format(getDate()));
		//    System.out.println(format.format(getDate(2000,2,29)));
		//Date date = format.parse("1999/01/20");
		//Date date = java.text.DateFormat.getDateInstance()
		//System.out.println(format.format(date));
	}
}