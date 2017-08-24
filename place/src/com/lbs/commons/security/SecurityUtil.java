package com.lbs.commons.security;

import java.security.MessageDigest;

/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: LBS</p>
 * @author <a href="mailto:chenkl@bjlbs.com.cn">chenkl</a>
 * @version 1.0
 */
public class SecurityUtil  {

  /**
   * md5Ëã·¨¼ÓÃÜ×Ö·û´®
   * @param str String
   * @return String
   */
  public static String MD5(String str) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(str.getBytes("UTF8"));
      byte s[] = md.digest();
      String result = "";
      for (int i = 0; i < s.length; i++) {
        result += Integer.toHexString( (0x000000FF & s[i]) | 0xFFFFFF00).
            substring(6);
      }
      return result;
    } catch (Exception e) {
      return null;
    }
  }
}
