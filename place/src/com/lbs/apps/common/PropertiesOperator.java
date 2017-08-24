package com.lbs.apps.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesOperator {
	/**
	 * # * @describe 根据指定的配置文件名和Key 返回 key 的属性值 # * @param name 配置文件名 # * @param
	 * key 键 # * @return 返回 属性值 #
	 * 
	 * @throws Exception
	 */
	public static String getValueByKey(String name, String key) {

		String result = null;
		Properties p = new Properties();
		InputStream in = PropertiesOperator.class.getClassLoader()
				.getResourceAsStream(name);
		if (in == null) {
			result = "";
		}
		
		try {
			p.load(in);
			result = p.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (result==null || result.equals("")){
			result = QueryUtil.SearchSysParaValue(key);
		}
		
		return result;
	}
}
