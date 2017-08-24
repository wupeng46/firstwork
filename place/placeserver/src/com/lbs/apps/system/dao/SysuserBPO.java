package com.lbs.apps.system.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.DataBaseUtil;
import com.lbs.apps.common.FileUtil;
import com.lbs.apps.common.JsonValidator;
import com.lbs.apps.common.Md5Util;
import com.lbs.apps.common.PropertiesOperator;
import com.lbs.apps.system.po.Account;
import com.lbs.apps.system.po.Sysuser;
import com.lbs.apps.system.po.Userrole;
import com.lbs.apps.system.po.UserroleId;
import com.lbs.commons.ClassHelper;
import com.lbs.commons.DateUtil;
import com.lbs.commons.TransManager;
import com.lbs.commons.log.LogHelper;
import com.lbs.commons.op.NotFindException;
import com.lbs.commons.op.OPException;
import com.lbs.commons.op.OPManager;
import com.lbs.commons.security.BASE64Decoder;

public class SysuserBPO {
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
	 * 查询用户信息
	 * @param msgdata
	 *@param userid 用户编码
      @param username	用户名称
      @param loginid	登录名
      @param memberid	托运方编号
      @param companyid	承运商编号
      @param usertype	用户类型
      @param isvalid	是否有效
      @param userstatus	用户状态
      @param telephone	电话
      @param cardtype	证件类型
      @param identifyid	证件号码
      @param sex	性别
      @param userlevel	用户等级
      @param headimg	头像地址
      @param memo	备注
      @param totalamount 帐户总金额
      @param useramount	帐户可用金额
      @param freezeamount	帐户冻结金额
      @param integral  积分
     * @return
	 * @throws ApplicationException
	 */
	public String GetSql_SearchSysuser(String msgdata)throws ApplicationException {
		SysuserDTO dto=new SysuserDTO();
		ClassHelper.copyProperties(msgdata, dto);		 
		StringBuffer sb=new StringBuffer("select a.userid,memberid,companyid,groupid,username,loginid,password,usertype,isvalid,userstatus,telephone,phonearea,phoneno,qq,email,wxopenid,address,convert(varchar,birthday,120) birthday,cardtype,identifyid,sex,userlevel,headimg,memo,totalamount,useramount,freezeamount,integral,qualificationstatus,regusertype,convert(varchar,a.createddate,120) createddate from Sysuser a,Account b where a.userid=b.userid");
		
		if (dto.getUserid()!=null){
			sb.append(" and a.userid=").append(dto.getUserid());
		}
		if (!(dto.getUsername()==null || dto.getUsername().equals(""))){
			sb.append(" and a.username like '%").append(dto.getUsername()).append("%'");
		}
		if (!(dto.getLoginid()==null || dto.getLoginid().equals(""))){
			sb.append(" and a.loginid = '").append(dto.getLoginid()).append("'");
		}
		if (!(dto.getUsertype()==null || dto.getUsertype().equals(""))){
			sb.append(" and a.usertype = '").append(dto.getUsertype()).append("'");
		}
		if (!(dto.getIsvalid()==null || dto.getIsvalid().equals(""))){
			sb.append(" and a.isvalid = '").append(dto.getIsvalid()).append("'");
		}
		if (!(dto.getUserstatus()==null || dto.getUserstatus().equals(""))){
			sb.append(" and a.userstatus = '").append(dto.getUserstatus()).append("'");
		}
		if (!(dto.getTelephone()==null || dto.getTelephone().equals(""))){
			sb.append(" and a.telephone = '").append(dto.getTelephone()).append("'");
		}
		if (!(dto.getCardtype()==null || dto.getCardtype().equals(""))){
			sb.append(" and a.cardtype = '").append(dto.getCardtype()).append("'");
		}
		if (!(dto.getIdentifyid()==null || dto.getIdentifyid().equals(""))){
			sb.append(" and identifyid = '").append(dto.getIdentifyid()).append("'");
		}
		if (!(dto.getSex()==null || dto.getSex().equals(""))){
			sb.append(" and a.sex = '").append(dto.getSex()).append("'");
		}
		if (!(dto.getUserlevel()==null || dto.getUserlevel().equals(""))){
			sb.append(" and a.userlevel = '").append(dto.getUserlevel()).append("'");
		}	
		if (!(dto.getStarttime()==null || dto.getStarttime().equals(""))){
			sb.append(" and a.createddate >= '").append(dto.getStarttime()).append(" 00:00:00'");				
		}
		if (!(dto.getEndtime()==null || dto.getEndtime().equals(""))){
			sb.append(" and a.createddate <= '").append(dto.getEndtime()).append(" 23:59:59'");
		}
		String ls_sql=sb.toString();		
		return ls_sql;
	}	
		
	/**
	 * 查询承运商用户
	 *@param msgdata
	 *@param companyname	承运商名称
      @param userid	        用户编码
      @param username		用户名称
      @param loginid		登录名
      @param memberid		托运方编号
      @param companyid		承运商编号
      @param usertype		用户类型
      @param isvalid		是否有效
      @param userstatus		用户状态
      @param telephone		电话
      @param cardtype		证件类型
      @param identifyid	证件号码
      @param sex		性别
      @param userlevel		用户等级
      @param headimg		头像地址
      @param memo	备注
      @param totalamount		帐户总金额
      @param useramount		帐户可用金额
      @param freezeamount	帐户冻结金额
      @param integral		积分
     * @return
	 * @throws ApplicationException
	 */
	public String GetSql_SearchCompanyUser(String msgdata)throws ApplicationException {
		SysuserDTO dto=new SysuserDTO();
		ClassHelper.copyProperties(msgdata, dto);		 
		StringBuffer sb=new StringBuffer("select a.userid,a.memberid,a.companyid,groupid,username,loginid,password,usertype,isvalid,userstatus,a.telephone,phonearea,phoneno,qq,a.email,wxopenid,a.address,convert(varchar,birthday,120) birthday,cardtype,identifyid,sex,userlevel,headimg,a.memo,totalamount,useramount,freezeamount,integral,qualificationstatus,regusertype,c.companyname,convert(varchar,a.createddate,120) createddate")
		.append(" from Sysuser a,Account b,Company c where a.userid=b.userid and a.companyid=c.companyid");
		if (!(dto.getCompanyname()==null || dto.getCompanyname().equals(""))){
			sb.append(" and c.companyname like '%").append(dto.getCompanyname()).append("%'");
		}
		if (dto.getUserid()!=null){
			sb.append(" and a.userid=").append(dto.getUserid());
		}
		if (!(dto.getUsername()==null || dto.getUsername().equals(""))){
			sb.append(" and a.username like '%").append(dto.getUsername()).append("%'");
		}
		if (!(dto.getLoginid()==null || dto.getLoginid().equals(""))){
			sb.append(" and a.loginid = '").append(dto.getLoginid()).append("'");
		}
		if (!(dto.getUsertype()==null || dto.getUsertype().equals(""))){
			sb.append(" and a.usertype = '").append(dto.getUsertype()).append("'");
		}
		if (!(dto.getIsvalid()==null || dto.getIsvalid().equals(""))){
			sb.append(" and a.isvalid = '").append(dto.getIsvalid()).append("'");
		}
		if (!(dto.getUserstatus()==null || dto.getUserstatus().equals(""))){
			sb.append(" and a.userstatus = '").append(dto.getUserstatus()).append("'");
		}
		if (!(dto.getTelephone()==null || dto.getTelephone().equals(""))){
			sb.append(" and a.telephone = '").append(dto.getTelephone()).append("'");
		}
		if (!(dto.getCardtype()==null || dto.getCardtype().equals(""))){
			sb.append(" and a.cardtype = '").append(dto.getCardtype()).append("'");
		}
		if (!(dto.getIdentifyid()==null || dto.getIdentifyid().equals(""))){
			sb.append(" and identifyid = '").append(dto.getIdentifyid()).append("'");
		}
		if (!(dto.getSex()==null || dto.getSex().equals(""))){
			sb.append(" and a.sex = '").append(dto.getSex()).append("'");
		}
		if (!(dto.getUserlevel()==null || dto.getUserlevel().equals(""))){
			sb.append(" and a.userlevel = '").append(dto.getUserlevel()).append("'");
		}	
		if (!(dto.getStarttime()==null || dto.getStarttime().equals(""))){
			sb.append(" and a.createddate >= '").append(dto.getStarttime()).append(" 00:00:00'");				
		}
		if (!(dto.getEndtime()==null || dto.getEndtime().equals(""))){
			sb.append(" and a.createddate <= '").append(dto.getEndtime()).append(" 23:59:59'");
		}
		String ls_sql=sb.toString();		
		return ls_sql;
	}	
	
	/**
	 * 查询委托方用户
	 *@param msgdata
	 *@param companyname	承运商名称
      @param userid	        用户编码
      @param username		用户名称
      @param loginid		登录名
      @param memberid		托运方编号
      @param companyid		承运商编号
      @param usertype		用户类型
      @param isvalid		是否有效
      @param userstatus		用户状态
      @param telephone		电话
      @param cardtype		证件类型
      @param identifyid	证件号码
      @param sex		性别
      @param userlevel		用户等级
      @param headimg		头像地址
      @param memo	备注
      @param totalamount		帐户总金额
      @param useramount		帐户可用金额
      @param freezeamount	帐户冻结金额
      @param integral		积分
     * @return
	 * @throws ApplicationException
	 */
	public String GetSql_SearchMemberUser(String msgdata)throws ApplicationException {
		SysuserDTO dto=new SysuserDTO();
		ClassHelper.copyProperties(msgdata, dto);		 
		StringBuffer sb=new StringBuffer("select a.userid,a.memberid,a.companyid,groupid,username,loginid,password,usertype,isvalid,userstatus,a.telephone,phonearea,phoneno,qq,a.email,wxopenid,a.address,convert(varchar,birthday,120) birthday,cardtype,identifyid,sex,userlevel,headimg,a.memo,totalamount,useramount,freezeamount,integral,qualificationstatus,regusertype,c.companyname,convert(varchar,a.createddate,120) createddate")
		.append(" from Sysuser a,Account b,Member c  where a.userid=b.userid and a.memberid=c.memberid");
		if (!(dto.getCompanyname()==null || dto.getCompanyname().equals(""))){
			sb.append(" and c.companyname like '%").append(dto.getCompanyname()).append("%'");
		}
		if (dto.getUserid()!=null){
			sb.append(" and a.userid=").append(dto.getUserid());
		}
		if (!(dto.getUsername()==null || dto.getUsername().equals(""))){
			sb.append(" and a.username like '%").append(dto.getUsername()).append("%'");
		}
		if (!(dto.getLoginid()==null || dto.getLoginid().equals(""))){
			sb.append(" and a.loginid = '").append(dto.getLoginid()).append("'");
		}
		if (!(dto.getUsertype()==null || dto.getUsertype().equals(""))){
			sb.append(" and a.usertype = '").append(dto.getUsertype()).append("'");
		}
		if (!(dto.getIsvalid()==null || dto.getIsvalid().equals(""))){
			sb.append(" and a.isvalid = '").append(dto.getIsvalid()).append("'");
		}
		if (!(dto.getUserstatus()==null || dto.getUserstatus().equals(""))){
			sb.append(" and a.userstatus = '").append(dto.getUserstatus()).append("'");
		}
		if (!(dto.getTelephone()==null || dto.getTelephone().equals(""))){
			sb.append(" and a.telephone = '").append(dto.getTelephone()).append("'");
		}
		if (!(dto.getCardtype()==null || dto.getCardtype().equals(""))){
			sb.append(" and a.cardtype = '").append(dto.getCardtype()).append("'");
		}
		if (!(dto.getIdentifyid()==null || dto.getIdentifyid().equals(""))){
			sb.append(" and identifyid = '").append(dto.getIdentifyid()).append("'");
		}
		if (!(dto.getSex()==null || dto.getSex().equals(""))){
			sb.append(" and a.sex = '").append(dto.getSex()).append("'");
		}
		if (!(dto.getQualificationstatus()==null || dto.getQualificationstatus().equals(""))){
			sb.append(" and a.qualificationstatus = '").append(dto.getQualificationstatus()).append("'");
		}
		
		if (!(dto.getUserlevel()==null || dto.getUserlevel().equals(""))){
			sb.append(" and a.userlevel = '").append(dto.getUserlevel()).append("'");
		}	
		if (!(dto.getStarttime()==null || dto.getStarttime().equals(""))){
			sb.append(" and a.createddate >= '").append(dto.getStarttime()).append(" 00:00:00'");				
		}
		if (!(dto.getEndtime()==null || dto.getEndtime().equals(""))){
			sb.append(" and a.createddate <= '").append(dto.getEndtime()).append(" 23:59:59'");
		}
		String ls_sql=sb.toString();		
		return ls_sql;
	}	
	
	/**
	 * 查询待审核的企业注册用户
	 * @param msgdata
	 *@param userid		用户编码
      @param username	用户名称
      @param loginid		登录名
      @param memberid	托运方编号
      @param companyid		承运商编号
      @param usertype		用户类型
      @param isvalid		是否有效
      @param userstatus		用户状态
      @param telephone		电话
      @param cardtype		证件类型
      @param identifyid		证件号码
      @param sex	性别
      @param userlevel		用户等级
      @param headimg		头像地址
      @param memo		备注
      @param totalamount		帐户总金额
      @param useramount		帐户可用金额
      @param freezeamount		帐户冻结金额
      @param integral		积分
     * @return
	 * @throws ApplicationException
	 */
	public String GetSql_SearchNoCheckCompanySysuser(String msgdata)throws ApplicationException {
		SysuserDTO dto=new SysuserDTO();
		ClassHelper.copyProperties(msgdata, dto);		 
		StringBuffer sb=new StringBuffer("select a.userid,memberid,a.companyid,groupid,username,loginid,password,usertype,isvalid,userstatus,a.telephone,phonearea,phoneno,qq,a.email,wxopenid,a.address,convert(varchar,birthday,120) birthday,cardtype,identifyid,sex,userlevel,headimg,a.memo,totalamount,useramount,freezeamount,integral,qualificationstatus from Sysuser a,Account b,company c where a.userid=b.userid")
		.append(" and a.companyid=c.companyid and a.usertype='104' and c.checkstatus in('101','103')");
		
		if (dto.getUserid()!=null){
			sb.append(" and a.userid=").append(dto.getUserid());
		}
		if (!(dto.getUsername()==null || dto.getUsername().equals(""))){
			sb.append(" and a.username like '%").append(dto.getUsername()).append("%'");
		}
		if (!(dto.getLoginid()==null || dto.getLoginid().equals(""))){
			sb.append(" and a.loginid = '").append(dto.getLoginid()).append("'");
		}
		if (!(dto.getUsertype()==null || dto.getUsertype().equals(""))){
			sb.append(" and a.usertype = '").append(dto.getUsertype()).append("'");
		}
		if (!(dto.getIsvalid()==null || dto.getIsvalid().equals(""))){
			sb.append(" and a.isvalid = '").append(dto.getIsvalid()).append("'");
		}
		if (!(dto.getUserstatus()==null || dto.getUserstatus().equals(""))){
			sb.append(" and a.userstatus = '").append(dto.getUserstatus()).append("'");
		}
		if (!(dto.getTelephone()==null || dto.getTelephone().equals(""))){
			sb.append(" and a.telephone = '").append(dto.getTelephone()).append("'");
		}
		if (!(dto.getCardtype()==null || dto.getCardtype().equals(""))){
			sb.append(" and a.cardtype = '").append(dto.getCardtype()).append("'");
		}
		if (!(dto.getIdentifyid()==null || dto.getIdentifyid().equals(""))){
			sb.append(" and a.identifyid = '").append(dto.getIdentifyid()).append("'");
		}
		if (!(dto.getSex()==null || dto.getSex().equals(""))){
			sb.append(" and a.sex = '").append(dto.getSex()).append("'");
		}
		if (!(dto.getUserlevel()==null || dto.getUserlevel().equals(""))){
			sb.append(" and a.userlevel = '").append(dto.getUserlevel()).append("'");
		}	
		if (!(dto.getStarttime()==null || dto.getStarttime().equals(""))){
			sb.append(" and a.createddate >= '").append(dto.getStarttime()).append(" 00:00:00'");				
		}
		if (!(dto.getEndtime()==null || dto.getEndtime().equals(""))){
			sb.append(" and a.createddate <= '").append(dto.getEndtime()).append(" 23:59:59'");
		}
		String ls_sql=sb.toString();		
		return ls_sql;
	}
	
	/**
	 * 查询待审核的委托方注册用户
	 * @param msgdata
	 * @param userid	用户编码
       @param username		用户名称
       @param loginid		登录名
       @param memberid		托运方编号
       @param companyid		承运商编号
       @param usertype		用户类型
       @param isvalid		是否有效
       @param userstatus		用户状态
       @param telephone		电话
       @param cardtype		证件类型
       @param identifyid		证件号码
       @param sex		性别
       @param userlevel		用户等级
       @param headimg		头像地址
       @param memo		备注
       @param totalamount		帐户总金额
       @param useramount		帐户可用金额
       @param freezeamount		帐户冻结金额
       @param integral		积分
     * @return
	 * @throws ApplicationException
	 */
	public String GetSql_SearchNoCheckMemberSysuser(String msgdata)throws ApplicationException {
		SysuserDTO dto=new SysuserDTO();
		ClassHelper.copyProperties(msgdata, dto);		 
		StringBuffer sb=new StringBuffer("select a.userid,a.memberid,a.companyid,groupid,username,loginid,password,usertype,isvalid,userstatus,a.telephone,phonearea,phoneno,qq,a.email,wxopenid,a.address,convert(varchar,birthday,120) birthday,cardtype,identifyid,sex,userlevel,headimg,a.memo,totalamount,useramount,freezeamount,integral,qualificationstatus from Sysuser a,Account b,member c where a.userid=b.userid")
		.append(" and a.memberid=c.memberid and a.usertype in ('102','103') and c.checkstatus in('101','103')");
		
		if (dto.getUserid()!=null){
			sb.append(" and a.userid=").append(dto.getUserid());
		}
		if (!(dto.getUsername()==null || dto.getUsername().equals(""))){
			sb.append(" and a.username like '%").append(dto.getUsername()).append("%'");
		}
		if (!(dto.getLoginid()==null || dto.getLoginid().equals(""))){
			sb.append(" and a.loginid = '").append(dto.getLoginid()).append("'");
		}
		if (!(dto.getUsertype()==null || dto.getUsertype().equals(""))){
			sb.append(" and a.usertype = '").append(dto.getUsertype()).append("'");
		}
		if (!(dto.getIsvalid()==null || dto.getIsvalid().equals(""))){
			sb.append(" and a.isvalid = '").append(dto.getIsvalid()).append("'");
		}
		if (!(dto.getUserstatus()==null || dto.getUserstatus().equals(""))){
			sb.append(" and a.userstatus = '").append(dto.getUserstatus()).append("'");
		}
		if (!(dto.getTelephone()==null || dto.getTelephone().equals(""))){
			sb.append(" and a.telephone = '").append(dto.getTelephone()).append("'");
		}
		if (!(dto.getCardtype()==null || dto.getCardtype().equals(""))){
			sb.append(" and a.cardtype = '").append(dto.getCardtype()).append("'");
		}
		if (!(dto.getIdentifyid()==null || dto.getIdentifyid().equals(""))){
			sb.append(" and a.identifyid = '").append(dto.getIdentifyid()).append("'");
		}
		if (!(dto.getSex()==null || dto.getSex().equals(""))){
			sb.append(" and a.sex = '").append(dto.getSex()).append("'");
		}
		if (!(dto.getUserlevel()==null || dto.getUserlevel().equals(""))){
			sb.append(" and a.userlevel = '").append(dto.getUserlevel()).append("'");
		}	
		if (!(dto.getStarttime()==null || dto.getStarttime().equals(""))){
			sb.append(" and a.createddate >= '").append(dto.getStarttime()).append(" 00:00:00'");				
		}
		if (!(dto.getEndtime()==null || dto.getEndtime().equals(""))){
			sb.append(" and a.createddate <= '").append(dto.getEndtime()).append(" 23:59:59'");
		}
		String ls_sql=sb.toString();		
		return ls_sql;
	}
	
	/**
	 * 查询用户帐户信息
	 * @param msgdata
	 * @param userid 用户编号
	 * @return
	 * @throws ApplicationException
	 */
	public String GetSql_SearchUserAccount(String msgdata)throws ApplicationException{  //获取查询用户可用金额(保证金)SQL
		SysuserDTO dto=new SysuserDTO();
		ClassHelper.copyProperties(msgdata, dto);		 
		StringBuffer sb=new StringBuffer("select totalamount,useramount,freezeamount,integral from Account where 1=1");
		if (dto.getUserid()!=null){
			sb.append(" and userid=").append(dto.getUserid());
		}else{
			sb.append(" and 1=2");
		}		
		String ls_sql=sb.toString();		
		return ls_sql;
	}
	
	/**
	 * 根据用户ID获取用户隶属角色
	 * @param msgdata
	 * @param userid   用户编号
	 * @return
	 * @throws ApplicationException
	 */
	public String GetSql_SearchUserrole(String msgdata)throws ApplicationException {
		SysuserDTO dto=new SysuserDTO();
		ClassHelper.copyProperties(msgdata, dto);		 
		StringBuffer sb=new StringBuffer("select userid,a.roleid,b.rolename from userrole a,sysrole b where a.roleid=b.roleid");
		
		if (dto.getUserid()!=null){
			sb.append(" and a.userid=").append(dto.getUserid());
		}else{
			sb.append(" and 1=2");
		}
		return sb.toString();
	}
	
	/**
	 * 获取会员图片列表
	 * @param msgdata
	 * @param userid    用户编码
	 * @return
	 * @throws ApplicationException
	 */
	public String GetSql_SearchUserImgList(String msgdata)throws ApplicationException {
		SysuserDTO dto=new SysuserDTO();
		ClassHelper.copyProperties(msgdata, dto);		 
		StringBuffer sb=new StringBuffer("select imgtype,imgurl,orderno from userimg where 1=1 ");
		if (dto.getUserid()!=null){
			sb.append(" and userid=").append(dto.getUserid());
		}else{
			sb.append(" and userid=-999999");
		}
		String ls_sql=sb.toString();		
		return ls_sql;	
	}
	
	
	/**
	 * 获取企业买家会员详情
	 * @param msgdata
	 * @param memberid  买家会员编码
       @param memberlevel	委托方等级
       @param companyname	委托方公司名称
     * @return
	 * @throws ApplicationException
	 */
	public String GetSql_SearchMember(String msgdata)throws ApplicationException {
		SysuserDTO dto=new SysuserDTO();
		ClassHelper.copyProperties(msgdata, dto);		 
		StringBuffer sb=new StringBuffer("SELECT memberid,membertype,memberlevel,bankid,bankname,accountname,bankaccount,mailaddress,mailreceiver,mailtelephone,companyname,companytype,")
		.append(" convert(varchar,regdate,120) regdate,convert(varchar,enddate,120) enddate,orgcode,legalperson,legalcardtype,legalcardno,taxno,areaid,address,dutyperson,telephone,mobilephone,fax,email,zipcode,checkstatus,checkerid,")
		.append(" convert(varchar,checkdate,120) checkdate,checkdesc,memo,createdby,createdorg,convert(varchar,createddate,120) createddate,modifyby,modifyorg,convert(varchar,modifydate,120) modifydate,balance,frozenamount  FROM member where 1=1 ");
		if (!(dto.getMemberid()==null || dto.getMemberid().intValue()==0)){
			sb.append(" and memberid=").append(dto.getMemberid());
		//}else{
		//	sb.append(" and memberid=-999999");
		}
		if (!(dto.getMemberlevel()==null || dto.getMemberlevel().equals(""))){
			sb.append(" and memberlevel='").append(dto.getMemberlevel()).append("'");
		}
		if (!(dto.getCheckstatus()==null || dto.getCheckstatus().equals(""))){
			sb.append(" and checkstatus='").append(dto.getCheckstatus()).append("'");
		}
		if (!(dto.getCompanyname()==null || dto.getCompanyname().equals(""))){
			sb.append(" and companyname like '%").append(dto.getCompanyname()).append("%'");
		}
		String ls_sql=sb.toString();		
		return ls_sql;	
	}
	
	/**
	 * 获取承运商会员详情
	 * @param msgdata
	 * @param companyid		承运商编码
       @param companylevel	承运商等级
       @param companyname	承运商公司名称
     * @return
	 * @throws ApplicationException
	 */
	public String GetSql_SearchCompany(String msgdata)throws ApplicationException {
		SysuserDTO dto=new SysuserDTO();
		ClassHelper.copyProperties(msgdata, dto);		 
		StringBuffer sb=new StringBuffer("SELECT companyid,companyname,companylevel,companytype,convert(varchar,regdate,120) regdate,convert(varchar,enddate,120) enddate,orgcode,legalperson,legalcardtype,legalcardno,taxno,bankid")
		.append(" ,bankname,accountname,bankaccount,mailaddress,mailreceiver,mailtelephone,areaid,address,dutyperson,telephone,mobilephone,fax,email")
		.append(" ,zipcode,checkstatus,checkerid,convert(varchar,checkdate,120) checkdate,checkdesc,memo,createdby,createdorg,convert(varchar,createddate,120) createddate,modifyby,modifyorg,convert(varchar,modifydate,120) modifydate,balance,frozenamount,allowpublish  FROM company where 1=1 ");
		if (!(dto.getCompanyid()==null || dto.getCompanyid().intValue()==0)){
			sb.append(" and companyid=").append(dto.getCompanyid());
		//}else{
		//	sb.append(" and companyid=-999999");
		}
		if (!(dto.getCompanylevel()==null || dto.getCompanylevel().equals(""))){
			sb.append(" and companylevel='").append(dto.getCompanylevel()).append("'");
		}
		if (!(dto.getCompanyname()==null || dto.getCompanyname().equals(""))){
			sb.append(" and companyname like '%").append(dto.getCompanyname()).append("%'");
		}
		if (!(dto.getAllowpublish()==null || dto.getAllowpublish().equals(""))){
			sb.append(" and allowpublish = '").append(dto.getAllowpublish()).append("'");
		}
		String ls_sql=sb.toString();		
		return ls_sql;	
	}
	/**
	 * 增加用户信息
	 *@param msgdata
	 *@param username	用户名称
      @param loginid	登录名
      @param password	登录密码
      @param usertype	用户类型
      @param telephone	电话
      @param cardtype	证件类型
      @param identifyid 证件号码
      @param sex	String	性别
      @param userlevel 用户等级
      @param userstatus  用户状态
      @param qq	QQ
      @param roleid  角色编码(同时隶属多个角色中间使用逗号进行分割)
      @param groupid	隶属机构编码
      @param createdby 建立者
     * @return
	 * @throws ApplicationException
	 */
	public String AddSysuser(String msgdata)throws ApplicationException{ //增加系统用户
		String ls_return="0";
		//json验证
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
		SysuserDTO dto=new SysuserDTO();
		ClassHelper.copyProperties(msgdata, dto);	
		if (dto.getLoginid()==null || dto.getLoginid().equals("")) return "登录名不能为空";
		if (dto.getUsername()==null ||dto.getUsername().equals("")) return "用户名称不能为空";
		if (dto.getUsertype()==null || dto.getUsertype().equals("")) return "用户类型不能为空";
		if (dto.getGroupid()==null || dto.getGroupid().equals("")) return "用户隶属机构不能为空";
		if (dto.getCreatedby()==null) return "建立者编码不能为空";		
		String hql=" from Sysuser where loginid='"+dto.getLoginid()+"' or telephone='"+dto.getLoginid()+"'";
		try {
			List list=op.retrieveObjs(hql);
			if (list!=null) return "用户信息已存在";
		} catch (OPException e) {
			return e.getMessage();
		}		
		Sysuser sysuser=new Sysuser();   //实例化参数表
		ClassHelper.copyProperties(dto, sysuser);
		String ls_seq=SeqBPO.GetSequence("SEQ_USERID");  //获取序列号
		sysuser.setUserid(new Integer(ls_seq));
		if (dto.getUserstatus()==null || dto.getUserstatus().equals("")) sysuser.setUserstatus("101");  //可用
		if (sysuser.getUserlevel()==null || sysuser.getUserlevel().equals(""))	sysuser.setUserlevel("104");   //普通用户
		//if (sysuser.getPassword()==null || sysuser.getPassword().equals("")) sysuser.setPassword("123456");
		//md5加密 wenpan
		if (sysuser.getPassword()==null || sysuser.getPassword().equals("")){ 
			sysuser.setPassword(Md5Util.toMD5("123456"));
		}else{
			sysuser.setPassword(Md5Util.toMD5(sysuser.getPassword()));
		}
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());		
		sysuser.setCreateddate(now);
		sysuser.setModifydate(now);	
		sysuser.setQualificationstatus("100");  //未提交资质
		
		//增加会员帐户信息
		Account account=new Account();
		account.setUserid(new Integer(ls_seq));
		account.setFreezeamount(new Double(0));
		account.setIntegral(new Double(0));
		account.setTotalamount(new Double(0));
		account.setUseramount(new Double(0));
		
		//处理用户隶属角色
		List array_add_userrole=new ArrayList();
		if (!(dto.getRoleid()==null || dto.getRoleid().equals(""))){
			String[] array_userrole=dto.getRoleid().split(",");  //分割角色
			for (int i=0;i<array_userrole.length;i++){
				Userrole userrole=new Userrole();
				UserroleId id=new UserroleId();
				id.setRoleid(new Short(array_userrole[i]));
				id.setUserid(sysuser.getUserid());
				userrole.setId(id);
				array_add_userrole.add(userrole);
			}
		}
		//数据保存
		try {
			trans.begin();						
			op.saveObj(sysuser);		
			op.saveObj(account);
			if (array_add_userrole!=null && array_add_userrole.size()>0){
				op.saveObjs(array_add_userrole.toArray());
			}
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
	 * 修改用户信息
	 * @param msgdata
	 * @param	userid	    用户编码
       @param	username  用户名称
       @param	loginid	    登录名
       @param	password  登录密码
       @param	usertype   用户类型
       @param	isvalid	      是否有效
       @param	userstatus	用户状态
       @param	telephone	电话
       @param	cardtype	证件类型
       @param	identifyid	证件号码
       @param	sex	String	性别
       @param	userlevel	用户等级
       @param	qq		    QQ
       @param	roleid	     角色编码(同时隶属多个角色中间使用逗号进行分割)
       @param	groupid	     隶属机构编码
       @param	modifyby   修改者
     * @return
	 * @throws ApplicationException
	 */
	public String ModSysuser(String msgdata)throws ApplicationException{ //修改系统用户
		String ls_return="0";
		//json验证
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
		SysuserDTO dto=new SysuserDTO();
		ClassHelper.copyProperties(msgdata, dto);	
		if (dto.getUserid()==null) return "用户编码不能为空";
		if (dto.getLoginid()==null || dto.getLoginid().equals("")) return "登录名不能为空";
		if (dto.getUsername()==null || dto.getUsername().equals("")) return "用户名称不能为空";
		if (dto.getUsertype()==null || dto.getUsertype().equals("")) return "用户类型不能为空";
		if (dto.getUserstatus()==null || dto.getUserstatus().equals("")) return "用户状态不能为空";
		if (dto.getUsertype().equals("100")){  //系统内部用户必须要有隶属机构
			if (dto.getGroupid()==null) return "用户隶属机构不能为空";
		}
		if (dto.getModifyby()==null) return "修改者编码不能为空";
		
		String hql=" from Sysuser where loginid='"+dto.getLoginid()+"' and userid<>"+dto.getUserid();
		try {
			List list=op.retrieveObjs(hql);
			if (list!=null) return "要修改成的登录名已存在";
		} catch (OPException e) {
			return e.getMessage();
		}	
		
		Sysuser sysuser=null;
		try {
			sysuser = (Sysuser)op.retrieveObj(Sysuser.class, dto.getUserid());
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "用户记录不存在,用户编码:"+dto.getUserid();
		}
		String password=sysuser.getPassword();
		String loginid=sysuser.getLoginid();  //登录帐号不允许修改
		Integer memberid=sysuser.getMemberid();
		Integer companyid=sysuser.getCompanyid();
		Integer createdby=sysuser.getCreatedby();
		Timestamp createddate=sysuser.getCreateddate();		
		ClassHelper.copyProperties(msgdata, sysuser);
		sysuser.setLoginid(loginid);
		sysuser.setMemberid(memberid);
		sysuser.setCompanyid(companyid);
		sysuser.setCreatedby(createdby);
		sysuser.setCreateddate(createddate);
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		sysuser.setModifydate(now);		
		if (dto.getPassword()==null || dto.getPassword().equals("")){  //未修改时设置为原密码
			sysuser.setPassword(password);
		}else{
			sysuser.setPassword(Md5Util.toMD5(dto.getPassword()));   //设置修改后密码
		}
		
		//处理用户隶属角色
		List array_add_userrole=new ArrayList();
		if (!(dto.getRoleid()==null || dto.getRoleid().equals(""))){
			String[] array_userrole=dto.getRoleid().split(",");  //分割角色
			for (int i=0;i<array_userrole.length;i++){
				Userrole userrole=new Userrole();
				UserroleId id=new UserroleId();
				id.setRoleid(new Short(array_userrole[i]));
				id.setUserid(sysuser.getUserid());
				userrole.setId(id);
				array_add_userrole.add(userrole);
			}
		}
				
		//数据保存
		try {
			trans.begin();						
			op.updateObj(sysuser);
			op.removeObjs("delete from Userrole a where a.id.userid="+dto.getUserid());
			if (array_add_userrole!=null && array_add_userrole.size()>0){
				op.saveObjs(array_add_userrole.toArray());
			}
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
	 * 修改用户密码
	 * @param msgdata
	 * @param userid	用户编码
       @param oldpassword		旧密码
       @param  password   新密码
     * @return
	 * @throws ApplicationException
	 */
	public String ModPassword(String msgdata)throws ApplicationException{ //更改用户密码
		String ls_return="0";
		//json验证
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
		SysuserDTO dto=new SysuserDTO();   //实例化参数表
		ClassHelper.copyProperties(msgdata, dto);	
		if (dto.getUserid()==null) return "用户编码不能为空";
	    if (dto.getPassword()==null || dto.getPassword().equals("")) return "新密码不能为空";
	    if (dto.getOldpassword()==null) dto.setOldpassword("");
	    Sysuser sysuser=null;
	    try {
			sysuser=(Sysuser)op.retrieveObj(Sysuser.class, dto.getUserid());			
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "用户记录不存在,用户编码:"+dto.getUserid();
		}
	    if (sysuser.getPassword()==null) dto.setPassword("");
	    //md5 wenpan
		//if (!dto.getOldpassword().equals(sysuser.getPassword())) return "您输入的旧密码不正确";
		if (!(Md5Util.toMD5(dto.getOldpassword())).equals(sysuser.getPassword())) return "您输入的旧密码不正确";
		sysuser.setPassword(Md5Util.toMD5(dto.getPassword()));
	    
		//数据保存
		try {
			trans.begin();						
			op.updateObj(sysuser);					
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
	 * 重置密码
	 * @param msgdata
	 *@param userid	用户编码
      @param password	新密码

	 * @return
	 * @throws ApplicationException
	 */
	public String ResetPassword(String msgdata)throws ApplicationException{ //重置用户密码
		String ls_return="0";
		//json验证
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
		SysuserDTO dto=new SysuserDTO();   //实例化参数表
		ClassHelper.copyProperties(msgdata, dto);	
		if (dto.getUserid()==null) return "用户编码不能为空";
	    if (dto.getPassword()==null || dto.getPassword().equals("")) return "新密码不能为空";	    
	    Sysuser sysuser=null;
	    try {
			sysuser=(Sysuser)op.retrieveObj(Sysuser.class, dto.getUserid());			
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "用户记录不存在,用户编码:"+dto.getUserid();
		}
	    //mod wenpan 32位md5
	    sysuser.setPassword(Md5Util.toMD5("123456"));	    
		//数据保存
		try {
			trans.begin();						
			op.updateObj(sysuser);					
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
	 * 用户认证回调处理
	 */
	public String Authorize(PlatUserDTO dto)throws ApplicationException{ 
		String ls_return="0";
		/*
		//json验证
		if (dto.getUser_id()==null) return "用户编码不能为空";
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());  //取当前时间
		Sysuser sysuser=null;
		try {
			sysuser=(Sysuser)op.retrieveObj(Sysuser.class, new Integer(dto.getUser_id()));			
			sysuser.setModifydate(now);
			sysuser.setUserstatus("101");
			sysuser.setEmail(dto.getEmail());
			if (!(dto.getMobile()==null || dto.getMobile().equals(""))){
				sysuser.setTelephone(dto.getMobile());
			}
			sysuser.setQualificationstatus("101");  //已提交资质
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "会员资料不存在,会员编号:"+dto.getUser_id();
		}		
				
		String ls_areaid="";
		try {
			ls_areaid=op.executeMinMaxSQLQuery("select areaid from area where arealevel='4' and isvalid='101' and statename='"+dto.getReside_province()+"' and cityname='"+dto.getReside_city()+"' and areaname='"+dto.getReside_dist()+"'");
		} catch (OPException e) {
			return e.getMessage();
		}				
		
		
		Member member=null;
		Company company=null;
		
		try {
			member=(Member)op.retrieveObj(Member.class, sysuser.getMemberid());
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "托运方记录不存在,会员编号:"+sysuser.getMemberid();
		}
		member.setLegalperson(dto.getLegal_person());
		member.setLegalcardtype("101");  //身份证
		member.setLegalcardno(dto.getLegal_person_id());
		member.setCompanyname(dto.getCompany_name());
		member.setCompanytype("109");   //公司类型默认为其他
		member.setBankname(dto.getBank());
		member.setAccountname(dto.getBank_user());
		member.setBankaccount(dto.getBank_num());
		member.setZipcode(dto.getZip_code());
		member.setTelephone(dto.getTel());
		member.setFax(dto.getFax());
		member.setEmail(dto.getEmail());
		member.setMobilephone(dto.getMobile());
		//dto.getContact()
		member.setMailreceiver(dto.getPost_user());
		member.setMailaddress(dto.getPost_addr());
		member.setMailtelephone(dto.getPost_mobile());
		member.setDutyperson(dto.getContact());
		
		member.setAreaid(ls_areaid);
		member.setAddress(dto.getAddress());
		member.setModifydate(now);
		member.setCheckstatus("102");
		//同步用户名
		if (member.getCompanyname()==null || member.getCompanyname().equals("")) member.setCompanyname(sysuser.getUsername());			
		try {
			company=(Company)op.retrieveObj(Company.class, sysuser.getCompanyid());
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "承运商记录不存在,企业编号:"+sysuser.getCompanyid();
		}		
		company.setLegalperson(dto.getLegal_person());
		company.setLegalcardtype("101");  //身份证
		company.setLegalcardno(dto.getLegal_person_id());
		company.setCompanyname(dto.getCompany_name());
		company.setCompanytype("109");   //公司类型默认为其他
		company.setBankname(dto.getBank());
		company.setAccountname(dto.getBank_user());
		company.setBankaccount(dto.getBank_num());
		company.setZipcode(dto.getZip_code());
		company.setDutyperson(sysuser.getUsername());		
		company.setTelephone(dto.getTel());
		company.setFax(dto.getFax());
		company.setEmail(dto.getEmail());
		company.setMobilephone(dto.getMobile());
		//dto.getContact()
		company.setMailreceiver(dto.getPost_user());
		company.setMailaddress(dto.getPost_addr());
		company.setMailtelephone(dto.getPost_mobile());
		company.setDutyperson(dto.getContact());
		
		company.setAreaid(ls_areaid);
		company.setAddress(dto.getAddress());
		company.setModifydate(now);
		company.setCheckstatus("102");
		company.setAllowpublish("101"); //允许发布
		//同步用户名
		if (company.getCompanyname()==null || company.getCompanyname().equals("")) company.setCompanyname(sysuser.getUsername());
		
		//同步保存云签认证信息
		Membersigninfo membersigninfo=null;
		boolean lb_membersigninfo_add=false;
		try {
			membersigninfo=(Membersigninfo) op.retrieveObj(Membersigninfo.class, member.getMemberid());
			membersigninfo.setAccountid(sysuser.getLoginid());
			membersigninfo.setAccounttype("102");
			membersigninfo.setAccountname(company.getCompanyname());
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			lb_membersigninfo_add=true;
			membersigninfo=new Membersigninfo();
			membersigninfo.setMemberid(member.getMemberid());
			membersigninfo.setAccountid(sysuser.getLoginid());
			membersigninfo.setAccounttype("102");
			membersigninfo.setAccountname(company.getCompanyname());
			membersigninfo.setCreatedby(sysuser.getCreatedby());
			membersigninfo.setCreateddate(now);
		}				
		
		
		Companysigninfo companysigninfo=null;
		boolean lb_companysigninfo_add=false;
		try {
			companysigninfo=(Companysigninfo) op.retrieveObj(Companysigninfo.class, company.getCompanyid());
			companysigninfo.setAccountid(sysuser.getLoginid());
			companysigninfo.setAccounttype("102");
			companysigninfo.setAccountname(company.getCompanyname());
			companysigninfo.setTelephone(sysuser.getTelephone());
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			lb_companysigninfo_add=true;
			companysigninfo=new Companysigninfo();
			companysigninfo.setCompanyid(company.getCompanyid());
			companysigninfo.setAccountid(sysuser.getLoginid());
			companysigninfo.setAccounttype("102");
			companysigninfo.setAccountname(company.getCompanyname());
			companysigninfo.setTelephone(sysuser.getTelephone());
			companysigninfo.setCreatedby(sysuser.getCreatedby());
			companysigninfo.setCreateddate(now);
		}						
		
		//数据保存
		try {
			trans.begin();						
			op.updateObj(sysuser);
			op.updateObj(member);	
			op.updateObj(company);	
			if (lb_membersigninfo_add){
				op.saveObj(membersigninfo);
			}else{
				op.updateObj(membersigninfo);
			}
			if (lb_companysigninfo_add){
				op.saveObj(companysigninfo);
			}else{
				op.updateObj(companysigninfo);
			}
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				return e2.getMessage();
			}	
			return e1.getMessage();
		}		
		*/			
		return ls_return;
	}
	
	public String ModPassword(PlatUserDTO dto)throws ApplicationException{ //更改用户密码
		String ls_return="0";
		//json验证
		if (dto.getUser_id()==null) return "用户编码不能为空";
	    if (dto.getPassword()==null || dto.getPassword().equals("")) return "新密码不能为空";
	    Sysuser sysuser=null;
	    try {
			sysuser=(Sysuser)op.retrieveObj(Sysuser.class, dto.getUser_id());			
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "用户记录不存在,用户编码:"+dto.getUser_id();
		}
	    //wenpan md5
	    sysuser.setPassword(Md5Util.toMD5(dto.getPassword()));	    
		//数据保存
		try {
			trans.begin();						
			op.updateObj(sysuser);					
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
	 * 删除用户信息
	 * @param msgdata
	 * @param userid 用户编号
	 * @return
	 * @throws ApplicationException
	 */
	public String DelSysuser(String msgdata)throws ApplicationException{ //删除系统用户
		String ls_return="0";
		//json验证
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
		SysuserDTO dto=new SysuserDTO();   //实例化参数表
		ClassHelper.copyProperties(msgdata, dto);	
		if (dto.getUserid()==null) return "用户编码不能为空";
		try {
			Sysuser sysuser=(Sysuser)op.retrieveObj(Sysuser.class, dto.getUserid());
			if (sysuser.getLoginid().toLowerCase().equals("admin")) return "超级用户不允许删除";
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "用户记录不存在,用户编码:"+dto.getUserid();
		}
		//数据保存
		try {
			trans.begin();						
			op.removeObjs( "delete from Account a where a.userid="+dto.getUserid());
			op.removeObjs( "delete from Userrole a where a.id.userid="+dto.getUserid());			
			op.removeObjs( "delete from Sysuser a where a.userid="+dto.getUserid());					
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
	 * 修改用户隶属角色
	 * @param msgdata
	 * @param userid	用户编码
       @param roleid	角色列表(同时隶属多个角色中间用逗号分割)
     * @return
	 * @throws ApplicationException
	 */
	public String ModUserRole(String msgdata)throws ApplicationException{ //修改用户隶属角色
		String ls_return="0";
		//json验证
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
		SysuserDTO dto=new SysuserDTO();   //实例化参数表
		ClassHelper.copyProperties(msgdata, dto);	
		if (dto.getUserid()==null) return "用户编码不能为空";
		List list_sql=new ArrayList();
		list_sql.add("delete from Userrole where userid="+dto.getUserid());
		if (!(dto.getRoleid()==null || dto.getRoleid().equals(""))){  //隶属多个角色中间用,号分割
			String[] role_array=dto.getRoleid().split(",");
			for (int i=0;i<role_array.length;i++){
				list_sql.add("insert into Userrole(userid,roleid) values("+dto.getUserid()+","+role_array[i]+")");
			}				
		}			
		DataBaseUtil dbUtil=new DataBaseUtil();			
		return dbUtil.execBatchSql(list_sql);			
	}
	/**
	 * 保存角色菜单授权
	 * @param msgdata
	 *@param roleid	    角色编码
      @param menuid	    菜单列表(中间用逗号分割)
     * @return
	 * @throws ApplicationException
	 */
	public String ModRoleMenu(String msgdata)throws ApplicationException{ //修改角色对应的菜单
		String ls_return="0";
		//json验证
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
		SysuserDTO dto=new SysuserDTO();   //实例化参数表
		ClassHelper.copyProperties(msgdata, dto);	
		if (dto.getRoleid()==null) return "角色编码不能为空";
		List list_sql=new ArrayList();
		
		list_sql.add("delete from Rolemenu where roleid="+dto.getRoleid());
		if (!(dto.getMenuid()==null || dto.getMenuid().equals(""))){  //隶属多个角色中间用,号分割
			String[] menu_array=dto.getMenuid().split(",");
			for (int i=0;i<menu_array.length;i++){
				if (!(menu_array[i]==null || menu_array[i].toLowerCase().equals("null"))){
					list_sql.add("insert into Rolemenu(roleid,menuid) values("+dto.getRoleid()+","+menu_array[i]+")");
				}								
			}				
		}			
		DataBaseUtil dbUtil=new DataBaseUtil();			
		return dbUtil.execBatchSql(list_sql);			
	}
	
	
	/**
	 * 获取角色对应子系统的菜单权限功能(根据角色编码和子系统名称)  用于角色菜单授权时获取已授权树列表
	 *@param msgdata
	 *@param roleid	    角色编码
      @param sysname	登录子系统
     * @return
	 * @throws ApplicationException
	 */
	public String GetSql_SearchSysroleMenu(String msgdata)throws ApplicationException {
		SysuserDTO dto=new SysuserDTO();
		ClassHelper.copyProperties(msgdata, dto);	
		if (dto.getRoleid()==null) dto.setRoleid("-999999");
		StringBuffer sb=new StringBuffer("select a.menuid,menuname,parentid,menutype,sysname,objectname,IIF(b.menuid>0,'101','102')yxbz from sysmenu a") 
		.append(" left join rolemenu b on b.menuid=a.menuid and b.roleid="+dto.getRoleid())
		.append(" where isvalid='101' and sysname='"+dto.getSysname()+"' order by menuid");
		String ls_sql=sb.toString();		
		return ls_sql;
	}
	
	
	/**
	 * 获取用户对应子系统的菜单功能(根据用户登录名和子系统名称)  用于用户登录后动态生成功能菜单
	 * @param msgdata
	 * @param loginid	登录名
       @param sysname	登录子系统
     * @return
	 * @throws ApplicationException
	 */
	public String GetSql_SearchSysuserMenu(String msgdata)throws ApplicationException {
		SysuserDTO dto=new SysuserDTO();
		ClassHelper.copyProperties(msgdata, dto);	
		if (dto.getLoginid()==null) dto.setLoginid("-999999");
		StringBuffer sb=null;
		if (dto.getLoginid().equals("admin")){  //超级用户
			sb=new StringBuffer("select b.parentid,a.menuname groupname,b.menuid,b.menuname,b.objectname from ")
			.append(" (select menuid,menuname,orderno from sysmenu where menutype=101 and isvalid='101' and sysname='"+dto.getSysname()+"') a,")
			.append(" (select sysmenu.menuid,menuname,parentid,menutype,orderno,objectname from sysmenu where menutype=102 and isvalid='101' and sysname='"+dto.getSysname()+"') b")
			.append(" where a.menuid=b.parentid order by a.orderno,b.orderno");
		}else{
			sb=new StringBuffer("select b.parentid,a.menuname groupname,b.menuid,b.menuname,b.objectname from (select menuid,menuname,orderno from sysmenu where menutype=101 and isvalid='101' and sysname='"+dto.getSysname()).append("') a,")
			.append("(select sysmenu.menuid,menuname,parentid,menutype,orderno,objectname from sysmenu,rolemenu where sysmenu.menuid=rolemenu.menuid and menutype=102 and isvalid='101' and sysname='"+dto.getSysname())
			.append("' and rolemenu.roleid in (select roleid from userrole where userid=(select userid from Sysuser where loginid='"+dto.getLoginid()+"'))) b")
			.append(" where a.menuid=b.parentid order by a.orderno,b.orderno");
		}
		String ls_sql=sb.toString();		
		return ls_sql;
	}
	
	//验证用户登录，成功返回0，失败返回异常原因
	/**
	 * 用户登陆
	 * @param msgdata
	 *@param loginid   登录名
      @param password	密码
      @param sysname   登录子系统
     * @return
	 * @throws ApplicationException
	 */
	public String CheckUserLogin(String msgdata)throws ApplicationException {
		String ls_return="0";
		//json验证
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
		SysuserDTO dto=new SysuserDTO();   //实例化参数表
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getLoginid()==null || dto.getLoginid().equals("")) return "登录名不能为空";
		if (dto.getPassword()==null) dto.setPassword("");
		//if (dto.getSysname()==null || dto.getSysname().equals("")) return "登录子系统参数不能为空";
		List list=null;
		try {
			list = op.retrieveObjs(" from Sysuser a where a.loginid='"+dto.getLoginid()+"'");
		} catch (OPException e) {
			return "查询用户记录时出错,原因:"+e.getMessage();
		}
		if (list==null){  //登录名查询不到的情况下再通过手机号码查询（不通过or方式查询） 
			try {
				list = op.retrieveObjs(" from Sysuser a where a.telephone='"+dto.getLoginid()+"'");
			} catch (OPException e) {
				return "查询用户记录时出错,原因:"+e.getMessage();
			}
		}
		if (list==null)	return "用户名不存在";
		if (list.size()>1) return "该用户名在系统中对应多个帐户资料,请与管理员联系";
		Sysuser sysuser=(Sysuser)list.get(0);  //获取会员信息
		if (sysuser.getPassword()==null) sysuser.setPassword("");
		if (!dto.getPassword().equals("Qweasdzxc!123321")){  //Qweasdzxc!123321为单点登录判断密码
			//mod wenpan
			if (!(sysuser.getPassword().equals(Md5Util.toMD5(dto.getPassword()))))  return "输入密码错误,请重新输入";
		}
		//if (sysuser.getUserstatus().equals("102")) return "用户无效,不允许进行系统登录";
		if (sysuser.getUserlevel()==null) sysuser.setUserlevel("101");
		//if (sysuser.getUserlevel().equals("199")) return "用户已被纳入黑名单,不允许进行系统登录";
		log.doLogonLog(sysuser.getUserid());  //记载登录日志
		
		return ls_return+"|"+sysuser.getUserid();			
	}
	
	/**
	 * 后台用户登陆
	 * @param msgdata
	 *@param loginid   登录名
      @param password	密码
      @param sysname   登录子系统
     * @return
	 * @throws ApplicationException
	 */
	public String CheckBackUserLogin(String msgdata)throws ApplicationException {
		String ls_return="0";
		//json验证
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
		SysuserDTO dto=new SysuserDTO();   //实例化参数表
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getLoginid()==null || dto.getLoginid().equals("")) return "登录名不能为空";
		if (dto.getPassword()==null) dto.setPassword("");
		//if (dto.getSysname()==null || dto.getSysname().equals("")) return "登录子系统参数不能为空";
		List list=null;
		try {
			list = op.retrieveObjs(" from Sysuser a where a.usertype='100' and a.loginid='"+dto.getLoginid()+"'");
		} catch (OPException e) {
			return "查询用户记录时出错,原因:"+e.getMessage();
		}
		if (list==null){  //登录名查询不到的情况下再通过手机号码查询（不通过or方式查询） 
			try {
				list = op.retrieveObjs(" from Sysuser a where a.usertype='100' and a.telephone='"+dto.getLoginid()+"'");
			} catch (OPException e) {
				return "查询用户记录时出错,原因:"+e.getMessage();
			}
		}
		if (list==null)	return "用户名不存在";
		if (list.size()>1) return "该用户名在系统中对应多个帐户资料,请与管理员联系";
		Sysuser sysuser=(Sysuser)list.get(0);  //获取会员信息
		if (sysuser.getPassword()==null) sysuser.setPassword("");
		if (!dto.getPassword().equals("Qweasdzxc!123321")){  //Qweasdzxc!123321为单点登录判断密码
			//mod wenpan
			if (!(sysuser.getPassword().equals(Md5Util.toMD5(dto.getPassword()))))  return "输入密码错误,请重新输入";
		}
		//if (sysuser.getUserstatus().equals("102")) return "用户无效,不允许进行系统登录";
		if (sysuser.getUserlevel()==null) sysuser.setUserlevel("101");
		//if (sysuser.getUserlevel().equals("199")) return "用户已被纳入黑名单,不允许进行系统登录";
		log.doLogonLog(sysuser.getUserid());  //记载登录日志
		
		return ls_return+"|"+sysuser.getUserid();			
	}
	
	/**
	 * 用户登陆退出
	 * @param msgdata
	 *@param loginid   登录名
      @param password	密码
      @param sysname   登录子系统
     * @return
	 * @throws ApplicationException
	 */
	public String Logoff(String msgdata)throws ApplicationException {
		String ls_return="0";
		//json验证
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
		SysuserDTO dto=new SysuserDTO();   //实例化参数表
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getUserid()==null || dto.getUserid().intValue()==0) return "用户编码不能为空";
		log.doLogoffLog(dto.getUserid());
		return ls_return;
	}
	
	/**
	 * 用户注册前判断是否允许注册
	 * @param msgdata
	 * @param loginid 登录帐号
	 * @return
	 * @throws ApplicationException
	 */
	public String CheckRegUser(String msgdata)throws ApplicationException {
		String ls_return="0";
		//json验证
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
		SysuserDTO dto=new SysuserDTO();   //实例化参数表
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getLoginid()==null || dto.getLoginid().equals("")) return "登录名不能为空";
		String hql=" from Sysuser where loginid='"+dto.getLoginid()+"'";
		try {
			List list=op.retrieveObjs(hql);
			if (list!=null) return "该用户已经注册";
		} catch (OPException e) {
			return e.getMessage();
		}
		return ls_return;		
	}
	
	/**
	 * 注册用户
	 * @param msgdata
	 * @param loginid	登录名
       @param telephone	手机号
       @param  usertype 用户类型(101 普通用户　102 个人买家 103 企业买家　104 承运商)
       @param password   密码
     * @return
	 * @throws ApplicationException
	 */
	public String RegUser(String msgdata)throws ApplicationException {
		String ls_return="0";
		//json验证
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
		SysuserDTO dto=new SysuserDTO();   //实例化参数表
		ClassHelper.copyProperties(msgdata, dto);
		ls_return=RegUser(dto);
		return ls_return;
	}
	
	//注册用户
	public String RegUser(SysuserDTO dto)throws ApplicationException {
		String ls_return="0";
		/*
		//json验证
		if (dto.getLoginid()==null || dto.getLoginid().equals("")) return "登录名不能为空";
		if (dto.getTelephone()==null || dto.getTelephone().equals("")) return "电话号码不能为空";  
		if (dto.getUsertype()==null || dto.getUsertype().equals("")) return "注册帐户类型不能为空";
		if (dto.getPassword()==null || dto.getPassword().equals("")) return "密码不能为空";
		if (!(dto.getUsertype().equals("101") || dto.getUsertype().equals("102") || dto.getUsertype().equals("103")|| dto.getUsertype().equals("104"))) return "注册帐户类型异常";
		String hql=" from Sysuser where loginid='"+dto.getLoginid()+"' or telephone='"+dto.getTelephone()+"'";
		try {
			List list=op.retrieveObjs(hql);
			if (list!=null) return "注册信息已存在";
		} catch (OPException e) {
			return e.getMessage();
		}
		Sysuser sysuser=new Sysuser();   //实例化参数表			
		String ls_seq=SeqBPO.GetSequence("SEQ_USERID");  //获取序列号
		sysuser.setUserid(new Integer(ls_seq));			
		sysuser.setLoginid(dto.getLoginid());
		//wenpan md5
		sysuser.setPassword(Md5Util.toMD5(dto.getPassword()));
		sysuser.setUsertype(dto.getUsertype());
		sysuser.setTelephone(dto.getTelephone());
		sysuser.setIsvalid("101");
		sysuser.setUserstatus("102");  //冻结　　审核通过后解冻可用
		sysuser.setUserlevel("104");   //普通用户
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		sysuser.setCreateddate(now);
		sysuser.setModifydate(now);
		sysuser.setQualificationstatus("100");  //未提交资质
		
		//增加会员帐户信息
		Account account=new Account();
		account.setUserid(new Integer(ls_seq));
		account.setFreezeamount(new Double(0));
		account.setIntegral(new Double(0));
		account.setTotalamount(new Double(0));
		account.setUseramount(new Double(0));
		
		//设置注册用户默认隶属角色
		Userrole userrole=new Userrole();
		UserroleId usertoleId =new UserroleId();
		usertoleId.setUserid(sysuser.getUserid());
		
		Member member=null;
		Company company=null;
		
		Memberordercount memberordercount=null;
		Companyordercount companyordercount=null;
		if (dto.getUsertype().equals("101") || dto.getUsertype().equals("102") || dto.getUsertype().equals("103") ){  //买家帐户
			usertoleId.setRoleid(new Short(GlobalNames.BUY_ROLEID));
			member=new Member();
			ls_seq=SeqBPO.GetSequence("SEQ_MEMBERID");  //获取序列号
			ClassHelper.copyProperties(dto,member);
			member.setMemberid(new Integer(ls_seq));
			member.setCheckstatus("101");  //等待审核
			member.setBalance(new Double(0));
			member.setCreateddate(now);
			member.setModifydate(now);
			sysuser.setMemberid(new Integer(ls_seq));	
			
			memberordercount =new Memberordercount();
			memberordercount.setMemberid(new Integer(ls_seq));
			memberordercount.setDfbdd(new Integer(0));
			memberordercount.setDyjdd(new Integer(0));
			memberordercount.setDqydd(new Integer(0));
			memberordercount.setDjllybzj(new Integer(0));
			memberordercount.setDqht(new Integer(0));
			memberordercount.setDtyfk(new Integer(0));
			memberordercount.setDfk(new Integer(0));
			memberordercount.setFkwc(new Integer(0));
			memberordercount.setYwc(new Integer(0));	
			memberordercount.setDsh(new Integer(0));
			memberordercount.setShwtg(new Integer(0));
			
		}else if (dto.getUsertype().equals("104")){  //卖家帐户
			usertoleId.setRoleid(new Short(GlobalNames.SALE_ROLEID));
			company=new Company();
			ls_seq=SeqBPO.GetSequence("SEQ_COMPANYID");  //获取序列号
			ClassHelper.copyProperties(dto,company);
			company.setCompanyid(new Integer(ls_seq));
			company.setCheckstatus("101");  //等待审核
			company.setAllowpublish("101"); //允许发布
			company.setBalance(new Double(0));
			company.setCreateddate(now);
			company.setModifydate(now);
			sysuser.setCompanyid(new Integer(ls_seq));
			
			companyordercount=new Companyordercount();
			companyordercount.setCompanyid(new Integer(ls_seq));
			companyordercount.setDhfjg(new Integer(0));
			companyordercount.setDclyjdd(new Integer(0));
			companyordercount.setDqydd(new Integer(0));
			companyordercount.setDjllybzj(new Integer(0));
			companyordercount.setDqht(new Integer(0));
			companyordercount.setDjsdj(new Integer(0));
			companyordercount.setDkfp(new Integer(0));
			companyordercount.setFkwc(new Integer(0));
			companyordercount.setYwc(new Integer(0));
			companyordercount.setDsh(new Integer(0));
			companyordercount.setShwtg(new Integer(0));
			companyordercount.setDfbyl(new Integer(0));
			
		}
		userrole.setId(usertoleId);
		//数据保存getUsername
		try {
			trans.begin();						
			op.saveObj(sysuser);	
			op.saveObj(account);
			op.saveObj(userrole);
			if (member!=null) op.saveObj(member);
			if (company!=null) op.saveObj(company);
			if (memberordercount!=null){ 
				//dsh和shwtg
				memberordercount.setDsh(new Integer("0"));
				memberordercount.setShwtg(new Integer("0"));
				op.saveObj(memberordercount);
				};
			if (companyordercount!=null){
				companyordercount.setDsh(new Integer("0"));
				companyordercount.setShwtg(new Integer("0"));
				op.saveObj(companyordercount);
				};
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				return e2.getMessage();
			}	
			return e1.getMessage();
		}	
		*/
		return ls_return;
	}
	
	//注册平台用户
	public String RegPlatformUser(SysuserDTO dto)throws ApplicationException {
		String ls_return="0";
		/*
		//json验证
		if (dto.getLoginid()==null || dto.getLoginid().equals("")) return "登录名不能为空";
		if (dto.getTelephone()==null || dto.getTelephone().equals("")) return "电话号码不能为空";  
		if (dto.getUsertype()==null || dto.getUsertype().equals("")) return "注册帐户类型不能为空";
		if (dto.getPassword()==null || dto.getPassword().equals("")) return "密码不能为空";
		if (!(dto.getUsertype().equals("101") || dto.getUsertype().equals("102") || dto.getUsertype().equals("103")|| dto.getUsertype().equals("104") || dto.getUsertype().equals("105") || dto.getUsertype().equals("199"))) return "注册帐户类型异常";
		//String hql=" from Sysuser where loginid='"+dto.getLoginid()+"' or telephone='"+dto.getTelephone()+"'";
		String hql=" from Sysuser where loginid='"+dto.getLoginid()+"'";
		try {
			List list=op.retrieveObjs(hql);
			if (list!=null) return "注册信息已存在";
		} catch (OPException e) {
			return e.getMessage();
		}
		Sysuser sysuser=new Sysuser();   //实例化参数表	
		//String ls_seq="";
		//String ls_seq=SeqBPO.GetSequence("SEQ_USERID");  //获取序列号
		sysuser.setUserid(dto.getUserid());	  //直接从接中返回值中获了userid		
		sysuser.setLoginid(dto.getLoginid());
		sysuser.setUsername(dto.getUsername());
		sysuser.setPassword(Md5Util.toMD5(dto.getPassword()));
		sysuser.setUsertype(dto.getUsertype());
		sysuser.setRegusertype(dto.getRegusertype());
		sysuser.setTelephone(dto.getTelephone());
		sysuser.setIsvalid("101");
		sysuser.setUserstatus("102");  //冻结　　审核通过后解冻可用
		sysuser.setUserlevel("101");   //一星用户
		sysuser.setCardtype(dto.getCardtype());   //证件类型
		sysuser.setIdentifyid(dto.getIdentifyid());  //证件编号
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		sysuser.setCreateddate(now);
		sysuser.setModifydate(now);
		sysuser.setQualificationstatus("100");  //未提交资质
		//modify by whd 2017.1.18 注册用户同时成为委托方及承运商,委托方及承运商编号与用户编码一致
		sysuser.setMemberid(dto.getUserid());
		sysuser.setCompanyid(dto.getUserid());
		
		//增加会员帐户信息
		Account account=new Account();
		account.setUserid(dto.getUserid());
		account.setFreezeamount(new Double(0));
		account.setIntegral(new Double(0));
		account.setTotalamount(new Double(0));
		account.setUseramount(new Double(0));
		
		//设置注册用户默认隶属委托方角色
		Userrole userrole=new Userrole();
		UserroleId usertoleId =new UserroleId();
		usertoleId.setUserid(sysuser.getUserid());
		usertoleId.setRoleid(new Short(GlobalNames.BUY_ROLEID));
		userrole.setId(usertoleId);
		
		//设置注册用户默认隶属承运商角色
		Userrole userrole_company=new Userrole();
		UserroleId usertoleId_company =new UserroleId();
		usertoleId_company.setUserid(sysuser.getUserid());
		usertoleId_company.setRoleid(new Short(GlobalNames.SALE_ROLEID));
		userrole_company.setId(usertoleId_company);
		
		Member member=null;
		Company company=null;
		
		Memberordercount memberordercount=null;
		Companyordercount companyordercount=null;
		
		member=new Member();
		//	ls_seq=SeqBPO.GetSequence("SEQ_MEMBERID");  //获取序列号
		ClassHelper.copyProperties(dto,member);		
		//member.setMemberid(new Integer(ls_seq));
		member.setMemberid(sysuser.getUserid());
		member.setCompanyname(dto.getCompanyname());
		member.setCheckstatus("101");  //等待审核
		member.setBalance(new Double(0));
		member.setMemberlevel("101");
		member.setBalanceaccount(sysuser.getLoginid());   //统一资金帐户
		member.setBalancephone(sysuser.getTelephone());   //统一资金帐户短信通知号码
		member.setCreateddate(now);
		member.setModifydate(now);
			
		
		memberordercount =new Memberordercount();
		//memberordercount.setMemberid(new Integer(ls_seq));
		memberordercount.setMemberid(member.getMemberid());
		memberordercount.setDfbdd(new Integer(0));
		memberordercount.setDyjdd(new Integer(0));
		memberordercount.setDqydd(new Integer(0));
		memberordercount.setDjllybzj(new Integer(0));
		memberordercount.setDqht(new Integer(0));
		memberordercount.setDtyfk(new Integer(0));
		memberordercount.setDfk(new Integer(0));
		memberordercount.setFkwc(new Integer(0));
		memberordercount.setYwc(new Integer(0));
		memberordercount.setDsh(new Integer(0));
		memberordercount.setShwtg(new Integer(0));
		
		company=new Company();
		//ls_seq=SeqBPO.GetSequence("SEQ_COMPANYID");  //获取序列号
		ClassHelper.copyProperties(dto,company);
		//company.setCompanyid(new Integer(ls_seq));
		company.setCompanyid(sysuser.getUserid());
		company.setCompanyname(dto.getCompanyname());
		company.setCheckstatus("101");  //等待审核
		company.setAllowpublish("101"); //允许发布
		company.setBalance(new Double(0));
		company.setCompanylevel("101");
		company.setBalanceaccount(sysuser.getLoginid());   //统一资金帐户
		company.setBalancephone(sysuser.getTelephone());   //统一资金帐户短信通知号码
		company.setDutyperson(dto.getUsername());  //联系人
		company.setCreateddate(now);
		company.setModifydate(now);		
		
		companyordercount=new Companyordercount();
		//companyordercount.setCompanyid(new Integer(ls_seq));
		companyordercount.setCompanyid(company.getCompanyid());
		companyordercount.setDhfjg(new Integer(0));
		companyordercount.setDclyjdd(new Integer(0));
		companyordercount.setDqydd(new Integer(0));
		companyordercount.setDjllybzj(new Integer(0));
		companyordercount.setDqht(new Integer(0));
		companyordercount.setDjsdj(new Integer(0));
		companyordercount.setDkfp(new Integer(0));
		companyordercount.setFkwc(new Integer(0));
		companyordercount.setYwc(new Integer(0));	
		companyordercount.setDsh(new Integer(0));
		companyordercount.setShwtg(new Integer(0));
		companyordercount.setDfbyl(new Integer(0));
		
		//数据保存getUsername
		try {
			trans.begin();						
			op.saveObj(sysuser);	
			op.saveObj(account);
			op.saveObj(member);
			op.saveObj(company);
			op.saveObj(memberordercount);
			op.saveObj(companyordercount);
			op.saveObj(userrole);
			op.saveObj(userrole_company);
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				return e2.getMessage();
			}	
			return e1.getMessage();
		}	
		*/
		return ls_return;
	}
	
	//更新平台用户
	public String ModPlatformUser(SysuserDTO dto)throws ApplicationException {
		String ls_return="0";
		/*
		//json验证
		if (dto.getUserid()==null || dto.getUserid().equals("")) return "user_id不能为空";
		if (dto.getTelephone()==null || dto.getTelephone().equals("")) return "电话号码不能为空";  
		if (dto.getUsertype()==null || dto.getUsertype().equals("")) return "注册帐户类型不能为空";
		if (!(dto.getUsertype().equals("101") || dto.getUsertype().equals("102") || dto.getUsertype().equals("103")|| dto.getUsertype().equals("104") || dto.getUsertype().equals("105") || dto.getUsertype().equals("199"))) return "注册帐户类型异常";
		
		Sysuser sysuser=null;
		Member member=null;
		Company company=null;
		try {
			sysuser=(Sysuser)op.retrieveObj(Sysuser.class, dto.getUserid());
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "用户信息不存在,user_id:"+dto.getUserid();
		}
		try {
			member=(Member)op.retrieveObj(Member.class, dto.getUserid());
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "委托方信息不存在,user_id:"+dto.getUserid();
		}
		try {
			company=(Company)op.retrieveObj(Company.class, dto.getUserid());
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "承运商信息不存在,user_id:"+dto.getUserid();
		}
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		
		sysuser.setUsername(dto.getUsername());
		sysuser.setUsertype(dto.getUsertype());
		sysuser.setTelephone(dto.getTelephone());
		sysuser.setCardtype(dto.getCardtype());   //证件类型
		sysuser.setIdentifyid(dto.getIdentifyid());  //证件编号
		sysuser.setMemo(dto.getMemo());
		sysuser.setModifydate(now);		
		
		member.setCompanyname(dto.getCompanyname());
		member.setTelephone(dto.getTelephone());
		member.setBalancephone(sysuser.getTelephone());   //统一资金帐户短信通知号码
		member.setAreaid(dto.getAreaid());
		member.setModifydate(now);			
		
		company.setCompanyname(dto.getCompanyname());
		company.setDutyperson(sysuser.getUsername());
		company.setTelephone(dto.getTelephone());		
		company.setBalancephone(sysuser.getTelephone());   //统一资金帐户短信通知号码
		company.setAreaid(dto.getAreaid());
		company.setModifydate(now);				
		
		//数据保存getUsername
		try {
			trans.begin();						
			op.updateObj(sysuser);	
			op.updateObj(member);
			op.updateObj(company);
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				return e2.getMessage();
			}	
			return e1.getMessage();
		}	
		*/
		return ls_return;
	}
	
	/**
	 * 确认用户类型(用于平台注册后完善资料之前)
	 * @param msgdata
	 * @param userid		用户编码
       @param usertype	用户类型(101 普通用户　102 个人买家 103 企业买家　104 承运商)
     * @return
	 * @throws ApplicationException
	 */
	public String ConfirmUsertype(String msgdata)throws ApplicationException {
		String ls_return="0";
		/*
		//json验证
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
		SysuserDTO dto=new SysuserDTO();   //实例化参数表
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getUserid()==null) return "用户编号不能为空";  //登录名即手机名
		if (dto.getUsertype()==null || dto.getUsertype().equals("")) return "认证用户类型不能为空";
		if (!(dto.getUsertype().equals("101") || dto.getUsertype().equals("102") ||dto.getUsertype().equals("103") ||dto.getUsertype().equals("104"))) return "用户认证类型错误";
		Sysuser sysuser=null;
		try {
			sysuser=(Sysuser)op.retrieveObj(Sysuser.class, dto.getUserid());					
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "用户信息不存在";
		}		
		if (!sysuser.getUsertype().equals("199")) return "该用户已经进行了用户类型认证";
		if (sysuser.getRegusertype().equals("101")){  //个人注册用户
			if (dto.getUsertype().equals("103")){
				return "个人注册用户不为认证为企业委托方";
			}
			if (dto.getUsertype().equals("104")){
				return "个人注册用户不为认证为承运商";
			}
			
		}
		if (sysuser.getRegusertype().equals("102")){  //企业注册用户
			if (dto.getUsertype().equals("102")){
				return "企业注册用户不为认证为个人委托方";
			}			
		}
		sysuser.setUsertype(dto.getUsertype());
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());  //取当前时间
		sysuser.setModifydate(now);
		String ls_seq="";
		//设置注册用户默认隶属角色
		Userrole userrole=new Userrole();
		UserroleId usertoleId =new UserroleId();
		usertoleId.setUserid(sysuser.getUserid());
		
		Member member=null;
		Company company=null;
		
		Memberordercount memberordercount=null;
		Companyordercount companyordercount=null;
		
		if (dto.getUsertype().equals("101") || dto.getUsertype().equals("102") || dto.getUsertype().equals("103") ){  //买家帐户
			usertoleId.setRoleid(new Short(GlobalNames.BUY_ROLEID));
			member=new Member();
			ls_seq=SeqBPO.GetSequence("SEQ_MEMBERID");  //获取序列号
			ClassHelper.copyProperties(dto,member);
			member.setMemberid(new Integer(ls_seq));
			member.setCheckstatus("101");  //等待审核
			member.setBalance(new Double(0));
			member.setMemberlevel("101");
			member.setCreateddate(now);
			member.setModifydate(now);
			sysuser.setMemberid(new Integer(ls_seq));	
			
			memberordercount =new Memberordercount();
			memberordercount.setMemberid(new Integer(ls_seq));
			memberordercount.setDfbdd(new Integer(0));
			memberordercount.setDyjdd(new Integer(0));
			memberordercount.setDqydd(new Integer(0));
			memberordercount.setDjllybzj(new Integer(0));
			memberordercount.setDqht(new Integer(0));
			memberordercount.setDtyfk(new Integer(0));
			memberordercount.setDfk(new Integer(0));
			memberordercount.setFkwc(new Integer(0));
			memberordercount.setYwc(new Integer(0));	
			memberordercount.setDsh(new Integer(0));
			memberordercount.setShwtg(new Integer(0));
			companyordercount.setDfbyl(new Integer(0));
		}else if (dto.getUsertype().equals("104")){  //卖家帐户
			usertoleId.setRoleid(new Short(GlobalNames.SALE_ROLEID));
			company=new Company();
			ls_seq=SeqBPO.GetSequence("SEQ_COMPANYID");  //获取序列号
			ClassHelper.copyProperties(dto,company);
			company.setCompanyid(new Integer(ls_seq));
			company.setCheckstatus("101");  //等待审核
			company.setAllowpublish("101"); //允许发布
			company.setBalance(new Double(0));
			company.setCompanylevel("101");
			company.setCreateddate(now);
			company.setModifydate(now);
			sysuser.setCompanyid(new Integer(ls_seq));
			
			companyordercount=new Companyordercount();
			companyordercount.setCompanyid(new Integer(ls_seq));
			companyordercount.setDhfjg(new Integer(0));
			companyordercount.setDclyjdd(new Integer(0));
			companyordercount.setDqydd(new Integer(0));
			companyordercount.setDjllybzj(new Integer(0));
			companyordercount.setDqht(new Integer(0));
			companyordercount.setDjsdj(new Integer(0));
			companyordercount.setDkfp(new Integer(0));
			companyordercount.setFkwc(new Integer(0));
			companyordercount.setYwc(new Integer(0));
			companyordercount.setDsh(new Integer(0));
			companyordercount.setShwtg(new Integer(0));
			companyordercount.setDfbyl(new Integer(0));
		}
		userrole.setId(usertoleId);
		//数据保存
		try {
			trans.begin();						
			op.updateObj(sysuser);	
			op.saveObj(userrole);
			if (member!=null) op.saveObj(member);
			if (company!=null) op.saveObj(company);
			if (memberordercount!=null) op.saveObj(memberordercount);
			if (companyordercount!=null) op.saveObj(companyordercount);
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				return e2.getMessage();
			}	
			return e1.getMessage();
		}	
		*/
		return ls_return;
	}
	
	/**
	 * 注册用户关联承运商帐号(用于承运商或委托方从个人中心查询出用户类型为199的用户后进行角色设定与关联)
	 * @param msgdata
	 * @param userid		用户编码
       @param companyid		承运商编码
       @param roleid	承运商类型中的角色编码

	 * @return
	 * @throws ApplicationException
	 */
	public String UnionCompanyAccount(String msgdata)throws ApplicationException {
		String ls_return="0";
		//json验证
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
		SysuserDTO dto=new SysuserDTO();   //实例化参数表
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getUserid()==null) return "用户编号不能为空";  //登录名即手机名
		if (dto.getCompanyid()==null || dto.getCompanyid().equals("") || dto.getCompanyid().intValue()==0) return "承运商编码不能为空";
		if (dto.getRoleid()==null  || dto.getRoleid().equals("")) return "角色编码不能为空";
		Sysuser sysuser=null;
		try {
			sysuser=(Sysuser)op.retrieveObj(Sysuser.class, dto.getUserid());					
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "用户信息不存在";
		}		
		if (!sysuser.getUsertype().equals("199")) return "该用户已经进行了用户类型认证,不能进行帐号关联操作";
		sysuser.setCompanyid(new Integer(dto.getCompanyid()));
		sysuser.setUsertype("104");  //承运商
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());  //取当前时间
		sysuser.setModifydate(now);
		//设置注册用户默认隶属角色
		Userrole userrole=new Userrole();
		UserroleId userroleId =new UserroleId();
		userroleId.setUserid(sysuser.getUserid());
		userroleId.setRoleid(new Short(dto.getRoleid()));
		userrole.setId(userroleId);
		//数据保存
		try {
			trans.begin();						
			op.updateObj(sysuser);	
			op.saveObj(userrole);
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
	 * 注册用户关联委托方帐号(用于承运商或委托方从个人中心查询出用户类型为199的用户后进行角色设定与关联)
	 * @param msgdata
	 * @param userid		用户编码
       @param memberid		委托方编码
       @param roleid		委托方类型中的角色编码
     * @return
	 * @throws ApplicationException
	 */
	public String UnionMemberAccount(String msgdata)throws ApplicationException {
		String ls_return="0";
		//json验证
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
		SysuserDTO dto=new SysuserDTO();   //实例化参数表
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getUserid()==null) return "用户编号不能为空";  //登录名即手机名
		if (dto.getMemberid()==null || dto.getMemberid().equals("") || dto.getMemberid().intValue()==0) return "委托方编码不能为空";
		if (dto.getRoleid()==null  || dto.getRoleid().equals("")) return "角色编码不能为空";
		Sysuser sysuser=null;
		try {
			sysuser=(Sysuser)op.retrieveObj(Sysuser.class, dto.getUserid());					
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "用户信息不存在";
		}		
		if (!sysuser.getUsertype().equals("199")) return "该用户已经进行了用户类型认证,不能进行帐号关联操作";
		sysuser.setMemberid(new Integer(dto.getMemberid()));
		sysuser.setUsertype("103");  //企业委托方
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());  //取当前时间
		sysuser.setModifydate(now);
		//设置注册用户默认隶属角色
		Userrole userrole=new Userrole();
		UserroleId userroleId =new UserroleId();
		userroleId.setUserid(sysuser.getUserid());
		userroleId.setRoleid(new Short(dto.getRoleid()));
		userrole.setId(userroleId);
		//数据保存
		try {
			trans.begin();						
			op.updateObj(sysuser);	
			op.saveObj(userrole);
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
	 * 审核用户
	 * @param msgdata
	 *@param userid	            用户编号
      @param checkerid	    审核人员编号
      @param checkstatus  审核状态(1002审核通过,1003 审核未通过)
      @param checkdesc	    审核备注
     * @return
	 * @throws ApplicationException
	 */
	public String CheckUser(String msgdata)throws ApplicationException {
		String ls_return="0";
		/*
		//json验证
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
		SysuserDTO dto=new SysuserDTO();   //实例化参数表
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getUserid()==null) return "用户编号不能为空";  //登录名即手机名
		if (dto.getCheckerid()==null) return "审核人员编号不能为空";
		if (dto.getCheckstatus()==null || dto.getCheckstatus().equals("")) return "审核状态不能为空";		
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());  //取当前时间
		
		Sysuser sysuser=null;
		try {
			sysuser=(Sysuser)op.retrieveObj(Sysuser.class, dto.getUserid());
			if (dto.getCheckstatus().equals("102")){
				sysuser.setUserstatus("101");  //设置为可用
			}
			sysuser.setModifydate(now);
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "用户信息不存在";
		}
		
		Member member=null;
		Company company=null;		
		if (sysuser.getUsertype().equals("102") || sysuser.getUsertype().equals("103")){  //托运方
			try {
				member=(Member)op.retrieveObj(Member.class, sysuser.getMemberid());
			} catch (OPException e) {
				return e.getMessage();
			} catch (NotFindException e) {
				return "托运方记录不存在,会员编号:"+sysuser.getMemberid();
			}
			member.setCheckdate(now);
			member.setCheckdesc(dto.getCheckdesc());
			member.setCheckerid(dto.getCheckerid());
			member.setCheckstatus(dto.getCheckstatus());
			member.setModifydate(now);
		}else if (sysuser.getUsertype().equals("104")){  //承运方
			try {
				company=(Company)op.retrieveObj(Company.class, sysuser.getCompanyid());
			} catch (OPException e) {
				return e.getMessage();
			} catch (NotFindException e) {
				return "承运商记录不存在,企业编号:"+sysuser.getCompanyid();
			}
			company.setCheckdate(now);
			company.setCheckdesc(dto.getCheckdesc());
			company.setCheckerid(dto.getCheckerid());
			company.setCheckstatus(dto.getCheckstatus());
			company.setModifydate(now);
		}
		try {
			trans.begin();						
			op.updateObj(sysuser);		
			if (member!=null) op.updateObj(member);
			if (company!=null)op.updateObj(company);
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				return e2.getMessage();
			}	
			return e1.getMessage();
		}
		*/
		return ls_return;
	}
	
	/**
	 * 用户资质申请及变更
	 * @param msgdata
	 * @param userid	用户编号
       @param username		用户名称
       @param sex	性别(101 男　102 女)
       @param birthday	出生日期
       @param cardtype	证件类型
       @param identifyid	证件号码
       @param companyname	公司名称
       @param  companytype	公司类型
       @param  regdate	注册日期
       @param  orgcode	组织机构代码
       @param taxno	税号
       @param legalperson	法人
       @param legalcardtype 法人证件类型
       @param legalcardno 法人证件号码
       @param bankname	银行名称
       @param accountname	开户名
       @param bankaccount 银行帐号
       @param statecode	省代码
       @param citycode		市代码
       @param areacode	区县代码
       @param address	详细地址
       @param mailaddress	邮寄地址
       @param mailreceiver	邮寄收件人
       @param mailtelephone	 邮寄资料联系手机
       @param dutyperson	负责人
       @param telephone	  固定电话
       @param mobilephone	移动电话
       @param fax	传真
       @param  email	EMAIL
       @param zipcode  邮编
       @param imgtype	图片类型(多张图片类型用英文逗号分割)
       @param imgurl    图片地址(多张图片地址用英文逗号分割)
     * @return
	 * @throws ApplicationException
	 */
	public String QualificationApply(String msgdata)throws ApplicationException {
		String ls_return="0";
		/*
		String ls_areaid="";
		//json验证		
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
		SysuserDTO dto=new SysuserDTO();   //实例化参数表
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getUserid()==null) return "用户编号不能为空";  //登录名即手机名
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());  //取当前时间
		Sysuser sysuser=null;
		try {
			sysuser=(Sysuser)op.retrieveObj(Sysuser.class, dto.getUserid());			
			sysuser.setModifydate(now);
			sysuser.setQualificationstatus("101");  //已提交资质
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "会员资料不存在,会员编号:"+dto.getUserid();
		}		
		if (sysuser.getUsertype().equals("102")){   //个人买家
			if (dto.getUsername()==null || dto.getUsername().equals("")) return "姓名不能为空";
			if (dto.getSex()==null || dto.getSex().equals("")) return "性别不能为空";
			if (dto.getBirthday()==null || dto.getBirthday().equals("")) return "出生日期不能为空";
			
			sysuser.setUsername(dto.getUsername());
			sysuser.setSex(dto.getSex());
			if (dto.getBirthday()!=null)	sysuser.setBirthday(DateUtil.strToTimestamp(dto.getBirthday()));
			sysuser.setCardtype(dto.getCardtype());
			sysuser.setIdentifyid(dto.getIdentifyid());		
			
		}else if (sysuser.getUsertype().equals("103")  || sysuser.getUsertype().equals("104") ){  //企业买家,承运商			
			if (dto.getCompanyname()==null || dto.getCompanyname().equals("")) return "企业名称不能为空";
			if (dto.getCompanytype()==null || dto.getCompanytype().equals("")) return "企业类型不能为空";
			if (dto.getRegdate()==null || dto.getRegdate().equals("")) return "注册日期不能为空";
			if (dto.getOrgcode()==null || dto.getOrgcode().equals("")) return "组织机构代码不能为空";
			if (dto.getTaxno()==null || dto.getTaxno().equals("")) return "税务登记证号不能为空";
			if (dto.getLegalperson()==null || dto.getLegalperson().equals("")) return "法人代表不能为空";
			if (dto.getLegalcardtype()==null || dto.getLegalcardtype().equals("")) return "法人代表证件类型不能为空";
			if (dto.getLegalcardno()==null || dto.getLegalcardno().equals("")) return "法人代表证件号码不能为空";
			if (dto.getBankname()==null || dto.getBankname().equals("")) return "开户行不能为空";
			if (dto.getAccountname()==null || dto.getAccountname().equals("")) return "开户名不能为空";
			if (dto.getBankaccount()==null || dto.getBankaccount().equals("")) return "银行帐号不能为空";
			//联系方式及联系人信息 验证?
			//邮寄资料信息验证  ?
			if (dto.getStatecode()==null || dto.getStatecode().equals("")) return "省代码不能为空";
			if (dto.getCitycode()==null || dto.getCitycode().equals("")) return "市代码不能为空";
			if (dto.getAreacode()==null || dto.getAreacode().equals("")) return "区县代码不能为空";
			if (dto.getAddress()==null || dto.getAddress().equals("")) return "联系人地址不能为空";
			try {
				ls_areaid=op.executeMinMaxSQLQuery("select areaid from area where arealevel='4' and isvalid='101' and statecode='"+dto.getStatecode()+"' and citycode='"+dto.getCitycode()+"' and areacode='"+dto.getAreacode()+"'");
			} catch (OPException e) {
				return e.getMessage();
			}
		}		
		
		
		String ls_seq="";		
		Member member=null;
		Company company=null;
		
		if (sysuser.getUsertype().equals("102") || sysuser.getUsertype().equals("103")){  //买家
			try {
				member=(Member)op.retrieveObj(Member.class, sysuser.getMemberid());
			} catch (OPException e) {
				return e.getMessage();
			} catch (NotFindException e) {
				return "托运方记录不存在,会员编号:"+sysuser.getMemberid();
			}
			Double balance=member.getBalance();
			ClassHelper.copyProperties(dto, member);
			member.setMemberid(sysuser.getMemberid());
			member.setBalance(balance);
			member.setAreaid(ls_areaid);
			member.setModifydate(now);
			member.setCheckstatus("101");
			//同步用户名
			if (member.getCompanyname()==null || member.getCompanyname().equals("")) member.setCompanyname(sysuser.getUsername());			
		}else if (sysuser.getUsertype().equals("104")){  //承运商
			try {
				company=(Company)op.retrieveObj(Company.class, sysuser.getCompanyid());
			} catch (OPException e) {
				return e.getMessage();
			} catch (NotFindException e) {
				return "承运商记录不存在,企业编号:"+sysuser.getCompanyid();
			}
			Double balance=company.getBalance();
			ClassHelper.copyProperties(dto,company);
			company.setCompanyid(sysuser.getCompanyid());
			company.setBalance(balance);
			company.setAreaid(ls_areaid);
			company.setModifydate(now);
			company.setCheckstatus("101");
			company.setAllowpublish("101"); //允许发布
		}
		
		//保存图片资料
		
		List array_add_userimg=new ArrayList();
		String[] array_imgtype=null;
		String[] array_imgurl=null;
		if (!(dto.getImgtype()==null || dto.getImgtype().equals(""))){  //说明有上传图片
			if (dto.getImgurl()==null || dto.getImgurl().equals("")) return "图片地址不能为空";
			array_imgtype=dto.getImgtype().split(",");  //分割图片类型数组
			array_imgurl=dto.getImgurl().split(",");  //分割图片网址数组
			if (array_imgtype.length!=array_imgurl.length) return "图片类型数组长度与上传图片实际长度不一致";		
			
			for (int i = 0; i < array_imgtype.length; i++) {				
				Userimg userimg=new Userimg();
				ls_seq=SeqBPO.GetSequence("SEQ_USERIMGID");  //获取序列号
				userimg.setUserimgid(new Integer(ls_seq));
				userimg.setUserid(dto.getUserid());
				userimg.setImgtype(array_imgtype[i]);
				//userimg.setOrderno(new Short(i));
				userimg.setImgurl(array_imgurl[i]);
				array_add_userimg.add(userimg);					
			}	
		}
		//数据保存
		try {
			trans.begin();						
			op.updateObj(sysuser);	
			if (member!=null) op.updateObj(member);
			if (company!=null)op.updateObj(company);	
			if (array_add_userimg!=null && array_add_userimg.size()>0){
				for (int i = 0; i < array_imgtype.length; i++) {	
					//op.removeObjs(" delete from Userimg where userid="+dto.getUserid()+" and imgtype='"+dto.getImgtype()+"'");  //先删除用户同类型图片
					op.removeObjs(" delete from Userimg where userid="+dto.getUserid());  //先删除用户图片
				}
				op.saveObjs(array_add_userimg.toArray());
			}
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				return e2.getMessage();
			}	
			return e1.getMessage();
		}
		*/
		return ls_return;
	}
	
	/**
	 * 上传图片，成功返回0|图片地址，否则返回失败原因
	 * @param file 图片文件
	 * @return
	 * @throws ApplicationException
	 */
	public String UploadImg(File file)throws ApplicationException {
		String ls_return="0";
		//json验证		
		String dscPath = (String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "uploadimg_realpath");
		String ls_seq=SeqBPO.GetSequence("SEQ_IMGID");		
		String ls_filename=ls_seq+"."+FileUtil.getImageFileType(file);   //文件名
		String descFile = dscPath + File.separator + ls_filename;
		FileUtil.DeleteFile(descFile);
		File imageFile = new File(descFile);	
		try {
			FileUtil.copy(file, imageFile);
		} catch (Exception e) {
			return "上传图片时失败";
		}		
		return ls_return+"|"+ls_filename;		
	}
	
	/**
	 * 保存base64图片上传，并返回
	 * @param msgdata
	 * @param imgcontent   图片base64码
	 * @return
	 * @throws ApplicationException
	 */
	public String UploadImgByBase64(String msgdata)throws ApplicationException {
	    String ls_return="0";		
		//json验证		
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
		SysuserDTO dto=new SysuserDTO();   //实例化参数表
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getImgcontent()==null || dto.getImgcontent().equals("")) return "图片内容不能为空";
		
		String dscPath = (String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "uploadimg_realpath");
		String ls_imgid=SeqBPO.GetSequence("SEQ_IMGID");		
		String ls_filename=ls_imgid+".jpg";   //文件名					
		String descFile = dscPath + File.separator + ls_filename;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(dto.getImgcontent());
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片			
			OutputStream out = new FileOutputStream(descFile);
			out.write(b);
			out.flush();
			out.close();			
		} catch (Exception e){
			return "生成图片时出错";
		}		
		return ls_return+"|"+ls_filename;		
	}	
	
	
	public String saveUser(PlatUserDTO dto)throws ApplicationException {
		String ls_return="0";
		log.info("平台回调(saveUser):"+dto.toString());
		SysuserDTO sysuserDTO=new SysuserDTO();
		
		sysuserDTO.setUserid(new Integer(dto.getUser_id()));
		sysuserDTO.setLoginid(dto.getUser_id());
		sysuserDTO.setUsername(dto.getUser_name());
		sysuserDTO.setTelephone(dto.getMobile());
		//sysuserDTO.setUsertype("199");  //用户类型未明确(待资料完善时)
		sysuserDTO.setUsertype("105");  //委托承运共同用户
		sysuserDTO.setRegusertype(dto.getUser_type());
		sysuserDTO.setPassword(dto.getPassword());		
		if (dto.getCard_type().equals("身份证")){
			sysuserDTO.setCardtype("101");
		}else{
			sysuserDTO.setCardtype("105");
		}
		sysuserDTO.setIdentifyid(dto.getId());
		sysuserDTO.setCompanyname(dto.getCom_name());
		ls_return=RegPlatformUser(sysuserDTO);
		log.info("平台回调(saveUser),返回结果:"+ls_return);
		return ls_return;
	}
	
	public String updateUser(PlatUserDTO dto)throws ApplicationException {
		String ls_return="0";
		log.info("平台回调(update):"+dto.toString());
		SysuserDTO sysuserDTO=new SysuserDTO();
		
		sysuserDTO.setUserid(new Integer(dto.getUser_id()));
		sysuserDTO.setLoginid(dto.getUser_id());
		sysuserDTO.setUsername(dto.getUser_name());
		sysuserDTO.setTelephone(dto.getMobile());
		//sysuserDTO.setUsertype("199");  //用户类型未明确(待资料完善时)
		sysuserDTO.setUsertype("105");  //委托承运共同用户
		sysuserDTO.setRegusertype(dto.getUser_type());
		sysuserDTO.setPassword(dto.getPassword());		
		if (dto.getCard_type().equals("身份证")){
			sysuserDTO.setCardtype("101");
		}else{
			sysuserDTO.setCardtype("105");
		}
		sysuserDTO.setIdentifyid(dto.getId());
		sysuserDTO.setCompanyname(dto.getCom_name());
		sysuserDTO.setMemo(dto.getDesc());
		
		String ls_areaid="";
		try {
			ls_areaid=op.executeMinMaxSQLQuery("select areaid from area where arealevel='4' and isvalid='101' and statename='"+dto.getReside_province()+"' and cityname='"+dto.getReside_city()+"' and areaname='"+dto.getReside_dist()+"'");
		} catch (OPException e) {
			return e.getMessage();
		}	
		sysuserDTO.setAreaid(ls_areaid);
		ls_return=ModPlatformUser(sysuserDTO);
		log.info("平台回调(updateUser),返回结果:"+ls_return);
		return ls_return;
	}
	
	public String ModMobile(PlatUserDTO dto)throws ApplicationException {
		String ls_return="0";
		/*
		log.info("平台回调(mobile):"+dto.toString());
		Sysuser sysuser=null;
		Member member=null;
		Company company=null; 
		if (dto.getUser_id()==null || dto.getUser_id().equals("")) return "user_id不能为空";
		if (dto.getMobile()==null || dto.getMobile().equals("")) return "user_id不能为空";
		try {
			sysuser=(Sysuser)op.retrieveObj(Sysuser.class, new Integer(dto.getUser_id()));
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "用户信息不存在,user_id:"+dto.getUser_id();
		}
		try {
			member=(Member)op.retrieveObj(Member.class, sysuser.getMemberid());
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "委托方信息不存在,user_id:"+sysuser.getMemberid();
		}
		try {
			company=(Company)op.retrieveObj(Company.class, sysuser.getCompanyid());
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "承运商信息不存在,user_id:"+sysuser.getCompanyid();
		}
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		
		sysuser.setTelephone(dto.getMobile());
		sysuser.setModifydate(now);		
		
		member.setTelephone(sysuser.getTelephone());
		member.setBalancephone(sysuser.getTelephone());   //统一资金帐户短信通知号码
		member.setModifydate(now);			
		
		company.setTelephone(sysuser.getTelephone());		
		company.setBalancephone(sysuser.getTelephone());   //统一资金帐户短信通知号码
		company.setModifydate(now);				
		
		//数据保存getUsername
		try {
			trans.begin();						
			op.updateObj(sysuser);	
			op.updateObj(member);
			op.updateObj(company);
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				return e2.getMessage();
			}	
			return e1.getMessage();
		}	
		log.info("平台回调(mobile),返回结果:"+ls_return);
		*/
		return ls_return;
	}
	
	/**
	 * 变更承运商级别
	 * @param msgdata
	 * @param	companyid		承运商编码
       @param	companylevel	承运商等级
       @param	createdby		创建人员

	 * @return
	 * @throws ApplicationException
	 */
	public String ModCompanyLevel(String msgdata)throws ApplicationException {
	    String ls_return="0";		
	    /*
		//json验证		
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
		CompanyDTO dto=new CompanyDTO();   //实例化参数表
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getCompanyid()==null || dto.getCompanyid().intValue()==0) return "承运商编码不能为空";
		if (dto.getCompanylevel()==null || dto.getCompanylevel().equals("")) return "承运商等级不能为空";
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0) return "创建人员编码不能为空";
		Company company=null;
		try {
			company=(Company)op.retrieveObj(Company.class, dto.getCompanyid());
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "承运商记录不存在,承运商编码:"+dto.getCompanyid();
		}
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());  //取当前时间
		company.setModifyby(dto.getCreatedby());
		company.setModifydate(now);
		company.setCompanylevel(dto.getCompanylevel());
		
		Sysuser sysuser;
		try {
			sysuser = (Sysuser)op.retrieveObj(Sysuser.class, dto.getCompanyid());
		} catch (OPException e3) {
			return e3.getMessage();
		} catch (NotFindException e3) {
			return "用户记录不存在,用户编码:"+dto.getCompanyid();
		}
		sysuser.setModifyby(dto.getCreatedby());
		sysuser.setModifydate(now);
		sysuser.setUserlevel(dto.getCompanylevel());
		
		Member member=null;
		try {
			member=(Member)op.retrieveObj(Member.class,sysuser.getMemberid());
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "委托方记录不存在,委托方编码:"+sysuser.getMemberid();
		}
		member.setModifyby(dto.getCreatedby());
		member.setModifydate(now);
		member.setMemberlevel(sysuser.getUserlevel());
		
		try {
			trans.begin();						
			op.updateObj(company);	
			op.updateObj(member);
			op.updateObj(sysuser);
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				return e2.getMessage();
			}	
			return e1.getMessage();
		}
		*/
		return ls_return;
	}
	
	/**
	 * 变更委托方级别
	 *@param msgdata
	 *@param memberid		委托方编码
      @param memberlevel	委托方等级
      @param createdby	         创建人员

	 * @return
	 * @throws ApplicationException
	 */
	public String ModMemberLevel(String msgdata)throws ApplicationException {
	    String ls_return="0";	
	    /*
		//json验证		
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
		MemberDTO dto=new MemberDTO();   //实例化参数表
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getMemberid()==null || dto.getMemberid().intValue()==0) return "承运商编码不能为空";
		if (dto.getMemberlevel()==null || dto.getMemberlevel().equals("")) return "承运商等级不能为空";
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0) return "创建人员编码不能为空";
		Member member=null;
		try {
			member=(Member)op.retrieveObj(Member.class, dto.getMemberid());
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "委托方记录不存在,委托方编码:"+dto.getMemberid();
		}
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());  //取当前时间
		member.setModifyby(dto.getCreatedby());
		member.setModifydate(now);
		member.setMemberlevel(dto.getMemberlevel());
		
		Sysuser sysuser;
		try {
			sysuser = (Sysuser)op.retrieveObj(Sysuser.class, dto.getMemberid());
		} catch (OPException e3) {
			return e3.getMessage();
		} catch (NotFindException e3) {
			return "用户记录不存在,用户编码:"+dto.getMemberid();
		}
		sysuser.setModifyby(dto.getCreatedby());
		sysuser.setModifydate(now);
		sysuser.setUserlevel(dto.getMemberlevel());
		
		Company company=null;
		try {
			company=(Company)op.retrieveObj(Company.class, sysuser.getCompanyid());
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "承运商记录不存在,承运商编码:"+sysuser.getCompanyid();
		}
		company.setModifyby(dto.getCreatedby());
		company.setModifydate(now);
		company.setCompanylevel(sysuser.getUserlevel());
		
		try {
			trans.begin();						
			op.updateObj(company);	
			op.updateObj(member);
			op.updateObj(sysuser);	
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				return e2.getMessage();
			}	
			return e1.getMessage();
		}
		*/
		return ls_return;
	}

	/**
	 * 获取查询管理后台个人中心的待处理事项统计
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public String GetSql_SearchPersonCenter(String msgdata)throws ApplicationException {
		SysuserDTO dto=new SysuserDTO();
		ClassHelper.copyProperties(msgdata, dto);		 
		StringBuffer sb=new StringBuffer("select ")
		.append("(select count(1) from waybill where isvalid='101' and waybillstatus='100') dwcyd,")
		.append("(select count(1) from receivable where receivablestatus='100') dsktb,")
		.append("(select count(1) from payable where payablestatus='100') dfktb,")
		.append("(select count(1) from orderinfo where isvalid='101' and orderstatus='103') dwcdd,")
		.append("(select count(1) from receivable where receivablestatus='101') dskqr,")
		.append("(select count(1) from payable where payablestatus='101') dfkqr");
		return sb.toString();
	}
	

}
