package com.lbs.apps.system.dao;

import net.sf.json.JSONObject;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.JsonValidator;
import com.lbs.apps.system.po.Area;
import com.lbs.commons.ClassHelper;
import com.lbs.commons.TransManager;
import com.lbs.commons.log.LogHelper;
import com.lbs.commons.op.NotFindException;
import com.lbs.commons.op.OPException;
import com.lbs.commons.op.OPManager;

public class AreaBPO {
	LogHelper log = new LogHelper(this.getClass());	
	TransManager trans = new TransManager();
	OPManager op = new OPManager();
	
	//判断查询条件是否OK，成功返回0，否则返回异常原因
		public String CheckSql_Where(String msgdata){
			String ls_return="0";
			if (msgdata==null || msgdata.equals("")){  //没有传递参数
				ls_return="请求参数异常";			
			}else{					 
				try{
					JSONObject obj = JSONObject.fromObject(msgdata);   //解析JSON数据包				
					//if (!obj.has("roleid")){					
					//	ls_return="数据包中未包含roleid参数信息";									
					//}
				}catch(Exception e){
					ls_return="msgdata数据字符串非法";						
				}			
			}	
			return ls_return;		
		}
		/**
		 * 查询行政区划
		 * @param msgdata
		 *@param areaid	   行政区划编码
          @param areafullname  行政区划全称
          @param arealevel  行政区划级别
          @param statecode	省代码
          @param statename	省名称
          @param citycode	市代码
          @param cityname   市名称
          @param areacode	区县代码
          @param areaname	区县名称
          @param towncode	乡镇街道代码
          @param townname	乡镇街道名称
          @param isvalid	是否有效
         * @return
		 * @throws ApplicationException
		 */
		public String GetSql_SearchArea(String msgdata)throws ApplicationException {
			AreaDTO dto=new AreaDTO();
			ClassHelper.copyProperties(msgdata, dto);		 
			StringBuffer sb=new StringBuffer("select areaid,areafullname,arealevel,statecode,statename,citycode,cityname,areacode,areaname,towncode,townname,isvalid from Area where 1=1");
			if (dto.getAreaid()!=null){
				sb.append(" and areaid='").append(dto.getAreaid()+"'");
			}
			if (!(dto.getAreafullname()==null || dto.getAreafullname().equals(""))){
				sb.append(" and areafullname like '%").append(dto.getAreafullname()).append("%'");
			}
			if (!(dto.getArealevel()==null || dto.getArealevel().equals(""))){
				sb.append(" and arealevel = '").append(dto.getArealevel()).append("'");
			}
			if (!(dto.getStatecode()==null || dto.getStatecode().equals(""))){
				sb.append(" and statecode = '").append(dto.getStatecode()).append("'");
			}
			if (!(dto.getStatename()==null || dto.getStatename().equals(""))){
				sb.append(" and statename = '").append(dto.getStatename()).append("'");
			}
			if (!(dto.getCitycode()==null || dto.getCitycode().equals(""))){
				sb.append(" and citycode = '").append(dto.getCitycode()).append("'");
			}
			if (!(dto.getCityname()==null || dto.getCityname().equals(""))){
				sb.append(" and cityname = '").append(dto.getCityname()).append("'");
			}
			if (!(dto.getAreacode()==null || dto.getAreacode().equals(""))){
				sb.append(" and areacode = '").append(dto.getAreacode()).append("'");
			}
			if (!(dto.getAreaname()==null || dto.getAreaname().equals(""))){
				sb.append(" and areaname = '").append(dto.getAreaname()).append("'");
			}
			if (!(dto.getTowncode()==null || dto.getTowncode().equals(""))){
				sb.append(" and towncode = '").append(dto.getTowncode()).append("'");
			}
			if (!(dto.getTownname()==null || dto.getTownname().equals(""))){
				sb.append(" and townname = '").append(dto.getTownname()).append("'");
			}
			if (!(dto.getIsvalid()==null || dto.getIsvalid().equals(""))){
				sb.append(" and isvalid = '").append(dto.getIsvalid()).append("'");
			}
			sb.append(" order by arealevel");
			String ls_sql=sb.toString();		
			return ls_sql;		
		}	
		
		/**
		 * 获取省列表
		 * @param msgdata
		 * @return
		 * @throws ApplicationException
		 */
		public String GetSql_SearchStateList(String msgdata)throws ApplicationException {
			return "select statecode,statename from area where arealevel='2' and isvalid='101'";	
		}
		
		/**
		 * 根据省代码获取市级列表
		 * @param msgdata
		 * @param statecode 省代码
		 * @return
		 * @throws ApplicationException
		 */
		public String GetSql_SearchCityList(String msgdata)throws ApplicationException {
			AreaDTO dto=new AreaDTO();
			ClassHelper.copyProperties(msgdata, dto);
			if (dto.getStatecode()==null) dto.setStatecode("");
			String ls_sql="select citycode,cityname from area where arealevel='3'  and isvalid='101' and statecode='"+dto.getStatecode()+"'";
			return ls_sql;
		}
		/**
		 * 根据市代码获取区县列表
		 * @param msgdata
		 * @param citycode 市代码
		 * @return
		 * @throws ApplicationException
		 */
		public String GetSql_SearchAreaList(String msgdata)throws ApplicationException {
			AreaDTO dto=new AreaDTO();
			ClassHelper.copyProperties(msgdata, dto);
			if (dto.getCitycode()==null) dto.setCitycode("");
			String ls_sql="select areacode,areaname from area where arealevel='4'  and isvalid='101'and citycode='"+dto.getCitycode()+"'";
			return ls_sql;
		}
		/**
		 * 根据区县代码获取乡镇街道列表
		 * @param msgdata
		 * @param areacode 区县代码
		 * @return
		 * @throws ApplicationException
		 */
		public String GetSql_SearchTownList(String msgdata)throws ApplicationException {
			AreaDTO dto=new AreaDTO();
			ClassHelper.copyProperties(msgdata, dto);
			if (dto.getAreacode()==null) dto.setAreacode("");
			String ls_sql="select towncode,townname from area where arealevel='5'  and isvalid='101'and areacode='"+dto.getAreacode()+"'";
			return ls_sql;
		}
		/**
		 * 增加行政区划
		 * @param msgdata
		 *@param areaid	String	是	行政区划编码
          @param areafullname  行政区划全称
          @param arealevel  行政区划级别
          @param statecode	省代码
          @param statename	省名称
          @param citycode	市代码
          @param cityname   市名称
          @param areacode	区县代码
          @param areaname	区县名称
          @param towncode	乡镇街道代码
          @param townname	乡镇街道名称
          @param isvalid	是否有效
         * @return
		 * @throws ApplicationException
		 */
		public String AddArea(String msgdata)throws ApplicationException{ //增加行政区划
			String ls_return="0";
			//json验证
			JsonValidator jsonValidator=new JsonValidator();
			if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
			Area area=new Area();   //实例化参数表
			ClassHelper.copyProperties(msgdata, area);	
			if (area.getAreaid()==null) return "行政区划编码不能为空";
			if (area.getAreafullname()==null || area.getAreafullname().equals("")) return "行政区划名称不能为空";
			if (area.getArealevel()==null || area.getArealevel().equals("")) return "行政区划级别不能为空";
			String hql="select count(*) from Area where areaid='"+area.getAreaid()+"' or areafullname='"+area.getAreafullname()+"'";
			try {
				if (op.getCount(hql).intValue()>0){
					return "行政区划编码或名称已存在";
				}
			} catch (OPException e) {
				return e.getMessage();
			}
			if (area.getArealevel().equals("2")){  //省
				if (area.getStatecode()==null || area.getStatecode().equals("") || area.getStatecode().equals("null")){
					area.setStatecode(area.getAreaid());
					area.setStatename(area.getAreafullname());
					area.setAreafullname(area.getStatename());
				}				
			}else if (area.getArealevel().equals("3")){  //市
				if (area.getCitycode()==null || area.getCitycode().equals("") || area.getCitycode().equals("null")){
					area.setCitycode(area.getAreaid());
					area.setCityname(area.getAreafullname());
					area.setAreafullname(area.getStatename()+area.getCityname());
				}
			}else if (area.getArealevel().equals("4")){  //区县
				if (area.getAreacode()==null || area.getAreacode().equals("") || area.getAreacode().equals("null")){
					area.setAreacode(area.getAreaid());
					area.setAreaname(area.getAreafullname());
					area.setAreafullname(area.getStatename()+area.getCityname()+area.getAreaname());
				}
			}else if (area.getArealevel().equals("5")){  //乡镇街道
				if (area.getTowncode()==null || area.getTowncode().equals("") || area.getTowncode().equals("null")){
					area.setTowncode(area.getAreaid());
					area.setTownname(area.getAreafullname());
					area.setAreafullname(area.getStatename()+area.getCityname()+area.getAreaname()+area.getTownname());
				}
			}
			//数据保存
			try {
				trans.begin();						
				op.saveObj(area);					
				trans.commit();
			} catch (OPException e1) {
			    try {
					trans.rollback();
				} catch (OPException e2) {
					return e2.getMessage();
				}	
				return e1.getMessage();
			}		
			return ls_return;		
		}
		/**
		 * 修改行政区划
		 * @param msgdata
		 *@param areaid	String	是	行政区划编码
          @param areafullname  行政区划全称
          @param arealevel  行政区划级别
          @param statecode	省代码
          @param statename	省名称
          @param citycode	市代码
          @param cityname   市名称
          @param areacode	区县代码
          @param areaname	区县名称
          @param towncode	乡镇街道代码
          @param townname	乡镇街道名称
          @param isvalid	是否有效
         * @return
		 * @throws ApplicationException
		 */
		public String ModArea(String msgdata)throws ApplicationException{ //修改行政区划
			String ls_return="0";
			//json验证
			JsonValidator jsonValidator=new JsonValidator();
			if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
			Area Area=new Area();   //实例化参数表
			ClassHelper.copyProperties(msgdata, Area);	
			if (Area.getAreaid()==null) return "行政区划编码不能为空";
			if (Area.getAreafullname()==null || Area.getAreafullname().equals("")) return "行政区划名称不能为空";
			if (Area.getArealevel()==null || Area.getArealevel().equals("")) return "行政区划级别不能为空";
			//数据保存
			try {
				trans.begin();						
				op.updateObj(Area);					
				trans.commit();
			} catch (OPException e1) {
			    try {
					trans.rollback();
				} catch (OPException e2) {
					return e2.getMessage();
				}	
				return e1.getMessage();
			}		
			return ls_return;	
		}
		/**
		 * 删除行政区划
		 * @param msgdata
		 *@param areaid	行政区划编码
         * @return
		 * @throws ApplicationException
		 */
		public String DelArea(String msgdata)throws ApplicationException{ //删除行政区划
			String ls_return="0";
			//json验证
			JsonValidator jsonValidator=new JsonValidator();
			if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
			AreaDTO dto=new AreaDTO();   //实例化参数表
			ClassHelper.copyProperties(msgdata, dto);	
			if (dto.getAreaid()==null) return "行政区划编码不能为空";
			//数据保存
			try {
				trans.begin();						
				op.removeObjs( "delete from Area a where a.areaid='"+dto.getAreaid()+"'");					
				trans.commit();
			} catch (OPException e1) {
			    try {
					trans.rollback();
				} catch (OPException e2) {
					return e2.getMessage();
				}	
				return e1.getMessage();
			}		
			return ls_return;	
		}

}
