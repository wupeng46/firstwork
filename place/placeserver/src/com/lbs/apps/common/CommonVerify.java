package com.lbs.apps.common;

import java.util.StringTokenizer;

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
public class CommonVerify {

	/**
	 * @������
	 */
	public CommonVerify() {
	}

	/**
	 * �ж�������ַ����Ƿ���ָ���ĳ������
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
	 * �ж�������ַ����׻�β�Ƿ��пո�,���пո���ȥ��
	 * 
	 * @param name
	 * @return java.lang.String
	 * @roseuid 3E486BFE034B
	 */
	public static String stringTrimVerify(String name) {
		return name.trim();
	}

	/**
	 * ����ȡ���ַ�����ÿһλ,�ж�ȡ�����ַ���ascii���Ƿ���'0'��'9'��Ӧascii֮��
	 * 
	 * @param name
	 * @return boolean
	 * @roseuid 3E486D4E009B
	 */
	public static boolean numberVerify(String name) {
		boolean isNumberChar = true;
		int i = 0;
		/**
		 * �ĸ������޸���20050105������
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
		 * ע��
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
	 * ����ȡ���ַ�����ÿһλ,�ж�ȡ�����ַ���ascii���Ƿ���'a'��'Z'��Ӧascii֮��
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
	 * �ж�ָ�����ַ����Ƿ�Ϊ�գ�����ֵ1�����գ�0��Ϊ��
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
	 * �ַ����ǿ��ж�
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
	 * ���ַ���ת��Ϊ�ַ�������
	 * 
	 * @param s_value
	 *            �ַ���
	 * @param delim
	 *            �ָ���
	 * @return s_list �ַ�������
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
	* ���֤������λ
	* @param _15idnum 15λ���֤����
	* @return 18λ���֤����
	* @author 
	*/
	public static String IDCard15to18(String _15idnum) {
	    
        String _18idnum = "";//��λ��18λ���֤����
        _15idnum = _15idnum.trim();//15λ������ո�
        int sum = 0;
        int j = 0;
        int[] w = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };//��Ȩ����
        String[] a = { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2" };//��֤��
        if (_15idnum.length() != 15) {
            return _15idnum;//������֤���벻��15λ������ԭ��ֵ��
        } else {
            //	�������80����>1980
            _18idnum = _15idnum.substring(0, 6) + "19" + _15idnum.substring(6);
        }
        //	���
        for (int i = 0; i < 17; i++) {
            j = Integer.parseInt(_18idnum.substring(i,i+1))*w[i];
            sum = sum + j;
        }
        //	ȡ����
        sum = sum % 11;
        //	�����֤��
        _18idnum = _18idnum + a[sum];
        //	����18λ���֤����
        return _18idnum;
    }
	/**
	 * ������ݺ����� 1.������15λ��18λ���ֻ��ַ� 2.15λ��ʽ:AAAAAAYYMMDDSSS
	 * 3.18λ��ʽ:AAAAAAYYYYMMDDSSSC 4.����: AAAAAA�ǵ�������
	 * YYMMDD�ǳ�������(15λ��ʽ,�������λ���ֱ�ʾ,YY:���, MM:�·�,DD:����)
	 * YYYYMMDD�ǳ�������(18λ��ʽ,�����4λ���ֱ�ʾ,YYYY:���, MM:�·�,DD:����) SSS��˳���(����������, ż����Ů��)
	 * C��У����(18λ��ʽ
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
	 * 8λ����(����yyyy-mm-dd),������ȡֵ��Χ����: ����31��,С��30��,����2��29��,ƽ��28��. ����������:yyyy %
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
	 * 6λ����ʽ��yyyymm��,��������, yyyy��1980��2050֮��,mm��1��12֮��
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