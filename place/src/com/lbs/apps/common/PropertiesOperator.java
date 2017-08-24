package com.lbs.apps.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesOperator {
	/**
	 * # * @describe ����ָ���������ļ�����Key ���� key ������ֵ # * @param name �����ļ��� # * @param
	 * key �� # * @return ���� ����ֵ #
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
