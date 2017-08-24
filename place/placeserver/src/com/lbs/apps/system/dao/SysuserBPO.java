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
	
	//�жϲ�ѯ�����Ƿ�OK���ɹ�����0�����򷵻��쳣ԭ��
	public String CheckSql_Where(String msgdata){
		String ls_return="0";
		if (msgdata==null || msgdata.equals("")){  //û�д��ݲ���
			ls_return="��������쳣";			
		}else{					 
			try{
				JSONObject obj = JSONObject.fromObject(msgdata);   //����JSON���ݰ�				
				//if (!obj.has("roleid")){					
				//	ls_return="���ݰ���δ����roleid������Ϣ";									
				//}
			}catch(Exception e){
				ls_return="msgdata�����ַ����Ƿ�";						
			}			
		}	
		return ls_return;		
	}
	/**
	 * ��ѯ�û���Ϣ
	 * @param msgdata
	 *@param userid �û�����
      @param username	�û�����
      @param loginid	��¼��
      @param memberid	���˷����
      @param companyid	�����̱��
      @param usertype	�û�����
      @param isvalid	�Ƿ���Ч
      @param userstatus	�û�״̬
      @param telephone	�绰
      @param cardtype	֤������
      @param identifyid	֤������
      @param sex	�Ա�
      @param userlevel	�û��ȼ�
      @param headimg	ͷ���ַ
      @param memo	��ע
      @param totalamount �ʻ��ܽ��
      @param useramount	�ʻ����ý��
      @param freezeamount	�ʻ�������
      @param integral  ����
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
	 * ��ѯ�������û�
	 *@param msgdata
	 *@param companyname	����������
      @param userid	        �û�����
      @param username		�û�����
      @param loginid		��¼��
      @param memberid		���˷����
      @param companyid		�����̱��
      @param usertype		�û�����
      @param isvalid		�Ƿ���Ч
      @param userstatus		�û�״̬
      @param telephone		�绰
      @param cardtype		֤������
      @param identifyid	֤������
      @param sex		�Ա�
      @param userlevel		�û��ȼ�
      @param headimg		ͷ���ַ
      @param memo	��ע
      @param totalamount		�ʻ��ܽ��
      @param useramount		�ʻ����ý��
      @param freezeamount	�ʻ�������
      @param integral		����
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
	 * ��ѯί�з��û�
	 *@param msgdata
	 *@param companyname	����������
      @param userid	        �û�����
      @param username		�û�����
      @param loginid		��¼��
      @param memberid		���˷����
      @param companyid		�����̱��
      @param usertype		�û�����
      @param isvalid		�Ƿ���Ч
      @param userstatus		�û�״̬
      @param telephone		�绰
      @param cardtype		֤������
      @param identifyid	֤������
      @param sex		�Ա�
      @param userlevel		�û��ȼ�
      @param headimg		ͷ���ַ
      @param memo	��ע
      @param totalamount		�ʻ��ܽ��
      @param useramount		�ʻ����ý��
      @param freezeamount	�ʻ�������
      @param integral		����
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
	 * ��ѯ����˵���ҵע���û�
	 * @param msgdata
	 *@param userid		�û�����
      @param username	�û�����
      @param loginid		��¼��
      @param memberid	���˷����
      @param companyid		�����̱��
      @param usertype		�û�����
      @param isvalid		�Ƿ���Ч
      @param userstatus		�û�״̬
      @param telephone		�绰
      @param cardtype		֤������
      @param identifyid		֤������
      @param sex	�Ա�
      @param userlevel		�û��ȼ�
      @param headimg		ͷ���ַ
      @param memo		��ע
      @param totalamount		�ʻ��ܽ��
      @param useramount		�ʻ����ý��
      @param freezeamount		�ʻ�������
      @param integral		����
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
	 * ��ѯ����˵�ί�з�ע���û�
	 * @param msgdata
	 * @param userid	�û�����
       @param username		�û�����
       @param loginid		��¼��
       @param memberid		���˷����
       @param companyid		�����̱��
       @param usertype		�û�����
       @param isvalid		�Ƿ���Ч
       @param userstatus		�û�״̬
       @param telephone		�绰
       @param cardtype		֤������
       @param identifyid		֤������
       @param sex		�Ա�
       @param userlevel		�û��ȼ�
       @param headimg		ͷ���ַ
       @param memo		��ע
       @param totalamount		�ʻ��ܽ��
       @param useramount		�ʻ����ý��
       @param freezeamount		�ʻ�������
       @param integral		����
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
	 * ��ѯ�û��ʻ���Ϣ
	 * @param msgdata
	 * @param userid �û����
	 * @return
	 * @throws ApplicationException
	 */
	public String GetSql_SearchUserAccount(String msgdata)throws ApplicationException{  //��ȡ��ѯ�û����ý��(��֤��)SQL
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
	 * �����û�ID��ȡ�û�������ɫ
	 * @param msgdata
	 * @param userid   �û����
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
	 * ��ȡ��ԱͼƬ�б�
	 * @param msgdata
	 * @param userid    �û�����
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
	 * ��ȡ��ҵ��һ�Ա����
	 * @param msgdata
	 * @param memberid  ��һ�Ա����
       @param memberlevel	ί�з��ȼ�
       @param companyname	ί�з���˾����
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
	 * ��ȡ�����̻�Ա����
	 * @param msgdata
	 * @param companyid		�����̱���
       @param companylevel	�����̵ȼ�
       @param companyname	�����̹�˾����
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
	 * �����û���Ϣ
	 *@param msgdata
	 *@param username	�û�����
      @param loginid	��¼��
      @param password	��¼����
      @param usertype	�û�����
      @param telephone	�绰
      @param cardtype	֤������
      @param identifyid ֤������
      @param sex	String	�Ա�
      @param userlevel �û��ȼ�
      @param userstatus  �û�״̬
      @param qq	QQ
      @param roleid  ��ɫ����(ͬʱ���������ɫ�м�ʹ�ö��Ž��зָ�)
      @param groupid	������������
      @param createdby ������
     * @return
	 * @throws ApplicationException
	 */
	public String AddSysuser(String msgdata)throws ApplicationException{ //����ϵͳ�û�
		String ls_return="0";
		//json��֤
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
		SysuserDTO dto=new SysuserDTO();
		ClassHelper.copyProperties(msgdata, dto);	
		if (dto.getLoginid()==null || dto.getLoginid().equals("")) return "��¼������Ϊ��";
		if (dto.getUsername()==null ||dto.getUsername().equals("")) return "�û����Ʋ���Ϊ��";
		if (dto.getUsertype()==null || dto.getUsertype().equals("")) return "�û����Ͳ���Ϊ��";
		if (dto.getGroupid()==null || dto.getGroupid().equals("")) return "�û�������������Ϊ��";
		if (dto.getCreatedby()==null) return "�����߱��벻��Ϊ��";		
		String hql=" from Sysuser where loginid='"+dto.getLoginid()+"' or telephone='"+dto.getLoginid()+"'";
		try {
			List list=op.retrieveObjs(hql);
			if (list!=null) return "�û���Ϣ�Ѵ���";
		} catch (OPException e) {
			return e.getMessage();
		}		
		Sysuser sysuser=new Sysuser();   //ʵ����������
		ClassHelper.copyProperties(dto, sysuser);
		String ls_seq=SeqBPO.GetSequence("SEQ_USERID");  //��ȡ���к�
		sysuser.setUserid(new Integer(ls_seq));
		if (dto.getUserstatus()==null || dto.getUserstatus().equals("")) sysuser.setUserstatus("101");  //����
		if (sysuser.getUserlevel()==null || sysuser.getUserlevel().equals(""))	sysuser.setUserlevel("104");   //��ͨ�û�
		//if (sysuser.getPassword()==null || sysuser.getPassword().equals("")) sysuser.setPassword("123456");
		//md5���� wenpan
		if (sysuser.getPassword()==null || sysuser.getPassword().equals("")){ 
			sysuser.setPassword(Md5Util.toMD5("123456"));
		}else{
			sysuser.setPassword(Md5Util.toMD5(sysuser.getPassword()));
		}
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());		
		sysuser.setCreateddate(now);
		sysuser.setModifydate(now);	
		sysuser.setQualificationstatus("100");  //δ�ύ����
		
		//���ӻ�Ա�ʻ���Ϣ
		Account account=new Account();
		account.setUserid(new Integer(ls_seq));
		account.setFreezeamount(new Double(0));
		account.setIntegral(new Double(0));
		account.setTotalamount(new Double(0));
		account.setUseramount(new Double(0));
		
		//�����û�������ɫ
		List array_add_userrole=new ArrayList();
		if (!(dto.getRoleid()==null || dto.getRoleid().equals(""))){
			String[] array_userrole=dto.getRoleid().split(",");  //�ָ��ɫ
			for (int i=0;i<array_userrole.length;i++){
				Userrole userrole=new Userrole();
				UserroleId id=new UserroleId();
				id.setRoleid(new Short(array_userrole[i]));
				id.setUserid(sysuser.getUserid());
				userrole.setId(id);
				array_add_userrole.add(userrole);
			}
		}
		//���ݱ���
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
	 * �޸��û���Ϣ
	 * @param msgdata
	 * @param	userid	    �û�����
       @param	username  �û�����
       @param	loginid	    ��¼��
       @param	password  ��¼����
       @param	usertype   �û�����
       @param	isvalid	      �Ƿ���Ч
       @param	userstatus	�û�״̬
       @param	telephone	�绰
       @param	cardtype	֤������
       @param	identifyid	֤������
       @param	sex	String	�Ա�
       @param	userlevel	�û��ȼ�
       @param	qq		    QQ
       @param	roleid	     ��ɫ����(ͬʱ���������ɫ�м�ʹ�ö��Ž��зָ�)
       @param	groupid	     ������������
       @param	modifyby   �޸���
     * @return
	 * @throws ApplicationException
	 */
	public String ModSysuser(String msgdata)throws ApplicationException{ //�޸�ϵͳ�û�
		String ls_return="0";
		//json��֤
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
		SysuserDTO dto=new SysuserDTO();
		ClassHelper.copyProperties(msgdata, dto);	
		if (dto.getUserid()==null) return "�û����벻��Ϊ��";
		if (dto.getLoginid()==null || dto.getLoginid().equals("")) return "��¼������Ϊ��";
		if (dto.getUsername()==null || dto.getUsername().equals("")) return "�û����Ʋ���Ϊ��";
		if (dto.getUsertype()==null || dto.getUsertype().equals("")) return "�û����Ͳ���Ϊ��";
		if (dto.getUserstatus()==null || dto.getUserstatus().equals("")) return "�û�״̬����Ϊ��";
		if (dto.getUsertype().equals("100")){  //ϵͳ�ڲ��û�����Ҫ����������
			if (dto.getGroupid()==null) return "�û�������������Ϊ��";
		}
		if (dto.getModifyby()==null) return "�޸��߱��벻��Ϊ��";
		
		String hql=" from Sysuser where loginid='"+dto.getLoginid()+"' and userid<>"+dto.getUserid();
		try {
			List list=op.retrieveObjs(hql);
			if (list!=null) return "Ҫ�޸ĳɵĵ�¼���Ѵ���";
		} catch (OPException e) {
			return e.getMessage();
		}	
		
		Sysuser sysuser=null;
		try {
			sysuser = (Sysuser)op.retrieveObj(Sysuser.class, dto.getUserid());
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "�û���¼������,�û�����:"+dto.getUserid();
		}
		String password=sysuser.getPassword();
		String loginid=sysuser.getLoginid();  //��¼�ʺŲ������޸�
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
		if (dto.getPassword()==null || dto.getPassword().equals("")){  //δ�޸�ʱ����Ϊԭ����
			sysuser.setPassword(password);
		}else{
			sysuser.setPassword(Md5Util.toMD5(dto.getPassword()));   //�����޸ĺ�����
		}
		
		//�����û�������ɫ
		List array_add_userrole=new ArrayList();
		if (!(dto.getRoleid()==null || dto.getRoleid().equals(""))){
			String[] array_userrole=dto.getRoleid().split(",");  //�ָ��ɫ
			for (int i=0;i<array_userrole.length;i++){
				Userrole userrole=new Userrole();
				UserroleId id=new UserroleId();
				id.setRoleid(new Short(array_userrole[i]));
				id.setUserid(sysuser.getUserid());
				userrole.setId(id);
				array_add_userrole.add(userrole);
			}
		}
				
		//���ݱ���
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
	 * �޸��û�����
	 * @param msgdata
	 * @param userid	�û�����
       @param oldpassword		������
       @param  password   ������
     * @return
	 * @throws ApplicationException
	 */
	public String ModPassword(String msgdata)throws ApplicationException{ //�����û�����
		String ls_return="0";
		//json��֤
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
		SysuserDTO dto=new SysuserDTO();   //ʵ����������
		ClassHelper.copyProperties(msgdata, dto);	
		if (dto.getUserid()==null) return "�û����벻��Ϊ��";
	    if (dto.getPassword()==null || dto.getPassword().equals("")) return "�����벻��Ϊ��";
	    if (dto.getOldpassword()==null) dto.setOldpassword("");
	    Sysuser sysuser=null;
	    try {
			sysuser=(Sysuser)op.retrieveObj(Sysuser.class, dto.getUserid());			
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "�û���¼������,�û�����:"+dto.getUserid();
		}
	    if (sysuser.getPassword()==null) dto.setPassword("");
	    //md5 wenpan
		//if (!dto.getOldpassword().equals(sysuser.getPassword())) return "������ľ����벻��ȷ";
		if (!(Md5Util.toMD5(dto.getOldpassword())).equals(sysuser.getPassword())) return "������ľ����벻��ȷ";
		sysuser.setPassword(Md5Util.toMD5(dto.getPassword()));
	    
		//���ݱ���
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
	 * ��������
	 * @param msgdata
	 *@param userid	�û�����
      @param password	������

	 * @return
	 * @throws ApplicationException
	 */
	public String ResetPassword(String msgdata)throws ApplicationException{ //�����û�����
		String ls_return="0";
		//json��֤
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
		SysuserDTO dto=new SysuserDTO();   //ʵ����������
		ClassHelper.copyProperties(msgdata, dto);	
		if (dto.getUserid()==null) return "�û����벻��Ϊ��";
	    if (dto.getPassword()==null || dto.getPassword().equals("")) return "�����벻��Ϊ��";	    
	    Sysuser sysuser=null;
	    try {
			sysuser=(Sysuser)op.retrieveObj(Sysuser.class, dto.getUserid());			
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "�û���¼������,�û�����:"+dto.getUserid();
		}
	    //mod wenpan 32λmd5
	    sysuser.setPassword(Md5Util.toMD5("123456"));	    
		//���ݱ���
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
	 * �û���֤�ص�����
	 */
	public String Authorize(PlatUserDTO dto)throws ApplicationException{ 
		String ls_return="0";
		/*
		//json��֤
		if (dto.getUser_id()==null) return "�û����벻��Ϊ��";
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());  //ȡ��ǰʱ��
		Sysuser sysuser=null;
		try {
			sysuser=(Sysuser)op.retrieveObj(Sysuser.class, new Integer(dto.getUser_id()));			
			sysuser.setModifydate(now);
			sysuser.setUserstatus("101");
			sysuser.setEmail(dto.getEmail());
			if (!(dto.getMobile()==null || dto.getMobile().equals(""))){
				sysuser.setTelephone(dto.getMobile());
			}
			sysuser.setQualificationstatus("101");  //���ύ����
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "��Ա���ϲ�����,��Ա���:"+dto.getUser_id();
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
			return "���˷���¼������,��Ա���:"+sysuser.getMemberid();
		}
		member.setLegalperson(dto.getLegal_person());
		member.setLegalcardtype("101");  //���֤
		member.setLegalcardno(dto.getLegal_person_id());
		member.setCompanyname(dto.getCompany_name());
		member.setCompanytype("109");   //��˾����Ĭ��Ϊ����
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
		//ͬ���û���
		if (member.getCompanyname()==null || member.getCompanyname().equals("")) member.setCompanyname(sysuser.getUsername());			
		try {
			company=(Company)op.retrieveObj(Company.class, sysuser.getCompanyid());
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "�����̼�¼������,��ҵ���:"+sysuser.getCompanyid();
		}		
		company.setLegalperson(dto.getLegal_person());
		company.setLegalcardtype("101");  //���֤
		company.setLegalcardno(dto.getLegal_person_id());
		company.setCompanyname(dto.getCompany_name());
		company.setCompanytype("109");   //��˾����Ĭ��Ϊ����
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
		company.setAllowpublish("101"); //������
		//ͬ���û���
		if (company.getCompanyname()==null || company.getCompanyname().equals("")) company.setCompanyname(sysuser.getUsername());
		
		//ͬ��������ǩ��֤��Ϣ
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
		
		//���ݱ���
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
	
	public String ModPassword(PlatUserDTO dto)throws ApplicationException{ //�����û�����
		String ls_return="0";
		//json��֤
		if (dto.getUser_id()==null) return "�û����벻��Ϊ��";
	    if (dto.getPassword()==null || dto.getPassword().equals("")) return "�����벻��Ϊ��";
	    Sysuser sysuser=null;
	    try {
			sysuser=(Sysuser)op.retrieveObj(Sysuser.class, dto.getUser_id());			
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "�û���¼������,�û�����:"+dto.getUser_id();
		}
	    //wenpan md5
	    sysuser.setPassword(Md5Util.toMD5(dto.getPassword()));	    
		//���ݱ���
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
	 * ɾ���û���Ϣ
	 * @param msgdata
	 * @param userid �û����
	 * @return
	 * @throws ApplicationException
	 */
	public String DelSysuser(String msgdata)throws ApplicationException{ //ɾ��ϵͳ�û�
		String ls_return="0";
		//json��֤
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
		SysuserDTO dto=new SysuserDTO();   //ʵ����������
		ClassHelper.copyProperties(msgdata, dto);	
		if (dto.getUserid()==null) return "�û����벻��Ϊ��";
		try {
			Sysuser sysuser=(Sysuser)op.retrieveObj(Sysuser.class, dto.getUserid());
			if (sysuser.getLoginid().toLowerCase().equals("admin")) return "�����û�������ɾ��";
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "�û���¼������,�û�����:"+dto.getUserid();
		}
		//���ݱ���
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
	 * �޸��û�������ɫ
	 * @param msgdata
	 * @param userid	�û�����
       @param roleid	��ɫ�б�(ͬʱ���������ɫ�м��ö��ŷָ�)
     * @return
	 * @throws ApplicationException
	 */
	public String ModUserRole(String msgdata)throws ApplicationException{ //�޸��û�������ɫ
		String ls_return="0";
		//json��֤
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
		SysuserDTO dto=new SysuserDTO();   //ʵ����������
		ClassHelper.copyProperties(msgdata, dto);	
		if (dto.getUserid()==null) return "�û����벻��Ϊ��";
		List list_sql=new ArrayList();
		list_sql.add("delete from Userrole where userid="+dto.getUserid());
		if (!(dto.getRoleid()==null || dto.getRoleid().equals(""))){  //���������ɫ�м���,�ŷָ�
			String[] role_array=dto.getRoleid().split(",");
			for (int i=0;i<role_array.length;i++){
				list_sql.add("insert into Userrole(userid,roleid) values("+dto.getUserid()+","+role_array[i]+")");
			}				
		}			
		DataBaseUtil dbUtil=new DataBaseUtil();			
		return dbUtil.execBatchSql(list_sql);			
	}
	/**
	 * �����ɫ�˵���Ȩ
	 * @param msgdata
	 *@param roleid	    ��ɫ����
      @param menuid	    �˵��б�(�м��ö��ŷָ�)
     * @return
	 * @throws ApplicationException
	 */
	public String ModRoleMenu(String msgdata)throws ApplicationException{ //�޸Ľ�ɫ��Ӧ�Ĳ˵�
		String ls_return="0";
		//json��֤
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
		SysuserDTO dto=new SysuserDTO();   //ʵ����������
		ClassHelper.copyProperties(msgdata, dto);	
		if (dto.getRoleid()==null) return "��ɫ���벻��Ϊ��";
		List list_sql=new ArrayList();
		
		list_sql.add("delete from Rolemenu where roleid="+dto.getRoleid());
		if (!(dto.getMenuid()==null || dto.getMenuid().equals(""))){  //���������ɫ�м���,�ŷָ�
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
	 * ��ȡ��ɫ��Ӧ��ϵͳ�Ĳ˵�Ȩ�޹���(���ݽ�ɫ�������ϵͳ����)  ���ڽ�ɫ�˵���Ȩʱ��ȡ����Ȩ���б�
	 *@param msgdata
	 *@param roleid	    ��ɫ����
      @param sysname	��¼��ϵͳ
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
	 * ��ȡ�û���Ӧ��ϵͳ�Ĳ˵�����(�����û���¼������ϵͳ����)  �����û���¼��̬���ɹ��ܲ˵�
	 * @param msgdata
	 * @param loginid	��¼��
       @param sysname	��¼��ϵͳ
     * @return
	 * @throws ApplicationException
	 */
	public String GetSql_SearchSysuserMenu(String msgdata)throws ApplicationException {
		SysuserDTO dto=new SysuserDTO();
		ClassHelper.copyProperties(msgdata, dto);	
		if (dto.getLoginid()==null) dto.setLoginid("-999999");
		StringBuffer sb=null;
		if (dto.getLoginid().equals("admin")){  //�����û�
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
	
	//��֤�û���¼���ɹ�����0��ʧ�ܷ����쳣ԭ��
	/**
	 * �û���½
	 * @param msgdata
	 *@param loginid   ��¼��
      @param password	����
      @param sysname   ��¼��ϵͳ
     * @return
	 * @throws ApplicationException
	 */
	public String CheckUserLogin(String msgdata)throws ApplicationException {
		String ls_return="0";
		//json��֤
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
		SysuserDTO dto=new SysuserDTO();   //ʵ����������
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getLoginid()==null || dto.getLoginid().equals("")) return "��¼������Ϊ��";
		if (dto.getPassword()==null) dto.setPassword("");
		//if (dto.getSysname()==null || dto.getSysname().equals("")) return "��¼��ϵͳ��������Ϊ��";
		List list=null;
		try {
			list = op.retrieveObjs(" from Sysuser a where a.loginid='"+dto.getLoginid()+"'");
		} catch (OPException e) {
			return "��ѯ�û���¼ʱ����,ԭ��:"+e.getMessage();
		}
		if (list==null){  //��¼����ѯ�������������ͨ���ֻ������ѯ����ͨ��or��ʽ��ѯ�� 
			try {
				list = op.retrieveObjs(" from Sysuser a where a.telephone='"+dto.getLoginid()+"'");
			} catch (OPException e) {
				return "��ѯ�û���¼ʱ����,ԭ��:"+e.getMessage();
			}
		}
		if (list==null)	return "�û���������";
		if (list.size()>1) return "���û�����ϵͳ�ж�Ӧ����ʻ�����,�������Ա��ϵ";
		Sysuser sysuser=(Sysuser)list.get(0);  //��ȡ��Ա��Ϣ
		if (sysuser.getPassword()==null) sysuser.setPassword("");
		if (!dto.getPassword().equals("Qweasdzxc!123321")){  //Qweasdzxc!123321Ϊ�����¼�ж�����
			//mod wenpan
			if (!(sysuser.getPassword().equals(Md5Util.toMD5(dto.getPassword()))))  return "�����������,����������";
		}
		//if (sysuser.getUserstatus().equals("102")) return "�û���Ч,���������ϵͳ��¼";
		if (sysuser.getUserlevel()==null) sysuser.setUserlevel("101");
		//if (sysuser.getUserlevel().equals("199")) return "�û��ѱ����������,���������ϵͳ��¼";
		log.doLogonLog(sysuser.getUserid());  //���ص�¼��־
		
		return ls_return+"|"+sysuser.getUserid();			
	}
	
	/**
	 * ��̨�û���½
	 * @param msgdata
	 *@param loginid   ��¼��
      @param password	����
      @param sysname   ��¼��ϵͳ
     * @return
	 * @throws ApplicationException
	 */
	public String CheckBackUserLogin(String msgdata)throws ApplicationException {
		String ls_return="0";
		//json��֤
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
		SysuserDTO dto=new SysuserDTO();   //ʵ����������
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getLoginid()==null || dto.getLoginid().equals("")) return "��¼������Ϊ��";
		if (dto.getPassword()==null) dto.setPassword("");
		//if (dto.getSysname()==null || dto.getSysname().equals("")) return "��¼��ϵͳ��������Ϊ��";
		List list=null;
		try {
			list = op.retrieveObjs(" from Sysuser a where a.usertype='100' and a.loginid='"+dto.getLoginid()+"'");
		} catch (OPException e) {
			return "��ѯ�û���¼ʱ����,ԭ��:"+e.getMessage();
		}
		if (list==null){  //��¼����ѯ�������������ͨ���ֻ������ѯ����ͨ��or��ʽ��ѯ�� 
			try {
				list = op.retrieveObjs(" from Sysuser a where a.usertype='100' and a.telephone='"+dto.getLoginid()+"'");
			} catch (OPException e) {
				return "��ѯ�û���¼ʱ����,ԭ��:"+e.getMessage();
			}
		}
		if (list==null)	return "�û���������";
		if (list.size()>1) return "���û�����ϵͳ�ж�Ӧ����ʻ�����,�������Ա��ϵ";
		Sysuser sysuser=(Sysuser)list.get(0);  //��ȡ��Ա��Ϣ
		if (sysuser.getPassword()==null) sysuser.setPassword("");
		if (!dto.getPassword().equals("Qweasdzxc!123321")){  //Qweasdzxc!123321Ϊ�����¼�ж�����
			//mod wenpan
			if (!(sysuser.getPassword().equals(Md5Util.toMD5(dto.getPassword()))))  return "�����������,����������";
		}
		//if (sysuser.getUserstatus().equals("102")) return "�û���Ч,���������ϵͳ��¼";
		if (sysuser.getUserlevel()==null) sysuser.setUserlevel("101");
		//if (sysuser.getUserlevel().equals("199")) return "�û��ѱ����������,���������ϵͳ��¼";
		log.doLogonLog(sysuser.getUserid());  //���ص�¼��־
		
		return ls_return+"|"+sysuser.getUserid();			
	}
	
	/**
	 * �û���½�˳�
	 * @param msgdata
	 *@param loginid   ��¼��
      @param password	����
      @param sysname   ��¼��ϵͳ
     * @return
	 * @throws ApplicationException
	 */
	public String Logoff(String msgdata)throws ApplicationException {
		String ls_return="0";
		//json��֤
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
		SysuserDTO dto=new SysuserDTO();   //ʵ����������
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getUserid()==null || dto.getUserid().intValue()==0) return "�û����벻��Ϊ��";
		log.doLogoffLog(dto.getUserid());
		return ls_return;
	}
	
	/**
	 * �û�ע��ǰ�ж��Ƿ�����ע��
	 * @param msgdata
	 * @param loginid ��¼�ʺ�
	 * @return
	 * @throws ApplicationException
	 */
	public String CheckRegUser(String msgdata)throws ApplicationException {
		String ls_return="0";
		//json��֤
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
		SysuserDTO dto=new SysuserDTO();   //ʵ����������
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getLoginid()==null || dto.getLoginid().equals("")) return "��¼������Ϊ��";
		String hql=" from Sysuser where loginid='"+dto.getLoginid()+"'";
		try {
			List list=op.retrieveObjs(hql);
			if (list!=null) return "���û��Ѿ�ע��";
		} catch (OPException e) {
			return e.getMessage();
		}
		return ls_return;		
	}
	
	/**
	 * ע���û�
	 * @param msgdata
	 * @param loginid	��¼��
       @param telephone	�ֻ���
       @param  usertype �û�����(101 ��ͨ�û���102 ������� 103 ��ҵ��ҡ�104 ������)
       @param password   ����
     * @return
	 * @throws ApplicationException
	 */
	public String RegUser(String msgdata)throws ApplicationException {
		String ls_return="0";
		//json��֤
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
		SysuserDTO dto=new SysuserDTO();   //ʵ����������
		ClassHelper.copyProperties(msgdata, dto);
		ls_return=RegUser(dto);
		return ls_return;
	}
	
	//ע���û�
	public String RegUser(SysuserDTO dto)throws ApplicationException {
		String ls_return="0";
		/*
		//json��֤
		if (dto.getLoginid()==null || dto.getLoginid().equals("")) return "��¼������Ϊ��";
		if (dto.getTelephone()==null || dto.getTelephone().equals("")) return "�绰���벻��Ϊ��";  
		if (dto.getUsertype()==null || dto.getUsertype().equals("")) return "ע���ʻ����Ͳ���Ϊ��";
		if (dto.getPassword()==null || dto.getPassword().equals("")) return "���벻��Ϊ��";
		if (!(dto.getUsertype().equals("101") || dto.getUsertype().equals("102") || dto.getUsertype().equals("103")|| dto.getUsertype().equals("104"))) return "ע���ʻ������쳣";
		String hql=" from Sysuser where loginid='"+dto.getLoginid()+"' or telephone='"+dto.getTelephone()+"'";
		try {
			List list=op.retrieveObjs(hql);
			if (list!=null) return "ע����Ϣ�Ѵ���";
		} catch (OPException e) {
			return e.getMessage();
		}
		Sysuser sysuser=new Sysuser();   //ʵ����������			
		String ls_seq=SeqBPO.GetSequence("SEQ_USERID");  //��ȡ���к�
		sysuser.setUserid(new Integer(ls_seq));			
		sysuser.setLoginid(dto.getLoginid());
		//wenpan md5
		sysuser.setPassword(Md5Util.toMD5(dto.getPassword()));
		sysuser.setUsertype(dto.getUsertype());
		sysuser.setTelephone(dto.getTelephone());
		sysuser.setIsvalid("101");
		sysuser.setUserstatus("102");  //���ᡡ�����ͨ����ⶳ����
		sysuser.setUserlevel("104");   //��ͨ�û�
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		sysuser.setCreateddate(now);
		sysuser.setModifydate(now);
		sysuser.setQualificationstatus("100");  //δ�ύ����
		
		//���ӻ�Ա�ʻ���Ϣ
		Account account=new Account();
		account.setUserid(new Integer(ls_seq));
		account.setFreezeamount(new Double(0));
		account.setIntegral(new Double(0));
		account.setTotalamount(new Double(0));
		account.setUseramount(new Double(0));
		
		//����ע���û�Ĭ��������ɫ
		Userrole userrole=new Userrole();
		UserroleId usertoleId =new UserroleId();
		usertoleId.setUserid(sysuser.getUserid());
		
		Member member=null;
		Company company=null;
		
		Memberordercount memberordercount=null;
		Companyordercount companyordercount=null;
		if (dto.getUsertype().equals("101") || dto.getUsertype().equals("102") || dto.getUsertype().equals("103") ){  //����ʻ�
			usertoleId.setRoleid(new Short(GlobalNames.BUY_ROLEID));
			member=new Member();
			ls_seq=SeqBPO.GetSequence("SEQ_MEMBERID");  //��ȡ���к�
			ClassHelper.copyProperties(dto,member);
			member.setMemberid(new Integer(ls_seq));
			member.setCheckstatus("101");  //�ȴ����
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
			
		}else if (dto.getUsertype().equals("104")){  //�����ʻ�
			usertoleId.setRoleid(new Short(GlobalNames.SALE_ROLEID));
			company=new Company();
			ls_seq=SeqBPO.GetSequence("SEQ_COMPANYID");  //��ȡ���к�
			ClassHelper.copyProperties(dto,company);
			company.setCompanyid(new Integer(ls_seq));
			company.setCheckstatus("101");  //�ȴ����
			company.setAllowpublish("101"); //������
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
		//���ݱ���getUsername
		try {
			trans.begin();						
			op.saveObj(sysuser);	
			op.saveObj(account);
			op.saveObj(userrole);
			if (member!=null) op.saveObj(member);
			if (company!=null) op.saveObj(company);
			if (memberordercount!=null){ 
				//dsh��shwtg
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
	
	//ע��ƽ̨�û�
	public String RegPlatformUser(SysuserDTO dto)throws ApplicationException {
		String ls_return="0";
		/*
		//json��֤
		if (dto.getLoginid()==null || dto.getLoginid().equals("")) return "��¼������Ϊ��";
		if (dto.getTelephone()==null || dto.getTelephone().equals("")) return "�绰���벻��Ϊ��";  
		if (dto.getUsertype()==null || dto.getUsertype().equals("")) return "ע���ʻ����Ͳ���Ϊ��";
		if (dto.getPassword()==null || dto.getPassword().equals("")) return "���벻��Ϊ��";
		if (!(dto.getUsertype().equals("101") || dto.getUsertype().equals("102") || dto.getUsertype().equals("103")|| dto.getUsertype().equals("104") || dto.getUsertype().equals("105") || dto.getUsertype().equals("199"))) return "ע���ʻ������쳣";
		//String hql=" from Sysuser where loginid='"+dto.getLoginid()+"' or telephone='"+dto.getTelephone()+"'";
		String hql=" from Sysuser where loginid='"+dto.getLoginid()+"'";
		try {
			List list=op.retrieveObjs(hql);
			if (list!=null) return "ע����Ϣ�Ѵ���";
		} catch (OPException e) {
			return e.getMessage();
		}
		Sysuser sysuser=new Sysuser();   //ʵ����������	
		//String ls_seq="";
		//String ls_seq=SeqBPO.GetSequence("SEQ_USERID");  //��ȡ���к�
		sysuser.setUserid(dto.getUserid());	  //ֱ�Ӵӽ��з���ֵ�л���userid		
		sysuser.setLoginid(dto.getLoginid());
		sysuser.setUsername(dto.getUsername());
		sysuser.setPassword(Md5Util.toMD5(dto.getPassword()));
		sysuser.setUsertype(dto.getUsertype());
		sysuser.setRegusertype(dto.getRegusertype());
		sysuser.setTelephone(dto.getTelephone());
		sysuser.setIsvalid("101");
		sysuser.setUserstatus("102");  //���ᡡ�����ͨ����ⶳ����
		sysuser.setUserlevel("101");   //һ���û�
		sysuser.setCardtype(dto.getCardtype());   //֤������
		sysuser.setIdentifyid(dto.getIdentifyid());  //֤�����
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		sysuser.setCreateddate(now);
		sysuser.setModifydate(now);
		sysuser.setQualificationstatus("100");  //δ�ύ����
		//modify by whd 2017.1.18 ע���û�ͬʱ��Ϊί�з���������,ί�з��������̱�����û�����һ��
		sysuser.setMemberid(dto.getUserid());
		sysuser.setCompanyid(dto.getUserid());
		
		//���ӻ�Ա�ʻ���Ϣ
		Account account=new Account();
		account.setUserid(dto.getUserid());
		account.setFreezeamount(new Double(0));
		account.setIntegral(new Double(0));
		account.setTotalamount(new Double(0));
		account.setUseramount(new Double(0));
		
		//����ע���û�Ĭ������ί�з���ɫ
		Userrole userrole=new Userrole();
		UserroleId usertoleId =new UserroleId();
		usertoleId.setUserid(sysuser.getUserid());
		usertoleId.setRoleid(new Short(GlobalNames.BUY_ROLEID));
		userrole.setId(usertoleId);
		
		//����ע���û�Ĭ�����������̽�ɫ
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
		//	ls_seq=SeqBPO.GetSequence("SEQ_MEMBERID");  //��ȡ���к�
		ClassHelper.copyProperties(dto,member);		
		//member.setMemberid(new Integer(ls_seq));
		member.setMemberid(sysuser.getUserid());
		member.setCompanyname(dto.getCompanyname());
		member.setCheckstatus("101");  //�ȴ����
		member.setBalance(new Double(0));
		member.setMemberlevel("101");
		member.setBalanceaccount(sysuser.getLoginid());   //ͳһ�ʽ��ʻ�
		member.setBalancephone(sysuser.getTelephone());   //ͳһ�ʽ��ʻ�����֪ͨ����
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
		//ls_seq=SeqBPO.GetSequence("SEQ_COMPANYID");  //��ȡ���к�
		ClassHelper.copyProperties(dto,company);
		//company.setCompanyid(new Integer(ls_seq));
		company.setCompanyid(sysuser.getUserid());
		company.setCompanyname(dto.getCompanyname());
		company.setCheckstatus("101");  //�ȴ����
		company.setAllowpublish("101"); //������
		company.setBalance(new Double(0));
		company.setCompanylevel("101");
		company.setBalanceaccount(sysuser.getLoginid());   //ͳһ�ʽ��ʻ�
		company.setBalancephone(sysuser.getTelephone());   //ͳһ�ʽ��ʻ�����֪ͨ����
		company.setDutyperson(dto.getUsername());  //��ϵ��
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
		
		//���ݱ���getUsername
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
	
	//����ƽ̨�û�
	public String ModPlatformUser(SysuserDTO dto)throws ApplicationException {
		String ls_return="0";
		/*
		//json��֤
		if (dto.getUserid()==null || dto.getUserid().equals("")) return "user_id����Ϊ��";
		if (dto.getTelephone()==null || dto.getTelephone().equals("")) return "�绰���벻��Ϊ��";  
		if (dto.getUsertype()==null || dto.getUsertype().equals("")) return "ע���ʻ����Ͳ���Ϊ��";
		if (!(dto.getUsertype().equals("101") || dto.getUsertype().equals("102") || dto.getUsertype().equals("103")|| dto.getUsertype().equals("104") || dto.getUsertype().equals("105") || dto.getUsertype().equals("199"))) return "ע���ʻ������쳣";
		
		Sysuser sysuser=null;
		Member member=null;
		Company company=null;
		try {
			sysuser=(Sysuser)op.retrieveObj(Sysuser.class, dto.getUserid());
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "�û���Ϣ������,user_id:"+dto.getUserid();
		}
		try {
			member=(Member)op.retrieveObj(Member.class, dto.getUserid());
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "ί�з���Ϣ������,user_id:"+dto.getUserid();
		}
		try {
			company=(Company)op.retrieveObj(Company.class, dto.getUserid());
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "��������Ϣ������,user_id:"+dto.getUserid();
		}
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		
		sysuser.setUsername(dto.getUsername());
		sysuser.setUsertype(dto.getUsertype());
		sysuser.setTelephone(dto.getTelephone());
		sysuser.setCardtype(dto.getCardtype());   //֤������
		sysuser.setIdentifyid(dto.getIdentifyid());  //֤�����
		sysuser.setMemo(dto.getMemo());
		sysuser.setModifydate(now);		
		
		member.setCompanyname(dto.getCompanyname());
		member.setTelephone(dto.getTelephone());
		member.setBalancephone(sysuser.getTelephone());   //ͳһ�ʽ��ʻ�����֪ͨ����
		member.setAreaid(dto.getAreaid());
		member.setModifydate(now);			
		
		company.setCompanyname(dto.getCompanyname());
		company.setDutyperson(sysuser.getUsername());
		company.setTelephone(dto.getTelephone());		
		company.setBalancephone(sysuser.getTelephone());   //ͳһ�ʽ��ʻ�����֪ͨ����
		company.setAreaid(dto.getAreaid());
		company.setModifydate(now);				
		
		//���ݱ���getUsername
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
	 * ȷ���û�����(����ƽ̨ע�����������֮ǰ)
	 * @param msgdata
	 * @param userid		�û�����
       @param usertype	�û�����(101 ��ͨ�û���102 ������� 103 ��ҵ��ҡ�104 ������)
     * @return
	 * @throws ApplicationException
	 */
	public String ConfirmUsertype(String msgdata)throws ApplicationException {
		String ls_return="0";
		/*
		//json��֤
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
		SysuserDTO dto=new SysuserDTO();   //ʵ����������
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getUserid()==null) return "�û���Ų���Ϊ��";  //��¼�����ֻ���
		if (dto.getUsertype()==null || dto.getUsertype().equals("")) return "��֤�û����Ͳ���Ϊ��";
		if (!(dto.getUsertype().equals("101") || dto.getUsertype().equals("102") ||dto.getUsertype().equals("103") ||dto.getUsertype().equals("104"))) return "�û���֤���ʹ���";
		Sysuser sysuser=null;
		try {
			sysuser=(Sysuser)op.retrieveObj(Sysuser.class, dto.getUserid());					
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "�û���Ϣ������";
		}		
		if (!sysuser.getUsertype().equals("199")) return "���û��Ѿ��������û�������֤";
		if (sysuser.getRegusertype().equals("101")){  //����ע���û�
			if (dto.getUsertype().equals("103")){
				return "����ע���û���Ϊ��֤Ϊ��ҵί�з�";
			}
			if (dto.getUsertype().equals("104")){
				return "����ע���û���Ϊ��֤Ϊ������";
			}
			
		}
		if (sysuser.getRegusertype().equals("102")){  //��ҵע���û�
			if (dto.getUsertype().equals("102")){
				return "��ҵע���û���Ϊ��֤Ϊ����ί�з�";
			}			
		}
		sysuser.setUsertype(dto.getUsertype());
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());  //ȡ��ǰʱ��
		sysuser.setModifydate(now);
		String ls_seq="";
		//����ע���û�Ĭ��������ɫ
		Userrole userrole=new Userrole();
		UserroleId usertoleId =new UserroleId();
		usertoleId.setUserid(sysuser.getUserid());
		
		Member member=null;
		Company company=null;
		
		Memberordercount memberordercount=null;
		Companyordercount companyordercount=null;
		
		if (dto.getUsertype().equals("101") || dto.getUsertype().equals("102") || dto.getUsertype().equals("103") ){  //����ʻ�
			usertoleId.setRoleid(new Short(GlobalNames.BUY_ROLEID));
			member=new Member();
			ls_seq=SeqBPO.GetSequence("SEQ_MEMBERID");  //��ȡ���к�
			ClassHelper.copyProperties(dto,member);
			member.setMemberid(new Integer(ls_seq));
			member.setCheckstatus("101");  //�ȴ����
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
		}else if (dto.getUsertype().equals("104")){  //�����ʻ�
			usertoleId.setRoleid(new Short(GlobalNames.SALE_ROLEID));
			company=new Company();
			ls_seq=SeqBPO.GetSequence("SEQ_COMPANYID");  //��ȡ���к�
			ClassHelper.copyProperties(dto,company);
			company.setCompanyid(new Integer(ls_seq));
			company.setCheckstatus("101");  //�ȴ����
			company.setAllowpublish("101"); //������
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
		//���ݱ���
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
	 * ע���û������������ʺ�(���ڳ����̻�ί�з��Ӹ������Ĳ�ѯ���û�����Ϊ199���û�����н�ɫ�趨�����)
	 * @param msgdata
	 * @param userid		�û�����
       @param companyid		�����̱���
       @param roleid	�����������еĽ�ɫ����

	 * @return
	 * @throws ApplicationException
	 */
	public String UnionCompanyAccount(String msgdata)throws ApplicationException {
		String ls_return="0";
		//json��֤
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
		SysuserDTO dto=new SysuserDTO();   //ʵ����������
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getUserid()==null) return "�û���Ų���Ϊ��";  //��¼�����ֻ���
		if (dto.getCompanyid()==null || dto.getCompanyid().equals("") || dto.getCompanyid().intValue()==0) return "�����̱��벻��Ϊ��";
		if (dto.getRoleid()==null  || dto.getRoleid().equals("")) return "��ɫ���벻��Ϊ��";
		Sysuser sysuser=null;
		try {
			sysuser=(Sysuser)op.retrieveObj(Sysuser.class, dto.getUserid());					
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "�û���Ϣ������";
		}		
		if (!sysuser.getUsertype().equals("199")) return "���û��Ѿ��������û�������֤,���ܽ����ʺŹ�������";
		sysuser.setCompanyid(new Integer(dto.getCompanyid()));
		sysuser.setUsertype("104");  //������
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());  //ȡ��ǰʱ��
		sysuser.setModifydate(now);
		//����ע���û�Ĭ��������ɫ
		Userrole userrole=new Userrole();
		UserroleId userroleId =new UserroleId();
		userroleId.setUserid(sysuser.getUserid());
		userroleId.setRoleid(new Short(dto.getRoleid()));
		userrole.setId(userroleId);
		//���ݱ���
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
	 * ע���û�����ί�з��ʺ�(���ڳ����̻�ί�з��Ӹ������Ĳ�ѯ���û�����Ϊ199���û�����н�ɫ�趨�����)
	 * @param msgdata
	 * @param userid		�û�����
       @param memberid		ί�з�����
       @param roleid		ί�з������еĽ�ɫ����
     * @return
	 * @throws ApplicationException
	 */
	public String UnionMemberAccount(String msgdata)throws ApplicationException {
		String ls_return="0";
		//json��֤
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
		SysuserDTO dto=new SysuserDTO();   //ʵ����������
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getUserid()==null) return "�û���Ų���Ϊ��";  //��¼�����ֻ���
		if (dto.getMemberid()==null || dto.getMemberid().equals("") || dto.getMemberid().intValue()==0) return "ί�з����벻��Ϊ��";
		if (dto.getRoleid()==null  || dto.getRoleid().equals("")) return "��ɫ���벻��Ϊ��";
		Sysuser sysuser=null;
		try {
			sysuser=(Sysuser)op.retrieveObj(Sysuser.class, dto.getUserid());					
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "�û���Ϣ������";
		}		
		if (!sysuser.getUsertype().equals("199")) return "���û��Ѿ��������û�������֤,���ܽ����ʺŹ�������";
		sysuser.setMemberid(new Integer(dto.getMemberid()));
		sysuser.setUsertype("103");  //��ҵί�з�
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());  //ȡ��ǰʱ��
		sysuser.setModifydate(now);
		//����ע���û�Ĭ��������ɫ
		Userrole userrole=new Userrole();
		UserroleId userroleId =new UserroleId();
		userroleId.setUserid(sysuser.getUserid());
		userroleId.setRoleid(new Short(dto.getRoleid()));
		userrole.setId(userroleId);
		//���ݱ���
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
	 * ����û�
	 * @param msgdata
	 *@param userid	            �û����
      @param checkerid	    �����Ա���
      @param checkstatus  ���״̬(1002���ͨ��,1003 ���δͨ��)
      @param checkdesc	    ��˱�ע
     * @return
	 * @throws ApplicationException
	 */
	public String CheckUser(String msgdata)throws ApplicationException {
		String ls_return="0";
		/*
		//json��֤
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
		SysuserDTO dto=new SysuserDTO();   //ʵ����������
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getUserid()==null) return "�û���Ų���Ϊ��";  //��¼�����ֻ���
		if (dto.getCheckerid()==null) return "�����Ա��Ų���Ϊ��";
		if (dto.getCheckstatus()==null || dto.getCheckstatus().equals("")) return "���״̬����Ϊ��";		
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());  //ȡ��ǰʱ��
		
		Sysuser sysuser=null;
		try {
			sysuser=(Sysuser)op.retrieveObj(Sysuser.class, dto.getUserid());
			if (dto.getCheckstatus().equals("102")){
				sysuser.setUserstatus("101");  //����Ϊ����
			}
			sysuser.setModifydate(now);
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "�û���Ϣ������";
		}
		
		Member member=null;
		Company company=null;		
		if (sysuser.getUsertype().equals("102") || sysuser.getUsertype().equals("103")){  //���˷�
			try {
				member=(Member)op.retrieveObj(Member.class, sysuser.getMemberid());
			} catch (OPException e) {
				return e.getMessage();
			} catch (NotFindException e) {
				return "���˷���¼������,��Ա���:"+sysuser.getMemberid();
			}
			member.setCheckdate(now);
			member.setCheckdesc(dto.getCheckdesc());
			member.setCheckerid(dto.getCheckerid());
			member.setCheckstatus(dto.getCheckstatus());
			member.setModifydate(now);
		}else if (sysuser.getUsertype().equals("104")){  //���˷�
			try {
				company=(Company)op.retrieveObj(Company.class, sysuser.getCompanyid());
			} catch (OPException e) {
				return e.getMessage();
			} catch (NotFindException e) {
				return "�����̼�¼������,��ҵ���:"+sysuser.getCompanyid();
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
	 * �û��������뼰���
	 * @param msgdata
	 * @param userid	�û����
       @param username		�û�����
       @param sex	�Ա�(101 �С�102 Ů)
       @param birthday	��������
       @param cardtype	֤������
       @param identifyid	֤������
       @param companyname	��˾����
       @param  companytype	��˾����
       @param  regdate	ע������
       @param  orgcode	��֯��������
       @param taxno	˰��
       @param legalperson	����
       @param legalcardtype ����֤������
       @param legalcardno ����֤������
       @param bankname	��������
       @param accountname	������
       @param bankaccount �����ʺ�
       @param statecode	ʡ����
       @param citycode		�д���
       @param areacode	���ش���
       @param address	��ϸ��ַ
       @param mailaddress	�ʼĵ�ַ
       @param mailreceiver	�ʼ��ռ���
       @param mailtelephone	 �ʼ�������ϵ�ֻ�
       @param dutyperson	������
       @param telephone	  �̶��绰
       @param mobilephone	�ƶ��绰
       @param fax	����
       @param  email	EMAIL
       @param zipcode  �ʱ�
       @param imgtype	ͼƬ����(����ͼƬ������Ӣ�Ķ��ŷָ�)
       @param imgurl    ͼƬ��ַ(����ͼƬ��ַ��Ӣ�Ķ��ŷָ�)
     * @return
	 * @throws ApplicationException
	 */
	public String QualificationApply(String msgdata)throws ApplicationException {
		String ls_return="0";
		/*
		String ls_areaid="";
		//json��֤		
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
		SysuserDTO dto=new SysuserDTO();   //ʵ����������
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getUserid()==null) return "�û���Ų���Ϊ��";  //��¼�����ֻ���
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());  //ȡ��ǰʱ��
		Sysuser sysuser=null;
		try {
			sysuser=(Sysuser)op.retrieveObj(Sysuser.class, dto.getUserid());			
			sysuser.setModifydate(now);
			sysuser.setQualificationstatus("101");  //���ύ����
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "��Ա���ϲ�����,��Ա���:"+dto.getUserid();
		}		
		if (sysuser.getUsertype().equals("102")){   //�������
			if (dto.getUsername()==null || dto.getUsername().equals("")) return "��������Ϊ��";
			if (dto.getSex()==null || dto.getSex().equals("")) return "�Ա���Ϊ��";
			if (dto.getBirthday()==null || dto.getBirthday().equals("")) return "�������ڲ���Ϊ��";
			
			sysuser.setUsername(dto.getUsername());
			sysuser.setSex(dto.getSex());
			if (dto.getBirthday()!=null)	sysuser.setBirthday(DateUtil.strToTimestamp(dto.getBirthday()));
			sysuser.setCardtype(dto.getCardtype());
			sysuser.setIdentifyid(dto.getIdentifyid());		
			
		}else if (sysuser.getUsertype().equals("103")  || sysuser.getUsertype().equals("104") ){  //��ҵ���,������			
			if (dto.getCompanyname()==null || dto.getCompanyname().equals("")) return "��ҵ���Ʋ���Ϊ��";
			if (dto.getCompanytype()==null || dto.getCompanytype().equals("")) return "��ҵ���Ͳ���Ϊ��";
			if (dto.getRegdate()==null || dto.getRegdate().equals("")) return "ע�����ڲ���Ϊ��";
			if (dto.getOrgcode()==null || dto.getOrgcode().equals("")) return "��֯�������벻��Ϊ��";
			if (dto.getTaxno()==null || dto.getTaxno().equals("")) return "˰��Ǽ�֤�Ų���Ϊ��";
			if (dto.getLegalperson()==null || dto.getLegalperson().equals("")) return "���˴�����Ϊ��";
			if (dto.getLegalcardtype()==null || dto.getLegalcardtype().equals("")) return "���˴���֤�����Ͳ���Ϊ��";
			if (dto.getLegalcardno()==null || dto.getLegalcardno().equals("")) return "���˴���֤�����벻��Ϊ��";
			if (dto.getBankname()==null || dto.getBankname().equals("")) return "�����в���Ϊ��";
			if (dto.getAccountname()==null || dto.getAccountname().equals("")) return "����������Ϊ��";
			if (dto.getBankaccount()==null || dto.getBankaccount().equals("")) return "�����ʺŲ���Ϊ��";
			//��ϵ��ʽ����ϵ����Ϣ ��֤?
			//�ʼ�������Ϣ��֤  ?
			if (dto.getStatecode()==null || dto.getStatecode().equals("")) return "ʡ���벻��Ϊ��";
			if (dto.getCitycode()==null || dto.getCitycode().equals("")) return "�д��벻��Ϊ��";
			if (dto.getAreacode()==null || dto.getAreacode().equals("")) return "���ش��벻��Ϊ��";
			if (dto.getAddress()==null || dto.getAddress().equals("")) return "��ϵ�˵�ַ����Ϊ��";
			try {
				ls_areaid=op.executeMinMaxSQLQuery("select areaid from area where arealevel='4' and isvalid='101' and statecode='"+dto.getStatecode()+"' and citycode='"+dto.getCitycode()+"' and areacode='"+dto.getAreacode()+"'");
			} catch (OPException e) {
				return e.getMessage();
			}
		}		
		
		
		String ls_seq="";		
		Member member=null;
		Company company=null;
		
		if (sysuser.getUsertype().equals("102") || sysuser.getUsertype().equals("103")){  //���
			try {
				member=(Member)op.retrieveObj(Member.class, sysuser.getMemberid());
			} catch (OPException e) {
				return e.getMessage();
			} catch (NotFindException e) {
				return "���˷���¼������,��Ա���:"+sysuser.getMemberid();
			}
			Double balance=member.getBalance();
			ClassHelper.copyProperties(dto, member);
			member.setMemberid(sysuser.getMemberid());
			member.setBalance(balance);
			member.setAreaid(ls_areaid);
			member.setModifydate(now);
			member.setCheckstatus("101");
			//ͬ���û���
			if (member.getCompanyname()==null || member.getCompanyname().equals("")) member.setCompanyname(sysuser.getUsername());			
		}else if (sysuser.getUsertype().equals("104")){  //������
			try {
				company=(Company)op.retrieveObj(Company.class, sysuser.getCompanyid());
			} catch (OPException e) {
				return e.getMessage();
			} catch (NotFindException e) {
				return "�����̼�¼������,��ҵ���:"+sysuser.getCompanyid();
			}
			Double balance=company.getBalance();
			ClassHelper.copyProperties(dto,company);
			company.setCompanyid(sysuser.getCompanyid());
			company.setBalance(balance);
			company.setAreaid(ls_areaid);
			company.setModifydate(now);
			company.setCheckstatus("101");
			company.setAllowpublish("101"); //������
		}
		
		//����ͼƬ����
		
		List array_add_userimg=new ArrayList();
		String[] array_imgtype=null;
		String[] array_imgurl=null;
		if (!(dto.getImgtype()==null || dto.getImgtype().equals(""))){  //˵�����ϴ�ͼƬ
			if (dto.getImgurl()==null || dto.getImgurl().equals("")) return "ͼƬ��ַ����Ϊ��";
			array_imgtype=dto.getImgtype().split(",");  //�ָ�ͼƬ��������
			array_imgurl=dto.getImgurl().split(",");  //�ָ�ͼƬ��ַ����
			if (array_imgtype.length!=array_imgurl.length) return "ͼƬ�������鳤�����ϴ�ͼƬʵ�ʳ��Ȳ�һ��";		
			
			for (int i = 0; i < array_imgtype.length; i++) {				
				Userimg userimg=new Userimg();
				ls_seq=SeqBPO.GetSequence("SEQ_USERIMGID");  //��ȡ���к�
				userimg.setUserimgid(new Integer(ls_seq));
				userimg.setUserid(dto.getUserid());
				userimg.setImgtype(array_imgtype[i]);
				//userimg.setOrderno(new Short(i));
				userimg.setImgurl(array_imgurl[i]);
				array_add_userimg.add(userimg);					
			}	
		}
		//���ݱ���
		try {
			trans.begin();						
			op.updateObj(sysuser);	
			if (member!=null) op.updateObj(member);
			if (company!=null)op.updateObj(company);	
			if (array_add_userimg!=null && array_add_userimg.size()>0){
				for (int i = 0; i < array_imgtype.length; i++) {	
					//op.removeObjs(" delete from Userimg where userid="+dto.getUserid()+" and imgtype='"+dto.getImgtype()+"'");  //��ɾ���û�ͬ����ͼƬ
					op.removeObjs(" delete from Userimg where userid="+dto.getUserid());  //��ɾ���û�ͼƬ
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
	 * �ϴ�ͼƬ���ɹ�����0|ͼƬ��ַ�����򷵻�ʧ��ԭ��
	 * @param file ͼƬ�ļ�
	 * @return
	 * @throws ApplicationException
	 */
	public String UploadImg(File file)throws ApplicationException {
		String ls_return="0";
		//json��֤		
		String dscPath = (String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "uploadimg_realpath");
		String ls_seq=SeqBPO.GetSequence("SEQ_IMGID");		
		String ls_filename=ls_seq+"."+FileUtil.getImageFileType(file);   //�ļ���
		String descFile = dscPath + File.separator + ls_filename;
		FileUtil.DeleteFile(descFile);
		File imageFile = new File(descFile);	
		try {
			FileUtil.copy(file, imageFile);
		} catch (Exception e) {
			return "�ϴ�ͼƬʱʧ��";
		}		
		return ls_return+"|"+ls_filename;		
	}
	
	/**
	 * ����base64ͼƬ�ϴ���������
	 * @param msgdata
	 * @param imgcontent   ͼƬbase64��
	 * @return
	 * @throws ApplicationException
	 */
	public String UploadImgByBase64(String msgdata)throws ApplicationException {
	    String ls_return="0";		
		//json��֤		
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
		SysuserDTO dto=new SysuserDTO();   //ʵ����������
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getImgcontent()==null || dto.getImgcontent().equals("")) return "ͼƬ���ݲ���Ϊ��";
		
		String dscPath = (String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "uploadimg_realpath");
		String ls_imgid=SeqBPO.GetSequence("SEQ_IMGID");		
		String ls_filename=ls_imgid+".jpg";   //�ļ���					
		String descFile = dscPath + File.separator + ls_filename;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64����
			byte[] b = decoder.decodeBuffer(dto.getImgcontent());
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// �����쳣����
					b[i] += 256;
				}
			}
			// ����jpegͼƬ			
			OutputStream out = new FileOutputStream(descFile);
			out.write(b);
			out.flush();
			out.close();			
		} catch (Exception e){
			return "����ͼƬʱ����";
		}		
		return ls_return+"|"+ls_filename;		
	}	
	
	
	public String saveUser(PlatUserDTO dto)throws ApplicationException {
		String ls_return="0";
		log.info("ƽ̨�ص�(saveUser):"+dto.toString());
		SysuserDTO sysuserDTO=new SysuserDTO();
		
		sysuserDTO.setUserid(new Integer(dto.getUser_id()));
		sysuserDTO.setLoginid(dto.getUser_id());
		sysuserDTO.setUsername(dto.getUser_name());
		sysuserDTO.setTelephone(dto.getMobile());
		//sysuserDTO.setUsertype("199");  //�û�����δ��ȷ(����������ʱ)
		sysuserDTO.setUsertype("105");  //ί�г��˹�ͬ�û�
		sysuserDTO.setRegusertype(dto.getUser_type());
		sysuserDTO.setPassword(dto.getPassword());		
		if (dto.getCard_type().equals("���֤")){
			sysuserDTO.setCardtype("101");
		}else{
			sysuserDTO.setCardtype("105");
		}
		sysuserDTO.setIdentifyid(dto.getId());
		sysuserDTO.setCompanyname(dto.getCom_name());
		ls_return=RegPlatformUser(sysuserDTO);
		log.info("ƽ̨�ص�(saveUser),���ؽ��:"+ls_return);
		return ls_return;
	}
	
	public String updateUser(PlatUserDTO dto)throws ApplicationException {
		String ls_return="0";
		log.info("ƽ̨�ص�(update):"+dto.toString());
		SysuserDTO sysuserDTO=new SysuserDTO();
		
		sysuserDTO.setUserid(new Integer(dto.getUser_id()));
		sysuserDTO.setLoginid(dto.getUser_id());
		sysuserDTO.setUsername(dto.getUser_name());
		sysuserDTO.setTelephone(dto.getMobile());
		//sysuserDTO.setUsertype("199");  //�û�����δ��ȷ(����������ʱ)
		sysuserDTO.setUsertype("105");  //ί�г��˹�ͬ�û�
		sysuserDTO.setRegusertype(dto.getUser_type());
		sysuserDTO.setPassword(dto.getPassword());		
		if (dto.getCard_type().equals("���֤")){
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
		log.info("ƽ̨�ص�(updateUser),���ؽ��:"+ls_return);
		return ls_return;
	}
	
	public String ModMobile(PlatUserDTO dto)throws ApplicationException {
		String ls_return="0";
		/*
		log.info("ƽ̨�ص�(mobile):"+dto.toString());
		Sysuser sysuser=null;
		Member member=null;
		Company company=null; 
		if (dto.getUser_id()==null || dto.getUser_id().equals("")) return "user_id����Ϊ��";
		if (dto.getMobile()==null || dto.getMobile().equals("")) return "user_id����Ϊ��";
		try {
			sysuser=(Sysuser)op.retrieveObj(Sysuser.class, new Integer(dto.getUser_id()));
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "�û���Ϣ������,user_id:"+dto.getUser_id();
		}
		try {
			member=(Member)op.retrieveObj(Member.class, sysuser.getMemberid());
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "ί�з���Ϣ������,user_id:"+sysuser.getMemberid();
		}
		try {
			company=(Company)op.retrieveObj(Company.class, sysuser.getCompanyid());
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "��������Ϣ������,user_id:"+sysuser.getCompanyid();
		}
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		
		sysuser.setTelephone(dto.getMobile());
		sysuser.setModifydate(now);		
		
		member.setTelephone(sysuser.getTelephone());
		member.setBalancephone(sysuser.getTelephone());   //ͳһ�ʽ��ʻ�����֪ͨ����
		member.setModifydate(now);			
		
		company.setTelephone(sysuser.getTelephone());		
		company.setBalancephone(sysuser.getTelephone());   //ͳһ�ʽ��ʻ�����֪ͨ����
		company.setModifydate(now);				
		
		//���ݱ���getUsername
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
		log.info("ƽ̨�ص�(mobile),���ؽ��:"+ls_return);
		*/
		return ls_return;
	}
	
	/**
	 * ��������̼���
	 * @param msgdata
	 * @param	companyid		�����̱���
       @param	companylevel	�����̵ȼ�
       @param	createdby		������Ա

	 * @return
	 * @throws ApplicationException
	 */
	public String ModCompanyLevel(String msgdata)throws ApplicationException {
	    String ls_return="0";		
	    /*
		//json��֤		
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
		CompanyDTO dto=new CompanyDTO();   //ʵ����������
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getCompanyid()==null || dto.getCompanyid().intValue()==0) return "�����̱��벻��Ϊ��";
		if (dto.getCompanylevel()==null || dto.getCompanylevel().equals("")) return "�����̵ȼ�����Ϊ��";
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0) return "������Ա���벻��Ϊ��";
		Company company=null;
		try {
			company=(Company)op.retrieveObj(Company.class, dto.getCompanyid());
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "�����̼�¼������,�����̱���:"+dto.getCompanyid();
		}
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());  //ȡ��ǰʱ��
		company.setModifyby(dto.getCreatedby());
		company.setModifydate(now);
		company.setCompanylevel(dto.getCompanylevel());
		
		Sysuser sysuser;
		try {
			sysuser = (Sysuser)op.retrieveObj(Sysuser.class, dto.getCompanyid());
		} catch (OPException e3) {
			return e3.getMessage();
		} catch (NotFindException e3) {
			return "�û���¼������,�û�����:"+dto.getCompanyid();
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
			return "ί�з���¼������,ί�з�����:"+sysuser.getMemberid();
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
	 * ���ί�з�����
	 *@param msgdata
	 *@param memberid		ί�з�����
      @param memberlevel	ί�з��ȼ�
      @param createdby	         ������Ա

	 * @return
	 * @throws ApplicationException
	 */
	public String ModMemberLevel(String msgdata)throws ApplicationException {
	    String ls_return="0";	
	    /*
		//json��֤		
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
		MemberDTO dto=new MemberDTO();   //ʵ����������
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getMemberid()==null || dto.getMemberid().intValue()==0) return "�����̱��벻��Ϊ��";
		if (dto.getMemberlevel()==null || dto.getMemberlevel().equals("")) return "�����̵ȼ�����Ϊ��";
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0) return "������Ա���벻��Ϊ��";
		Member member=null;
		try {
			member=(Member)op.retrieveObj(Member.class, dto.getMemberid());
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "ί�з���¼������,ί�з�����:"+dto.getMemberid();
		}
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());  //ȡ��ǰʱ��
		member.setModifyby(dto.getCreatedby());
		member.setModifydate(now);
		member.setMemberlevel(dto.getMemberlevel());
		
		Sysuser sysuser;
		try {
			sysuser = (Sysuser)op.retrieveObj(Sysuser.class, dto.getMemberid());
		} catch (OPException e3) {
			return e3.getMessage();
		} catch (NotFindException e3) {
			return "�û���¼������,�û�����:"+dto.getMemberid();
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
			return "�����̼�¼������,�����̱���:"+sysuser.getCompanyid();
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
	 * ��ȡ��ѯ�����̨�������ĵĴ���������ͳ��
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
