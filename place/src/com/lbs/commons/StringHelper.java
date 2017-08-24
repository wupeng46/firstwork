package com.lbs.commons;

import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * Title:leaf
 * </p>
 * <p>
 * Description:�ṩ�ַ��������ʵ�÷�����
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: LBS
 * </p>
 *
 * @author <a href="mailto:chenkl@bjlbs.com.cn">chenkl </a>
 * @version 1.0
 */

public class StringHelper {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(StringHelper.class);
	public static final char DOT='.';                                    // �� 

	static Pattern collectionPattern = null;


	//��̬���췽��
	static {
			collectionPattern = Pattern.compile("[a-z]{3}[0-9]{2}[0-9a-z]");
	}

	public StringHelper() {
	}

	/**
	 * ��nullת��Ϊ���ַ���""���������obj��Ϊnull�������������objΪnull���򷵻��ַ���""
	 *
	 * @param obj -
	 *            ��ת���Ķ���
	 * @return String - ת����Ľ��
	 */
	public static String showNull2Empty(Object obj) {
		if (null == obj) {
			return "";
		} else {
			return obj + "";
		}
	}

	/**
	 * ���ַ���str�е�oleSub�滻��newSub
	 *
	 * @param inputstr
	 *            String - ���滻���ַ���
	 * @param oldsub
	 *            String - Ҫ�滻���ӷ�
	 * @param newsub
	 *            String -
	 * @return String - �滻����ַ���
	 */
	public static String replace(String str, String oldSub, String newSub) {
		if (str == null) {
			return null;
		}
		if (oldSub == null || oldSub.equals("")) {
			return str;
		}
		if (newSub == null) {
			return str;
		}

		int oldlen = oldSub.length();
		while (str.indexOf(oldSub) != -1) {
			int position = str.indexOf(oldSub);
			str = str.substring(0, position) + newSub
					+ str.substring(position + oldlen);

		}
		return str;
	}

	//��Ź���һ�����ֲ�ͬ��������ʼ��λ��
	static final int[] li_SecPosValue = {1601, 1637, 1833, 2078, 2274, 2302,
			2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027,
			4086, 4390, 4558, 4684, 4925, 5249};

	//��Ź���һ�����ֲ�ͬ��������ʼ��λ���Ӧ����
	static final String[] lc_FirstLetter = {"A", "B", "C", "D", "E", "F", "G",
			"H", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "W",
			"X", "Y", "Z"};

	//������й���������ֶ���
	static final String ls_SecondSecTable = new StringBuffer(4096)
			.append(
					"CJWGNSPGCGNE[Y[BTYYZDXYKYGT[JNNJQMBSGZSCYJSYY[PGKBZGY[YWJKGKLJYWKPJQHY[W[DZLSGMRYPYWWCCKZNKYYGTT")
			.append(
					"NJJNYKKZYTCJNMCYLQLYPYQFQRPZSLWBTGKJFYXJWZLTBNCXJJJJTXDTTSQZYCDXXHGCK[PHFFSS[YBGXLPPBYLL[HLXS[ZM")
			.append(
					"[JHSOJNGHDZQYKLGJHSGQZHXQGKEZZWYSCSCJXYEYXADZPMDSSMZJZQJYZC[J[WQJBYZPXGZNZCPWHKXHQKMWFBPBYDTJZZK")
			.append(
					"QHYLYGXFPTYJYYZPSZLFCHMQSHGMXXSXJ[[DCSBBQBEFSJYHXWGZKPYLQBGLDLCCTNMAYDDKSSNGYCSGXLYZAYBNPTSDKDYLH")
			.append(
					"GYMYLCXPY[JNDQJWXQXFYYFJLEJPZRXCCQWQQSBNKYMGPLBMJRQCFLNYMYQMSQYRBCJTHZTQFRXQHXMJJCJLXQGJMSHZKBSWYE")
			.append(
					"MYLTXFSYDSWLYCJQXSJNQBSCTYHBFTDCYZDJWYGHQFRXWCKQKXEBPTLPXJZSRMEBWHJLBJSLYYSMDXLCLQKXLHXJRZJMFQHXHW")
			.append(
					"YWSBHTRXXGLHQHFNM[YKLDYXZPYLGG[MTCFPAJJZYLJTYANJGBJPLQGDZYQYAXBKYSECJSZNSLYZHSXLZCGHPXZHZNYTDSBCJK")
			.append(
					"DLZAYFMYDLEBBGQYZKXGLDNDNYSKJSHDLYXBCGHXYPKDJMMZNGMMCLGWZSZXZJFZNMLZZTHCSYDBDLLSCDDNLKJYKJSYCJLKWH")
			.append(
					"QASDKNHCSGANHDAASHTCPLCPQYBSDMPJLPZJOQLCDHJJYSPRCHN[NNLHLYYQYHWZPTCZGWWMZFFJQQQQYXACLBHKDJXDGMMYDJ")
			.append(
					"XZLLSYGXGKJRYWZWYCLZMSSJZLDBYD[FCXYHLXCHYZJQ[[QAGMNYXPFRKSSBJLYXYSYGLNSCMHZWWMNZJJLXXHCHSY[[TTXRYC")
			.append(
					"YXBYHCSMXJSZNPWGPXXTAYBGAJCXLY[DCCWZOCWKCCSBNHCPDYZNFCYYTYCKXKYBSQKKYTQQXFCWCHCYKELZQBSQYJQCCLMTHS")
			.append(
					"YWHMKTLKJLYCXWHEQQHTQH[PQ[QSCFYMNDMGBWHWLGSLLYSDLMLXPTHMJHWLJZYHZJXHTXJLHXRSWLWZJCBXMHZQXSDZPMGFCS")
			.append(
					"GLSXYMJSHXPJXWMYQKSMYPLRTHBXFTPMHYXLCHLHLZYLXGSSSSTCLSLDCLRPBHZHXYYFHB[GDMYCNQQWLQHJJ[YWJZYEJJDHPB")
			.append(
					"LQXTQKWHLCHQXAGTLXLJXMSL[HTZKZJECXJCJNMFBY[SFYWYBJZGNYSDZSQYRSLJPCLPWXSDWEJBJCBCNAYTWGMPAPCLYQPCLZ")
			.append(
					"XSBNMSGGFNZJJBZSFZYNDXHPLQKZCZWALSBCCJX[YZGWKYPSGXFZFCDKHJGXDLQFSGDSLQWZKXTMHSBGZMJZRGLYJBPMLMSXLZ")
			.append(
					"JQQHZYJCZYDJWBMYKLDDPMJEGXYHYLXHLQYQHKYCWCJMYYXNATJHYCCXZPCQLBZWWYTWBQCMLPMYRJCCCXFPZNZZLJPLXXYZTZ")
			.append(
					"LGDLDCKLYRZZGQTGJHHGJLJAXFGFJZSLCFDQZLCLGJDJCSNZLLJPJQDCCLCJXMYZFTSXGCGSBRZXJQQCTZHGYQTJQQLZXJYLYL")
			.append(
					"BCYAMCSTYLPDJBYREGKLZYZHLYSZQLZNWCZCLLWJQJJJKDGJZOLBBZPPGLGHTGZXYGHZMYCNQSYCYHBHGXKAMTXYXNBSKYZZGJ")
			.append(
					"ZLQJDFCJXDYGJQJJPMGWGJJJPKQSBGBMMCJSSCLPQPDXCDYYKY[CJDDYYGYWRHJRTGZNYQLDKLJSZZGZQZJGDYKSHPZMTLCPWN")
			.append(
					"JAFYZDJCNMWESCYGLBTZCGMSSLLYXQSXSBSJSBBSGGHFJLYPMZJNLYYWDQSHZXTYYWHMZYHYWDBXBTLMSYYYFSXJC[DXXLHJHF")
			.append(
					"[SXZQHFZMZCZTQCXZXRTTDJHNNYZQQMNQDMMG[YDXMJGDHCDYZBFFALLZTDLTFXMXQZDNGWQDBDCZJDXBZGSQQDDJCMBKZFFXM")
			.append(
					"KDMDSYYSZCMLJDSYNSBRSKMKMPCKLGDBQTFZSWTFGGLYPLLJZHGJ[GYPZLTCSMCNBTJBQFKTHBYZGKPBBYMTDSSXTBNPDKLEYC")
			.append(
					"JNYDDYKZDDHQHSDZSCTARLLTKZLGECLLKJLQJAQNBDKKGHPJTZQKSECSHALQFMMGJNLYJBBTMLYZXDCJPLDLPCQDHZYCBZSCZB")
			.append(
					"ZMSLJFLKRZJSNFRGJHXPDHYJYBZGDLQCSEZGXLBLGYXTWMABCHECMWYJYZLLJJYHLG[DJLSLYGKDZPZXJYYZLWCXSZFGWYYDLY")
			.append(
					"HCLJSCMBJHBLYZLYCBLYDPDQYSXQZBYTDKYXJY[CNRJMPDJGKLCLJBCTBJDDBBLBLCZQRPPXJCJLZCSHLTOLJNMDDDLNGKAQHQ")
			.append(
					"HJGYKHEZNMSHRP[QQJCHGMFPRXHJGDYCHGHLYRZQLCYQJNZSQTKQJYMSZSWLCFQQQXYFGGYPTQWLMCRNFKKFSYYLQBMQAMMMYX")
			.append(
					"CTPSHCPTXXZZSMPHPSHMCLMLDQFYQXSZYYDYJZZHQPDSZGLSTJBCKBXYQZJSGPSXQZQZRQTBDKYXZKHHGFLBCSMDLDGDZDBLZY")
			.append(
					"YCXNNCSYBZBFGLZZXSWMSCCMQNJQSBDQSJTXXMBLTXZCLZSHZCXRQJGJYLXZFJPHYMZQQYDFQJJLZZNZJCDGZYGCTXMZYSCTLK")
			.append(
					"PHTXHTLBJXJLXSCDQXCBBTJFQZFSLTJBTKQBXXJJLJCHCZDBZJDCZJDCPRNPQCJPFCZLCLZXZDMXMPHJSGZGSZZQLYLWTJPFSY")
			.append(
					"ASMCJBTZKYCWMYTCSJJLJCQLWZMALBXYFBPNLSFHTGJWEJJXXGLLJSTGSHJQLZFKCGNNNSZFDEQFHBSAQTGYLBXMMYGSZLDYDQ")
			.append(
					"MJJRGBJTKGDHGKBLQKBDMBYLXWCXYTTYBKMRTJZXQJBHLMHMJJZMQASLDCYXYQDLQCAFYWYXQHZ")
			.toString();

	/**
	 * ����ƴ����
	 * ���ظ������ִ�������ĸ��,����ĸ�� �˷������ں��ֵĹ��꺺�ֿ���λ�������Ч�ԣ������ϴ˱����ϵͳ�˺�����Ч
	 * �����ִ����зǺ����ַ�,��ͼ�η��Ż�ASCII��,����Щ�Ǻ����ַ������ֲ���.
	 *
	 * @param strinput
	 * @return ��ĸ��
	 * @throws Exception
	 */
	public static String MnemonicWords(String strinput) throws Exception {

		//���ش�
		String ls_ReturnStr = "";
		try {
			for (int i = 0; i < strinput.length(); i++) { //���δ���as_InputString��ÿ���ַ�
				String s = strinput.substring(i, i + 1);
				//Unix���⣿
				byte b[] = s.getBytes("gbk");
				//System.out.println(b);
				if (b.length == 1) { //������Ǻ���,��ֱ�ӷ���
					ls_ReturnStr = ls_ReturnStr
							+ strinput.substring(i, i + 1).toUpperCase();
				} else {
					int li_SectorCode = 256 + (int) b[0] - 160; //����
					int li_PositionCode = 256 + (int) b[1] - 160; //λ��
					int li_SecPosCode = li_SectorCode * 100 + li_PositionCode; //��λ��
					if (li_SecPosCode > 1600 && li_SecPosCode < 5590) {
						for (int j = 22; j >= 0; j--) { // ����ĸ
							if (li_SecPosCode >= li_SecPosValue[j]) {
								ls_ReturnStr = ls_ReturnStr + lc_FirstLetter[j];
								break;
							}
						}
					} else { //��Ҫ����ƫ����
						int li_offset = (li_SectorCode - 56) * 94
								+ li_PositionCode - 1;
						if (li_offset >= 0 && li_offset <= 3007) { //��������
							ls_ReturnStr = ls_ReturnStr
									+ ls_SecondSecTable.substring(li_offset,
											li_offset + 1); //ȡ��������ĸ
						} else {
							ls_ReturnStr = ls_ReturnStr + strinput.charAt(i); //����������ַ�����
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("ȡ����ƴ�������",e);
			e.printStackTrace();
		}
		return (ls_ReturnStr.toLowerCase());
	}

	/**
	 * ������ת����AscII���Ա�������ݿ�
	 *
	 * @param s
	 *            �����ַ���
	 * @return 16�����ַ���
	 */
	public static String ChineseStringToAscii(String s) {
//		try {
//			CharToByteConverter toByte = CharToByteConverter
//					.getConverter("GBK");
//			byte[] orig = toByte.convertAll(s.toCharArray());
//			char[] dest = new char[orig.length];
//			for (int i = 0; i < orig.length; i++)
//				dest[i] = (char) (orig[i] & 0xFF);
//			return new String(dest);
//		} catch (Exception e) {
//			System.out.println(e);
//			return s;
//		}
		try {
			return new String(s.getBytes("gbk"),"ISO8859_1");
		}
		catch (Exception e) {
			System.out.println(e);
			return s;
		}
	}
    /**
     * ������ת����AscII���Ա�������ݿ�
     *
     * @param s
     *            �����ַ���
     * @return 16�����ַ���
     */
    public static String ChineseStringToUTF(String s) {
//      try {
//          CharToByteConverter toByte = CharToByteConverter
//                  .getConverter("UTF-8");
//          byte[] orig = toByte.convertAll(s.toCharArray());
//          char[] dest = new char[orig.length];
//          for (int i = 0; i < orig.length; i++)
//              dest[i] = (char) (orig[i] & 0xFF);
//          return new String(dest);
//      } catch (Exception e) {
//          System.out.println(e);
//          return s;
//      }
  	
  	try {
			return new String(s.getBytes("gbk"),"UTF-8");
		}
		catch (Exception e) {
			System.out.println(e);
			return s;
		}
  }

	/**
	 * ��AscII�ַ�ת���ɺ���
	 *
	 * @param s -
	 *            ASCII�ַ���
	 * @return ����
	 */
	public static String AsciiToChineseString(String s) {
//		char[] orig = s.toCharArray();
//		byte[] dest = new byte[orig.length];
//		for (int i = 0; i < orig.length; i++)
//			dest[i] = (byte) (orig[i] & 0xFF);
//		try {
//			ByteToCharConverter toChar = ByteToCharConverter
//					.getConverter("GBK");
//			return new String(toChar.convertAll(dest));
//		} catch (Exception e) {
//			System.out.println(e);
//			return s;
//		}
		try {
			return new String(s.getBytes("ISO8859_1"),"gbk");
		}
		catch (Exception e) {
			System.out.println(e);
			return s;
		}
	}

	/**
	 * ʹ��������ʽ�����ַ��������滻
	 *
	 * @param regularExpression -
	 *            ������ʽ
	 * @param sub -
	 * @param input
	 * @return
	 */
	//������ʽ�滻�Ӵ�
	public static synchronized String regexReplace(String regularExpression, String sub,
			String input) {
		Pattern pattern = PatternFactory.getPattern(regularExpression);
		Matcher matcher = pattern.matcher(input);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb,sub);
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * �ж�һ���ַ������Ƿ��������������ʽ�����ƥ���������Ӵ�
	 *
	 * @param regularExpression -
	 *            ������ʽ
	 * @param input -
	 *            ������ַ���
	 * @return - �������ַ����а�������������ʽ�����ƥ���������Ӵ����򷵻�true�����򷵻�false
	 */
	//������ʽƥ���ж�
	public static  synchronized  boolean exist(String regularExpression, String input) {
		Pattern pattern = PatternFactory.getPattern(regularExpression);
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}

	/**
	 * Return the contents of the parenthesized subgroups of a collectionPattern
	 * match
	 *
	 * @param str
	 * @return
	 */
	public static String parseCollection(String str) {
		String result = null;
		Matcher matcher = collectionPattern.matcher(str);
		if(matcher.find()){
			result = matcher.group(0);
		}		
		return result.toLowerCase();
	}

	/**
	 * ��"0"����һ���ַ�����ָ������
	 *
	 * @param str -
	 *            Դ�ַ���
	 * @param size -
	 *            �����Ӧ�ﵽ�ĳ���
	 * @return - �����Ľ��
	 */
	//�ַ�������
	public static String fillZero(String str, int size) {
		String result;
		if (str.length() < size) {
			char[] s = new char[size - str.length()];
			for (int i = 0; i < (size - str.length()); i++) {
				s[i] = '0';
			}
			result = new String(s) + str;
		} else {
			result = str;
		}

		return result;
	}

	/**
	 * ��hql�е�order by �滻��GlobalNames.ORDER_BY
	 *
	 * @param hql
	 *            String
	 * @return String
	 */
	public static synchronized String dealOrderBy(String hql) {
		String regexp = "order\\s+by";
		String spaceReg = "\\s+";
		hql = regexReplace(spaceReg, " ", hql);
		String orderHql = hql;
		if (StringHelper.exist(regexp, orderHql.toLowerCase())) {
			orderHql = StringHelper.regexReplace(regexp, GlobalNames.ORDER_BY,
					orderHql.toLowerCase());
			int orderByIndex = orderHql.lastIndexOf(GlobalNames.ORDER_BY);
			hql = new StringBuffer(hql.substring(0, orderByIndex)).append(
					GlobalNames.ORDER_BY).append(" ").append(
					hql.substring(orderByIndex + 8)).toString();

		}
		return hql;
	}
	
	/**
	 * �ж��ַ����Ƿ�Ϊ����
	 *
	 * @param String
	 * 
	 * @return boolean
	 */
	public static boolean isNumber(String str)
    {
        java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("[0-9]*");
        java.util.regex.Matcher match=pattern.matcher(str);
        if(match.matches()==false)
        {
             return false;
        }
        else
        {
             return true;
        }
    }
	
}
