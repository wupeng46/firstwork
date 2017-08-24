package com.lbs.commons;

import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * Title:leaf
 * </p>
 * <p>
 * Description:提供字符串处理的实用方法集
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
	public static final char DOT='.';                                    // 点 

	static Pattern collectionPattern = null;


	//静态构造方法
	static {
			collectionPattern = Pattern.compile("[a-z]{3}[0-9]{2}[0-9a-z]");
	}

	public StringHelper() {
	}

	/**
	 * 将null转换为成字符串""，如果参数obj不为null，则不做处理；如果obj为null，则返回字符串""
	 *
	 * @param obj -
	 *            待转换的对象
	 * @return String - 转换后的结果
	 */
	public static String showNull2Empty(Object obj) {
		if (null == obj) {
			return "";
		} else {
			return obj + "";
		}
	}

	/**
	 * 将字符串str中的oleSub替换成newSub
	 *
	 * @param inputstr
	 *            String - 待替换的字符串
	 * @param oldsub
	 *            String - 要替换的子符
	 * @param newsub
	 *            String -
	 * @return String - 替换后的字符串
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

	//存放国标一级汉字不同读音的起始区位码
	static final int[] li_SecPosValue = {1601, 1637, 1833, 2078, 2274, 2302,
			2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027,
			4086, 4390, 4558, 4684, 4925, 5249};

	//存放国标一级汉字不同读音的起始区位码对应读音
	static final String[] lc_FirstLetter = {"A", "B", "C", "D", "E", "F", "G",
			"H", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "W",
			"X", "Y", "Z"};

	//存放所有国标二级汉字读音
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
	 * 生成拼音码
	 * 返回给定汉字串的首字母串,即声母串 此方法基于汉字的国标汉字库区位编码的有效性，不符合此编码的系统此函数无效
	 * 若汉字串含有非汉字字符,如图形符号或ASCII码,则这些非汉字字符将保持不变.
	 *
	 * @param strinput
	 * @return 声母串
	 * @throws Exception
	 */
	public static String MnemonicWords(String strinput) throws Exception {

		//返回串
		String ls_ReturnStr = "";
		try {
			for (int i = 0; i < strinput.length(); i++) { //依次处理as_InputString中每个字符
				String s = strinput.substring(i, i + 1);
				//Unix问题？
				byte b[] = s.getBytes("gbk");
				//System.out.println(b);
				if (b.length == 1) { //如果不是汉字,则直接返回
					ls_ReturnStr = ls_ReturnStr
							+ strinput.substring(i, i + 1).toUpperCase();
				} else {
					int li_SectorCode = 256 + (int) b[0] - 160; //区码
					int li_PositionCode = 256 + (int) b[1] - 160; //位码
					int li_SecPosCode = li_SectorCode * 100 + li_PositionCode; //区位码
					if (li_SecPosCode > 1600 && li_SecPosCode < 5590) {
						for (int j = 22; j >= 0; j--) { // 找声母
							if (li_SecPosCode >= li_SecPosValue[j]) {
								ls_ReturnStr = ls_ReturnStr + lc_FirstLetter[j];
								break;
							}
						}
					} else { //需要计算偏移量
						int li_offset = (li_SectorCode - 56) * 94
								+ li_PositionCode - 1;
						if (li_offset >= 0 && li_offset <= 3007) { //二区汉字
							ls_ReturnStr = ls_ReturnStr
									+ ls_SecondSecTable.substring(li_offset,
											li_offset + 1); //取出此字声母
						} else {
							ls_ReturnStr = ls_ReturnStr + strinput.charAt(i); //如果是特殊字符保留
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("取汉字拼音码错误！",e);
			e.printStackTrace();
		}
		return (ls_ReturnStr.toLowerCase());
	}

	/**
	 * 将中文转化成AscII码以便存入数据库
	 *
	 * @param s
	 *            中文字符串
	 * @return 16进制字符串
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
     * 将中文转化成AscII码以便存入数据库
     *
     * @param s
     *            中文字符串
     * @return 16进制字符串
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
	 * 将AscII字符转换成汉字
	 *
	 * @param s -
	 *            ASCII字符串
	 * @return 汉字
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
	 * 使用正则表达式进行字符串内容替换
	 *
	 * @param regularExpression -
	 *            正则表达式
	 * @param sub -
	 * @param input
	 * @return
	 */
	//正则表达式替换子串
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
	 * 判断一个字符串中是否包含符合正则表达式定义的匹配条件的子串
	 *
	 * @param regularExpression -
	 *            正则表达式
	 * @param input -
	 *            待检查字符串
	 * @return - 若输入字符串中包含符合正则表达式定义的匹配条件的子串，则返回true，否则返回false
	 */
	//正则表达式匹配判断
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
	 * 用"0"补足一个字符串到指定长度
	 *
	 * @param str -
	 *            源字符串
	 * @param size -
	 *            补足后应达到的长度
	 * @return - 补零后的结果
	 */
	//字符串补零
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
	 * 将hql中的order by 替换成GlobalNames.ORDER_BY
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
	 * 判断字符串是否为数字
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
