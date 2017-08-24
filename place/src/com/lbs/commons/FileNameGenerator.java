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
 * Description: 随机生成文件名
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
	 * 随机数产生对象
	 */
	protected static Random r = new Random();

	/**
	 * 随机产生一个文件名
	 * 
	 * @return - 随机生成的文件名
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