/*
 * Created on 2004-3-49:05:56
 *
 */
package com.lbs.commons;

import java.util.Random;


/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: ��������ļ���
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: BJLBS
 * </p>
 * 
 * @author HongliLi <lihl@bjlbs.com.cn>
 * @version 1.0
 */
public class FileNameGenerator {

	/**
	 * �������������
	 */
	protected static Random r = new Random();

	/**
	 * �������һ���ļ���
	 * 
	 * @return - ������ɵ��ļ���
	 */
	public static String nextFileName() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < Integer.valueOf(GlobalNames.TEMP_FILE_NAME_LENGTH)
				.intValue(); i++) {
			sb.append(r.nextInt(10));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
	}
}