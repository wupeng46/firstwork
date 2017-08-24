package com.lbs.apps.common;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.lbs.apps.system.po.Noticemsg;
import com.lbs.apps.system.po.Userrole;
import com.lbs.commons.TransManager;
import com.lbs.commons.op.OPException;
import com.lbs.commons.op.OPManager;

public class NoticeUtil {
	static OPManager op = new OPManager();
	static TransManager trans = new TransManager();
	//��ȡ��ɫ
	private static String getRole(String domaincode) throws OPException{
		String roles_sql="select parameter2 from syscode where domainid='NOTICETYPE' and domaincode='"+domaincode+"'";
		String roles = op.executeMinMaxSQLQuery(roles_sql);
		return roles;
	}
	
	
	
	//��ȡ��ɫ������û�
	private static List<Userrole> getUser(String domaincode) throws OPException{
		String roles=NoticeUtil.getRole(domaincode);
		String[] array_role=roles.split(",");
		List<Userrole> user_list = new ArrayList();
		for(int i=0;i<array_role.length;i++){
			List users_list = new ArrayList();
			/*String user_sql = "select userid from userrole where roleid="+ array_role[i];
			users_list = (List) op.executeSQLQuery(user_sql);*/
			String hql=" from Userrole a where a.id.roleid="+array_role[i];
			List<Userrole> list_userrole=op.retrieveObjs(hql);
			if (list_userrole!=null){
				for(int j=0;j<list_userrole.size();j++){
					user_list.add(list_userrole.get(j));
				}
			}
		}
		return user_list;
	}
	
	//��ȡ��������Ӧ��ɫ������û�
	private static List<Userrole> getMemberUser(String domaincode,Integer memberid) throws OPException{
		String roles=NoticeUtil.getRole(domaincode);
		String[] array_role=roles.split(",");
		List<Userrole> user_list = new ArrayList();
		for(int i=0;i<array_role.length;i++){
			List users_list = new ArrayList();
			/*String user_sql = "select userid from userrole where roleid="+ array_role[i];
			users_list = (List) op.executeSQLQuery(user_sql);*/
			String hql="select a from Userrole a,Sysuser b where a.id.userid=b.userid and b.memberid="+memberid+" and a.id.roleid="+array_role[i];
			List<Userrole> list_userrole=op.retrieveObjs(hql);
			if (list_userrole!=null){
				for(int j=0;j<list_userrole.size();j++){
					user_list.add(list_userrole.get(j));
				}
			}
		}
		return user_list;
	}
	
	//��ȡ��������Ӧ��ɫ������û�
	private static List<Userrole> getCompanyUser(String domaincode,Integer companyid) throws OPException{
		String roles=NoticeUtil.getRole(domaincode);
		String[] array_role=roles.split(",");
		List<Userrole> user_list = new ArrayList();
		for(int i=0;i<array_role.length;i++){
			List users_list = new ArrayList();
			/*String user_sql = "select userid from userrole where roleid="+ array_role[i];
			users_list = (List) op.executeSQLQuery(user_sql);*/
			String hql="select a from Userrole a,Sysuser b where a.id.userid=b.userid and b.companyid="+companyid+" and a.id.roleid="+array_role[i];
			List<Userrole> list_userrole=op.retrieveObjs(hql);
			if (list_userrole!=null){
				for(int j=0;j<list_userrole.size();j++){
					user_list.add(list_userrole.get(j));
				}
			}
		}
		return user_list;
	}
	
	//��ȡ��Ϣ������
	private static String getMsgcontent(String domaincode) throws OPException{
		String content_sql="select templatecontent from msgtemplate where msgtemplateid=(select parameter1 from syscode where domainid='NOTICETYPE' and domaincode='"+domaincode+"')";
		String msgcontent = op.executeMinMaxSQLQuery(content_sql);// ��Ϣ����
		return msgcontent;
	}
	
	//��ȡ��Ϣ֪ͨ�Ķ���list
	public static void Noticemsg(String domaincode,Integer createdby, Timestamp now){
		List add_array_notice=new ArrayList();
		List<Userrole> user_list;
		try {
			user_list = NoticeUtil.getUser(domaincode);
			if (user_list==null) return;
			String msgcontent=NoticeUtil.getMsgcontent(domaincode);
			for (int i = 0; i < user_list.size(); i++) {
				Noticemsg noticemsg = new Noticemsg();
				//noticemsg.setNoticetype("101"); // ֪ͨ����
				noticemsg.setNoticetype(domaincode); // ֪ͨ����
				noticemsg.setReceiverid(user_list.get(i).getId().getUserid()); // ������
				noticemsg.setMsgcontent(msgcontent); // ��Ϣ����
				noticemsg.setIsread("101"); // �Ƿ��Ķ�
				noticemsg.setCreatedby(createdby); // ������
				noticemsg.setCreateddate(now); // ����ʱ��
				add_array_notice.add(noticemsg);
			}
		} catch (OPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//���ݱ���
		try {
			trans.begin();
			op.saveObjs(add_array_notice.toArray());
			trans.commit();
		} catch (OPException e1) {
			try {
				trans.rollback();
			} catch (OPException e2) {
				
			}
		}		
	}
	
	//������Ϣ��������
	public static void NoticemsgToMember(String domaincode,Integer createdby, Timestamp now,Integer memberid){
		List add_array_notice=new ArrayList();
		List<Userrole> user_list;
		try {
			user_list = NoticeUtil.getMemberUser(domaincode,memberid);
			if (user_list==null) return;
			String msgcontent=NoticeUtil.getMsgcontent(domaincode);
			for (int i = 0; i < user_list.size(); i++) {
				Noticemsg noticemsg = new Noticemsg();
				//noticemsg.setNoticetype("101"); // ֪ͨ����
				noticemsg.setNoticetype(domaincode); // ֪ͨ����
				noticemsg.setReceiverid(user_list.get(i).getId().getUserid()); // ������
				noticemsg.setMsgcontent(msgcontent); // ��Ϣ����
				noticemsg.setIsread("101"); // �Ƿ��Ķ�
				noticemsg.setCreatedby(createdby); // ������
				noticemsg.setCreateddate(now); // ����ʱ��
				add_array_notice.add(noticemsg);
				//������Ϣ���͸���Ϣ����
				//PushMessage("","","",msgcontent,"",memberid.toString(),createdby.toString(),createdby.toString());	
			}
		} catch (OPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//���ݱ���
		try {
			trans.begin();
			op.saveObjs(add_array_notice.toArray());
			trans.commit();
		} catch (OPException e1) {
			try {
				trans.rollback();
			} catch (OPException e2) {
				
			}
		}		
	}
	
	//������Ϣ��������
	public static void NoticemsgToCompany(String domaincode,Integer createdby, Timestamp now,Integer companyid){
		List add_array_notice=new ArrayList();
		List<Userrole> user_list;
		try {
			user_list = NoticeUtil.getCompanyUser(domaincode,companyid);
			if (user_list==null) return;
			String msgcontent=NoticeUtil.getMsgcontent(domaincode);
			for (int i = 0; i < user_list.size(); i++) {
				Noticemsg noticemsg = new Noticemsg();
				//noticemsg.setNoticetype("101"); // ֪ͨ����
				noticemsg.setNoticetype(domaincode); // ֪ͨ����
				noticemsg.setReceiverid(user_list.get(i).getId().getUserid()); // ������
				noticemsg.setMsgcontent(msgcontent); // ��Ϣ����
				noticemsg.setIsread("101"); // �Ƿ��Ķ�
				noticemsg.setCreatedby(createdby); // ������
				noticemsg.setCreateddate(now); // ����ʱ��
				add_array_notice.add(noticemsg);
				//������Ϣ���͸���Ϣ����
				//PushMessage("","","",msgcontent,"",companyid.toString(),createdby.toString(),createdby.toString());				
			}
		} catch (OPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//���ݱ���
		try {
			trans.begin();
			op.saveObjs(add_array_notice.toArray());
			trans.commit();
		} catch (OPException e1) {
			try {
				trans.rollback();
			} catch (OPException e2) {
				
			}
		}		
	}	
	
}
