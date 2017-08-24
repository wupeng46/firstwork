package com.lbs.apps.common;

import java.util.StringTokenizer;

/**
 * <p>
 * Title: 劳动力市场信息系统
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
public class CommonVerify {

	/**
	 * @构造器
	 */
	public CommonVerify() {
	}

	/**
	 * 判断输入的字符串是否与指定的长度相等
	 * 
	 * @param name
	 * @param length
	 * @return boolean
	 * @roseuid 3E486AD4014E
	 */
	public static boolean stringLenthVerify(String name, int length) {
		if (name.length() == length) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断输入的字符串首或尾是否含有空格,如有空格，则去掉
	 * 
	 * @param name
	 * @return java.lang.String
	 * @roseuid 3E486BFE034B
	 */
	public static String stringTrimVerify(String name) {
		return name.trim();
	}

	/**
	 * 依次取出字符串的每一位,判断取出的字符的ascii码是否在'0'和'9'对应ascii之间
	 * 
	 * @param name
	 * @return boolean
	 * @roseuid 3E486D4E009B
	 */
	public static boolean numberVerify(String name) {
		boolean isNumberChar = true;
		int i = 0;
		/**
		 * 饶福华　修改于20050105　新增
		 */
/*		String x= "";
		String numberString	=	"0123456789";
		while (!isNumberChar || i < name.length()) {
			x = name.substring(i,i+1);
			if (numberString.indexOf(x)<0){
				isNumberChar = false;
				return false;
			}
			i++;
		}
*/		/**
		 * 注释
		 */
		char x;
		while (!isNumberChar || i < name.length()) {
			x = name.charAt(i);
			if (x < '0' || x > '9'){
				isNumberChar = false;
				return false;
			}
			i++;
		}
		// END
		return isNumberChar;
	}

	/**
	 * 依次取出字符串的每一位,判断取出的字符的ascii码是否在'a'和'Z'对应ascii之间
	 * 
	 * @param name
	 * @return boolean
	 * @roseuid 3E486D590051
	 */
	public static boolean charVerify(String name) {
		char achar[];
		achar = name.toCharArray();
		for (int i = 0; i < achar.length; i++) {
			if (achar[i] >= 'A' && achar[i] <= 'z') {
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断指定的字符串是否为空，返回值1，不空；0，为空
	 * 
	 * @param name
	 * @return int
	 * @roseuid 3E486D810199
	 */
	public static int nullVerify(String name) {
		if (name == null) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * 字符串非空判断
	 * 
	 * @param str
	 *            String
	 * @return boolean
	 */
	public static boolean checkStr(String str) {
		if (str == null) {
			return false;
		} else {
			if (str.trim().equals("")) {
				return false;
			} else
				return true;
		}
	}

	/**
	 * 将字符串转化为字符串数组
	 * 
	 * @param s_value
	 *            字符串
	 * @param delim
	 *            分隔符
	 * @return s_list 字符串数组
	 */
	private static String[] getAsStringArray(String s_value, String delim) {
		String[] s_list;
		int i = 0;

		if (s_value != null && delim != null) {
			StringTokenizer st = new StringTokenizer(s_value, delim);
			s_list = new String[st.countTokens()];
			while (st.hasMoreTokens()) {
				s_list[i] = st.nextToken();

				i++;
			}
		} else
			s_list = new String[0];
		return s_list;
	}
	
	/**
	* 身份证号码升位
	* @param _15idnum 15位身份证号码
	* @return 18位身份证号码
	* @author 
	*/
	public static String IDCard15to18(String _15idnum) {
	    
        String _18idnum = "";//升位后18位身份证号码
        _15idnum = _15idnum.trim();//15位号码除空格
        int sum = 0;
        int j = 0;
        int[] w = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };//加权因子
        String[] a = { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2" };//验证码
        if (_15idnum.length() != 15) {
            return _15idnum;//如果身份证号码不是15位，返回原来值。
        } else {
            //	补充年份80－－>1980
            _18idnum = _15idnum.substring(0, 6) + "19" + _15idnum.substring(6);
        }
        //	求和
        for (int i = 0; i < 17; i++) {
            j = Integer.parseInt(_18idnum.substring(i,i+1))*w[i];
            sum = sum + j;
        }
        //	取余数
        sum = sum % 11;
        //	添加验证码
        _18idnum = _18idnum + a[sum];
        //	返回18位身份证号码
        return _18idnum;
    }
	/**
	 * 公民身份号码检查 1.必须是15位或18位数字或字符 2.15位格式:AAAAAAYYMMDDSSS
	 * 3.18位格式:AAAAAAYYYYMMDDSSSC 4.其中: AAAAAA是地区编码
	 * YYMMDD是出生日期(15位格式,年份用两位数字表示,YY:年份, MM:月份,DD:日期)
	 * YYYYMMDD是出生日期(18位格式,年份用4位数字表示,YYYY:年份, MM:月份,DD:日期) SSS是顺序号(奇数是男性, 偶数是女性)
	 * C是校验码(18位格式
	 * 
	 * @param burgherID
	 * @return boolean
	 * @roseuid 3E4884B30197
	 */
	public static boolean burgherIDVerify(String burgherID) {
		int length = burgherID.length();
		String areaCode = null;
		String numberCode = "";
		String date = null;
		String squc = null;
		if (length == 15) {
			areaCode = burgherID.substring(0, 6);
			date = "19" + burgherID.substring(6, 12);
			squc = burgherID.substring(12, 15);
			numberCode = burgherID;

		} else if (length == 18) {
			areaCode = burgherID.substring(0, 6);
			date = burgherID.substring(6, 14);
			squc = burgherID.substring(14, 17);
			numberCode = burgherID.substring(0, 17);
		} else
			return false;
		if (!CommonVerify.numberVerify(numberCode)) {
			return false;
		}
		if (!dateVerify(date)) {
			return false;
		}

		return true;
	}

	/**
	 * 8位数字(次序按yyyy-mm-dd),年月日取值范围规则: 大月31天,小月30天,闰年2月29天,平年28天. 闰年计算规则:yyyy %
	 * 100 != 0 && yyyy % 4==0 || yyyy % 400==0
	 * 
	 * @param date
	 * @return boolean
	 * @roseuid 3E4885A602F5
	 */
	public static boolean dateVerify(String date) {

		int yyyy, MM, dd;
		if (!CommonVerify.numberVerify(date) || date.length() != 8) {
			return false;
		}
		yyyy = Integer.parseInt(date.substring(0, 4));
		MM = Integer.parseInt(date.substring(4, 6));
		dd = Integer.parseInt(date.substring(6, 8));
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
	 * 6位，格式（yyyymm）,都是数字, yyyy在1980－2050之间,mm在1－12之间
	 * 
	 * @param costDate
	 * @return boolean
	 * @roseuid 3E48867101D5
	 */
	public static boolean costDateVerify(String costDate) {
		int yyyy, MM, dd;
		boolean flag = false;

		if (!CommonVerify.numberVerify(costDate) || costDate.length() != 6) {
			return false;
		}
		yyyy = Integer.parseInt(costDate.substring(0, 4));
		MM = Integer.parseInt(costDate.substring(4, 6));
		if (yyyy >= 1980 && yyyy <= 2050 && MM >= 1 && MM <= 12)
			flag = true;

		return flag;
	}

	public static void main(String[] args) {
		String msg = "abc|vvv|123456";

	}
}