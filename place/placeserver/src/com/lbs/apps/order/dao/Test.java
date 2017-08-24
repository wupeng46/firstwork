package com.lbs.apps.order.dao;

import com.lbs.commons.ClassHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test {
	private static String ls_weigh_str;

	public static void main(String[] args) {
		ls_weigh_str="{\"root\":[{\"rows\":\"1\",\"weighid\":\"GBD201705220001\"},{\"rows\":\"2\",\"weighid\":\"GBD201705220002\"}]}";
		JSONObject obj = JSONObject.fromObject(ls_weigh_str); // 解析JSON数据包
		JSONArray jarray = obj.getJSONArray("root");
		for (int i = 0; i < jarray.size(); i++){
            JSONObject array = jarray.getJSONObject(i);
            WeighDTO dto=new WeighDTO();
            ClassHelper.copyProperties(array, dto);
            System.out.println(dto.getWeighid());
        }
		
		//String ls_companyid = (String) obj.get("companyid");

	}

}
