package com.lbs.apps.common;
 
import com.lbs.commons.op.OPException;

import com.lbs.commons.op.OPManager;

public class QueryUtil {
	/*
	 * 获取系统参数值
	 */
	
	private final static OPManager op = new OPManager();
	
	public static String SearchSysParaValue(String paracode) {		
		StringBuffer sb = new StringBuffer("");
		sb.append("select paravalue from syspara where 1=1 and isvalid='1001'");		
		sb.append(" and (paracode='"+ paracode + "') ");
		
		try {
			return op.executeMinMaxSQLQuery(sb.toString());
		} catch (OPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
}
